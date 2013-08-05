package psidev.psi.mi.jami.enricher.impl.experiment.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Experiment;

/**
 * An listenerManager for ExperimentEnricherListeners.
 * Allows multiple listeners to be applied to one enricher.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 0 1/08/13
 */
public class ExperimentEnricherListenerManager
        extends EnricherListenerManager<ExperimentEnricherListener>
        implements ExperimentEnricherListener{

    public ExperimentEnricherListenerManager (ExperimentEnricherListener... experimentEnricherListener){
        super (experimentEnricherListener);
    }

    /**
     * An event fired when an experiment's enrichment has been completed.
     * @param experiment    The experiment that was enriched. can not be null.
     * @param status        The status of the enrichment. Can not be null.
     * @param message       An additional message which may be included if the status was failed. Can be null.
     */
    public void onExperimentEnriched(Experiment experiment, EnrichmentStatus status, String message) {
        for(ExperimentEnricherListener listener : listenersList) {
            listener.onExperimentEnriched(experiment , status, message);
        }
    }
}
