/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


import java.io.Serializable;

/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="name" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="nameAc">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */

public class Attribute implements Serializable {

    /**
     * Valued held by the attribute.
     */
    private String value;

    /**
     * Name of the attribute. required in the PSI-MI 2.5 schema.
     */
    private String name;

    /**
     * MI reference of the name.
     */
    private String nameAc;

    //////////////////////////////
    // Constructors

    public Attribute() {
    }

    public Attribute( String name ) {
        setName( name );
    }

    public Attribute( String name, String value ) {
        setName( name );
        setValue( value );
    }

    public Attribute( String nameAc, String name, String value ) {
        setNameAc( nameAc );
        setName( name );
        setValue( value );
    }

    //////////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link String }
     */
    public void setValue( String value ) {
        this.value = value;
    }

    /**
     * Check if the optional value is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasValue() {
        return !( value == null || value.trim().length() == 0 );
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     */
    public void setName( String value ) {
        this.name = value;
    }

    /**
     * Check if the optional nameAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasNameAc() {
        return nameAc != null;
    }

    /**
     * Gets the value of the nameAc property.
     *
     * @return possible object is {@link String }
     */
    public String getNameAc() {
        return nameAc;
    }

    /**
     * Sets the value of the nameAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setNameAc( String value ) {
        this.nameAc = value;
    }

    //////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Attribute" );
        sb.append( "{value='" ).append( value ).append( '\'' );
        sb.append( ", name='" ).append( name ).append( '\'' );
        sb.append( ", nameAc='" ).append( nameAc ).append( '\'' );
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

        final Attribute attribute = ( Attribute ) o;

        // TODO name is not null
        if ( !name.equals( attribute.name ) ) {
            return false;
        }
        if ( nameAc != null ? !nameAc.equals( attribute.nameAc ) : attribute.nameAc != null ) {
            return false;
        }
        if ( value != null ? !value.equals( attribute.value ) : attribute.value != null ) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = ( value != null ? value.hashCode() : 0 );
        result = 29 * result + name.hashCode();
        result = 29 * result + ( nameAc != null ? nameAc.hashCode() : 0 );
        return result;
    }
}