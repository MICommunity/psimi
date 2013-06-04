package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultPosition;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Positions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class PositionUtils {

    /**
     * Check if a position is undetermined (MI:0339)
     * @param position
     * @return
     */
    public static boolean isUndetermined(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getUndetermined(), status);
    }

    /**
     * Check if a position is N-terminal range (MI:1040)
     * @param position
     * @return
     */
    public static boolean isNTerminalRange(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getNTerminalRange(), status);
    }

    /**
     * Check if a position is C-terminal range (MI:1039)
     * @param position
     * @return
     */
    public static boolean isCTerminalRange(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getCTerminalRange(), status);
    }

    /**
     * Check if a position is N-terminal position (MI:0340)
     * @param position
     * @return
     */
    public static boolean isNTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getNTerminal(), status);
    }

    /**
     * Check if a position is C-terminal position (MI:0334)
     * @param position
     * @return
     */
    public static boolean isCTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getCTerminal(), status);
    }

    /**
     * Check if a position is ragged N-terminal position (MI:0341)
     * @param position
     * @return
     */
    public static boolean isRaggedNTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getNTerminalRagged(), status);
    }

    /**
     * Check if a position is fuzzy range (MI:0338)
     * @param position
     * @return
     */
    public static boolean isFuzzyRange(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getFuzzyRange(), status);
    }

    /**
     * Check if a position is greater-than (MI:0336)
     * @param position
     * @return
     */
    public static boolean isGreaterThan(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getGreaterThan(), status);
    }

    /**
     * Check if a position is less-than (MI:0337)
     * @param position
     * @return
     */
    public static boolean isLessThan(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getLessThan(), status);
    }

    /**
     * Check if a position is certain (MI:0335)
     * @param position
     * @return
     */
    public static boolean isCertain(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getCertain(), status);
    }

    /**
     * Converts a given position in a String using specific mnemonic for encoding the position status
     * @param position
     * @return
     */
    public static String convertPositionToString(Position position){
        if (position == null){
            return Range.UNDETERMINED_POSITION_SYMBOL;
        }

        if (isUndetermined(position)){
            return Range.UNDETERMINED_POSITION_SYMBOL;
        }
        else if (isNTerminalRange(position)){
            return Range.N_TERMINAL_POSITION_SYMBOL;
        }
        else if (isCTerminalRange(position)){
            return Range.C_TERMINAL_POSITION_SYMBOL;
        }
        else if (isGreaterThan(position)){
            return Range.GREATER_THAN_POSITION_SYMBOL+position.getStart();
        }
        else if (isLessThan(position)){
            return Range.LESS_THAN_POSITION_SYMBOL+position.getStart();
        }
        else if (position.getStart() != position.getEnd() || isFuzzyRange(position)){
            return position.getStart()+Range.FUZZY_POSITION_SYMBOL+position.getEnd();
        }
        else {
            return Long.toString(position.getStart());
        }
    }

    public static Position createUndeterminedPosition(){
        return new DefaultPosition(0);
    }

    public static Position createNTerminalRangePosition(){
        return new DefaultPosition(CvTermUtils.createNTerminalRangeStatus(), 0);
    }

    public static Position createCTerminalRangePosition(){
        return new DefaultPosition(CvTermUtils.createCTerminalRangeStatus(), 0);
    }

    public static Position createNTerminalPosition(){
        return new DefaultPosition(CvTermUtils.createNTerminalStatus(), 1);
    }

    public static Position createCTerminalPosition(int lastPosition){
        return new DefaultPosition(CvTermUtils.createCTerminalStatus(), lastPosition);
    }

    public static Position createCertainPosition(int position){
        return new DefaultPosition(position);
    }

    public static Position createGreaterThanPosition(int position){
        return new DefaultPosition(CvTermUtils.createGreaterThanRangeStatus(), position);
    }

    public static Position createLessThanPosition(int position){
        return new DefaultPosition(CvTermUtils.createLessThanRangeStatus(), position);
    }

    public static Position createRaggedNTerminusPosition(int position){
        return new DefaultPosition(CvTermUtils.createRaggedNTerminalStatus(), position);
    }

    public static Position createFuzzyPosition(int position){
        return new DefaultPosition(CvTermUtils.createRangeStatus(), position);
    }

    public static Position createFuzzyPosition(int start, int end){
        return new DefaultPosition(start, end);
    }

    public static Position createPosition(String statusName, String statusMi, int start){
        return new DefaultPosition(CvTermUtils.createMICvTerm(statusName, statusMi), start);
    }

    public static Position createPositionFromString(String pos) throws IllegalRangeException {
        if (pos == null){
            return createUndeterminedPosition();
        }
        // shortcut for n-terminal position
        else if (Range.N_TERMINAL_POSITION_SYMBOL.equals(pos)){
            return createNTerminalRangePosition();
        }
        // shortcut for c-terminal position
        else if (Range.C_TERMINAL_POSITION_SYMBOL.equals(pos)){
            return createCTerminalRangePosition();
        }
        // shortcut for undetermined position
        else if (Range.UNDETERMINED_POSITION_SYMBOL.equals(pos)){
            return createUndeterminedPosition();
        }
        // shortcut for greater than position
        else if (pos.contains(Range.GREATER_THAN_POSITION_SYMBOL)){
            String rangePosition = pos.replace(Range.GREATER_THAN_POSITION_SYMBOL, "");
            return createGreaterThanPosition(convertStringToPositionValue(rangePosition));
        }
        // shortcut for less than position
        else if (pos.contains(Range.LESS_THAN_POSITION_SYMBOL)){
            String rangePosition = pos.replace(Range.LESS_THAN_POSITION_SYMBOL, "");
            return createLessThanPosition(convertStringToPositionValue(rangePosition));
        }
        // shortcut for fuzzy position
        else if (pos.contains(Range.FUZZY_POSITION_SYMBOL)){
            String[] positionString = pos.split("\\.\\.");
            if (positionString.length != 2){
                throw new IllegalRangeException("The fuzzy position " + pos + " is not valid and cannot be converted into a Position.");
            }
            else {
                return createFuzzyPosition(convertStringToPositionValue(positionString[0]), convertStringToPositionValue(positionString[1]));
            }
        }
        // shortcut for certain position
        else {
            int position = convertStringToPositionValue(pos);
            return createCertainPosition(position);
        }
    }

    public static int convertStringToPositionValue(String rangeString) throws IllegalRangeException {
        try{
            int pos = Integer.parseInt(rangeString);
            return pos;
        }
        catch (NumberFormatException e){
            throw new IllegalRangeException("The range " + rangeString + " is not a valid position.");
        }
    }

    /**
     * Check if the positions and the position status are consistent.
     * @param position : the position to check
     * @param sequence : the sequence of the protein
     * @return empty list if the range positions and the position status are consistent. If there are some inconsistencies, it will return a list of error messages
     */
    public static List<String> validateRangePosition(Position position, String sequence){
        // the sequence length is 0 if the sequence is null
        int sequenceLength = 0;

        if (sequence != null){
            sequenceLength = sequence.length();
        }

        List<String> messages = new ArrayList<String>();

        long start = position.getStart();
        long end = position.getEnd();

        // undetermined position, we expect to have a position equal to 0 for both the start and the end
        if (position.isPositionUndetermined()){
            if (start != 0 || end != 0){
                messages.add( "It is not consistent to have a 'undetermined', 'n-terminal region' or 'c-terminal region' range position with a " +
                        "value different from null or 0. Actual position : (" + start + "-"+end+")");
            }
        }
        // n-terminal position : we expect to have a position equal to 1 for both the start and the end
        else if (PositionUtils.isNTerminal(position)){
            if (start != 1 || end != 1){
                messages.add( "It is not consistent to have a 'n-terminal' range position with a " +
                        "value different from 1 because 'n-terminal' means the first amino acid of the participant. Actual position : (" + start + "-"+end+")");
            }
        }
        // c-terminal position : we expect to have a position equal to the sequence length (0 if the sequence is null) for both the start and the end
        else if (PositionUtils.isCTerminal(position)){
            if (sequenceLength == 0 && start != end){
                messages.add( "The range position cannot be negative or null if the position is 'c-terminal'. Actual position : "+ start);
            }
            else if ((start != sequenceLength || end != sequenceLength) && sequenceLength > 0){
                messages.add( "It is not consistent to have a 'c-terminal' range position with a " +
                        "value different from the sequence length because 'c-terminal' means the last amino acid of the participant." +
                        "However, we can accept a position null or 0 if the participant sequence is not known. Actual position : (" + start + "-"+end+"), sequence length: " +sequenceLength);
            }
        }
        // greater than position : we don't expect an interval for this position so the start should be equal to the end
        else if (PositionUtils.isGreaterThan(position)){
            if (start != end) {
                messages.add( "It is not consistent to give the range status 'greater-than'" +
                        " to a range position which is an interval ("+start+"-"+end+")");
            }

            // The sequence is not null, we expect to have positions superior to 0 and STRICTLY inferior to the sequence length
            if (sequenceLength != 0 ? start > sequenceLength-1 || end > sequenceLength-1 : false){
                messages.add( "A range position 'greater-than' must be strictly inferior to the interactor sequence length. Actual position : (" + start + "-"+end+"), sequence length: " +sequenceLength);
            }
        }
        // less than position : we don't expect an interval for this position so the start should be equal to the end
        else if (PositionUtils.isLessThan(position)){
            if (start != end) {
                messages.add( "It is not consistent to give the range status 'greater-than'" +
                        " to a range position which is an interval ("+start+"-"+end+")");            }
            // The sequence is not null, we expect to have positions inferior or equal to the sequence length
            if (areRangePositionsOutOfBounds(start, end, sequenceLength)) {
                messages.add( "A range position 'less-than' must be inferior or equal to the interactor sequence length. Actual position : (" + start + "-"+end+"), sequence length: "+sequenceLength);
            }
        }
        // if the range position is certain or ragged-n-terminus, we expect to have the positions superior to 0 and inferior or
        // equal to the sequence length (only possible to check if the sequence is not null)
        // We don't expect any interval for this position so the start should be equal to the end
        else if (PositionUtils.isCertain(position) || PositionUtils.isRaggedNTerminal(position)){
            if (start != end) {
                messages.add( "It is not consistent to give a range status different from 'range'" +
                        " to a range position which is an interval ("+start+"-"+end+")");
            }

            if (areRangePositionsOutOfBounds(start, end, sequenceLength)){
                messages.add( "This range position is out of bounds. Actual position : (" + start + "-"+end+"), sequence length: " +sequenceLength);
            }
        }
        // the range status is not well known, so we allow the position to be an interval, we just check that the start and end are inferior to the sequence
        // length (only possible to check if the sequence is not null)
        else {
            if (!areRangePositionsValid(start, end)) {
                messages.add( "The range position is invalid : " + start + "-" + end + ". The start cannot be superior to the end");
            } else if (areRangePositionsOutOfBounds(start, end, sequenceLength)){
                messages.add( "This range position is out of bounds. Actual position : (" + start + "-"+end+"), sequence length: " + sequenceLength);
            }
        }

        return messages;
    }

    /**
     * Checks if the interval positions are overlapping
     * @param fromStart
     * @param fromEnd
     * @param toStart
     * @param toEnd
     * @return true if the range intervals are overlapping
     */
    public static boolean arePositionsOverlapping(long fromStart, long fromEnd, long toStart, long toEnd){

        if (fromStart > fromEnd || fromEnd > toStart || toStart > toEnd){
            return true;
        }
        return false;
    }

    /**
     * A position is out of bound if superior to the sequence length. Inferior to 0 is accepted for describing promotor regions
     * @param start : the start position of the interval
     * @param end  : the end position of the interval
     * @param sequenceLength : the length of the sequence, 0 if the sequence is null
     * @return true if the start or the end is superior to the sequence length
     */
    public static boolean areRangePositionsOutOfBounds(long start, long end, int sequenceLength){
        return sequenceLength != 0 ? start > sequenceLength || end > sequenceLength : false;
    }

    /**
     * A range interval is invalid if the start is after the end
     * @param start : the start position of the interval
     * @param end : the end position of the interval
     * @return true if the positions are valid, false otherwise
     */
    public static boolean areRangePositionsValid(long start, long end){

        if (start > end){
            return false;
        }
        return true;
    }
}
