package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.VariableParameter;

/**
 * Utility class for cloning VariableParameters
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/02/13</pre>
 */

public class VariableParameterCloner {

    /***
     * This method will copy properties of parameter source in parameter target (excepted experiment) and will override all the other properties of Target parameter.
     * @param source
     * @param target
     */
    public static void copyAndOverrideVariableParameterProperties(VariableParameter source, VariableParameter target){
        if (source != null && target != null){
            target.setDescription(source.getDescription());
            target.setUnit(source.getUnit());

            // copy collections
            target.getVariableValues().clear();
            target.getVariableValues().addAll(source.getVariableValues());
        }
    }
}
