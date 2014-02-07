package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.InteractionEvidenceEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.ModelledInteractionEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.*;

import java.util.Iterator;

/**
 * Full enricher for ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullModelledInteractionEnricher<I extends ModelledInteraction> extends MinimalModelledInteractionEnricher<I>{

    private FullInteractionEnricher<I, ModelledParticipant, ModelledFeature> interactionEnricher;
    private InteractionEvidenceEnricher evidenceEnricher;

    public FullModelledInteractionEnricher() {
        super();
        this.interactionEnricher = new FullInteractionEnricher<I, ModelledParticipant, ModelledFeature>();
    }

    protected FullModelledInteractionEnricher(FullInteractionEnricher<I, ModelledParticipant, ModelledFeature> interactionEnricher) {
        super();
        this.interactionEnricher = interactionEnricher != null ? interactionEnricher : new FullInteractionEnricher();
    }

    @Override
    protected void processOtherProperties(I interactionToEnrich) throws EnricherException {
        super.processOtherProperties(interactionToEnrich);

        // PROCESS RIGID
        this.interactionEnricher.processOtherProperties(interactionToEnrich);

        // process interaction evidences
        processInteractionEvidences(interactionToEnrich);

    }

    @Override
    protected void processOtherProperties(I objectToEnrich, I objectSource) throws EnricherException {
        super.processOtherProperties(objectToEnrich, objectSource);

        this.interactionEnricher.processOtherProperties(objectToEnrich, objectSource);

        // confidences
        processConfidences(objectToEnrich, objectSource);
        // parameters
        processParameters(objectToEnrich, objectSource);
        // cooperative effects
        processCooperativeEffects(objectToEnrich, objectSource);
        // interactionEvidences
        processInteractionEvidences(objectToEnrich, objectSource);
    }

    @Override
    public CvTermEnricher<CvTerm> getCvTermEnricher() {
        return this.interactionEnricher.getCvTermEnricher();
    }

    @Override
    public ParticipantEnricher<ModelledParticipant, ModelledFeature> getParticipantEnricher() {
        return this.interactionEnricher.getParticipantEnricher();
    }

    @Override
    public InteractionEnricherListener<I> getInteractionEnricherListener() {
        return this.interactionEnricher.getInteractionEnricherListener();
    }

    @Override
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        this.interactionEnricher.setCvTermEnricher(cvTermEnricher);
    }

    @Override
    public void setParticipantEnricher(ParticipantEnricher<ModelledParticipant, ModelledFeature> participantEnricher) {
        this.interactionEnricher.setParticipantEnricher(participantEnricher);
    }

    @Override
    public void setInteractionEnricherListener(InteractionEnricherListener<I> listener) {
        this.interactionEnricher.setInteractionEnricherListener(listener);
    }

    protected void processConfidences(I objectToEnrich, I objectSource) {
        EnricherUtils.mergeConfidences(objectToEnrich, objectToEnrich.getModelledConfidences(), objectSource.getModelledConfidences(), false,
                (getInteractionEnricherListener() instanceof ModelledInteractionEnricherListener ? (ModelledInteractionEnricherListener) getInteractionEnricherListener() : null));
    }

    protected void processParameters(I objectToEnrich, I objectSource) {

        EnricherUtils.mergeParameters(objectToEnrich, objectToEnrich.getModelledParameters(), objectSource.getModelledParameters(), false,
                (getInteractionEnricherListener() instanceof ModelledInteractionEnricherListener ? (ModelledInteractionEnricherListener)getInteractionEnricherListener():null));
    }

    protected void processCooperativeEffects(I objectToEnrich, I objectSource) {
        mergeCooperativeEffects(objectToEnrich, objectSource, false);
    }

    protected void mergeCooperativeEffects(I interactionToEnrich, I objectSource, boolean remove){
        Iterator<CooperativeEffect> effectIterator = interactionToEnrich.getCooperativeEffects().iterator();
        if (remove){
            while(effectIterator.hasNext()){
                CooperativeEffect effect = effectIterator.next();

                boolean containsEffect = false;
                for (CooperativeEffect effect2 : objectSource.getCooperativeEffects()){
                    // identical parameter
                    if (effect == effect2){
                        containsEffect = true;
                        break;
                    }
                }
                // remove parameter not in second list
                if (!containsEffect){
                    effectIterator.remove();
                    if (getInteractionEnricherListener() instanceof ModelledInteractionEnricherListener){
                        ((ModelledInteractionEnricherListener)getInteractionEnricherListener()).onRemovedCooperativeEffect(interactionToEnrich, effect);
                    }
                }
            }
        }

        effectIterator = objectSource.getCooperativeEffects().iterator();
        while(effectIterator.hasNext()){
            CooperativeEffect effect = effectIterator.next();
            boolean containsEffect = false;
            for (CooperativeEffect effect2 : interactionToEnrich.getCooperativeEffects()){
                // identical param
                if (effect == effect2){
                    containsEffect = true;
                    break;
                }
            }
            if (!containsEffect){
                interactionToEnrich.getCooperativeEffects().add(effect);
                if (getInteractionEnricherListener() instanceof ModelledInteractionEnricherListener){
                    ((ModelledInteractionEnricherListener)getInteractionEnricherListener()).onAddedCooperativeEffect(interactionToEnrich, effect);
                }
            }
        }
    }

    protected void processInteractionEvidences(I objectToEnrich) throws EnricherException {
        if (getEvidenceEnricher() != null){
           getEvidenceEnricher().enrich(objectToEnrich.getInteractionEvidences());
        }
    }

    protected void processInteractionEvidences(I objectToEnrich, I objectSource) throws EnricherException {
        mergeInteractionEvidences(objectToEnrich, objectSource, false);

        processInteractionEvidences(objectToEnrich);
    }

    protected void mergeInteractionEvidences(I interactionToEnrich, I objectSource, boolean remove) throws EnricherException {
        Iterator<InteractionEvidence> evidenceIterator = interactionToEnrich.getInteractionEvidences().iterator();
        if (remove){
            while(evidenceIterator.hasNext()){
                InteractionEvidence inter = evidenceIterator.next();

                boolean containsEvidence = false;
                for (InteractionEvidence inter2 : objectSource.getInteractionEvidences()){
                    // identical parameter
                    if (inter == inter2){
                        containsEvidence = true;
                        break;
                    }
                }
                // remove parameter not in second list
                if (!containsEvidence){
                    evidenceIterator.remove();
                    if (getInteractionEnricherListener() instanceof ModelledInteractionEnricherListener){
                        ((ModelledInteractionEnricherListener)getInteractionEnricherListener()).onRemovedInteractionEvidence(interactionToEnrich, inter);
                    }
                }
            }
        }

        evidenceIterator = objectSource.getInteractionEvidences().iterator();
        while(evidenceIterator.hasNext()){
            InteractionEvidence inter = evidenceIterator.next();
            boolean containsEvidences = false;
            for (InteractionEvidence inter2 : interactionToEnrich.getInteractionEvidences()){
                // identical param
                if (inter == inter2){
                    containsEvidences = true;
                    if (getEvidenceEnricher() != null){
                        getEvidenceEnricher().enrich(inter2, inter);
                    }
                    break;
                }
            }
            if (!containsEvidences){
                interactionToEnrich.getInteractionEvidences().add(inter);
                if (getInteractionEnricherListener() instanceof ModelledInteractionEnricherListener){
                    ((ModelledInteractionEnricherListener)getInteractionEnricherListener()).onAddedInteractionEvidence(interactionToEnrich, inter);
                }
            }
        }
    }

    public InteractionEvidenceEnricher getEvidenceEnricher() {
        return evidenceEnricher;
    }

    public void setEvidenceEnricher(InteractionEvidenceEnricher evidenceEnricher) {
        this.evidenceEnricher = evidenceEnricher;
    }
}
