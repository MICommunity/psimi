/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */


package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;


/**
 * Describes a molecular interactor.
 * <p/>
 * <p>Java class for interactorElementType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="interactorElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="interactorType" type="{net:sf:psidev:mi}cvType"/>
 *         &lt;element name="organism" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{net:sf:psidev:mi}bioSourceType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="sequence" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Interactor implements HasId, NamesContainer, XrefContainer, AttributeContainer {

    private Names names;

    private Xref xref;

    private InteractorType interactorType;

    private Organism organism;

    private String sequence;

    private Collection<Attribute> attributes;

    private int id;

    ///////////////////////////
    // Constructors

    public Interactor() {
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId( int value ) {
        this.id = value;
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
     * Gets the value of the interactorType property.
     *
     * @return possible object is {@link CvType }
     */
    public InteractorType getInteractorType() {
        return interactorType;
    }

    /**
     * Sets the value of the interactorType property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setInteractorType( InteractorType value ) {
        this.interactorType = value;
    }

    /**
     * Check if the optional organism is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasOrganism() {
        return organism != null;
    }

    /**
     * Gets the value of the organism property.
     *
     * @return possible object is {@link Organism }
     */
    public Organism getOrganism() {
        return organism;
    }

    /**
     * Sets the value of the organism property.
     *
     * @param value allowed object is {@link Organism }
     */
    public void setOrganism( Organism value ) {
        this.organism = value;
    }

    /**
     * Check if the optional sequence is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasSequence() {
        return sequence != null && sequence.trim().length() > 0;
    }

    /**
     * Gets the value of the sequence property.
     *
     * @return possible object is {@link String }
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * Sets the value of the sequence property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSequence( String value ) {
        this.sequence = value;
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

    /////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Interactor" );
        sb.append( "{ id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", interactorType=" ).append( interactorType );
        sb.append( ", organism=" ).append( organism );
        sb.append( ", sequence='" ).append( sequence ).append( '\'' );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Interactor that = ( Interactor ) o;

        if ( interactorType != null ? !interactorType.equals( that.interactorType ) : that.interactorType != null )
            return false;
        if ( organism != null ? !organism.equals( that.organism ) : that.organism != null ) return false;
        //if (names != null ? !names.equals(that.names) : that.names != null) return false;
        if ( xref != null ? !xref.equals( that.xref ) : that.xref != null ) return false;
        if ( sequence != null ? !sequence.equals( that.sequence ) : that.sequence != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31;
        //result = (names != null ? names.hashCode() : 0);
        result = 31 * result + ( xref != null ? xref.hashCode() : 0 );
        result = 31 * result + ( interactorType != null ? interactorType.hashCode() : 0 );
        result = 31 * result + ( organism != null ? organism.hashCode() : 0 );
        result = 31 * result + ( sequence != null ? sequence.hashCode() : 0 );
        return result;
    }
}