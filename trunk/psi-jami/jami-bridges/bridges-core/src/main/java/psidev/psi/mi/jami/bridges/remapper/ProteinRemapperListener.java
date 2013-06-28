package psidev.psi.mi.jami.bridges.remapper;


import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 05/06/13
 */
public interface ProteinRemapperListener {

    void onIdentifierConflict(
            IdentificationResults remappedIdentifierOne, IdentificationResults remappedIdentifierTwo);

    public void onSequenceToIdentifierConflict(
            IdentificationResults remappedSequenceResult , IdentificationResults remappedIdentifierResult);

    public void onGettingRemappingFromIdentifiers(Protein p , Collection<IdentificationResults> remappedIdentifiersResults);

    public void onGettingRemappingFromSequence(Protein p , IdentificationResults remappedSequenceResult);

    public void onRemappingSuccessful(Protein p, String message);
    public void onRemappingFailed(Protein p, String message);


}
