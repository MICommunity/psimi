package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.EntityPool;
import psidev.psi.mi.jami.model.Feature;

/**
 * A basic minimal updater for entity pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalEntityPoolUpdater<P extends EntityPool, F extends Feature> extends MinimalEntityPoolEnricher<P,F>{

    private MinimalParticipantEnricher<P,F> minimalUpdater;

    public MinimalEntityPoolUpdater(){
        super();
        this.minimalUpdater = new MinimalParticipantUpdater<P, F>();
    }

    protected MinimalEntityPoolUpdater(MinimalParticipantEnricher<P,F> minimalUpdater){
        super();
        if (minimalUpdater == null){
            throw new IllegalArgumentException("The minimal updater is required and cannot be null.");
        }
        this.minimalUpdater = minimalUpdater;
    }


    protected boolean removeEntitiesFromPool(){
        return true;
    }

    @Override
    protected void processInteractor(P objectToEnrich, P objectSource) throws EnricherException {
        this.minimalUpdater.processInteractor(objectToEnrich, objectSource);
    }

    @Override
    protected void processFeatures(P objectToEnrich, P objectSource) throws EnricherException {
        this.minimalUpdater.processFeatures(objectToEnrich, objectSource);
    }

    @Override
    protected void processBiologicalRole(P objectToEnrich, P objectSource) throws EnricherException {
        this.minimalUpdater.processBiologicalRole(objectToEnrich, objectSource);
    }

    @Override
    protected void processAliases(P objectToEnrich, P objectSource) {
        this.minimalUpdater.processAliases(objectToEnrich, objectSource);
    }

    @Override
    public void setParticipantListener(ParticipantEnricherListener listener) {
        this.minimalUpdater.setParticipantListener(listener);
    }

    @Override
    public ParticipantEnricherListener getParticipantEnricherListener() {
        return this.minimalUpdater.getParticipantEnricherListener();
    }

    @Override
    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvTermEnricher) {
        this.minimalUpdater.setCvTermEnricher(cvTermEnricher);
    }

    @Override
    public CvTermEnricher<CvTerm> getCvTermEnricher() {
        return this.minimalUpdater.getCvTermEnricher();
    }

    @Override
    public void setFeatureEnricher(FeatureEnricher<F> featureEnricher) {
        this.minimalUpdater.setFeatureEnricher(featureEnricher);
    }

    @Override
    public FeatureEnricher<F> getFeatureEnricher() {
        return this.minimalUpdater.getFeatureEnricher();
    }

    @Override
    public void setInteractorEnricher(CompositeInteractorEnricher interactorEnricher) {
        this.minimalUpdater.setInteractorEnricher(interactorEnricher);
    }

    @Override
    public CompositeInteractorEnricher getInteractorEnricher() {
        return this.minimalUpdater.getInteractorEnricher();
    }

    protected MinimalParticipantEnricher<P, F> getMinimalUpdater() {
        return minimalUpdater;
    }
}
