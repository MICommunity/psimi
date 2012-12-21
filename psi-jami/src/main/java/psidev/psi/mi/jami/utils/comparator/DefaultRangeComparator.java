package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Range;

/**
 * Default RangeComparator.
 * It compares first the start Position, then the end Position using a DefaultPositionComparator,
 * If start/end positions are equals, the linked ranges will always come before the ranges that are not linked.
 * - Two ranges which are null are equals
 * - The range which is not null is before null.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/12/12</pre>
 */

public class DefaultRangeComparator extends RangeComparator {

    private static DefaultRangeComparator defaultRangeComparator;

    /**
     * Creates a new DefaultRangeComparator with DefaultPositionComparator
     */
    public DefaultRangeComparator() {
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
        if (defaultRangeComparator == null){
            defaultRangeComparator = new DefaultRangeComparator();
        }

        return defaultRangeComparator.compare(range1, range2) == 0;
    }
}
