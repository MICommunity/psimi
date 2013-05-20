package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousRangeComparator;

import java.io.Serializable;

/**
 * Default implementation for Range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultRange implements Range, Serializable {

    private Position start;
    private Position end;
    private boolean isLink;

    public DefaultRange(Position start, Position end){
        setPositions(start, end);
    }

    public DefaultRange(Position start, Position end, boolean isLink){
        this(start, end);
        this.isLink = isLink;
    }

    public Position getStart() {
        return this.start;
    }

    public Position getEnd() {
        return this.end;
    }

    public void setPositions(Position start, Position end) {
        if (start == null){
            throw new IllegalArgumentException("The start position is required and cannot be null");
        }
        if (end == null){
            throw new IllegalArgumentException("The end position is required and cannot be null");
        }

        if (start.getEnd() != 0 && end.getStart() != 0 && start.getEnd() > end.getStart()){
            throw new IllegalArgumentException("The start position cannot be ending before the end position");
        }
        this.start = start;
        this.end = end;
    }

    public boolean isLink() {
        return this.isLink;
    }

    public void setLink(boolean link) {
        this.isLink = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Range)){
            return false;
        }

        return UnambiguousRangeComparator.areEquals(this, (Range) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousRangeComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return start.toString() + " - " + end.toString() + (isLink ? "(linked)" : "");
    }
}
