package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/03/13</pre>
 */

public class FeatureUtils {

    /**
     * Method to check if the range is valid or not. If the range is valid, the method returns null otherwise it returns a message.
     * @param range : the range to check
     * @param sequence : the sequence of the polymer
     * @return empty list if the range is within the sequence, coherent with its fuzzy type and not overlapping. If the range is not valid, it will return a list of error messages describing why the range is invalid
     */
    public static List<String> validateRange(Range range, String sequence){

        if (range != null) {

            Position start = range.getStart();
            Position end = range.getEnd();

            List<String> messages = validateRangePosition(start, sequence);

            messages.addAll(validateRangePosition(end, sequence));

            if (areRangeStatusInconsistent(start, end)){
                messages.add("The start status "+start.getStatus().getShortName()  +" and end status "+end.getStatus().getShortName()+" are inconsistent");
            }

            if (!(start.isPositionUndetermined()) && !(end.isPositionUndetermined()) && areRangePositionsOverlapping(range, start.getStart(), start.getEnd(), end.getStart(), end.getEnd())){
                messages.add("The range positions overlap : ("+start.getStart()+"-"+start.getEnd()+") - ("+end.getStart()+"-"+end.getEnd()+")");
            }

            return messages;
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * A position is out of bound if inferior or equal to 0 or superior to the sequence length.
     * @param start : the start position of the interval
     * @param end  : the end position of the interval
     * @param sequenceLength : the length of the sequence, 0 if the sequence is null
     * @return true if the start or the end is inferior or equal to 0 and if the start or the end is superior to the sequence length
     */
    private static boolean areRangePositionsOutOfBounds(long start, long end, int sequenceLength){
        return start <= 0 || end <= 0 || start > sequenceLength || end > sequenceLength;
    }

    /**
     * A range interval is invalid if the start is after the end
     * @param start : the start position of the interval
     * @param end : the end position of the interval
     * @return true if the start is after the end
     */
    private static boolean areRangePositionsInvalid(long start, long end){

        if (start > end){
            return true;
        }
        return false;
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
                messages.add( "It is not consistent to have a range position with a " +
                        "range status 'undetermined', 'n-terminal region' or 'c-terminal region' and a value different from null or 0. Actual position : (" + start + "-"+end+")");
            }
        }
        // n-terminal position : we expect to have a position equal to 1 for both the start and the end
        else if (PositionUtils.isNTerminal(position)){
            if (start != 1 || end != 1){
                messages.add( "It is not consistent to have a range position with a " +
                        "range status 'n-terminal' and a value different from 1 because 'n-terminal' means the first amino acid of the participant. Actual position : (" + start + "-"+end+")");
            }
        }
        // c-terminal position : we expect to have a position equal to the sequence length (0 if the sequence is null) for both the start and the end
        else if (PositionUtils.isCTerminal(position)){
            if (sequenceLength == 0 && (start <= 0 ||end <= 0 || start != end)){
                messages.add( "The range position cannot be negative or null if the position is 'c-terminal'. Actual position : "+ start);
            }
            else if ((start != sequenceLength || end != sequenceLength) && sequenceLength > 0){
                messages.add( "It is not consistent to have a range position with a " +
                        "range status 'c-terminal' and a value different from the sequence length because 'c-terminal' means the last amino acid of the participant." +
                        "However, we can accept a position null or 0 if the participant sequence is not known. Actual position : (" + start + "-"+end+")");
            }
        }
        // greater than position : we don't expect an interval for this position so the start should be equal to the end
        else if (PositionUtils.isGreaterThan(position)){
            if (start != end) {
                messages.add( "It is not consistent to give the range status 'greater-than'" +
                        " to a range position which is an interval ("+start+"-"+end+")");
            }

            // The sequence is null, all we can expect is at least a start superior to 0.
            if (sequenceLength == 0){
                if (start <= 0 ){
                    messages.add( "A range position with a status 'greater-than' cannot be negative or equal to 0. Actual position : (" + start + "-"+end+")");
                }
            }
            // The sequence is not null, we expect to have positions superior to 0 and STRICTLY inferior to the sequence length
            else {
                if (start >= sequenceLength || start <= 0 ){
                    messages.add( "A range position 'greater-than' must be strictly " +
                            "superior to 0 and strictly inferior to the interactor sequence length. Actual position : (" + start + "-"+end+")");
                }
            }
        }
        // less than position : we don't expect an interval for this position so the start should be equal to the end
        else if (PositionUtils.isLessThan(position)){
            if (start != end) {
                messages.add( "A range position 'greater-than' must be strictly " +
                        "superior to 0 and strictly inferior to the interactor sequence length. Actual position : (" + start + "-"+end+")");
            }
            // The sequence is null, all we can expect is at least a start STRICTLY superior to 1.
            if (sequenceLength == 0){
                if (start <= 1){
                    messages.add( "A range position with a status 'less-than' cannot inferior or equal to 1. Actual position : (" + start + "-"+end+")");
                }
            }
            // The sequence is not null, we expect to have positions STRICTLY superior to 1 and inferior or equal to the sequence length
            else {
                if (start <= 1 || start > sequenceLength) {
                    messages.add( "A range position 'less-than' must be strictly " +
                            "superior to 1 and inferior or equal to the interactor sequence length. Actual position : (" + start + "-"+end+")");
                }
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

            if (sequenceLength == 0){
                if (start <= 0){
                    messages.add( "The range with a status 'certain' or 'ragged-n-terminus' must be strictly superior to 0. Actual position : (" + start + "-"+end+")");
                }
            }
            else {
                if (areRangePositionsOutOfBounds(start, end, sequenceLength)){
                    messages.add( "This range is out of bounds. Actual position : (" + start + "-"+end+")");
                }
            }
        }
        // the range status is not well known, so we allow the position to be an interval, we just check that the start and end are superior to 0 and inferior to the sequence
        // length (only possible to check if the sequence is not null)
        else {
            if (sequenceLength == 0){
                if (areRangePositionsInvalid(start, end) || start <= 0 || end <= 0){
                    messages.add( "The range position is invalid : "+start+"-"+end+". It cannot be inferior or equal to 0 and the start should be inferior or equal to the end.");
                }
            }
            else {
                if (areRangePositionsInvalid(start, end)) {
                    messages.add( "The range position is invalid : " + start + "-" + end + ". The start cannot be superior to the end");
                } else if (areRangePositionsOutOfBounds(start, end, sequenceLength)){
                    messages.add( "This range position is out of bounds. Actual position : (" + start + "-"+end+")");
                }
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
    private static boolean arePositionsOverlapping(long fromStart, long fromEnd, long toStart, long toEnd){

        if (fromStart > toStart || fromEnd > toStart || fromStart > toEnd || fromEnd > toEnd){
            return true;
        }
        return false;
    }

    /**
     * Checks if the interval positions of the range are overlapping
     * @param range
     * @return true if the range intervals are overlapping
     */
    private static boolean areRangePositionsOverlapping(Range range, long fromStart, long fromEnd, long toStart, long toEnd){
        // get the range status
        Position start = range.getStart();
        Position end = range.getEnd();

        // both the end and the start have a specific status
        // in the specific case where the start is superior to a position and the end is inferior to another position, we need to check that the
        // range is not invalid because 'greater than' and 'less than' are both exclusive
        if (PositionUtils.isGreaterThan(start) && PositionUtils.isLessThan(end) && toEnd - fromStart < 2){
            return true;
        }
        // we have a greater than start position and the end position is equal to the start position
        else if (PositionUtils.isGreaterThan(start) && !PositionUtils.isGreaterThan(end) && fromStart == toEnd){
            return true;
        }
        // we have a less than end position and the start position is equal to the start position
        else if (!PositionUtils.isLessThan(start) && PositionUtils.isLessThan(end) && fromStart == toEnd){
            return true;
        }
        // As the range positions are 0 when the status is undetermined, we can only check if the ranges are not overlapping when both start and end are not undetermined
        else if (!(PositionUtils.isUndetermined(start) || PositionUtils.isCTerminalRange(start) || PositionUtils.isNTerminalRange(start)) && !(PositionUtils.isUndetermined(end) || PositionUtils.isCTerminalRange(end) || PositionUtils.isNTerminalRange(end))){
            return arePositionsOverlapping(fromStart, fromEnd, toStart, toEnd);
        }

        return false;
    }

    /**
     *
     * @param start : the start position
     * @param end : the end position
     * @return  true if the range status are inconsistent (n-terminal is the end, c-terminal is the beginning)
     */
    private static boolean areRangeStatusInconsistent(Position start, Position end){

        // the start position is C-terminal but the end position is different from C-terminal
        if (PositionUtils.isCTerminal(start) && !PositionUtils.isCTerminal(end)){
            return true;
        }
        // the end position is N-terminal but the start position is different from N-terminal
        else if (PositionUtils.isNTerminal(end) && !PositionUtils.isNTerminal(start)){
            return true;
        }
        else if (PositionUtils.isCTerminalRange(start) && !(PositionUtils.isCTerminal(end) || PositionUtils.isCTerminalRange(end))){
            return true;
        }
        else if (PositionUtils.isNTerminalRange(end) && !(PositionUtils.isNTerminal(start) || PositionUtils.isNTerminalRange(start))){
            return true;
        }

        return false;
    }
}
