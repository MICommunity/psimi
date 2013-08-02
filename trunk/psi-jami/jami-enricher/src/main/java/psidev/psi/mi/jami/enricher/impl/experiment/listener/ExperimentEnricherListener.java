package psidev.psi.mi.jami.enricher.impl.experiment.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Experiment;

/**
 * An enricher listener that reports changes made to an experiment.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  31/07/13
 */
public interface ExperimentEnricherListener
        extends EnricherListener{

    /**
     * An event fired when an experiment's enrichment has been completed.
     * @param experiment    The experiment that was enriched. can not be null.
     * @param status        The status of the enrichment. Can not be null.
     * @param message       An additional message which may be included if the status was failed. Can be null.
     */
    public void onExperimentEnriched(Experiment experiment , EnrichmentStatus status , String message);
}
