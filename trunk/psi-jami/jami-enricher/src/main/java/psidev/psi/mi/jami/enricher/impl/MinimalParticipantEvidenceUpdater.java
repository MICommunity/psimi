package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalEntity;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class MinimalParticipantEvidenceUpdater<P extends ExperimentalEntity, F extends FeatureEvidence> extends MinimalParticipantEvidenceEnricher<P , F> {

    private MinimalParticipantUpdater<P,F> minimalUpdater;

    public MinimalParticipantEvidenceUpdater(){
        super();
        this.minimalUpdater = new MinimalParticipantUpdater<P, F>();
    }

    @Override
    protected void processIdentificationMethods(P participantEvidenceToEnrich, P objectSource) throws EnricherException {
        mergeIdentificationMethods(participantEvidenceToEnrich, participantEvidenceToEnrich.getIdentificationMethods(), objectSource.getIdentificationMethods(), true);

        processIdentificationMethods(participantEvidenceToEnrich);
    }

    @Override
    protected void processExperimentalRole(P participantEvidenceToEnrich, P objectSource) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(participantEvidenceToEnrich.getExperimentalRole(), objectSource.getExperimentalRole())){
            CvTerm old = participantEvidenceToEnrich.getExperimentalRole();
            participantEvidenceToEnrich.setBiologicalRole(objectSource.getExperimentalRole());
            if (getParticipantEnricherListener() instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)getParticipantEnricherListener()).onExperimentalRoleUpdate(participantEvidenceToEnrich, old);
            }
        }
        else if (getCvTermEnricher() != null
                && participantEvidenceToEnrich.getExperimentalRole() != objectSource.getExperimentalRole()){
            getCvTermEnricher().enrich(participantEvidenceToEnrich.getExperimentalRole(), objectSource.getExperimentalRole());
        }

        processExperimentalRole(participantEvidenceToEnrich);
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
}
