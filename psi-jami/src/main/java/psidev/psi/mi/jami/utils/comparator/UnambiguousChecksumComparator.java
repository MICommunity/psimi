package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Checksum;

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
        return (UnambiguousCvTermComparator) methodComparator;
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
}
