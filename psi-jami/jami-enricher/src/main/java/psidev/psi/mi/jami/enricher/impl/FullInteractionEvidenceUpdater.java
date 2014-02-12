package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractionEvidenceEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.experiment.DefaultCuratedExperimentComparator;

/**
 * Full updater for interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullInteractionEvidenceUpdater extends FullInteractionEvidenceEnricher{

    public FullInteractionEvidenceUpdater() {
        super(new FullInteractionUpdater<InteractionEvidence, ParticipantEvidence, FeatureEvidence>());
    }

    @Override
    protected void processConfidences(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
        EnricherUtils.mergeConfidences(objectToEnrich, objectToEnrich.getConfidences(), objectSource.getConfidences(), true,
                (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener ? (InteractionEvidenceEnricherListener) getInteractionEnricherListener() : null));
    }

    @Override
    protected void processParameters(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {

        EnricherUtils.mergeParameters(objectToEnrich, objectToEnrich.getParameters(), objectSource.getParameters(), true,
                (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener ? (InteractionEvidenceEnricherListener) getInteractionEnricherListener() : null));
    }

    @Override
    protected void processVariableParameters(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) {
        mergerVariableParameters(objectToEnrich, objectSource, true);
    }

    @Override
    protected void processOtherProperties(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) throws EnricherException {
        super.processOtherProperties(objectToEnrich, objectSource);
        if (objectSource.isNegative() != objectToEnrich.isNegative()){
            objectToEnrich.setNegative(objectSource.isNegative());
            if (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)getInteractionEnricherListener()).onNegativePropertyUpdate(objectToEnrich, !objectSource.isNegative());
            }
        }
        if (objectSource.isInferred() != objectToEnrich.isInferred()){
            objectToEnrich.setInferred(objectSource.isInferred());
            if (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)getInteractionEnricherListener()).onInferredPropertyUpdate(objectToEnrich, !objectSource.isInferred());
            }
        }
    }

    @Override
    protected void processExperiment(InteractionEvidence objectToEnrich, InteractionEvidence objectSource) throws EnricherException {
        if (!DefaultCuratedExperimentComparator.areEquals(objectSource.getExperiment(), objectToEnrich.getExperiment())){
            Experiment old = objectToEnrich.getExperiment();
            objectToEnrich.setExperiment(objectSource.getExperiment());
            if (getInteractionEnricherListener() instanceof InteractionEvidenceEnricherListener){
                ((InteractionEvidenceEnricherListener)getInteractionEnricherListener()).onExperimentUpdate(objectToEnrich, old);
            }
        }
        else if (getExperimentEnricher() != null
                && objectToEnrich.getExperiment() != objectSource.getExperiment()){
            getExperimentEnricher().enrich(objectToEnrich.getExperiment(), objectSource.getExperiment());
        }

        processExperiment(objectToEnrich);
    }
}