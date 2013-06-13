package psidev.psi.mi.jami.bridges.uniprot.remapping.listener;


import psidev.psi.mi.jami.bridges.uniprot.remapping.RemapReport;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 05/06/13
 * Time: 15:47
 */
public interface RemapListener {

    void onIdentifierConflict(String remappedIdentifierOne, String remappedIdentifierTwo);
    public void onSequenceToIdentifierConflict(String remappedSequence , String remappedIdentifier);

    void onGettingRemappingFromIdentifiers(Protein p);
    void onGettingRemappingFromSequence(Protein p);
    
    void onRemappingComplete(Protein p, String s);


}
