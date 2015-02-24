package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.CompositeInteractorEnricher;
import psidev.psi.mi.jami.enricher.listener.EntityEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class MinimalParticipantEvidenceUpdater<P extends ParticipantEvidence> extends MinimalParticipantEvidenceEnricher<P> {

    private MinimalParticipantUpdater<P,FeatureEvidence> minimalUpdater;

    public MinimalParticipantEvidenceUpdater(){
        super();
        this.minimalUpdater = new MinimalParticipantUpdater<P, FeatureEvidence>();
    }

    protected MinimalParticipantEvidenceUpdater(MinimalParticipantUpdater<P, FeatureEvidence> delegate){
        super();
        this.minimalUpdater =  delegate != null ? delegate : new MinimalParticipantUpdater<P, FeatureEvidence>();
    }


    @Override
    public void processIdentificationMethods(P participantEvidenceToEnrich, P objectSource) throws EnricherException {
        mergeIdentificationMethods(participantEvidenceToEnrich, participantEvidenceToEnrich.getIdentificationMethods(), objectSource.getIdentificationMethods(), true);

        processIdentificationMethods(participantEvidenceToEnrich);
    }

    @Override
    protected void processExpressedInOrganism(P participantEvidenceToEnrich, P objectSource) throws EnricherException {
        if (!DefaultOrganismComparator.areEquals(participantEvidenceToEnrich.getExpressedInOrganism(), objectSource.getExpressedInOrganism())){
            Organism old = participantEvidenceToEnrich.getExpressedInOrganism();
            participantEvidenceToEnrich.setExpressedInOrganism(objectSource.getExpressedInOrganism());
            if (getParticipantEnricherListener() instanceof ParticipantEvidenceEnricherListener){
                ((ParticipantEvidenceEnricherListener)getParticipantEnricherListener()).onExpressedInUpdate(participantEvidenceToEnrich, old);
            }
        }
        else if (getOrganismEnricher() != null &&
                participantEvidenceToEnrich.getExpressedInOrganism() != objectSource.getExpressedInOrganism()){
            getOrganismEnricher().enrich(participantEvidenceToEnrich.getExpressedInOrganism(), objectSource.getExpressedInOrganism());
        }
    }

    @Override
    public void processExperimentalRole(P participantEvidenceToEnrich, P objectSource) throws EnricherException {
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
    public void processAliases(P objectToEnrich, P objectSource) throws EnricherException{
        this.minimalUpdater.processAliases(objectToEnrich, objectSource);
    }

    @Override
    public void setParticipantEnricherListener(EntityEnricherListener listener) {
        this.minimalUpdater.setParticipantEnricherListener(listener);
    }

    @Override
    public EntityEnricherListener getParticipantEnricherListener() {
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
    public void setFeatureEnricher(FeatureEnricher<FeatureEvidence> featureEnricher) {
        this.minimalUpdater.setFeatureEnricher(featureEnricher);
    }

    @Override
    public FeatureEnricher<FeatureEvidence> getFeatureEnricher() {
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

    protected MinimalParticipantUpdater<P, FeatureEvidence> getMinimalUpdater() {
        return minimalUpdater;
    }
}
