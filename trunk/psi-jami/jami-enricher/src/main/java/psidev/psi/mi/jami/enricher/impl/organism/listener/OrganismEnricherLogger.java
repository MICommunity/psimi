package psidev.psi.mi.jami.enricher.impl.organism.listener;


import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.impl.OrganismChangeLogger;
import psidev.psi.mi.jami.model.Organism;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 21/06/13
 */
public class OrganismEnricherLogger
        extends OrganismChangeLogger
        implements OrganismEnricherListener {


    private static final Logger organismChangeLogger = Logger.getLogger("ProteinEnricherLogger");


    public void onEnrichmentComplete(Organism organism, EnrichmentStatus status, String message) {
        organismChangeLogger.log(Level.INFO, "Organism enriching complete. " +
                "The status was: "+status+". Additional info: "+message);
    }
}
