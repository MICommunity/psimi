/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.CvTermCloner;
import psidev.psi.mi.jami.utils.clone.FeatureCloner;
import psidev.psi.mi.jami.utils.clone.ParticipantCloner;
import psidev.psi.mi.jami.utils.clone.RangeCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * A feature, e.g. domain, on a sequence.
 * <p/>
 * <p>Java class for featureElementType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="featureElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="featureType" type="{net:sf:psidev:mi}cvType" minOccurs="0"/>
 *         &lt;element name="featureDetectionMethod" type="{net:sf:psidev:mi}cvType" minOccurs="0"/>
 *         &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType" minOccurs="0"/>
 *         &lt;element name="featureRangeList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="featureRange" type="{net:sf:psidev:mi}baseLocationType" maxOccurs="unbounded"/>
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

public class Feature extends DefaultFeatureEvidence implements ComponentFeature, HasId, NamesContainer, XrefContainer, AttributeContainer {

    private int id;

    private Names names;

    private Xref xref;

    private Collection<ExperimentDescription> experiments;

    private Collection<ExperimentRef> experimentRefs;

    private Collection<Range> xmlRanges;

    private Collection<Attribute> attributes;

    private Collection<Feature> bindingFeatures;

    ///////////////////////////
    // Constructors

    public Feature() {
        super();
    }

