package psidev.psi.mi.jami.model;

/**
 * A Confidence gives information about how reliable an object is.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Confidence {

    /**
     * Method used to compute the confidence value.
     * The confidence type is a controlled vocabulary term and it cannot be null.
     * @return the confidence type
     */
    public CvTerm getType();

    /**
     * The confidence value cannot be null. It can be a numerical or literal value
     * @return the confidence value
     */
    public String getValue();

    /**
     * The unit of a confidence can be null.
     * @return The unit of the value if it exists.
     */
    public CvTerm getUnit();
}
