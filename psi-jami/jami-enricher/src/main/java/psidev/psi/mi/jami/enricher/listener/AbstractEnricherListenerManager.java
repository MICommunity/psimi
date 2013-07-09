package psidev.psi.mi.jami.enricher.listener;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/07/13
 */
public abstract class AbstractEnricherListenerManager <T extends EnricherListener> {
    protected ArrayList<T> listenersList = new ArrayList<T>();

    public void addEnricherListener(T listener){
        listenersList.add(listener);
    }
    public void addAllEnricherListeners(Collection<T> listeners){
        listenersList.addAll(listeners);
    }

    public void removeEnricherListener(T listener){
        listenersList.remove(listener);
    }
}
