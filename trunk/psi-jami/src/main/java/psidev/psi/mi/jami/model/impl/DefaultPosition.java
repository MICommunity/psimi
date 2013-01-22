package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousPositionComparator;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Default implementation for Position
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultPosition implements Position, Serializable {

    private CvTerm status;
    private int start;
    private int end;
    private boolean isPositionUndetermined;

    private static final Logger log = Logger.getLogger("DefaultPosition");

    public DefaultPosition(CvTerm status, int start, int end){
        if (status == null){
            throw new IllegalArgumentException("The position status is required and cannot be null");
        }
        this.status = status;

        isPositionUndetermined = (PositionUtils.isUndetermined(this) || PositionUtils.isCTerminalRange(this) || PositionUtils.isNTerminalRange(this));
        if (isPositionUndetermined){
            this.start = 0;
            this.end = 0;
            if (start != 0){
                log.warning("The exact position is undetermined so start should be 0. Will override given start " + start);
            }
            if (end != 0){
                log.warning("The exact position is undetermined so end should be 0. Will override given end " + end);
            }
        }
        else {
            if (start > end){
               throw new IllegalArgumentException("The start cannot be after the end.");
            }

            this.start = start;
            this.end = end;
        }
    }

    public CvTerm getStatus() {
        return this.status;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public boolean isPositionUndetermined() {
        return this.isPositionUndetermined;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Position)){
            return false;
        }

        return UnambiguousPositionComparator.areEquals(this, (Position) o);
    }

    @Override
    public String toString() {
        return status.toString() + ": " + start  + end;
    }

    @Override
    public int hashCode() {
        return UnambiguousPositionComparator.hashCode(this);
    }
}
