package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.AllostericEffectorType;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.MoleculeEffector;

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
}
