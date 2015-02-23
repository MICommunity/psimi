package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultRange;

/**
 * Unit tester for RangeUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class RangeUtilsTest {

    @Test
    public void test_convert_range_to_string(){
        Range range1 = null;
        Range undetermined = RangeUtils.createUndeterminedRange();
        Range nTerminal = RangeUtils.createNTerminalRange();
        Range cTerminal = RangeUtils.createCTerminalRange();
        Range certain = RangeUtils.createCertainRange(4);
        Range certain2 = RangeUtils.createCertainRange(4, 6);
        Range fuzzy = RangeUtils.createFuzzyRange(4);
        Range fuzzy2 = RangeUtils.createFuzzyRange(4, 6);
        Range greaterThan = RangeUtils.createGreaterThanRange(4);
        Range lessThan = RangeUtils.createLessThanRange(4);

        Assert.assertNull(RangeUtils.convertRangeToString(range1));
        Assert.assertEquals("?-?", RangeUtils.convertRangeToString(undetermined));
        Assert.assertEquals("n-n", RangeUtils.convertRangeToString(nTerminal));
        Assert.assertEquals("c-c", RangeUtils.convertRangeToString(cTerminal));
        Assert.assertEquals("4-4", RangeUtils.convertRangeToString(certain));
        Assert.assertEquals("4-6", RangeUtils.convertRangeToString(certain2));
        Assert.assertEquals("4..4-4..4", RangeUtils.convertRangeToString(fuzzy));
        Assert.assertEquals("4..4-6..6", RangeUtils.convertRangeToString(fuzzy2));
        Assert.assertEquals(">4->4", RangeUtils.convertRangeToString(greaterThan));
        Assert.assertEquals("<4-<4", RangeUtils.convertRangeToString(lessThan));
    }

    @Test
    public void test_create_range_from_string() throws IllegalRangeException {
        Range range1 = null;
        Range undetermined = RangeUtils.createUndeterminedRange();
        Range nTerminal = RangeUtils.createNTerminalRange();
        Range cTerminal = RangeUtils.createCTerminalRange();
        Range certain = RangeUtils.createCertainRange(4);
        Range certain2 = RangeUtils.createCertainRange(4, 6);
        Range certain3 = RangeUtils.createLinkedCertainRange(4, 6);
        Range fuzzy = RangeUtils.createFuzzyRange(4);
        Range fuzzy2 = RangeUtils.createFuzzyRange(4, 6);
        Range greaterThan = RangeUtils.createGreaterThanRange(4);
        Range lessThan = RangeUtils.createLessThanRange(4);

        Assert.assertEquals(undetermined, RangeUtils.createRangeFromString(null, false));
        Assert.assertEquals(undetermined, RangeUtils.createRangeFromString("?-?"));
        Assert.assertEquals(nTerminal, RangeUtils.createRangeFromString("n-n"));
        Assert.assertEquals(cTerminal, RangeUtils.createRangeFromString("c-c"));
        Assert.assertEquals(certain, RangeUtils.createRangeFromString("4-4"));
        Assert.assertEquals(certain2, RangeUtils.createRangeFromString("4-6"));
        Assert.assertEquals(certain3, RangeUtils.createRangeFromString("4-6", true));
        Assert.assertEquals(fuzzy, RangeUtils.createRangeFromString("4..4-4..4"));
        Assert.assertEquals(fuzzy2, RangeUtils.createRangeFromString("4..4-6..6"));
        Assert.assertEquals(greaterThan, RangeUtils.createRangeFromString(">4->4"));
        Assert.assertEquals(lessThan, RangeUtils.createRangeFromString("<4-<4"));
    }

    @Test
    public void test_range_status_inconsistent(){

        Range invalid_c_terminal = new DefaultRange(PositionUtils.createCTerminalPosition(20), PositionUtils.createCertainPosition(22));
        Range valid_c_terminal = new DefaultRange(PositionUtils.createCTerminalPosition(20), PositionUtils.createCTerminalPosition(20));
        Range valid_n_terminal = new DefaultRange(PositionUtils.createNTerminalPosition(), PositionUtils.createNTerminalPosition());
        Range invalid_n_terminal = new DefaultRange(PositionUtils.createCertainPosition(1), PositionUtils.createNTerminalPosition());
        Range invalid_c_terminal_range = new DefaultRange(PositionUtils.createCTerminalRangePosition(), PositionUtils.createCertainPosition(22));
        Range valid_c_terminal_range1 = new DefaultRange(PositionUtils.createCTerminalRangePosition(), PositionUtils.createCTerminalRangePosition());
        Range valid_c_terminal_range2 = new DefaultRange(PositionUtils.createCTerminalRangePosition(), PositionUtils.createCTerminalPosition(22));
        Range invalid_n_terminal_range = new DefaultRange(PositionUtils.createCertainPosition(1), PositionUtils.createNTerminalRangePosition());
        Range valid_n_terminal_range1 = new DefaultRange(PositionUtils.createNTerminalPosition(), PositionUtils.createNTerminalRangePosition());
        Range valid_n_terminal_range2 = new DefaultRange(PositionUtils.createNTerminalRangePosition(), PositionUtils.createNTerminalRangePosition());

        Assert.assertTrue(RangeUtils.areRangeStatusInconsistent(invalid_c_terminal));
        Assert.assertFalse(RangeUtils.areRangeStatusInconsistent(valid_c_terminal));
        Assert.assertTrue(RangeUtils.areRangeStatusInconsistent(invalid_n_terminal));
        Assert.assertFalse(RangeUtils.areRangeStatusInconsistent(valid_n_terminal));
        Assert.assertTrue(RangeUtils.areRangeStatusInconsistent(invalid_c_terminal_range));
        Assert.assertFalse(RangeUtils.areRangeStatusInconsistent(valid_c_terminal_range1));
        Assert.assertFalse(RangeUtils.areRangeStatusInconsistent(valid_c_terminal_range2));
        Assert.assertTrue(RangeUtils.areRangeStatusInconsistent(invalid_n_terminal_range));
        Assert.assertFalse(RangeUtils.areRangeStatusInconsistent(valid_n_terminal_range1));
        Assert.assertFalse(RangeUtils.areRangeStatusInconsistent(valid_n_terminal_range2));
    }

    @Test
    public void test_range_overlapping(){

        Range invalid_greater_start_less_end = new DefaultRange(PositionUtils.createGreaterThanPosition(4), PositionUtils.createLessThanPosition(5));
        Range valid_greater_start_less_end = new DefaultRange(PositionUtils.createGreaterThanPosition(4), PositionUtils.createLessThanPosition(6));
        Range invalid_greater_start_different_end = new DefaultRange(PositionUtils.createGreaterThanPosition(4), PositionUtils.createCertainPosition(4));
        Range valid_greater_start_different_end = new DefaultRange(PositionUtils.createGreaterThanPosition(4), PositionUtils.createCertainPosition(5));
        Range invalid_different_start_less_end = new DefaultRange(PositionUtils.createCertainPosition(4), PositionUtils.createLessThanPosition(4));
        Range valid_different_start_less_end = new DefaultRange(PositionUtils.createCertainPosition(3), PositionUtils.createLessThanPosition(4));
        Range valid_certain_start_undetermined_end = new DefaultRange(PositionUtils.createUndeterminedPosition(), PositionUtils.createCertainPosition(4));
        Range valid_undetermined_start_certain_end = new DefaultRange(PositionUtils.createCertainPosition(4), PositionUtils.createUndeterminedPosition());

        Assert.assertTrue(RangeUtils.areRangePositionsOverlapping(invalid_greater_start_less_end));
        Assert.assertFalse(RangeUtils.areRangePositionsOverlapping(valid_greater_start_less_end));
        Assert.assertTrue(RangeUtils.areRangePositionsOverlapping(invalid_greater_start_different_end));
        Assert.assertFalse(RangeUtils.areRangePositionsOverlapping(valid_greater_start_different_end));
        Assert.assertTrue(RangeUtils.areRangePositionsOverlapping(invalid_different_start_less_end));
        Assert.assertFalse(RangeUtils.areRangePositionsOverlapping(valid_different_start_less_end));
        Assert.assertFalse(RangeUtils.areRangePositionsOverlapping(valid_certain_start_undetermined_end));
        Assert.assertFalse(RangeUtils.areRangePositionsOverlapping(valid_undetermined_start_certain_end));
    }
}
