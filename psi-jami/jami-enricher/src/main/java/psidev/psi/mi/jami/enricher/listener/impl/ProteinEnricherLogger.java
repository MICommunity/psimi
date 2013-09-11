package psidev.psi.mi.jami.enricher.listener.impl;

import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.listener.impl.ProteinChangeLogger;
import psidev.psi.mi.jami.model.Protein;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  11/06/13
 */
public class ProteinEnricherLogger
        extends ProteinChangeLogger
        implements ProteinEnricherListener {

    private static final org.slf4j.Logger proteinChangeLogger = LoggerFactory.getLogger(ProteinEnricherLogger.class);

    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
        proteinChangeLogger.info(protein.toString() + " enrichment complete. " +
                "The status was: " + status + ". The message reads: " + message);
    }

    public void onEnrichmentError(Protein object, String message, Exception e) {
        proteinChangeLogger.error(object.toString() + " enrichment error. " +
                "The message reads: " + message, e);
    }
}
