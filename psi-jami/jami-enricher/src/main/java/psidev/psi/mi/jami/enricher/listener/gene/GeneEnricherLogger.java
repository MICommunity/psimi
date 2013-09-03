package psidev.psi.mi.jami.enricher.listener.gene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Gene;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 03/09/13
 */
public class GeneEnricherLogger
        implements GeneEnricherListener{

    protected static final Logger log = LoggerFactory.getLogger(GeneEnricherLogger.class.getName());

    public void onEnrichmentComplete(Gene object, EnrichmentStatus status, String message) {
        log.info(object.toString()+" enrichment complete with status of "+status+", message: "+message);
    }
}
