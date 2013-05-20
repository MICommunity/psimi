package psidev.psi.mi.jami.utils.comparator.range;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.impl.DefaultPosition;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Unit tester for UnambiguousPositionComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class UnambiguousPositionComparatorTest {

    private UnambiguousPositionComparator comparator = new UnambiguousPositionComparator();

    @Test
    public void test_position_null_after() throws Exception {
        Position pos1 = null;
        Position pos2 = new DefaultPosition(CvTermFactory.createUndeterminedStatus(), 0);

        Assert.assertTrue(comparator.compare(pos1, pos2) > 0);
        Assert.assertTrue(comparator.compare(pos2, pos1) < 0);

        Assert.assertFalse(UnambiguousPositionComparator.areEquals(pos1, pos2));
        Assert.assertTrue(UnambiguousPositionComparator.hashCode(pos1) != UnambiguousPositionComparator.hashCode(pos2));
    }

    @Test
    public void test_position_status_comparison() throws Exception {
        Position pos1 = new DefaultPosition(CvTermFactory.createNTerminalRangeStatus(), 0);
        Position pos2 = new DefaultPosition(CvTermFactory.createUndeterminedStatus(), 0);

        Assert.assertTrue(comparator.compare(pos1, pos2) != 0);
        Assert.assertTrue(comparator.compare(pos2, pos1) != 0);

        Assert.assertFalse(UnambiguousPositionComparator.areEquals(pos1, pos2));
        Assert.assertTrue(UnambiguousPositionComparator.hashCode(pos1) != UnambiguousPositionComparator.hashCode(pos2));
    }

    @Test
    public void test_position_same_undetermined_status_ignore_start_end() throws Exception {
        Position pos1 = new DefaultPosition(CvTermFactory.createUndeterminedStatus(), 3);
        Position pos2 = new DefaultPosition(CvTermFactory.createUndeterminedStatus(), 0);

        Assert.assertTrue(comparator.compare(pos1, pos2) == 0);
        Assert.assertTrue(comparator.compare(pos2, pos1) == 0);

        Assert.assertTrue(UnambiguousPositionComparator.areEquals(pos1, pos2));
        Assert.assertTrue(UnambiguousPositionComparator.hashCode(pos1) == UnambiguousPositionComparator.hashCode(pos2));
    }

    @Test
    public void test_position_same_nTerminalrange_status_ignore_start_end() throws Exception {
        Position pos1 = new DefaultPosition(CvTermFactory.createNTerminalRangeStatus(), 3);
        Position pos2 = new DefaultPosition(CvTermFactory.createNTerminalRangeStatus(), 0);

        Assert.assertTrue(comparator.compare(pos1, pos2) == 0);
        Assert.assertTrue(comparator.compare(pos2, pos1) == 0);

        Assert.assertTrue(UnambiguousPositionComparator.areEquals(pos1, pos2));
        Assert.assertTrue(UnambiguousPositionComparator.hashCode(pos1) == UnambiguousPositionComparator.hashCode(pos2));
    }

    @Test
    public void test_position_same_CTerminalRange_status_ignore_start_end() throws Exception {
        Position pos1 = new DefaultPosition(CvTermFactory.createCTerminalRangeStatus(), 3);
        Position pos2 = new DefaultPosition(CvTermFactory.createCTerminalRangeStatus(), 0);

        Assert.assertTrue(comparator.compare(pos1, pos2) == 0);
        Assert.assertTrue(comparator.compare(pos2, pos1) == 0);

        Assert.assertTrue(UnambiguousPositionComparator.areEquals(pos1, pos2));
        Assert.assertTrue(UnambiguousPositionComparator.hashCode(pos1) == UnambiguousPositionComparator.hashCode(pos2));
    }

    @Test
    public void test_position_same_status_lowest_start_before() throws Exception {
        Position pos1 = new DefaultPosition(3, 4);
        Position pos2 = new DefaultPosition(2, 4);

        Assert.assertTrue(comparator.compare(pos1, pos2) > 0);
        Assert.assertTrue(comparator.compare(pos2, pos1) < 0);

        Assert.assertFalse(UnambiguousPositionComparator.areEquals(pos1, pos2));
        Assert.assertTrue(UnambiguousPositionComparator.hashCode(pos1) != UnambiguousPositionComparator.hashCode(pos2));
    }

    @Test
    public void test_position_same_status_lowest_end_before() throws Exception {
        Position pos1 = new DefaultPosition(2, 3);
        Position pos2 = new DefaultPosition(2, 4);

        Assert.assertTrue(comparator.compare(pos1, pos2) < 0);
        Assert.assertTrue(comparator.compare(pos2, pos1) > 0);

        Assert.assertFalse(UnambiguousPositionComparator.areEquals(pos1, pos2));
        Assert.assertTrue(UnambiguousPositionComparator.hashCode(pos1) != UnambiguousPositionComparator.hashCode(pos2));
    }

    @Test
    public void test_position_same_status_same_start_end() throws Exception {
        Position pos1 = new DefaultPosition(CvTermFactory.createCertainStatus(), 3);
        Position pos2 = new DefaultPosition(CvTermFactory.createCertainStatus(), 3);

        Assert.assertTrue(comparator.compare(pos1, pos2) == 0);
        Assert.assertTrue(comparator.compare(pos2, pos1) == 0);

        Assert.assertTrue(UnambiguousPositionComparator.areEquals(pos1, pos2));
        Assert.assertTrue(UnambiguousPositionComparator.hashCode(pos1) == UnambiguousPositionComparator.hashCode(pos2));
    }
}
