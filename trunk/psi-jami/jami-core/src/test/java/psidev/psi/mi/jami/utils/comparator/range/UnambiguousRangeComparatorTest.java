package psidev.psi.mi.jami.utils.comparator.range;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultPosition;
import psidev.psi.mi.jami.model.impl.DefaultRange;

/**
 * Unit tester for UnambiguousRangeComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class UnambiguousRangeComparatorTest {

    private UnambiguousRangeComparator comparator = new UnambiguousRangeComparator();

    @Test
    public void test_position_null_after() throws Exception {
        Range range1 = null;
        Range range2 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(3));

        Assert.assertTrue(comparator.compare(range1, range2) > 0);
        Assert.assertTrue(comparator.compare(range2, range1) < 0);

        Assert.assertFalse(UnambiguousRangeComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeComparator.hashCode(range1) != UnambiguousRangeComparator.hashCode(range2));
    }

    @Test
    public void test_position_start_lowest_before() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(2), new DefaultPosition(4));
        Range range2 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4));

        Assert.assertTrue(comparator.compare(range1, range2) < 0);
        Assert.assertTrue(comparator.compare(range2, range1) > 0);

        Assert.assertFalse(UnambiguousRangeComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeComparator.hashCode(range1) != UnambiguousRangeComparator.hashCode(range2));
    }

    @Test
    public void test_position_end_lowest_before() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(5));
        Range range2 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4));

        Assert.assertTrue(comparator.compare(range1, range2) > 0);
        Assert.assertTrue(comparator.compare(range2, range1) < 0);

        Assert.assertFalse(UnambiguousRangeComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeComparator.hashCode(range1) != UnambiguousRangeComparator.hashCode(range2));
    }

    @Test
    public void test_range_link_before() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4));
        Range range2 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4), true);

        Assert.assertTrue(comparator.compare(range1, range2) > 0);
        Assert.assertTrue(comparator.compare(range2, range1) < 0);

        Assert.assertFalse(UnambiguousRangeComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeComparator.hashCode(range1) != UnambiguousRangeComparator.hashCode(range2));
    }

    @Test
    public void test_identical_ranges() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(3,4), new DefaultPosition(4,5), true);
        Range range2 = new DefaultRange(new DefaultPosition(3,4), new DefaultPosition(4,5), true);

        Assert.assertTrue(comparator.compare(range1, range2) == 0);
        Assert.assertTrue(comparator.compare(range2, range1) == 0);

        Assert.assertTrue(UnambiguousRangeComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeComparator.hashCode(range1) == UnambiguousRangeComparator.hashCode(range2));
    }
}
