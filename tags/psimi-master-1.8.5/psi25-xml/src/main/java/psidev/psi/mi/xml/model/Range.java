/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


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

public class Range {

    private RangeStatus startStatus;

    private Position begin;

    private Interval beginInterval;

    private RangeStatus endStatus;

    private Position end;

    private Interval endInterval;

    private Boolean isLink;

    ///////////////////////////
    // Constructors

    public Range() {
    }

    public Range( RangeStatus startStatus, Position begin, RangeStatus endStatus, Position end ) {
        setStartStatus( startStatus );
        setBegin( begin );
        setEndStatus( endStatus );
        setEnd( end );
    }

    public Range( RangeStatus startStatus, Interval beginInterval, RangeStatus endStatus, Position end ) {
        this.startStatus = startStatus;
        this.beginInterval = beginInterval;
        this.endStatus = endStatus;
        this.end = end;
    }

    public Range( RangeStatus startStatus, RangeStatus endStatus, Position begin, Interval endInterval ) {
        this.startStatus = startStatus;
        this.endStatus = endStatus;
        this.begin = begin;
        this.endInterval = endInterval;
    }

    public Range( Interval endInterval, Interval beginInterval, RangeStatus startStatus, RangeStatus endStatus ) {
        this.endInterval = endInterval;
        this.beginInterval = beginInterval;
        this.startStatus = startStatus;
        this.endStatus = endStatus;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the startStatus property.
     *
     * @return possible object is {@link RangeStatus }
     */
    public RangeStatus getStartStatus() {
        return startStatus;
    }

    /**
     * Sets the start of the startStatus property.
     *
     * @param start allowed object is {@link RangeStatus }
     */
    public void setStartStatus( RangeStatus start ) {
        this.startStatus = start;
    }

    /**
     * Check if the optional begin is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBegin() {
        return begin != null;
    }

    /**
     * Gets the value of the begin property.
     *
     * @return possible object is {@link Position }
     */
    public Position getBegin() {
        return begin;
    }

    /**
     * Sets the value of the begin property.
     *
     * @param value allowed object is {@link Position }
     */
    public void setBegin( Position value ) {
        this.begin = value;
    }

    /**
     * Check if the optional beginInterval is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBeginInterval() {
        return beginInterval != null;
    }

    /**
     * Gets the value of the beginInterval property.
     *
     * @return possible object is {@link Interval }
     */
    public Interval getBeginInterval() {
        return beginInterval;
    }

    /**
     * Sets the value of the beginInterval property.
     *
     * @param value allowed object is {@link Interval }
     */
    public void setBeginInterval( Interval value ) {
        this.beginInterval = value;
    }

    /**
     * Gets the value of the endStatus property.
     *
     * @return possible object is {@link RangeStatus }
     */
    public RangeStatus getEndStatus() {
        return endStatus;
    }

    /**
     * Sets the end of the endStatus property.
     *
     * @param end allowed object is {@link RangeStatus }
     */
    public void setEndStatus( RangeStatus end ) {
        this.endStatus = end;
    }

    /**
     * Check if the optional end is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasEnd() {
        return end != null;
    }

    /**
     * Gets the value of the end property.
     *
     * @return possible object is {@link Position }
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value allowed object is {@link Position }
     */
    public void setEnd( Position value ) {
        this.end = value;
    }

    /**
     * Check if the optional endInterval is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasEndInterval() {
        return endInterval != null;
    }

    /**
     * Gets the value of the endInterval property.
     *
     * @return possible object is {@link Interval }
     */
    public Interval getEndInterval() {
        return endInterval;
    }

    /**
     * Sets the value of the endInterval property.
     *
     * @param value allowed object is {@link Interval }
     */
    public void setEndInterval( Interval value ) {
        this.endInterval = value;
    }

    /**
     * Gets the value of the isLink property.
     *
     * @return possible object is {@link Boolean }
     */
    public boolean isLink() {
        if ( isLink == null ) {
            return false;
        }
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
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Range" );
        sb.append( "{startStatus=" ).append( startStatus );
        sb.append( ", begin=" ).append( begin );
        sb.append( ", beginInterval=" ).append( beginInterval );
        sb.append( ", endStatus=" ).append( endStatus );
        sb.append( ", end=" ).append( end );
        sb.append( ", endInterval=" ).append( endInterval );
        sb.append( ", isLink=" ).append( isLink );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        final Range range = ( Range ) o;

        if ( begin != null ? !begin.equals( range.begin ) : range.begin != null ) return false;
        if ( beginInterval != null ? !beginInterval.equals( range.beginInterval ) : range.beginInterval != null )
            return false;
        if ( end != null ? !end.equals( range.end ) : range.end != null ) return false;
        if ( endInterval != null ? !endInterval.equals( range.endInterval ) : range.endInterval != null ) return false;
        if ( !endStatus.equals( range.endStatus ) ) return false;
        if ( isLink != null ? !isLink.equals( range.isLink ) : range.isLink != null ) return false;
        if ( !startStatus.equals( range.startStatus ) ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = startStatus.hashCode();
        result = 29 * result + ( begin != null ? begin.hashCode() : 0 );
        result = 29 * result + ( beginInterval != null ? beginInterval.hashCode() : 0 );
        result = 29 * result + endStatus.hashCode();
        result = 29 * result + ( end != null ? end.hashCode() : 0 );
        result = 29 * result + ( endInterval != null ? endInterval.hashCode() : 0 );
        result = 29 * result + ( isLink != null ? isLink.hashCode() : 0 );
        return result;
    }
}