package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Position;

/**
 * Default PositionComparator.
 * It will first compare the status (using DefaultCvTermComparator) and then will check if the position is undetermined. It will then check the start and the end.
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

public class DefaultPositionComparator extends PositionComparator {

    private static DefaultPositionComparator defaultPositionComparator;

    /**
     * Creates a new positionComparator with DefaultCvTermComparator
     *
     */
    public DefaultPositionComparator() {
        super(new DefaultCvTermComparator());
    }

    @Override
    public DefaultCvTermComparator getStatusComparator() {
        return (DefaultCvTermComparator) this.statusComparator;
    }

    @Override
    /**
     * It will first compare the status (using DefaultCvTermComparator) and then will check if the position is undetermined. It will then check the start and the end.
     * - Two positions which are null are equals
     * - The position which is not null is before null.
     * - Use DefaultCvTermComparator to compare first the position status.
     * - If position status are equals, the undetermined positions are always coming after the determined positions.
     * - If both positions have same status and are undetermined, they are equals
     * - If both positions are not undetermined, compare the start position first and then the end position
     */
    public int compare(Position position1, Position position2) {
        return super.compare(position1, position2);
    }

    /**
     * Use DefaultPositionComparator to know if two positions are equals.
     * @param pos1
     * @param pos2
     * @return true if the two positions are equal
     */
    public static boolean areEquals(Position pos1, Position pos2){
        if (defaultPositionComparator == null){
            defaultPositionComparator = new DefaultPositionComparator();
        }

        return defaultPositionComparator.compare(pos1, pos2) == 0;
    }
}
