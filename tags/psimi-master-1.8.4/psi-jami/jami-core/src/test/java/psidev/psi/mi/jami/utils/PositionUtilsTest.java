package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultPosition;

/**
 * Unit tester for PositionUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class PositionUtilsTest {

    @Test
    public void test_recognize_position_status(){

        Assert.assertTrue(PositionUtils.isGreaterThan(new DefaultPosition(CvTermUtils.createMICvTerm(Position.GREATER_THAN, Position.GREATER_THAN_MI), 2)));
        Assert.assertTrue(PositionUtils.isLessThan(new DefaultPosition(CvTermUtils.createMICvTerm(Position.LESS_THAN, Position.LESS_THAN_MI), 2)));
        Assert.assertTrue(PositionUtils.isCTerminal(new DefaultPosition(CvTermUtils.createMICvTerm(Position.C_TERMINAL, Position.C_TERMINAL_MI), 2)));
        Assert.assertTrue(PositionUtils.isCTerminalRange(new DefaultPosition(CvTermUtils.createMICvTerm(Position.C_TERMINAL_RANGE, Position.C_TERMINAL_RANGE_MI), 0)));
        Assert.assertTrue(PositionUtils.isCertain(new DefaultPosition(CvTermUtils.createMICvTerm(Position.CERTAIN, Position.CERTAIN_MI), 2)));
        Assert.assertTrue(PositionUtils.isNTerminal(new DefaultPosition(CvTermUtils.createMICvTerm(Position.N_TERMINAL, Position.N_TERMINAL_MI), 1)));
        Assert.assertTrue(PositionUtils.isNTerminalRange(new DefaultPosition(CvTermUtils.createMICvTerm(Position.N_TERMINAL_RANGE, Position.N_TERMINAL_RANGE_MI), 0)));
        Assert.assertTrue(PositionUtils.isRaggedNTerminal(new DefaultPosition(CvTermUtils.createMICvTerm(Position.RAGGED_N_TERMINAL, Position.RAGGED_N_TERMINAL_MI), 2)));
        Assert.assertTrue(PositionUtils.isUndetermined(new DefaultPosition(CvTermUtils.createMICvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), 0)));
        Assert.assertTrue(PositionUtils.isFuzzyRange(new DefaultPosition(CvTermUtils.createMICvTerm(Position.RANGE, Position.RANGE_MI), 2)));
    }

    @Test
    public void test_convert_position_to_string(){

        Assert.assertEquals(Range.UNDETERMINED_POSITION_SYMBOL, PositionUtils.convertPositionToString(null));
        Assert.assertEquals(Range.UNDETERMINED_POSITION_SYMBOL, PositionUtils.convertPositionToString(PositionUtils.createUndeterminedPosition()));
        Assert.assertEquals(Range.N_TERMINAL_POSITION_SYMBOL, PositionUtils.convertPositionToString(PositionUtils.createNTerminalRangePosition()));
        Assert.assertEquals(Range.C_TERMINAL_POSITION_SYMBOL, PositionUtils.convertPositionToString(PositionUtils.createCTerminalRangePosition()));
        Assert.assertEquals(Range.GREATER_THAN_POSITION_SYMBOL+"2", PositionUtils.convertPositionToString(PositionUtils.createGreaterThanPosition(2)));
        Assert.assertEquals(Range.LESS_THAN_POSITION_SYMBOL+"2", PositionUtils.convertPositionToString(PositionUtils.createLessThanPosition(2)));
        Assert.assertEquals("2", PositionUtils.convertPositionToString(PositionUtils.createCertainPosition(2)));
        Assert.assertEquals("2..5", PositionUtils.convertPositionToString(PositionUtils.createFuzzyPosition(2, 5)));
        Assert.assertEquals("2..2", PositionUtils.convertPositionToString(PositionUtils.createFuzzyPosition(2)));
    }

    @Test
    public void test_convert_string_to_position() throws IllegalRangeException {

        Assert.assertEquals(PositionUtils.createUndeterminedPosition(), PositionUtils.createPositionFromString(null));
        Assert.assertEquals(PositionUtils.createUndeterminedPosition(), PositionUtils.createPositionFromString(Range.UNDETERMINED_POSITION_SYMBOL));
        Assert.assertEquals(PositionUtils.createNTerminalRangePosition(), PositionUtils.createPositionFromString(Range.N_TERMINAL_POSITION_SYMBOL));
        Assert.assertEquals(PositionUtils.createCTerminalRangePosition(), PositionUtils.createPositionFromString(Range.C_TERMINAL_POSITION_SYMBOL));
        Assert.assertEquals(PositionUtils.createGreaterThanPosition(2), PositionUtils.createPositionFromString(Range.GREATER_THAN_POSITION_SYMBOL+"2"));
        Assert.assertEquals(PositionUtils.createLessThanPosition(2), PositionUtils.createPositionFromString(Range.LESS_THAN_POSITION_SYMBOL+"2"));
        Assert.assertEquals(PositionUtils.createCertainPosition(2), PositionUtils.createPositionFromString("2"));
        Assert.assertEquals(PositionUtils.createFuzzyPosition(2, 5), PositionUtils.createPositionFromString("2..5"));
        Assert.assertEquals(PositionUtils.createFuzzyPosition(2),PositionUtils.createPositionFromString("2..2"));
    }

    @Test
    public void test_invalid_positions() throws IllegalRangeException {

        Assert.assertFalse(PositionUtils.areRangePositionsValid(5, 2));
    }

    @Test
    public void test_range_positions_out_of_bounds() throws IllegalRangeException {

        Assert.assertTrue(PositionUtils.areRangePositionsOutOfBounds(2, 67, 60));
        Assert.assertTrue(PositionUtils.areRangePositionsOutOfBounds(67, 2, 60));
        Assert.assertFalse(PositionUtils.areRangePositionsOutOfBounds(-2, 59, 60));
    }

    @Test
    public void test_overlapping_positions() throws IllegalRangeException {

        Assert.assertTrue(PositionUtils.arePositionsOverlapping(2, 4, 3, 5));
        Assert.assertTrue(PositionUtils.arePositionsOverlapping(4, 2 , 5, 6));
        Assert.assertTrue(PositionUtils.arePositionsOverlapping(2,4 , 6, 5));
        Assert.assertFalse(PositionUtils.arePositionsOverlapping(2,2 , 4, 4));
        Assert.assertFalse(PositionUtils.arePositionsOverlapping(2,7 , 8, 9));
    }

    @Test
    public void test_invalid_undetermined_ranges_with_positions_different_from_0() throws IllegalRangeException {

        // valid undetermined position
        Position pos1 = new DefaultPosition(0);
        // valid N-terminal range
        Position pos2 = new DefaultPosition(CvTermUtils.createNTerminalRangeStatus(), 0);
        // valid C-terminal range
        Position pos3 = new DefaultPosition(CvTermUtils.createCTerminalRangeStatus(), 0);
        // invalid undetermined position
        Position pos4 = new DefaultPosition(CvTermUtils.createUndeterminedStatus(), 4);
        // invalid N terminal range
        Position pos5 = new DefaultPosition(CvTermUtils.createNTerminalRangeStatus(), 1);
        // invalid C terminal range
        Position pos6 = new DefaultPosition(CvTermUtils.createCTerminalRangeStatus(), 20);

        Assert.assertTrue(PositionUtils.validateRangePosition(pos1, null).isEmpty());
        Assert.assertTrue(PositionUtils.validateRangePosition(pos2, null).isEmpty());
        Assert.assertTrue(PositionUtils.validateRangePosition(pos3, null).isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos4, null).size());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos5, null).size());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos6, null).size());
    }

    @Test
    public void test_invalid_N_terminal(){

        // valid N-terminal position
        Position pos1 = new DefaultPosition(CvTermUtils.createNTerminalStatus(), 1);
        // invalid N-terminal position (pos > 1)
        Position pos2 = new DefaultPosition(CvTermUtils.createNTerminalStatus(), 2);
        // invalid N-terminal position (end > 1)
        Position pos3 = new DefaultPosition(CvTermUtils.createNTerminalStatus(), 1, 4);

        Assert.assertTrue(PositionUtils.validateRangePosition(pos1, null).isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos2, null).size());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos3, null).size());
    }

    @Test
    public void test_invalid_C_terminal(){

        // valid C-terminal position if sequence null
        Position pos1 = new DefaultPosition(CvTermUtils.createCTerminalStatus(), 0);
        // valid C terminal position
        Position pos2 = new DefaultPosition(CvTermUtils.createCTerminalStatus(), 5);
        // invalid C-terminal position (pos < sequence length)
        Position pos3 = new DefaultPosition(CvTermUtils.createCTerminalStatus(), 3);
        // invalid C-terminal position (start != end)
        Position pos4 = new DefaultPosition(CvTermUtils.createCTerminalStatus(), 3, 5);

        Assert.assertTrue(PositionUtils.validateRangePosition(pos1, null).isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos1, "AAGTT").size());
        Assert.assertTrue(PositionUtils.validateRangePosition(pos2, "AAGTT").isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos3, "AAGTT").size());
        Assert.assertTrue(PositionUtils.validateRangePosition(pos3, "AAG").isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos4, null).size());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos4, "AAGTT").size());
    }

    @Test
    public void test_invalid_greater_than_position(){

        // valid greater-than position
        Position pos1 = new DefaultPosition(CvTermUtils.createGreaterThanRangeStatus(), 3);
        // invalid greater-than position position (pos > 1)
        Position pos2 = new DefaultPosition(CvTermUtils.createGreaterThanRangeStatus(), 5);
        // invalid greater-than position position (start!=end)
        Position pos3 = new DefaultPosition(CvTermUtils.createGreaterThanRangeStatus(), 1, 4);

        Assert.assertTrue(PositionUtils.validateRangePosition(pos1, null).isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos2, "AAGTT").size());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos3, "AAGTT").size());
    }

    @Test
    public void test_invalid_less_than_position(){

        // valid less-than position
        Position pos1 = new DefaultPosition(CvTermUtils.createLessThanRangeStatus(), 3);
        // invalid less-than position position (pos > sequence length)
        Position pos2 = new DefaultPosition(CvTermUtils.createLessThanRangeStatus(), 7);
        // invalid less-than position position (start!=end)
        Position pos3 = new DefaultPosition(CvTermUtils.createLessThanRangeStatus(), 1, 4);

        Assert.assertTrue(PositionUtils.validateRangePosition(pos1, null).isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos2, "AAGTT").size());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos3, "AAGTT").size());
    }

    @Test
    public void test_invalid_certain_or_ragged_n_terminal_position(){

        // valid certain position
        Position pos1 = new DefaultPosition(CvTermUtils.createCertainStatus(), 3);
        // invalid certain position position (pos > sequence length)
        Position pos2 = new DefaultPosition(CvTermUtils.createCertainStatus(), 7);
        // invalid certain position position (start!=end)
        Position pos3 = new DefaultPosition(CvTermUtils.createCertainStatus(), 1, 4);
        // valid ragged position
        Position pos4 = new DefaultPosition(CvTermUtils.createRaggedNTerminalStatus(), 3);
        // invalid ragged position position (pos > sequence length)
        Position pos5 = new DefaultPosition(CvTermUtils.createRaggedNTerminalStatus(), 7);
        // invalid ragged position position (start!=end)
        Position pos6 = new DefaultPosition(CvTermUtils.createRaggedNTerminalStatus(), 1, 4);

        Assert.assertTrue(PositionUtils.validateRangePosition(pos1, null).isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos2, "AAGTT").size());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos3, "AAGTT").size());
        Assert.assertTrue(PositionUtils.validateRangePosition(pos4, null).isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos5, "AAGTT").size());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos6, "AAGTT").size());
    }

    @Test
    public void test_invalid_fuzzy_ranges(){

        // valid fuzzy position
        Position pos1 = new DefaultPosition(CvTermUtils.createRangeStatus(), 3);
        // valid fuzzy position2
        Position pos2 = new DefaultPosition(CvTermUtils.createRangeStatus(), 3,4);
        // invalid fuzzy position position (pos > sequence length)
        Position pos3 = new DefaultPosition(CvTermUtils.createRangeStatus(), 7);

        Assert.assertTrue(PositionUtils.validateRangePosition(pos1, null).isEmpty());
        Assert.assertTrue(PositionUtils.validateRangePosition(pos2, null).isEmpty());
        Assert.assertEquals(1, PositionUtils.validateRangePosition(pos3, "AAGTT").size());
    }
}
