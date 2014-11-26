package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.enricher.ExperimentEnricher;
import psidev.psi.mi.jami.enricher.InteractionEvidenceEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractionEvidenceEnricherListener;
import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * Minimal enricher for interaction evidence
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalInteractionEvidenceEnricher
        extends MinimalInteractionEnricher<InteractionEvidence>
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
}

