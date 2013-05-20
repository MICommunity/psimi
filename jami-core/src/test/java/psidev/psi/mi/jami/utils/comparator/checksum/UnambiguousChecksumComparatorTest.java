package psidev.psi.mi.jami.utils.comparator.checksum;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Unit tester for UnambiguousChecksumComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class UnambiguousChecksumComparatorTest {

    private UnambiguousChecksumComparator comparator = new UnambiguousChecksumComparator();

    @Test
    public void test_checksum_null_after() throws Exception {
        Checksum checksum1 = null;
        Checksum checksum2 = new DefaultChecksum(CvTermFactory.createRogid(), "xxxxxxxxx");

        Assert.assertTrue(comparator.compare(checksum1, checksum2) > 0);
        Assert.assertTrue(comparator.compare(checksum2, checksum1) < 0);

        Assert.assertFalse(UnambiguousChecksumComparator.areEquals(checksum1, checksum2));
        Assert.assertTrue(UnambiguousChecksumComparator.hashCode(checksum1) != UnambiguousChecksumComparator.hashCode(checksum2));
    }

    @Test
    public void test_checksum_method_comparison() throws Exception {
        Checksum checksum1 = new DefaultChecksum(CvTermFactory.createStandardInchiKey(), "xxxxxxxxx");
        Checksum checksum2 = new DefaultChecksum(CvTermFactory.createRogid(), "xxxxxxxxx");

        Assert.assertTrue(comparator.compare(checksum1, checksum2) != 0);

        Assert.assertFalse(UnambiguousChecksumComparator.areEquals(checksum1, checksum2));
        Assert.assertTrue(UnambiguousChecksumComparator.hashCode(checksum1) != UnambiguousChecksumComparator.hashCode(checksum2));
    }

    @Test
    public void test_checksum_value_comparison() throws Exception {
        Checksum checksum1 = new DefaultChecksum(CvTermFactory.createRogid(), "xxxxxxxxx");
        Checksum checksum2 = new DefaultChecksum(CvTermFactory.createRogid(), "xxxxxxxxx");

        Assert.assertTrue(comparator.compare(checksum1, checksum2) == 0);

        Assert.assertTrue(UnambiguousChecksumComparator.areEquals(checksum1, checksum2));
        Assert.assertTrue(UnambiguousChecksumComparator.hashCode(checksum1) == UnambiguousChecksumComparator.hashCode(checksum2));
    }

    @Test
    public void test_checksum_value_case_sensitive() throws Exception {
        Checksum checksum1 = new DefaultChecksum(CvTermFactory.createRogid(), "xxxXXXxxx");
        Checksum checksum2 = new DefaultChecksum(CvTermFactory.createRogid(), "xxxxxxxxx");

        Assert.assertTrue(comparator.compare(checksum1, checksum2) != 0);

        Assert.assertFalse(UnambiguousChecksumComparator.areEquals(checksum1, checksum2));
        Assert.assertTrue(UnambiguousChecksumComparator.hashCode(checksum1) != UnambiguousChecksumComparator.hashCode(checksum2));
    }
}
