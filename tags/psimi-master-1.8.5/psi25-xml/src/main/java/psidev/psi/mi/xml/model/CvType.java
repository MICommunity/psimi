/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


/**
 * Reference to an external controlled vocabulary.
 * <p/>
 * <p>Java class for cvType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="cvType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public abstract class CvType implements NamesContainer, XrefContainer {

    private Names names;

    private Xref xref;

    ///////////////////////////
    // Getters and Setters

    protected CvType() {
    }

    protected CvType( Names names, Xref xref ) {
        setNames( names );
        setXref( xref );
    }

    /**
     * Check if the optional names is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasNames() {
        return names != null;
    }


    /**
     * Gets the value of the names property.
     *
     * @return possible object is {@link Names }
     */
    public Names getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     *
     * @param value allowed object is {@link Names }
     */
    public void setNames( Names value ) {
        this.names = value;
    }

    /**
     * Gets the value of the xref property.
     *
     * @return possible object is {@link Xref }
     */
    public Xref getXref() {
        return xref;
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value allowed object is {@link Xref }
     */
    public void setXref( Xref value ) {
        this.xref = value;
    }

    //////////////////////////
    // Object override


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "CvType" );
        sb.append( "{names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
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

        final CvType cvType = ( CvType ) o;

        if ( names != null ? !names.equals( cvType.names ) : cvType.names != null ) {
            return false;
        }
        if ( xref != null ? !xref.equals( cvType.xref ) : cvType.xref != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( names != null ? names.hashCode() : 0 );
        result = 29 * result + ( xref != null ? xref.hashCode() : 0 );
        return result;
    }
}