/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.utils.ChecksumUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.*;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


public class Interaction extends DefaultInteractionEvidence implements Complex, HasId, NamesContainer, XrefContainer, AttributeContainer {

    private int id;

    private Names names;

    private Xref xref;

    private Availability xmlAvailability;

    private Collection<ExperimentDescription> experimentDescriptions = new InteractionExperimentsList();

    private Collection<ExperimentRef> experimentRefs;

    private Collection<Participant> xmlParticipants = new InteractionXmlParticipantsList();

    private Collection<InferredInteraction> inferredInteractions = new InferredInteractionsList();

    private Collection<InteractionType> interactionTypes = new InteractionTypesList();

    private Boolean modelled;

    private Boolean intraMolecular;

    private Collection<Confidence> confidencesList = new InteractionXmlConfidencesList();

    private Collection<Parameter> parametersList = new InteractionXmlParametersList();

    private Collection<Attribute> attributeList = new InteractionXmlAnnotationList();

    ///////////////////////////
    // Constructors

    public Interaction() {
        super();
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new InteractionIdentifierList();
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new InteractionXrefList();
    }

    @Override
    protected void initializeParticipants() {
        this.participants = new InteractionParticipantsList();
    }

    @Override
    protected void initializeConfidences() {
        this.confidences = new InteractionConfidencesList();
    }

    @Override
    protected void initializeParameters() {
        this.parameters = new InteractionParametersList();
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new InteractionAnnotationList();
    }

