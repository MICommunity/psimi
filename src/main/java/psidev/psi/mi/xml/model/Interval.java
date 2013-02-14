/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


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

public class Interval implements Serializable {

    private long begin;

    private long end;

    ///////////////////////////
    // Constructors

    public Interval() {
    }

    public Interval( long begin, long end ) {
        this.begin = begin;
        this.end = end;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the begin property.
     *
     * @return possible object is {@link long }
     */
    public long getBegin() {
        return begin;
    }

    /**
     * Sets the value of the begin property.
     *
     * @param value allowed object is {@link long }
     */
    public void setBegin( long value ) {
        this.begin = value;
    }

    /**
     * Gets the value of the end property.
     *
     * @return possible object is {@link long }
     */
    public long getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value allowed object is {@link long }
     */
    public void setEnd( long value ) {
        this.end = value;
    }

    ////////////////////////
    // Object override

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Interval" );
        sb.append( "{begin=" ).append( begin );
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

        if ( begin != interval.begin ) {
            return false;
        }
        if ( end != interval.end ) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = ( int ) ( begin ^ ( begin >>> 32 ) );
        result = 29 * result + ( int ) ( end ^ ( end >>> 32 ) );
        return result;
    }
}