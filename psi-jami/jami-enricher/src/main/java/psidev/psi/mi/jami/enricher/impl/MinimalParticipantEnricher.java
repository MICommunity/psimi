package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * The participant enricher is an enricher which can enrich either single participant or a collection.
 * The participant enricher has subEnrichers and no fetchers.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/06/13
 */
public class MinimalParticipantEnricher<P extends Entity , F extends Feature>
        implements ParticipantEnricher<P , F>  {

    private ParticipantEnricherListener<P> listener;
    private CompositeInteractorEnricher interactorEnricher;
    private CvTermEnricher<CvTerm> cvTermEnricher;
    private FeatureEnricher<F> featureEnricher;

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
            // process aliases
            processAliases(objectToEnrich, objectSource);
            // == CvTerm BioRole =========================================================
            processBiologicalRole(objectToEnrich, objectSource);

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

        // == CvTerm BioRole =========================================================
        processBiologicalRole(participantToEnrich);

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

    protected void processOtherProperties(P objectToEnrich, P objectSource) throws EnricherException {
        // nothing to do here
        processOtherProperties(objectToEnrich);
    }

    protected void processInteractor(P objectToEnrich, P objectSource) throws EnricherException {
        // nothing to do here
        processInteractor(objectToEnrich);
    }

    protected void processFeatures(P objectToEnrich, P objectSource) throws EnricherException {
        EnricherUtils.mergeFeatures(objectToEnrich, objectToEnrich.getFeatures(), objectSource.getFeatures(), false, getParticipantEnricherListener(),
                getFeatureEnricher());
        processFeatures(objectToEnrich);
    }

    protected void processBiologicalRole(P objectToEnrich, P objectSource) throws EnricherException {
        // nothing to do here
        processBiologicalRole(objectToEnrich);
    }

    protected void processAliases(P objectToEnrich, P objectSource) {
        EnricherUtils.mergeAliases(objectToEnrich, objectToEnrich.getAliases(), objectSource.getAliases(), false, getParticipantEnricherListener());
    }

    protected void processOtherProperties(P participantToEnrich) throws EnricherException {
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

    protected void processBiologicalRole(P participantToEnrich) throws EnricherException {
        if(getCvTermEnricher() != null && participantToEnrich.getBiologicalRole() != null)
            getCvTermEnricher().enrich(participantToEnrich.getBiologicalRole());
    }

    /**
     * Sets the listener for Participant events. If null, events will not be reported.
     * @param listener  The listener to use. Can be null.
     */
    public void setParticipantListener(ParticipantEnricherListener listener) {
        this.listener = listener;
    }

    /**
     * The current listener that participant changes are reported to.
     * If null, events are not being reported.
     * @return  TThe current listener. Can be null.
     */
    public ParticipantEnricherListener getParticipantEnricherListener() {
        return listener;
    }

    /**
     * Sets the enricher for CvTerms. If null, cvTerms are not being enriched.
     * @param cvTermEnricher    The new enricher for CvTerms
     */
    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    /**
     * The current CvTerm enricher, If null, CvTerms will not be enriched.
     * @return  The new enricher for CvTerms. Can be null.
     */
    public CvTermEnricher<CvTerm> getCvTermEnricher(){
        return cvTermEnricher;
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
