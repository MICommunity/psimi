/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.clone.*;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
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

    private InteractionRef interactionRef;

    private Collection<ParticipantIdentificationMethod> participantIdentificationMethods = new ParticipantIdentificationMethodsList();

    private Collection<ExperimentalRole> experimentalRoles = new ExperimentalRolesList();

    private Collection<ExperimentalPreparation> xmlExperimentalPreparations = new ExperimentalPreparationsXmlList();

    private Collection<ExperimentalInteractor> experimentalInteractors;

    private Collection<Feature> xmlFeatures = new ParticipantXmlFeaturesList();

    private Collection<HostOrganism> hostOrganisms = new HostOrganismsList();

    private Collection<Confidence> confidenceList = new ParticipantXmlConfidencesList();

    private Collection<Parameter> parametersList = new ParticipantXmlParametersList();

    private Collection<Attribute> attributes = new ParticipantXmlAnnotationList();

    private boolean isInteractorAComplex = false;

    ///////////////////////////
    // Constructors

    public Participant() {
        super(new Interactor(), new BiologicalRole(), new ExperimentalRole(), new ParticipantIdentificationMethod());

        experimentalRole.setShortName(UNSPECIFIED_ROLE);
        experimentalRole.setMIIdentifier(UNSPECIFIED_ROLE_MI);
        biologicalRole.setShortName(UNSPECIFIED_ROLE);
        biologicalRole.setMIIdentifier(UNSPECIFIED_ROLE_MI);
    }

    @Override
    protected void initialiseAliases() {
        this.aliases = new ParticipantAliasList();
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new ParticipantXrefList();
    }

    @Override
    public void initializeAnnotations() {
        this.annotations = new ParticipantAnnotationList();
    }

    @Override
    protected void initializeExperimentalPreparations() {
        this.experimentalPreparations = new ExperimentalPreparationsList();
    }

    @Override
    protected void initializeFeatures() {
        this.features = new ParticipantFeaturesList();
    }

    @Override
    protected void initializeConfidences() {
        this.confidences = new ParticipantConfidencesList();
    }

    @Override
    protected void initializeParameters() {
        this.parameters = new ParticipantParametersList();
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
        if (value != null){
            if (this.names == null){
                this.names = new ParticipantNames();
            }
            else {
                aliases.clear();
            }
            this.names.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            this.names.getAliases().clear();
            this.names.getAliases().addAll(value.getAliases());
        }
        else if (this.names != null) {
            aliases.clear();
            this.names = null;
        }
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
        if (value != null){
            if (this.xref != null){
                this.xrefs.clear();
            }
            this.xref = new ParticipantXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            xrefs.clear();
            this.xref = null;
        }
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
        return interactor != null && !isInteractorAComplex;
    }

    /**
     * Gets the value of the interactor property.
     *
     * @return possible object is {@link Interactor }
     */
    public Interactor getInteractor() {
        return (Interactor) interactor;
    }

    /**
     * Sets the value of the interactor property.
     *
     * @param value allowed object is {@link Interactor }
     */
    public void setInteractor( Interactor value ) {
        this.interactor = value;
        if (value == null){
            this.interactor = new Interactor();
        }
        isInteractorAComplex = false;
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
        return isInteractorAComplex && interactor != null;
    }

    public Interaction getInteractionComplex() {
        return (Interaction) interactor;
    }

    public void setInteraction( Interaction interaction ) {
        this.interactor = interaction;
        if (interaction == null){
            this.interactor = new Interaction();
        }
        isInteractorAComplex = true;
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
        return (BiologicalRole) biologicalRole;
    }

    /**
     * Sets the value of the biologicalRole property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setBiologicalRole( BiologicalRole value ) {
        if (value == null){
            this.biologicalRole = new BiologicalRole();
            this.biologicalRole.setShortName(UNSPECIFIED_ROLE);
            this.biologicalRole.setMIIdentifier(UNSPECIFIED_ROLE_MI);
        }
        else {
            this.biologicalRole = value;
        }
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
        return experimentalRoles;
    }

    /**
     * Check if the optional xmlExperimentalPreparations is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperimentalPreparations() {
        return ( xmlExperimentalPreparations != null ) && ( !xmlExperimentalPreparations.isEmpty() );
    }

    /**
     * Gets the value of the experimentalPreparationList property.
     *
     * @return possible object is {@link ExperimentalPreparation }
     */
    public Collection<ExperimentalPreparation> getParticipantExperimentalPreparations() {
        return xmlExperimentalPreparations;
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
     * Check if the optional xmlFeatures is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatures() {
        return ( xmlFeatures != null ) && ( !xmlFeatures.isEmpty() );
    }

    /**
     * Gets the value of the featureList property.
     *
     * @return possible object is {@link Feature }
     */
    public Collection<Feature> getParticipantFeatures() {
        return xmlFeatures;
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
        return confidenceList;
    }

    /**
     * Check if the optional parametersList is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasParameters() {
        return ( parametersList != null ) && ( !parametersList.isEmpty() );
    }

    /**
     * Gets the value of the parameterList property.
     *
     * @return possible object is {@link Parameter }
     */
    public Collection<Parameter> getParametersList() {
        return parametersList;
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
        return attributes;
    }

    protected Participant getInstance(){
        return this;
    }

    ////////////////////////
    // Object override


    @Override
    public void setInteractor(psidev.psi.mi.jami.model.Interactor interactor) {
        if (interactor == null){
            this.interactor = new Interactor();
        }
        else if (interactor instanceof Interactor){
            super.setInteractor(interactor);
        }
        else {
            psidev.psi.mi.jami.model.Interactor convertedInteractor = null;

            if (interactor instanceof Complex){
                convertedInteractor = new Interaction();
                InteractorCloner.copyAndOverrideComplexProperties((Complex) interactor, (Complex) convertedInteractor);
                isInteractorAComplex = true;
            }
            else {
                convertedInteractor = new Interactor();
                isInteractorAComplex = false;
                InteractorCloner.copyAndOverrideInteractorProperties(interactor, convertedInteractor);
            }
            super.setInteractor(convertedInteractor);
        }
    }

    @Override
    public void setIdentificationMethod(CvTerm identificationMethod) {
        if (identificationMethod != null){
            if (this.identificationMethod != null){
                participantIdentificationMethods.remove(this.identificationMethod);
            }
            if(identificationMethod instanceof ParticipantIdentificationMethod){
                this.identificationMethod = identificationMethod;
                ((ParticipantIdentificationMethodsList)participantIdentificationMethods).addOnly((ParticipantIdentificationMethod) this.identificationMethod);
            }
            else {
                this.identificationMethod = new ParticipantIdentificationMethod();

                CvTermCloner.copyAndOverrideCvTermProperties(identificationMethod, this.identificationMethod);
                ((ParticipantIdentificationMethodsList) participantIdentificationMethods).addOnly((ParticipantIdentificationMethod) this.identificationMethod);
            }
        }
        else if (!this.participantIdentificationMethods.isEmpty()) {
            this.identificationMethod = null;
            ((ParticipantIdentificationMethodsList)participantIdentificationMethods).clearOnly();
        }
    }

    @Override
    public void setBiologicalRole(CvTerm bioRole) {
        if (bioRole == null){
            this.biologicalRole = new BiologicalRole();
            biologicalRole.setShortName(UNSPECIFIED_ROLE);
            biologicalRole.setMIIdentifier(UNSPECIFIED_ROLE_MI);
        }
        else if (bioRole instanceof BiologicalRole){
            this.biologicalRole = bioRole;
        }
        else {
            this.biologicalRole = new BiologicalRole();
            CvTermCloner.copyAndOverrideCvTermProperties(bioRole, this.biologicalRole);
        }
    }

    @Override
    public void setExperimentalRole(CvTerm expRole) {
        if (expRole != null){
            if (this.experimentalRole != null){
                experimentalRoles.remove(this.experimentalRole);
            }
            if(experimentalRole instanceof ExperimentalRole){
                this.experimentalRole = expRole;
                ((ExperimentalRolesList)experimentalRoles).addOnly((ExperimentalRole) this.experimentalRole);
            }
            else {
                this.experimentalRole = new ExperimentalRole();

                CvTermCloner.copyAndOverrideCvTermProperties(expRole, this.experimentalRole);
                ((ExperimentalRolesList) experimentalRoles).addOnly((ExperimentalRole) this.experimentalRole);
            }
        }
        else if (!this.experimentalRoles.isEmpty()) {
            experimentalRoles.clear();
        }
    }

    @Override
    public void setExpressedInOrganism(psidev.psi.mi.jami.model.Organism organism) {
        if (organism != null){
            if (this.expressedIn != null){
                hostOrganisms.remove(this.expressedIn);
            }
            if(expressedIn instanceof HostOrganism){
                this.expressedIn = organism;
                ((HostOrganismsList)hostOrganisms).addOnly((HostOrganism) this.expressedIn);
            }
            else {
                this.expressedIn = new HostOrganism();

                OrganismCloner.copyAndOverrideOrganismProperties(organism, this.expressedIn);
                ((HostOrganismsList)hostOrganisms).addOnly((HostOrganism) this.expressedIn);
            }
        }
        else if (!this.hostOrganisms.isEmpty()) {
            this.expressedIn = null;
            ((HostOrganismsList)hostOrganisms).clearOnly();
        }
    }

    @Override
    public void setInteraction(InteractionEvidence interaction) {
        if (interaction == null){
            super.setInteraction(null);
        }
        else if (interaction instanceof Interaction){
            super.setInteraction(interaction);
        }
        else {
            Interaction convertedInteraction = new Interaction();

            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, convertedInteraction);
            super.setInteraction(convertedInteraction);
        }
    }

    @Override
    public void setInteractionEvidenceAndAddParticipantEvidence(InteractionEvidence interaction) {
        if (interaction == null){
            super.setInteraction(null);
        }
        else if (interaction instanceof Interaction){
            super.setInteraction(interaction);
            interaction.getParticipants().add(this);
        }
        else {
            Interaction convertedInteraction = new Interaction();

            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, convertedInteraction);
            super.setInteraction(convertedInteraction);
            convertedInteraction.getParticipants().add(this);
        }
    }

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
        sb.append( ", xmlExperimentalPreparations=" ).append(xmlExperimentalPreparations);
        sb.append( ", experimentalInteractors=" ).append( experimentalInteractors );
        sb.append( ", xmlFeatures=" ).append(xmlFeatures);
        sb.append( ", hostOrganisms=" ).append( hostOrganisms );
        sb.append( ", confidences=" ).append( confidenceList );
        sb.append( ", parametersList=" ).append(parametersList);
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
        if ( xmlExperimentalPreparations != null ? !xmlExperimentalPreparations.equals( that.xmlExperimentalPreparations) : that.xmlExperimentalPreparations != null )
            return false;
        if ( experimentalRoles != null ? !experimentalRoles.equals( that.experimentalRoles ) : that.experimentalRoles != null )
            return false;
        if ( xmlFeatures != null ? !xmlFeatures.equals( that.xmlFeatures) : that.xmlFeatures != null ) return false;
        if ( hostOrganisms != null ? !hostOrganisms.equals( that.hostOrganisms ) : that.hostOrganisms != null )
            return false;
        //if (interaction != null ? !interaction.equals(that.interaction) : that.interaction != null) return false;
        if ( interactionRef != null ? !interactionRef.equals( that.interactionRef ) : that.interactionRef != null )
            return false;
        if ( interactor != null ? !interactor.equals( that.interactor ) : that.interactor != null ) return false;
        if ( interactorRef != null ? !interactorRef.equals( that.interactorRef ) : that.interactorRef != null )
            return false;
        if ( names != null ? !names.equals( that.names ) : that.names != null ) return false;
        if ( parametersList != null ? !parametersList.equals( that.parametersList) : that.parametersList != null ) return false;
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
        result = 31 * result + ( xmlExperimentalPreparations != null ? xmlExperimentalPreparations.hashCode() : 0 );
        result = 31 * result + ( experimentalInteractors != null ? experimentalInteractors.hashCode() : 0 );
        result = 31 * result + ( xmlFeatures != null ? xmlFeatures.hashCode() : 0 );
        result = 31 * result + ( hostOrganisms != null ? hostOrganisms.hashCode() : 0 );
        result = 31 * result + ( confidenceList != null ? confidenceList.hashCode() : 0 );
        result = 31 * result + ( parametersList != null ? parametersList.hashCode() : 0 );
        result = 31 * result + ( attributes != null ? attributes.hashCode() : 0 );
        return result;
    }

    protected class ParticipantNames extends Names{

        protected AliasList extendedAliases = new AliasList();

        public Collection<Alias> getAliases() {
            return this.extendedAliases;
        }

        public boolean hasAliases() {
            return ( extendedAliases != null ) && ( !extendedAliases.isEmpty() );
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Names" );
            sb.append( "{shortLabel='" ).append( getShortLabel() ).append( '\'' );
            sb.append( ", fullName='" ).append( getFullName() ).append( '\'' );
            sb.append( ", aliases=" ).append( extendedAliases );
            sb.append( '}' );
            return sb.toString();
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            Names names = ( Names ) o;

            if ( extendedAliases != null ? !extendedAliases.equals( names.getAliases() ) : names.getAliases() != null ) return false;
            if ( getFullName() != null ? !getFullName().equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( getShortLabel() != null ? !getShortLabel().equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( getShortLabel() != null ? getShortLabel().hashCode() : 0 );
            result = 31 * result + ( getFullName() != null ? getFullName().hashCode() : 0 );
            result = 31 * result + ( extendedAliases != null ? extendedAliases.hashCode() : 0 );
            return result;
        }

        protected class AliasList extends AbstractListHavingPoperties<Alias> {

            @Override
            protected void processAddedObjectEvent(Alias added) {
                ((ParticipantAliasList) aliases).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((ParticipantAliasList)aliases).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((ParticipantAliasList)aliases).clearOnly();
            }
        }
    }

    private class ParticipantAliasList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Alias> {
        public ParticipantAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Alias added) {

            if (names != null){
                ParticipantNames name = (ParticipantNames) names;

                if (added instanceof Alias){
                    ((ParticipantNames.AliasList) name.getAliases()).addOnly((Alias) added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    ((ParticipantNames.AliasList) name.getAliases()).addOnly(fixedAlias);
                }
            }
            else {
                if (added instanceof Alias){
                    names = new ParticipantNames();
                    ((ParticipantNames.AliasList) names.getAliases()).addOnly((Alias) added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    names = new ParticipantNames();
                    ((ParticipantNames.AliasList) names.getAliases()).addOnly((Alias) fixedAlias);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Alias removed) {

            if (names != null){
                ParticipantNames name = (ParticipantNames) names;

                if (removed instanceof Alias){
                    name.extendedAliases.removeOnly((Alias) removed);

                }
                else {
                    Alias fixedAlias = new Alias(removed.getName(), removed.getType() != null ? removed.getType().getShortName() : null, removed.getType() != null ? removed.getType().getMIIdentifier() : null);

                    name.extendedAliases.removeOnly(fixedAlias);
                }
            }
        }

        @Override
        protected void clearProperties() {

            if (names != null){
                ParticipantNames name = (ParticipantNames) names;
                name.extendedAliases.clearOnly();
            }
        }
    }

    protected class ParticipantXref extends Xref{

        protected SecondaryRefList extendedSecondaryRefList = new SecondaryRefList();

        public ParticipantXref() {
            super();
        }

        public ParticipantXref(DbReference primaryRef) {
            super(primaryRef);
        }

        public ParticipantXref(DbReference primaryRef, Collection<DbReference> secondaryRef) {
            super( primaryRef );

            if (secondaryRef != null && !secondaryRef.isEmpty()){
                secondaryRef.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    ((ParticipantXrefList)xrefs).addOnly(value);
                }
            }
            else {
                ((ParticipantXrefList)xrefs).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    ((ParticipantXrefList)xrefs).addOnly(value);
                }
            }
        }

        public void setPrimaryRefOnly( DbReference value ) {
            if (value == null && !extendedSecondaryRefList.isEmpty()){
                super.setPrimaryRef(extendedSecondaryRefList.get(0));
                extendedSecondaryRefList.removeOnly(0);
            }
            else {
                super.setPrimaryRef(value);
            }
        }

        public boolean hasSecondaryRef() {
            return ( extendedSecondaryRefList != null ) && ( !extendedSecondaryRefList.isEmpty() );
        }

        public Collection<DbReference> getSecondaryRef() {
            return this.extendedSecondaryRefList;
        }

        public Collection<DbReference> getAllDbReferences() {
            Collection<DbReference> refs = new ArrayList<DbReference>();
            if ( getPrimaryRef() != null ) {
                refs.add( getPrimaryRef() );
            }
            if ( extendedSecondaryRefList != null ) {
                refs.addAll( extendedSecondaryRefList );
            }
            return refs;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Xref" );
            sb.append( "{primaryRef=" ).append( getPrimaryRef() );
            sb.append( ", secondaryRef=" ).append( extendedSecondaryRefList );
            sb.append( '}' );
            return sb.toString();
        }

        protected class SecondaryRefList extends AbstractListHavingPoperties<DbReference>{

            @Override
            protected void processAddedObjectEvent(DbReference added) {
                ((ParticipantXrefList)xrefs).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                ((ParticipantXrefList)xrefs).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((ParticipantXrefList)xrefs).retainAllOnly(primary);
            }
        }
    }

    private class ParticipantXrefList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public ParticipantXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                ParticipantXref reference = (ParticipantXref) xref;

                if (xref.getPrimaryRef() == null){
                    if (added instanceof DbReference){
                        reference.setPrimaryRefOnly((DbReference) added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.setPrimaryRefOnly(fixedRef);
                    }
                }
                else {
                    if (added instanceof DbReference){
                        reference.extendedSecondaryRefList.addOnly((DbReference) added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.addOnly(fixedRef);
                    }
                }
            }
            else {
                if (added instanceof DbReference){
                    xref = new ParticipantXref();
                    ((ParticipantXref) xref).setPrimaryRefOnly((DbReference) added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new ParticipantXref();
                    ((ParticipantXref) xref).setPrimaryRefOnly(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                ParticipantXref reference = (ParticipantXref) xref;

                if (reference.getPrimaryRef() == removed){
                    reference.setPrimaryRefOnly(null);
                }
                else {
                    if (removed instanceof DbReference){
                        reference.extendedSecondaryRefList.removeOnly((DbReference) removed);

                    }
                    else {
                        DbReference fixedRef = new DbReference(removed.getDatabase().getShortName(), removed.getDatabase().getMIIdentifier(), removed.getId(),
                                removed.getQualifier() != null ? removed.getQualifier().getShortName() : null, removed.getQualifier() != null ? removed.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.removeOnly(fixedRef);
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {

            if (xref != null){
                ParticipantXref reference = (ParticipantXref) xref;
                reference.extendedSecondaryRefList.clearOnly();
                reference.setPrimaryRef(null);
            }
        }
    }

    protected class ParticipantAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public ParticipantAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((ParticipantXmlAnnotationList)attributes).addOnly((Attribute) added);
            }
            else {
                Attribute att = new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue());
                ((ParticipantXmlAnnotationList)attributes).addOnly(att);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((ParticipantXmlAnnotationList)attributes).removeOnly(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((ParticipantXmlAnnotationList)attributes).removeOnly(att);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ParticipantXmlAnnotationList)attributes).clearOnly();
        }
    }

    protected class ParticipantXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public ParticipantXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {

            // we added a annotation, needs to add it in annotations
            ((ParticipantAnnotationList)annotations).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            // we removed a annotation, needs to remove it in annotations
            ((ParticipantAnnotationList)annotations).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ParticipantAnnotationList)annotations).clearOnly();
        }
    }

    private class ParticipantIdentificationMethodsList extends AbstractListHavingPoperties<ParticipantIdentificationMethod> {
        public ParticipantIdentificationMethodsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(ParticipantIdentificationMethod added) {

            if (identificationMethod == null){
                identificationMethod = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(ParticipantIdentificationMethod removed) {

            if (isEmpty()){
                identificationMethod = null;
            }
            else if (identificationMethod != null && removed.equals(identificationMethod)){
                identificationMethod = iterator().next();
            }
        }

        @Override
        protected void clearProperties() {

            identificationMethod = null;
        }
    }

    private class ExperimentalRolesList extends AbstractListHavingPoperties<ExperimentalRole> {
        public ExperimentalRolesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(ExperimentalRole added) {

            if (experimentalRole == null){
                experimentalRole = added;
            }
            else if (size() > 1 && psidev.psi.mi.jami.model.Participant.UNSPECIFIED_ROLE.equalsIgnoreCase(experimentalRole.getShortName().trim())){
                remove(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(ExperimentalRole removed) {

            if (isEmpty()){
                experimentalRole = new ExperimentalRole();
                experimentalRole.setShortName(UNSPECIFIED_ROLE);
                experimentalRole.setMIIdentifier(UNSPECIFIED_ROLE_MI);
            }
            else if (experimentalRole != null && removed.equals(experimentalRole)){
                experimentalRole = iterator().next();
            }
        }

        @Override
        protected void clearProperties() {

            experimentalRole = new ExperimentalRole();
            experimentalRole.setShortName(UNSPECIFIED_ROLE);
            experimentalRole.setMIIdentifier(UNSPECIFIED_ROLE_MI);
        }
    }

    protected class ExperimentalPreparationsList extends AbstractListHavingPoperties<CvTerm> {
        public ExperimentalPreparationsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.CvTerm added) {
            if (added instanceof ExperimentalPreparation){
                ((ExperimentalPreparationsXmlList)xmlExperimentalPreparations).addOnly((ExperimentalPreparation) added);
            }
            else {
                ExperimentalPreparation exp = new ExperimentalPreparation();
                CvTermCloner.copyAndOverrideCvTermProperties(added, exp);
                ((ExperimentalPreparationsXmlList)xmlExperimentalPreparations).addOnly(exp);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.CvTerm removed) {
            if (removed instanceof ExperimentalPreparation){
                ((ExperimentalPreparationsXmlList)xmlExperimentalPreparations).removeOnly(removed);
            }
            else {
                ExperimentalPreparation exp = new ExperimentalPreparation();
                CvTermCloner.copyAndOverrideCvTermProperties(removed, exp);
                ((ExperimentalPreparationsXmlList)xmlExperimentalPreparations).removeOnly(exp);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ExperimentalPreparationsXmlList)xmlExperimentalPreparations).clearOnly();
        }
    }

    protected class ExperimentalPreparationsXmlList extends AbstractListHavingPoperties<ExperimentalPreparation> {
        public ExperimentalPreparationsXmlList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(ExperimentalPreparation added) {

            ((ExperimentalPreparationsList) experimentalPreparations).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(ExperimentalPreparation removed) {

            ((ExperimentalPreparationsList) experimentalPreparations).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((ExperimentalPreparationsList) experimentalPreparations).clearOnly();
        }
    }

    protected class ParticipantFeaturesList extends AbstractListHavingPoperties<FeatureEvidence> {
        public ParticipantFeaturesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.FeatureEvidence added) {
            if (added instanceof Feature){
                added.setParticipant(getInstance());
                ((ParticipantXmlFeaturesList)xmlFeatures).addOnly((Feature) added);
            }
            else {
                Feature f = new Feature();
                FeatureCloner.copyAndOverrideFeatureProperties(added, f);
                f.setParticipant(getInstance());
                ((ParticipantXmlFeaturesList)xmlFeatures).addOnly(f);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.FeatureEvidence removed) {

            if (removed instanceof Feature){
                ((ParticipantXmlFeaturesList)xmlFeatures).removeOnly(removed);
            }
            else {
                Feature f = new Feature();
                FeatureCloner.copyAndOverrideFeatureProperties(removed, f);
                ((ParticipantXmlFeaturesList)xmlFeatures).removeOnly(f);
            }
            removed.setParticipant(null);
        }

        @Override
        protected void clearProperties() {
            for (Feature f : xmlFeatures){
                f.setParticipant(null);
            }
            // clear all annotations
            ((ParticipantXmlFeaturesList)xmlFeatures).clearOnly();
        }
    }

    protected class ParticipantXmlFeaturesList extends AbstractListHavingPoperties<Feature> {
        public ParticipantXmlFeaturesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Feature added) {

            added.setParticipant(getInstance());
            ((ParticipantFeaturesList) features).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Feature removed) {
            ((ParticipantFeaturesList) features).removeOnly(removed);
            removed.setParticipant(null);
        }

        @Override
        protected void clearProperties() {
            for (psidev.psi.mi.jami.model.Feature f : features){
                f.setParticipant(null);
            }
            ((ParticipantFeaturesList) features).clearOnly();
        }
    }

    private class HostOrganismsList extends AbstractListHavingPoperties<HostOrganism> {
        public HostOrganismsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(HostOrganism added) {

            if (expressedIn == null){
                expressedIn = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(HostOrganism removed) {

            if (isEmpty()){
                expressedIn = null;
            }
            else if (expressedIn != null && removed.equals(expressedIn)){
                expressedIn = iterator().next();
            }
        }

        @Override
        protected void clearProperties() {

            expressedIn = null;
        }
    }

    protected class ParticipantConfidencesList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Confidence> {
        public ParticipantConfidencesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Confidence added) {
            if (added instanceof Confidence){
                ((ParticipantXmlConfidencesList)confidenceList).addOnly((Confidence) added);
            }
            else {
                Confidence conf = cloneConfidence(added);
                ((ParticipantXmlConfidencesList)confidenceList).addOnly(conf);
            }
        }

        private Confidence cloneConfidence(psidev.psi.mi.jami.model.Confidence added) {
            Confidence conf = new Confidence();
            Unit unit = new Unit();
            unit.setShortName(added.getType().getShortName());
            unit.setMIIdentifier(added.getType().getMIIdentifier());
            conf.setUnit(unit);
            conf.setValue(added.getValue());
            return conf;
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Confidence removed) {
            if (removed instanceof Confidence){
                ((ParticipantXmlConfidencesList)confidenceList).removeOnly(removed);
            }
            else {
                Confidence conf = cloneConfidence(removed);
                ((ParticipantXmlConfidencesList)confidenceList).removeOnly(conf);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ParticipantXmlConfidencesList)confidenceList).clearOnly();
        }
    }

    protected class ParticipantXmlConfidencesList extends AbstractListHavingPoperties<Confidence> {
        public ParticipantXmlConfidencesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Confidence added) {

            ((ParticipantConfidencesList) confidences).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Confidence removed) {

            ((ParticipantConfidencesList) confidences).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((ParticipantConfidencesList) confidences).clearOnly();
        }
    }

    protected class ParticipantParametersList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Parameter> {
        public ParticipantParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Parameter added) {
            if (added instanceof Parameter){
                ((ParticipantXmlParametersList)parametersList).addOnly((Parameter) added);
            }
            else {
                Parameter param = cloneParameter(added);

                ((ParticipantXmlParametersList)parametersList).addOnly(param);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Parameter removed) {
            if (removed instanceof ExperimentalPreparation){
                ((ParticipantXmlParametersList)parametersList).removeOnly(removed);
            }
            else {
                Parameter param = cloneParameter(removed);
                ((ParticipantXmlParametersList)parametersList).removeOnly(param);
            }
        }

        private Parameter cloneParameter(psidev.psi.mi.jami.model.Parameter removed) {
            Parameter param = new Parameter();
            param.setTerm(removed.getType().getShortName());
            param.setTermAc(removed.getType().getMIIdentifier());
            if (removed.getUnit() != null){
                param.setUnit(removed.getType().getShortName());
                param.setUnitAc(removed.getType().getMIIdentifier());
            }
            param.setBase(removed.getValue().getBase());
            param.setExponent(removed.getValue().getExponent());
            param.setFactor(removed.getValue().getFactor().doubleValue());
            if (removed.getUncertainty() != null){
                param.setUncertainty(removed.getUncertainty().doubleValue());
            }
            return param;
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ParticipantXmlParametersList)parametersList).clearOnly();
        }
    }

    protected class ParticipantXmlParametersList extends AbstractListHavingPoperties<Parameter> {
        public ParticipantXmlParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Parameter added) {

            ((ParticipantParametersList) parameters).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Parameter removed) {

            ((ParticipantParametersList) parameters).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((ParticipantParametersList) parameters).clearOnly();
        }
    }
}