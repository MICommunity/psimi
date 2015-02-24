package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.EntityEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.CompositeInteractorEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Feature;

import java.util.Collection;

/**
 * The entity enricher is an enricher which can enrich either single participant or a collection.
 * The entity enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public class MinimalEntityEnricher<P extends Entity, F extends Feature>
        implements EntityEnricher<P,F> {

    private CompositeInteractorEnricher interactorEnricher;
    private FeatureEnricher<F> featureEnricher;
    private EntityEnricherListener<P> listener;

    public void enrich(Collection<P> participantsToEnrich) throws EnricherException {
        if(participantsToEnrich == null) throw new IllegalArgumentException("Cannot enrich a null collection of participants.");

        for(P participant : participantsToEnrich){
            enrich(participant);
        }
    }

    public void enrich(P objectToEnrich, P objectSource) throws EnricherException {
        if (objectSource == null){
            enrich(objectToEnrich);
        }
        else{
            // == Prepare Features =====================================
            // == Enrich Features =========================================================
            processFeatures(objectToEnrich, objectSource);

            // == Enrich Interactor ============================
            processInteractor(objectToEnrich, objectSource);

            // enrich other properties
            processOtherProperties(objectToEnrich, objectSource);

            if( getParticipantEnricherListener() != null )
                getParticipantEnricherListener().onEnrichmentComplete(objectToEnrich , EnrichmentStatus.SUCCESS , null);
        }
    }

    public void enrich(P participantToEnrich) throws EnricherException{

        if(participantToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null participant.");

        // == Prepare Features =====================================
        if( getFeatureEnricher() != null )
            getFeatureEnricher().setFeaturesWithRangesToUpdate((Collection<F>)participantToEnrich.getFeatures());
        // == Enrich Features =========================================================
        processFeatures(participantToEnrich);

        // == Enrich Interactor ============================
        processInteractor(participantToEnrich);

        // enrich other properties
        processOtherProperties(participantToEnrich);

        if( getParticipantEnricherListener() != null )
            getParticipantEnricherListener().onEnrichmentComplete(participantToEnrich , EnrichmentStatus.SUCCESS , null);
    }

    public void processOtherProperties(P objectToEnrich, P objectSource) throws EnricherException {
        // nothing to do here
        processOtherProperties(objectToEnrich);
    }

    public void processInteractor(P objectToEnrich, P objectSource) throws EnricherException {
        // nothing to do here
        processInteractor(objectToEnrich);
    }

    public void processFeatures(P objectToEnrich, P objectSource) throws EnricherException {
        EnricherUtils.mergeFeatures(objectToEnrich, objectToEnrich.getFeatures(), objectSource.getFeatures(), false, getParticipantEnricherListener(),
                getFeatureEnricher());
        processFeatures(objectToEnrich);
    }

    public void processOtherProperties(P participantToEnrich) throws EnricherException {
        // do nothing
    }

    protected void processFeatures(P participantToEnrich) throws EnricherException {
        if( getFeatureEnricher() != null ) {
            getFeatureEnricher().setFeaturesWithRangesToUpdate((Collection<F>)participantToEnrich.getFeatures());
            getFeatureEnricher().enrich(participantToEnrich.getFeatures());
        }

    }

    protected void processInteractor(P participantToEnrich) throws EnricherException {
        // we can enrich interactors
        if (getInteractorEnricher() != null){
            getInteractorEnricher().enrich(participantToEnrich.getInteractor());
        }
    }

    /**
     * Sets the listener for Participant events. If null, events will not be reported.
     * @param listener  The listener to use. Can be null.
     */
    public void setParticipantEnricherListener(EntityEnricherListener listener) {
        this.listener = listener;
    }

    /**
     * The current listener that participant changes are reported to.
     * If null, events are not being reported.
     * @return  TThe current listener. Can be null.
     */
    public EntityEnricherListener getParticipantEnricherListener() {
        return listener;
    }

    /**
     * Will attempt to add the featureEnricher as a proteinListener if this is valid.
     * If the proteinEnricher already has a listener, this will be preserved using a listener manager.
     * @param featureEnricher   The enricher to use for features. Can be null.
     */
    public void setFeatureEnricher(FeatureEnricher<F> featureEnricher){
        this.featureEnricher = featureEnricher;
    }

    /**
     * The current enricher used for features. If null, features are not currently being enriched.
     * @return  The current enricher. May be null.
     */
    public FeatureEnricher<F> getFeatureEnricher(){
        return featureEnricher;
    }

    public void setInteractorEnricher(CompositeInteractorEnricher interactorEnricher) {
        this.interactorEnricher = interactorEnricher;
    }

    public CompositeInteractorEnricher getInteractorEnricher() {
        return this.interactorEnricher;
    }


}
