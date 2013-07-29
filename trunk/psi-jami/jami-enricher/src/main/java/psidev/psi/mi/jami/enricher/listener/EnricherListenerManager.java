package psidev.psi.mi.jami.enricher.listener;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public abstract class EnricherListenerManager<T extends EnricherListener> {

    protected Collection<T> listenersList = new ArrayList<T>();

    protected EnricherListenerManager(){}
    protected EnricherListenerManager(T... listeners){
        for(T listener : listeners){
            addEnricherListener(listener);
        }
    }

    /**
     * Adds the listener to the list of listeners, if the list does not already contain it.
     * @param listener
     */
    public void addEnricherListener(T listener){
       // if( ! listenersList.contains(listener) )
            listenersList.add(listener);
    }

    /**
     * Removes the listener from the list
     * @param listener
     */
    public void removeEnricherListener(T listener){
        listenersList.remove(listener);
    }

    /**
     * Adds the listener to the list of listeners, if the list does not already contain it.
     * @param listener
     */
    public boolean containsEnricherListener(T listener){
        return listenersList.contains(listener);
    }
}
