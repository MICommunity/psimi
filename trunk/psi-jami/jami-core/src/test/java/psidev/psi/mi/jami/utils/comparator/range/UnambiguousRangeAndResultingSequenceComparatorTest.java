package psidev.psi.mi.jami.utils.comparator.range;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultPosition;
import psidev.psi.mi.jami.model.impl.DefaultRange;
import psidev.psi.mi.jami.model.impl.DefaultResultingSequence;

/**
 * Unit tester for UnambiguousRangeAndResultingSequenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class UnambiguousRangeAndResultingSequenceComparatorTest {


    private UnambiguousRangeAndResultingSequenceComparator comparator = new UnambiguousRangeAndResultingSequenceComparator();

    @Test
    public void test_position_null_after() throws Exception {
        Range range1 = null;
        Range range2 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(3));

        Assert.assertTrue(comparator.compare(range1, range2) > 0);
        Assert.assertTrue(comparator.compare(range2, range1) < 0);

        Assert.assertFalse(UnambiguousRangeAndResultingSequenceComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeAndResultingSequenceComparator.hashCode(range1) != UnambiguousRangeComparator.hashCode(range2));
    }

    @Test
    public void test_position_start_lowest_before() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(2), new DefaultPosition(4));
        Range range2 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4));

        Assert.assertTrue(comparator.compare(range1, range2) < 0);
        Assert.assertTrue(comparator.compare(range2, range1) > 0);

        Assert.assertFalse(UnambiguousRangeAndResultingSequenceComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeAndResultingSequenceComparator.hashCode(range1) != UnambiguousRangeComparator.hashCode(range2));
    }

    @Test
    public void test_position_end_lowest_before() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(5));
        Range range2 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4));

        Assert.assertTrue(comparator.compare(range1, range2) > 0);
        Assert.assertTrue(comparator.compare(range2, range1) < 0);

        Assert.assertFalse(UnambiguousRangeAndResultingSequenceComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeAndResultingSequenceComparator.hashCode(range1) != UnambiguousRangeAndResultingSequenceComparator.hashCode(range2));
    }

    @Test
    public void test_range_link_before() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4));
        Range range2 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4), true);

        Assert.assertTrue(comparator.compare(range1, range2) > 0);
        Assert.assertTrue(comparator.compare(range2, range1) < 0);

        Assert.assertFalse(UnambiguousRangeAndResultingSequenceComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeAndResultingSequenceComparator.hashCode(range1) != UnambiguousRangeAndResultingSequenceComparator.hashCode(range2));
    }

    @Test
    public void test_identical_ranges() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(3,4), new DefaultPosition(4,5), true);
        Range range2 = new DefaultRange(new DefaultPosition(3,4), new DefaultPosition(4,5), true);

        Assert.assertTrue(comparator.compare(range1, range2) == 0);
        Assert.assertTrue(comparator.compare(range2, range1) == 0);

        Assert.assertTrue(UnambiguousRangeAndResultingSequenceComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeAndResultingSequenceComparator.hashCode(range1) == UnambiguousRangeAndResultingSequenceComparator.hashCode(range2));
    }

    @Test
    public void test_range_resulting_sequence_before() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(3,4), new DefaultPosition(4,5), true);
        Range range2 = new DefaultRange(new DefaultPosition(3,4), new DefaultPosition(4,5), true);
        range1.setResultingSequence(new DefaultResultingSequence("AAG", "AAA"));

        Assert.assertTrue(comparator.compare(range1, range2) < 0);
        Assert.assertTrue(comparator.compare(range2, range1) > 0);

        Assert.assertFalse(UnambiguousRangeAndResultingSequenceComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeAndResultingSequenceComparator.hashCode(range1) != UnambiguousRangeAndResultingSequenceComparator.hashCode(range2));
    }

    @Test
    public void test_different_resulting_sequence() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(3,4), new DefaultPosition(4,5), true);
        Range range2 = new DefaultRange(new DefaultPosition(3,4), new DefaultPosition(4,5), true);
        range1.setResultingSequence(new DefaultResultingSequence("AGG", "AAA"));
        range2.setResultingSequence(new DefaultResultingSequence("AAG", "AAA"));

        Assert.assertTrue(comparator.compare(range1, range2) > 0);
        Assert.assertTrue(comparator.compare(range2, range1) < 0);

        Assert.assertFalse(UnambiguousRangeAndResultingSequenceComparator.areEquals(range1, range2));
        Assert.assertTrue(UnambiguousRangeAndResultingSequenceComparator.hashCode(range1) != UnambiguousRangeAndResultingSequenceComparator.hashCode(range2));
    }
}
