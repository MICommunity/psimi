package psidev.psi.mi.jami.model;

/**
 * A value for a specific variableParameter in a specific experiment - eg - the concentration of a specific drug.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface VariableParameterValue {

    /**
     * The value for the variableParameterValue (drug concentration, etc.)
     * It cannot be null.
     * @return the value of the variableParameterValue
     */
    public String getValue();

    /**
     * The decimal order for this variableParameterValue.
     * It can be null if the VariableParameterValueComparator does not have any order.
     * @return the decimal order
     */
    public Integer getOrder();

    /**
     * The variableParameter reporting this VariableParameterValueComparator.
     * It can be null if the variableParameterValue is not attached to any variableParameter
     * @return the variableParameter reporting the variableParameterValue
     */
    public VariableParameter getVariableParameter();
}
