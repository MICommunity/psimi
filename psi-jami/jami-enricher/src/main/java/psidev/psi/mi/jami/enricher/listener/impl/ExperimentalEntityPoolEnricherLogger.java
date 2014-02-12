package psidev.psi.mi.jami.enricher.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.ExperimentalEntityPoolEnricherListener;
import psidev.psi.mi.jami.listener.impl.ExperimentalEntityPoolChangeLogger;
import psidev.psi.mi.jami.model.ExperimentalEntityPool;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class ExperimentalEntityPoolEnricherLogger
        extends ExperimentalEntityPoolChangeLogger implements ExperimentalEntityPoolEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(ExperimentalEntityPoolEnricherLogger.class.getName());

    public void onEnrichmentComplete(ExperimentalEntityPool participant, EnrichmentStatus status, String message) {
        log.info(participant.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(ExperimentalEntityPool object, String message, Exception e) {
        log.info(object.toString()+" enrichment error, message: "+message, e);
    }
}
