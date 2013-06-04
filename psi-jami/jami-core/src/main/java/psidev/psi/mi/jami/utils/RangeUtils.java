package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultRange;

import java.util.Collections;
import java.util.List;

/**
 * Utility methods for Ranges
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class RangeUtils {

    /**
     * Converts a range in a String.
     * Undetermined position is represented with ?
     * N-terminal range is represented with n
     * C-terminal range is represented with c
     * fuzzy ranges are represented with x1..x2
     * @param range
     * @return
     */
    public static String convertRangeToString(Range range){
        if (range == null){
            return null;
        }

        String pos1 = PositionUtils.convertPositionToString(range.getStart());
        String pos2 = PositionUtils.convertPositionToString(range.getEnd());

        return pos1+Range.POSITION_SEPARATOR+pos2;
    }

    /**
     * Create a Range from a String
     * @param rangeString
     * @return
     * @throws IllegalRangeException
     */
    public static Range createRangeFromString(String rangeString) throws IllegalRangeException {

        return createRangeFromString(rangeString, false);
    }

    /**
     * Create a range with a given linked property from a String
     * @param rangeString
     * @param linked
     * @return
     * @throws IllegalRangeException
     */
    public static Range createRangeFromString(String rangeString, boolean linked) throws IllegalRangeException {

        if (rangeString == null){
            Range r = createUndeterminedRange();
            r.setLink(linked);
            return r;
        }
        // we have two positions
        else if (rangeString.contains(Range.POSITION_SEPARATOR)){
            String[] rangePositions = rangeString.split(Range.POSITION_SEPARATOR);
            if (rangePositions.length != 2){
                throw new IllegalRangeException("The range positions " + rangeString + " are not valid and cannot be converted into a range.");
            }
            else {
                Position pos1 = PositionUtils.createPositionFromString(rangePositions[0]);
                Position pos2 = PositionUtils.createPositionFromString(rangePositions[1]);
                return new DefaultRange(pos1, pos2, linked);
            }
        }
        // we have one position
        else {
            // shortcut for n-terminal range
            if (Range.N_TERMINAL_POSITION_SYMBOL.equals(rangeString)){
                Range r = createNTerminalRange();
                r.setLink(linked);
                return r;
            }
            // shortcut for c-terminal range
            else if (Range.C_TERMINAL_POSITION_SYMBOL.equals(rangeString)){
                Range r = createCTerminalRange();
                r.setLink(linked);
                return r;
            }
            // shortcut for undetermined range
            else if (Range.UNDETERMINED_POSITION_SYMBOL.equals(rangeString)){
                Range r = createUndeterminedRange();
                r.setLink(linked);
                return r;
            }
            // shortcut for greater than range
            else if (rangeString.contains(Range.GREATER_THAN_POSITION_SYMBOL)){
                String rangePosition = rangeString.replace(Range.GREATER_THAN_POSITION_SYMBOL, "");
                Range r =  createGreaterThanRange(PositionUtils.convertStringToPositionValue(rangePosition));
                r.setLink(linked);
                return r;
            }
            // shortcut for less than range
            else if (rangeString.contains(Range.LESS_THAN_POSITION_SYMBOL)){
                String rangePosition = rangeString.replace(Range.LESS_THAN_POSITION_SYMBOL, "");
                Range r = createLessThanRange(PositionUtils.convertStringToPositionValue(rangePosition));
                r.setLink(linked);
                return r;
            }
            // shortcut for fuzzy range
            else if (rangeString.contains(Range.FUZZY_POSITION_SYMBOL)){
                String[] positionString = rangeString.split(Range.FUZZY_POSITION_SYMBOL);
                if (positionString.length != 2){
                    throw new IllegalRangeException("The fuzzy range " + rangeString + " is not valid and cannot be converted into a range.");
                }
                else {
                    Range r = createFuzzyRange(PositionUtils.convertStringToPositionValue(positionString[0]), PositionUtils.convertStringToPositionValue(positionString[1]));
                    r.setLink(linked);
                    return r;
                }
            }
            // shortcut for certain
            else {
                int pos = PositionUtils.convertStringToPositionValue(rangeString);
                Range r = createCertainRange(pos);
                r.setLink(linked);
                return r;
            }
        }
    }

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

            List<String> messages = PositionUtils.validateRangePosition(start, sequence);

            messages.addAll(PositionUtils.validateRangePosition(end, sequence));

            if (areRangeStatusInconsistent(range)){
                messages.add("The start status "+start.getStatus().getShortName()  +" and end status "+end.getStatus().getShortName()+" are inconsistent");
            }

            if (areRangePositionsOverlapping(range)){
                messages.add("The range positions overlap : ("+start.getStart()+"-"+start.getEnd()+") - ("+end.getStart()+"-"+end.getEnd()+")");
            }

            return messages;
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * Checks if the interval positions of the range are overlapping
     * @param range
     * @return true if the range intervals are overlapping
     */
    public static boolean areRangePositionsOverlapping(Range range){
        // get the range status
        Position start = range.getStart();
        Position end = range.getEnd();
        long fromStart = start.getStart();
        long fromEnd = start.getEnd();
        long toStart = end.getStart();
        long toEnd = end.getEnd();

        // both the end and the start have a specific status
        // in the specific case where the start is superior to a position and the end is inferior to another position, we need to check that the
        // range is not invalid because 'greater than' and 'less than' are both exclusive
        if (PositionUtils.isGreaterThan(start) && PositionUtils.isLessThan(end) && toStart - fromEnd < 2){
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
        else if (!start.isPositionUndetermined() && !end.isPositionUndetermined()){
            return PositionUtils.arePositionsOverlapping(fromStart, fromEnd, toStart, toEnd);
        }

        return false;
    }

    /**
     *
     * @param range : the range to check
     * @return  true if the range status are inconsistent (n-terminal is the end, c-terminal is the beginning)
     */
    public static boolean areRangeStatusInconsistent(Range range){
        Position start = range.getStart();
        Position end = range.getEnd();

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

    public static Range createUndeterminedRange(){
        return new DefaultRange(PositionUtils.createUndeterminedPosition(), PositionUtils.createUndeterminedPosition());
    }

    public static Range createNTerminalRange(){
        return new DefaultRange(PositionUtils.createNTerminalRangePosition(), PositionUtils.createNTerminalRangePosition());
    }

    public static Range createCTerminalRange(){
        return new DefaultRange(PositionUtils.createCTerminalRangePosition(), PositionUtils.createCTerminalRangePosition());
    }

    public static Range createNTerminusRange(){
        return new DefaultRange(PositionUtils.createNTerminalPosition(), PositionUtils.createNTerminalPosition());
    }

    public static Range createCTerminusRange(int lastPosition){
        return new DefaultRange(PositionUtils.createCTerminalPosition(lastPosition), PositionUtils.createCTerminalPosition(lastPosition));
    }

    public static Range createCertainRange(int position){
        return new DefaultRange(PositionUtils.createCertainPosition(position), PositionUtils.createCertainPosition(position));
    }

    public static Range createLinkedCertainRange(int position){
        return new DefaultRange(PositionUtils.createCertainPosition(position), PositionUtils.createCertainPosition(position), true);
    }

    public static Range createGreaterThanRange(int position){
        return new DefaultRange(PositionUtils.createGreaterThanPosition(position), PositionUtils.createGreaterThanPosition(position));
    }

    public static Range createLessThanRange(int position){
        return new DefaultRange(PositionUtils.createLessThanPosition(position), PositionUtils.createLessThanPosition(position));
    }

    public static Range createRaggedNTerminusRange(int position){
        return new DefaultRange(PositionUtils.createRaggedNTerminusPosition(position), PositionUtils.createRaggedNTerminusPosition(position));
    }

    public static Range createLinkedRaggedNTerminusRange(int position){
        return new DefaultRange(PositionUtils.createRaggedNTerminusPosition(position), PositionUtils.createRaggedNTerminusPosition(position), true);
    }

    public static Range createFuzzyRange(int position){
        return new DefaultRange(PositionUtils.createFuzzyPosition(position), PositionUtils.createFuzzyPosition(position));
    }

    public static Range createCertainRange(int start, int end){
        return new DefaultRange(PositionUtils.createCertainPosition(start), PositionUtils.createCertainPosition(end));
    }

    public static Range createLinkedFuzzyRange(int position){
        return new DefaultRange(PositionUtils.createFuzzyPosition(position), PositionUtils.createFuzzyPosition(position), true);
    }

    public static Range createLinkedCertainRange(int start, int end){
        return new DefaultRange(PositionUtils.createCertainPosition(start), PositionUtils.createCertainPosition(end), true);
    }

    public static Range createGreaterThanRange(int start, int end){
        return new DefaultRange(PositionUtils.createGreaterThanPosition(start), PositionUtils.createGreaterThanPosition(end));
    }

    public static Range createLessThanRange(int start, int end){
        return new DefaultRange(PositionUtils.createLessThanPosition(start), PositionUtils.createLessThanPosition(end));
    }

    public static Range createRaggedNTerminusRange(int start, int end){
        return new DefaultRange(PositionUtils.createRaggedNTerminusPosition(start), PositionUtils.createRaggedNTerminusPosition(end));
    }

    public static Range createFuzzyRange(int start, int end){
        return new DefaultRange(PositionUtils.createFuzzyPosition(start), PositionUtils.createFuzzyPosition(end));
    }

    public static Range createFuzzyRange(int fromStart, int fromEnd, int toStart, int toEnd){
        return new DefaultRange(PositionUtils.createFuzzyPosition(fromStart, fromEnd), PositionUtils.createFuzzyPosition(toStart, toEnd));
    }

    public static Range createLinkedRaggedNTerminusRange(int start, int end){
        return new DefaultRange(PositionUtils.createRaggedNTerminusPosition(start), PositionUtils.createRaggedNTerminusPosition(end), true);
    }

    public static Range createLinkedFuzzyRange(int start, int end){
        return new DefaultRange(PositionUtils.createFuzzyPosition(start), PositionUtils.createFuzzyPosition(end), true);
    }

    public static Range createLinkedFuzzyRange(int fromStart, int fromEnd, int toStart, int toEnd){
        return new DefaultRange(PositionUtils.createFuzzyPosition(fromStart, fromEnd), PositionUtils.createFuzzyPosition(toStart, toEnd), true);
    }

    public static Range createRange(String statusName, String statusMi, int position){
        return new DefaultRange(PositionUtils.createPosition(statusName, statusMi, position), PositionUtils.createPosition(statusName, statusMi, position));
    }

    public static Range createLinkedRange(String statusName, String statusMi, int position){
        return new DefaultRange(PositionUtils.createPosition(statusName, statusMi, position), PositionUtils.createPosition(statusName, statusMi, position), true);
    }

    public static Range createRange(String statusName, String statusMi, int start, int end){
        return new DefaultRange(PositionUtils.createPosition(statusName, statusMi, start), PositionUtils.createPosition(statusName, statusMi, end));
    }

    public static Range createLinkedRange(String statusName, String statusMi, int start, int end){
        return new DefaultRange(PositionUtils.createPosition(statusName, statusMi, start), PositionUtils.createPosition(statusName, statusMi, end), true);
    }
}
