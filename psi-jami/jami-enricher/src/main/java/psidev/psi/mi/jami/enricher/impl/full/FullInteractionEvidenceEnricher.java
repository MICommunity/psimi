package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalInteractionEvidenceEnricher;
import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.enricher.listener.InteractionEvidenceEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.VariableParameterValueSet;

import java.util.Iterator;

/**
 * Full enricher for Interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullInteractionEvidenceEnricher extends MinimalInteractionEvidenceEnricher {

    private FullInteractionEnricher<InteractionEvidence> interactionEnricher;

    public FullInteractionEvidenceEnricher() {
        super();
        this.interactionEnricher = new FullInteractionEnricher<InteractionEvidence>();
    }

    protected FullInteractionEvidenceEnricher(FullInteractionEnricher<InteractionEvidence> interactionEnricher) {
        super();
        this.interactionEnricher = interactionEnricher != null ? interactionEnricher : new FullInteractionEnricher<InteractionEvidence>();
    }

    @Override
    protected void processOtherProperties(InteractionEvidence interactionToEnrich) throws EnricherException {
        super.processOtherProperties(interactionToEnrich);

        // PROCESS RIGID
        this.interactionEnricher.processOtherProperties(interactionToEnrich);

    }

    @Override
    protected void processOtherProperties(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) throws EnricherException {
        super.processOtherProperties(objectToEnrich, objectSource);

        this.interactionEnricher.processOtherProperties(objectToEnrich, objectSource);
        // confidences
        processConfidences(objectToEnrich, objectSource);
        // parameters
        processParameters(objectToEnrich, objectSource);
        // variable parameters
        processVariableParameters(objectToEnrich, objectSource);
    }

    @Override
    public CvTermEnricher<CvTerm> getCvTermEnricher() {
        return this.interactionEnricher.getCvTermEnricher();
    }

    @Override
    public ParticipantEnricher getParticipantEnricher() {
        return this.interactionEnricher.getParticipantEnricher();
    }

    @Override
    public InteractionEnricherListener<InteractionEvidence> getInteractionEnricherListener() {
        return this.interactionEnricher.getInteractionEnricherListener();
    }

    @Override
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        this.interactionEnricher.setCvTermEnricher(cvTermEnricher);
    }

    @Override
    public void setParticipantEnricher(ParticipantEnricher participantEnricher) {
        this.interactionEnricher.setParticipantEnricher(participantEnricher);
    }

    @Override
    public void setInteractionEnricherListener(InteractionEnricherListener listener) {
        this.interactionEnricher.setInteractionEnricherListener(listener);
    }

    protected void processConfidences(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
        EnricherUtils.mergeConfidences(objectToEnrich, objectToEnrich.getConfidences(), objectSource.getConfidences(), false,
                (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener ? (InteractionEvidenceEnricherListener) getInteractionEnricherListener() : null));
    }

    protected void processParameters(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {

        EnricherUtils.mergeParameters(objectToEnrich, objectToEnrich.getParameters(), objectSource.getParameters(), false,
                (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener ? (InteractionEvidenceEnricherListener)getInteractionEnricherListener():null));
    }

    protected void processVariableParameters(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
        mergerVariableParameters(objectToEnrich, objectSource, false);
    }

    protected void mergerVariableParameters(InteractionEvidence interactionToEnrich, InteractionEvidence objectSource, boolean remove){
        Iterator<VariableParameterValueSet> variableParamIterator = interactionToEnrich.getVariableParameterValues().iterator();
        if (remove){
            while(variableParamIterator.hasNext()){
                VariableParameterValueSet param = variableParamIterator.next();

                boolean containsParam = false;
                for (VariableParameterValueSet param2 : objectSource.getVariableParameterValues()){
                    // identical parameter
                    if (param == param2){
                        containsParam = true;
                        break;
                    }
                }
                // remove parameter not in second list
                if (!containsParam){
                    variableParamIterator.remove();
                    if (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener){
                        ((InteractionEvidenceEnricherListener)getInteractionEnricherListener()).onRemovedVariableParameterValues(interactionToEnrich, param);
                    }
                }
            }
        }

        variableParamIterator = objectSource.getVariableParameterValues().iterator();
        while(variableParamIterator.hasNext()){
            VariableParameterValueSet param = variableParamIterator.next();
            boolean containsParam = false;
            for (VariableParameterValueSet param2 : interactionToEnrich.getVariableParameterValues()){
                // identical param
                if (param == param2){
                    containsParam = true;
                    break;
                }
            }
            if (!containsParam){
                interactionToEnrich.getVariableParameterValues().add(param);
                if (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener){
                    ((InteractionEvidenceEnricherListener)getInteractionEnricherListener()).onAddedVariableParameterValues(interactionToEnrich, param);
                }
            }
        }
    }

    protected FullInteractionEnricher<InteractionEvidence> getInteractionEnricher() {
        return interactionEnricher;
    }
}