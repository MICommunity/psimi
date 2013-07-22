package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.utils.PositionUtils;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousRangeAndResultingSequenceComparator;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Xml implementation of Range
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "baseLocation", propOrder = {
        "startStatus",
        "beginInterval",
        "beginPosition",
        "endStatus",
        "endInterval",
        "endPosition",
        "link"
})
public class XmlRange implements Range, Serializable{
    private AbstractXmlPosition start;
    private AbstractXmlPosition end;
    private boolean isLink;

    private ResultingSequence resultingSequence;
    private Participant participant;

    public XmlRange(){

    }

    public XmlRange(Position start, Position end){
        setPositions(start, end);
    }

    public XmlRange(Position start, Position end, boolean isLink){
        this(start, end);
        this.isLink = isLink;
    }

    public XmlRange(Position start, Position end, ResultingSequence resultingSequence){
        this(start, end);
        this.resultingSequence = resultingSequence;
    }

    public XmlRange(Position start, Position end, boolean isLink, ResultingSequence resultingSequence){
        this(start, end, isLink);
        this.resultingSequence = resultingSequence;
    }

    public XmlRange(Position start, Position end, Participant participant){
        this(start, end);
        this.participant = participant;
    }

    public XmlRange(Position start, Position end, boolean isLink, Participant participant){
        this(start, end, isLink);
        this.participant = participant;
    }

    /**
     * Gets the value of the startStatus property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "startStatus", required = true)
    public XmlCvTerm getStartStatus() {
        return getStart().getStatus();
    }

    /**
     * Sets the value of the startStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    public void setStartStatus(XmlCvTerm value) {
        getStart().setStatus(value);
    }

    @XmlTransient
    public AbstractXmlPosition getStart() {
        if (start == null){
           start = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), null, false);
        }
        return start;
    }

    /**
     * Gets the value of the beginInterval property.
     *
     * @return
     *     possible object is
     *     {@link XmlInterval }
     *
     */
    @XmlElement(name = "beginInterval")
    public XmlInterval getBeginInterval() {
        if (start instanceof XmlInterval){
            return (XmlInterval) start;
        }
        return null;
    }

    /**
     * Sets the value of the beginInterval property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlInterval }
     *
     */
    public void setBeginInterval(XmlInterval value) {
        this.start = value;
    }

    /**
     * Gets the value of the begin property.
     *
     * @return
     *     possible object is
     *     {@link Position }
     *
     */
    @XmlElement(name = "begin")
    public Position getBeginPosition() {
        if (start instanceof XmlPosition){
            return (XmlPosition) start;
        }
        return null;
    }

    /**
     * Sets the value of the begin property.
     *
     * @param value
     *     allowed object is
     *     {@link Position }
     *
     */
    public void setBeginPosition(XmlPosition value) {
        this.start = value;
    }

    /**
     * Gets the value of the endStatus property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "endStatus", required = true)
    public XmlCvTerm getEndStatus() {
        return getEnd().getStatus();
    }

    /**
     * Sets the value of the endStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    public void setEndStatus(XmlCvTerm value) {
        getEnd().setStatus(value);
    }

    /**
     * Gets the value of the endInterval property.
     *
     * @return
     *     possible object is
     *     {@link XmlInterval }
     *
     */
    @XmlElement(name = "endInterval")
    public XmlInterval getEndInterval() {
        if (end instanceof XmlInterval){
            return (XmlInterval) end;
        }
        return null;
    }

    /**
     * Sets the value of the endInterval property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlInterval }
     *
     */
    public void setEndInterval(XmlInterval value) {
        this.end = value;
    }

    /**
     * Gets the value of the end property.
     *
     * @return
     *     possible object is
     *     {@link XmlPosition }
     *
     */
    @XmlElement(name = "end")
    public XmlPosition getEndPosition() {
        if (end instanceof XmlPosition){
            return (XmlPosition) end;
        }
        return null;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlPosition }
     *
     */
    public void setEndPosition(XmlPosition value) {
        this.end = value;
    }

    @XmlTransient
    public AbstractXmlPosition getEnd() {
        if (end == null){
            end = new XmlPosition(new XmlCvTerm(Position.UNDETERMINED, Position.UNDETERMINED_MI), null, false);
        }
        return this.end;
    }

    /**
     * Gets the value of the isLink property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    @XmlElement(name = "isLink", defaultValue = "false")
    public boolean isLink() {
        return false;
    }

    public void setLink(boolean link) {
        this.isLink = link;
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
        if (start instanceof AbstractXmlPosition){
            this.start = (AbstractXmlPosition) start;
        }
        // we have xml interval
        else if (start.getEnd() != start.getStart() || PositionUtils.isFuzzyRange(start)){
            this.start = new XmlInterval(new XmlCvTerm(start.getStatus().getShortName(), start.getStatus().getMIIdentifier()), start.getStart(), start.getEnd(), start.isPositionUndetermined());
        }
        // we have xml position
        else{
            this.start = new XmlPosition(new XmlCvTerm(start.getStatus().getShortName(), start.getStatus().getMIIdentifier()), start.getStart(), start.isPositionUndetermined());
        }

        if (end instanceof AbstractXmlPosition){
            this.end = (AbstractXmlPosition) end;
        }
        // we have xml interval
        else if (end.getEnd() != end.getStart() || PositionUtils.isFuzzyRange(end)){
            this.end = new XmlInterval(new XmlCvTerm(end.getStatus().getShortName(), end.getStatus().getMIIdentifier()), end.getStart(), end.getEnd(), end.isPositionUndetermined());
        }
        // we have xml position
        else {
            this.end = new XmlPosition(new XmlCvTerm(end.getStatus().getShortName(), end.getStatus().getMIIdentifier()), end.getStart(), end.isPositionUndetermined());
        }
    }

    @XmlTransient
    public ResultingSequence getResultingSequence() {
        return this.resultingSequence;
    }

    public void setResultingSequence(ResultingSequence resultingSequence) {
        this.resultingSequence = resultingSequence;
    }

    @XmlTransient
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
        return getStart().toString() + " - " + getEnd().toString() + (isLink ? "(linked)" : "");
    }
}
