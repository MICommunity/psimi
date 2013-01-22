package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

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

    private static UnambiguousPositionComparator unambiguousPositionComparator;
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

    /**
     * Use UnabmbiguousPositionComparator to know if two positions are equals.
     * @param pos1
     * @param pos2
     * @return true if the two positions are equal
     */
    public static boolean areEquals(Position pos1, Position pos2){
        if (unambiguousPositionComparator == null){
            unambiguousPositionComparator = new UnambiguousPositionComparator();
        }

        return unambiguousPositionComparator.compare(pos1, pos2) == 0;
    }

    /**
     *
     * @param pos
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Position pos){
        if (unambiguousPositionComparator == null){
            unambiguousPositionComparator = new UnambiguousPositionComparator();
        }

        if (pos == null){
            return 0;
        }

        int hashcode = 31;
        CvTerm status = pos.getStatus();
        hashcode = 31*hashcode + unambiguousPositionComparator.getStatusComparator().hashCode(status);
        hashcode = 31*hashcode + pos.getStart();
        hashcode = 31*hashcode + pos.getEnd();

        return hashcode;
    }
}
