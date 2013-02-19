package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
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
}
