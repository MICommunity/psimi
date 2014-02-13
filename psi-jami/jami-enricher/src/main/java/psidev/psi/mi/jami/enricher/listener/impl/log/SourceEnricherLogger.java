package psidev.psi.mi.jami.enricher.listener.impl.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.SourceEnricherListener;
import psidev.psi.mi.jami.listener.impl.SourceChangeLogger;
import psidev.psi.mi.jami.model.Source;

/**
 * A logging listener. It will display a message when each event is fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 18/07/13
 */
public class SourceEnricherLogger extends SourceChangeLogger implements SourceEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(SourceEnricherLogger.class.getName());

    public void onEnrichmentComplete(Source cvTerm, EnrichmentStatus status, String message) {
        log.info(cvTerm.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(Source object, String message, Exception e) {
        log.error(object.toString()+" enrichment error, message: "+message, e);
    }
}
