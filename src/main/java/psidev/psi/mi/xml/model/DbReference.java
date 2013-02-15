/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultXref;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Refers to a unique object in an external database.
 * <p/>
 * <p>Java class for dbReferenceType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="dbReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="db" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="dbAc">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="id" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="refType">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="refTypeAc">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="secondary">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="version">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class DbReference extends DefaultXref{

    private Collection<Attribute> attributes;

    private String secondary;

    private static String UNKNOWN = "unknown";
    private static String UNSPECIFIED_ID = "-";

    ///////////////////////////
    // Constructors

    public DbReference() {
        super(new DefaultCvTerm(UNKNOWN), UNSPECIFIED_ID);
    }

    public DbReference( String id, String db ) {
        super(new DefaultCvTerm(db != null ? db : UNKNOWN), id != null && id.length() > 0 ? id : UNSPECIFIED_ID);
    }

    public DbReference( String db, String dbAc, String id, String refType, String refTypeAc ) {
        super(new DefaultCvTerm(db != null ? db : UNKNOWN, dbAc), id != null && id.length() > 0 ? id : UNSPECIFIED_ID);

        if (refType != null && refTypeAc != null){
            this.qualifier = new DefaultCvTerm(refType, refTypeAc);
        }
        else if (refType != null){
            this.qualifier = new DefaultCvTerm(refType);
        }
        else if (refTypeAc != null){
            this.qualifier = new DefaultCvTerm(UNKNOWN, refTypeAc);
        }
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

    /**
     * Gets the value of the db property.
     *
     * @return possible object is {@link String }
     */
    public String getDb() {
        return database != null ? database.getShortName() : null;
    }

    /**
     * Sets the value of the db property.
     *
     * @param value allowed object is {@link String }
     */
    public void setDb( String value ) {
        if (value == null){
            if (this.database != null && this.database.getMIIdentifier() != null){
                database.setShortName(UNKNOWN);
            }
            else {
                this.database = new DefaultCvTerm(UNKNOWN);
            }
        }
        else if (this.database == null){
            this.database = new DefaultCvTerm(value);
        }
        else {
            this.database.setShortName(value);
        }
    }

    /**
     * Check if the optional dbAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasDbAc() {
        return database != null && database.getMIIdentifier() != null;
    }


    /**
     * Gets the value of the dbAc property.
     *
     * @return possible object is {@link String }
     */
    public String getDbAc() {
        return database != null ? database.getMIIdentifier() : null;
    }

    /**
     * Sets the value of the dbAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setDbAc( String value ) {
        if (value == null){
            if (this.database != null){
                database.setMIIdentifier(null);
            }
            else {
                this.database = new DefaultCvTerm(UNKNOWN);
            }
        }
        else if (this.database == null){
            this.database = new DefaultCvTerm(UNKNOWN, value);
        }
        else {
            this.database.setMIIdentifier(value);
        }
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is {@link String }
     */
    public void setId( String value ) {
        if (value == null || value.length() == 0){
           this.id = UNSPECIFIED_ID;
        }
        else {
            this.id = value;
        }
    }

    /**
     * Check if the optional refType is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasRefType() {
        return qualifier != null && !UNKNOWN.equals(qualifier.getShortName());
    }

    /**
     * Gets the value of the refType property.
     *
     * @return possible object is {@link String }
     */
    public String getRefType() {
        return qualifier != null ? qualifier.getShortName() : null;
    }

    /**
     * Sets the value of the refType property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRefType( String value ) {
        if (value == null){
            if (this.qualifier != null && this.qualifier.getMIIdentifier() != null){
                qualifier.setShortName(UNKNOWN);
            }
            else {
                this.qualifier = null;
            }
        }
        else if (this.qualifier == null){
            this.qualifier = new DefaultCvTerm(value);
        }
        else {
            this.qualifier.setShortName(value);
        }
    }

    /**
     * Check if the optional refTypeAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasRefTypeAc() {
        return qualifier != null && qualifier.getMIIdentifier() != null;
    }

    /**
     * Gets the value of the refTypeAc property.
     *
     * @return possible object is {@link String }
     */
    public String getRefTypeAc() {
        return qualifier != null ? qualifier.getMIIdentifier() : null;
    }

    /**
     * Sets the value of the refTypeAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRefTypeAc( String value ) {
        if (value == null){
            if (this.qualifier != null){
                qualifier.setMIIdentifier(null);
            }
            else {
                this.qualifier = null;
            }
        }
        else if (this.qualifier == null){
            this.qualifier = new DefaultCvTerm(UNKNOWN, value);
        }
        else {
            this.qualifier.setMIIdentifier(value);
        }
    }

    /**
     * Check if the optional secondary is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasSecondary() {
        return secondary != null;
    }

    /**
     * Gets the value of the secondary property.
     *
     * @return possible object is {@link String }
     */
    public String getSecondary() {
        return secondary;
    }

    /**
     * Sets the value of the secondary property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSecondary( String value ) {
        this.secondary = value;
    }

    /**
     * Check if the optional version is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasVersion() {
        return version != null;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link String }
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is {@link String }
     */
    public void setVersion( String value ) {
        this.version = value;
    }

    //////////////////////////////
    // Object override


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "DbReference" );
        sb.append( "{attributes=" ).append( attributes );
        sb.append( ", db='" ).append( getDb() ).append( '\'' );
        sb.append( ", dbAc='" ).append( getDbAc() ).append( '\'' );
        sb.append( ", id='" ).append( id ).append( '\'' );
        sb.append( ", refType='" ).append( getRefType() ).append( '\'' );
        sb.append( ", refTypeAc='" ).append( getRefTypeAc() ).append( '\'' );
        sb.append( ", secondary='" ).append( secondary ).append( '\'' );
        sb.append( ", version='" ).append( version ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        DbReference that = ( DbReference ) o;

        if ( attributes != null ? !attributes.equals( that.attributes ) : that.attributes != null ) return false;
        if ( getDb() != null ? !getDb().equals( that.getDb() ) : that.getDb() != null ) return false;
        if ( getDbAc() != null ? !getDbAc().equals( that.getDbAc() ) : that.getDbAc() != null ) return false;
        if ( id != null ? !id.equals( that.id ) : that.id != null ) return false;
        if ( getRefType() != null ? !getRefType().equals( that.getRefType() ) : that.getRefType() != null ) return false;
        if ( getRefTypeAc() != null ? !getRefTypeAc().equals( that.getRefTypeAc() ) : that.getRefTypeAc() != null ) return false;
        if ( secondary != null ? !secondary.equals( that.secondary ) : that.secondary != null ) return false;
        if ( version != null ? !version.equals( that.version ) : that.version != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( attributes != null ? attributes.hashCode() : 0 );
        result = 31 * result + ( getDb() != null ? getDb().hashCode() : 0 );
        result = 31 * result + ( getDbAc() != null ? getDbAc().hashCode() : 0 );
        result = 31 * result + ( id != null ? id.hashCode() : 0 );
        result = 31 * result + ( getRefType() != null ? getRefType().hashCode() : 0 );
        result = 31 * result + ( getRefTypeAc() != null ? getRefTypeAc().hashCode() : 0 );
        result = 31 * result + ( secondary != null ? secondary.hashCode() : 0 );
        result = 31 * result + ( version != null ? version.hashCode() : 0 );
        return result;
    }
}