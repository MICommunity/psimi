package uk.ac.ebi.intact.jami.imex.listener.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.PublicationEnricherListener;
import psidev.psi.mi.jami.listener.impl.PublicationChangeLogger;
import psidev.psi.mi.jami.model.Publication;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class PublicationEnricherLogger extends PublicationChangeLogger implements PublicationEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(psidev.psi.mi.jami.enricher.listener.impl.log.PublicationEnricherLogger.class.getName());

    public void onEnrichmentComplete(Publication publication, EnrichmentStatus status, String message) {
        log.info(publication.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(Publication object, String message, Exception e) {
        log.error(object.toString() + " enrichment error, message: " + message, e);    }
}
