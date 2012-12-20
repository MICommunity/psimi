package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Position;

/**
 * Strict PositionComparator.
 * It will first compare the status (using UnambiguousCvTermComparator) and then will check if the position is undetermined. It will then check the start and the end.
 * - Two positions which are null are equals
 * - The position which is not null is before null.
 * - Use UnambiguousCvTermComparator to compare first the position status.
 * - If position status are equals, the undetermined positions are always coming after the determined positions.
 * - If both positions have same status and are undetermined, they are equals
 * - If both positions are not undetermined, compare the start position first and then the end position
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class UnambiguousPositionComparator extends PositionComparator {

    /**
     * Creates a new positionComparator with UnambiguousCvTermComparator
     *
     */
    public UnambiguousPositionComparator() {
        super(new DefaultCvTermComparator());
    }

    @Override
    public UnambiguousCvTermComparator getStatusComparator() {
        return (UnambiguousCvTermComparator) this.statusComparator;
    }

    @Override
    /**
     * It will first compare the status (using UnambiguousCvTermComparator) and then will check if the position is undetermined. It will then check the start and the end.
     * - Two positions which are null are equals
     * - The position which is not null is before null.
     * - Use UnambiguousCvTermComparator to compare first the position status.
     * - If position status are equals, the undetermined positions are always coming after the determined positions.
     * - If both positions have same status and are undetermined, they are equals
     * - If both positions are not undetermined, compare the start position first and then the end position
     */
    public int compare(Position position1, Position position2) {
        return super.compare(position1, position2);
    }
}
