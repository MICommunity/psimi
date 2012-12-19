package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;

import java.util.Comparator;

/**
 * Abstract class for Position comparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public abstract class AbstractPositionComparator<T extends AbstractCvTermComparator> implements Comparator<Position>{

    protected T statusComparator;

    public AbstractPositionComparator(){
        instantiateStatusComparator();
    }

    protected abstract void instantiateStatusComparator();

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

                int start1 = position1.getStart();
                int start2 = position2.getStart();

                if (start1 < start2){
                    return BEFORE;
                }
                else if (start1 > start2){
                    return AFTER;
                }
                else {
                    int end1 = position1.getEnd();
                    int end2 = position2.getEnd();

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
