package psidev.psi.mi.jami.enricher.impl.protein;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapper;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.protein.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.Collection;

/**
 * The Protein enricher is an enricher which can enrich either single protein or a collection.
 * The Protein enricher has one subEnricher. A protein enricher must be initiated with a protein fetcher.
 *
 * If the protein fetcher also fetches an organism, and the organism fetcher is a mockOrganism fetcher,
 * then the organism from the fetched protein is set in the mock organism fetcher.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  14/06/13
 */
public abstract class AbstractProteinEnricher
        implements ProteinEnricher {

    protected static final Logger log = LoggerFactory.getLogger(AbstractProteinEnricher.class.getName());


    public static final int RETRY_COUNT = 5;

    private ProteinFetcher fetcher = null;
    private ProteinEnricherListener listener = null;

    private OrganismEnricher organismEnricher = null;
    private ProteinRemapper proteinRemapper = null;

    protected Protein proteinFetched = null;

    /**
     * The only constructor which forces the setting of the fetcher
     * If the cvTerm fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param proteinFetcher    The protein fetcher to use.
     */
    public AbstractProteinEnricher(ProteinFetcher proteinFetcher){
        setProteinFetcher(proteinFetcher);
    }

    /**
     * Enrichment of a collection of proteins.
     * @param proteinsToEnrich      The proteins to be enriched
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichProteins(Collection<Protein> proteinsToEnrich) throws EnricherException {
        for(Protein proteinToEnrich : proteinsToEnrich){
            enrichProtein(proteinToEnrich);
        }
    }


    /**
     * Enrichment of a single Protein.
     * At the end of the enrichment, the listener will be fired
     * @param proteinToEnrich       A protein to enrich
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichProtein(Protein proteinToEnrich) throws EnricherException {
        // If there are several entries, try to find one with the same organism as the proteinToEnrich
        // If you don't find one or several entries have the same organism,
        // fire a specific event because we want to track these proteins.

        proteinFetched = fetchProtein(proteinToEnrich);
        if(proteinFetched == null){
            if( log.isTraceEnabled() ) log.trace("No protein has been fetched");
            return ;
        }

        if (! isProteinConflictFree(proteinToEnrich) ) return;

        if(getOrganismEnricher() != null ){
            if (getOrganismEnricher().getOrganismFetcher() == null ||
                    getOrganismEnricher().getMockFetcher() == getOrganismEnricher().getOrganismFetcher()){
                getOrganismEnricher().getMockFetcher().clearEntries();

                getOrganismEnricher().getMockFetcher().addEntry(
                        Integer.toString(proteinToEnrich.getOrganism().getTaxId()),
                        proteinFetched.getOrganism());
            }

            getOrganismEnricher().enrichOrganism(proteinToEnrich.getOrganism());
        }

        //InteractorType
        if(!proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(Protein.PROTEIN_MI)){
            if(proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(
                    Interactor.UNKNOWN_INTERACTOR_MI)){
                proteinToEnrich.setInteractorType(CvTermUtils.createProteinInteractorType());
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onAddedInteractorType(proteinToEnrich);
            }
        }

        processProtein(proteinToEnrich);

        if(getProteinEnricherListener() != null)
            getProteinEnricherListener().onEnrichmentComplete(proteinToEnrich, EnrichmentStatus.SUCCESS, "Protein enriched.");
    }



    /**
     * Checks for fatal conflicts between the proteinToEnrich and the enriched form.
     * The interactor type is the first comparison.
     * It may either be the psi-mi term for 'protein' or 'unknown interactor'.
     * If anything other than these terms are found, the enrichment stops and returns false.
     * <p>
     * The organisms are then compared. If the Enriched organism is null, an exception is fired.
     * If the proteinToEnrich has a null organism, a new, 'unknown' organism is added.
     * Otherwise the organisms taxids' are compared, returning false if they do not match.
     * <p>
     * In both cases, if false is returned, an onProteinEnriched event is fired with a 'failed' status.
     *
     * @param proteinToEnrich   The protein which is being enriched.
     * @return                  Whether there were any conflicts which would halt enrichment
     */
    protected boolean isProteinConflictFree(Protein proteinToEnrich){
        boolean conflictFree = true;
        String existMsg = "";


        //InteractorType
        if(! CvTermUtils.isCvTerm(proteinToEnrich.getInteractorType() , Protein.PROTEIN_MI , null)
                && ! CvTermUtils.isCvTerm(
                        proteinToEnrich.getInteractorType() , Interactor.UNKNOWN_INTERACTOR_MI , null)){

            existMsg = "Conflict in interactorType. " +
                        "Found " + proteinToEnrich.getInteractorType().getShortName() + " " +
                        "(psi-mi id:" + proteinToEnrich.getInteractorType().getMIIdentifier() + "). ";
            conflictFree = false;
        }

        //Organism
        if(proteinFetched.getOrganism() == null) proteinFetched.setOrganism(new DefaultOrganism(-3));
        if(proteinToEnrich.getOrganism() == null) proteinToEnrich.setOrganism(new DefaultOrganism(-3));

        if(proteinToEnrich.getOrganism().getTaxId() > 0
                && proteinToEnrich.getOrganism().getTaxId() != proteinFetched.getOrganism().getTaxId()){
            existMsg = existMsg +
                        "Conflict in organism. " +
                        "Fetched taxid " + proteinFetched.getOrganism().getTaxId() + " " +
                        "but was enriching taxid " + proteinToEnrich.getOrganism().getTaxId() + ".";
            conflictFree = false;
        }

        if(! conflictFree)
            if(getProteinEnricherListener() != null) getProteinEnricherListener().onEnrichmentComplete(
                    proteinToEnrich ,
                    EnrichmentStatus.FAILED ,
                    existMsg);

        return conflictFree;
    }


    /**
     * Fetches the protein entry which matches the proteinToEnrich.
     * Uses the provided proteinFetcher.
     * Will also resort to the proteinRemapper if the protein is dead..
     *
     * @param proteinToEnrich   The protein which is being enriched.
     * @return                  A complete protein record which matches the proteinToEnrich
     */
    protected Protein fetchProtein(Protein proteinToEnrich) throws EnricherException {

        if(getProteinFetcher() == null) throw new IllegalStateException("ProteinFetcher has not been provided.");
        if(proteinToEnrich == null) throw new IllegalArgumentException("Attempted to fetch for a null protein.");

        // If there is no uniprotID - try and remap.
        if(proteinToEnrich.getUniprotkb() == null) {
            if( log.isTraceEnabled() ) log.trace("Remapping protein without uniprotkb");
            if(! remapProtein(proteinToEnrich , "proteinToEnrich has no UniprotKB ID.")){
                return null;
            }
        }

        //
        Collection<Protein> proteinsEnriched = fetchProteinList(proteinToEnrich);


        // If the Protein is dead
        if(proteinsEnriched.isEmpty()){
            // If the protein cannot be remapped - exit
            if( ! remapDeadProtein(proteinToEnrich)){
                return null;
            }else{
                // Otherwise fetch the details of the protein using the fetcher.
                proteinsEnriched = fetchProteinList(proteinToEnrich);

                // If the remapping can not be fetched
                if(proteinsEnriched == null
                        || proteinsEnriched.isEmpty()){
                    if(getProteinEnricherListener() != null)
                        getProteinEnricherListener().onEnrichmentComplete(proteinToEnrich, EnrichmentStatus.FAILED ,
                            "Protein is dead. " +
                            "Was able to remap but the remapped identifier returned no results.");
                    return null;
                }
            }
        }

        // 1 protein, use it
        if(proteinsEnriched.size() == 1) {
            return proteinsEnriched.iterator().next();
        }

        // Examples:
        // - one single entry : P12345
        // - uniprot demerge (different uniprot entries with different organisms) : P77681
        // - uniprot demerge (different uniprot entries with same organisms) : P11163
        // In your enricher, if you have several entries,
        // try to look for the one with the same organism as the protein you try to enrich.
        // If you don't find one or several entries have the same organism,
        // fire a specific event because we want to track these proteins.

        // Many proteins, try and choose
        // TODO - review this as it was commented for an unknown reason.
        if(proteinToEnrich.getOrganism() == null
                || proteinToEnrich.getOrganism().getTaxId() == -3){
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onEnrichmentComplete(proteinToEnrich,  EnrichmentStatus.FAILED ,
                        "Protein is demerged. Found " + proteinsEnriched.size() + " entries. " +
                        "Cannot choose as proteinToEnrich has no organism.");
            return null;
        }

        Protein proteinFetched = null;

        for(Protein protein : proteinsEnriched){
            if(protein.getOrganism() != null && proteinToEnrich.getOrganism() != null
                    && protein.getOrganism().getTaxId() == proteinToEnrich.getOrganism().getTaxId()){

                if(proteinFetched == null) proteinFetched = protein;
                else{
                    // Multiple proteins share this organism - impossible to choose
                    if(getProteinEnricherListener() != null)
                        getProteinEnricherListener().onEnrichmentComplete(proteinToEnrich, EnrichmentStatus.FAILED ,
                            "Protein is demerged. Found " + proteinsEnriched.size() + " entries. " +
                            "Cannot choose as multiple entries match organism in proteinToEnrich.");
                    return null;
                }
            }
        }

        if(proteinFetched == null){
            // No proteins share this organism - impossible to choose
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onEnrichmentComplete(proteinToEnrich, EnrichmentStatus.FAILED ,
                    "Protein is demerged. Found " + proteinsEnriched.size() + " entries. " +
                    "Cannot choose as no entries match the organism in proteinToEnrich.");
            return null;
        }

        //if(log.isInfoEnabled()) log.info("Chose a demerged protein from a choice of "+proteinsEnriched.size());
        return proteinFetched;
    }

    private Collection<Protein> fetchProteinList(Protein proteinToEnrich) throws EnricherException {
        try {
            return fetcher.getProteinsByIdentifier(proteinToEnrich.getUniprotkb());
        } catch (BridgeFailedException e) {
            int index = 0;
            while(index < RETRY_COUNT){
                try {
                    return fetcher.getProteinsByIdentifier(proteinToEnrich.getUniprotkb());
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
        }
    }

    /**
     * Attempts to remap the protein using the provided proteinRemapper.
     * If one has not been included, this method returns false and reports a failure to the listener.
     * If after remapping, the protein still has no entry, this entry returns false.
     *
     * @param proteinToEnrich   The protein to find a remapping for.
     * @param remapCause        The reason the remapping was requested.
     *                          Will be included in the output if the remapping fails.
     * @return                  Whether the remapping was successful.
     */
    protected boolean remapProtein(Protein proteinToEnrich, String remapCause) throws EnricherException {

        // If there is no protein remapper, the enrichment fails
        if( getProteinRemapper() == null ){
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onEnrichmentComplete(proteinToEnrich , EnrichmentStatus.FAILED ,
                    "Attempted to remap because "+remapCause+" "+
                    "Was unable to complete remap as service was missing.");
            return false;
        }

        // Attempt the remapping
        try {
            getProteinRemapper().remapProtein(proteinToEnrich);
        } catch (BridgeFailedException e) {
            throw new EnricherException(e);
        }

        //
        if(proteinToEnrich.getUniprotkb() == null){
            if(getProteinEnricherListener() != null)
                getProteinEnricherListener().onEnrichmentComplete(proteinToEnrich , EnrichmentStatus.FAILED ,
                    "Attempted to remap because "+remapCause+" "+
                    "Was unable to find a mapping.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Attempts to remap a dead protein and prepare it for enrichment.
     * This may include marking the previous AC as being dead and archiving the old AC.
     * The actual remapping can be handled by the method 'remapProtein'
     *
     * @param proteinToEnrich
     * @return  True if a remapping was found, and false if it could not be found.
     */
    protected abstract boolean remapDeadProtein(Protein proteinToEnrich) throws EnricherException;

    /**
     * Strategy for the protein enrichment.
     * This method can be overwritten to change how the protein is enriched.
     * @param proteinToEnrich   The protein to be enriched.
     */
    protected abstract void processProtein(Protein proteinToEnrich) ;

    /**
     * Sets the protein fetcher to be used for enrichment.
     * If the fetcher is null, an illegal state exception will be thrown at the the next enrichment.
     * @param fetcher   The fetcher to be used to gather data for enrichment
     */
    public void setProteinFetcher(ProteinFetcher fetcher) {
        this.fetcher = fetcher;
    }


    /**
    * The fetcher to be used for used to collect data.
    * @return  The fetcher which is currently being used for fetching.
    */
    public ProteinFetcher getProteinFetcher() {
        return fetcher;
    }

    /**
     * The proteinEnricherListener to be used.
     * It will be fired at all points where a change is made to the protein
     * @param listener  The listener to use. Can be null.
     */
    public void setProteinEnricherListener(ProteinEnricherListener listener) {
        this.listener = listener;
    }
    /**
     * The listener which is fired when changes are made to the proteinToEnrich.
     * @return  The current listener. May be null.
     */
    public ProteinEnricherListener getProteinEnricherListener() {
        return listener;
    }

    /**
     * The protein mapper to be used when a protein doesn't have a uniprot id or the uniprotID is dead.
     * @param proteinRemapper   The remapper to use.
     */
    public void setProteinRemapper(ProteinRemapper proteinRemapper){
        this.proteinRemapper = proteinRemapper;
    }
    /**
     * The protein remapper has no default and can be left null
     * @return  The current remapper.
     */
    public ProteinRemapper getProteinRemapper(){
        return proteinRemapper;
    }

    /**
     * The organism enricher which will be used to collect data about the organisms.
     * @param organismEnricher  The organism enricher to be used.
     */
    public void setOrganismEnricher(OrganismEnricher organismEnricher) {
        this.organismEnricher = organismEnricher;
    }
    /**
     * The Enricher to use on the protein's organism.
     * @return  The current organism enricher.
     */
    public OrganismEnricher getOrganismEnricher(){
        return organismEnricher;
    }
}
