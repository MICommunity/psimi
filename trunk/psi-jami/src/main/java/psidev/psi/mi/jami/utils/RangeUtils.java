package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Range;

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
}
