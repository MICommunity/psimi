package psidev.psi.mi.jami.utils.comparator.range;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default PositionComparator.
 * It will first compare the status (using DefaultCvTermComparator) and then will check if the position is undetermined. It will then check the start and the end.
 * - Two positions which are null are equals
 * - Use DefaultCvTermComparator to compare first the position status.
 * - If position status are equals, the undetermined positions are always coming after the determined positions.
 * - If both positions have same status and are undetermined, they are equals
 * - If both positions are not undetermined, compare the start position first and then the end position
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class DefaultPositionComparator {


    /**
     * Use DefaultPositionComparator to know if two positions are equals.
     * @param position1
     * @param position2
     * @return true if the two positions are equal
     */
    public static boolean areEquals(Position position1, Position position2){

        if (position1 == null && position2 == null){
            return true;
        }
        else if (position1 == null || position2 == null){
            return false;
        }
        else {

            CvTerm status1 = position1.getStatus();
            CvTerm status2 = position2.getStatus();

            if (!DefaultCvTermComparator.areEquals(status1, status2)){
                return false;
            }

            // undetermined positions are after positions with start/end values
            if (position1.isPositionUndetermined() && !position2.isPositionUndetermined()){
                return false;
            }
            else if (!position1.isPositionUndetermined() && position2.isPositionUndetermined()){
                return false;
            }
            // both undetermined with same status, no need to check positions
            else if (position1.isPositionUndetermined() && position2.isPositionUndetermined()){
                return true;
            }
            else {
                // compare start and then end

                long start1 = position1.getStart();
                long start2 = position2.getStart();

                if (start1 != start2){
                    return false;
                }
                else {
                    long end1 = position1.getEnd();
                    long end2 = position2.getEnd();

                    if (end1 != end2){
                        return false;
                    }
                    else {
                        return true;
                    }
                }
            }
        }
    }
}
