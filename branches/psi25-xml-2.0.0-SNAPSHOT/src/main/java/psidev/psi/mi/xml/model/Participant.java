/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.clone.*;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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

public class Participant extends DefaultParticipantEvidence implements Component, HasId, NamesContainer, XrefContainer, AttributeContainer, FileSourceContext{

    private int id;

    private Names names;

    private Xref xref;

    private InteractorRef interactorRef;

    private InteractionRef interactionRef;

    private Collection<ParticipantIdentificationMethod> participantIdentificationMethods;

    private Collection<ExperimentalRole> experimentalRoles;

    private Collection<ExperimentalPreparation> xmlExperimentalPreparations;

    private Collection<ExperimentalInteractor> experimentalInteractors;

    private Collection<Feature> xmlFeatures;

    private Collection<HostOrganism> hostOrganisms;

    private Collection<Confidence> confidenceList;

    private Collection<Parameter> parametersList;

    private Collection<Attribute> attributes;

    private boolean isInteractorAComplex = false;

    private PsiXmlFileLocator locator;

    ///////////////////////////
    // Constructors

    public Participant() {
        super(new Interactor(), new BiologicalRole(), new ExperimentalRole(), null);

        getExperimentalRole().setShortName(UNSPECIFIED_ROLE);
        getExperimentalRole().setMIIdentifier(UNSPECIFIED_ROLE_MI);
        getBiologicalRole().setShortName(UNSPECIFIED_ROLE);
        getBiologicalRole().setMIIdentifier(UNSPECIFIED_ROLE_MI);
        ((ExperimentalRolesList)getExperimentalRoles()).addOnly(getExperimentalRole());
    }

