package psidev.psi.mi.jami.enricher.impl.participant.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class ParticipantEnricherLogger
        implements  ParticipantEnricherListener{

    protected static final Logger log = LoggerFactory.getLogger(ParticipantEnricherLogger.class.getName());

    public void onParticipantEnriched(Participant participant, EnrichmentStatus status, String message) {
        log.info(participant.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }
}
