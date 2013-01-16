package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

/**
 * Comparator for collection of Ranges
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class RangeCollectionComparator extends CollectionComparator<Range>{
    /**
     * Creates a new range CollectionComparator. It requires a Comparator for the ranges in the Collection
     *
     * @param rangeComparator
     */
    public RangeCollectionComparator(RangeComparator rangeComparator) {
        super(rangeComparator);
    }

    @Override
    public RangeComparator getObjectComparator() {
        return (RangeComparator) objectComparator;
    }
}
