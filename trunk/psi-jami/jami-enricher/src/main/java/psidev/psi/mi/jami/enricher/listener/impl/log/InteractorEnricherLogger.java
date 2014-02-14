package psidev.psi.mi.jami.enricher.listener.impl.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.listener.impl.InteractorChangeLogger;
import psidev.psi.mi.jami.model.*;

/**
 * Logger of interactor enrichment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class InteractorEnricherLogger<T extends Interactor> extends InteractorChangeLogger<T> implements InteractorEnricherListener<T> {

    private static final Logger log = LoggerFactory.getLogger(InteractorEnricherLogger.class.getName());

    public void onEnrichmentComplete(T object, EnrichmentStatus status, String message) {
        log.info("Bioactive entity "+object.toString()+" has been enriched with status of "+status+", message: "+message);
    }

    public void onEnrichmentError(T object, String message, Exception e) {
        log.error("Enrichment error for bioactive entity "+object.toString()+", message: "+message, e);
    }
}