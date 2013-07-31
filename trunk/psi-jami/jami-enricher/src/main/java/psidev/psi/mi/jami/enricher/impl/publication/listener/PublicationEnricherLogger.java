package psidev.psi.mi.jami.enricher.impl.publication.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Publication;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 31/07/13
 */
public class PublicationEnricherLogger implements PublicationEnricherListener{

    protected static final Logger log = LoggerFactory.getLogger(PublicationEnricherLogger.class.getName());

    public void onPublicationEnriched(Publication publication, EnrichmentStatus status, String message) {
        log.info(publication.toString()+" enrichment complete with status ["+status+"], message: "+message);
    }
}
