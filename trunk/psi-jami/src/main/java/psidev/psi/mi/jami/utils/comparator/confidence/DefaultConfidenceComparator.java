package psidev.psi.mi.jami.utils.comparator.confidence;

import psidev.psi.mi.jami.model.Confidence;
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

public class DefaultConfidenceComparator extends ConfidenceComparator {

    private static DefaultConfidenceComparator defaultConfidenceComparator;

    public DefaultConfidenceComparator() {
        super(new DefaultCvTermComparator());
    }

    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) cvTermComparator;
    }

    /**
     * It will compares the confidence types first, then the units and finally the value.
     *
     * - Two confidences which are null are equals
     * - The confidence which is not null is before null.
     * - Use DefaultCvTermComparator to compare first the confidence types.
     * - If confidence types are equals, use DefaultCvTermComparator to compare the units.
     * - If the units are not set, compares the values (case sensitive)
     * - If both units are set and If they are equals, compares the values (case sensitive)
     * - The confidence (same type, same value) with unit which is not null will be before the one with a null unit
     * @param confidence1
     * @param confidence2
     * @return
     */
    public int compare(Confidence confidence1, Confidence confidence2) {
        return super.compare(confidence1, confidence2);
    }

    /**
     * Use DefaultConfidenceComparator to know if two confidences are equals.
     * @param conf1
     * @param conf2
     * @return true if the two confidences are equal
     */
    public static boolean areEquals(Confidence conf1, Confidence conf2){
        if (defaultConfidenceComparator == null){
            defaultConfidenceComparator = new DefaultConfidenceComparator();
        }

        return defaultConfidenceComparator.compare(conf1, conf2) == 0;
    }
}
