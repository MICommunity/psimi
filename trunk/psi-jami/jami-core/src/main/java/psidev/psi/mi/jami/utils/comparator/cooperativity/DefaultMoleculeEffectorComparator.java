package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.ModelledEntity;
import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledEntityComparator;

/**
 * Default Comparator for MoleculeEffector.
 *
 * It is using a DefaultModelledEntityComparator to compare the molecule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultMoleculeEffectorComparator {

    /**
     * Use DefaultMoleculeEffectorComparator to know if two moleculeEffectors are equals.
     * @param moleculeEffector1
     * @param moleculeEffector2
     * @return true if the two moleculeEffectors are equal
     */
    public static boolean areEquals(MoleculeEffector moleculeEffector1, MoleculeEffector moleculeEffector2){
        if (moleculeEffector1 == moleculeEffector2){
            return true;
        }
        else if (moleculeEffector1 == null || moleculeEffector2 == null){
            return false;
        }
        else {

            ModelledEntity molecule1 = moleculeEffector1.getMolecule();
            ModelledEntity molecule2 = moleculeEffector2.getMolecule();

            return DefaultModelledEntityComparator.areEquals(molecule1, molecule2, true);
        }
    }
}
