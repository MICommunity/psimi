package psidev.psi.mi.jami.enricher.listener.impl.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.ComplexEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.impl.ComplexChangeLogger;
import psidev.psi.mi.jami.model.Complex;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13
 */
public class ComplexEnricherLogger
        extends ComplexChangeLogger implements ComplexEnricherListener{

    private static final Logger log = LoggerFactory.getLogger(ComplexChangeLogger.class.getName());

    public void onEnrichmentComplete(Complex interaction, EnrichmentStatus status, String message) {
        log.info(interaction.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(Complex object, String message, Exception e) {
        log.error(object.toString() + " enrichment error, message: " + message, e);
    }
}
