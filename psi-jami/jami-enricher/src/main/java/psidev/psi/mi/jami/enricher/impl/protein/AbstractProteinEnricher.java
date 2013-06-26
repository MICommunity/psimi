package psidev.psi.mi.jami.enricher.impl.protein;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapper;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/06/13
 * Time: 15:34
 */
public abstract class AbstractProteinEnricher
implements ProteinEnricher {

    protected ProteinFetcher fetcher = null;
    protected ProteinEnricherListener listener = null;

    protected OrganismEnricher organismEnricher = null;
    protected ProteinRemapper proteinRemapper = null;

    protected Protein proteinFetched = null;

    /**
     * Takes a protein, gathers information about it and will try to return a more complete form.
     *
     * @param proteinToEnrich   the Protein which is being enriched
     * @return  a boolean denoting whether the enrichment was successful

     */
    public boolean enrichProtein(Protein proteinToEnrich) throws EnricherException {


        // In your enricher, if you have several entries,
        // try to look for the one with the same organism as the protein you try to enrich.
        // If you don't find one or several entries have the same organism,
        // fire a specific event because we want to track these proteins.

        proteinFetched = fetchProtein(proteinToEnrich);
        if(proteinFetched == null){
            return false;
        }

        if (! areNoConflicts(proteinToEnrich) ) return false;

        if (getOrganismEnricher().getFetcher() instanceof MockOrganismFetcher){
            MockOrganismFetcher organismFetcher = (MockOrganismFetcher)getOrganismEnricher().getFetcher();
            organismFetcher.clearOrganisms();
            organismFetcher.addNewOrganism(""+proteinToEnrich.getOrganism().getTaxId(), proteinFetched.getOrganism());
        }

        getOrganismEnricher().enrichOrganism(proteinToEnrich.getOrganism());

        processProtein(proteinToEnrich);

        if(listener != null) listener.onProteinEnriched(proteinToEnrich, "Success. Protein enriched.");
        return true;
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
     * @return  Whether there were any fatal conflicts which

     */
    protected boolean areNoConflicts(Protein proteinToEnrich){

        //InteractorType
        if(!proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(
                Protein.PROTEIN_MI)
                && !proteinToEnrich.getInteractorType().getMIIdentifier().equalsIgnoreCase(
                Interactor.UNKNOWN_INTERACTOR_MI)){

            if(listener != null) listener.onProteinEnriched(proteinToEnrich, "Failed. Conflict in interactorType. " +
                    "Found " + proteinToEnrich.getInteractorType().getShortName() + " " +
                    "(psi-mi id:" + proteinToEnrich.getInteractorType().getMIIdentifier() + ").");
            return false;
        }

        //Organism
        if(proteinFetched.getOrganism() == null) throw new IllegalArgumentException(
                "The enriched protein has a null organism.");
        if(proteinToEnrich.getOrganism() == null) proteinToEnrich.setOrganism(new DefaultOrganism(-3));

        if(proteinToEnrich.getOrganism().getTaxId() != -3
                && ! OrganismTaxIdComparator.areEquals(proteinToEnrich.getOrganism(), proteinFetched.getOrganism())){

            if(listener != null) listener.onProteinEnriched(proteinToEnrich, "Failed. Conflict in organism. " +
                    "Found taxid " + proteinToEnrich.getOrganism().getTaxId() + " " +
                    "but was enriching taxid " + proteinFetched.getOrganism().getTaxId() + ".");
            return false;
        }

        return true;
    }


    /**
     * Fetches the protein entry which matches the proteinToEnrich.
     * Uses the provided proteinFetcher.
     *
     * @param proteinToEnrich
     * @return
     */
    protected Protein fetchProtein(Protein proteinToEnrich) throws EnricherException {

        if(getFetcher() == null) throw new IllegalStateException("ProteinFetcher has not been provided.");
        if(proteinToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null protein.");

        if(proteinToEnrich.getUniprotkb() == null) {
            if(getProteinRemapper() == null){
                if(! remapProtein(proteinToEnrich , "proteinToEnrich has no UniprotKB ID.")){
                    return null;
                }
            }
        }

        Collection<Protein> proteinsEnriched;
        try {
            proteinsEnriched = fetcher.getProteinsByIdentifier(proteinToEnrich.getUniprotkb());
        } catch (BridgeFailedException e) {
            throw new EnricherException(
                    "Could not enrich protein with identifier "+proteinToEnrich.getUniprotkb() , e);
        }

        // If the Protein is dead
        if(proteinsEnriched == null
                || proteinsEnriched.isEmpty()){
            // If the protein cannot be remapped - exit
            if( ! remapDeadProtein(proteinToEnrich)){
                return null;
            }else{
                try {
                    proteinsEnriched = fetcher.getProteinsByIdentifier(proteinToEnrich.getUniprotkb());
                } catch (BridgeFailedException e) {
                    throw new EnricherException(
                            "Could not enrich protein with identifier "+proteinToEnrich.getUniprotkb() , e);
                }
                // If the remapping can not be fetched
                if(proteinsEnriched == null
                        || proteinsEnriched.isEmpty()){
                    if(listener != null) listener.onProteinEnriched(proteinToEnrich,
                            "Failed. Protein is dead. " +
                            "Was able to remap but the remapped entry also appears to be dead.");
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
        if(proteinToEnrich.getOrganism() == null
                || proteinToEnrich.getOrganism().getTaxId() == -3){
            if(listener != null) listener.onProteinEnriched(proteinToEnrich,
                    "Failed. Protein is demerged. Found " + proteinsEnriched.size() + " entries. " +
                    "Cannot choose as proteinToEnrich has no organism.");
            return null;
        }

        Protein proteinFetched = null;

        for(Protein protein : proteinsEnriched){
            if(protein.getOrganism().getTaxId() == proteinToEnrich.getOrganism().getTaxId()){
                if(proteinFetched == null) proteinFetched = protein;
                else{
                    if(listener != null) listener.onProteinEnriched(proteinToEnrich,
                            "Failed. Protein is demerged. Found " + proteinsEnriched.size() + " entries. " +
                            "Cannot choose as multiple entries match organism in proteinToEnrich.");
                    return null;
                }
            }
        }

        if(proteinFetched == null){
            if(listener != null) listener.onProteinEnriched(proteinToEnrich,
                    "Failed. Protein is demerged. Found " + proteinsEnriched.size() + " entries. " +
                    "Cannot choose as no entries match the organism in proteinToEnrich.");
            return null;
        }

        //if(log.isInfoEnabled()) log.info("Chose a demerged protein from a choice of "+proteinsEnriched.size());
        return proteinFetched;
    }

    /**
     * Attempts to remap the protein using the provided proteinRemapper.
     * If one has not been included, this method returns false and reports a failure to the listener.
     * If after remapping, the protein still has no entry, this entry returns false.
     *
     * @param proteinToEnrich
     * @param remapCause
     * @return
     */
    protected boolean remapProtein(Protein proteinToEnrich, String remapCause) throws EnricherException {

        if( getProteinRemapper() == null ){
            if(listener != null) listener.onProteinEnriched(proteinToEnrich , "Failed. " +
                    "Attempted to remap because "+remapCause+" "+
                    "Was unable to complete remap as service was missing.");
            return false;
        }

        try {
            getProteinRemapper().remapProtein(proteinToEnrich);
        } catch (BridgeFailedException e) {
            throw new EnricherException(e);
        }

        if(proteinToEnrich.getUniprotkb() == null){
            if(listener != null) listener.onProteinEnriched(proteinToEnrich , "Failed. " +
                    "Attempted to remap because "+remapCause+" "+
                    "Was unable to find a mapping.");
            return false;
        } else {
            if(listener != null) {
                listener.onProteinRemapped(proteinToEnrich , null);
                listener.onUniprotKbUpdate(proteinToEnrich , null);
            }
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
     * How to process the protein if an enriched form can be found.
     *
     * @param proteinToEnrich
     */
    protected abstract void processProtein(Protein proteinToEnrich) ;

    /**
     * Sets the protein fetching service which will be used to fetch the enriched form
     * @param fetcher
     */
    public void setFetcher(ProteinFetcher fetcher) {
        this.fetcher = fetcher;
    }

    /**
     * Gets the protein fetching service which will be used to fetch the enriched form
     * @return
     */
    public ProteinFetcher getFetcher() {
        return fetcher;
    }

    public void setProteinEnricherListener(ProteinEnricherListener proteinEnricherListener) {
        this.listener = proteinEnricherListener;
    }

    public ProteinEnricherListener getProteinEnricherListener() {
        return listener;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setProteinRemapper(ProteinRemapper proteinRemapper){
        this.proteinRemapper = proteinRemapper;
    }
    public ProteinRemapper getProteinRemapper(){
        return proteinRemapper;
    }

    public void setOrganismEnricher(OrganismEnricher organismEnricher) {
        this.organismEnricher = organismEnricher;
    }

    public abstract OrganismEnricher getOrganismEnricher();
}
