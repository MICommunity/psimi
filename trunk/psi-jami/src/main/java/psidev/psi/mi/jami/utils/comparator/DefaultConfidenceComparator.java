package psidev.psi.mi.jami.utils.comparator;

/**
 * Default confidence comparator.
 *
 * - Two confidences which are null are equals
 * - The confidence which is not null is before null.
 * - Use DefaultCvTermComparator to compare first the confidence types.
 * - If confidence types are equals, use DefaultCvTermComparator to compare the units.
 * - If the units are not set, compares the values (case sensitive)
 * - If both units are set and If they are equals, compares the values (case sensitive)
 * - The confidence (same type, same value) with unit which is not null will be before the one with a null unit
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultConfidenceComparator extends AbstractConfidenceComparator<DefaultCvTermComparator> {
    @Override
    protected void instantiateCvTermComparator() {
        this.cvTermComparator = new DefaultCvTermComparator();
    }
}
