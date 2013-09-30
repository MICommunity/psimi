package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.BioactiveEntityEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.InteractorChangeListener;
import psidev.psi.mi.jami.model.BioactiveEntity;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Provides minimum enrichment of the bioactiveEntity.
 * As an enricher, no values from the provided bioactiveEntity to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MinimalBioactiveEntityEnricher extends AbstractInteractorEnricher<BioactiveEntity> implements BioactiveEntityEnricher {

    private BioactiveEntityFetcher fetcher;
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
     * Returns the current fetcher which is being used to collect information about entities for enrichment.
     * @return  The current fetcher.
     */
    public BioactiveEntityFetcher getBioactiveEntityFetcher() {
        return fetcher;
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

    @Override
    protected InteractorChangeListener<BioactiveEntity> getListener() {
        return listener;
    }

    @Override
    protected BioactiveEntity fetchEnrichedVersionFrom(BioactiveEntity objectToEnrich) throws EnricherException {
        BioactiveEntity fetchedBioactiveEntity = null;

        if(objectToEnrich.getChebi() != null){
            fetchedBioactiveEntity = fetchEntity(objectToEnrich.getChebi());
        }
        return fetchedBioactiveEntity;
    }

    @Override
    protected void onEnrichedVersionNotFound(BioactiveEntity objectToEnrich) throws EnricherException {
        getBioactiveEntityEnricherListener().onEnrichmentComplete(
                objectToEnrich , EnrichmentStatus.FAILED ,
                "Could not fetch a bioactive entity with the provided CHEBI identifier.");
    }

    @Override
    protected boolean isFullEnrichment() {
        return false;
    }

    @Override
    protected void onCompletedEnrichment(BioactiveEntity objectToEnrich) {
        if(getBioactiveEntityEnricherListener() != null)
            getBioactiveEntityEnricherListener().onEnrichmentComplete(
                    objectToEnrich , EnrichmentStatus.SUCCESS , "The bioactive entity has been successfully enriched.");
    }

    @Override
    protected void onInteractorCheckFailure(BioactiveEntity objectToEnrich, BioactiveEntity fetchedObject) {
        if(getBioactiveEntityEnricherListener() != null)
            getBioactiveEntityEnricherListener().onEnrichmentComplete(
                    objectToEnrich , EnrichmentStatus.FAILED , "Cannot enrich the bioactive entity because the interactor type is not a bioactive entity type.");
    }

    @Override
    protected boolean canEnrichInteractor(BioactiveEntity entityToEnrich, BioactiveEntity fetchedEntity) {
        // if the interactor type is not a valid bioactive entity interactor type, we cannot enrich
        if (entityToEnrich.getInteractorType() != null &&
                !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), BioactiveEntity.POLYSACCHARIDE_MI, BioactiveEntity.POLYSACCHARIDE)
                && !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), BioactiveEntity.BIOACTIVE_ENTITY_MI, BioactiveEntity.BIOACTIVE_ENTITY)
                && !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), BioactiveEntity.SMALL_MOLECULE_MI, BioactiveEntity.SMALL_MOLECULE)
                && !CvTermUtils.isCvTerm(entityToEnrich.getInteractorType(), Interactor.UNKNOWN_INTERACTOR_MI, Interactor.UNKNOWN_INTERACTOR)){
            return false;
        }
        return true;
    }

    private BioactiveEntity fetchEntity(String id) throws EnricherException {
        try {
            return getBioactiveEntityFetcher().fetchByIdentifier(id);
        } catch (BridgeFailedException e) {
            int index = 1;
            while(index < getRetryCount()){
                try {
                    return getBioactiveEntityFetcher().fetchByIdentifier(id);
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Re-tried "+ getRetryCount() +" times to fetch the Publication but cannot connect to the fetcher.", e);
        }
    }
}
