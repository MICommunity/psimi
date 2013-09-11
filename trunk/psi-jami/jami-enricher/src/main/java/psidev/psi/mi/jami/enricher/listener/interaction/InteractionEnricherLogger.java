package psidev.psi.mi.jami.enricher.listener.interaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Interaction;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class InteractionEnricherLogger
        implements InteractionEnricherListener{

    private static final Logger log = LoggerFactory.getLogger(InteractionEnricherLogger.class.getName());

    public void onEnrichmentComplete(Interaction interaction, EnrichmentStatus status, String message) {
        log.info(interaction.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(Interaction object, String message, Exception e) {
        log.error(object.toString()+" enrichment error, message: "+message, e);
    }
}
