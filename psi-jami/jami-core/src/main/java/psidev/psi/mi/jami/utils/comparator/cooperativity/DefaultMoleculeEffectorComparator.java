package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultModelledParticipantComparator;

/**
 * Default Comparator for MoleculeEffector.
 *
 * It is using a DefaultModelledParticipantComparator to compare the molecule
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
        if (moleculeEffector1 == null && moleculeEffector2 == null){
            return true;
        }
        else if (moleculeEffector1 == null || moleculeEffector2 == null){
            return false;
        }
        else {

            ModelledParticipant molecule1 = moleculeEffector1.getMolecule();
            ModelledParticipant molecule2 = moleculeEffector2.getMolecule();

            return DefaultModelledParticipantComparator.areEquals(molecule1, molecule2, true);
        }
    }
}
