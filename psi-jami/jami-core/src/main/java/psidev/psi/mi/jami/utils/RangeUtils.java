package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultRange;

/**
 * Utility methods for Ranges
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class RangeUtils {
    
    public static String convertRangeToString(Range range){
        if (range == null){
            return null;
        }

        String pos1 = PositionUtils.convertPositionToString(range.getStart());
        String pos2 = PositionUtils.convertPositionToString(range.getEnd());

        return pos1+Range.POSITION_SEPARATOR+pos2;
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

    public static Range createRangeFromString(String rangeString) throws IllegalRangeException {

        if (rangeString == null){
            return createUndeterminedRange();
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
                return new DefaultRange(pos1, pos2);
            }
        }
        // we have one position
        else {
            // shortcut for n-terminal range
            if (Range.N_TERMINAL_POSITION_SYMBOL.equals(rangeString)){
                return createNTerminalRange();
            }
            // shortcut for c-terminal range
            else if (Range.C_TERMINAL_POSITION_SYMBOL.equals(rangeString)){
                return createCTerminalRange();
            }
            // shortcut for undetermined range
            else if (Range.UNDETERMINED_POSITION_SYMBOL.equals(rangeString)){
                return createUndeterminedRange();
            }
            // shortcut for greater than range
            else if (rangeString.contains(Range.GREATER_THAN_POSITION_SYMBOL)){
                String rangePosition = rangeString.replace(Range.GREATER_THAN_POSITION_SYMBOL, "");
                return createGreaterThanRange(PositionUtils.convertStringToPositionValue(rangePosition));
            }
            // shortcut for less than range
            else if (rangeString.contains(Range.LESS_THAN_POSITION_SYMBOL)){
                String rangePosition = rangeString.replace(Range.LESS_THAN_POSITION_SYMBOL, "");
                return createLessThanRange(PositionUtils.convertStringToPositionValue(rangePosition));
            }
            // shortcut for fuzzy range
            else if (rangeString.contains(Range.FUZZY_POSITION_SYMBOL)){
                String[] positionString = rangeString.split(Range.FUZZY_POSITION_SYMBOL);
                if (positionString.length != 2){
                    throw new IllegalRangeException("The fuzzy range " + rangeString + " is not valid and cannot be converted into a range.");
                }
                else {
                    return createFuzzyRange(PositionUtils.convertStringToPositionValue(positionString[0]), PositionUtils.convertStringToPositionValue(positionString[1]));
                }
            }
            // shortcut for certain
            else {
                int pos = PositionUtils.convertStringToPositionValue(rangeString);
                return createCertainRange(pos);
            }
        }
    }

    public static Range createLinkedRangeFromString(String rangeString) throws IllegalRangeException {

        if (rangeString == null){
            return createUndeterminedRange();
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
                return new DefaultRange(pos1, pos2, true);
            }
        }
        // we have one position
        else {
            // shortcut for n-terminal range
            if (Range.N_TERMINAL_POSITION_SYMBOL.equals(rangeString)){
                return createNTerminalRange();
            }
            // shortcut for c-terminal range
            else if (Range.C_TERMINAL_POSITION_SYMBOL.equals(rangeString)){
                return createCTerminalRange();
            }
            // shortcut for undetermined range
            else if (Range.UNDETERMINED_POSITION_SYMBOL.equals(rangeString)){
                return createUndeterminedRange();
            }
            // shortcut for greater than range
            else if (rangeString.contains(Range.GREATER_THAN_POSITION_SYMBOL)){
                String rangePosition = rangeString.replace(Range.GREATER_THAN_POSITION_SYMBOL, "");
                return createGreaterThanRange(PositionUtils.convertStringToPositionValue(rangePosition));
            }
            // shortcut for less than range
            else if (rangeString.contains(Range.LESS_THAN_POSITION_SYMBOL)){
                String rangePosition = rangeString.replace(Range.LESS_THAN_POSITION_SYMBOL, "");
                return createLessThanRange(PositionUtils.convertStringToPositionValue(rangePosition));
            }
            // shortcut for fuzzy range
            else if (rangeString.contains(Range.FUZZY_POSITION_SYMBOL)){
                String[] positionString = rangeString.split(Range.FUZZY_POSITION_SYMBOL);
                if (positionString.length != 2){
                    throw new IllegalRangeException("The fuzzy range " + rangeString + " is not valid and cannot be converted into a range.");
                }
                else {
                    return createLinkedFuzzyRange(PositionUtils.convertStringToPositionValue(positionString[0]), PositionUtils.convertStringToPositionValue(positionString[1]));
                }
            }
            // shortcut for certain
            else {
                int pos = PositionUtils.convertStringToPositionValue(rangeString);
                return createLinkedCertainRange(pos);
            }
        }
    }
}
