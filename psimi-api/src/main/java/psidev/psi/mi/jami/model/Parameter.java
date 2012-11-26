package psidev.psi.mi.jami.model;

import java.math.BigDecimal;

/**
 * A numeric parameter e.g. for a kinetic value
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Parameter {

    /**
     * The parameter type is a controlled vocabulary term and it cannot be null.
     * @return the parameter type
     */
    public CvTerm getType();

    /**
     * The uncertainty of the parameter. By default is null
     * @return the uncertainty of the parameter.
     */
    public BigDecimal getUncertainty();

    /**
     * Unit of the parameter is a controlled vocabulary term. It can be null
     * @return the unit
     */
    public CvTerm getUnit();

    /**
     * The parameter value cannot be null
     * @return the parameter value
     */
    public ParameterValue getValue();
}
