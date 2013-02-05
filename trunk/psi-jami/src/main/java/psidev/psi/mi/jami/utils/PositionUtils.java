package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
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
}
