package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultPosition;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Utility class for Positions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class PositionUtils {

    public static boolean isUndetermined(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getUndetermined(), status);
    }

    public static boolean isNTerminalRange(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getNTerminalRange(), status);
    }

    public static boolean isCTerminalRange(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getCTerminalRange(), status);
    }

    public static boolean isNTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getNTerminal(), status);
    }

    public static boolean isCTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getCTerminal(), status);
    }

    public static boolean isRaggedNTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getNTerminalRagged(), status);
    }

    public static boolean isGreaterThan(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getGreaterThan(), status);
    }

    public static boolean isLessThan(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getLessThan(), status);
    }

    public static boolean isCertain(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(CvTermUtils.getCertain(), status);
    }

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
        else if (position.getStart() != position.getEnd()){
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
}
