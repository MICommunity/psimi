package psidev.psi.mi.jami.bridges.uniprot.remapping.listener;

import psidev.psi.mi.jami.bridges.remapper.ProteinRemapperListener;
import psidev.psi.mi.jami.model.Protein;

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
    private String status = null;

    public String getStatus() {
        return status;
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



    public void onIdentifierConflict(String remappedIdentifierOne, String remappedIdentifierTwo) {
        conflictCount ++;
    }

    public void onSequenceToIdentifierConflict(String remappedSequence, String remappedIdentifier) {
        conflictCount++;
    }

    public void onGettingRemappingFromIdentifiers(Protein p) {
        fromIdentifiers = true;
    }

    public void onGettingRemappingFromSequence(Protein p) {
        fromSequence = true;
    }

    public void onRemappingComplete(Protein p, String s) {
        status = s;
    }
}
