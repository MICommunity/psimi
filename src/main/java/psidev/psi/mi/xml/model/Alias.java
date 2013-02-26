/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.CvTerm;
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

public class Alias implements psidev.psi.mi.jami.model.Alias, Serializable {

    ///////////////////////////
    // Constructors

    private static String UNSPECIFIED = "unspecified";

    private CvTerm type;
    private String name;

    public Alias() {
        this.name = UNSPECIFIED;
    }

    public Alias( String value ) {
        this.name = value != null && value.length() > 0 ? value : UNSPECIFIED;
    }

    public Alias( String value, String type, String typeAc ) {
        this.name = value != null && value.length() > 0  ? value : UNSPECIFIED;

        if (type != null && typeAc != null){
            this.type = new DefaultCvTerm(type, typeAc);
        }
        else if (type != null){
            this.type = new DefaultCvTerm(type);
        }
        else if (typeAc != null){
            this.type = new DefaultCvTerm(UNSPECIFIED, typeAc);
        }
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link String }
     */
    public String getValue() {
        return name;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link String }
     */
    public void setValue( String value ) {
        this.name = value != null && value.length() > 0  ? value : UNSPECIFIED;
    }

    /**
     * Check if the optional value is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasValue() {
        return !UNSPECIFIED.equals(this.name);
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link String }
     */
    public String getAliasType() {
        return type != null ? type.getShortName() : null;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link String }
     */
    public void setType( String value ) {
        if (value == null){
            if (this.type != null && this.type.getMIIdentifier() != null){
                type.setShortName(UNSPECIFIED);
            }
            else {
                this.type = null;
            }
        }
        else if (this.type == null){
            this.type = new DefaultCvTerm(value);
        }
        else {
            this.type.setShortName(value);
        }
    }

    /**
     * Check if the optional type is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasType() {
        return this.type != null && !UNSPECIFIED.equals(type.getShortName());
    }

    /**
     * Gets the value of the typeAc property.
     *
     * @return possible object is {@link String }
     */
    public String getTypeAc() {
        return this.type != null ? this.type.getMIIdentifier() : null;
    }

    /**
     * Sets the value of the typeAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setTypeAc( String value ) {
        if (value == null){
            if (this.type != null){
                type.setMIIdentifier(null);
            }
            else {
                this.type = null;
            }
        }
        else if (this.type == null){
            this.type = new DefaultCvTerm(UNSPECIFIED, value);
        }
        else {
            this.type.setMIIdentifier(value);
        }
    }

    /**
     * Check if the optional typeAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasTypeAc() {
        return this.type != null && this.type.getMIIdentifier() != null;
    }

    //////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Alias" );
        sb.append( "{value='" ).append( name ).append( '\'' );
        sb.append( ", type='" ).append( type != null ? type.getShortName() : null ).append( '\'' );
        sb.append( ", typeAc='" ).append( type != null ? type.getMIIdentifier() : null ).append( '\'' );
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

        if ( getAliasType() != null ? !getAliasType().equals( alias.getAliasType() ) : alias.getAliasType() != null ) {
            return false;
        }
        if ( getTypeAc() != null ? !getTypeAc().equals( alias.getTypeAc() ) : alias.getTypeAc() != null ) {
            return false;
        }
        if ( name != null ? !name.equals( alias.name ) : alias.name != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( name != null ? name.hashCode() : 0 );
        result = 29 * result + ( getAliasType() != null ? getAliasType().hashCode() : 0 );
        result = 29 * result + ( getTypeAc() != null ? getTypeAc().hashCode() : 0 );
        return result;
    }

    public CvTerm getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }
}