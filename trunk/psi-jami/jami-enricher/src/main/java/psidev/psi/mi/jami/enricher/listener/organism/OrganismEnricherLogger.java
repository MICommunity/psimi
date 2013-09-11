package psidev.psi.mi.jami.enricher.listener.organism;


import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.impl.OrganismChangeLogger;
import psidev.psi.mi.jami.model.Organism;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 21/06/13
 */
public class OrganismEnricherLogger
        extends OrganismChangeLogger
        implements OrganismEnricherListener {


    private static final org.slf4j.Logger organismChangeLogger = LoggerFactory.getLogger(OrganismEnricherLogger.class);

    public void onEnrichmentComplete(Organism organism, EnrichmentStatus status, String message) {
        organismChangeLogger.info(organism.toString()+" enrichment complete " +
                "The status was: "+status+". Additional info: "+message);
    }

    public void onEnrichmentError(Organism object, String message, Exception e) {
        organismChangeLogger.error(object.toString()+" enrichment error " +
                "Error message : " + message, e);
    }
}
