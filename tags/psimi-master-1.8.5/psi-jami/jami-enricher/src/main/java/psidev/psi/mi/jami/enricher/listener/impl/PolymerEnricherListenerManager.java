package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.enricher.listener.PolymerEnricherListener;
import psidev.psi.mi.jami.listener.PolymerChangeListener;
import psidev.psi.mi.jami.model.*;

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
public class PolymerEnricherListenerManager<P extends Polymer>
        extends InteractorEnricherListenerManager<P>
        implements PolymerEnricherListener<P> {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public PolymerEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public PolymerEnricherListenerManager(InteractorEnricherListener<P>... listeners){
        super(listeners);
    }

    //============================================================================================

    public void onSequenceUpdate(P protein, String oldSequence) {
        for(InteractorEnricherListener<P> l : getListenersList()){
            if (l instanceof PolymerChangeListener){
                ((PolymerChangeListener)l).onSequenceUpdate(protein, oldSequence);
            }
        }
    }
}
