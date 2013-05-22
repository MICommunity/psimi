package psidev.psi.mi.jami.model;

/**
 * A polymer is an interactor with a sequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/03/13</pre>
 */

public interface Polymer extends Molecule{

    /**
     * The sequence of the polymer. Null if it is not known
     * @return
     */
    public String getSequence();

    /**
     * Sets the sequence of the polymer.
     * @param sequence
     */
    public void setSequence(String sequence);
}
