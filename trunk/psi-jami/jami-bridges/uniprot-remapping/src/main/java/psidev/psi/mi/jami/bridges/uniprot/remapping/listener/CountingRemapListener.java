package psidev.psi.mi.jami.bridges.uniprot.remapping.listener;

import psidev.psi.mi.jami.bridges.remapper.ProteinRemapperListener;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 12:56
 */
public class CountingRemapListener implements ProteinRemapperListener {

    private int conflictCount = 0;
    private boolean fromIdentifiers = false;
    private boolean fromSequence = false;
    private boolean success = false;
    private boolean failed = false;

    public boolean getSuccess() {
        return success;
    }

    public boolean getFailed(){
        return failed;
    }

    public boolean isFromIdentifiers() {
        return fromIdentifiers;
    }

    public boolean isFromSequence() {
        return fromSequence;
    }

    public int getConflictCount() {
        return conflictCount;
    }



    public void onIdentifierConflict(IdentificationResults remappedIdentifierOne, IdentificationResults remappedIdentifierTwo) {
        conflictCount ++;
    }

    public void onSequenceToIdentifierConflict(IdentificationResults remappedSequenceResult, IdentificationResults remappedIdentifierResult) {
        conflictCount ++;
    }

    public void onGettingRemappingFromIdentifiers(Protein p, Collection<IdentificationResults> remappedIdentifiersResults) {
        fromIdentifiers = true;
    }

    public void onGettingRemappingFromSequence(Protein p, IdentificationResults remappedSequenceResult) {
        fromSequence = true;
    }

    public void onRemappingSuccessful(Protein p, String s) {
        success = true;
    }

    public void onRemappingFailed(Protein p, String s) {
        failed = true;
    }

}
