package psidev.psi.mi.jami.utils.comparator.cooperativity;

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

public class DefaultMoleculeEffectorComparator extends MoleculeEffectorComparator{

    private static DefaultMoleculeEffectorComparator defaultMoleculeEffectorComparator;

    public DefaultMoleculeEffectorComparator() {
        super(new DefaultModelledParticipantComparator());
    }

    @Override
    public DefaultModelledParticipantComparator getParticipantComparator() {
        return (DefaultModelledParticipantComparator) super.getParticipantComparator();
    }

    /**
     * It is using a DefaultModelledParticipantComparator to compare the molecule
     * @param molecule1
     * @param molecule2
     * @return
     */
    public int compare(MoleculeEffector molecule1, MoleculeEffector molecule2) {
        return super.compare(molecule1, molecule2);
    }

    /**
     * Use DefaultMoleculeEffectorComparator to know if two moleculeEffectors are equals.
     * @param molecule1
     * @param molecule2
     * @return true if the two moleculeEffectors are equal
     */
    public static boolean areEquals(MoleculeEffector molecule1, MoleculeEffector molecule2){
        if (defaultMoleculeEffectorComparator == null){
            defaultMoleculeEffectorComparator = new DefaultMoleculeEffectorComparator();
        }

        return defaultMoleculeEffectorComparator.compare(molecule1, molecule2) == 0;
    }
}
