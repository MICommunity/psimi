package psidev.psi.mi.jami.enricher.listener.impl;

import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No contract is given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class BioactiveEntityEnricherListenerManager extends InteractorEnricherListenerManager<BioactiveEntity>
        implements BioactiveEntityEnricherListener{
    /**
     * A constructor to create a listener manager with no listeners.
     */
    public BioactiveEntityEnricherListenerManager(){}

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public BioactiveEntityEnricherListenerManager(BioactiveEntityEnricherListener... listeners){
        super(listeners);
    }
}
