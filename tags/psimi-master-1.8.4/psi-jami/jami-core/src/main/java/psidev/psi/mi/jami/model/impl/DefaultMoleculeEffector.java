package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.AllostericEffectorType;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.MoleculeEffector;

/**
 * Default implementation for MoleculeEffector
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the MoleculeEffector object is a complex object.
 * To compare MoleculeEffector objects, you can use some comparators provided by default:
 * - DefaultMoleculeEffectorComparator
 * - UnambiguousMoleculeEffectorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class DefaultMoleculeEffector implements MoleculeEffector {

    private ModelledParticipant participant;

    public DefaultMoleculeEffector(ModelledParticipant participant){
        if (participant == null){
            throw new IllegalArgumentException("The participant of a MoleculeEffector cannot be null.");
        }
        this.participant = participant;
    }

    public ModelledParticipant getMolecule() {
        return participant;
    }

    public AllostericEffectorType getEffectorType() {
        return AllostericEffectorType.molecule;
    }

    @Override
    public String toString() {
        return "molecule effector: " + participant.toString();
    }
}
