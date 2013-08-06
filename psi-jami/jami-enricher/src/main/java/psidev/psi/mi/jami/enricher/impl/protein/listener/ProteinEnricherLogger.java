package psidev.psi.mi.jami.enricher.impl.protein.listener;

import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.listener.impl.ProteinChangeLogger;
import psidev.psi.mi.jami.model.Protein;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  11/06/13
 */
public class ProteinEnricherLogger
        extends ProteinChangeLogger
        implements  ProteinEnricherListener{

    private static final Logger proteinChangeLogger = Logger.getLogger("ProteinEnricherLogger");


    public void onEnrichmentComplete(Protein protein, EnrichmentStatus status, String message) {
        proteinChangeLogger.log(Level.INFO, "Protein enriching complete. " +
                "The status was: "+status+". The message reads: "+message);
    }

    public void onProteinRemapped(Protein protein, String oldUniprot) {
        proteinChangeLogger.log(Level.INFO, "Protein is remapped. Old value was: "+oldUniprot+". " +
                "New value is: "+protein.getUniprotkb()+".");
    }
}
