/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="source" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *                   &lt;element name="bibref" type="{net:sf:psidev:mi}bibrefType" minOccurs="0"/>
 *                   &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *                   &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="release">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;minLength value="1"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="availabilityList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="availability" type="{net:sf:psidev:mi}availabilityType" maxOccurs="unbounded"
 * minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="experimentList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="experimentDescription" type="{net:sf:psidev:mi}experimentType"
 * maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="interactorList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="interactor" type="{net:sf:psidev:mi}interactorElementType" maxOccurs="unbounded"
 * minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="interactionList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="interaction" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{net:sf:psidev:mi}interactionElementType">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Entry implements AttributeContainer {

    private Source source;

    private Collection<Availability> availabilities;

    private Collection<ExperimentDescription> experiments;

    private Collection<Interactor> interactors;

    private Collection<Interaction> interactions;

    private Collection<Attribute> attributes;

    ///////////////////////////
    // Constructors

    public Entry() {
    }

    public Entry( Collection<Interaction> interactions ) {
        this.interactions = interactions;
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Check if the optional source is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasSource() {
        return source != null;
    }

    /**
     * Gets the value of the source property.
     *
     * @return possible object is {@link Source }
     */
    public Source getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     *
     * @param value allowed object is {@link Source }
     */
    public void setSource( Source value ) {
        this.source = value;
    }

    /**
     * Check if the optional availabilities is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasAvailabilities() {
        return ( availabilities != null ) && ( !availabilities.isEmpty() );
    }

    /**
     * Gets the value of the availabilityList property.
     *
     * @return possible object is {@link Availability }
     */
    public Collection<Availability> getAvailabilities() {
        if ( availabilities == null ) {
            availabilities = new ArrayList<Availability>();
        }
        return availabilities;
    }

    /**
     * Check if the optional experiments is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperiments() {
        return ( experiments != null ) && ( !experiments.isEmpty() );
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return possible object is {@link ExperimentDescription }
     */
    public Collection<ExperimentDescription> getExperiments() {
        if ( experiments == null ) {
            experiments = new ArrayList<ExperimentDescription>();
        }
        return experiments;
    }

    /**
     * Check if the optional interactors is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInteractors() {
        return ( interactors != null ) && ( !interactors.isEmpty() );
    }

    /**
     * Gets the value of the interactorList property.
     *
     * @return possible object is {@link Interactor }
     */
    public Collection<Interactor> getInteractors() {
        if ( interactors == null ) {
            interactors = new ArrayList<Interactor>();
        }
        return interactors;
    }

    /**
     * Gets the value of the interactionList property.
     *
     * @return possible object is {@link Interaction }
     */
    public Collection<Interaction> getInteractions() {
        if ( interactions == null ) {
            interactions = new ArrayList<Interaction>();
        }
        return interactions;
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

    //////////////////////////////
    // Object override


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Entry" );
        sb.append( "{source=" ).append( source );
        sb.append( ", availabilities=" ).append( availabilities );
        sb.append( ", experiments=" ).append( experiments );
        sb.append( ", interactors=" ).append( interactors );
        sb.append( ", interactions=" ).append( interactions );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( '}' );
        return sb.toString();
    }

    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Entry entry = ( Entry ) o;

        if ( attributes != null ? !attributes.equals( entry.attributes ) : entry.attributes != null ) return false;
        if ( availabilities != null ? !availabilities.equals( entry.availabilities ) : entry.availabilities != null )
            return false;
        if ( experiments != null ? !experiments.equals( entry.experiments ) : entry.experiments != null ) return false;
        if ( interactions != null ? !interactions.equals( entry.interactions ) : entry.interactions != null )
            return false;
        if ( interactors != null ? !interactors.equals( entry.interactors ) : entry.interactors != null ) return false;
        if ( source != null ? !source.equals( entry.source ) : entry.source != null ) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = ( source != null ? source.hashCode() : 0 );
        result = 31 * result + ( availabilities != null ? availabilities.hashCode() : 0 );
        result = 31 * result + ( experiments != null ? experiments.hashCode() : 0 );
        result = 31 * result + ( interactors != null ? interactors.hashCode() : 0 );
        result = 31 * result + ( interactions != null ? interactions.hashCode() : 0 );
        result = 31 * result + ( attributes != null ? attributes.hashCode() : 0 );
        return result;
    }

    //TODO equals and hashcode
}