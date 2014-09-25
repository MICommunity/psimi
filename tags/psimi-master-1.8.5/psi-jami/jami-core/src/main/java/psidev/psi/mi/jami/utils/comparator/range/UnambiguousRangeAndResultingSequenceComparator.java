package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.ResultingSequence;

/**
 * Unambiguous RangeComparator.
 * It compares first the start Position, then the end Position using a UnambiguousPositionComparator,
 * If start/end positions are equals, the linked ranges will always come before the ranges that are not linked.
 * Then, if the positions and linked boolean are the same, it will compare the resultingSequences using ResultingSequenceComparator
 * - Two ranges which are null are equals
 * - The range which is not null is before null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class UnambiguousRangeAndResultingSequenceComparator extends RangeAndResultingSequenceComparator {

    private static UnambiguousRangeAndResultingSequenceComparator unambiguousRangeAndResultingSequenceComparator;

    /**
     * Creates a new UnambiguousRangeAndResultingSequenceComparator with UnambiguousPositionComparator
     */
    public UnambiguousRangeAndResultingSequenceComparator() {
        super(new UnambiguousPositionComparator());
    }

    @Override
    public UnambiguousPositionComparator getPositionComparator() {
        return (UnambiguousPositionComparator) this.positionComparator;
    }

    @Override
    /**
     * It compares first the start Position, then the end Position using a UnambiguousPositionComparator,
     * If start/end positions are equals, the linked ranges will always come before the ranges that are not linked.
     * Then, if the positions and linked boolean are the same, it will compare the resultingSequences using ResultingSequenceComparator
     * - Two ranges which are null are equals
     * - The range which is not null is before null.
     */
    public int compare(Range range1, Range range2) {
        return super.compare(range1, range2);
    }

    /**
     * Use DefaultRangeComparator to know if two ranges are equals.
     * @param range1
     * @param range2
     * @return true if the two ranges are equal
     */
    public static boolean areEquals(Range range1, Range range2){
        if (unambiguousRangeAndResultingSequenceComparator == null){
            unambiguousRangeAndResultingSequenceComparator = new UnambiguousRangeAndResultingSequenceComparator();
        }

        return unambiguousRangeAndResultingSequenceComparator.compare(range1, range2) == 0;
    }


    /**
     *
     * @param range
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Range range){
        if (unambiguousRangeAndResultingSequenceComparator == null){
            unambiguousRangeAndResultingSequenceComparator = new UnambiguousRangeAndResultingSequenceComparator();
        }

        if (range == null){
            return 0;
        }

        int hashcode = 31;
        Position start = range.getStart();
        hashcode = 31*hashcode + UnambiguousPositionComparator.hashCode(start);
        Position end = range.getEnd();
        hashcode = 31*hashcode + UnambiguousPositionComparator.hashCode(end);
        hashcode = 31 * hashcode + (range.isLink() ? 0 : 1);

        ResultingSequence resultingSequence = range.getResultingSequence();
        hashcode = 31 * hashcode + ResultingSequenceComparator.hashCode(resultingSequence);

        return hashcode;
    }
}
