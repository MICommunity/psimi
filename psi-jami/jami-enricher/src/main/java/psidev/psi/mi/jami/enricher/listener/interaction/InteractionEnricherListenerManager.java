package psidev.psi.mi.jami.enricher.listener.interaction;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Interaction;

/**
 * A manager for listeners which holds a list of listeners.
 * Listener manager allows enrichers to send events to multiple listeners.
 * A listener itself, it implements all methods
 * which will then fire the corresponding method in each entry of the listener list.
 * No promise can be given to the order in which the listeners are fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionEnricherListenerManager
        extends EnricherListenerManager<InteractionEnricherListener>
        implements InteractionEnricherListener{

    /**
     * A constructor to create a listener manager with no listeners.
     */
    public InteractionEnricherListenerManager(){ }

    /**
     * A constructor to initiate a listener manager with as many listeners as required.
     * @param listeners     The listeners to add.
     */
    public InteractionEnricherListenerManager(InteractionEnricherListener... listeners){
        super(listeners);
    }

    //============================================================================================

    public void onEnrichmentComplete(Interaction interaction, EnrichmentStatus status, String message) {
        for(InteractionEnricherListener listener : listenersList){
            listener.onEnrichmentComplete(interaction, status, message);
        }
    }
}
