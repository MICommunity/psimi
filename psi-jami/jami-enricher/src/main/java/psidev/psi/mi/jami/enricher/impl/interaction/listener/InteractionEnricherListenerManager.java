package psidev.psi.mi.jami.enricher.impl.interaction.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Interaction;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionEnricherListenerManager
        extends EnricherListenerManager<InteractionEnricherListener>
        implements InteractionEnricherListener{


    public void onInteractionEnriched(Interaction interaction, EnrichmentStatus status, String message) {
        for(InteractionEnricherListener listener : listenersList){
            listener.onInteractionEnriched(interaction, status, message);
        }
    }
}
