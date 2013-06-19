package psidev.psi.mi.jami.bridges.uniprot.remapping.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapperListener;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/06/13
 * Time: 16:04
 */
public class LoggingRemapListener implements ProteinRemapperListener {

    public static final Log log = LogFactory.getLog(LoggingRemapListener.class);



    public void onGettingRemappingFromIdentifiers(Protein p) {
        log.info("Remapping is supported by identifier(s).");
    }

    public void onGettingRemappingFromSequence(Protein p) {
        log.info("Remapping is supported by sequence.");
    }

    public void onRemappingComplete(Protein protein, String msg) {
        log.info("Remapping completed: "+msg);
    }

    public void onIdentifierConflict(String remappedIdentifierOne, String remappedIdentifierTwo) {
        log.info("Conflict found between identifier remappings: "+remappedIdentifierOne+", "+remappedIdentifierTwo);
    }

    public void onSequenceToIdentifierConflict(String remappedSequence, String remappedIdentifier) {
        log.info("Conflict found between sequence remapping ("+remappedSequence+") and identifier remapping ("+remappedSequence+").");
    }
}
