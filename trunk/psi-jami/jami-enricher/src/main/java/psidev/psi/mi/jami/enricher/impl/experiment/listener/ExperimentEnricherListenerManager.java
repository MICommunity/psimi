package psidev.psi.mi.jami.enricher.impl.experiment.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Experiment;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/08/13
 */
public class ExperimentEnricherListenerManager
        extends EnricherListenerManager<ExperimentEnricherListener>
        implements ExperimentEnricherListener{


    public void onExperimentEnriched(Experiment experiment, EnrichmentStatus status, String message) {
        for(ExperimentEnricherListener listener : listenersList) {
            listener.onExperimentEnriched(experiment , status, message);
        }
    }
}
