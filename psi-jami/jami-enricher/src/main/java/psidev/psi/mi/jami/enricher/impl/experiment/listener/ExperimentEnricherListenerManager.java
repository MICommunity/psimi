package psidev.psi.mi.jami.enricher.impl.experiment.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Experiment;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
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
    public void onEnrichmentComplete(Experiment experiment, EnrichmentStatus status, String message) {
        for(ExperimentEnricherListener listener : listenersList) {
            listener.onEnrichmentComplete(experiment , status, message);
        }
    }
}
