package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;

import java.util.Comparator;

/**
 * Simple Position comparator.
 *
 * It will first compare the status and then will check if the position is undetermined. It will then check the start and the end.
 * - Two positions which are null are equals
 * - The position which is not null is before null.
 * - An undetermined position always comes after a determined position
 * - Two undetermined positions with same status are equals (no need to look at the positions start and end)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class PositionComparator implements Comparator<Position>{

    protected Comparator<CvTerm> statusComparator;

    /**
     * Creates a new positionComparator
     * @param statusComparator : the status comparator is required for comparing the position status
     */
    public PositionComparator(Comparator<CvTerm> statusComparator){
        if (statusComparator == null){
            throw new IllegalArgumentException("The status comparator is required for comparing the position status. It cannot be null");
        }
        this.statusComparator = statusComparator;
    }

    public Comparator<CvTerm> getStatusComparator() {
        return statusComparator;
    }

    /**
     * It will first compare the status and then will check if the position is undetermined. It will then check the start and the end.
     * - Two positions which are null are equals
     * - The position which is not null is before null.
     * - An undetermined position always comes after a determined position
     * - Two undetermined positions with same status are equals (no need to look at the positions start and end)
     * @param position1
     * @param position2
     * @return
     */
    public int compare(Position position1, Position position2) {

        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (position1 == null && position2 == null){
            return EQUAL;
        }
        else if (position1 == null){
            return AFTER;
        }
        else if (position2 == null){
            return BEFORE;
        }
        else {

            CvTerm status1 = position1.getStatus();
            CvTerm status2 = position2.getStatus();

            int comp = statusComparator.compare(status1, status2);
            if (comp != 0){
                return comp;
            }

            // undetermined positions are after positions with start/end values
            if (position1.isPositionUndetermined() && !position2.isPositionUndetermined()){
                return AFTER;
            }
            else if (!position1.isPositionUndetermined() && position2.isPositionUndetermined()){
                return BEFORE;
            }
            // both undetermined with same status, no need to check positions
            else if (position1.isPositionUndetermined() && position2.isPositionUndetermined()){
                return EQUAL;
            }
            else {
                // compare start and then end

                long start1 = position1.getStart();
                long start2 = position2.getStart();

                if (start1 < start2){
                    return BEFORE;
                }
                else if (start1 > start2){
                    return AFTER;
                }
                else {
                    long end1 = position1.getEnd();
                    long end2 = position2.getEnd();

                    if (end1 < end2){
                        return BEFORE;
                    }
                    else if (end1 > end2){
                        return AFTER;
                    }
                    else {
                        return EQUAL;
                    }
                }
            }
        }
    }
}
