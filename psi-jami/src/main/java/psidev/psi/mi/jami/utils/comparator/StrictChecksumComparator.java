package psidev.psi.mi.jami.utils.comparator;

/**
 * Strict checksum comparator.
 *
 * - Two checksum which are null are equals
 * - The checksum which is not null is before null.
 * - use StrictCvTermComparator to compare the methods. If they are equals, compares the values (case sensitive)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class StrictChecksumComparator extends AbstractChecksumComparator<StrictCvTermComparator> {
    @Override
    protected void instantiateMethodComparator() {
        this.methodComparator = new StrictCvTermComparator();
    }
}
