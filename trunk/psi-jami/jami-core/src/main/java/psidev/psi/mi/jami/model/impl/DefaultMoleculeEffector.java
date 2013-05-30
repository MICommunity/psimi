package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.AllostericEffectorType;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.utils.comparator.cooperativity.UnambiguousExactMoleculeEffectorComparator;

/**
 * Default implementation for MoleculeEffector
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class DefaultMoleculeEffector implements MoleculeEffector{

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
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof MoleculeEffector)){
            return false;
        }

        return UnambiguousExactMoleculeEffectorComparator.areEquals(this, (MoleculeEffector) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousExactMoleculeEffectorComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return "molecule effector: " + participant.toString();
    }
}
