package psidev.psi.mi.jami.enricher.listener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @param <T>   The type of the listeners which are to be used.
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public abstract class EnricherListenerManager<T extends EnricherListener> {

    /**
     * The list of listeners of the given type to be fired.
     */
    protected Collection<T> listenersList = new ArrayList<T>();

    /**
     * An empty constructor to allow the manager to initiated without an listeners.
     */
    protected EnricherListenerManager(){}

    /**
     * A constructor to allow setting of an unlimited number of listeners.
     * @param listeners     The listeners to be added to the listener list.
     */
    protected EnricherListenerManager(T... listeners){
        for(T listener : listeners){
            addEnricherListener(listener);
        }
    }

    /**
     * Adds the listener to the list of listeners, if the list does not already contain it.
     * @param listener      The listener to be added.
     */
    public void addEnricherListener(T listener){
       // if( ! listenersList.contains(listener) )
            listenersList.add(listener);
    }

    /**
     * Removes the listener from the list
     * @param listener      The listener to be removed.
     */
    public void removeEnricherListener(T listener){
        listenersList.remove(listener);
    }

    /**
     * Finds whether the listener manager contains the given listener.
     * @param listener  The listener to be searched for.
     * @return          True if the list contains the listener.
     */
    public boolean containsEnricherListener(T listener){
        return listenersList.contains(listener);
    }
}
