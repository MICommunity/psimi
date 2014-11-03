package uk.ac.ebi.intact.jami.imex.listener.impl;

import psidev.psi.mi.jami.enricher.listener.ExperimentEnricherListener;
import psidev.psi.mi.jami.enricher.listener.impl.ExperimentEnricherListenerManager;
import psidev.psi.mi.jami.model.*;
import uk.ac.ebi.intact.jami.imex.listener.ExperimentImexEnricherListener;

import java.util.Collection;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 */
public class ExperimentImexEnricherListenerManager
        extends ExperimentEnricherListenerManager
        implements ExperimentImexEnricherListener {

    public ExperimentImexEnricherListenerManager(ExperimentImexEnricherListener... experimentEnricherListener){
        super (experimentEnricherListener);
    }

    public void onImexIdConflicts(Experiment originalExperiment, Collection<Xref> conflictingXrefs) {
        for (ExperimentEnricherListener listener : getListenersList()){
            if (listener instanceof ExperimentImexEnricherListener){
               ((ExperimentImexEnricherListener) listener).onImexIdConflicts(originalExperiment, conflictingXrefs);
            }
        }
    }

    public void onImexIdAssigned(Experiment experiment, String imex) {
        for (ExperimentEnricherListener listener : getListenersList()){
            if (listener instanceof ExperimentImexEnricherListener){
                ((ExperimentImexEnricherListener) listener).onImexIdAssigned(experiment, imex);
            }
        }
    }
}
