/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */


package psidev.psi.mi.xml.model;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Describes one set of experimental parameters.
 * <p/>
 * <p>Java class for experimentType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="experimentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *         &lt;element name="bibref" type="{net:sf:psidev:mi}bibrefType"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="hostOrganismList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="hostOrganism" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{net:sf:psidev:mi}bioSourceType">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="interactionDetectionMethod" type="{net:sf:psidev:mi}cvType"/>
 *         &lt;element name="participantIdentificationMethod" type="{net:sf:psidev:mi}cvType" minOccurs="0"/>
 *         &lt;element name="featureDetectionMethod" type="{net:sf:psidev:mi}cvType" minOccurs="0"/>
 *         &lt;element name="confidenceList" type="{net:sf:psidev:mi}confidenceListType" minOccurs="0"/>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class ExperimentDescription implements HasId, NamesContainer, XrefContainer, AttributeContainer {

    private int id;

    private Names names;

    private Bibref bibref;

    private Xref xref;


    private Collection<Organism> hostOrganisms;

    private InteractionDetectionMethod interactionDetectionMethod;

    private ParticipantIdentificationMethod participantIdentificationMethod;

    private FeatureDetectionMethod featureDetectionMethod;


    private Collection<Confidence> confidences;

    private Collection<Attribute> attributes;

    ///////////////////////////
    // Constructors

    public ExperimentDescription() {
    }

    public ExperimentDescription( Bibref bibref, InteractionDetectionMethod interactionDetectionMethod ) {
        setBibref( bibref );
        setInteractionDetectionMethod( interactionDetectionMethod );
    }

    ///////////////////////////
    // Getters and Setters

    /**
     * Check if the optional names is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasNames() {
        return names != null;
    }

    /**
     * Gets the value of the names property.
     *
     * @return possible object is {@link Names }
     */
    public Names getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     *
     * @param value allowed object is {@link Names }
     */
    public void setNames( Names value ) {
        this.names = value;
    }

    /**
     * Gets the value of the bibref property.
     *
     * @return possible object is {@link Bibref }
     */
    public Bibref getBibref() {
        return bibref;
    }

    /**
     * Sets the value of the bibref property.
     *
     * @param value allowed object is {@link Bibref }
     */
    public void setBibref( Bibref value ) {
        this.bibref = value;
    }

    /**
     * Check if the optional xref is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasXref() {
        return xref != null;
    }

    /**
     * Gets the value of the xref property.
     *
     * @return possible object is {@link Xref }
     */
    public Xref getXref() {
        return xref;
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value allowed object is {@link Xref }
     */
    public void setXref( Xref value ) {
        this.xref = value;
    }

    /**
     * Check if the optional hostOrganisms is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasHostOrganisms() {
        return ( hostOrganisms != null ) && ( !hostOrganisms.isEmpty() );
    }

    /**
     * Gets the value of the hostOrganismList property.
     *
     * @return possible object is {@link Organism }
     */
    public Collection<Organism> getHostOrganisms() {
        if ( hostOrganisms == null ) {
            hostOrganisms = new ArrayList<Organism>();
        }
        return hostOrganisms;
    }

    /**
     * Gets the value of the interactionDetectionMethod property.
     *
     * @return possible object is {@link CvType }
     */
    public InteractionDetectionMethod getInteractionDetectionMethod() {
        return interactionDetectionMethod;
    }

    /**
     * Sets the value of the interactionDetectionMethod property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setInteractionDetectionMethod( InteractionDetectionMethod value ) {
        this.interactionDetectionMethod = value;
    }

    /**
     * Check if the optional participantIdentificationMethod is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasParticipantIdentificationMethod() {
        return participantIdentificationMethod != null;
    }

    /**
     * Gets the value of the participantIdentificationMethod property.
     *
     * @return possible object is {@link CvType }
     */
    public ParticipantIdentificationMethod getParticipantIdentificationMethod() {
        return participantIdentificationMethod;
    }

    /**
     * Sets the value of the participantIdentificationMethod property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setParticipantIdentificationMethod( ParticipantIdentificationMethod value ) {
        this.participantIdentificationMethod = value;
    }

    /**
     * Check if the optional featureDetectionMethod is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatureDetectionMethod() {
        return featureDetectionMethod != null;
    }

    /**
     * Gets the value of the featureDetectionMethod property.
     *
     * @return possible object is {@link CvType }
     */
    public FeatureDetectionMethod getFeatureDetectionMethod() {
        return featureDetectionMethod;
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setFeatureDetectionMethod( FeatureDetectionMethod value ) {
        this.featureDetectionMethod = value;
    }

    /**
     * Check if the optional confidences is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasConfidences() {
        return ( confidences != null ) && ( !confidences.isEmpty() );
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return possible object is {@link Confidence }
     */
    public Collection<Confidence> getConfidences() {
        if ( confidences == null ) {
            confidences = new ArrayList<Confidence>();
        }
        return confidences;
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
        sb.append( "ExperimentDescription" );
        sb.append( "{id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", bibref=" ).append( bibref );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", hostOrganisms=" ).append( hostOrganisms );
        sb.append( ", interactionDetectionMethod=" ).append( interactionDetectionMethod );
        sb.append( ", participantIdentificationMethod=" ).append( participantIdentificationMethod );
        sb.append( ", featureDetectionMethod=" ).append( featureDetectionMethod );
        sb.append( ", confidences=" ).append( confidences );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( '}' );
        return sb.toString();
    }

    //TODO are bibref and interactionDetectionMethod sufficient ?
    //     Yes, but as it is here, we need to enforce that they are set, or relax equals and check for null.
    // TODO wouldn't id be enough in the context of PSI-MI ?!

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        final ExperimentDescription that = ( ExperimentDescription ) o;

        if ( id != that.id ) {
            return false;
        }
        if ( bibref != null ? !bibref.equals( that.bibref ) : that.bibref != null ) {
            return false;
        }
        if ( interactionDetectionMethod != null ? !interactionDetectionMethod.equals( that.interactionDetectionMethod ) : that.interactionDetectionMethod != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = id;
        result = 29 * result + ( bibref != null ? bibref.hashCode() : 0 );
        result = 29 * result + ( interactionDetectionMethod != null ? interactionDetectionMethod.hashCode() : 0 );
        return result;
    }
}