    public Feature( int id, Collection<Range> ranges ) {
        super();
        if (ranges != null){
            getFeatureRanges().addAll(ranges);
        }
        setId( id );
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new FeatureIdentifierList());
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new FeatureXrefList());
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new FeatureAnnotationList());
    }

    @Override
    protected void initialiseRanges() {
        initialiseRangesWith(new FeatureRangeList());
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
               this.names = new FeatureNames();
            }
            else {
                this.names.getAliases().clear();
            }
            super.setShortName(value.getShortLabel());
            super.setFullName(value.getFullName());
            this.names.getAliases().addAll(value.getAliases());
        }
        else if (this.names != null){
            super.setShortName(null);
            super.setFullName(null);
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
            this.xref = new FeatureXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            getIdentifiers().clear();
            getXrefs().clear();
            this.xref = null;
        }
    }

    /**
     * Check if the optional featureType is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatureType() {
        return getType() != null;
    }

    /**
     * Gets the value of the featureType property.
     *
     * @return possible object is {@link CvType }
     */
    public FeatureType getFeatureType() {
        return (FeatureType) getType();
    }

    /**
     * Sets the value of the featureType property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setFeatureType( FeatureType value ) {
        super.setType(value);
    }

    /**
     * Check if the optional featureDetectionMethod is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatureDetectionMethod() {
        return getDetectionMethod() != null;
    }

    /**
     * Gets the value of the featureDetectionMethod property.
     *
     * @return possible object is {@link CvType }
     */
    public FeatureDetectionMethod getFeatureDetectionMethod() {
        return (FeatureDetectionMethod) getDetectionMethod();
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setFeatureDetectionMethod( FeatureDetectionMethod value ) {
        super.setDetectionMethod(value);
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
     * Gets the value of the experimentRefList property.
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
     * Check if the optional experimentRefs is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasExperimentRefs() {
        return experimentRefs != null && !experimentRefs.isEmpty();
    }

    /**
     * Gets the value of the experimentRef property.
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
     * Gets the value of the featureRangeList property.
     *
     * @return possible object is {@link Range }
     */
    public Collection<Range> getFeatureRanges() {
        if (xmlRanges == null){
            xmlRanges = new FeatureXmlRangeList();
        }
        return xmlRanges;
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
            attributes = new FeatureXmlAnnotationList();
        }
        return attributes;
    }

    /////////////////////////////////
    // Object override

    @Override
    public void setShortName(String name) {
        if (name == null){
            this.names = null;
        }
        else if (names != null){
            super.setShortName(name);
        }
        else  {
            names = new FeatureNames();
            super.setShortName(name);
        }
    }

    @Override
    public void setFullName(String name) {
        if (names != null){
            if (names.getShortLabel() == null){
                super.setShortName(name);
            }
            super.setFullName(name);
        }
        else if (name != null) {
            names = new FeatureNames();
            super.setShortName(name);
            super.setFullName(name);
        }
    }

    protected void setShortNameOnly(String name) {
        super.setShortName(name);

    }

    protected void setFullNameOnly(String name) {
        super.setFullName(name);
    }

    protected String getFeatureFullName(){
        return super.getFullName();
    }

    @Override
    public FeatureDetectionMethod getDetectionMethod() {
        return (FeatureDetectionMethod) super.getDetectionMethod();
    }

    @Override
    public Participant getParticipantEvidence() {
        return (Participant) super.getParticipantEvidence();
    }

    @Override
    public FeatureType getType() {
        return (FeatureType) super.getType();
    }

    @Override
    public void setType(CvTerm type) {
        if (type == null){
            super.setType(null);
        }
        else if (type instanceof FeatureType){
            super.setType(type);
        }
        else {
            FeatureType t = new FeatureType();
            CvTermCloner.copyAndOverrideCvTermProperties(type, t);
            super.setType(t);
        }
    }

    @Override
    public void setDetectionMethod(CvTerm method) {
        if (method == null){
            super.setType(null);
        }
        else if (method instanceof FeatureDetectionMethod){
            super.setDetectionMethod(method);
        }
        else {
            FeatureDetectionMethod detectionMethod = new FeatureDetectionMethod();
            CvTermCloner.copyAndOverrideCvTermProperties(method, detectionMethod);
            super.setDetectionMethod(detectionMethod);
        }
    }

    @Override
    public void setParticipantEvidence(ParticipantEvidence participant) {
        if (participant == null){
            super.setParticipantEvidence(null);
        }
        else if (participant instanceof Participant){
            super.setParticipantEvidence(participant);
        }
        else {
            Participant convertedParticipant = new Participant();

            ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(participant, convertedParticipant);
            super.setParticipantEvidence(convertedParticipant);
        }
    }

    @Override
    public void setParticipantEvidenceAndAddFeature(ParticipantEvidence participant) {
        if (participant == null){
            super.setParticipantEvidence(null);
        }
        else if (participant instanceof Participant){
            participant.addFeatureEvidence(this);
        }
        else {
            Participant convertedParticipant = new Participant();

            ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(participant, convertedParticipant);
            convertedParticipant.addFeatureEvidence(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Feature" );
        sb.append( "{id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", featureType=" ).append( getType() );
        sb.append( ", featureDetectionMethod=" ).append( getDetectionMethod() );
        sb.append( ", experiments=" ).append( experiments );
        sb.append( ", experimentRefs=" ).append( experimentRefs );
        sb.append( ", xmlRanges=" ).append(xmlRanges);
        sb.append( ", attributes=" ).append( attributes );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Feature feature = ( Feature ) o;

        if ( id != feature.id ) return false;
        if ( attributes != null ? !attributes.equals( feature.attributes ) : feature.attributes != null ) return false;
        if ( experimentRefs != null ? !experimentRefs.equals( feature.experimentRefs ) : feature.experimentRefs != null )
            return false;
        if ( experiments != null ? !experiments.equals( feature.experiments ) : feature.experiments != null )
            return false;
        if ( getDetectionMethod() != null ? !getDetectionMethod().equals( feature.getDetectionMethod() ) : feature.getDetectionMethod() != null )
            return false;
        if ( getType() != null ? !getType().equals( feature.getType() ) : feature.getType() != null )
            return false;
        if ( names != null ? !names.equals( feature.names ) : feature.names != null ) return false;
        if ( xmlRanges != null ? !xmlRanges.equals( feature.xmlRanges) : feature.xmlRanges != null ) return false;
        if ( xref != null ? !xref.equals( feature.xref ) : feature.xref != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = id;
        result = 31 * result + ( names != null ? names.hashCode() : 0 );
        result = 31 * result + ( xref != null ? xref.hashCode() : 0 );
        result = 31 * result + ( getType() != null ? getType().hashCode() : 0 );
        result = 31 * result + ( getDetectionMethod() != null ? getDetectionMethod().hashCode() : 0 );
        result = 31 * result + ( experiments != null ? experiments.hashCode() : 0 );
        result = 31 * result + ( experimentRefs != null ? experimentRefs.hashCode() : 0 );
        result = 31 * result + ( xmlRanges != null ? xmlRanges.hashCode() : 0 );
        result = 31 * result + ( attributes != null ? attributes.hashCode() : 0 );
        return result;
    }

    public Component getComponent() {
        return (Participant) getParticipantEvidence();
    }

    public void setComponent(Component participant) {
        if (participant == null){
            super.setParticipantEvidence(null);
        }
        else if (participant instanceof Participant){
            super.setParticipantEvidence((Participant)participant);
        }
        else {
            Participant convertedParticipant = new Participant();

            ParticipantCloner.copyAndOverrideComponentProperties(participant, convertedParticipant);
            super.setParticipantEvidence(convertedParticipant);
        }
    }

    public void setComponentAndAddFeature(Component participant) {
        if (participant == null){
            super.setParticipantEvidence(null);
        }
        else if (participant instanceof Participant){
            super.setParticipantEvidenceAndAddFeature((Participant)participant);
        }
        else {
            Participant convertedParticipant = new Participant();

            ParticipantCloner.copyAndOverrideComponentProperties(participant, convertedParticipant);
            super.setParticipantEvidenceAndAddFeature(convertedParticipant);
        }
    }

    public Collection<? extends ComponentFeature> getBindingSites() {
        if (bindingFeatures == null){
            bindingFeatures = new ArrayList<Feature>();
        }
        return bindingFeatures;
    }

    public boolean addBindingSite(ComponentFeature feature) {
        if (feature == null){
            return false;
        }
        if (bindingFeatures == null){
            bindingFeatures = new ArrayList<Feature>();
        }

        if (feature instanceof Feature){
            return bindingFeatures.add((Feature)feature);
        }
        else {
            Feature f = new Feature();
            FeatureCloner.copyAndOverrideComponentFeaturesProperties(feature, f);

            return bindingFeatures.add(f);
        }
    }

    public boolean removeBindingSite(ComponentFeature feature) {
        if (feature == null){
            return false;
        }
        if (bindingFeatures == null){
            bindingFeatures = new ArrayList<Feature>();
        }

        if (feature instanceof Feature){
            return bindingFeatures.remove(feature);
        }
        else {
            Feature f = new Feature();
            FeatureCloner.copyAndOverrideComponentFeaturesProperties(feature, f);

            return bindingFeatures.remove(f);
        }
    }

    public boolean addAllBindingSites(Collection<? extends ComponentFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ComponentFeature feature : features){
            if (addBindingSite(feature)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllBindingSites(Collection<? extends ComponentFeature> features) {
        if (features == null){
            return false;
        }

        boolean removed = false;
        for (ComponentFeature feature : features){
            if (removeBindingSite(feature)){
                removed = true;
            }
        }
        return removed;
    }

    @Override
    protected void initialiseBindingSiteEvidences() {
        super.initialiseBindingSiteEvidencesWith(Collections.EMPTY_LIST);
    }

    @Override
    protected void initialiseBindingSiteEvidencesWith(Collection<FeatureEvidence> features) {
        if (features == null){
            initialiseBindingSiteEvidencesWith(Collections.EMPTY_LIST);
        }
        else {
            for (FeatureEvidence f : features){
                addBindingSiteEvidence(f);
            }
        }
    }

    @Override
    public Collection<? extends FeatureEvidence> getBindingSiteEvidences() {
        if (bindingFeatures == null){
            bindingFeatures = new ArrayList<Feature>();
        }
        return bindingFeatures;
    }

    @Override
    public boolean addBindingSiteEvidence(FeatureEvidence feature) {
        if (feature == null){
            return false;
        }
        if (bindingFeatures == null){
            bindingFeatures = new ArrayList<Feature>();
        }

        if (feature instanceof Feature){
            return bindingFeatures.add((Feature)feature);
        }
        else {
            Feature f = new Feature();
            FeatureCloner.copyAndOverrideFeatureEvidenceProperties(feature, f);

            return bindingFeatures.add(f);
        }
    }

    @Override
    public boolean removeBindingSiteEvidence(FeatureEvidence feature) {
        if (feature == null){
            return false;
        }
        if (bindingFeatures == null){
            bindingFeatures = new ArrayList<Feature>();
        }

        if (feature instanceof Feature){
            return bindingFeatures.remove(feature);
        }
        else {
            Feature f = new Feature();
            FeatureCloner.copyAndOverrideFeatureEvidenceProperties(feature, f);

            return bindingFeatures.remove(f);
        }
    }

    private class FeatureNames extends Names{

        public String getShortLabel() {
            return getShortName();
        }

        public boolean hasShortLabel() {
            return getShortName() != null;
        }

        public void setShortLabel( String value ) {
            setShortNameOnly(value);
        }

        public String getFullName() {
            return getFeatureFullName();
        }

        public boolean hasFullName() {
            return getFeatureFullName() != null;
        }

        public void setFullName( String value ) {
            setFullNameOnly(value);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Names" );
            sb.append( "{shortLabel='" ).append( getShortName() ).append( '\'' );
            sb.append( ", fullName='" ).append( getFeatureFullName() ).append( '\'' );
            sb.append( ", aliases=" ).append( getAliases() );
            sb.append( '}' );
            return sb.toString();
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            Names names = ( Names ) o;

            if ( getAliases() != null ? !getAliases().equals(names.getAliases()) : names.getAliases() != null ) return false;
            if ( getFeatureFullName() != null ? !getFeatureFullName().equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( getShortName() != null ? !getShortName().equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( getShortName() != null ? getShortName().hashCode() : 0 );
            result = 31 * result + ( getFeatureFullName() != null ? getFeatureFullName().hashCode() : 0 );
            result = 31 * result + ( getAliases() != null ? getAliases().hashCode() : 0 );
            return result;
        }
    }

    private class FeatureXref extends Xref{

        protected boolean isPrimaryAnIdentity = false;
        protected SecondaryRefList extendedSecondaryRefList = new SecondaryRefList();

        public FeatureXref() {
            super();
        }

        public FeatureXref(DbReference primaryRef) {
            super(primaryRef);
        }

        public FeatureXref(DbReference primaryRef, Collection<DbReference> secondaryRef) {
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
                        ((FeatureIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((FeatureXrefList)getXrefs()).addOnly(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((FeatureIdentifierList)getIdentifiers()).removeOnly(getPrimaryRef());
                processRemovedIdentifierEvent(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((FeatureIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((FeatureXrefList)getXrefs()).addOnly(value);
                    }
                }
            }
            else {
                ((FeatureXrefList)getXrefs()).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((FeatureIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((FeatureXrefList)getXrefs()).addOnly(value);
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
                    ((FeatureIdentifierList)getIdentifiers()).addOnly(added);
                    processAddedIdentifierEvent(added);
                }
                else {
                    ((FeatureXrefList)getXrefs()).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((FeatureIdentifierList)getIdentifiers()).removeOnly(removed);
                    processRemovedIdentifierEvent(removed);
                }
                else {
                    ((FeatureXrefList)getXrefs()).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((FeatureIdentifierList)getIdentifiers()).retainAllOnly(primary);
                clearPropertiesLinkedToIdentifiers();
                ((FeatureXrefList)getXrefs()).retainAllOnly(primary);
            }
        }
    }

    private class FeatureIdentifierList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public FeatureIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                FeatureXref reference = (FeatureXref) xref;

                if (xref.getPrimaryRef() == null){
                    if (added instanceof DbReference){
                        reference.setPrimaryRefOnly((DbReference) added);
                        processAddedIdentifierEvent(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.setPrimaryRefOnly(fixedRef);
                        processAddedIdentifierEvent(fixedRef);
                    }
                }
                else {
                    if (added instanceof DbReference){
                        reference.extendedSecondaryRefList.addOnly((DbReference) added);
                        processAddedIdentifierEvent(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.addOnly(fixedRef);
                        processAddedIdentifierEvent(fixedRef);
                    }
                }
            }
            else {
                if (added instanceof DbReference){
                    xref = new FeatureXref();
                    ((FeatureXref) xref).setPrimaryRefOnly((DbReference) added);
                    processAddedIdentifierEvent(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new FeatureXref();
                    ((FeatureXref) xref).setPrimaryRefOnly(fixedRef);
                    processAddedIdentifierEvent(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                FeatureXref reference = (FeatureXref) xref;

                if (reference.getPrimaryRef() == removed){
                    reference.setPrimaryRefOnly(null);
                    processRemovedIdentifierEvent(removed);
                }
                else {
                    if (removed instanceof DbReference){
                        reference.extendedSecondaryRefList.removeOnly((DbReference) removed);
                        processRemovedIdentifierEvent(removed);

                    }
                    else {
                        DbReference fixedRef = new DbReference(removed.getDatabase().getShortName(), removed.getDatabase().getMIIdentifier(), removed.getId(),
                                removed.getQualifier() != null ? removed.getQualifier().getShortName() : null, removed.getQualifier() != null ? removed.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.removeOnly(fixedRef);
                        processRemovedIdentifierEvent(removed);
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToIdentifiers();

            if (xref != null){
                FeatureXref reference = (FeatureXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(getXrefs());

                if (reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    private class FeatureXrefList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public FeatureXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                FeatureXref reference = (FeatureXref) xref;

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
                    xref = new FeatureXref();
                    ((FeatureXref) xref).setPrimaryRefOnly((DbReference) added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new FeatureXref(fixedRef);
                    ((FeatureXref) xref).setPrimaryRefOnly(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (xref != null){
                FeatureXref reference = (FeatureXref) xref;

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
                FeatureXref reference = (FeatureXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(getIdentifiers());

                if (!reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    private class FeatureAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public FeatureAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((FeatureXmlAnnotationList)attributes).addOnly((Attribute) added);
            }
            else {
                Attribute att = new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue());
                ((FeatureXmlAnnotationList)attributes).addOnly(att);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((FeatureXmlAnnotationList)getAttributes()).removeOnly(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((FeatureXmlAnnotationList)getAttributes()).removeOnly(att);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((FeatureXmlAnnotationList)getAttributes()).clearOnly();
        }
    }

    private class FeatureXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public FeatureXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {

            // we added a annotation, needs to add it in annotations
            ((FeatureAnnotationList)getAnnotations()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            // we removed a annotation, needs to remove it in annotations
            ((FeatureAnnotationList)getAnnotations()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((FeatureAnnotationList)getAnnotations()).clearOnly();
        }
    }

    private class FeatureRangeList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Range> {
        public FeatureRangeList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Range added) {
            if (added instanceof Range){
                ((FeatureXmlRangeList)getFeatureRanges()).addOnly((Range) added);
            }
            else {
                Range range = new Range();

                RangeCloner.copyAndOverrideRangeProperties(added, range);
                ((FeatureXmlRangeList)getFeatureRanges()).addOnly(range);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Range removed) {
            if (removed instanceof Range){
                ((FeatureXmlRangeList)getFeatureRanges()).removeOnly(removed);
            }
            else {
                Range range = new Range();

                RangeCloner.copyAndOverrideRangeProperties(removed, range);
                ((FeatureXmlRangeList) getFeatureRanges()).removeOnly(range);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((FeatureXmlRangeList) getFeatureRanges()).clearOnly();
        }
    }

    private class FeatureXmlRangeList extends AbstractListHavingPoperties<Range> {
        public FeatureXmlRangeList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Range added) {

            // we added a annotation, needs to add it in annotations
            ((FeatureRangeList) getRanges()).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Range removed) {

            // we removed a annotation, needs to remove it in annotations
            ((FeatureRangeList) getRanges()).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((FeatureRangeList)getRanges()).clearOnly();
        }
    }
}