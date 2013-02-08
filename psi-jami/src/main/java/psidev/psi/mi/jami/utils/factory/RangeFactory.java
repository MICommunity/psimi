package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultRange;

/**
 * Factory to create ranges
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class RangeFactory {

    public static Range createUndeterminedRange(){
        return new DefaultRange(PositionFactory.createUndeterminedPosition(), PositionFactory.createUndeterminedPosition());
    }

    public static Range createNTerminalRange(){
        return new DefaultRange(PositionFactory.createNTerminalRangePosition(), PositionFactory.createNTerminalRangePosition());
    }

    public static Range createCTerminalRange(){
        return new DefaultRange(PositionFactory.createCTerminalRangePosition(), PositionFactory.createCTerminalRangePosition());
    }

    public static Range createNTerminusRange(){
        return new DefaultRange(PositionFactory.createNTerminalPosition(), PositionFactory.createNTerminalPosition());
    }

    public static Range createCTerminusRange(int lastPosition){
        return new DefaultRange(PositionFactory.createCTerminalPosition(lastPosition), PositionFactory.createCTerminalPosition(lastPosition));
    }

    public static Range createCertainRange(int position){
        return new DefaultRange(PositionFactory.createCertainPosition(position), PositionFactory.createCertainPosition(position));
    }

    public static Range createLinkedCertainRange(int position){
        return new DefaultRange(PositionFactory.createCertainPosition(position), PositionFactory.createCertainPosition(position), true);
    }

    public static Range createGreaterThanRange(int position){
        return new DefaultRange(PositionFactory.createGreaterThanPosition(position), PositionFactory.createGreaterThanPosition(position));
    }

    public static Range createLessThanRange(int position){
        return new DefaultRange(PositionFactory.createLessThanPosition(position), PositionFactory.createLessThanPosition(position));
    }

    public static Range createRaggedNTerminusRange(int position){
        return new DefaultRange(PositionFactory.createRaggedNTerminusPosition(position), PositionFactory.createRaggedNTerminusPosition(position));
    }

    public static Range createLinkedRaggedNTerminusRange(int position){
        return new DefaultRange(PositionFactory.createRaggedNTerminusPosition(position), PositionFactory.createRaggedNTerminusPosition(position), true);
    }

    public static Range createFuzzyRange(int position){
        return new DefaultRange(PositionFactory.createFuzzyPosition(position), PositionFactory.createFuzzyPosition(position));
    }

    public static Range createCertainRange(int start, int end){
        return new DefaultRange(PositionFactory.createCertainPosition(start), PositionFactory.createCertainPosition(end));
    }

    public static Range createLinkedFuzzyRange(int position){
        return new DefaultRange(PositionFactory.createFuzzyPosition(position), PositionFactory.createFuzzyPosition(position), true);
    }

    public static Range createLinkedCertainRange(int start, int end){
        return new DefaultRange(PositionFactory.createCertainPosition(start), PositionFactory.createCertainPosition(end), true);
    }

    public static Range createGreaterThanRange(int start, int end){
        return new DefaultRange(PositionFactory.createGreaterThanPosition(start), PositionFactory.createGreaterThanPosition(end));
    }

    public static Range createLessThanRange(int start, int end){
        return new DefaultRange(PositionFactory.createLessThanPosition(start), PositionFactory.createLessThanPosition(end));
    }

    public static Range createRaggedNTerminusRange(int start, int end){
        return new DefaultRange(PositionFactory.createRaggedNTerminusPosition(start), PositionFactory.createRaggedNTerminusPosition(end));
    }

    public static Range createFuzzyRange(int start, int end){
        return new DefaultRange(PositionFactory.createFuzzyPosition(start), PositionFactory.createFuzzyPosition(end));
    }

    public static Range createFuzzyRange(int fromStart, int fromEnd, int toStart, int toEnd){
        return new DefaultRange(PositionFactory.createFuzzyPosition(fromStart, fromEnd), PositionFactory.createFuzzyPosition(toStart, toEnd));
    }

    public static Range createLinkedRaggedNTerminusRange(int start, int end){
        return new DefaultRange(PositionFactory.createRaggedNTerminusPosition(start), PositionFactory.createRaggedNTerminusPosition(end), true);
    }

    public static Range createLinkedFuzzyRange(int start, int end){
        return new DefaultRange(PositionFactory.createFuzzyPosition(start), PositionFactory.createFuzzyPosition(end), true);
    }

    public static Range createLinkedFuzzyRange(int fromStart, int fromEnd, int toStart, int toEnd){
        return new DefaultRange(PositionFactory.createFuzzyPosition(fromStart, fromEnd), PositionFactory.createFuzzyPosition(toStart, toEnd), true);
    }

    public static Range createRange(String statusName, String statusMi, int position){
        return new DefaultRange(PositionFactory.createPosition(statusName, statusMi, position), PositionFactory.createPosition(statusName, statusMi, position));
    }

    public static Range createLinkedRange(String statusName, String statusMi, int position){
        return new DefaultRange(PositionFactory.createPosition(statusName, statusMi, position), PositionFactory.createPosition(statusName, statusMi, position), true);
    }

    public static Range createRange(String statusName, String statusMi, int start, int end){
        return new DefaultRange(PositionFactory.createPosition(statusName, statusMi, start), PositionFactory.createPosition(statusName, statusMi, end));
    }

    public static Range createLinkedRange(String statusName, String statusMi, int start, int end){
        return new DefaultRange(PositionFactory.createPosition(statusName, statusMi, start), PositionFactory.createPosition(statusName, statusMi, end), true);
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
                Position pos1 = PositionFactory.createPositionFromString(rangePositions[0]);
                Position pos2 = PositionFactory.createPositionFromString(rangePositions[2]);
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
                return createGreaterThanRange(PositionFactory.convertStringToPositionValue(rangePosition));
            }
            // shortcut for less than range
            else if (rangeString.contains(Range.LESS_THAN_POSITION_SYMBOL)){
                String rangePosition = rangeString.replace(Range.LESS_THAN_POSITION_SYMBOL, "");
                return createLessThanRange(PositionFactory.convertStringToPositionValue(rangePosition));
            }
            // shortcut for fuzzy range
            else if (rangeString.contains(Range.FUZZY_POSITION_SYMBOL)){
                String[] positionString = rangeString.split(Range.FUZZY_POSITION_SYMBOL);
                if (positionString.length != 2){
                    throw new IllegalRangeException("The fuzzy range " + rangeString + " is not valid and cannot be converted into a range.");
                }
                else {
                    return createFuzzyRange(PositionFactory.convertStringToPositionValue(positionString[0]), PositionFactory.convertStringToPositionValue(positionString[1]));
                }
            }
            // shortcut for certain
            else {
                int pos = PositionFactory.convertStringToPositionValue(rangeString);
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
                Position pos1 = PositionFactory.createPositionFromString(rangePositions[0]);
                Position pos2 = PositionFactory.createPositionFromString(rangePositions[2]);
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
                return createGreaterThanRange(PositionFactory.convertStringToPositionValue(rangePosition));
            }
            // shortcut for less than range
            else if (rangeString.contains(Range.LESS_THAN_POSITION_SYMBOL)){
                String rangePosition = rangeString.replace(Range.LESS_THAN_POSITION_SYMBOL, "");
                return createLessThanRange(PositionFactory.convertStringToPositionValue(rangePosition));
            }
            // shortcut for fuzzy range
            else if (rangeString.contains(Range.FUZZY_POSITION_SYMBOL)){
                String[] positionString = rangeString.split(Range.FUZZY_POSITION_SYMBOL);
                if (positionString.length != 2){
                    throw new IllegalRangeException("The fuzzy range " + rangeString + " is not valid and cannot be converted into a range.");
                }
                else {
                    return createLinkedFuzzyRange(PositionFactory.convertStringToPositionValue(positionString[0]), PositionFactory.convertStringToPositionValue(positionString[1]));
                }
            }
            // shortcut for certain
            else {
                int pos = PositionFactory.convertStringToPositionValue(rangeString);
                return createLinkedCertainRange(pos);
            }
        }
    }
}
