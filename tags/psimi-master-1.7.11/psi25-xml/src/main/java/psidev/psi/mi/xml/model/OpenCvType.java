/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Allows to reference an external controlled vocabulary, or to directly include a value if no suitable external
 * definition is available.
 * <p/>
 * <p>Java class for openCvType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="openCvType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
public abstract class OpenCvType extends CvType {

    private Collection<Attribute> attributes;

    ///////////////////////////
    // Constructors

    protected OpenCvType() {
    }

    protected OpenCvType( Collection<Attribute> attributes ) {
        this.attributes = attributes;
    }

    protected OpenCvType( Names names, Xref xref, Collection<Attribute> attributes ) {
        super( names, xref );
        this.attributes = attributes;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Check if the optional attributes is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasAttributes() {
        return ( attributes != null ) && ( !attributes.isEmpty() );
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

    //////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder( 128 );
        sb.append( "OpenCvType" );
        sb.append( "{attributes=" ).append( attributes );
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

        final OpenCvType that = ( OpenCvType ) o;

        if ( attributes != null ? !attributes.equals( that.attributes ) : that.attributes != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return ( attributes != null ? attributes.hashCode() : 0 );
    }
}