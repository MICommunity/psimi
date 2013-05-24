package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.VariableParameterValue;

import java.util.Comparator;

/**
 * Simple Comparator for VariableParameterValue
 *
 * It will first compare the value (case insensitive) and then the order.
 * It ignores the variableParameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class VariableParameterValueComparator implements Comparator<VariableParameterValue>{

    private static VariableParameterValueComparator variableParameterValueComparator;

    /**
     * It will first compare the value (case insensitive) and then the order.
     * It ignores the variableParameter
     * @param variableParameterValue1
     * @param variableParameterValue2
     * @return
     */
    public int compare(VariableParameterValue variableParameterValue1, VariableParameterValue variableParameterValue2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (variableParameterValue1 == null && variableParameterValue2 == null){
            return EQUAL;
        }
        else if (variableParameterValue1 == null){
            return AFTER;
        }
        else if (variableParameterValue2 == null){
            return BEFORE;
        }
        else {
            // first compares values
            String value1 = variableParameterValue1.getValue();
            String value2 = variableParameterValue2.getValue();

            int comp;
            comp = value1.toLowerCase().trim().compareTo(value2.toLowerCase().trim());

            if (comp != 0){
               return comp;
            }

            Integer order1 = variableParameterValue1.getOrder();
            Integer order2 = variableParameterValue2.getOrder();

            if (order1 == null && order2 == null){
                return EQUAL;
            }
            else if (order1 == null){
                return AFTER;
            }
            else if (order2 == null){
                return BEFORE;
            }
            else if (order1 < order2){
                return BEFORE;
            }
            else if (order1 > order2){
                return AFTER;
            }
            else {
                return EQUAL;
            }
        }
    }

    /**
     * Use VariableParameterValueComparator to know if two variableParameterValues are equals.
     * @param parameterValue1
     * @param parameterValue2
     * @return true if the two variableParameterValues are equal
     */
    public static boolean areEquals(VariableParameterValue parameterValue1, VariableParameterValue parameterValue2){
        if (variableParameterValueComparator == null){
            variableParameterValueComparator = new VariableParameterValueComparator();
        }

        return variableParameterValueComparator.compare(parameterValue1, parameterValue2) == 0;
    }

    /**
     *
     * @param value
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(VariableParameterValue value){
        if (variableParameterValueComparator == null){
            variableParameterValueComparator = new VariableParameterValueComparator();
        }

        if (value == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + (value.getValue() != null ? value.getValue().trim().toLowerCase().hashCode() : 0);
        hashcode = 31*hashcode + (value.getOrder() != null ? value.getOrder() : 0);

        return hashcode;
    }
}
