package psidev.psi.mi.jami.enricher.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.bridges.mapper.ProteinMapper;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.comparator.organism.OrganismTaxIdComparator;

import java.util.Collection;
import java.util.Collections;

/**
 * Enriches a protein to the minimum level. As an enricher, no data will be overwritten in the protein being enriched.
 * This covers full name, primary AC, checksums, identifiers and aliases.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 14/05/13
 */
public class MinimalProteinEnricher extends AbstractInteractorEnricher<Protein> implements ProteinEnricher {

    private ProteinFetcher fetcher;
    private ProteinEnricherListener listener = null;
    private ProteinMapper proteinMapper = null;
    private static final Logger log = LoggerFactory.getLogger(MinimalProteinEnricher.class.getName());

    public static final String UNIPROT_REMOVED_QUALIFIER = "uniprot-removed-ac";
    public static final String CAUTION_MESSAGE = "This sequence has been withdrawn from Uniprot.";
    /**
     * The only constructor, fulfilling the requirement of a protein fetcher.
     * If the protein fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param fetcher   The fetcher used to collect protein records.
     */
    public MinimalProteinEnricher(ProteinFetcher fetcher){
        if (fetcher == null){
            throw new IllegalArgumentException("The fetcher is required and cannot be null");
        }
        this.fetcher = fetcher;
    }

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
     * @param proteinMapper   The remapper to use.
     */
    public void setProteinMapper(ProteinMapper proteinMapper){
        this.proteinMapper = proteinMapper;
    }
    /**
     * The protein remapper has no default and can be left null
     * @return  The current remapper.
     */
    public ProteinMapper getProteinMapper(){
        return proteinMapper;
    }

    @Override
    public InteractorEnricherListener<Protein> getListener() {
        return listener;
    }

    @Override
    public void setListener(InteractorEnricherListener<Protein> listener) {
        if (listener instanceof ProteinEnricherListener){
            this.listener = (ProteinEnricherListener)listener;
        }
        else if (listener == null){
            this.listener = null;
        }
        else{
            throw new IllegalArgumentException("A ProteinEnricherListener is expected and we tried to set a " + listener.getClass().getCanonicalName() + " instead");
        }
    }

