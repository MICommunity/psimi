/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */


package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;


/**
 * Bibliographic reference.
 * <p/>
 * <p>Java class for bibrefType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="bibrefType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType"/>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Bibref implements XrefContainer, AttributeContainer {

    private Xref xref;

    private Collection<Attribute> attributes;

    ///////////////////////////
    // Constructors

    public Bibref() {
    }

    public Bibref( Xref xref ) {
        setXref( xref );
    }

    public Bibref( Collection<Attribute> attributes ) {
        this.attributes = attributes;
    }

    ///////////////////////////
    // Getters and Setters

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

    public boolean hasAttributes() {
        if (attributes == null){
            return false;
        }
        return !attributes.isEmpty();
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return possible object is {@link Attribute }
     */
    public Collection<Attribute> getAttributes() {
        if ( attributes == null ) {
            attributes = new ArrayList<Attribute>();
        }
        return attributes;
    }

    /////////////////////////
    // Object override


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Bibref" );
        sb.append( "{xref=" ).append( xref );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        final Bibref bibref = ( Bibref ) o;

        if ( attributes != null ? !attributes.equals( bibref.attributes ) : bibref.attributes != null ) return false;
        if ( xref != null ? !xref.equals( bibref.xref ) : bibref.xref != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( xref != null ? xref.hashCode() : 0 );
        result = 29 * result + ( attributes != null ? attributes.hashCode() : 0 );
        return result;
    }
}