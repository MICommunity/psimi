package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultPosition;

/**
 * Factory for creating positions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class PositionFactory {

    public static Position createUndeterminedPosition(){
        return new DefaultPosition(0);
    }

    public static Position createNTerminalRangePosition(){
        return new DefaultPosition(CvTermFactory.createNTerminalRangeStatus(), 0);
    }

    public static Position createCTerminalRangePosition(){
        return new DefaultPosition(CvTermFactory.createCTerminalRangeStatus(), 0);
    }

    public static Position createNTerminalPosition(){
        return new DefaultPosition(CvTermFactory.createNTerminalStatus(), 1);
    }

    public static Position createCTerminalPosition(int lastPosition){
        return new DefaultPosition(CvTermFactory.createCTerminalStatus(), lastPosition);
    }

    public static Position createCertainPosition(int position){
        return new DefaultPosition(position);
    }

    public static Position createGreaterThanPosition(int position){
        return new DefaultPosition(CvTermFactory.createGreaterThanRangeStatus(), position);
    }

    public static Position createLessThanPosition(int position){
        return new DefaultPosition(CvTermFactory.createLessThanRangeStatus(), position);
    }

    public static Position createRaggedNTerminusPosition(int position){
        return new DefaultPosition(CvTermFactory.createRaggedNTerminalStatus(), position);
    }

    public static Position createFuzzyPosition(int position){
        return new DefaultPosition(CvTermFactory.createRangeStatus(), position);
    }

    public static Position createFuzzyPosition(int start, int end){
        return new DefaultPosition(start, end);
    }

    public static Position createPosition(String statusName, String statusMi, int start){
        return new DefaultPosition(CvTermFactory.createMICvTerm(statusName, statusMi), start);
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
            String[] positionString = pos.split(Range.FUZZY_POSITION_SYMBOL);
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
}