    @Override
    protected Protein fetchEnrichedVersionFrom(Protein proteinToEnrich) throws EnricherException {
        // If there is no uniprotID - try and remap.
        if(proteinToEnrich.getUniprotkb() == null) {
            if( log.isTraceEnabled() ) log.trace("Remapping protein without uniprotkb");
            if(!remapProtein(proteinToEnrich)){
                return null;
            }
        }

        // fetch proteins
        Collection<Protein> proteinsEnriched = fetchProteins(proteinToEnrich.getUniprotkb());
        // If the Protein is dead
        if(proteinsEnriched.isEmpty()){
            proteinsEnriched = processAndRemapDeadProtein(proteinToEnrich);
            if(proteinsEnriched.isEmpty()){
                return null;
            }
        }

        // 1 protein, use it
        if(proteinsEnriched.size() == 1) {
            return proteinsEnriched.iterator().next();
        }
        else{
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
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onEnrichmentError(proteinToEnrich,  "The protein does not have a valid organism and could match "+proteinsEnriched.size()+" uniprot entries.", new EnricherException("Cannot enrich a demerged protein"));
                return null;
            }

            Protein proteinFetched = null;

            for(Protein protein : proteinsEnriched){
                if(OrganismTaxIdComparator.areEquals(protein.getOrganism(), proteinToEnrich.getOrganism())){
                    if(proteinFetched == null) proteinFetched = protein;
                    else{
                        // Multiple proteins share this organism - impossible to choose
                        if(getProteinEnricherListener() != null)
                            getProteinEnricherListener().onEnrichmentError(proteinToEnrich,  "The protein could match "+proteinsEnriched.size()+" uniprot entries and several have the same organism taxid.", new EnricherException("Cannot enrich a demerged protein"));
                        return null;
                    }
                }
            }

            if(proteinFetched == null){
                // No proteins share this organism - impossible to choose
                if(getProteinEnricherListener() != null)
                    getProteinEnricherListener().onEnrichmentError(proteinToEnrich,  "The protein could match "+proteinsEnriched.size()+" uniprot entries and non of them have the same organism taxid.", new EnricherException("Cannot enrich a demerged protein"));
                return null;
            }

            return proteinFetched;
        }
    }

    @Override
    protected void onEnrichedVersionNotFound(Protein objectToEnrich) throws EnricherException {
        getProteinEnricherListener().onEnrichmentComplete(
                objectToEnrich , EnrichmentStatus.FAILED ,
                "Could not fetch a protein with the provided identifier/sequence.");
    }

    @Override
    protected boolean isFullEnrichment() {
        return false;
    }

    @Override
    protected void onCompletedEnrichment(Protein objectToEnrich) {
        if(getProteinEnricherListener() != null)
            getProteinEnricherListener().onEnrichmentComplete(
                    objectToEnrich , EnrichmentStatus.SUCCESS , "The protein has been successfully enriched.");
    }

    @Override
    protected void onInteractorCheckFailure(Protein objectToEnrich, Protein fetchedObject) {
        if(getProteinEnricherListener() != null)
            getProteinEnricherListener().onEnrichmentComplete(
                    objectToEnrich , EnrichmentStatus.FAILED , "Cannot enrich the protein because the interactor type is not a protein/peptide type and/or there is a conflict with the organism.");
    }

    @Override
    protected boolean canEnrichInteractor(Protein entityToEnrich, Protein fetchedEntity) {
        // if the interactor type is not a valid protein interactor type, we cannot enrich
        if (entityToEnrich.getInteractorType() != null &&
                !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), Protein.PROTEIN_MI, Protein.PROTEIN)
                && !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), Protein.PEPTIDE_MI, Protein.PEPTIDE)
                && !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), Polymer.POLYMER_MI, Polymer.POLYMER)
                && !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), Interactor.UNKNOWN_INTERACTOR_MI, Interactor.UNKNOWN_INTERACTOR)){
            return false;
        }

        // if the organism is different, we cannot enrich
        if (entityToEnrich.getOrganism() != null && fetchedEntity.getOrganism() != null &&
                !OrganismTaxIdComparator.areEquals(entityToEnrich.getOrganism(), fetchedEntity.getOrganism())){
            return false;
        }

        return true;
    }

    protected void processDeadUniprotIdentity(Protein proteinToEnrich, Xref uniprotIdentity) {
        proteinToEnrich.getIdentifiers().remove(uniprotIdentity);
        if(getProteinEnricherListener() != null){
            getProteinEnricherListener().onRemovedIdentifier(proteinToEnrich, uniprotIdentity);
        }
        proteinToEnrich.getXrefs().add(uniprotIdentity);
        if(getProteinEnricherListener() != null){
            getProteinEnricherListener().onAddedXref(proteinToEnrich, uniprotIdentity);
        }
    }

    /**
     * Attempts to remap the protein using the provided proteinRemapper.
     * If one has not been included, this method returns false and reports a failure to the listener.
     * If after remapping, the protein still has no entry, this entry returns false.
     *
     * @param proteinToEnrich   The protein to find a remapping for.
     * @return                  Whether the remapping was successful.
     */
    protected boolean remapProtein(Protein proteinToEnrich) throws EnricherException {

        // If there is no protein remapper, the enrichment fails
        if( getProteinMapper() == null ){
            return false;
        }

        // Attempt the remapping
        try {
            getProteinMapper().map(proteinToEnrich);
        } catch (BridgeFailedException e) {
            throw new EnricherException("Impossible to remap the protein to uniprot.", e);
        }

        return proteinToEnrich.getUniprotkb() != null;
    }

    private Collection<Protein> processAndRemapDeadProtein(Protein proteinToEnrich) throws EnricherException {
        Collection<Protein> fetchedProteins = Collections.EMPTY_LIST;

        // move old uniprot identity to xrefs
        Collection<Xref> uniprotIdentities = XrefUtils.collectAllXrefsHavingDatabase(proteinToEnrich.getIdentifiers(), Xref.UNIPROTKB_MI, Xref.UNIPROTKB);
        for (Xref uniprotIdentity : uniprotIdentities){
            // we have the dead entry
            if (uniprotIdentity.getId().equals(proteinToEnrich.getUniprotkb())){
                processDeadUniprotIdentity(proteinToEnrich, uniprotIdentity);
            }
            else if(fetchedProteins.isEmpty()){
                fetchedProteins = fetchProteins(uniprotIdentity.getId());
                if (fetchedProteins.isEmpty()){
                    processDeadUniprotIdentity(proteinToEnrich, uniprotIdentity);
                }
            }
        }

        // remap successfull
        if (fetchedProteins.isEmpty() && remapProtein(proteinToEnrich)){
            fetchedProteins = fetchProteins(proteinToEnrich.getUniprotkb());
        }
        // remap unsuccessful, add caution
        else if (fetchedProteins.isEmpty()){
            boolean hasCaution = false;
            for (Annotation annot : proteinToEnrich.getAnnotations()){
                if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.CAUTION_MI, Annotation.CAUTION)){
                    if (annot.getValue() != null && CAUTION_MESSAGE.equalsIgnoreCase(annot.getValue())){
                        hasCaution = true;
                        break;
                    }
                }
            }
            if (!hasCaution){
                Annotation annot = AnnotationUtils.createCaution(CAUTION_MESSAGE);
                proteinToEnrich.getAnnotations().add(annot);
                if(getProteinEnricherListener() != null){
                    getProteinEnricherListener().onAddedAnnotation(proteinToEnrich, annot);
                }
            }

        }
        return fetchedProteins;
    }

    private Collection<Protein> fetchProteins(String uniprotkb) throws EnricherException {
        try {
            return fetcher.fetchByIdentifier(uniprotkb);
        } catch (BridgeFailedException e) {
            int index = 0;
            while(index < getRetryCount()){
                try {
                    return fetcher.fetchByIdentifier(uniprotkb);
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Retried "+getRetryCount()+" times", e);
        }
    }
}
