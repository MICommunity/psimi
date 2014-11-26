package psidev.psi.mi.jami.enricher.listener.impl;


import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.enricher.listener.InteractorPoolEnricherListener;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;

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
public class InteractorPoolEnricherListenerManager
        extends InteractorEnricherListenerManager<InteractorPool>
        implements InteractorPoolEnricherListener {

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public InteractorPoolEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public InteractorPoolEnricherListenerManager(InteractorPoolEnricherListener... listeners){
        super(listeners);
    }

    //============================================================================================

    public void onAddedInteractor(InteractorPool interactor, Interactor added) {
        for(InteractorEnricherListener<InteractorPool> l : getListenersList()){
            if (l instanceof InteractorPoolEnricherListener){
                ((InteractorPoolEnricherListener)l).onAddedInteractor(interactor, added);
            }
        }
    }

    public void onRemovedInteractor(InteractorPool interactor, Interactor removed) {
        for(InteractorEnricherListener<InteractorPool> l : getListenersList()){
            if (l instanceof InteractorPoolEnricherListener){
                ((InteractorPoolEnricherListener)l).onRemovedInteractor(interactor, removed);
            }
        }
    }
}
