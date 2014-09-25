/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Names for an object.
 * <p/>
 * <p>Java class for namesType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="namesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shortLabel" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="fullName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="alias" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="type">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="typeAc">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Names {

    private String shortLabel;

    private String fullName;

    private Collection<Alias> aliases;

    ///////////////////////////
    // Constructors

    //TODO constructors

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the shortLabel property.
     *
     * @return possible object is {@link String }
     */
    public String getShortLabel() {
        return shortLabel;
    }

    /**
     * Check if the optional shortLabel is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasShortLabel() {
        return shortLabel != null;
    }

    /**
     * Sets the value of the shortLabel property.
     *
     * @param value allowed object is {@link String }
     */
    public void setShortLabel( String value ) {
        this.shortLabel = value;
    }

    /**
     * Gets the value of the fullName property.
     *
     * @return possible object is {@link String }
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Check if the optional fullName is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFullName() {
        return fullName != null;
    }

    /**
     * Sets the value of the fullName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setFullName( String value ) {
        this.fullName = value;
    }

    /**
     * Gets the value of the alias property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the alias property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlias().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link Alias }
     */
    public Collection<Alias> getAliases() {
        if ( aliases == null ) {
            aliases = new ArrayList<Alias>();
        }
        return this.aliases;
    }

    /**
     * Check if the optional xxx is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasAliases() {
        return ( aliases != null ) && ( !aliases.isEmpty() );
    }

    ///////////////////////////
    // Object override


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Names" );
        sb.append( "{shortLabel='" ).append( shortLabel ).append( '\'' );
        sb.append( ", fullName='" ).append( fullName ).append( '\'' );
        sb.append( ", aliases=" ).append( aliases );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Names names = ( Names ) o;

        if ( aliases != null ? !aliases.equals( names.aliases ) : names.aliases != null ) return false;
        if ( fullName != null ? !fullName.equals( names.fullName ) : names.fullName != null ) return false;
        if ( shortLabel != null ? !shortLabel.equals( names.shortLabel ) : names.shortLabel != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( shortLabel != null ? shortLabel.hashCode() : 0 );
        result = 31 * result + ( fullName != null ? fullName.hashCode() : 0 );
        result = 31 * result + ( aliases != null ? aliases.hashCode() : 0 );
        return result;
    }
}
