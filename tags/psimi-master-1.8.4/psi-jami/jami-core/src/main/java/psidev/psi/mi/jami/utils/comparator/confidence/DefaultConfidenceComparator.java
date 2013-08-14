package psidev.psi.mi.jami.utils.comparator.confidence;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default confidence comparator.
 * It will compares the confidence types first, then the units and finally the value.

 * - Two confidences which are null are equals
 * - The confidence which is not null is before null.
 * - Use DefaultCvTermComparator to compare first the confidence types.
 * - If confidence types are equals, use DefaultCvTermComparator to compare the units.
 * - If the units are not set, compares the values (case sensitive)
 * - If both units are set and If they are equals, compares the values (case sensitive)
 * - The confidence (same type, same value) with unit which is not null will be before the one with a null unit
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultConfidenceComparator {

    /**
     * Use DefaultConfidenceComparator to know if two confidences are equals.
     * @param confidence1
     * @param confidence2
     * @return true if the two confidences are equal
     */
    public static boolean areEquals(Confidence confidence1, Confidence confidence2){
        if (confidence1 == null && confidence2 == null){
            return true;
        }
        else if (confidence1 == null || confidence2 == null){
            return false;
        }
        else {
            CvTerm type1 = confidence1.getType();
            CvTerm type2 = confidence2.getType();

            if (!DefaultCvTermComparator.areEquals(type1, type2)){
                return false;
            }

            String value1 = confidence1.getValue();
            String value2 = confidence2.getValue();

            return value1.equals(value2);
        }
    }
}
