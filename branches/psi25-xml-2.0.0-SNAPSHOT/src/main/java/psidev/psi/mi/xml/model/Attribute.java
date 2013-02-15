/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

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

public class Attribute extends DefaultAnnotation implements Serializable {

    private static String UNSPECIFIED = "unspecified";

    //////////////////////////////
    // Constructors

    public Attribute() {
        super(new DefaultCvTerm(UNSPECIFIED));
    }

    public Attribute( String name ) {
        super(new DefaultCvTerm(name != null ? name : UNSPECIFIED));
    }

    public Attribute( String name, String value ) {
        super(new DefaultCvTerm(name != null ? name : UNSPECIFIED), value);
    }

    public Attribute( String nameAc, String name, String value ) {
        super(new DefaultCvTerm(name != null ? name : UNSPECIFIED, nameAc), value);
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
        return this.topic != null ? this.topic.getShortName() : null;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     */
    public void setName( String value ) {
        if (value == null){
            if (this.topic != null && this.topic.getMIIdentifier() != null){
                topic.setShortName(UNSPECIFIED);
            }
            else {
                this.topic = new DefaultCvTerm(UNSPECIFIED);
            }
        }
        else if (this.topic == null){
            this.topic = new DefaultCvTerm(value);
        }
        else {
            this.topic.setShortName(value);
        }
    }

    /**
     * Check if the optional nameAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasNameAc() {
        return this.topic != null && this.topic.getMIIdentifier() != null;
    }

    /**
     * Gets the value of the nameAc property.
     *
     * @return possible object is {@link String }
     */
    public String getNameAc() {
        return this.topic != null ? this.topic.getMIIdentifier() : null;
    }

    /**
     * Sets the value of the nameAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setNameAc( String value ) {
        if (value == null){
            if (this.topic != null){
                topic.setMIIdentifier(null);
            }
            else {
                this.topic = new DefaultCvTerm(UNSPECIFIED);
            }
        }
        else if (this.topic == null){
            this.topic = new DefaultCvTerm(UNSPECIFIED, value);
        }
        else {
            this.topic.setMIIdentifier(value);
        }
    }

    //////////////////////////
    // Object's override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Attribute" );
        sb.append( "{value='" ).append( value ).append( '\'' );
        sb.append( ", name='" ).append( getName() ).append( '\'' );
        sb.append( ", nameAc='" ).append( getNameAc() ).append( '\'' );
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
        if ( getName() != null && !getName().equals(attribute.getName()) ) {
            return false;
        }
        if ( getNameAc() != null ? !getNameAc().equals(attribute.getNameAc()) : attribute.getNameAc() != null ) {
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
        result = 29 * result + getName().hashCode();
        result = 29 * result + ( getNameAc() != null ? getNameAc().hashCode() : 0 );
        return result;
    }
}