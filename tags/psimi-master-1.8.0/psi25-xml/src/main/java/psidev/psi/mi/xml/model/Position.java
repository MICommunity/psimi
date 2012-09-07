/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


/**
 * <p>Java class for positionType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="positionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Position {

    private long position;

    ///////////////////////////
    // Constructors

    public Position() {
    }

    public Position( long position ) {
        setPosition( position );
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the position property.
     *
     * @return the position
     */
    public long getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     *
     * @param position the position
     */
    public void setPosition( long position ) {
        if ( position < 0 ) {
            throw new IllegalArgumentException( "position cannot be negative." );
        }
        this.position = position;
    }

    /////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Position" );
        sb.append( "{position=" ).append( position );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final Position position1 = ( Position ) o;

        if ( position != position1.position ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return ( int ) ( position ^ ( position >>> 32 ) );
    }
}