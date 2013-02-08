package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Range;

/**
 * Unit tester for DefaultRange
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class DefaultRangeTest {

    @Test
    public void test_create_unlinked_range(){
        Range range1 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4));

        Assert.assertEquals(new DefaultPosition(3), range1.getStart());
        Assert.assertEquals(new DefaultPosition(4), range1.getEnd());
        Assert.assertFalse(range1.isLink());
    }

    @Test
    public void test_create_range_undetermined_end(){
        Range range1 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(0));

        Assert.assertEquals(new DefaultPosition(3), range1.getStart());
        Assert.assertEquals(new DefaultPosition(0), range1.getEnd());
        Assert.assertFalse(range1.isLink());
    }

    @Test
    public void test_create_linked_range(){
        Range range1 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4), true);

        Assert.assertEquals(new DefaultPosition(3), range1.getStart());
        Assert.assertEquals(new DefaultPosition(4), range1.getEnd());
        Assert.assertTrue(range1.isLink());
    }

    @Test
    public void test_set_range_positions(){
        Range range1 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4));
        range1.setPositions(new DefaultPosition(6), new DefaultPosition(8));
        Assert.assertEquals(new DefaultPosition(6), range1.getStart());
        Assert.assertEquals(new DefaultPosition(8), range1.getEnd());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_set_bad_range_positions(){
        Range range1 = new DefaultRange(new DefaultPosition(3), new DefaultPosition(4));
        range1.setPositions(new DefaultPosition(8), new DefaultPosition(6));
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_range_no_start() throws Exception {
        Range range1 = new DefaultRange(null, new DefaultPosition(4));
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_range_no_end() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(4), null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_invalid_range() throws Exception {
        Range range1 = new DefaultRange(new DefaultPosition(5), new DefaultPosition(4));
    }
}
