package psidev.psi.mi.jami.utils.comparator.checksum;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Comparator;

/**
 * Simple comparator for checksum.
 * It will first compares the method using a CvTermComparator and then it will compare the checksum values
 *
 * - Two annotations which are null are equals
 * - The annotation which is not null is before null.
 * - use DefaultCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
 * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class ChecksumComparator implements Comparator<Checksum>{

    private Comparator<CvTerm> methodComparator;

    /**
     * Creates a new ChecksumComparator.
     * @param termComparator : comparator for the method which is required
     */
    public ChecksumComparator(Comparator<CvTerm> termComparator){
        if (termComparator == null){
            throw new IllegalArgumentException("The method comparator is required and cannot be null");
        }
        this.methodComparator = termComparator;
    }

    public Comparator<CvTerm> getMethodComparator() {
        return methodComparator;
    }

    /**
     * It will first compares the method using a CvTermComparator and then it will compare the checksum values
     *
     * - Two annotations which are null are equals
     * - The annotation which is not null is before null.
     * - use CvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
     * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
     * @param checksum1 : the first checksum
     * @param checksum2 : the second checksum
     * @return the comparison value
     */
    public int compare(Checksum checksum1, Checksum checksum2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (checksum1 == checksum2){
            return 0;
        }
        else if (checksum1 == null){
            return AFTER;
        }
        else if (checksum2 == null){
            return BEFORE;
        }
        else {
            CvTerm method1 = checksum1.getMethod();
            CvTerm method2 = checksum2.getMethod();

            int comp = methodComparator.compare(method1, method2);
            if (comp != 0){
                return comp;
            }
            // check checksum
            String checksum1Value = checksum1.getValue();
            String checksum2Value = checksum2.getValue();

            if (checksum1Value == null && checksum2Value == null){
                return EQUAL;
            }
            else if (checksum1Value == null){
                return AFTER;
            }
            else if (checksum2Value == null){
                return BEFORE;
            }
            else {

                return checksum1Value.compareTo(checksum2Value);
            }
        }
    }
}
