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
 *       &lt;attribute name="type">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="typeAc">
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

public class Alias implements Serializable {

    private String value;

    private String type;

    private String typeAc;

    ///////////////////////////
    // Constructors

    public Alias() {
    }

    public Alias( String value ) {
        setValue( value );
    }

    public Alias( String value, String type, String typeAc ) {
        setValue( value );
        setType( type );
        setTypeAc( typeAc );
    }

    ///////////////////////////
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
        return value != null && value.length() > 0;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link String }
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link String }
     */
    public void setType( String value ) {
        this.type = value;
    }

    /**
     * Check if the optional type is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasType() {
        return type != null;
    }

    /**
     * Gets the value of the typeAc property.
     *
     * @return possible object is {@link String }
     */
    public String getTypeAc() {
        return typeAc;
    }

    /**
     * Sets the value of the typeAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setTypeAc( String value ) {
        this.typeAc = value;
    }

    /**
     * Check if the optional typeAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasTypeAc() {
        return typeAc != null;
    }

    //////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Alias" );
        sb.append( "{value='" ).append( value ).append( '\'' );
        sb.append( ", type='" ).append( type ).append( '\'' );
        sb.append( ", typeAc='" ).append( typeAc ).append( '\'' );
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

        final Alias alias = ( Alias ) o;

        if ( type != null ? !type.equals( alias.type ) : alias.type != null ) {
            return false;
        }
        if ( typeAc != null ? !typeAc.equals( alias.typeAc ) : alias.typeAc != null ) {
            return false;
        }
        if ( value != null ? !value.equals( alias.value ) : alias.value != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( value != null ? value.hashCode() : 0 );
        result = 29 * result + ( type != null ? type.hashCode() : 0 );
        result = 29 * result + ( typeAc != null ? typeAc.hashCode() : 0 );
        return result;
    }
}