package psidev.psi.mi.jami.enricher.listener.impl.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.ParticipantEvidencePoolEnricherListener;
import psidev.psi.mi.jami.listener.impl.ParticipantEvidencePoolChangeLogger;
import psidev.psi.mi.jami.model.ParticipantEvidencePool;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class ParticipantEvidencePoolEnricherLogger
        extends ParticipantEvidencePoolChangeLogger implements ParticipantEvidencePoolEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(ParticipantEvidencePoolEnricherLogger.class.getName());

    public void onEnrichmentComplete(ParticipantEvidencePool participant, EnrichmentStatus status, String message) {
        log.info(participant.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(ParticipantEvidencePool object, String message, Exception e) {
        log.info(object.toString()+" enrichment error, message: "+message, e);
    }
}
