package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * A basic minimal enricher for experimental entity pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalExperimentalEntityPoolEnricher extends MinimalEntityPoolEnricher<ExperimentalEntityPool,FeatureEvidence>{

    private MinimalParticipantEvidenceEnricher<ExperimentalEntityPool,FeatureEvidence> delegate;

    public MinimalExperimentalEntityPoolEnricher(){
        super();
        this.delegate = new MinimalParticipantEvidenceEnricher<ExperimentalEntityPool,FeatureEvidence>();
    }

    protected MinimalExperimentalEntityPoolEnricher(MinimalParticipantEvidenceEnricher<ExperimentalEntityPool,FeatureEvidence> minimalUpdater){
        super();
        if (minimalUpdater == null){
            throw new IllegalArgumentException("The minimal updater is required and cannot be null.");
        }
        this.delegate = minimalUpdater;
    }

    protected boolean removeEntitiesFromPool(){
        return true;
    }

    @Override
    protected void processOtherProperties(ExperimentalEntityPool poolToEnrich, ExperimentalEntityPool fetched) throws EnricherException {
        this.delegate.processOtherProperties(poolToEnrich, fetched);
        super.processOtherProperties(poolToEnrich, fetched);
    }

    @Override
    protected void processOtherProperties(ExperimentalEntityPool participantToEnrich) throws EnricherException {
        this.delegate.processOtherProperties(participantToEnrich);
        super.processOtherProperties(participantToEnrich);
    }

    @Override
    public ParticipantEnricherListener getParticipantEnricherListener() {
        return this.delegate.getParticipantEnricherListener();
    }

    @Override
    public void setParticipantListener(ParticipantEnricherListener listener) {
        this.delegate.setParticipantListener(listener);
    }

    @Override
    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvTermEnricher) {
        this.delegate.setCvTermEnricher(cvTermEnricher);
    }

    @Override
    public CvTermEnricher<CvTerm> getCvTermEnricher() {
        return this.delegate.getCvTermEnricher();
    }

    @Override
    public void setFeatureEnricher(FeatureEnricher<FeatureEvidence> featureEnricher) {
        this.delegate.setFeatureEnricher(featureEnricher);
    }

    @Override
    public FeatureEnricher<FeatureEvidence> getFeatureEnricher() {
        return this.delegate.getFeatureEnricher();
    }

    @Override
    public void setInteractorEnricher(CompositeInteractorEnricher interactorEnricher) {
        this.delegate.setInteractorEnricher(interactorEnricher);
    }

    @Override
    public CompositeInteractorEnricher getInteractorEnricher() {
        return this.delegate.getInteractorEnricher();
    }
}
