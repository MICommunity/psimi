package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultExactModelledParticipantComparator;

/**
 * Default exact Comparator for MoleculeEffector.
 *
 * It is using a DefaultExactModelledParticipantComparator to compare the molecule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultExactMoleculeEffectorComparator extends MoleculeEffectorComparator{

    private static DefaultExactMoleculeEffectorComparator defaultExactMoleculeEffectorComparator;

    public DefaultExactMoleculeEffectorComparator() {
        super(new DefaultExactModelledParticipantComparator());
    }

    @Override
    public DefaultExactModelledParticipantComparator getParticipantComparator() {
        return (DefaultExactModelledParticipantComparator) super.getParticipantComparator();
    }

    /**
     * It is using a DefaultExactModelledParticipantComparator to compare the molecule
     * @param molecule1
     * @param molecule2
     * @return
     */
    public int compare(MoleculeEffector molecule1, MoleculeEffector molecule2) {
        return super.compare(molecule1, molecule2);
    }

    /**
     * Use DefaultExactMoleculeEffectorComparator to know if two moleculeEffectors are equals.
     * @param molecule1
     * @param molecule2
     * @return true if the two moleculeEffectors are equal
     */
    public static boolean areEquals(MoleculeEffector molecule1, MoleculeEffector molecule2){
        if (defaultExactMoleculeEffectorComparator == null){
            defaultExactMoleculeEffectorComparator = new DefaultExactMoleculeEffectorComparator();
        }

        return defaultExactMoleculeEffectorComparator.compare(molecule1, molecule2) == 0;
    }
}
