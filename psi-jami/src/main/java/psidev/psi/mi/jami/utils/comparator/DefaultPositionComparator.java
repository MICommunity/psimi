package psidev.psi.mi.jami.utils.comparator;

/**
 * Default PositionComparator.
 *
 * - Two positions which are null are equals
 * - The position which is not null is before null.
 * - Use DefaultCvTermComparator to compare first the position status.
 * - If position status are equals, the undetermined positions are always coming after the determined positions.
 * - If both positions have same status and are undetermined, they are equals
 * - If both positions are not undetermined, compare the start position first and then the end position
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class DefaultPositionComparator extends AbstractPositionComparator<DefaultCvTermComparator>{
    @Override
    protected void instantiateStatusComparator() {
        this.statusComparator = new DefaultCvTermComparator();
    }
}
