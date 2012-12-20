package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Confidence;

/**
 * Unambiguous confidence comparator.
 * It will compares the confidence types first, then the units and finally the value.
 *
 * - Two confidences which are null are equals
 * - The confidence which is not null is before null.
 * - Use UnambiguousCvTermComparator to compare first the confidence types.
 * - If confidence types are equals, use UnambiguousCvTermComparator to compare the units.
 * - If the units are not set, compares the values (case sensitive)
 * - If both units are set and If they are equals, compares the values (case sensitive)
 * - The confidence (same type, same value) with unit which is not null will be before the one with a null unit
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class UnambiguousConfidenceComparator extends ConfidenceComparator {

    public UnambiguousConfidenceComparator() {
        super(new UnambiguousCvTermComparator());
    }

    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) cvTermComparator;
    }

    /**
     * It will compares the confidence types first, then the units and finally the value.
     *
     * - Two confidences which are null are equals
     * - The confidence which is not null is before null.
     * - Use UnambiguousCvTermComparator to compare first the confidence types.
     * - If confidence types are equals, use UnambiguousCvTermComparator to compare the units.
     * - If the units are not set, compares the values (case sensitive)
     * - If both units are set and If they are equals, compares the values (case sensitive)
     * - The confidence (same type, same value) with unit which is not null will be before the one with a null unit
     *
     * @param confidence1
     * @param confidence2
     * @return
     */
    public int compare(Confidence confidence1, Confidence confidence2) {
        return super.compare(confidence1, confidence2);
    }
}
