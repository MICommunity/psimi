package psidev.psi.mi.jami.utils.comparator;

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

    /**
     *
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
            return (value1.getFactor().multiply(BigDecimal.valueOf(value1.getBase() ^ value1.getExponent()))).compareTo(value2.getFactor().multiply(BigDecimal.valueOf(value2.getBase()^value2.getExponent())));
        }
    }
}
