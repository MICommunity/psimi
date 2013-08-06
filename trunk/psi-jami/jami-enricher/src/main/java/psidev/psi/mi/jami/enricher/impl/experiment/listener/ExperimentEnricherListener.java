package psidev.psi.mi.jami.enricher.impl.experiment.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Experiment;

/**
 * //An extension of the ExperimentChangeListener
 * //with specific methods related to the process of enriching.
 * Each method will be fired after the change has been made to the experiment.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  31/07/13
 */
public interface ExperimentEnricherListener
        extends EnricherListener<Experiment>{

    /**
     * An event fired when an experiment's enrichment has been completed.
     * @param experiment    The experiment that was enriched. can not be null.
     * @param status        The status of the enrichment. Can not be null.
     * @param message       An additional message which may be included if the status was failed. Can be null.
     */
    public void onEnrichmentComplete(Experiment experiment , EnrichmentStatus status , String message);
}
