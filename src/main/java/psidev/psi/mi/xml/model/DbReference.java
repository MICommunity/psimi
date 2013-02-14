/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


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

public class DbReference {

    private Collection<Attribute> attributes;

    private String db;

    private String dbAc;

    private String id;

    private String refType;

    private String refTypeAc;

    private String secondary;

    private String version;

    ///////////////////////////
    // Constructors

    public DbReference() {
    }

    public DbReference( String id, String db ) {
        setId( id );
        setDb( db );
    }

    public DbReference( String db, String dbAc, String id, String refType, String refTypeAc ) {
        this.db = db;
        this.dbAc = dbAc;
        this.id = id;
        this.refType = refType;
        this.refTypeAc = refTypeAc;
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
        return db;
    }

    /**
     * Sets the value of the db property.
     *
     * @param value allowed object is {@link String }
     */
    public void setDb( String value ) {
        this.db = value;
    }

    /**
     * Check if the optional dbAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasDbAc() {
        return dbAc != null;
    }


    /**
     * Gets the value of the dbAc property.
     *
     * @return possible object is {@link String }
     */
    public String getDbAc() {
        return dbAc;
    }

    /**
     * Sets the value of the dbAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setDbAc( String value ) {
        this.dbAc = value;
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
        this.id = value;
    }

    /**
     * Check if the optional refType is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasRefType() {
        return refType != null;
    }

    /**
     * Gets the value of the refType property.
     *
     * @return possible object is {@link String }
     */
    public String getRefType() {
        return refType;
    }

    /**
     * Sets the value of the refType property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRefType( String value ) {
        this.refType = value;
    }

    /**
     * Check if the optional refTypeAc is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasRefTypeAc() {
        return refTypeAc != null;
    }

    /**
     * Gets the value of the refTypeAc property.
     *
     * @return possible object is {@link String }
     */
    public String getRefTypeAc() {
        return refTypeAc;
    }

    /**
     * Sets the value of the refTypeAc property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRefTypeAc( String value ) {
        this.refTypeAc = value;
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
        sb.append( ", db='" ).append( db ).append( '\'' );
        sb.append( ", dbAc='" ).append( dbAc ).append( '\'' );
        sb.append( ", id='" ).append( id ).append( '\'' );
        sb.append( ", refType='" ).append( refType ).append( '\'' );
        sb.append( ", refTypeAc='" ).append( refTypeAc ).append( '\'' );
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
        if ( db != null ? !db.equals( that.db ) : that.db != null ) return false;
        if ( dbAc != null ? !dbAc.equals( that.dbAc ) : that.dbAc != null ) return false;
        if ( id != null ? !id.equals( that.id ) : that.id != null ) return false;
        if ( refType != null ? !refType.equals( that.refType ) : that.refType != null ) return false;
        if ( refTypeAc != null ? !refTypeAc.equals( that.refTypeAc ) : that.refTypeAc != null ) return false;
        if ( secondary != null ? !secondary.equals( that.secondary ) : that.secondary != null ) return false;
        if ( version != null ? !version.equals( that.version ) : that.version != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( attributes != null ? attributes.hashCode() : 0 );
        result = 31 * result + ( db != null ? db.hashCode() : 0 );
        result = 31 * result + ( dbAc != null ? dbAc.hashCode() : 0 );
        result = 31 * result + ( id != null ? id.hashCode() : 0 );
        result = 31 * result + ( refType != null ? refType.hashCode() : 0 );
        result = 31 * result + ( refTypeAc != null ? refTypeAc.hashCode() : 0 );
        result = 31 * result + ( secondary != null ? secondary.hashCode() : 0 );
        result = 31 * result + ( version != null ? version.hashCode() : 0 );
        return result;
    }
}