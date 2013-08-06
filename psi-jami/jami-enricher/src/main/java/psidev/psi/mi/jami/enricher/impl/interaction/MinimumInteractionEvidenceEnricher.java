package psidev.psi.mi.jami.enricher.impl.interaction;


import psidev.psi.mi.jami.enricher.ExperimentEnricher;
import psidev.psi.mi.jami.enricher.InteractionEvidenceEnricher;
import psidev.psi.mi.jami.enricher.PublicationEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.*;

/**
 * An extension of the interaction enricher which only accepts InteractionEvidence
 * Overrides the default ParticipantEnricher to the evidence only form.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class MinimumInteractionEvidenceEnricher
        extends MinimumInteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence>
        implements InteractionEvidenceEnricher {


    private ExperimentEnricher experimentEnricher = null;

    /**
     * Strategy for the Interaction enrichment.
     * This method can be overwritten to change how the interaction is enriched.
     * @param interactionToEnrich   The interaction to be enriched.
     */
    @Override
    public void processInteraction(InteractionEvidence interactionToEnrich) throws EnricherException {
        super.processInteraction(interactionToEnrich);

        if( getExperimentEnricher() != null
                && interactionToEnrich.getExperiment() != null )
            getExperimentEnricher().enrichExperiment( interactionToEnrich.getExperiment() );
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
}
