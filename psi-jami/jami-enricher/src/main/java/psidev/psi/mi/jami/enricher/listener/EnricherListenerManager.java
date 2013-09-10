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
public abstract class EnricherListenerManager<O extends Object, T extends EnricherListener<O>> implements EnricherListener<O>{

    /**
     * The list of listeners of the given type to be fired.
     */
    private Collection<T> listenersList = new ArrayList<T>();

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
            this.listenersList.add(listener);
        }
    }

    protected Collection<T> getListenersList() {
        return listenersList;
    }

    public void onEnrichmentComplete(O object, EnrichmentStatus status, String message) {
        for(T listener : this.listenersList){
            listener.onEnrichmentComplete(object, status, message);
        }
    }

    public void onEnrichmentError(O object, String message, Exception e) {
        for(T listener : this.listenersList){
            listener.onEnrichmentError(object, message, e);
        }
    }
}
