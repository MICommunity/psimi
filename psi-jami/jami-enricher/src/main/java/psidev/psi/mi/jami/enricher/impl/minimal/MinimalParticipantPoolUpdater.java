package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.CompositeInteractorEnricher;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ParticipantPool;

/**
 * A basic minimal updater for Participant pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalParticipantPoolUpdater<P extends ParticipantPool, F extends Feature> extends MinimalParticipantPoolEnricher<P,F> {

    private MinimalParticipantEnricher<P,F> minimalUpdater;

    public MinimalParticipantPoolUpdater(){
        super();
        this.minimalUpdater = new MinimalParticipantUpdater<P, F>();
    }

    protected MinimalParticipantPoolUpdater(MinimalParticipantEnricher<P, F> minimalUpdater){
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
    public void processInteractor(P objectToEnrich, P objectSource) throws EnricherException {
        this.minimalUpdater.processInteractor(objectToEnrich, objectSource);
    }

    @Override
    public void processFeatures(P objectToEnrich, P objectSource) throws EnricherException {
        this.minimalUpdater.processFeatures(objectToEnrich, objectSource);
    }

    @Override
    public void processBiologicalRole(P objectToEnrich, P objectSource) throws EnricherException {
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
