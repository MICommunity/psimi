package psidev.psi.mi.jami.utils.comparator.parameter;

import psidev.psi.mi.jami.model.ParameterValue;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Comparator for ParameterValue.
 *
 * It compares the BigDecimal value of factor.multiply(BigDecimal.valueOf(base^exponent))
 * If both values are null, they are equals.
 * The value that is not null comes before the one that is null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class ParameterValueComparator implements Comparator<ParameterValue>{

    private static ParameterValueComparator parameterValueComparator;

    /**
     * It compares the BigDecimal value of factor.multiply(BigDecimal.valueOf(base^exponent))
     * If both values are null, they are equals.
     * The value that is not null comes before the one that is null.
     * @param value1
     * @param value2
     * @return
     */
    public int compare(ParameterValue value1, ParameterValue value2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (value1 == null && value2 == null){
            return EQUAL;
        }
        else if (value1 == null){
            return AFTER;
        }
        else if (value2 == null){
            return BEFORE;
        }
        else {
            return (value1.getFactor().multiply(BigDecimal.valueOf(Math.pow(value1.getBase(), value1.getExponent())))).compareTo(value2.getFactor().multiply(BigDecimal.valueOf(Math.pow(value2.getBase(), value2.getExponent()))));
        }
    }

    /**
     * Use ParameterValueComparator to know if two ParameterValue are equals.
     * @param value1
     * @param value2
     * @return true if the two parameter values are equal
     */
    public static boolean areEquals(ParameterValue value1, ParameterValue value2){
        if (parameterValueComparator == null){
            parameterValueComparator = new ParameterValueComparator();
        }

        return parameterValueComparator.compare(value1, value2) == 0;
    }

    /**
     *
     * @param param
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(ParameterValue param){
        if (parameterValueComparator == null){
            parameterValueComparator = new ParameterValueComparator();
        }

        if (param == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + (param.getFactor().multiply(BigDecimal.valueOf(Math.pow(param.getBase(), param.getExponent())))).hashCode();

        return hashcode;
    }
}
