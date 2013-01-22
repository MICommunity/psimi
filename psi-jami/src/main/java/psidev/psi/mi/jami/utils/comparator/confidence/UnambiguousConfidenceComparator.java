package psidev.psi.mi.jami.utils.comparator.confidence;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous confidence comparator.
 * It will compares the confidence types first, then the units and finally the value.
 *
 * - Two confidences which are null are equals
 * - The confidence which is not null is before null.
 * - Use UnambiguousCvTermComparator to compare first the confidence types.
 * - If confidence types are equals, use UnambiguousCvTermComparator to compare the units.
 * - If the units are not set, compares the values (case sensitive)
 * - If both units are set and If they are equals, compares the values (case sensitive)
 * - The confidence (same type, same value) with unit which is not null will be before the one with a null unit
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class UnambiguousConfidenceComparator extends ConfidenceComparator {

    private static UnambiguousConfidenceComparator unambiguousConfidenceComparator;

    public UnambiguousConfidenceComparator() {
        super(new UnambiguousCvTermComparator());
    }

    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) cvTermComparator;
    }

    /**
     * It will compares the confidence types first, then the units and finally the value.
     *
     * - Two confidences which are null are equals
     * - The confidence which is not null is before null.
     * - Use UnambiguousCvTermComparator to compare first the confidence types.
     * - If confidence types are equals, use UnambiguousCvTermComparator to compare the units.
     * - If the units are not set, compares the values (case sensitive)
     * - If both units are set and If they are equals, compares the values (case sensitive)
     * - The confidence (same type, same value) with unit which is not null will be before the one with a null unit
     *
     * @param confidence1
     * @param confidence2
     * @return
     */
    public int compare(Confidence confidence1, Confidence confidence2) {
        return super.compare(confidence1, confidence2);
    }

    /**
     * Use UnambiguousConfidenceComparator to know if two confidences are equals.
     * @param conf1
     * @param conf2
     * @return true if the two confidences are equal
     */
    public static boolean areEquals(Confidence conf1, Confidence conf2){
        if (unambiguousConfidenceComparator == null){
            unambiguousConfidenceComparator = new UnambiguousConfidenceComparator();
        }

        return unambiguousConfidenceComparator.compare(conf1, conf2) == 0;
    }

    /**
     *
     * @param conf
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Confidence conf){
        if (unambiguousConfidenceComparator == null){
            unambiguousConfidenceComparator = new UnambiguousConfidenceComparator();
        }

        if (conf == null){
            return 0;
        }

        int hashcode = 31;
        CvTerm type = conf.getType();
        hashcode = 31*hashcode + unambiguousConfidenceComparator.getCvTermComparator().hashCode(type);

        String value = conf.getValue();
        hashcode = 31*hashcode + value.hashCode();

        CvTerm unit = conf.getUnit();
        hashcode = 31*hashcode + unambiguousConfidenceComparator.getCvTermComparator().hashCode(unit);

        return hashcode;
    }
}
