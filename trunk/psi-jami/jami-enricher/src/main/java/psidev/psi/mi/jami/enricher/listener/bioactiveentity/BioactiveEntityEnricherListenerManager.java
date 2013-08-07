package psidev.psi.mi.jami.enricher.listener.bioactiveentity;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
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
public class BioactiveEntityEnricherListenerManager
        extends EnricherListenerManager<BioactiveEntityEnricherListener>
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


    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
        for(BioactiveEntityEnricherListener listener : listenersList){
            listener.onEnrichmentComplete(object, status, message);
        }
    }
}
