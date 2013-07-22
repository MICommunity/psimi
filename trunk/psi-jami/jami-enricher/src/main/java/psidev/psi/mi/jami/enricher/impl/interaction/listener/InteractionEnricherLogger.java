package psidev.psi.mi.jami.enricher.impl.interaction.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Interaction;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class InteractionEnricherLogger
        implements InteractionEnricherListener{

    protected static final Logger log = LoggerFactory.getLogger(InteractionEnricherLogger.class.getName());

    public void onInteractionEnriched(Interaction interaction, EnrichmentStatus status, String message) {
        log.info(interaction.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }
}
