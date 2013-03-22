/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;

/**
 * A text describing the availability of data, e.g. a copyright statement.
 * <p/>
 * <p>Java class for availabilityType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="availabilityType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */

public class Availability implements FileSourceContext{

    private String value;

    private int id;

    private FileSourceLocator locator;

    ///////////////////////////
    // Constructors

    public Availability() {
    }

    public Availability( int id, String value ) {
        setId( id );
        setValue( value );
    }

    public Availability( String value ) {
        setValue( value );
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
        return !( value == null || value.trim().length() == 0 );
    }

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

    ///////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Availability" );
        sb.append( "{value='" ).append( value ).append( '\'' );
        sb.append( ", id=" ).append( id );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        final Availability that = ( Availability ) o;

        if ( id != that.id ) return false;
        if ( value != null ? !value.equals( that.value ) : that.value != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( value != null ? value.hashCode() : 0 );
        result = 29 * result + id;
        return result;
    }

    public FileSourceLocator getSourceLocator() {
        return this.locator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
        this.locator = locator;
    }
}