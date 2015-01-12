package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * An enricher for interaction evidences.
 * As well as extending the interaction enricher, this also includes an experiment enricher.
 * Experiment is only found in InteractionEvidence .
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 24/07/13
 */
public interface InteractionEvidenceEnricher
        extends InteractionEnricher<InteractionEvidence> {

    /**
     * The experimentEnricher which is currently being used for the enriching or updating of experiments.
     * @return The experiment enricher. Can be null.
     */
    public ExperimentEnricher getExperimentEnricher();

    public void setExperimentEnricher(ExperimentEnricher enricher);

}