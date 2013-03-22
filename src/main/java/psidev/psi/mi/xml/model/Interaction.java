/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml.model;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.CvTermCloner;
import psidev.psi.mi.jami.utils.clone.ExperimentCloner;
import psidev.psi.mi.jami.utils.clone.ParticipantCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.*;


public class Interaction extends DefaultInteractionEvidence implements Complex, HasId, NamesContainer, XrefContainer, AttributeContainer, FileSourceContext {

    private int id;

    private Names names;

    private Xref xref;

    private Availability xmlAvailability;

    private Collection<ExperimentDescription> experiments;

    private Collection<ExperimentRef> experimentRefs;

    private Collection<Participant> participants;

    private Collection<InferredInteraction> inferredInteractions;

    private Collection<InteractionType> interactionTypes;

    private Boolean modelled;

    private Boolean intraMolecular;

    private Collection<Confidence> confidencesList;

    private Collection<Parameter> parametersList;

    private Collection<Attribute> attributeList;

    private Annotation physicalProperties;

    private Collection<psidev.psi.mi.jami.model.Xref> publications;

    private Collection<Alias> aliases;

    private Organism organism;

    private Collection<ModelledConfidence> modelledConfidences;
    private Collection<ModelledParameter> modelledParameters;

    private PsiXmlFileLocator locator;

    ///////////////////////////
    // Constructors

