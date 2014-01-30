package psidev.psi.mi.jami.enricher.listener.impl;

import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.PolymerEnricherListener;
import psidev.psi.mi.jami.listener.impl.PolymerChangeLogger;
import psidev.psi.mi.jami.model.Polymer;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  11/06/13
 */
public class PolymerEnricherLogger<P extends Polymer>
        extends PolymerChangeLogger<P>
        implements PolymerEnricherListener<P> {

    private static final org.slf4j.Logger proteinChangeLogger = LoggerFactory.getLogger(PolymerEnricherLogger.class);

    public void onEnrichmentComplete(P protein, EnrichmentStatus status, String message) {
        proteinChangeLogger.info(protein.toString() + " enrichment complete. " +
                "The status was: " + status + ". The message reads: " + message);
    }

    public void onEnrichmentError(P object, String message, Exception e) {
        proteinChangeLogger.error(object.toString() + " enrichment error. " +
                "The message reads: " + message, e);
    }
}
