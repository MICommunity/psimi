package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.BioactiveEntityEnricher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.Collection;

/**
 * Provides minimum enrichment of the bioactiveEntity.
 * As an enricher, no values from the provided bioactiveEntity to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MinimalBioactiveEntityEnricher implements BioactiveEntityEnricher {

    private int retryCount = 5;
    private BioactiveEntityFetcher fetcher;
    private CvTermEnricher cvTermEnricher = null;
    private OrganismEnricher organismEnricher = null;
    private BioactiveEntityEnricherListener listener = null;

    /**
     * The only constructor, fulfilling the requirement of a bioactiveEntity fetcher.
     * If the bioactiveEntity fetcher is null, an illegal state exception will be thrown at the next enrichment.
     * @param fetcher   The fetcher used to collect bioactiveEntity records.
     */
    public MinimalBioactiveEntityEnricher(BioactiveEntityFetcher fetcher){
        if (fetcher == null){
            throw new IllegalArgumentException("The fetcher is required and cannot be null");
        }
        this.fetcher = fetcher;
    }

    /**
     * Enriches a collection of bioactive entities.
     * @param bioactiveEntitiesToEnrich       The entities to be enriched
     * @throws psidev.psi.mi.jami.enricher.exception.EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichBioactiveEntities(Collection<BioactiveEntity> bioactiveEntitiesToEnrich) throws EnricherException {
        for(BioactiveEntity bioactiveEntityToEnrich : bioactiveEntitiesToEnrich){
            enrichBioactiveEntity(bioactiveEntityToEnrich);
        }
    }

    /**
     * Enriches a single bioactive entity.
     * @param bioactiveEntityToEnrich   The entity to be enriched.
     * @throws EnricherException        Thrown if problems are encountered in the fetcher
     */
    public void enrichBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich) throws EnricherException {
        if(bioactiveEntityToEnrich == null)
            throw new IllegalArgumentException("Can not enrich null Bioactive Entity");

        BioactiveEntity bioactiveEntityFetched = fetchBioactiveEntity(bioactiveEntityToEnrich);

        if(bioactiveEntityFetched == null){
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onEnrichmentComplete(
                        bioactiveEntityToEnrich , EnrichmentStatus.FAILED ,
                        "Could not fetch a bioactiveEntity with the provided CHEBI identifier.");
            return;
        }

        // if the interactor type is not a valid bioactive entity interactor type, we cannot enrich
        if (bioactiveEntityToEnrich.getInteractorType() == null ||
                !CvTermUtils.isCvTerm(bioactiveEntityToEnrich.getInteractorType(), Interactor.UNKNOWN_INTERACTOR_MI, Interactor.UNKNOWN_INTERACTOR)){
            bioactiveEntityToEnrich.setInteractorType(bioactiveEntityFetched.getInteractorType());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onAddedInteractorType(bioactiveEntityToEnrich);
        }
        else if (!CvTermUtils.isCvTerm(bioactiveEntityToEnrich.getInteractorType(), BioactiveEntity.POLYSACCHARIDE_MI, BioactiveEntity.POLYSACCHARIDE)
                && !CvTermUtils.isCvTerm(bioactiveEntityToEnrich.getInteractorType(), BioactiveEntity.BIOACTIVE_ENTITY_MI, BioactiveEntity.BIOACTIVE_ENTITY)
                && !CvTermUtils.isCvTerm(bioactiveEntityToEnrich.getInteractorType(), BioactiveEntity.SMALL_MOLECULE_MI, BioactiveEntity.SMALL_MOLECULE)){
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onEnrichmentComplete(
                        bioactiveEntityToEnrich , EnrichmentStatus.FAILED ,
                        "The interactor type of this bioactive entity is not recognized as a valid bioactive entity type. It accepts null interactor type and non null polysaccharyde, small molecule, bioactive entity or unknown participant type.");
            return;
        }

        processBioactiveEntity(bioactiveEntityToEnrich, bioactiveEntityFetched);

        if(getBioactiveEntityEnricherListener() != null)
            getBioactiveEntityEnricherListener().onEnrichmentComplete(
                    bioactiveEntityToEnrich , EnrichmentStatus.SUCCESS , "The bioactive entity has been successfully enriched.");
    }

    /**
     * Returns the current fetcher which is being used to collect information about entities for enrichment.
     * @return  The current fetcher.
     */
    public BioactiveEntityFetcher getBioactiveEntityFetcher() {
        return fetcher;
    }


    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }

    /**
     * Sets the listener to use when the bioactiveEntity has been changed
     * @param listener  The new listener. Can be null.
     */
    public void setBioactiveEntityEnricherListener(BioactiveEntityEnricherListener listener) {
        this.listener = listener;
    }

    /**
     * The current listener of changes to the bioactiveEntities.
     * @return  The current listener. Can be null.
     */
    public BioactiveEntityEnricherListener getBioactiveEntityEnricherListener(){
        return listener;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public OrganismEnricher getOrganismEnricher() {
        return organismEnricher;
    }

    public void setOrganismEnricher(OrganismEnricher organismEnricher) {
        this.organismEnricher = organismEnricher;
    }

    /**
     * Strategy for the BioactiveEntity enrichment.
     * This method can be overwritten to change how the BioactiveEntity is enriched.
     * @param bioactiveEntityToEnrich   The BioactiveEntity to be enriched.
     */
    protected void processBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) throws EnricherException {

        // SHORT NAME is never null

        // Interactor type
        processInteractorType(bioactiveEntityToEnrich);

        // Organism
        processOrganism(bioactiveEntityToEnrich, fetched);

        // FULL NAME
        processFullName(bioactiveEntityToEnrich, fetched);

        // IDENTIFIERS
        processIdentifiers(bioactiveEntityToEnrich, fetched);

        //ALIASES
        processAliases(bioactiveEntityToEnrich, fetched);
    }

    protected void processAliases(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) {
        EnricherUtils.mergeAliases(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getAliases(), fetched.getAliases(), false,
                getBioactiveEntityEnricherListener());
    }

    protected void processIdentifiers(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) {
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getIdentifiers(), fetched.getIdentifiers(), false, true,
                getBioactiveEntityEnricherListener(), getBioactiveEntityEnricherListener());
    }

    protected void processFullName(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) {
        if(bioactiveEntityToEnrich.getFullName() == null
                && fetched.getFullName() != null){
            bioactiveEntityToEnrich.setFullName(fetched.getFullName());
            if(getBioactiveEntityEnricherListener() != null)
                getBioactiveEntityEnricherListener().onFullNameUpdate(bioactiveEntityToEnrich , null);
        }
    }

    protected void processInteractorType(BioactiveEntity bioactiveEntityToEnrich) throws EnricherException {
        if (cvTermEnricher != null){
            cvTermEnricher.enrichCvTerm(bioactiveEntityToEnrich.getInteractorType());
        }
    }

    protected void processOrganism(BioactiveEntity bioactiveEntityToEnrich, BioactiveEntity fetched) throws EnricherException {
        if (bioactiveEntityToEnrich.getOrganism() == null && fetched.getOrganism() != null){
            bioactiveEntityToEnrich.setOrganism(fetched.getOrganism());
            if (getBioactiveEntityEnricherListener() != null){
                getBioactiveEntityEnricherListener().onAddedOrganism(bioactiveEntityToEnrich);
            }
        }

        if (organismEnricher != null && bioactiveEntityToEnrich.getOrganism() != null){
            organismEnricher.enrichOrganism(bioactiveEntityToEnrich.getOrganism());
        }
    }

    private BioactiveEntity fetchBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich) throws EnricherException {
        if(getBioactiveEntityFetcher() == null)
            throw new IllegalStateException("Can not fetch with null fetcher");

        BioactiveEntity fetchedBioactiveEntity = null;

        if(bioactiveEntityToEnrich.getChebi() != null){
            fetchedBioactiveEntity = fetchEntity(bioactiveEntityToEnrich.getChebi());
        }
        return fetchedBioactiveEntity;
    }

    private BioactiveEntity fetchEntity(String id) throws EnricherException {
        try {
            return getBioactiveEntityFetcher().fetchBioactiveEntityByIdentifier(id);
        } catch (BridgeFailedException e) {
            int index = 1;
            while(index < retryCount){
                try {
                    return getBioactiveEntityFetcher().fetchBioactiveEntityByIdentifier(id);
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Re-tried "+ retryCount +" times to fetch the Publication but cannot connect to the fetcher.", e);
        }
    }
}
