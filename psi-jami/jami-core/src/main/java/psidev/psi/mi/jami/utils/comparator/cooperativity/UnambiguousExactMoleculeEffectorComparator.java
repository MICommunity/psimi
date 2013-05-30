package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactModelledParticipantComparator;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactParticipantBaseComparator;

/**
 * Unambiguous exact Comparator for MoleculeEffector.
 *
 * It is using a UnambiguousExactModelledParticipantComparator to compare the molecule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousExactMoleculeEffectorComparator extends MoleculeEffectorComparator{

    private static UnambiguousExactMoleculeEffectorComparator unambiguousExactMoleculeEffectorComparator;

    public UnambiguousExactMoleculeEffectorComparator() {
        super(new UnambiguousExactModelledParticipantComparator());
    }

    @Override
    public UnambiguousExactModelledParticipantComparator getParticipantComparator() {
        return (UnambiguousExactModelledParticipantComparator) super.getParticipantComparator();
    }

    /**
     * It is using a UnambiguousExactModelledParticipantComparator to compare the molecule
     * @param molecule1
     * @param molecule2
     * @return
     */
    public int compare(MoleculeEffector molecule1, MoleculeEffector molecule2) {
        return super.compare(molecule1, molecule2);
    }

    /**
     * Use UnambiguousExactMoleculeEffectorComparator to know if two moleculeEffectors are equals.
     * @param molecule1
     * @param molecule2
     * @return true if the two moleculeEffectors are equal
     */
    public static boolean areEquals(MoleculeEffector molecule1, MoleculeEffector molecule2){
        if (unambiguousExactMoleculeEffectorComparator == null){
            unambiguousExactMoleculeEffectorComparator = new UnambiguousExactMoleculeEffectorComparator();
        }

        return unambiguousExactMoleculeEffectorComparator.compare(molecule1, molecule2) == 0;
    }

    /**
     *
     * @param effector
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(MoleculeEffector effector){
        if (unambiguousExactMoleculeEffectorComparator == null){
            unambiguousExactMoleculeEffectorComparator = new UnambiguousExactMoleculeEffectorComparator();
        }

        if (effector == null){
            return 0;
        }

        int hashcode = 31;
        ModelledParticipant molecule = effector.getMolecule();
        hashcode = 31*hashcode + UnambiguousExactParticipantBaseComparator.hashCode(molecule);
        return hashcode;
    }
}
