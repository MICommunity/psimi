/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *         &lt;element name="bibref" type="{net:sf:psidev:mi}bibrefType" minOccurs="0"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="release">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Source implements NamesContainer, XrefContainer, AttributeContainer {

    private Names names;

    private Bibref bibref;

    private Xref xref;

    private Collection<Attribute> attributes;

    private String release;

    private Date releaseDate;

    ///////////////////////////
    // Constructors

    public Source() {
    }

    ///////////////////////////
    // Getters and Setters

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
     * Check if the optional bibref is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBibref() {
        return bibref != null;
    }

    /**
     * Gets the value of the bibref property.
     *
     * @return possible object is {@link Bibref }
     */
    public Bibref getBibref() {
        return bibref;
    }

    /**
     * Sets the value of the bibref property.
     *
     * @param value allowed object is {@link Bibref }
     */
    public void setBibref( Bibref value ) {
        this.bibref = value;
    }

    /**
     * Check if the optional xref is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasXref() {
        return xref != null;
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

    /**
     * Check if the optional release is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasRelease() {
        return release != null;
    }

    /**
     * Gets the value of the release property.
     *
     * @return possible object is {@link String }
     */
    public String getRelease() {
        return release;
    }

    /**
     * Sets the value of the release property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRelease( String value ) {
        this.release = value;
    }

    /**
     * Check if the optional releaseDate is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasReleaseDate() {
        return releaseDate != null;
    }

    /**
     * Gets the value of the releaseDate property.
     *
     * @return possible object is {@link Date }
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the value of the releaseDate property.
     *
     * @param value allowed object is {@link Date }
     */
    public void setReleaseDate( Date value ) {
        this.releaseDate = value;
    }

    /////////////////////
    // Object override


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Source" );
        sb.append( "{names=" ).append( names );
        sb.append( ", bibref=" ).append( bibref );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( ", release='" ).append( release ).append( '\'' );
        sb.append( ", releaseDate=" ).append( releaseDate );
        sb.append( '}' );
        return sb.toString();
    }

    //TODO equals and hashCode
}