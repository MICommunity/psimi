package psidev.psi.mi.jami.enricher.enricherimplementation.protein.listener;

import psidev.psi.mi.jami.listener.ProteinChangeListener;
import psidev.psi.mi.jami.listener.impl.ProteinChangeLogger;
import psidev.psi.mi.jami.model.Protein;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 11/06/13
 * Time: 11:09
 */
public class ProteinEnricherLogger
        extends ProteinChangeLogger
        implements  ProteinEnricherListener{

    private static final Logger proteinChangeLogger = Logger.getLogger("ProteinEnricherLogger");

    public void onProteinEnriched(Protein protein, String status) {
        proteinChangeLogger.log(Level.INFO, "Protein enriching complete. The status was: "+status);
    }
}
