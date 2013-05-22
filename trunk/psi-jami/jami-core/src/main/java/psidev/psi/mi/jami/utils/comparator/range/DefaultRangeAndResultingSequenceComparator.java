package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.Range;

/**
 * Default RangeComparator.
 * It compares first the start Position, then the end Position using a DefaultPositionComparator,
 * If start/end positions are equals, the linked ranges will always come before the ranges that are not linked.
 * Then, if the positions and linked boolean are the same, it will compare the resultingSequences using ResultingSequenceComparator
 * - Two ranges which are null are equals
 * - The range which is not null is before null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class DefaultRangeAndResultingSequenceComparator extends RangeAndResultingSequenceComparator{
    private static DefaultRangeAndResultingSequenceComparator defaultRangeAndResultingSequenceComparator;

    /**
     * Creates a new DefaultRangeAndResultingSequenceComparator with DefaultPositionComparator
     */
    public DefaultRangeAndResultingSequenceComparator() {
        super(new DefaultPositionComparator());
    }

    @Override
    public DefaultPositionComparator getPositionComparator() {
        return (DefaultPositionComparator) this.positionComparator;
    }

    @Override
    /**
     * It compares first the start Position, then the end Position using a DefaultPositionComparator,
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
        if (defaultRangeAndResultingSequenceComparator == null){
            defaultRangeAndResultingSequenceComparator = new DefaultRangeAndResultingSequenceComparator();
        }

        return defaultRangeAndResultingSequenceComparator.compare(range1, range2) == 0;
    }
}
