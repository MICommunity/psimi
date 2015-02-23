package psidev.psi.mi.jami.utils.comparator.checksum;

import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous checksum comparator.
 *
 * - Two checksum which are null are equals
 * - The checksum which is not null is before null.
 * - use UnambiguousCvTermComparator to compare the methods. If they are equals, compares the values (case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class UnambiguousChecksumComparator extends ChecksumComparator {

    private static UnambiguousChecksumComparator unambiguousChecksumComparator;

    public UnambiguousChecksumComparator() {
        super(new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousCvTermComparator getMethodComparator() {
        return (UnambiguousCvTermComparator) super.getMethodComparator();
    }

    /**
     * It will first compares the method using a UnambiguousCvTermComparator and then it will compare the checksum values
     *
     * - Two annotations which are null are equals
     * - The annotation which is not null is before null.
     * - use UnambiguousCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
     * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
     * @param checksum1
     * @param checksum2
     * @return
     */
    public int compare(Checksum checksum1, Checksum checksum2){
        return super.compare(checksum1, checksum2);
    }

    /**
     * Use UnambiguousChecksumComparator to know if two checksum are equals.
     * @param checksum1
     * @param checksum2
     * @return true if the two checksum are equal
     */
    public static boolean areEquals(Checksum checksum1, Checksum checksum2){
        if (unambiguousChecksumComparator == null){
            unambiguousChecksumComparator = new UnambiguousChecksumComparator();
        }

        return unambiguousChecksumComparator.compare(checksum1, checksum2) == 0;
    }

    /**
     *
     * @param checksum
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Checksum checksum){
        if (unambiguousChecksumComparator == null){
            unambiguousChecksumComparator = new UnambiguousChecksumComparator();
        }

        if (checksum == null){
            return 0;
        }

        int hashcode = 31;
        CvTerm method = checksum.getMethod();
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(method);

        String value = checksum.getValue();
        hashcode = 31*hashcode + value.hashCode();

        return hashcode;
    }
}
