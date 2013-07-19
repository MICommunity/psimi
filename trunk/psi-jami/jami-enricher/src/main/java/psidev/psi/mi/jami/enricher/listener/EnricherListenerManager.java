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
    protected ArrayList<T> listenersList = new ArrayList<T>();

    /**
     * Adds the listener to the list of listeners, if the list does not already contain it.
     * @param listener
     */
    public void addEnricherListener(T listener){
        if( ! listenersList.contains(listener) )
            listenersList.add(listener);
    }

    /**
     * Removes the listener from the list
     * @param listener
     */
    public void removeEnricherListener(T listener){
        listenersList.remove(listener);
    }
}
