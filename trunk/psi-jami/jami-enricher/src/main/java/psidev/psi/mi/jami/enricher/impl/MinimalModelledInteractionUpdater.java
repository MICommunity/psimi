package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ModelledInteractionEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.*;

import java.util.Date;

/**
 * Minimal updater for modelled interaction
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalModelledInteractionUpdater<I extends ModelledInteraction>
        extends MinimalModelledInteractionEnricher<I>{

    private InteractionEnricher<I, ModelledParticipant, ModelledFeature> delegate;

    public MinimalModelledInteractionUpdater(){
        super();
        this.delegate = new MinimalInteractionUpdater<I, ModelledParticipant, ModelledFeature>();
    }

    @Override
    protected void processSource(I objectToEnrich, I objectSource) {
         if (objectSource.getSource() != objectToEnrich.getSource()){
             Source old = objectToEnrich.getSource();
              objectToEnrich.setSource(objectSource.getSource());
             if (getInteractionEnricherListener() instanceof ModelledInteractionEnricherListener){
                 ((ModelledInteractionEnricherListener)getInteractionEnricherListener()).onSourceUpdate(objectToEnrich, old);
             }
         }
    }

    @Override
    protected void processCreatedDate(I objectToEnrich, I objectSource) {
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
    protected void processUpdateDate(I objectToEnrich, I objectSource) {
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
    protected void processShortName(I objectToEnrich, I objectSource) {
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
    protected void processIdentifiers(I objectToEnrich, I objectSource) {
        EnricherUtils.mergeXrefs(objectToEnrich, objectToEnrich.getIdentifiers(), objectSource.getIdentifiers(), true, true,
                getInteractionEnricherListener(), getInteractionEnricherListener());
    }

    @Override
    protected void processParticipants(I objectToEnrich, I objectSource) throws EnricherException{
        EnricherUtils.mergeParticipants(objectToEnrich, objectToEnrich.getParticipants(), objectSource.getParticipants(),
                true, getInteractionEnricherListener(), getParticipantEnricher());

        processParticipants(objectToEnrich);
    }

    @Override
    protected void processInteractionType(I objectToEnrich, I objectSource) throws EnricherException {

        if (objectToEnrich.getInteractionType() != objectSource.getInteractionType()){
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
    public void setParticipantEnricher(ParticipantEnricher<ModelledParticipant, ModelledFeature> participantEnricher) {
        this.delegate.setParticipantEnricher(participantEnricher);
    }

    @Override
    public ParticipantEnricher<ModelledParticipant, ModelledFeature> getParticipantEnricher() {
        return this.delegate.getParticipantEnricher();
    }

    @Override
    public InteractionEnricherListener<I> getInteractionEnricherListener() {
        return this.delegate.getInteractionEnricherListener();
    }

    @Override
    public void setInteractionEnricherListener(InteractionEnricherListener<I> listener) {
        this.delegate.setInteractionEnricherListener(listener);
    }
}

