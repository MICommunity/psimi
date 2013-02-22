/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;

import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A molecule participating in an interaction.
 * <p/>
 * <p>Java class for participantType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="participantType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="interactorRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="interactor" type="{net:sf:psidev:mi}interactorElementType"/>
 *           &lt;element name="interactionRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;/choice>
 *         &lt;element name="participantIdentificationMethodList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="participantIdentificationMethod" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{net:sf:psidev:mi}cvType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType"
 * minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="biologicalRole" type="{net:sf:psidev:mi}cvType" minOccurs="0"/>
 *         &lt;element name="experimentalRoleList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="experimentalRole" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{net:sf:psidev:mi}cvType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType"
 * minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="experimentalPreparationList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="experimentalPreparation" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{net:sf:psidev:mi}cvType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType"
 * minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="experimentalInteractorList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="experimentalInteractor" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;choice>
 *                               &lt;element name="interactorRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                               &lt;element name="interactor" type="{net:sf:psidev:mi}interactorElementType"/>
 *                             &lt;/choice>
 *                             &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType"
 * minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="featureList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="feature" type="{net:sf:psidev:mi}featureElementType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="hostOrganismList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="hostOrganism" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{net:sf:psidev:mi}bioSourceType">
 *                           &lt;sequence minOccurs="0">
 *                             &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType"
 * minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="confidenceList" type="{net:sf:psidev:mi}confidenceListType" minOccurs="0"/>
 *         &lt;element name="parameterList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="parameter" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{net:sf:psidev:mi}parameterType">
 *                           &lt;sequence>
 *                             &lt;element name="experimentRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="uncertainty" type="{http://www.w3.org/2001/XMLSchema}decimal" />
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
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Participant extends DefaultParticipantEvidence implements HasId, NamesContainer, XrefContainer, AttributeContainer {

    private int id;

    private Names names;

    private Xref xref;

    private InteractorRef interactorRef;
    private Interactor interactor;

    private Interaction interaction;

    private InteractionRef interactionRef;

    private Collection<ParticipantIdentificationMethod> participantIdentificationMethods;

    private BiologicalRole biologicalRole;

    private Collection<ExperimentalRole> experimentalRoles;

    private Collection<ExperimentalPreparation> experimentalPreparations;

    private Collection<ExperimentalInteractor> experimentalInteractors;

    private Collection<Feature> features;

    private Collection<HostOrganism> hostOrganisms;

    private Collection<Confidence> confidenceList;

    private Collection<Parameter> parameters;

    private Collection<Attribute> attributes;

    ///////////////////////////
    // Constructors

    public Participant() {
    }

    ///////////////////////////
    // Getters and Setters

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
     * Check if the optional interactorRef is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInteractorRef() {
        return interactorRef != null;
    }

    /**
     * Gets the value of the interactorRef property.
     *
     * @return possible object is {@link InteractorRef }
     */
    public InteractorRef getInteractorRef() {
        return interactorRef;
    }

    /**
     * Sets the value of the interactorRef property.
     *
     * @param interactorRef allowed object is {@link InteractorRef }
     */
    public void setInteractorRef( InteractorRef interactorRef ) {
        this.interactorRef = interactorRef;
    }

    /**
     * Check if the optional interactor is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInteractor() {
        return interactor != null;
    }

    /**
     * Gets the value of the interactor property.
     *
     * @return possible object is {@link Interactor }
     */
    public Interactor getInteractor() {
        return interactor;
    }

    /**
     * Sets the value of the interactor property.
     *
     * @param value allowed object is {@link Interactor }
     */
    public void setInteractor( Interactor value ) {
        this.interactor = value;
    }

    /**
     * Check if the optional interactionRef is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInteractionRef() {
        return interactionRef != null;
    }

    /**
     * Gets the value of the interactionRef property.
     *
     * @return possible object is {@link InteractionRef }
     */
    public InteractionRef getInteractionRef() {
        return interactionRef;
    }

    /**
     * Sets the value of the interactionRef property.
     *
     * @param interactionRef allowed object is {@link InteractionRef }
     */
    public void setInteractionRef( InteractionRef interactionRef ) {
        this.interactionRef = interactionRef;
    }

    /**
     * Check if the optional interaction is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInteraction() {
        return interaction != null;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction( Interaction interaction ) {
        this.interaction = interaction;
    }

    /**
     * Check if the optional participantIdentificationMethods is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasParticipantIdentificationMethods() {
        return ( participantIdentificationMethods != null ) && ( !participantIdentificationMethods.isEmpty() );
    }

    /**
     * Gets the value of the participantIdentificationMethodList property.
     *
     * @return possible object is {@link OpenCvType }
     */
    public Collection<ParticipantIdentificationMethod> getParticipantIdentificationMethods() {
        if ( participantIdentificationMethods == null ) {
            participantIdentificationMethods = new ArrayList<ParticipantIdentificationMethod>();
        }
        return participantIdentificationMethods;
    }

    /**
     * Check if the optional biologicalRole is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBiologicalRole() {
        return biologicalRole != null;
    }

    /**
     * Gets the value of the biologicalRole property.
     *
     * @return possible object is {@link CvType }
     */
    public BiologicalRole getBiologicalRole() {
        return biologicalRole;
    }

    /**
     * Sets the value of the biologicalRole property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setBiologicalRole( BiologicalRole value ) {
        this.biologicalRole = value;
    }

    /**
     * Check if the optional experimentalRoles is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperimentalRoles() {
        return ( experimentalRoles != null ) && ( !experimentalRoles.isEmpty() );
    }

    /**
     * Gets the value of the experimentalRoleList property.
     *
     * @return possible object is {@link ExperimentalRole }
     */
    public Collection<ExperimentalRole> getExperimentalRoles() {
        if ( experimentalRoles == null ) {
            experimentalRoles = new ArrayList<ExperimentalRole>();
        }
        return experimentalRoles;
    }

    /**
     * Check if the optional experimentalPreparations is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperimentalPreparations() {
        return ( experimentalPreparations != null ) && ( !experimentalPreparations.isEmpty() );
    }

    /**
     * Gets the value of the experimentalPreparationList property.
     *
     * @return possible object is {@link ExperimentalPreparation }
     */
    public Collection<ExperimentalPreparation> getExperimentalPreparations() {
        if ( experimentalPreparations == null ) {
            experimentalPreparations = new ArrayList<ExperimentalPreparation>();
        }
        return experimentalPreparations;
    }

    /**
     * Check if the optional experimentalInteractors is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperimentalInteractors() {
        return ( experimentalInteractors != null ) && ( !experimentalInteractors.isEmpty() );
    }

    /**
     * Gets the value of the experimentalInteractorList property.
     *
     * @return possible object is {@link ExperimentalInteractor }
     */
    public Collection<ExperimentalInteractor> getExperimentalInteractors() {
        if ( experimentalInteractors == null ) {
            experimentalInteractors = new ArrayList<ExperimentalInteractor>();
        }
        return experimentalInteractors;
    }

    /**
     * Check if the optional features is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatures() {
        return ( features != null ) && ( !features.isEmpty() );
    }

    /**
     * Gets the value of the featureList property.
     *
     * @return possible object is {@link Feature }
     */
    public Collection<Feature> getFeatures() {
        if ( features == null ) {
            features = new ArrayList<Feature>();
        }
        return features;
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
    public Collection<HostOrganism> getHostOrganisms() {
        if ( hostOrganisms == null ) {
            hostOrganisms = new ArrayList<HostOrganism>();
        }
        return hostOrganisms;
    }

    /**
     * Check if the optional confidences is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasConfidences() {
        return ( confidenceList != null ) && ( !confidenceList.isEmpty() );
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return possible object is {@link Confidence }
     */
    public Collection<Confidence> getConfidenceList() {
        if ( confidenceList == null ) {
            confidenceList = new ArrayList<Confidence>();
        }
        return confidenceList;
    }

    /**
     * Check if the optional parameters is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasParameters() {
        return ( parameters != null ) && ( !parameters.isEmpty() );
    }

    /**
     * Gets the value of the parameterList property.
     *
     * @return possible object is {@link Parameter }
     */
    public Collection<Parameter> getParameters() {
        if ( parameters == null ) {
            parameters = new ArrayList<Parameter>();
        }
        return parameters;
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

    ////////////////////////
    // Object override

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Participant" );
        sb.append( "{id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", interactor=" ).append( interactor );
        sb.append( ", participantIdentificationMethods=" ).append( participantIdentificationMethods );
        sb.append( ", biologicalRole=" ).append( biologicalRole );
        sb.append( ", experimentalRoles=" ).append( experimentalRoles );
        sb.append( ", experimentalPreparations=" ).append( experimentalPreparations );
        sb.append( ", experimentalInteractors=" ).append( experimentalInteractors );
        sb.append( ", features=" ).append( features );
        sb.append( ", hostOrganisms=" ).append( hostOrganisms );
        sb.append( ", confidences=" ).append( confidenceList );
        sb.append( ", parameters=" ).append( parameters );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( '}' );
        return sb.toString();
    }

    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Participant that = ( Participant ) o;

        if ( id != that.id ) return false;
        if ( attributes != null ? !attributes.equals( that.attributes ) : that.attributes != null ) return false;
        if ( biologicalRole != null ? !biologicalRole.equals( that.biologicalRole ) : that.biologicalRole != null )
            return false;
        if ( confidenceList != null ? !confidenceList.equals( that.confidenceList ) : that.confidenceList != null )
            return false;
        if ( experimentalInteractors != null ? !experimentalInteractors.equals( that.experimentalInteractors ) : that.experimentalInteractors != null )
            return false;
        if ( experimentalPreparations != null ? !experimentalPreparations.equals( that.experimentalPreparations ) : that.experimentalPreparations != null )
            return false;
        if ( experimentalRoles != null ? !experimentalRoles.equals( that.experimentalRoles ) : that.experimentalRoles != null )
            return false;
        if ( features != null ? !features.equals( that.features ) : that.features != null ) return false;
        if ( hostOrganisms != null ? !hostOrganisms.equals( that.hostOrganisms ) : that.hostOrganisms != null )
            return false;
        //if (interaction != null ? !interaction.equals(that.interaction) : that.interaction != null) return false;
        if ( interactionRef != null ? !interactionRef.equals( that.interactionRef ) : that.interactionRef != null )
            return false;
        if ( interactor != null ? !interactor.equals( that.interactor ) : that.interactor != null ) return false;
        if ( interactorRef != null ? !interactorRef.equals( that.interactorRef ) : that.interactorRef != null )
            return false;
        if ( names != null ? !names.equals( that.names ) : that.names != null ) return false;
        if ( parameters != null ? !parameters.equals( that.parameters ) : that.parameters != null ) return false;
        if ( participantIdentificationMethods != null ? !participantIdentificationMethods.equals( that.participantIdentificationMethods ) : that.participantIdentificationMethods != null )
            return false;
        if ( xref != null ? !xref.equals( that.xref ) : that.xref != null ) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = id;
        result = 31 * result + ( names != null ? names.hashCode() : 0 );
        result = 31 * result + ( xref != null ? xref.hashCode() : 0 );
        result = 31 * result + ( interactorRef != null ? interactorRef.hashCode() : 0 );
        result = 31 * result + ( interactor != null ? interactor.hashCode() : 0 );
        //result = 31 * result + (interaction != null ? interaction.hashCode() : 0);
        result = 31 * result + ( interactionRef != null ? interactionRef.hashCode() : 0 );
        result = 31 * result + ( participantIdentificationMethods != null ? participantIdentificationMethods.hashCode() : 0 );
        result = 31 * result + ( biologicalRole != null ? biologicalRole.hashCode() : 0 );
        result = 31 * result + ( experimentalRoles != null ? experimentalRoles.hashCode() : 0 );
        result = 31 * result + ( experimentalPreparations != null ? experimentalPreparations.hashCode() : 0 );
        result = 31 * result + ( experimentalInteractors != null ? experimentalInteractors.hashCode() : 0 );
        result = 31 * result + ( features != null ? features.hashCode() : 0 );
        result = 31 * result + ( hostOrganisms != null ? hostOrganisms.hashCode() : 0 );
        result = 31 * result + ( confidenceList != null ? confidenceList.hashCode() : 0 );
        result = 31 * result + ( parameters != null ? parameters.hashCode() : 0 );
        result = 31 * result + ( attributes != null ? attributes.hashCode() : 0 );
        return result;
    }
}