package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Parameter;

/**
 * Utility class for parameters
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class ParameterUtils {

    public static String getParameterValueAsString(Parameter param){
        if (param == null){
            return null;
        }
        return param.getValue().toString() + (param.getUncertainty() != null ? " ~" + param.getUncertainty().toString() : "");
    }
}
