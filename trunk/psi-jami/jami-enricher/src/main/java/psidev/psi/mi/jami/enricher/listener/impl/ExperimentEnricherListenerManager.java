package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.impl.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.ExperimentEnricherListener;
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
        extends EnricherListenerManager<Experiment, ExperimentEnricherListener>
        implements ExperimentEnricherListener{

    public ExperimentEnricherListenerManager (ExperimentEnricherListener... experimentEnricherListener){
        super (experimentEnricherListener);
    }
}
