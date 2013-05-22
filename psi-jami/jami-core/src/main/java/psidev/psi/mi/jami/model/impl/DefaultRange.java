package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousRangeAndResultingSequenceComparator;

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

    private ResultingSequence resultingSequence;
    private Participant participant;

    public DefaultRange(Position start, Position end){
        setPositions(start, end);
    }

    public DefaultRange(Position start, Position end, boolean isLink){
        this(start, end);
        this.isLink = isLink;
    }

    public DefaultRange(Position start, Position end, ResultingSequence resultingSequence){
        this(start, end);
        this.resultingSequence = resultingSequence;
    }

    public DefaultRange(Position start, Position end, boolean isLink, ResultingSequence resultingSequence){
        this(start, end, isLink);
        this.resultingSequence = resultingSequence;
    }

    public DefaultRange(Position start, Position end, Participant participant){
        this(start, end);
        this.participant = participant;
    }

    public DefaultRange(Position start, Position end, boolean isLink, Participant participant){
        this(start, end, isLink);
        this.participant = participant;
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

    public ResultingSequence getResultingSequence() {
        return this.resultingSequence;
    }

    public void setResultingSequence(ResultingSequence resultingSequence) {
        this.resultingSequence = resultingSequence;
    }

    public Participant getParticipant() {
        return this.participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Range)){
            return false;
        }

        return UnambiguousRangeAndResultingSequenceComparator.areEquals(this, (Range) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousRangeAndResultingSequenceComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return start.toString() + " - " + end.toString() + (isLink ? "(linked)" : "");
    }
}
