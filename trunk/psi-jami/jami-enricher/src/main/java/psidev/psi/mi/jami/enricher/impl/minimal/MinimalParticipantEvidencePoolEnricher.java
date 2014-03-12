package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.*;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.CompositeInteractorEnricher;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * A basic minimal enricher for experimental Participant pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalParticipantEvidencePoolEnricher extends MinimalParticipantPoolEnricher<ParticipantEvidencePool,FeatureEvidence>
implements ParticipantPoolEnricher<ParticipantEvidencePool, FeatureEvidence>, ParticipantEvidenceEnricher<ParticipantEvidencePool>{

    private MinimalParticipantEvidenceEnricher<ParticipantEvidencePool> delegate;

    public MinimalParticipantEvidencePoolEnricher(){
        super();
        this.delegate = new MinimalParticipantEvidenceEnricher<ParticipantEvidencePool>();
    }

    protected MinimalParticipantEvidencePoolEnricher(MinimalParticipantEvidenceEnricher<ParticipantEvidencePool> minimalUpdater){
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
    public void processOtherProperties(ParticipantEvidencePool poolToEnrich, ParticipantEvidencePool fetched) throws EnricherException {
        this.delegate.processOtherProperties(poolToEnrich, fetched);
        super.processOtherProperties(poolToEnrich, fetched);
    }

    @Override
    public void processOtherProperties(ParticipantEvidencePool participantToEnrich) throws EnricherException {
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

    public void setOrganismEnricher(OrganismEnricher organismEnricher) {
        this.delegate.setOrganismEnricher(organismEnricher);
    }

    public OrganismEnricher getOrganismEnricher() {
        return this.delegate.getOrganismEnricher();
    }
}