    @Override
    protected void initialiseAliases() {
        initialiseAliasesWith(new ParticipantAliasList());
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new ParticipantXrefList());
    }

    @Override
    public void initialiseAnnotations() {
        initialiseAnnotationsWith(new ParticipantAnnotationList());
    }

    @Override
    protected void initialiseExperimentalPreparations() {
        initialiseExperimentalPreparationsWith(new ExperimentalPreparationsList());
    }

    @Override
    protected void initialiseFeatureEvidences() {
        super.initialiseFeatureEvidencesWith(Collections.EMPTY_LIST);
    }

    @Override
    protected void initialiseFeatureEvidencesWith(Collection<FeatureEvidence> features) {
        if (features == null){
            super.initialiseFeatureEvidencesWith(Collections.EMPTY_LIST);
        }
        else {
            if (xmlFeatures == null){
                xmlFeatures = new ArrayList<Feature>();
            }
            else {
                xmlFeatures.clear();
                for (FeatureEvidence f : features){
                    addFeatureEvidence(f);
                }
            }
        }
    }

    @Override
    protected void initialiseConfidences() {
        initialiseConfidencesWith(new ParticipantConfidencesList());
    }

    @Override
    protected void initialiseParameters() {
        initialiseParametersWith(new ParticipantParametersList());
    }

    ///////////////////////////
    // Getters and Setters


    public PsiXmlFileLocator getSourceLocator() {
        return this.locator;
    }

    public void setSourceLocator(PsiXmlFileLocator locator) {
        this.locator = locator;
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
                getAliases().clear();
            }
            this.names.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            getAliases().addAll(value.getAliases());
        }
        else if (this.names != null) {
            getAliases().clear();
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
                getXrefs().clear();
            }
            this.xref = new ParticipantXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            getXrefs().clear();
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
        if (interactorRef != null){
           isInteractorAComplex = false;
        }
    }

    /**
     * Check if the optional interactor is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInteractor() {
        return getInteractor() != null && !isInteractorAComplex;
    }

    /**
     * Gets the value of the interactor property.
     *
     * @return possible object is {@link Interactor }
     */
    public Interactor getInteractor() {
        return isInteractorAComplex ? null : (Interactor) super.getInteractor();
    }

    /**
     * Sets the value of the interactor property.
     *
     * @param value allowed object is {@link Interactor }
     */
    public void setInteractor( Interactor value ) {

        if (value != null){
            super.setInteractor(value);
            isInteractorAComplex = false;
        }
        else if (!isInteractorAComplex) {
            super.setInteractor(new Interactor());
        }
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
        if (interactionRef != null){
             isInteractorAComplex = true;
        }
    }

    /**
     * Check if the optional interaction is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInteraction() {
        return getInteraction() != null;
    }

    public Interaction getInteraction() {
        return isInteractorAComplex ? (Interaction) super.getInteractor() : null;
    }

    public void setInteraction( Interaction interaction ) {
        if (interaction != null){
            isInteractorAComplex = true;
            super.setInteractor(interaction);
        }
        else if (isInteractorAComplex){
           super.setInteractor(new Interaction());
        }
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
        if (participantIdentificationMethods == null){
            participantIdentificationMethods = new ParticipantIdentificationMethodsList();
        }
        return participantIdentificationMethods;
    }

    /**
     * Check if the optional biologicalRole is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBiologicalRole() {
        return getBiologicalRole() != null;
    }

    /**
     * Sets the value of the biologicalRole property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setBiologicalRole( BiologicalRole value ) {
        if (value == null){
            super.setBiologicalRole(new BiologicalRole());
            getBiologicalRole().setShortName(UNSPECIFIED_ROLE);
            getBiologicalRole().setMIIdentifier(UNSPECIFIED_ROLE_MI);
        }
        else {
            super.setBiologicalRole(value);
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
        if (experimentalRoles == null){
            experimentalRoles = new ExperimentalRolesList();
        }
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
        if (xmlExperimentalPreparations == null){
            xmlExperimentalPreparations = new ExperimentalPreparationsXmlList();
        }
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
    public Collection<Feature> getFeatures() {
        if (xmlFeatures == null){
            xmlFeatures = new ArrayList<Feature>();
        }
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
        if (hostOrganisms == null){
            hostOrganisms  = new HostOrganismsList();
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
        if (confidenceList == null){
            confidenceList = new ParticipantXmlConfidencesList();
        }
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
        if (parametersList == null){
            parametersList = new ParticipantXmlParametersList();
        }
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
        if (attributes == null){
            attributes = new ParticipantXmlAnnotationList();
        }
        return attributes;
    }

    protected Participant getInstance(){
        return this;
    }

    ////////////////////////
    // Object override

    @Override
    public HostOrganism getExpressedInOrganism() {
        return (HostOrganism) super.getExpressedInOrganism();
    }

    @Override
    public ParticipantIdentificationMethod getIdentificationMethod() {
        return (ParticipantIdentificationMethod) super.getIdentificationMethod();
    }

    @Override
    public ExperimentalRole getExperimentalRole() {
        return (ExperimentalRole) super.getExperimentalRole();
    }

    @Override
    public BiologicalRole getBiologicalRole() {
        return (BiologicalRole) super.getBiologicalRole();
    }

    @Override
    public void setInteractor(psidev.psi.mi.jami.model.Interactor interactor) {
        if (interactor == null){
            super.setInteractor(null);
        }
        else if (interactor instanceof Interactor){
            super.setInteractor(interactor);
        }
        else {
            psidev.psi.mi.jami.model.Interactor convertedInteractor = null;

            if (interactor instanceof Complex){
                convertedInteractor = new Interaction();
                InteractorCloner.copyAndOverrideComplexProperties((Complex) interactor, (Interaction)convertedInteractor);
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
            if (getIdentificationMethod() != null){
                getParticipantIdentificationMethods().remove(getIdentificationMethod());
            }
            if(identificationMethod instanceof ParticipantIdentificationMethod){
                super.setIdentificationMethod(identificationMethod);
                ((ParticipantIdentificationMethodsList)getParticipantIdentificationMethods()).addOnly(getIdentificationMethod());
            }
            else {
                super.setIdentificationMethod(new ParticipantIdentificationMethod());

                CvTermCloner.copyAndOverrideCvTermProperties(identificationMethod, getIdentificationMethod());
                ((ParticipantIdentificationMethodsList) getParticipantIdentificationMethods()).addOnly(getIdentificationMethod());
            }
        }
        else if (!getParticipantIdentificationMethods().isEmpty()) {
            super.setIdentificationMethod(null);
            ((ParticipantIdentificationMethodsList)getParticipantIdentificationMethods()).clearOnly();
        }
    }

    protected void setIdentificationMethodOnly(CvTerm identificationMethod) {
        super.setIdentificationMethod(identificationMethod);
    }

    @Override
    public void setBiologicalRole(CvTerm bioRole) {
        if (bioRole == null){
            super.setBiologicalRole(new BiologicalRole());
            getBiologicalRole().setShortName(UNSPECIFIED_ROLE);
            getBiologicalRole().setMIIdentifier(UNSPECIFIED_ROLE_MI);
        }
        else if (bioRole instanceof BiologicalRole){
            super.setBiologicalRole(bioRole);
        }
        else {
            super.setBiologicalRole(new BiologicalRole());
            CvTermCloner.copyAndOverrideCvTermProperties(bioRole, getBiologicalRole());
        }
    }

    @Override
    public void setExperimentalRole(CvTerm expRole) {
        if (expRole != null){
            if (getExperimentalRole() != null){
                getExperimentalRoles().remove(getExperimentalRole());
            }
            if(expRole instanceof ExperimentalRole){
                super.setExperimentalRole(expRole);
                ((ExperimentalRolesList)getExperimentalRoles()).addOnly(getExperimentalRole());
            }
            else {
                super.setExperimentalRole(new ExperimentalRole());

                CvTermCloner.copyAndOverrideCvTermProperties(expRole, getExperimentalRole());
                ((ExperimentalRolesList) getExperimentalRoles()).addOnly(getExperimentalRole());
            }
        }
        else if (!getExperimentalRoles().isEmpty()) {
            experimentalRoles.clear();
        }
    }

    protected void setExperimentalRoleOnly(CvTerm expRole) {
        super.setExperimentalRole(expRole);
    }

    @Override
    public void setExpressedInOrganism(psidev.psi.mi.jami.model.Organism organism) {
        if (organism != null){
            if (getExpressedInOrganism() != null){
                getHostOrganisms().remove(getExpressedInOrganism());
            }
            if(organism instanceof HostOrganism){
                super.setExpressedInOrganism(organism);
                ((HostOrganismsList)getHostOrganisms()).addOnly(getExpressedInOrganism());
            }
            else {
                super.setExpressedInOrganism(new HostOrganism());

                OrganismCloner.copyAndOverrideOrganismProperties(organism, getExpressedInOrganism());
                ((HostOrganismsList)getHostOrganisms()).addOnly(getExpressedInOrganism());
            }
        }
        else if (!getHostOrganisms().isEmpty()) {
            super.setExpressedInOrganism(null);
            ((HostOrganismsList)getHostOrganisms()).clearOnly();
        }
    }

    protected void setExpressedInOrganismOnly(psidev.psi.mi.jami.model.Organism organism) {
        super.setExpressedInOrganism(organism);
    }

    @Override
    public void setInteractionEvidence(InteractionEvidence interaction) {
        if (interaction == null){
            super.setInteractionEvidence(null);
        }
        else if (interaction instanceof Interaction){
            super.setInteractionEvidence(interaction);
        }
        else {
            Interaction convertedInteraction = new Interaction();

            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, convertedInteraction);
            super.setInteractionEvidence(convertedInteraction);
        }
    }

    @Override
    public void setInteractionEvidenceAndAddParticipantEvidence(InteractionEvidence interaction) {
        if (interaction == null){
            super.setInteractionEvidence(null);
        }
        else if (interaction instanceof Interaction){
            interaction.addParticipantEvidence(this);
        }
        else {
            Interaction convertedInteraction = new Interaction();

            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, convertedInteraction);
            convertedInteraction.addParticipantEvidence(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Participant" );
        sb.append( "{id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", interactor=" ).append( getInteractor() );
        sb.append( ", participantIdentificationMethods=" ).append( participantIdentificationMethods );
        sb.append( ", biologicalRole=" ).append( getBiologicalRole() );
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
        if ( getBiologicalRole() != null ? !getBiologicalRole().equals(that.getBiologicalRole()) : that.getBiologicalRole() != null )
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
        if ( getInteractor() != null ? !getInteractor().equals(that.getInteractor()) : that.getInteractor() != null ) return false;
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
        result = 31 * result + ( getInteractor() != null ? getInteractor().hashCode() : 0 );
        //result = 31 * result + (interaction != null ? interaction.hashCode() : 0);
        result = 31 * result + ( interactionRef != null ? interactionRef.hashCode() : 0 );
        result = 31 * result + ( participantIdentificationMethods != null ? participantIdentificationMethods.hashCode() : 0 );
        result = 31 * result + ( getBiologicalRole() != null ? getBiologicalRole().hashCode() : 0 );
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

    public void setComplexAndAddComponent(Complex interaction) {
        if (interaction == null){
            super.setInteractionEvidence(null);
        }
        else if (interaction instanceof Interaction){
            interaction.addComponent(this);
        }
        else {
            Interaction convertedInteraction = new Interaction();

            InteractorCloner.copyAndOverrideComplexProperties(interaction, convertedInteraction);
            convertedInteraction.addComponent(this);
        }
    }

    public Interaction getComplex() {
        return getInteractionEvidence();
    }

    public void setComplex(Complex interaction) {

        if (interaction == null){
            setInteraction(null);
        }
        else if (interaction instanceof Interaction){
            setInteraction((Interaction)interaction);
        }
        else {
            Interaction convertedInteraction = new Interaction();

            InteractorCloner.copyAndOverrideComplexProperties(interaction, convertedInteraction);
            setInteraction(convertedInteraction);
        }
    }

    public Collection<? extends ComponentFeature> getComponentFeatures() {
        return getFeatures();
    }

    public boolean addComponentFeature(ComponentFeature feature) {
        if (feature == null){
            return false;
        }
        if (xmlFeatures == null){
            xmlFeatures = new ArrayList<Feature>();
        }

        if (feature instanceof Feature){
            if (xmlFeatures.add((Feature)feature)){
                feature.setComponent(this);
                return true;
            }
        }
        else{
            Feature f = new Feature();
            FeatureCloner.copyAndOverrideComponentFeaturesProperties(feature, f);
            if (xmlFeatures.add(f)){
                f.setComponent(this);
                return true;
            }
        }
        return false;
    }

    public boolean removeComponentFeature(ComponentFeature feature) {
        if (feature == null){
            return false;
        }
        if (xmlFeatures == null){
            xmlFeatures = new ArrayList<Feature>();
        }

        if (feature instanceof Feature){
            if (xmlFeatures.remove(feature)){
                feature.setComponent(null);
                return true;
            }
        }
        else{
            Feature f = new Feature();
            FeatureCloner.copyAndOverrideComponentFeaturesProperties(feature, f);
            if (xmlFeatures.remove(f)){
                f.setComponent(null);
                return true;
            }
        }
        return false;
    }

    public boolean addAllComponentFeatures(Collection<? extends ComponentFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ComponentFeature feature : features){
            if (addComponentFeature(feature)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllComponentFeatures(Collection<? extends ComponentFeature> features) {
        if (features == null){
            return false;
        }

        boolean removed = false;
        for (ComponentFeature feature : features){
            if (removeComponentFeature(feature)){
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public Interaction getInteractionEvidence() {
        return (Interaction) super.getInteractionEvidence();
    }

    @Override
    public Collection<? extends FeatureEvidence> getFeatureEvidences() {
        return getFeatures();
    }

    @Override
    public boolean addFeatureEvidence(FeatureEvidence feature) {
        if (feature == null){
            return false;
        }
        if (xmlFeatures == null){
            xmlFeatures = new ArrayList<Feature>();
        }

        if (feature instanceof Feature){
            if (xmlFeatures.add((Feature)feature)){
                feature.setParticipantEvidence(this);
                return true;
            }
        }
        else{
            Feature f = new Feature();
            FeatureCloner.copyAndOverrideFeatureEvidenceProperties(feature, f);
            if (xmlFeatures.add(f)){
                f.setParticipantEvidence(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeFeatureEvidence(FeatureEvidence feature) {
        if (feature == null){
            return false;
        }
        if (xmlFeatures == null){
            xmlFeatures = new ArrayList<Feature>();
        }

        if (feature instanceof Feature){
            if (xmlFeatures.remove(feature)){
                feature.setParticipantEvidence(null);
                return true;
            }
        }
        else{
            Feature f = new Feature();
            FeatureCloner.copyAndOverrideFeatureEvidenceProperties(feature, f);
            if (xmlFeatures.remove(f)){
                f.setParticipantEvidence(null);
                return true;
            }
        }
        return false;
    }

    protected Collection<psidev.psi.mi.jami.model.Alias> getParticipantAliases(){
        return super.getAliases();
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
                ((ParticipantAliasList) getParticipantAliases()).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((ParticipantAliasList)getParticipantAliases()).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((ParticipantAliasList)getParticipantAliases()).clearOnly();
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
                extendedSecondaryRefList.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    ((ParticipantXrefList)getXrefs()).addOnly(value);
                }
            }
            else {
                ((ParticipantXrefList)getXrefs()).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    ((ParticipantXrefList)getXrefs()).addOnly(value);
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
                ((ParticipantXrefList)getXrefs()).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                ((ParticipantXrefList)getXrefs()).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((ParticipantXrefList)getXrefs()).retainAllOnly(primary);
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

    private class ParticipantAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public ParticipantAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((ParticipantXmlAnnotationList)getAttributes()).addOnly((Attribute) added);
            }
            else {
                Attribute att = new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue());
                ((ParticipantXmlAnnotationList)getAttributes()).addOnly(att);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((ParticipantXmlAnnotationList)getAttributes()).removeOnly(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((ParticipantXmlAnnotationList)getAttributes()).removeOnly(att);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ParticipantXmlAnnotationList)getAttributes()).clearOnly();
        }
    }

    private class ParticipantXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public ParticipantXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {

            // we added a annotation, needs to add it in annotations
            ((ParticipantAnnotationList)getAnnotations()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            // we removed a annotation, needs to remove it in annotations
            ((ParticipantAnnotationList)getAnnotations()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ParticipantAnnotationList)getAnnotations()).clearOnly();
        }
    }

    private class ParticipantIdentificationMethodsList extends AbstractListHavingPoperties<ParticipantIdentificationMethod> {
        public ParticipantIdentificationMethodsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(ParticipantIdentificationMethod added) {

            if (getIdentificationMethod() == null){
                setIdentificationMethodOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(ParticipantIdentificationMethod removed) {

            if (isEmpty()){
                setIdentificationMethodOnly(null);
            }
            else if (getIdentificationMethod() != null && removed.equals(getIdentificationMethod())){
                setIdentificationMethodOnly(iterator().next());
            }
        }

        @Override
        protected void clearProperties() {

            setIdentificationMethodOnly(null);
        }
    }

    private class ExperimentalRolesList extends AbstractListHavingPoperties<ExperimentalRole> {
        public ExperimentalRolesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(ExperimentalRole added) {

            if (getExperimentalRole() == null){
                setExperimentalRoleOnly(added);
            }
            else if (size() == 2 && psidev.psi.mi.jami.model.Participant.UNSPECIFIED_ROLE.equalsIgnoreCase(getExperimentalRole().getShortName().trim())){
                remove(0);
                setExperimentalRoleOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(ExperimentalRole removed) {

            if (isEmpty()){
                setExperimentalRoleOnly(new ExperimentalRole());
                getExperimentalRole().setShortName(UNSPECIFIED_ROLE);
                getExperimentalRole().setMIIdentifier(UNSPECIFIED_ROLE_MI);
            }
            else if (getExperimentalRole() != null && removed.equals(getExperimentalRole())){
                setExperimentalRoleOnly(iterator().next());
            }
        }

        @Override
        protected void clearProperties() {

            setExperimentalRoleOnly(new ExperimentalRole());
            getExperimentalRole().setShortName(UNSPECIFIED_ROLE);
            getExperimentalRole().setMIIdentifier(UNSPECIFIED_ROLE_MI);
            addOnly(getExperimentalRole());
        }
    }

    private class ExperimentalPreparationsList extends AbstractListHavingPoperties<CvTerm> {
        public ExperimentalPreparationsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.CvTerm added) {
            if (added instanceof ExperimentalPreparation){
                ((ExperimentalPreparationsXmlList)getParticipantExperimentalPreparations()).addOnly((ExperimentalPreparation) added);
            }
            else {
                ExperimentalPreparation exp = new ExperimentalPreparation();
                CvTermCloner.copyAndOverrideCvTermProperties(added, exp);
                ((ExperimentalPreparationsXmlList)getParticipantExperimentalPreparations()).addOnly(exp);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.CvTerm removed) {
            if (removed instanceof ExperimentalPreparation){
                ((ExperimentalPreparationsXmlList)getParticipantExperimentalPreparations()).removeOnly(removed);
            }
            else {
                ExperimentalPreparation exp = new ExperimentalPreparation();
                CvTermCloner.copyAndOverrideCvTermProperties(removed, exp);
                ((ExperimentalPreparationsXmlList)getParticipantExperimentalPreparations()).removeOnly(exp);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ExperimentalPreparationsXmlList)getParticipantExperimentalPreparations()).clearOnly();
        }
    }

    private class ExperimentalPreparationsXmlList extends AbstractListHavingPoperties<ExperimentalPreparation> {
        public ExperimentalPreparationsXmlList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(ExperimentalPreparation added) {

            ((ExperimentalPreparationsList) getExperimentalPreparations()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(ExperimentalPreparation removed) {

            ((ExperimentalPreparationsList) getExperimentalPreparations()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((ExperimentalPreparationsList) getExperimentalPreparations()).clearOnly();
        }
    }

    private class HostOrganismsList extends AbstractListHavingPoperties<HostOrganism> {
        public HostOrganismsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(HostOrganism added) {

            if (getExpressedInOrganism() == null){
                setExpressedInOrganismOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(HostOrganism removed) {

            if (isEmpty()){
                setExpressedInOrganismOnly(null);
            }
            else if (getExpressedInOrganism() != null && removed.equals(getExpressedInOrganism())){
                setExpressedInOrganismOnly(iterator().next());
            }
        }

        @Override
        protected void clearProperties() {

            setExpressedInOrganismOnly(null);
        }
    }

    private class ParticipantConfidencesList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Confidence> {
        public ParticipantConfidencesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Confidence added) {
            if (added instanceof Confidence){
                ((ParticipantXmlConfidencesList)getConfidenceList()).addOnly((Confidence) added);
            }
            else {
                Confidence conf = cloneConfidence(added);
                ((ParticipantXmlConfidencesList)getConfidenceList()).addOnly(conf);
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
                ((ParticipantXmlConfidencesList)getConfidenceList()).removeOnly(removed);
            }
            else {
                Confidence conf = cloneConfidence(removed);
                ((ParticipantXmlConfidencesList)getConfidenceList()).removeOnly(conf);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ParticipantXmlConfidencesList)getConfidenceList()).clearOnly();
        }
    }

    private class ParticipantXmlConfidencesList extends AbstractListHavingPoperties<Confidence> {
        public ParticipantXmlConfidencesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Confidence added) {

            ((ParticipantConfidencesList) getConfidences()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Confidence removed) {

            ((ParticipantConfidencesList) getConfidences()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((ParticipantConfidencesList) getConfidences()).clearOnly();
        }
    }

    private class ParticipantParametersList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Parameter> {
        public ParticipantParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Parameter added) {
            if (added instanceof Parameter){
                ((ParticipantXmlParametersList)getParametersList()).addOnly((Parameter) added);
            }
            else {
                Parameter param = cloneParameter(added);

                ((ParticipantXmlParametersList)getParametersList()).addOnly(param);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Parameter removed) {
            if (removed instanceof ExperimentalPreparation){
                ((ParticipantXmlParametersList)getParametersList()).removeOnly(removed);
            }
            else {
                Parameter param = cloneParameter(removed);
                ((ParticipantXmlParametersList)getParametersList()).removeOnly(param);
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
            ((ParticipantXmlParametersList)getParametersList()).clearOnly();
        }
    }

    private class ParticipantXmlParametersList extends AbstractListHavingPoperties<Parameter> {
        public ParticipantXmlParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Parameter added) {

            ((ParticipantParametersList) getParameters()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Parameter removed) {

            ((ParticipantParametersList) getParameters()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((ParticipantParametersList) getParameters()).clearOnly();
        }
    }
}