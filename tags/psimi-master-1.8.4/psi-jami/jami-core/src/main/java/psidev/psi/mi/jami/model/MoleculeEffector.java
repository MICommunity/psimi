package psidev.psi.mi.jami.model;

/**
 * The participant that elicits an allosteric response in an allosteric molecule upon binding to that molecule.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface MoleculeEffector extends AllostericEffector{

    /**
     * Refers to the participant that elicits an allosteric response in an allosteric molecule upon binding to that molecule.
     * It cannot be null.
     * @return the participant that elicits the allosteric response.
     */
    public ModelledParticipant getMolecule();
}