    public Interaction() {
        super();
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new InteractionIdentifierList());
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new InteractionXrefList());
    }

    @Override
    protected void initialiseParticipantEvidences() {
        initialiseParticipantEvidencesWith(Collections.EMPTY_LIST);
    }

    @Override
    protected void initialiseParticipantEvidencesWith(Collection<ParticipantEvidence> participants) {
        if (participants == null){
            initialiseParticipantEvidencesWith(Collections.EMPTY_LIST);
        }
        else {
            if (this.participants == null){
               this.participants = new ArrayList<Participant>();
            }
            else {
                this.participants.clear();
            }
            for (ParticipantEvidence p : participants){
                addParticipantEvidence(p);
            }
        }
    }

    @Override
    protected void initialiseExperimentalConfidences() {
        initialiseExperimentalConfidencesWith(new InteractionConfidencesList());
    }

    @Override
    protected void initialiseExperimentalParameters() {
        initialiseExperimentalParametersWith(new InteractionParametersList());
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new InteractionAnnotationList());
    }

    @Override
    protected void initialiseChecksum() {
        initialiseChecksumWith(new InteractionChecksumList());
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
     * Check if the optional imexId is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasImexId() {
        return getImexId() != null;
    }

    /**
     * Sets the value of the imexId property.
     *
     * @param value allowed object is {@link String }
     */
    public void setImexId( String value ) {
        if (value == null && getImexId() != null){
            getXrefs().remove(new DefaultXref(CvTermFactory.createImexDatabase(), getImexId(), CvTermFactory.createMICvTerm(psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI)));
        }
        else if (value != null){
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
            super.setShortName(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            getAliases().clear();
            getAliases().addAll(value.getAliases());
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
                getIdentifiers().clear();
                getXrefs().clear();
            }
            this.xref = new InteractionXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            getIdentifiers().clear();
            getXrefs().clear();
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
            super.setAvailability(null);
            xmlAvailability = null;
        }
        else if (this.xmlAvailability != null){
            super.setAvailability(value.getValue());
            this.xmlAvailability.setId(value.getId());
        }
        else {
            this.xmlAvailability = new InteractionAvailability(value.getId(), value.getValue());
        }
    }

    public boolean hasExperiments() {
        return experiments != null && !experiments.isEmpty();
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return possible object is {@link ExperimentDescription }
     */
    public Collection<ExperimentDescription> getExperiments() {
        if (experiments == null){
            experiments = new InteractionExperimentsList();
        }
        return experiments;
    }

    public Collection<? extends Component> getComponents() {
        return getParticipants();
    }

    public String getPhysicalProperties() {
        return physicalProperties != null ? physicalProperties.getValue() : null;
    }

    public void setPhysicalProperties(String properties) {
        Collection<Annotation> complexAnnotationList = getAnnotations();

        // add new physical properties if not null
        if (properties != null){

            CvTerm complexPhysicalProperties = CvTermFactory.createComplexPhysicalProperties();
            // first remove old physical property if not null
            if (this.physicalProperties != null){
                complexAnnotationList.remove(this.physicalProperties);
            }
            this.physicalProperties = new DefaultAnnotation(complexPhysicalProperties, properties);
            complexAnnotationList.add(this.physicalProperties);
        }
        // remove all physical properties if the collection is not empty
        else if (!complexAnnotationList.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(complexAnnotationList, COMPLEX_MI, COMPLEX);
            physicalProperties = null;
        }
    }

    public Collection<psidev.psi.mi.jami.model.Xref> getPublications() {
        if (publications == null){
            this.publications = new ArrayList<psidev.psi.mi.jami.model.Xref>();
        }
        return this.publications;
    }

    protected void processAddedAnnotationEvent(Annotation added) {
        if (physicalProperties == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES)){
            physicalProperties = added;
        }
    }

    protected void processRemovedAnnotationEvent(Annotation removed) {
        if (physicalProperties != null && physicalProperties.equals(removed)){
            physicalProperties = AnnotationUtils.collectFirstAnnotationWithTopic(getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        }
    }

    protected void clearPropertiesLinkedToAnnotations() {
        physicalProperties = null;
    }

    public boolean addComponent(Component part) {
        if (part == null){
            return false;
        }
        if (participants == null){
            participants = new ArrayList<Participant>();
        }
        if (part instanceof Participant){
            if (participants.add((Participant)part)){
                part.setComplex(this);
                return true;
            }
            return false;
        }
        else {
            Participant p = new Participant();
            ParticipantCloner.copyAndOverrideComponentProperties(part, p);
            if (participants.add(p)){
                p.setComplex(this);
                return true;
            }
            return false;
        }
    }

    public boolean removeComponent(Component part) {
        if (part == null){
            return false;
        }
        if (participants == null){
            participants = new ArrayList<Participant>();
        }
        if (part instanceof Participant){
            if (participants.remove(part)){
                part.setComplex(null);
                return true;
            }
            return false;
        }
        else {
            Participant p = new Participant();
            ParticipantCloner.copyAndOverrideComponentProperties(part, p);
            if (participants.remove(p)){
                p.setComplex(null);
                return true;
            }
            return false;
        }
    }

    public boolean addAllComponents(Collection<? extends Component> part) {
        if (part == null){
            return false;
        }

        boolean added = false;
        for (Component p : part){
            if (addComponent(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllComponents(Collection<? extends Component> part) {
        if (part == null){
            return false;
        }

        boolean removed = false;
        for (Component p : part){
            if (removeComponent(p)){
                removed = true;
            }
        }
        return removed;
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
    public Collection<Participant> getParticipants() {
        if (participants == null){
            participants = new ArrayList<Participant>();
        }
        return participants;
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
        if (inferredInteractions == null){
            inferredInteractions = new InferredInteractionsList();
        }
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
        if (interactionTypes == null){
            interactionTypes = new InteractionTypesList();
        }
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
        if (confidencesList == null){
            confidencesList = new InteractionXmlConfidencesList();
        }
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
        if (parametersList == null){
            parametersList = new InteractionXmlParametersList();
        }
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
        if (attributeList == null){
            attributeList = new InteractionXmlAnnotationList();
        }
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

    protected Interaction getInstance(){
        return this;
    }

    protected void processAddedExperimentEvent(ExperimentDescription added) {

        if (getExperiment() == null){
            super.setExperiment(added);
        }
    }

    protected void processRemovedExperimentEvent(ExperimentDescription removed) {

        if (getExperiments().isEmpty()){
            super.setExperiment(null);
        }
        else if (getExperiment() != null && removed.equals(getExperiment())){
            super.setExperiment(experiments.iterator().next());
        }
    }

    protected void clearPropertiesLinkedToExperiments() {

        super.setExperiment(null);
    }

    ///////////////////////////
    // Object override

    protected Collection<Alias> getComplexAliases(){
        return getAliases();
    }

    @Override
    public void setShortName(String name) {
        if (name == null){
           this.names = null;
        }
        else if (names != null){
            super.setShortName(name);
        }
        else  {
            names = new InteractionNames();
            super.setShortName(name);
        }
    }

    protected void setShortNameOnly(String name) {
        super.setShortName(name);
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
            super.setAvailability(availability);
        }
    }

    protected void setAvailabilityOnly(String availability) {
        super.setAvailability(availability);
    }

    @Override
    public void setExperiment(Experiment experiment) {
        if (experiment != null){
            if (getExperiment() != null){
                getExperiments().remove(getExperiment());
            }
            if(experiment instanceof ExperimentDescription){
                super.setExperiment(experiment);
                ((InteractionExperimentsList) getExperiments()).addOnly(getExperiment());
            }
            else {
                super.setExperiment(new ExperimentDescription());

                ExperimentCloner.copyAndOverrideExperimentProperties(experiment, getExperiment());
                ((InteractionExperimentsList) getExperiments()).addOnly(getExperiment());
            }
        }
        else if (!getExperiments().isEmpty()) {
            super.setExperiment(null);
            ((InteractionExperimentsList) getExperiments()).clearOnly();
        }
    }

    @Override
    public void setType(CvTerm term) {
        if (term != null){
            if (getType() != null){
                getInteractionTypes().remove(getType());
            }
            if(term instanceof InteractionType){
                super.setType(term);
                ((InteractionTypesList)getInteractionTypes()).addOnly(getType());
            }
            else {
                super.setType(new InteractionType());

                CvTermCloner.copyAndOverrideCvTermProperties(term, getType());
                ((InteractionTypesList)getInteractionTypes()).addOnly(getType());
            }
        }
        else if (!getInteractionTypes().isEmpty()) {
            super.setType(null);
            ((InteractionTypesList)getInteractionTypes()).clearOnly();
        }
    }

    protected void setTypeOnly(CvTerm term) {
        super.setType(term);
    }

    @Override
    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        if (experiment != null){
            if (getExperiment() != null){
                getExperiments().remove(getExperiment());
            }
            if(experiment instanceof ExperimentDescription){
                super.setExperiment(experiment);
                ((InteractionExperimentsList) getExperiments()).addOnly(getExperiment());
                getExperiment().addInteractionEvidence(this);
            }
            else {
                super.setExperiment(new ExperimentDescription());

                ExperimentCloner.copyAndOverrideExperimentProperties(experiment, getExperiment());
                ((InteractionExperimentsList) getExperiments()).addOnly(getExperiment());
                getExperiment().addInteractionEvidence(this);
            }
        }
        else if (!getExperiments().isEmpty()) {
            super.setExperiment(null);
            ((InteractionExperimentsList) getExperiments()).clearOnly();
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Interaction" );
        sb.append( "{id=" ).append( id );
        sb.append( ", imexId='" ).append( getImexId() ).append( '\'' );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", xmlAvailability=" ).append(xmlAvailability);
        sb.append( ", experiments=" ).append(experiments);
        sb.append( ", participants=" ).append(participants);
        sb.append( ", inferredInteractions=" ).append( inferredInteractions );
        sb.append( ", interactionTypes=" ).append( interactionTypes );
        sb.append( ", modelled=" ).append( modelled );
        sb.append( ", intraMolecular=" ).append( intraMolecular );
        sb.append( ", negative=" ).append( isNegative() );
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
        if ( experiments != null ? !experiments.equals( that.experiments) : that.experiments != null ) return false;
        if ( getImexId() != null ? !getImexId().equals(that.getImexId()) : that.getImexId() != null ) return false;
        if ( inferredInteractions != null ? !inferredInteractions.equals( that.inferredInteractions ) : that.inferredInteractions != null )
            return false;
        if ( interactionTypes != null ? !interactionTypes.equals( that.interactionTypes ) : that.interactionTypes != null )
            return false;
        if ( intraMolecular != null ? !intraMolecular.equals( that.intraMolecular ) : that.intraMolecular != null )
            return false;
        if ( modelled != null ? !modelled.equals( that.modelled ) : that.modelled != null ) return false;
        if ( names != null ? !names.equals( that.names ) : that.names != null ) return false;
        if ( isNegative() != that.isNegative() ) return false;
        if ( parametersList != null ? !parametersList.equals( that.parametersList) : that.parametersList != null ) return false;
        if ( participants != null ? !participants.equals( that.participants) : that.participants != null )
            return false;
        if ( xref != null ? !xref.equals( that.xref ) : that.xref != null ) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = id;
        result = 31 * result + ( getImexId() != null ? getImexId().hashCode() : 0 );
        result = 31 * result + ( names != null ? names.hashCode() : 0 );
        result = 31 * result + ( xref != null ? xref.hashCode() : 0 );
        result = 31 * result + ( xmlAvailability != null ? xmlAvailability.hashCode() : 0 );
        result = 31 * result + ( experiments != null ? experiments.hashCode() : 0 );
        result = 31 * result + ( participants != null ? participants.hashCode() : 0 );
        result = 31 * result + ( inferredInteractions != null ? inferredInteractions.hashCode() : 0 );
        result = 31 * result + ( interactionTypes != null ? interactionTypes.hashCode() : 0 );
        result = 31 * result + ( modelled != null ? modelled.hashCode() : 0 );
        result = 31 * result + ( intraMolecular != null ? intraMolecular.hashCode() : 0 );
        result = 31 * result + ( isNegative() ? 0 : 1 );
        result = 31 * result + ( confidencesList != null ? confidencesList.hashCode() : 0 );
        result = 31 * result + ( parametersList != null ? parametersList.hashCode() : 0 );
        result = 31 * result + ( attributeList != null ? attributeList.hashCode() : 0 );
        return result;
    }

    public String getFullName() {
        return this.names != null ? this.names.getFullName() : null;
    }

    public void setFullName(String name) {
        if (this.names != null){
            this.names.setFullName(name);
        }
        else if (name != null){
            this.names = new InteractionNames();
            this.names.setShortLabel(name);
            this.names.setFullName(name);
        }
    }

    public Collection<Alias> getAliases() {
        if (aliases == null){
            aliases = new ArrayList<Alias>();
        }
        return aliases;
    }

    public Organism getOrganism() {
        return this.organism;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    @Override
    public ExperimentDescription getExperiment() {
        return (ExperimentDescription) super.getExperiment();
    }

    @Override
    public Collection<? extends ParticipantEvidence> getParticipantEvidences() {
        return getParticipants();
    }

    @Override
    public boolean addParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }
        if (participants == null){
            participants = new ArrayList<Participant>();
        }
        if (part instanceof Participant){
            if (participants.add((Participant)part)){
                part.setInteractionEvidence(this);
                return true;
            }
            return false;
        }
        else {
            Participant p = new Participant();
            ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(part, p);
            if (participants.add(p)){
                p.setInteractionEvidence(this);
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean removeParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }
        if (participants == null){
            participants = new ArrayList<Participant>();
        }
        if (part instanceof Participant){
            if (participants.remove(part)){
                part.setInteractionEvidence(null);
                return true;
            }
            return false;
        }
        else {
            Participant p = new Participant();
            ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(part, p);
            if (participants.remove(p)){
                p.setInteractionEvidence(null);
                return true;
            }
            return false;
        }
    }

    @Override
    public InteractionType getType() {
        return (InteractionType) super.getType();
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        return getExperiment() != null ? getExperiment().getInteractionEvidences() : Collections.EMPTY_LIST;
    }

    public Collection<ModelledConfidence> getConfidences() {
        if (modelledConfidences == null){
            modelledConfidences = new ComplexConfidencesList();
        }
        return modelledConfidences;
    }

    public Collection<ModelledParameter> getParameters() {
        if (modelledParameters == null){
            modelledParameters = new ComplexParametersList();
        }
        return modelledParameters;
    }

    protected class InteractionNames extends Names{
        protected AliasList extendedAliases = new AliasList();

        public String getShortLabel() {
            return getShortName();
        }

        public boolean hasShortLabel() {
            return getShortName() != null;
        }

        public void setShortLabel( String value ) {
           setShortNameOnly(value);
        }

        public Collection<psidev.psi.mi.xml.model.Alias> getAliases() {
            return this.extendedAliases;
        }

        public boolean hasAliases() {
            return ( extendedAliases != null ) && ( !extendedAliases.isEmpty() );
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Names" );
            sb.append( "{shortLabel='" ).append( getShortName() ).append( '\'' );
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

            if ( extendedAliases  != null ? !extendedAliases .equals(names.getAliases()) : names.getAliases() != null ) return false;
            if ( getFullName() != null ? !getFullName().equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( getShortName() != null ? !getShortName().equals(names.getShortLabel()) : getShortName() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( getShortName() != null ? getShortName().hashCode() : 0 );
            result = 31 * result + ( getFullName() != null ? getFullName().hashCode() : 0 );
            result = 31 * result + ( extendedAliases != null ? extendedAliases .hashCode() : 0 );
            return result;
        }

        protected class AliasList extends AbstractListHavingPoperties<psidev.psi.mi.xml.model.Alias> {

            @Override
            protected void processAddedObjectEvent(psidev.psi.mi.xml.model.Alias added) {
                ((InteractionAliasList) getComplexAliases()).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(psidev.psi.mi.xml.model.Alias removed) {
                ((InteractionAliasList)getComplexAliases()).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((InteractionAliasList)getComplexAliases()).clearOnly();
            }
        }
    }

    private class InteractionAliasList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Alias> {
        public InteractionAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Alias added) {

            if (names != null){
                InteractionNames name = (InteractionNames) names;

                if (added instanceof psidev.psi.mi.xml.model.Alias){
                    ((InteractionNames.AliasList) name.getAliases()).addOnly((psidev.psi.mi.xml.model.Alias) added);
                }
                else {
                    psidev.psi.mi.xml.model.Alias fixedAlias = new psidev.psi.mi.xml.model.Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    ((InteractionNames.AliasList) name.getAliases()).addOnly(fixedAlias);
                }
            }
            else {
                if (added instanceof psidev.psi.mi.xml.model.Alias){
                    names = new InteractionNames();
                    ((InteractionNames.AliasList) names.getAliases()).addOnly((psidev.psi.mi.xml.model.Alias) added);
                }
                else {
                    psidev.psi.mi.xml.model.Alias fixedAlias = new psidev.psi.mi.xml.model.Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    names = new InteractionNames();
                    ((InteractionNames.AliasList) names.getAliases()).addOnly((psidev.psi.mi.xml.model.Alias) fixedAlias);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Alias removed) {

            if (names != null){
                InteractionNames name = (InteractionNames) names;

                if (removed instanceof psidev.psi.mi.xml.model.Alias){
                    name.extendedAliases.removeOnly((psidev.psi.mi.xml.model.Alias) removed);

                }
                else {
                    psidev.psi.mi.xml.model.Alias fixedAlias = new psidev.psi.mi.xml.model.Alias(removed.getName(), removed.getType() != null ? removed.getType().getShortName() : null, removed.getType() != null ? removed.getType().getMIIdentifier() : null);

                    name.extendedAliases.removeOnly(fixedAlias);
                }
            }
        }

        @Override
        protected void clearProperties() {

            if (names != null){
                InteractionNames name = (InteractionNames) names;
                name.extendedAliases.clearOnly();
            }
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
                extendedSecondaryRefList.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractionIdentifierList)getIdentifiers()).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractionXrefList)getXrefs()).addOnly(value);
                        processAddedXrefEvent(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((InteractionIdentifierList)getIdentifiers()).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractionIdentifierList)getIdentifiers()).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractionXrefList)getXrefs()).addOnly(value);
                        processAddedXrefEvent(value);
                    }
                }
            }
            else {
                ((InteractionXrefList)getXrefs()).removeOnly(getPrimaryRef());
                processRemovedXrefEvent(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractionIdentifierList)getIdentifiers()).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractionXrefList)getXrefs()).addOnly(value);
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
                    ((InteractionIdentifierList)getIdentifiers()).addOnly(added);
                }
                else {
                    ((InteractionXrefList)getXrefs()).addOnly(added);
                    processAddedXrefEvent(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((InteractionIdentifierList)getIdentifiers()).removeOnly(removed);
                }
                else {
                    ((InteractionXrefList)getXrefs()).removeOnly(removed);
                    processRemovedXrefEvent(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((InteractionIdentifierList)getIdentifiers()).retainAllOnly(primary);
                clearPropertiesLinkedToXrefs();
                ((InteractionXrefList)getXrefs()).retainAllOnly(primary);
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
                reference.extendedSecondaryRefList.retainAllOnly(getXrefs());

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
                reference.extendedSecondaryRefList.retainAllOnly(getIdentifiers());

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
            setAvailabilityOnly(value);
        }

        public InteractionAvailability(String value) {
            setAvailabilityOnly(value);
        }

        ///////////////////////////
        // Getters and Setters

        /**
         * Gets the value of the value property.
         *
         * @return possible object is {@link String }
         */
        public String getValue() {
            return getAvailability();
        }

        /**
         * Sets the value of the value property.
         *
         * @param value allowed object is {@link String }
         */
        public void setValue( String value ) {
            setAvailabilityOnly(value);
        }

        /**
         * Check if the optional value is defined.
         *
         * @return true if defined, false otherwise.
         */
        public boolean hasValue() {
            return !( getAvailability() == null || getAvailability().trim().length() == 0 );
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
            sb.append( "{value='" ).append( getAvailability() ).append( '\'' );
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
            if ( getAvailability() != null ? !getAvailability().equals(that.getValue()) : that.getValue() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( getAvailability() != null ? getAvailability().hashCode() : 0 );
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

            processAddedExperimentEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(ExperimentDescription removed) {

            processRemovedExperimentEvent(removed);
        }

        @Override
        protected void clearProperties() {

            clearPropertiesLinkedToExperiments();
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
                                f.addBindingSiteEvidence(p2.getFeature());
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
                                f.removeBindingSiteEvidence(p2.getFeature());
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

            for (Participant p : getParticipants()){
                for (Feature f : p.getFeatures()){
                    f.getBindingSiteEvidences().clear();
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

            if (getType() == null){
                setTypeOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(InteractionType removed) {

            if (isEmpty()){
                setTypeOnly(null);
            }
            else if (getType() != null && removed.equals(getType())){
                setTypeOnly(iterator().next());
            }
        }

        @Override
        protected void clearProperties() {

            setTypeOnly(null);
        }
    }

    private class ComplexConfidencesList extends AbstractListHavingPoperties<ModelledConfidence> {
        public ComplexConfidencesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.ModelledConfidence added) {
            if (added instanceof Confidence){
                ((InteractionXmlConfidencesList)getConfidencesList()).addOnly((Confidence) added);
            }
            else {
                Confidence conf = cloneConfidence(added);
                ((InteractionXmlConfidencesList)getConfidencesList()).addOnly(conf);
            }
        }

        private Confidence cloneConfidence(psidev.psi.mi.jami.model.ModelledConfidence added) {
            Confidence conf = new Confidence();
            Unit unit = new Unit();
            unit.setShortName(added.getType().getShortName());
            unit.setMIIdentifier(added.getType().getMIIdentifier());
            conf.setUnit(unit);
            conf.setValue(added.getValue());
            return conf;
        }

        @Override
        protected void processRemovedObjectEvent(ModelledConfidence removed) {
            if (removed instanceof Confidence){
                ((InteractionXmlConfidencesList)getConfidencesList()).removeOnly(removed);
            }
            else {
                Confidence conf = cloneConfidence(removed);
                ((InteractionXmlConfidencesList)getConfidencesList()).removeOnly(conf);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionXmlConfidencesList)getConfidencesList()).retainAllOnly(getExperimentalConfidences());
        }
    }

    private class InteractionConfidencesList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Confidence> {
        public InteractionConfidencesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Confidence added) {
            if (added instanceof Confidence){
                ((InteractionXmlConfidencesList)getConfidencesList()).addOnly((Confidence) added);
            }
            else {
                Confidence conf = cloneConfidence(added);
                ((InteractionXmlConfidencesList)getConfidencesList()).addOnly(conf);
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
                ((InteractionXmlConfidencesList)getConfidencesList()).removeOnly(removed);
            }
            else {
                Confidence conf = cloneConfidence(removed);
                ((InteractionXmlConfidencesList)getConfidencesList()).removeOnly(conf);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionXmlConfidencesList)getConfidencesList()).clearOnly();
        }
    }

    private class InteractionXmlConfidencesList extends AbstractListHavingPoperties<Confidence> {
        public InteractionXmlConfidencesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Confidence added) {
            ((InteractionConfidencesList) getExperimentalConfidences()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Confidence removed) {

            ((InteractionConfidencesList) getExperimentalConfidences()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((InteractionConfidencesList) getExperimentalConfidences()).clearOnly();
            ((ComplexConfidencesList) getConfidences()).clearOnly();
        }
    }

    private class ComplexParametersList extends AbstractListHavingPoperties<ModelledParameter> {
        public ComplexParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.ModelledParameter added) {
            if (added instanceof Parameter){
                ((InteractionXmlParametersList)getParametersList()).addOnly((Parameter) added);
            }
            else {
                Parameter param = cloneParameter(added);

                ((InteractionXmlParametersList)getParametersList()).addOnly(param);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.ModelledParameter removed) {
            if (removed instanceof ExperimentalPreparation){
                ((InteractionXmlParametersList)getParametersList()).removeOnly(removed);
            }
            else {
                Parameter param = cloneParameter(removed);
                ((InteractionXmlParametersList)getParametersList()).removeOnly(param);
            }
        }

        private Parameter cloneParameter(psidev.psi.mi.jami.model.ModelledParameter removed) {
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
            ((InteractionXmlParametersList)getParametersList()).retainAllOnly(getExperimentalParameters());
        }
    }

    private class InteractionParametersList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Parameter> {
        public InteractionParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Parameter added) {
            if (added instanceof Parameter){
                ((InteractionXmlParametersList)getParametersList()).addOnly((Parameter) added);
            }
            else {
                Parameter param = cloneParameter(added);

                ((InteractionXmlParametersList)getParametersList()).addOnly(param);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Parameter removed) {
            if (removed instanceof ExperimentalPreparation){
                ((InteractionXmlParametersList)getParametersList()).removeOnly(removed);
            }
            else {
                Parameter param = cloneParameter(removed);
                ((InteractionXmlParametersList)getParametersList()).removeOnly(param);
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
            ((InteractionXmlParametersList)parametersList).retainAllOnly(getParameters());
        }
    }

    private class InteractionXmlParametersList extends AbstractListHavingPoperties<Parameter> {
        public InteractionXmlParametersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Parameter added) {

            ((InteractionParametersList) getExperimentalParameters()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Parameter removed) {

            ((InteractionParametersList) getExperimentalParameters()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            ((InteractionParametersList) getExperimentalParameters()).clearOnly();
            ((ComplexParametersList) getParameters()).clearOnly();
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
                processAddedAnnotationEvent(added);
            }
            else {
                Attribute att = new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue());
                ((InteractionXmlAnnotationList)attributeList).addOnly(att);
                processAddedAnnotationEvent(att);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((InteractionXmlAnnotationList)attributeList).removeOnly(removed);
                processRemovedAnnotationEvent(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((InteractionXmlAnnotationList)attributeList).removeOnly(att);
                processRemovedAnnotationEvent(att);
            }
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToAnnotations();
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
                ((InteractionChecksumList) getChecksums()).addOnly(check);

                processAddedChecksumEvent(check);
            }
            else if (added.getNameAc() == null
                    && (added.getName().equals(Checksum.RIGID)
                    || added.getName().equals("checksum"))){
                Checksum check = new DefaultChecksum(new DefaultCvTerm(added.getName(), added.getNameAc()), added.getValue());

                ((InteractionChecksumList) getChecksums()).addOnly(check);
                processAddedChecksumEvent(check);
            }
            else {
                // we added a annotation, needs to add it in annotations
                ((InteractionAnnotationList)getAnnotations()).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {
            if (removed.getNameAc() != null
                    && (removed.getNameAc().equals(Checksum.RIGID_MI)
                    || removed.getNameAc().equals("MI:1212"))){
                Checksum check = new DefaultChecksum(new DefaultCvTerm(removed.getName(), removed.getNameAc()), removed.getValue());
                ((InteractionChecksumList) getChecksums()).removeOnly(check);
                processRemovedChecksumEvent(check);
            }
            else if (removed.getNameAc() == null
                    && (removed.getName().equals(Checksum.RIGID)
                    || removed.getName().equals("checksum"))){
                Checksum check = new DefaultChecksum(new DefaultCvTerm(removed.getName(), removed.getNameAc()), removed.getValue());
                ((InteractionChecksumList) getChecksums()).removeOnly(new DefaultChecksum(new DefaultCvTerm(removed.getName()), removed.getValue()));
                processRemovedChecksumEvent(check);
            }
            else{
                // we removed a annotation, needs to remove it in annotations
                ((InteractionAnnotationList)getAnnotations()).removeOnly(removed);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionAnnotationList)getAnnotations()).clearOnly();
            clearPropertiesLinkedToChecksums();
            clearPropertiesLinkedToAnnotations();
        }
    }

    private class InteractionChecksumList extends AbstractListHavingPoperties<Checksum> {
        public InteractionChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Checksum added) {
            processAddedChecksumEvent(added);
            ((InteractionXmlAnnotationList)getAttributes()).addOnly(new Attribute(added.getMethod().getMIIdentifier(), added.getMethod().getShortName(), added.getValue()));
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Checksum removed) {
            ((InteractionXmlAnnotationList)getAttributes()).removeOnly(new Attribute(removed.getMethod().getMIIdentifier(), removed.getMethod().getShortName(), removed.getValue()));
            processRemovedChecksumEvent(removed);

        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractionXmlAnnotationList)getAttributes()).clearOnly();
            clearPropertiesLinkedToChecksums();
        }
    }
}