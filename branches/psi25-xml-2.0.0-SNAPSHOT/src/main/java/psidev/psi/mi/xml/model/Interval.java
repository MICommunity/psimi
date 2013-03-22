/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.utils.PositionUtils;

import java.io.Serializable;

/**
 * A interval on a sequence.
 * <p/>
 * <p>Java class for intervalType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="intervalType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="begin" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" />
 *       &lt;attribute name="end" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Interval implements psidev.psi.mi.jami.model.Position, Serializable, FileSourceContext {

    private RangeStatus status;
    private long start;
    private long end;
    private boolean isPositionUndetermined;

    private FileSourceLocator locator;

    ///////////////////////////
    // Constructors

    public Interval() {
        this.status = new RangeStatus();
        this.start = 0;
        this.end = 0;
        this.isPositionUndetermined = true;
    }

    public Interval( long begin, long end ) {
        this.status = new RangeStatus();
        this.start = begin;
        this.end = end;
        this.isPositionUndetermined = true;
    }

    ///////////////////////////
    // Getters and Setters

    public FileSourceLocator getSourceLocator() {
        return this.locator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
        this.locator = locator;
    }

    /**
     * Gets the value of the begin property.
     *
     * @return possible object is {@link long }
     */
    public long getBegin() {
        return start;
    }

    /**
     * Sets the value of the begin property.
     *
     * @param value allowed object is {@link long }
     */
    public void setBegin( long value ) {
        this.start = value;
    }

    public RangeStatus getStatus() {
        return status;
    }

    public long getStart() {
        return start;
    }

    /**
     * Gets the value of the end property.
     *
     * @return possible object is {@link long }
     */
    public long getEnd() {
        return end;
    }

    public boolean isPositionUndetermined() {
        return isPositionUndetermined;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value allowed object is {@link long }
     */
    public void setEnd( long value ) {
        this.end = value;
    }

    public void setStatus(RangeStatus status){
        if (status != null){
            this.status = status;
            isPositionUndetermined = (PositionUtils.isUndetermined(this) || PositionUtils.isCTerminalRange(this) || PositionUtils.isNTerminalRange(this));
        }
        else {
            this.status = new RangeStatus();
            this.isPositionUndetermined = true;
        }
    }

    ////////////////////////
    // Object override


    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Interval" );
        sb.append( "{begin=" ).append( start );
        sb.append( ", end=" ).append( end );
        sb.append( '}' );
        return sb.toString();
    }

    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final Interval interval = ( Interval ) o;

        if ( start != interval.start ) {
            return false;
        }
        if ( end != interval.end ) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = ( int ) ( start ^ ( start >>> 32 ) );
        result = 29 * result + ( int ) ( end ^ ( end >>> 32 ) );
        return result;
    }
}