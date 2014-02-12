package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.enricher.InteractionEvidenceEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.InteractionEvidenceEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.Date;

/**
 * Minimal updater for interaction evidence
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalInteractionEvidenceUpdater
        extends MinimalInteractionEvidenceEnricher
        implements InteractionEvidenceEnricher {

    private InteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence> delegate;

    public MinimalInteractionEvidenceUpdater(){
        super();
        this.delegate = new MinimalInteractionUpdater<InteractionEvidence, ParticipantEvidence, FeatureEvidence>();
    }

    @Override
    protected void processExperiment(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
         if (objectSource.getExperiment() != objectToEnrich.getExperiment()){
             Experiment old = objectToEnrich.getExperiment();
              objectToEnrich.setExperiment(objectSource.getExperiment());
             if (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener){
                 ((InteractionEvidenceEnricherListener)getInteractionEnricherListener()).onExperimentUpdate(objectToEnrich, old);
             }
         }
    }

    @Override
    protected void processCreatedDate(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
        if ((objectSource.getCreatedDate() != null && !objectSource.getCreatedDate().equals(objectToEnrich.getCreatedDate()))
                || (objectSource.getCreatedDate() == null && objectToEnrich.getCreatedDate() != null)){
            Date oldDate = objectToEnrich.getCreatedDate();
            objectToEnrich.setCreatedDate(objectSource.getCreatedDate());
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onCreatedDateUpdate(objectToEnrich, oldDate);
            }
        }
    }

    @Override
    protected void processUpdateDate(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
        if ((objectSource.getUpdatedDate() != null && !objectSource.getUpdatedDate().equals(objectToEnrich.getUpdatedDate()))
                || (objectSource.getUpdatedDate() == null && objectToEnrich.getUpdatedDate() != null)){
            Date oldDate = objectToEnrich.getUpdatedDate();
            objectToEnrich.setUpdatedDate(objectSource.getUpdatedDate());
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onUpdatedDateUpdate(objectToEnrich, oldDate);
            }
        }
    }

    @Override
    protected void processShortName(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
        if ((objectSource.getShortName() != null && !objectSource.getShortName().equals(objectToEnrich.getShortName()))
                || (objectSource.getShortName() == null && objectToEnrich.getShortName() != null)){
            String oldName = objectToEnrich.getShortName();
            objectToEnrich.setShortName(objectSource.getShortName());
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onShortNameUpdate(objectToEnrich, oldName);
            }
        }
    }

    @Override
    protected void processOtherProperties(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) throws EnricherException{
        super.processOtherProperties(objectToEnrich, objectSource);

        // set negative
        if (objectSource.isNegative() != objectToEnrich.isNegative()){
            objectToEnrich.setNegative(objectSource.isNegative());
            if (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)getInteractionEnricherListener()).onNegativePropertyUpdate(objectToEnrich, !objectSource.isNegative());
            }
        }
    }

    @Override
    protected void processIdentifiers(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getIdentifiers(), objectSource.getIdentifiers(), true, true,
                getInteractionEnricherListener(), getInteractionEnricherListener());
    }

    @Override
    protected void processParticipants(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) throws EnricherException{
        EnricherUtils.mergeParticipants(objectToEnrich, objectToEnrich.getParticipants(), objectSource.getParticipants(),
                true, getInteractionEnricherListener(), getParticipantEnricher());

        processParticipants(objectToEnrich);
    }

    @Override
    protected void processInteractionType(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) throws EnricherException {

        if (!DefaultCvTermComparator.areEquals(objectToEnrich.getInteractionType(), objectSource.getInteractionType())){
            CvTerm oldType = objectToEnrich.getInteractionType();
            objectToEnrich.setInteractionType(objectSource.getInteractionType());
            if (getInteractionEnricherListener() != null){
                getInteractionEnricherListener().onInteractionTypeUpdate(objectToEnrich, oldType);
            }
        }

        processInteractionType(objectToEnrich);
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
    public void setParticipantEnricher(ParticipantEnricher<ParticipantEvidence, FeatureEvidence> participantEnricher) {
        this.delegate.setParticipantEnricher(participantEnricher);
    }

    @Override
    public ParticipantEnricher<ParticipantEvidence, FeatureEvidence> getParticipantEnricher() {
        return this.delegate.getParticipantEnricher();
    }

    @Override
    public InteractionEnricherListener<InteractionEvidence> getInteractionEnricherListener() {
        return this.delegate.getInteractionEnricherListener();
    }

    @Override
    public void setInteractionEnricherListener(InteractionEnricherListener<InteractionEvidence> listener) {
        this.delegate.setInteractionEnricherListener(listener);
    }
}

