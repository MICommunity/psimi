package psidev.psi.mi.jami.utils.comparator.checksum;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default checksum comparator.
 *
 * - Two checksum which are null are equals
 * - The checksum which is not null is before null.
 * - use DefaultCvTermComparator to compare the methods. If they are equals, compares the values (case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultChecksumComparator {

    /**
     * Use DefaultChecksumComparator to know if two checksum are equals.
     * @param checksum1
     * @param checksum2
     * @return true if the two checksum are equal
     */
    public static boolean areEquals(Checksum checksum1, Checksum checksum2){
        if (checksum1 == null && checksum2 == null){
            return true;
        }
        else if (checksum1 == null || checksum2 == null){
            return false;
        }
        else {
            CvTerm method1 = checksum1.getMethod();
            CvTerm method2 = checksum2.getMethod();

            if (!DefaultCvTermComparator.areEquals(method1, method2)){
                return false;
            }
            // check checksum
            String checksum1Value = checksum1.getValue();
            String checksum2Value = checksum2.getValue();

            if (checksum1Value == null && checksum2Value == null){
                return true;
            }
            else if (checksum1Value == null || checksum2 == null){
                return false;
            }
            else {

                return checksum1Value.equals(checksum2Value);
            }
        }
    }
}
