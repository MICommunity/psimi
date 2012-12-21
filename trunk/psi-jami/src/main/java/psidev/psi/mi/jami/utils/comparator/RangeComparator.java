package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;

import java.util.Comparator;

/**
 * Simple range comparator.
 * It compares first the start Position, then the end Position using a PositionComparator,
 * If start/end positions are equals, the linked ranges will always come before the ranges that are not linked.
 * - Two ranges which are null are equals
 * - The range which is not null is before null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class RangeComparator implements Comparator<Range> {
    
    protected PositionComparator positionComparator;

    /**
     * Creates a new RangeComparator
     * @param positionComparator : the position comparator is required to compare start/end positions
     */
    public RangeComparator(PositionComparator positionComparator){
        if (positionComparator == null){
            throw new IllegalArgumentException("The PositionComparator is required for comparing start/end positions. It cannot be null");
        }
        this.positionComparator = positionComparator;
    }

    public PositionComparator getPositionComparator() {
        return positionComparator;
    }

    /**
     * It compares first the start Position, then the end Position using a PositionComparator,
     * If start/end positions are equals, the linked ranges will always come before the ranges that are not linked.
     * - Two ranges which are null are equals
     * - The range which is not null is before null.
     * @param range1
     * @param range2
     * @return
     */
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
            Position start1 = range1.getStart();
            Position start2 = range2.getStart();

            int comp = positionComparator.compare(start1, start2);

            if (comp != 0){
                return comp;
            }

            Position end1 = range1.getEnd();
            Position end2 = range2.getEnd();

            int comp2 = positionComparator.compare(end1, end2);

            if (comp2 != 0){
                return comp2;
            }

            if (range1.isLink() && !range2.isLink()){
                return BEFORE;
            }
            else if (!range1.isLink() && range2.isLink()){
                return AFTER;
            }
            else {
                return EQUAL;
            }
        }
    }
}
