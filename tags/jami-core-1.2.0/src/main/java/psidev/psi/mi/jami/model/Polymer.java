package psidev.psi.mi.jami.model;

/**
 * A polymer is an interactor with a sequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/03/13</pre>
 */

public interface Polymer extends Molecule{

    public static final String POLYMER = "biopolymer";
    public static final String POLYMER_MI="MI:0383";

    /**
     * The sequence of the polymer. Null if it is not known
     * @return the sequence
     */
    public String getSequence();

    /**
     * Sets the sequence of the polymer.
     * @param sequence : sequence to set
     */
    public void setSequence(String sequence);
}
