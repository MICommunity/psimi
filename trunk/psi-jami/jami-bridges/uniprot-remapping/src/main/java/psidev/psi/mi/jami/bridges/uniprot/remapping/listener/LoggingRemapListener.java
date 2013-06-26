package psidev.psi.mi.jami.bridges.uniprot.remapping.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapperListener;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/06/13
 * Time: 16:04
 */
public class LoggingRemapListener implements ProteinRemapperListener {

    public static final Log log = LogFactory.getLog(LoggingRemapListener.class);

    public void onIdentifierConflict(IdentificationResults remappedIdentifierOne, IdentificationResults remappedIdentifierTwo) {
        log.info("Conflict found between identifier remappings: " +
                remappedIdentifierOne.getFinalUniprotId()+", "+
                remappedIdentifierTwo.getFinalUniprotId());

    }

    public void onSequenceToIdentifierConflict(IdentificationResults remappedSequenceResult, IdentificationResults remappedIdentifierResult) {
        log.info("Conflict found between sequence remapping " +
                "("+remappedSequenceResult.getFinalUniprotId()+") " +
                "and identifier remapping " +
                "("+remappedSequenceResult.getFinalUniprotId()+").");
    }

    public void onGettingRemappingFromIdentifiers(Protein p, Collection<IdentificationResults> remappedIdentifiersResults) {
        log.info("Remapping is supported by " +
                remappedIdentifiersResults.size()+" identifier(s).");
    }

    public void onGettingRemappingFromSequence(Protein p, IdentificationResults remappedSequenceResult) {
        log.info("Remapping is supported by sequence.");
    }

    public void onRemappingSuccessful(Protein p, String message) {
        log.info("Remapping succeeded: "+message);
    }

    public void onRemappingFailed(Protein p, String message) {
        log.info("Remapping failed: "+message);
    }
}
