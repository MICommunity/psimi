/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.xml.PsimiXmlVersion;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Root element of the Molecular Interaction Format
 * <p/>
 * <p>Java class for entrySet element declaration.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;element name="entrySet">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="entry" maxOccurs="unbounded">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="source" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *                               &lt;element name="bibref" type="{net:sf:psidev:mi}bibrefType" minOccurs="0"/>
 *                               &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *                               &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType"
 * minOccurs="0"/>
 *                             &lt;/sequence>
 *                             &lt;attribute name="release">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/attribute>
 *                             &lt;attribute name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="availabilityList" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="availability" type="{net:sf:psidev:mi}availabilityType"
 * maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="experimentList" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="experimentDescription" type="{net:sf:psidev:mi}experimentType"
 * maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="interactorList" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="interactor" type="{net:sf:psidev:mi}interactorElementType"
 * maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="interactionList">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="interaction" maxOccurs="unbounded">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;extension base="{net:sf:psidev:mi}interactionElementType">
 *                                     &lt;/extension>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;attribute name="level" use="required" type="{http://www.w3.org/2001/XMLSchema}int" fixed="2" />
 *         &lt;attribute name="minorVersion" type="{http://www.w3.org/2001/XMLSchema}int" fixed="3" />
 *         &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}int" fixed="5" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 */

public class EntrySet {

    private Collection<Entry> entries;

    private int level;

    private int version;

    private int minorVersion;

    ///////////////////////////
    // Constructors

    public EntrySet() {
    }

    public EntrySet( Collection<Entry> entries, int level, int version, int minorVersion ) {
        this.entries = entries;
        setLevel( level );
        setVersion( version );
        setMinorVersion( minorVersion );
    }

    public EntrySet( int level, int version, int minorVersion ) {
        setLevel( level );
        setVersion( version );
        setMinorVersion( minorVersion );
    }

    public EntrySet( PsimiXmlVersion version ) {
        setLevel( version.getLevel() );
        setVersion( version.getMajor() );
        setMinorVersion( version.getMinor() );
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Gets the value of the entry property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the entry property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    unmarshalledEntry().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link Entry }
     */
    public Collection<Entry> getEntries() {
        if ( entries == null ) {
            entries = new ArrayList<Entry>();
        }
        return this.entries;
    }

    /**
     * Gets the value of the level property.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     */
    public void setLevel( int value ) {
        this.level = value;
    }

    /**
     * Gets the value of the minorVersion property.
     *
     * @return possible object is {@link Integer }
     */
    public int getMinorVersion() {
        return minorVersion;
    }

    /**
     * Sets the value of the minorVersion property.
     *
     * @param value allowed object is {@link Integer }
     */
    public void setMinorVersion( int value ) {
        this.minorVersion = value;
    }

    /**
     * Gets the value of the version property.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     */
    public void setVersion( int value ) {
        this.version = value;
    }

    /////////////////////////////
    // Object override


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "EntrySet" );
        sb.append( "{entries=" ).append( entries );
        sb.append( ", level=" ).append( level );
        sb.append( ", version=" ).append( version );
        sb.append( ", minorVersion=" ).append( minorVersion );
        sb.append( '}' );
        return sb.toString();
    }


}
