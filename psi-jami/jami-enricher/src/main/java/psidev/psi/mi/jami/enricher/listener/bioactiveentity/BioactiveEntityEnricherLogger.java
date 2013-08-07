package psidev.psi.mi.jami.enricher.listener.bioactiveentity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * A logging listener. It will display a message when each event is fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class BioactiveEntityEnricherLogger
        implements BioactiveEntityEnricherListener{

    protected static final Logger log = LoggerFactory.getLogger(BioactiveEntityEnricherLogger.class.getName());

    public void onEnrichmentComplete(BioactiveEntity object, EnrichmentStatus status, String message) {
        log.info(object.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }
}
