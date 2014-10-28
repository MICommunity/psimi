package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for DefaultPosition
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class DefaultPositionTest {

    @Test
    public void test_create_undetermined(){
        Position pos = new DefaultPosition(CvTermUtils.createUndeterminedStatus(), 3);

        Assert.assertEquals(CvTermUtils.createUndeterminedStatus(), pos.getStatus());
        Assert.assertTrue(pos.getStart() == pos.getEnd());
        Assert.assertTrue(pos.getStart() == 3);
        Assert.assertTrue(pos.isPositionUndetermined());
    }

    @Test
    public void test_create_undetermined_default_constructor(){
        Position pos = new DefaultPosition(0);

        Assert.assertEquals(CvTermUtils.createUndeterminedStatus(), pos.getStatus());
        Assert.assertTrue(pos.getStart() == pos.getEnd());
        Assert.assertTrue(pos.getStart() == 0);
        Assert.assertTrue(pos.isPositionUndetermined());
    }

    @Test
    public void test_create_nTerminalRange(){
        Position pos = new DefaultPosition(CvTermUtils.createNTerminalRangeStatus(), 3);

        Assert.assertEquals(CvTermUtils.createNTerminalRangeStatus(), pos.getStatus());
        Assert.assertTrue(pos.getStart() == pos.getEnd());
        Assert.assertTrue(pos.getStart() == 3);
        Assert.assertTrue(pos.isPositionUndetermined());
    }

    @Test
    public void test_create_cTerminalRange(){
        Position pos = new DefaultPosition(CvTermUtils.createCTerminalRangeStatus(), 3);

        Assert.assertEquals(CvTermUtils.createCTerminalRangeStatus(), pos.getStatus());
        Assert.assertTrue(pos.getStart() == pos.getEnd());
        Assert.assertTrue(pos.getStart() == 3);
        Assert.assertTrue(pos.isPositionUndetermined());
    }

    @Test
    public void test_create_certain_unique_position(){
        Position pos = new DefaultPosition(CvTermUtils.createCertainStatus(), 3);

        Assert.assertEquals(CvTermUtils.createCertainStatus(), pos.getStatus());
        Assert.assertTrue(pos.getStart() == pos.getEnd());
        Assert.assertTrue(pos.getStart() == 3);
        Assert.assertFalse(pos.isPositionUndetermined());
    }

    @Test
    public void test_create_certain_unique_position_default_constructor(){
        Position pos = new DefaultPosition( 3);

        Assert.assertEquals(CvTermUtils.createCertainStatus(), pos.getStatus());
        Assert.assertTrue(pos.getStart() == pos.getEnd());
        Assert.assertTrue(pos.getStart() == 3);
        Assert.assertFalse(pos.isPositionUndetermined());
    }

    @Test
    public void test_create_range_position(){
        Position pos = new DefaultPosition(2, 3);

        Assert.assertEquals(CvTermUtils.createRangeStatus(), pos.getStatus());
        Assert.assertTrue(pos.getEnd() == 3);
        Assert.assertTrue(pos.getStart() == 2);
        Assert.assertFalse(pos.isPositionUndetermined());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_position_no_status() throws Exception {
        Position position = new DefaultPosition(null, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_invalid_range() throws Exception {
        Position position = new DefaultPosition(2, 1);
    }
}
