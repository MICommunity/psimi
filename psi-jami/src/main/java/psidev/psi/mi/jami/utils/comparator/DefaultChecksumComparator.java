package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Checksum;

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

public class DefaultChecksumComparator extends ChecksumComparator {

    private static DefaultChecksumComparator defaultChecksumComparator;

    public DefaultChecksumComparator() {
        super(new DefaultCvTermComparator());
    }

    @Override
    public DefaultCvTermComparator getMethodComparator() {
        return (DefaultCvTermComparator) methodComparator;
    }

    /**
     * It will first compares the method using a DefaultCvTermComparator and then it will compare the checksum values
     *
     * - Two annotations which are null are equals
     * - The annotation which is not null is before null.
     * - use DefaultCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
     * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
     * @param checksum1
     * @param checksum2
     * @return
     */
    public int compare(Checksum checksum1, Checksum checksum2){
        return super.compare(checksum1, checksum2);
    }

    /**
     * Use DefaultChecksumComparator to know if two checksum are equals.
     * @param checksum1
     * @param checksum2
     * @return true if the two checksum are equal
     */
    public static boolean areEquals(Checksum checksum1, Checksum checksum2){
        if (defaultChecksumComparator == null){
            defaultChecksumComparator = new DefaultChecksumComparator();
        }

        return defaultChecksumComparator.compare(checksum1, checksum2) == 0;
    }
}
