package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.ExperimentEnricher;
import psidev.psi.mi.jami.enricher.InteractionEvidenceEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractionEvidenceEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.VariableParameterValueSet;

import java.util.Iterator;

/**
 * Minimal enricher for interaction evidence
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalInteractionEvidenceEnricher
        extends MinimalInteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence>
        implements InteractionEvidenceEnricher {

    private ExperimentEnricher experimentEnricher = null;

    /**
     * Strategy for the Interaction enrichment.
     * This method can be overwritten to change how the interaction is enriched.
     * @param interactionToEnrich   The interaction to be enriched.
     */
    @Override
    protected void processOtherProperties(InteractionEvidence interactionToEnrich) throws EnricherException {

        //PROCESS experiment
        processExperiment(interactionToEnrich);
    }

    /**
     * The experimentEnricher which is currently being used for the enriching or updating of experiments.
     * @return The experiment enricher. Can be null.
     */
    public ExperimentEnricher getExperimentEnricher() {
        return experimentEnricher ;
    }

    /**
     * Sets the experimentEnricher to be used.
     * @param experimentEnricher The experiment enricher to be used. Can be null.
     */
    public void setExperimentEnricher(ExperimentEnricher experimentEnricher) {
        this.experimentEnricher = experimentEnricher;
    }

    protected void processExperiment(InteractionEvidence interactionToEnrich) throws EnricherException {
        if( getExperimentEnricher() != null
                && interactionToEnrich.getExperiment() != null )
            getExperimentEnricher().enrich(interactionToEnrich.getExperiment());
    }

    @Override
    protected void processOtherProperties(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) throws EnricherException {
        // experiment
        processExperiment(objectToEnrich, objectSource);
        // confidences
        processConfidences(objectToEnrich, objectSource);
        // parameters
        processParameters(objectToEnrich, objectSource);
        // variable parameters
        processVariableParameters(objectToEnrich, objectSource);
    }

    protected void processExperiment(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) throws EnricherException {
         if (objectSource.getExperiment() != null && objectToEnrich.getExperiment() == null){
              objectToEnrich.setExperiment(objectSource.getExperiment());
             if (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener){
                 ((InteractionEvidenceEnricherListener)getInteractionEnricherListener()).onExperimentUpdate(objectToEnrich, null);
             }
         }

        processExperiment(objectToEnrich);
    }

    protected void processConfidences(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
        EnricherUtils.mergeConfidences(objectToEnrich, objectToEnrich.getConfidences(), objectSource.getConfidences(), false,
                (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener ? (InteractionEvidenceEnricherListener)getInteractionEnricherListener():null));
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
}

