package psidev.psi.mi.jami.utils.comparator.experiment;

import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.model.VariableParameterValueSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Simple comparator for VariableParameterValueSet
 *
 * It will use VariableParameterValueComparator to compare each variableParameterValue independently from the order.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class VariableParameterValueSetComparator implements Comparator<VariableParameterValueSet> {

    private static VariableParameterValueSetComparator variableParameterValueSetComparator;
    private VariableParameterValueCollectionComparator variableParameterValueCollectionComparator;

    /**
     * Creates a new variableParameterValue CollectionComparator.
     *
     */
    public VariableParameterValueSetComparator() {
       this.variableParameterValueCollectionComparator = new VariableParameterValueCollectionComparator() ;
    }

    public VariableParameterValueCollectionComparator getVariableParameterValueCollectionComparator() {
        return variableParameterValueCollectionComparator;
    }


    /**
     * Use VariableParameterValueComparator to know if two variableParameterValueSet are equals.
     * @param set1
     * @param set2
     * @return true if the two variableParameterValueSet are equal
     */
    public static boolean areEquals(VariableParameterValueSet set1, VariableParameterValueSet set2){
        if (variableParameterValueSetComparator == null){
            variableParameterValueSetComparator = new VariableParameterValueSetComparator();
        }

        return variableParameterValueSetComparator.compare(set1, set2) == 0;
    }

    /**
     *
     * @param set
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(VariableParameterValueSet set){
        if (variableParameterValueSetComparator == null){
            variableParameterValueSetComparator = new VariableParameterValueSetComparator();
        }

        if (set == null){
            return 0;
        }

        int hashcode = 31;
        List<VariableParameterValue> list1 = new ArrayList<VariableParameterValue>(set);
        Collections.sort(list1, variableParameterValueSetComparator.getVariableParameterValueCollectionComparator().getObjectComparator());
        for (VariableParameterValue value : list1){
            hashcode = 31*hashcode + VariableParameterValueComparator.hashCode(value);
        }

        return hashcode;
    }

    public int compare(VariableParameterValueSet variableParameterValues1, VariableParameterValueSet variableParameterValues2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (variableParameterValues1 == null && variableParameterValues2 == null){
            return EQUAL;
        }
        else if (variableParameterValues1 == null){
            return AFTER;
        }
        else if (variableParameterValues2 == null){
            return BEFORE;
        }
        else {

            return variableParameterValueCollectionComparator.compare(variableParameterValues1, variableParameterValues2);
        }
    }
}
