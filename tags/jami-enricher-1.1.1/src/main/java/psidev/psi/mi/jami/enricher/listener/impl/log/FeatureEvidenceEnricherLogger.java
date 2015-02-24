package psidev.psi.mi.jami.enricher.listener.impl.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.FeatureEvidenceEnricherListener;
import psidev.psi.mi.jami.listener.impl.FeatureEvidenceChangeLogger;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 19/07/13
 */
public class FeatureEvidenceEnricherLogger extends FeatureEvidenceChangeLogger
        implements FeatureEvidenceEnricherListener {


    private static final Logger log = LoggerFactory.getLogger(FeatureEvidenceEnricherLogger.class.getName());

    public void onEnrichmentComplete(FeatureEvidence feature, EnrichmentStatus status, String message) {
        log.info(feature.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }

    public void onEnrichmentError(FeatureEvidence object, String message, Exception e) {
        log.error(object.toString()+" enrichment error, message: "+message, e);
    }
}
