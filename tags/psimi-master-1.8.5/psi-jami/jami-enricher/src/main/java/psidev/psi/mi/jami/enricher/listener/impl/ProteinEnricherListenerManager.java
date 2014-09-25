package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.model.Protein;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  12/06/13
 */
public class ProteinEnricherListenerManager
        extends PolymerEnricherListenerManager<Protein>
        implements ProteinEnricherListener {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public ProteinEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public ProteinEnricherListenerManager(ProteinEnricherListener... listeners){
        super(listeners);
    }

    //============================================================================================
}
