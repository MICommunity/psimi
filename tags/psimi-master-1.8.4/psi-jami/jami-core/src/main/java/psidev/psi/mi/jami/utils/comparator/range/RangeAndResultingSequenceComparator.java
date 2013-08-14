package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.ResultingSequence;

/**
 * Simple range comparator that will also compare resulting sequences..
 * It compares first the start Position, then the end Position using a PositionComparator,
 * If start/end positions are equals, the linked ranges will always come before the ranges that are not linked.
 * - Two ranges which are null are equals
 * - The range which is not null is before null.
 * Then, if the positions and linked boolean are the same, it will compare the resultingSequences using ResultingSequenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public class RangeAndResultingSequenceComparator extends RangeComparator {

    private ResultingSequenceComparator resultingSequenceComparator;

    /**
     * Creates a new RangeComparator
     *
     * @param positionComparator : the position comparator is required to compare start/end positions
     */
    public RangeAndResultingSequenceComparator(PositionComparator positionComparator) {
        super(positionComparator);
        this.resultingSequenceComparator = new ResultingSequenceComparator();
    }

    public ResultingSequenceComparator getResultingSequenceComparator() {
        return resultingSequenceComparator;
    }

    /**
     * It compares first the start Position, then the end Position using a PositionComparator,
     * If start/end positions are equals, the linked ranges will always come before the ranges that are not linked.
     * If both ranges have the same positions and linked property, it will compare the resultingSequences using ResultingSequenceComparator
     * - Two ranges which are null are equals
     * - The range which is not null is before null.
     * @param range1
     * @param range2
     * @return
     */
    @Override
    public int compare(Range range1, Range range2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (range1 == null && range2 == null){
            return EQUAL;
        }
        else if (range1 == null){
            return AFTER;
        }
        else if (range2 == null){
            return BEFORE;
        }
        else {
            int comp = super.compare(range1, range2);

            if (comp != 0){
                return comp;
            }

            ResultingSequence resultingSequence1 = range1.getResultingSequence();
            ResultingSequence resultingSequence2 = range2.getResultingSequence();

            return resultingSequenceComparator.compare(resultingSequence1, resultingSequence2);
        }
    }
}
