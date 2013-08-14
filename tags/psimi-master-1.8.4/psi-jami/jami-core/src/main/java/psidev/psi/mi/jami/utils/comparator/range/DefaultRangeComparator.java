package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;

/**
 * Default RangeComparator.
 * It compares first the start Position, then the end Position using a DefaultPositionComparator,
 * If start/end positions are equals, compare if the ranges are linked.
 * - Two ranges which are null are equals
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class DefaultRangeComparator {

    /**
     * Use DefaultRangeComparator to know if two ranges are equals.
     * @param range1
     * @param range2
     * @return true if the two ranges are equal
     */
    public static boolean areEquals(Range range1, Range range2){

        if (range1 == null && range2 == null){
            return true;
        }
        else if (range1 == null || range2 == null){
            return false;
        }
        else {
            Position start1 = range1.getStart();
            Position start2 = range2.getStart();

            if (!DefaultPositionComparator.areEquals(start1, start2)){
                return false;
            }

            Position end1 = range1.getEnd();
            Position end2 = range2.getEnd();

            if (!DefaultPositionComparator.areEquals(end1, end2)){
                return false;
            }

            if (range1.isLink() != range2.isLink()){
                return false;
            }

            return true;
        }
    }
}
