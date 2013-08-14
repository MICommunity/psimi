package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * An enricher for interaction evidences.
 * As well as extending the interaction enricher, this also includes an experiment enricher.
 * Experiment is only found in InteractionEvidence .
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 24/07/13
 */
public interface InteractionEvidenceEnricher
        extends InteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence> {

    /**
     * The experimentEnricher which is currently being used for the enriching or updating of experiments.
     * @return The experiment enricher. Can be null.
     */
    public ExperimentEnricher getExperimentEnricher();

    /**
     * Sets the experimentEnricher to be used.
     * @param experimentEnricher The experiment enricher to be used. Can be null.
     */
    public void setExperimentEnricher(ExperimentEnricher experimentEnricher);
}