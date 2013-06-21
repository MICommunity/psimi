package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.ResultingSequence;

/**
 * Default RangeComparator.
 * It compares first the start Position, then the end Position using a DefaultPositionComparator,
 * If start/end positions are equals, the linked ranges will always come before the ranges that are not linked.
 * Then, if the positions and linked boolean are the same, it will compare the resultingSequences using ResultingSequenceComparator
 * - Two ranges which are null are equals
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class DefaultRangeAndResultingSequenceComparator {

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
            if (!DefaultRangeComparator.areEquals(range1, range2)){
                return false;
            }

            ResultingSequence resultingSequence1 = range1.getResultingSequence();
            ResultingSequence resultingSequence2 = range2.getResultingSequence();

            return ResultingSequenceComparator.areEquals(resultingSequence1, resultingSequence2);
        }
    }
}
