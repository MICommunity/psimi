package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.ModelledEntity;
import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.utils.comparator.participant.ModelledEntityComparator;

import java.util.Comparator;

/**
 * Basic Comparator for MoleculeEffector.
 * 
 * It is using a ModelledEntityComparator to compare the molecule
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class MoleculeEffectorComparator implements Comparator<MoleculeEffector>{

    private ModelledEntityComparator participantComparator;

    public MoleculeEffectorComparator(ModelledEntityComparator participantComparator){

        if (participantComparator == null){
            throw new IllegalArgumentException("The ModelledParticipantComparator is required to compare the molecules of the moleculeEffector");
        }
        this.participantComparator = participantComparator;
    }

    public ModelledEntityComparator getParticipantComparator() {
        return participantComparator;
    }

    /**
     * It is using a ModelledEntityComparator to compare the molecule
     * @param moleculeEffector1
     * @param moleculeEffector2
     * @return
     */
    public int compare(MoleculeEffector moleculeEffector1, MoleculeEffector moleculeEffector2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (moleculeEffector1 == moleculeEffector2){
            return 0;
        }
        else if (moleculeEffector1 == null){
            return AFTER;
        }
        else if (moleculeEffector2 == null){
            return BEFORE;
        }
        else {

            ModelledEntity molecule1 = moleculeEffector1.getMolecule();
            ModelledEntity molecule2 = moleculeEffector2.getMolecule();

            return participantComparator.compare(molecule1, molecule2);
        }
    }
}
