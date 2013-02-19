/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.impl.DefaultRange;
import psidev.psi.mi.jami.utils.clone.CvTermCloner;

/**
 * A location on a sequence. Both begin and end can be a defined position, a fuzzy position, or undetermined.
 * <p/>
 * <p>Java class for baseLocationType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="baseLocationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;sequence>
 *           &lt;element name="startStatus" type="{net:sf:psidev:mi}cvType"/>
 *           &lt;choice minOccurs="0">
 *             &lt;element name="begin" type="{net:sf:psidev:mi}positionType"/>
 *             &lt;element name="beginInterval" type="{net:sf:psidev:mi}intervalType"/>
 *           &lt;/choice>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="endStatus" type="{net:sf:psidev:mi}cvType"/>
 *           &lt;choice minOccurs="0">
 *             &lt;element name="end" type="{net:sf:psidev:mi}positionType"/>
 *             &lt;element name="endInterval" type="{net:sf:psidev:mi}intervalType"/>
 *           &lt;/choice>
 *         &lt;/sequence>
 *         &lt;element name="isLink" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Range extends DefaultRange{

    ///////////////////////////
    // Constructors

    private boolean isBeginInterval=false;
    private boolean isEndInterval=false;

    public Range() {
        super(new Position(), new Position());
    }

    public Range( RangeStatus startStatus, Position begin, RangeStatus endStatus, Position end ) {
        super(begin, end);
        getBegin().setStatus(startStatus);
        getEnd().setStatus(endStatus);
    }

    public Range( RangeStatus startStatus, Interval beginInterval, RangeStatus endStatus, Position end ) {
        super(beginInterval, end);
        getBeginInterval().setStatus(startStatus);
        getEndInterval().setStatus(endStatus);
        isBeginInterval = true;
    }

    public Range( RangeStatus startStatus, RangeStatus endStatus, Position begin, Interval endInterval ) {
        super(begin, endInterval);
        getBegin().setStatus(startStatus);
        getEndInterval().setStatus(endStatus);
        isEndInterval = true;
    }

    public Range( Interval endInterval, Interval beginInterval, RangeStatus startStatus, RangeStatus endStatus ) {
        super(beginInterval, endInterval);
        getBeginInterval().setStatus(startStatus);
        getEndInterval().setStatus(endStatus);
        isBeginInterval = true;
        isEndInterval = true;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the startStatus property.
     *
     * @return possible object is {@link RangeStatus }
     */
    public RangeStatus getStartStatus() {
        return (RangeStatus) start.getStatus();
    }

    /**
     * Sets the start of the startStatus property.
     *
     * @param start allowed object is {@link RangeStatus }
     */
    public void setStartStatus( RangeStatus start ) {
        if (isBeginInterval){
            getBeginInterval().setStatus(start);
        }
        else {
            getBegin().setStatus(start);
        }
    }

    /**
     * Check if the optional begin is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBegin() {
        return !isBeginInterval;
    }

    /**
     * Gets the value of the begin property.
     *
     * @return possible object is {@link Position }
     */
    public Position getBegin() {
        return isBeginInterval ? null : (Position) start;
    }

    /**
     * Sets the value of the begin property.
     *
     * @param value allowed object is {@link Position }
     */
    public void setBegin( Position value ) {
        if (value == null && !isBeginInterval){
            this.start = new Position();
        }
        else if (value != null) {
            this.start = value;
            isBeginInterval = false;
        }
    }

    /**
     * Check if the optional beginInterval is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBeginInterval() {
        return isBeginInterval;
    }

    /**
     * Gets the value of the beginInterval property.
     *
     * @return possible object is {@link Interval }
     */
    public Interval getBeginInterval() {
        return isBeginInterval? (Interval) start : null;
    }

    /**
     * Sets the value of the beginInterval property.
     *
     * @param value allowed object is {@link Interval }
     */
    public void setBeginInterval( Interval value ) {
        if (value == null && isBeginInterval){
            this.start = new Interval();
        }
        else if (value != null) {
            this.start = value;
            isBeginInterval = true;
        }
    }

    /**
     * Gets the value of the endStatus property.
     *
     * @return possible object is {@link RangeStatus }
     */
    public RangeStatus getEndStatus() {
        return (RangeStatus) end.getStatus();
    }

    /**
     * Sets the end of the endStatus property.
     *
     * @param end allowed object is {@link RangeStatus }
     */
    public void setEndStatus( RangeStatus end ) {
        if (isEndInterval){
            getEndInterval().setStatus(end);
        }
        else {
            getEnd().setStatus(end);
        }
    }

    /**
     * Check if the optional end is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasEnd() {
        return !isEndInterval;
    }

    /**
     * Gets the value of the end property.
     *
     * @return possible object is {@link Position }
     */
    public Position getEnd() {
        return isEndInterval ? null : (Position) this.end;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value allowed object is {@link Position }
     */
    public void setEnd( Position value ) {
        if (value == null && !isEndInterval){
            this.end = new Position();
        }
        else if (value != null) {
            this.end = value;
            isEndInterval = false;
        }
    }

    /**
     * Check if the optional endInterval is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasEndInterval() {
        return isEndInterval;
    }

    /**
     * Gets the value of the endInterval property.
     *
     * @return possible object is {@link Interval }
     */
    public Interval getEndInterval() {
        return isEndInterval ? (Interval) this.end : null;
    }

    /**
     * Sets the value of the endInterval property.
     *
     * @param value allowed object is {@link Interval }
     */
    public void setEndInterval( Interval value ) {
        if (value == null && isEndInterval){
            this.end = new Interval();
        }
        else if (value != null) {
            this.end = value;
            isEndInterval = true;
        }
    }

    /**
     * Gets the value of the isLink property.
     *
     * @return possible object is {@link Boolean }
     */
    public boolean isLink() {
        return isLink;
    }

    /**
     * Sets the value of the isLink property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setIsLink( boolean value ) {
        this.isLink = value;
    }

    //////////////////////
    // Object override


    @Override
    public void setPositions(psidev.psi.mi.jami.model.Position start, psidev.psi.mi.jami.model.Position end) {
        psidev.psi.mi.jami.model.Position newStart = start;
        psidev.psi.mi.jami.model.Position newEnd = end;

        if (!(start instanceof Position) && !(start instanceof Interval)){
            if (start.getStart() != start.getEnd()){
                newStart = new Interval(start.getStart(), start.getEnd());
                CvTermCloner.copyAndOverrideCvTermProperties(start.getStatus(), newStart.getStatus());
            }
            else {
                newStart = new Position(start.getStart());
                CvTermCloner.copyAndOverrideCvTermProperties(start.getStatus(), newStart.getStatus());
            }
        }
        if (!(end instanceof Position) && !(end instanceof Interval)){
            if (end.getStart() != end.getEnd()){
                newEnd = new Interval(end.getStart(), end.getEnd());
                CvTermCloner.copyAndOverrideCvTermProperties(end.getStatus(), newEnd.getStatus());
            }
            else {
                newEnd = new Position(end.getStart());
                CvTermCloner.copyAndOverrideCvTermProperties(end.getStatus(), newEnd.getStatus());
            }
        }

        super.setPositions(newStart, newEnd);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Range" );
        sb.append( "{startStatus=" ).append( getStartStatus() );
        sb.append( ", begin=" ).append( getBegin() );
        sb.append( ", beginInterval=" ).append( getBeginInterval() );
        sb.append( ", endStatus=" ).append( getEndStatus() );
        sb.append( ", end=" ).append( getEnd() );
        sb.append( ", endInterval=" ).append( getEndInterval() );
        sb.append( ", isLink=" ).append( isLink );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        final Range range = ( Range ) o;

        if ( getBegin() != null ? !getBegin().equals(range.getBegin()) : range.getBegin() != null ) return false;
        if ( getBeginInterval() != null ? !getBeginInterval().equals(range.getBeginInterval()) : range.getBeginInterval() != null )
            return false;
        if ( getEnd() != null ? !getEnd().equals(range.getEnd()) : range.getEnd() != null ) return false;
        if ( getEndInterval() != null ? !getEndInterval().equals(range.getEndInterval()) : range.getEndInterval() != null ) return false;
        if ( !getEndStatus().equals(range.getEndStatus()) ) return false;
        if ( isLink != range.isLink ) return false;
        if ( !getStartStatus().equals(range.getStartStatus()) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = getStartStatus().hashCode();
        result = 29 * result + ( getBegin() != null ? getBegin().hashCode() : 0 );
        result = 29 * result + ( getBeginInterval() != null ? getBeginInterval().hashCode() : 0 );
        result = 29 * result + getEndStatus().hashCode();
        result = 29 * result + ( getEnd() != null ? getEnd().hashCode() : 0 );
        result = 29 * result + ( getEndInterval() != null ? getEndInterval().hashCode() : 0 );
        result = 29 * result + ( isLink ? 1 : 0 );
        return result;
    }
}