    @Override
    protected void initializeChecksum() {
        this.checksums = new InteractionChecksumList();
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
     * Check if the optional imexId is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasImexId() {
        return imexId != null;
    }

    /**
     * Sets the value of the imexId property.
     *
     * @param value allowed object is {@link String }
     */
    public void setImexId( String value ) {
        if (value == null){
            xrefs.remove(imexId);
        }
        else{
            assignImexId(value);
        }
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
                this.names = new InteractionNames();
            }
            this.names.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            this.names.getAliases().clear();
            this.names.getAliases().addAll(value.getAliases());
        }
        else if (this.names != null) {
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
                identifiers.clear();
                this.xrefs.clear();
            }
            this.xref = new InteractionXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            identifiers.clear();
            xrefs.clear();
            this.xref = null;
        }
    }

    /**
     * Check if the optional xmlAvailability is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasAvailability() {
        return xmlAvailability != null;
    }

    /**
     * Gets the value of the xmlAvailability property.
     *
     * @return possible object is {@link Availability }
     */
    public Availability getInteractionAvailability() {
        return xmlAvailability;
    }

    /**
     * Sets the value of the xmlAvailability property.
     *
     * @param value allowed object is {@link Availability }
     */
    public void setAvailability( Availability value ) {
        if (value == null){
            availability = null;
            xmlAvailability = null;
        }
        else if (this.xmlAvailability != null){
            this.xmlAvailability.setValue(value.getValue());
            this.xmlAvailability.setId(value.getId());
        }
        else {
            this.xmlAvailability = new InteractionAvailability(value.getId(), value.getValue());
        }
    }

    public boolean hasExperiments() {
        return experimentDescriptions != null && !experimentDescriptions.isEmpty();
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return possible object is {@link ExperimentDescription }
     */
    public Collection<ExperimentDescription> getExperimentDescriptions() {
        return experimentDescriptions;
    }

    public Collection<Experiment> getExperiments() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Component> getComponents() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getPhysicalProperties() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPhysicalProperties(String properties) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean addComponent(Component part) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean removeComponent(Component part) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean addAllComponents(Collection<? extends Component> part) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean removeAllComponents(Collection<? extends Component> part) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean hasExperimentRefs() {
        return experimentRefs != null && !experimentRefs.isEmpty();
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return possible object is {@link ExperimentRef }
     */
    public Collection<ExperimentRef> getExperimentRefs() {
        if ( experimentRefs == null ) {
            experimentRefs = new ArrayList<ExperimentRef>();
        }
        return experimentRefs;
    }

    /**
     * Gets the value of the participantList property.
     *
     * @return possible object is {@link Participant }
     */
    public Collection<Participant> getInteractionParticipants() {
        return xmlParticipants;
    }

    /**
     * Check if the optional inferredInteractions is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInferredInteractions() {
        return ( inferredInteractions != null ) && ( !inferredInteractions.isEmpty() );
    }

    /**
     * Gets the value of the inferredInteractionList property.
     *
     * @return possible object is {@link InferredInteraction }
     */
    public Collection<InferredInteraction> getInferredInteractions() {
        return inferredInteractions;
    }

    /**
     * Check if the optional interactionTypes is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasInteractionTypes() {
        return ( interactionTypes != null ) && ( !interactionTypes.isEmpty() );
    }

    /**
     * Gets the value of the interactionType property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the interactionType property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInteractionType().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link CvType }
     */
    public Collection<InteractionType> getInteractionTypes() {
        return interactionTypes;
    }

    /**
     * Check if the optional modelled is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasModelled() {
        return modelled != null;
    }

    /**
     * Gets the value of the modelled property.
     *
     * @return possible object is {@link Boolean }
     */
    public boolean isModelled() {
        if ( modelled == null ) {
            return false;
        }
        return modelled;
    }

    /**
     * Sets the value of the modelled property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setModelled( boolean value ) {
        this.modelled = value;
    }

    /**
     * Gets the value of the intraMolecular property.
     *
     * @return possible object is {@link Boolean }
     */
    public boolean isIntraMolecular() {
        if ( intraMolecular == null ) {
            return false;
        }
        return intraMolecular;
    }

    /**
     * Sets the value of the intraMolecular property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setIntraMolecular( boolean value ) {
        this.intraMolecular = value;
    }

    /**
     * Gets the value of the negative property.
     *
     * @return possible object is {@link Boolean }
     */
    public boolean isNegative() {
        return isNegative;
    }

    /**
     * Sets the value of the negative property.
     *
     * @param value allowed object is {@link Boolean }
     */
    public void setNegative( boolean value ) {
        this.isNegative = value;
    }

    /**
     * Check if the optional confidencesList is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasConfidences() {
        return ( confidencesList != null ) && ( !confidencesList.isEmpty() );
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return possible object is {@link Confidence }
     */
    public Collection<Confidence> getConfidencesList() {
        return confidencesList;
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
        return ( attributeList != null ) && ( !attributeList.isEmpty() );
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return possible object is {@link Attribute }
     */
    public Collection<Attribute> getAttributes() {
        return attributeList;
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return possible object is {@link Attribute }
     */
    @Deprecated
    public Collection<Attribute> getAttributeList() {
        return getAttributes();
    }

    protected void processAddedXrefEvent(psidev.psi.mi.jami.model.Xref added) {

        // the added identifier is imex and the current imex is not set
        if (imexId == null && XrefUtils.isXrefFromDatabase(added, psidev.psi.mi.jami.model.Xref.IMEX_MI, psidev.psi.mi.jami.model.Xref.IMEX)){
            // the added xref is imex-primary
            if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY)){
                imexId = added;
            }
        }
    }

    protected void processRemovedXrefEvent(psidev.psi.mi.jami.model.Xref removed) {
        // the removed identifier is pubmed
        if (imexId != null && imexId.equals(removed)){
            imexId = null;
        }
    }

    protected void clearPropertiesLinkedToXrefs() {
        imexId = null;
    }

    protected Interaction getInstance(){
        return this;
    }

    protected void processAddedChecksumEvent(Checksum added) {

        if (rigid == null && ChecksumUtils.doesChecksumHaveMethod(added, Checksum.RIGID_MI, Checksum.RIGID)){
            rigid = added;
        }
    }

    protected void processRemovedChecksumEvent(Checksum removed) {
        if (rigid != null && rigid.equals(removed)){
            rigid = null;
        }
    }

    protected void clearPropertiesLinkedToChecksums() {
        rigid = null;
    }

    ///////////////////////////
    // Object override

    @Override
    public void setShortName(String name) {
        if (name == null){
           this.names = null;
        }
        else if (names != null){
            names.setShortLabel(name);
        }
        else  {
            names = new InteractionNames();
            names.setShortLabel(name);
        }
    }

    @Override
    public void setAvailability(String availability) {
        if (availability == null){
            this.xmlAvailability = null;
        }
        else if (this.xmlAvailability == null){
            this.xmlAvailability = new InteractionAvailability(availability);
        }
        else {
            this.xmlAvailability.setValue(availability);
        }
    }

    @Override
    public void setExperiment(Experiment experiment) {
        if (experiment != null){
            if (this.experiment != null){
                experimentDescriptions.remove(this.experiment);
            }
            if(experiment instanceof ExperimentDescription){
                this.experiment = experiment;
                ((InteractionExperimentsList)experimentDescriptions).addOnly((ExperimentDescription) this.experiment);
            }
            else {
                this.experiment = new ExperimentDescription();

                ExperimentCloner.copyAndOverrideExperimentProperties(experiment, this.experiment);
                ((InteractionExperimentsList)experimentDescriptions).addOnly((ExperimentDescription) this.experiment);
            }
        }
        else if (!this.experimentDescriptions.isEmpty()) {
            this.experiment = null;
            ((InteractionExperimentsList)experimentDescriptions).clearOnly();
        }
    }

    @Override
    public void setType(CvTerm term) {
        if (term != null){
            if (this.type != null){
                interactionTypes.remove(this.type);
            }
            if(term instanceof InteractionType){
                this.type = term;
                ((InteractionTypesList)interactionTypes).addOnly((InteractionType) this.type);
            }
            else {
                this.type = new InteractionType();

                CvTermCloner.copyAndOverrideCvTermProperties(term, this.type);
                ((InteractionTypesList)interactionTypes).addOnly((InteractionType) this.type);
            }
        }
        else if (!this.interactionTypes.isEmpty()) {
            this.type = null;
            ((InteractionTypesList)interactionTypes).clearOnly();
        }
    }

    @Override
    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        super.setExperimentAndAddInteractionEvidence(experiment);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Interaction" );
        sb.append( "{id=" ).append( id );
        sb.append( ", imexId='" ).append( imexId ).append( '\'' );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", xmlAvailability=" ).append(xmlAvailability);
        sb.append( ", experimentDescriptions=" ).append(experimentDescriptions);
        sb.append( ", xmlParticipants=" ).append(xmlParticipants);
        sb.append( ", inferredInteractions=" ).append( inferredInteractions );
        sb.append( ", interactionTypes=" ).append( interactionTypes );
        sb.append( ", modelled=" ).append( modelled );
        sb.append( ", intraMolecular=" ).append( intraMolecular );
        sb.append( ", negative=" ).append( isNegative );
        sb.append( ", confidencesList=" ).append(confidencesList);
        sb.append( ", parametersList=" ).append(parametersList);
        sb.append( ", attributes=" ).append( attributeList );
        sb.append( '}' );
        return sb.toString();
    }

    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Interaction that = ( Interaction ) o;

        if ( id != that.id ) return false;
        if ( attributeList != null ? !attributeList.equals( that.attributeList ) : that.attributeList != null )
            return false;
        if ( xmlAvailability != null ? !xmlAvailability.equals( that.xmlAvailability) : that.xmlAvailability != null )
            return false;
        if ( confidencesList != null ? !confidencesList.equals( that.confidencesList) : that.confidencesList != null ) return false;
        if ( experimentDescriptions != null ? !experimentDescriptions.equals( that.experimentDescriptions) : that.experimentDescriptions != null ) return false;
        if ( imexId != null ? !imexId.equals( that.imexId ) : that.imexId != null ) return false;
        if ( inferredInteractions != null ? !inferredInteractions.equals( that.inferredInteractions ) : that.inferredInteractions != null )
            return false;
        if ( interactionTypes != null ? !interactionTypes.equals( that.interactionTypes ) : that.interactionTypes != null )
            return false;
        if ( intraMolecular != null ? !intraMolecular.equals( that.intraMolecular ) : that.intraMolecular != null )
            return false;
        if ( modelled != null ? !modelled.equals( that.modelled ) : that.modelled != null ) return false;
        if ( names != null ? !names.equals( that.names ) : that.names != null ) return false;
        if ( isNegative != that.isNegative ) return false;
        if ( parametersList != null ? !parametersList.equals( that.parametersList) : that.parametersList != null ) return false;
        if ( xmlParticipants != null ? !xmlParticipants.equals( that.xmlParticipants) : that.xmlParticipants != null )
            return false;
        if ( xref != null ? !xref.equals( that.xref ) : that.xref != null ) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = id;
        result = 31 * result + ( imexId != null ? imexId.hashCode() : 0 );
        result = 31 * result + ( names != null ? names.hashCode() : 0 );
        result = 31 * result + ( xref != null ? xref.hashCode() : 0 );
        result = 31 * result + ( xmlAvailability != null ? xmlAvailability.hashCode() : 0 );
        result = 31 * result + ( experimentDescriptions != null ? experimentDescriptions.hashCode() : 0 );
        result = 31 * result + ( xmlParticipants != null ? xmlParticipants.hashCode() : 0 );
        result = 31 * result + ( inferredInteractions != null ? inferredInteractions.hashCode() : 0 );
        result = 31 * result + ( interactionTypes != null ? interactionTypes.hashCode() : 0 );
        result = 31 * result + ( modelled != null ? modelled.hashCode() : 0 );
        result = 31 * result + ( intraMolecular != null ? intraMolecular.hashCode() : 0 );
        result = 31 * result + ( isNegative ? 0 : 1 );
        result = 31 * result + ( confidencesList != null ? confidencesList.hashCode() : 0 );
        result = 31 * result + ( parametersList != null ? parametersList.hashCode() : 0 );
        result = 31 * result + ( attributeList != null ? attributeList.hashCode() : 0 );
        return result;
    }

    public String getFullName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFullName(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Alias> getAliases() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Organism getOrganism() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setOrganism(Organism organism) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected class InteractionNames extends Names{

        public String getShortLabel() {
            return shortName;
        }

        public boolean hasShortLabel() {
            return shortName != null;
        }

        public void setShortLabel( String value ) {
            shortName = value;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Names" );
            sb.append( "{shortLabel='" ).append( shortName ).append( '\'' );
            sb.append( ", fullName='" ).append( getFullName() ).append( '\'' );
            sb.append( ", aliases=" ).append( getAliases()  );
            sb.append( '}' );
            return sb.toString();
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            Names names = ( Names ) o;

            if ( getAliases()  != null ? !getAliases() .equals(names.getAliases()) : names.getAliases() != null ) return false;
            if ( getFullName() != null ? !getFullName().equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( shortName != null ? !shortName.equals(names.getShortLabel()) : shortName != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( shortName != null ? shortName.hashCode() : 0 );
            result = 31 * result + ( getFullName() != null ? getFullName().hashCode() : 0 );
            result = 31 * result + ( getAliases() != null ? getAliases() .hashCode() : 0 );
            return result;
        }
    }

    protected class InteractionXref extends Xref{

        protected boolean isPrimaryAnIdentity = false;
        protected SecondaryRefList extendedSecondaryRefList = new SecondaryRefList();

        public InteractionXref() {
            super();
        }

        public InteractionXref(DbReference primaryRef) {
            super(primaryRef);
        }

        public InteractionXref(DbReference primaryRef, Collection<DbReference> secondaryRef) {
            super( primaryRef );

            if (secondaryRef != null && !secondaryRef.isEmpty()){
                secondaryRef.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractionIdentifierList)identifiers).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractionXrefList)xrefs).addOnly(value);
                        processAddedXrefEvent(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((InteractionIdentifierList)identifiers).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractionIdentifierList)identifiers).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractionXrefList)xrefs).addOnly(value);
                        processAddedXrefEvent(value);
                    }
                }
            }
            else {
                ((InteractionXrefList)xrefs).removeOnly(getPrimaryRef());
                processRemovedXrefEvent(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractionIdentifierList)identifiers).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractionXrefList)xrefs).addOnly(value);
                        processAddedXrefEvent(value);
                    }
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
                if (XrefUtils.isXrefAnIdentifier(value)){
                    isPrimaryAnIdentity = true;
                }
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
                if (XrefUtils.isXrefAnIdentifier(added)){
                    ((InteractionIdentifierList)identifiers).addOnly(added);
                }
                else {
                    ((InteractionXrefList)xrefs).addOnly(added);
                    processAddedXrefEvent(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((InteractionIdentifierList)identifiers).removeOnly(removed);
                }
                else {
                    ((InteractionXrefList)xrefs).removeOnly(removed);
                    processRemovedXrefEvent(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((InteractionIdentifierList)identifiers).retainAllOnly(primary);
                clearPropertiesLinkedToXrefs();
                ((InteractionXrefList)xrefs).retainAllOnly(primary);
            }
        }
    }

    private class InteractionIdentifierList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public InteractionIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                InteractionXref reference = (InteractionXref) xref;

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
                    xref = new InteractionXref();
                    ((InteractionXref) xref).setPrimaryRefOnly((DbReference) added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new InteractionXref();
                    ((InteractionXref) xref).setPrimaryRefOnly(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                InteractionXref reference = (InteractionXref) xref;

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
                InteractionXref reference = (InteractionXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(xrefs);

                if (reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    private class InteractionXrefList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public InteractionXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                InteractionXref reference = (InteractionXref) xref;

                if (xref.getPrimaryRef() == null){
                    if (added instanceof DbReference){
                        reference.setPrimaryRefOnly((DbReference) added);
                        processAddedXrefEvent(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.setPrimaryRefOnly(fixedRef);
                        processAddedXrefEvent(fixedRef);
                    }
                }
                else {
                    if (added instanceof DbReference){
                        reference.extendedSecondaryRefList.addOnly((DbReference) added);
                        processAddedXrefEvent(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.addOnly(fixedRef);
                        processAddedXrefEvent(fixedRef);
                    }
                }
            }
            else {
                if (added instanceof DbReference){
                    xref = new InteractionXref();
                    ((InteractionXref) xref).setPrimaryRefOnly((DbReference) added);
                    processAddedXrefEvent(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new InteractionXref(fixedRef);
                    ((InteractionXref) xref).setPrimaryRefOnly(fixedRef);
                    processAddedXrefEvent(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (xref != null){
                InteractionXref reference = (InteractionXref) xref;

                if (reference.getPrimaryRef() == removed){
                    reference.setPrimaryRefOnly(null);
                    processRemovedXrefEvent(removed);
                }
                else {
                    if (removed instanceof DbReference){
                        reference.extendedSecondaryRefList.removeOnly((DbReference) removed);
                        processRemovedXrefEvent(removed);
                    }
                    else {
                        DbReference fixedRef = new DbReference(removed.getDatabase().getShortName(), removed.getDatabase().getMIIdentifier(), removed.getId(),
                                removed.getQualifier() != null ? removed.getQualifier().getShortName() : null, removed.getQualifier() != null ? removed.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.removeOnly(fixedRef);
                        processRemovedXrefEvent(fixedRef);
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToXrefs();
            if (xref != null){
                InteractionXref reference = (InteractionXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(identifiers);

                if (!reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    protected class InteractionAvailability extends Availability{

        private int id;

        ///////////////////////////
        // Constructors

        public InteractionAvailability() {
        }

        public InteractionAvailability(int id, String value) {
            setId( id );
            availability = value;
        }

        public InteractionAvailability(String value) {
            availability = value;
        }

        ///////////////////////////
        // Getters and Setters

        /**
         * Gets the value of the value property.
         *
         * @return possible object is {@link String }
         */
        public String getValue() {
            return availability;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value allowed object is {@link String }
         */
        public void setValue( String value ) {
            availability = value;
        }

        /**
         * Check if the optional value is defined.
         *
         * @return true if defined, false otherwise.
         */
        public boolean hasValue() {
            return !( availability == null || availability.trim().length() == 0 );
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
            sb.append( "{value='" ).append( availability ).append( '\'' );
            sb.append( ", id=" ).append( id );
            sb.append( '}' );
            return sb.toString();
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            final Availability that = ( Availability ) o;

            if ( id != that.getId() ) return false;
            if ( availability != null ? !availability.equals( that.getValue() ) : that.getValue() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( availability != null ? availability.hashCode() : 0 );
            result = 29 * result + id;
            return result;
        }
    }

    private class InteractionExperimentsList extends AbstractListHavingPoperties<ExperimentDescription> {
        public InteractionExperimentsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(ExperimentDescription added) {

            if (experiment == null){
                experiment = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(ExperimentDescription removed) {

            if (isEmpty()){
                experiment = null;
            }
            else if (experiment != null && removed.equals(experiment)){
                experiment = iterator().next();
            }
        }

        @Override
        protected void clearProperties() {

            experiment = null;
        }
    }

    private class InteractionParticipantsList extends AbstractListHavingPoperties<ParticipantEvidence> {
        public InteractionParticipantsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.ParticipantEvidence added) {
            if (added instanceof Participant){
                added.setInteraction(getInstance());
                ((InteractionXmlParticipantsList)xmlParticipants).addOnly((Participant) added);
            }
            else {
                Participant f = new Participant();
                ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(added, f);
                f.setInteraction(getInstance());
                ((InteractionXmlParticipantsList)xmlParticipants).addOnly(f);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.ParticipantEvidence removed) {

            if (removed instanceof Feature){
                ((InteractionXmlParticipantsList)xmlParticipants).removeOnly(removed);
            }
            else {
                Participant f = new Participant();
                ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(removed, f);
                ((InteractionXmlParticipantsList)xmlParticipants).removeOnly(f);
            }
            removed.setInteraction(null);
        }

        @Override
        protected void clearProperties() {
            for (Participant f : xmlParticipants){
                f.setInteraction(null);
            }
            // clear all annotations
            ((InteractionXmlParticipantsList)xmlParticipants).clearOnly();
        }
    }

    private class InteractionXmlParticipantsList extends AbstractListHavingPoperties<Participant> {
        public InteractionXmlParticipantsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Participant added) {

            added.setInteraction(getInstance());
            ((InteractionParticipantsList) participants).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Participant removed) {
            ((InteractionParticipantsList) participants).removeOnly(removed);
            removed.setInteraction(null);
        }

        @Override
        protected void clearProperties() {
            for (ParticipantEvidence f : participants){
                f.setInteraction(null);
            }
            ((InteractionParticipantsList) participants).clearOnly();
        }
    }

    private class InferredInteractionsList extends AbstractListHavingPoperties<InferredInteraction> {
        public InferredInteractionsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(InferredInteraction added) {

            Collection<InferredInteractionParticipant> bindingFeatures = added.getParticipant();
            Iterator<InferredInteractionParticipant> iterator = bindingFeatures.iterator();

            int index = 0;
            while (iterator.hasNext()){
                InferredInteractionParticipant p = iterator.next();
                if (p.hasFeature()){
                    Feature f = p.getFeature();
                    int i = 0;
                    for (InferredInteractionParticipant p2 : bindingFeatures){
                        if (i != index){
                            if (p2.hasFeature()){
                                f.getBindingFeatures().add(p2.getFeature());
                            }
                        }
                        i++;
                    }
                }
                index++;
            }
        }

        @Override
        protected void processRemovedObjectEvent(InferredInteraction removed) {

            Collection<InferredInteractionParticipant> bindingFeatures = removed.getParticipant();
            Iterator<InferredInteractionParticipant> iterator = bindingFeatures.iterator();

            int index = 0;
            while (iterator.hasNext()){
                InferredInteractionParticipant p = iterator.next();
                if (p.hasFeature()){
                    Feature f = p.getFeature();
                    int i = 0;
                    for (InferredInteractionParticipant p2 : bindingFeatures){
                        if (i != index){
                            if (p2.hasFeature()){
                                f.getBindingFeatures().remove(p2.getFeature());
                            }
                        }
                        i++;
                    }
                }
                index++;
            }
        }

        @Override
        protected void clearProperties() {

            for (ParticipantEvidence p : participants){
                for (psidev.psi.mi.jami.model.Feature f : p.getFeatures()){
                    f.getBindingFeatures().clear();
                }
            }
        }
    }

    private class InteractionTypesList extends AbstractListHavingPoperties<InteractionType> {
        public InteractionTypesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(InteractionType added) {

            if (type == null){
                type = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(InteractionType removed) {

            if (isEmpty()){
                type = null;
            }
            else if (type != null && removed.equals(type)){
                type = iterator().next();
            }
        }

        @Override
        protected void clearProperties() {

            type = null;
        }
    }

    private class InteractionConfidencesList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Confidence> {
        public InteractionConfidencesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Confidence added) {
            if (added instanceof Confidence){
                ((InteractionXmlConfidencesList)confidencesList).addOnly((Confidence) added);
            }
            else {
                Confidence conf = cloneConfidence(added);
                ((InteractionXmlConfidencesList)confidencesList).addOnly(conf);
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
                ((InteractionXmlConfidencesList)confidencesList).removeOnly(removed);
            }
            else {
                Confidence conf = cloneConfidence(removed);
                ((InteractionXmlConfidencesList)confidencesList).removeOnly(conf);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionXmlConfidencesList)confidencesList).clearOnly();
        }
    }

    private class InteractionXmlConfidencesList extends AbstractListHavingPoperties<Confidence> {
        public InteractionXmlConfidencesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Confidence added) {

            ((InteractionConfidencesList) confidences).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Confidence removed) {

            ((InteractionConfidencesList) confidences).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((InteractionConfidencesList) confidences).clearOnly();
        }
    }

    private class InteractionParametersList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Parameter> {
        public InteractionParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Parameter added) {
            if (added instanceof Parameter){
                ((InteractionXmlParametersList)parametersList).addOnly((Parameter) added);
            }
            else {
                Parameter param = cloneParameter(added);

                ((InteractionXmlParametersList)parametersList).addOnly(param);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Parameter removed) {
            if (removed instanceof ExperimentalPreparation){
                ((InteractionXmlParametersList)parametersList).removeOnly(removed);
            }
            else {
                Parameter param = cloneParameter(removed);
                ((InteractionXmlParametersList)parametersList).removeOnly(param);
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
            ((InteractionXmlParametersList)parametersList).clearOnly();
        }
    }

    private class InteractionXmlParametersList extends AbstractListHavingPoperties<Parameter> {
        public InteractionXmlParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Parameter added) {

            ((InteractionParametersList) parameters).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Parameter removed) {

            ((InteractionParametersList) parameters).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((InteractionParametersList) parameters).clearOnly();
        }
    }

    private class InteractionAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public InteractionAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((InteractionXmlAnnotationList)attributeList).addOnly((Attribute) added);
            }
            else {
                Attribute att = new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue());
                ((InteractionXmlAnnotationList)attributeList).addOnly(att);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((InteractionXmlAnnotationList)attributeList).removeOnly(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((InteractionXmlAnnotationList)attributeList).removeOnly(att);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionXmlAnnotationList)attributeList).clearOnly();
        }
    }

    private class InteractionXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public InteractionXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {

            if (added.getNameAc() != null
                    && (added.getNameAc().equals(Checksum.RIGID_MI)
                    || added.getNameAc().equals("MI:1212"))){
                Checksum check = new DefaultChecksum(new DefaultCvTerm(added.getName(), added.getNameAc()), added.getValue());
                ((InteractionChecksumList) checksums).addOnly(check);

                processAddedChecksumEvent(check);
            }
            else if (added.getNameAc() == null
                    && (added.getName().equals(Checksum.RIGID)
                    || added.getName().equals("checksum"))){
                Checksum check = new DefaultChecksum(new DefaultCvTerm(added.getName(), added.getNameAc()), added.getValue());

                ((InteractionChecksumList) checksums).addOnly(check);
                processAddedChecksumEvent(check);
            }
            else {
                // we added a annotation, needs to add it in annotations
                ((InteractionAnnotationList)annotations).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {
            if (removed.getNameAc() != null
                    && (removed.getNameAc().equals(Checksum.RIGID_MI)
                    || removed.getNameAc().equals("MI:1212"))){
                Checksum check = new DefaultChecksum(new DefaultCvTerm(removed.getName(), removed.getNameAc()), removed.getValue());
                ((InteractionChecksumList) checksums).removeOnly(check);
                processRemovedChecksumEvent(check);
            }
            else if (removed.getNameAc() == null
                    && (removed.getName().equals(Checksum.RIGID)
                    || removed.getName().equals("checksum"))){
                Checksum check = new DefaultChecksum(new DefaultCvTerm(removed.getName(), removed.getNameAc()), removed.getValue());
                ((InteractionChecksumList) checksums).removeOnly(new DefaultChecksum(new DefaultCvTerm(removed.getName()), removed.getValue()));
                processRemovedChecksumEvent(check);
            }
            else{
                // we removed a annotation, needs to remove it in annotations
                ((InteractionAnnotationList)annotations).removeOnly(removed);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionAnnotationList)annotations).clearOnly();
            clearPropertiesLinkedToChecksums();
        }
    }

    private class InteractionChecksumList extends AbstractListHavingPoperties<Checksum> {
        public InteractionChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Checksum added) {
            processAddedChecksumEvent(added);
            ((InteractionXmlAnnotationList)attributeList).addOnly(new Attribute(added.getMethod().getMIIdentifier(), added.getMethod().getShortName(), added.getValue()));
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Checksum removed) {
            ((InteractionXmlAnnotationList)attributeList).removeOnly(new Attribute(removed.getMethod().getMIIdentifier(), removed.getMethod().getShortName(), removed.getValue()));
            processRemovedChecksumEvent(removed);

        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionXmlAnnotationList)attributeList).clearOnly();
            clearPropertiesLinkedToChecksums();
        }
    }
}