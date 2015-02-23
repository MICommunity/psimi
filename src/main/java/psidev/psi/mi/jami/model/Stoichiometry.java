package psidev.psi.mi.jami.model;

/**
 * The stoichiometry of a participant
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface Stoichiometry {

    /**
     * The minimum value for the stoichiometry. If the stoichiometry does not have a minValue because it is a mean value, getMinValue() and getMaxValue()
     * will return the same mean stoichiometry value.
     * @return The minimum value for this stoichiometry if there is one, otherwise the mean stoichiometry value
     */
    public int getMinValue();

    /**
     * The maximum value for the stoichiometry. If the stoichiometry does not have a maxValue because it is a mean value, getMinValue() and getMaxValue()
     * will return the same mean stoichiometry value.
     * @return The maximum value for this stoichiometry if there is one, otherwise the mean stoichiometry value
     */
    public int getMaxValue();
}
