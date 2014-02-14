package psidev.psi.mi.jami.enricher.listener.impl.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.listener.impl.EntityChangeLogger;
import psidev.psi.mi.jami.model.Entity;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class ParticipantEnricherLogger<P extends Entity>
        extends EntityChangeLogger<P> implements ParticipantEnricherListener<P> {

    private static final Logger log = LoggerFactory.getLogger(ParticipantEnricherLogger.class.getName());

    public void onEnrichmentComplete(P participant, EnrichmentStatus status, String message) {
        log.info(participant.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(P object, String message, Exception e) {
        log.info(object.toString()+" enrichment error, message: "+message, e);
    }
}
