/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.CvTermCloner;
import psidev.psi.mi.jami.utils.clone.RangeCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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

public class Feature extends DefaultFeatureEvidence implements HasId, NamesContainer, XrefContainer, AttributeContainer {

    private int id;

    private Names names;

    private Xref xref;

    private Collection<ExperimentDescription> experiments;

    private Collection<ExperimentRef> experimentRefs;

    private Collection<Range> xmlRanges = new FeatureXmlRangeList();

    private Collection<Attribute> attributes = new FeatureXmlAnnotationList();

    ///////////////////////////
    // Constructors

    public Feature() {
        super();
    }

    public Feature( int id, Collection<Range> ranges ) {
        super();
        this.xmlRanges = ranges;
        setId( id );
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new FeatureIdentifierList();
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new FeatureXrefList();
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new FeatureAnnotationList();
    }

    @Override
    protected void initializeRanges() {
        this.ranges = new FeatureRangeList();
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
            this.names.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            this.names.getAliases().addAll(value.getAliases());
        }
        else {
            this.shortName = null;
            this.fullName = null;
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
            this.xref = new FeatureXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else {
            identifiers.clear();
            xrefs.clear();
            this.xref = null;
        }
    }

    /**
     * Check if the optional featureType is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatureType() {
        return type != null;
    }

    /**
     * Gets the value of the featureType property.
     *
     * @return possible object is {@link CvType }
     */
    public FeatureType getFeatureType() {
        return (FeatureType) type;
    }

    /**
     * Sets the value of the featureType property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setFeatureType( FeatureType value ) {
        this.type = value;
    }

    /**
     * Check if the optional featureDetectionMethod is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasFeatureDetectionMethod() {
        return detectionMethod != null;
    }

    /**
     * Gets the value of the featureDetectionMethod property.
     *
     * @return possible object is {@link CvType }
     */
    public FeatureDetectionMethod getFeatureDetectionMethod() {
        return (FeatureDetectionMethod) detectionMethod;
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setFeatureDetectionMethod( FeatureDetectionMethod value ) {
        this.detectionMethod = value;
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
        return attributes;
    }

    protected void processAddedIdentifier(psidev.psi.mi.jami.model.Xref added){
        if (interpro != added && XrefUtils.isXrefFromDatabase(added, psidev.psi.mi.jami.model.Xref.INTERPRO_MI, psidev.psi.mi.jami.model.Xref.INTERPRO)){
            // the current interpro identifier is not identity, we may want to set interpro Identifier
            if (!XrefUtils.doesXrefHaveQualifier(interpro, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                // the interpro identifier is not set, we can set the interpro identifier
                if (interpro == null){
                    interpro = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                    interpro = added;
                }
                // the added xref is secondary object and the current interpro identifier is not a secondary object, we reset interpro identifier
                else if (!XrefUtils.doesXrefHaveQualifier(interpro, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)){
                    interpro = added;
                }
            }
        }
    }

    protected void processRemovedIdentifier(psidev.psi.mi.jami.model.Xref removed){
        if (interpro != null && interpro.equals(removed)){
            interpro = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, psidev.psi.mi.jami.model.Xref.INTERPRO_MI, psidev.psi.mi.jami.model.Xref.INTERPRO);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers(){
        interpro = null;
    }

    /////////////////////////////////
    // Object override

    @Override
    public void setShortName(String name) {
        if (names != null){
            names.setShortLabel(name);
        }
        else if (name != null) {
            names = new FeatureNames();
            names.setShortLabel(name);
        }
    }

    @Override
    public void setFullName(String name) {
        if (names != null){
            names.setShortLabel(name);
            names.setFullName(name);
        }
        else if (name != null) {
            names = new FeatureNames();
            names.setShortLabel(name);
            names.setFullName(name);
        }
    }

    @Override
    public void setType(CvTerm type) {
        if (type == null){
            super.setType(null);
        }
        else if (type instanceof FeatureType){
            this.type = type;
        }
        else {
            this.type = new FeatureType();
            CvTermCloner.copyAndOverrideCvTermProperties(type, this.type);
        }
    }

    @Override
    public void setDetectionMethod(CvTerm method) {
        if (method == null){
            super.setType(null);
        }
        else if (method instanceof FeatureDetectionMethod){
            this.detectionMethod = method;
        }
        else {
            this.detectionMethod = new FeatureDetectionMethod();
            CvTermCloner.copyAndOverrideCvTermProperties(method, this.detectionMethod);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Feature" );
        sb.append( "{id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", featureType=" ).append( type );
        sb.append( ", featureDetectionMethod=" ).append( detectionMethod );
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
        if ( detectionMethod != null ? !detectionMethod.equals( feature.detectionMethod ) : feature.detectionMethod != null )
            return false;
        if ( type != null ? !type.equals( feature.type ) : feature.type != null )
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
        result = 31 * result + ( type != null ? type.hashCode() : 0 );
        result = 31 * result + ( detectionMethod != null ? detectionMethod.hashCode() : 0 );
        result = 31 * result + ( experiments != null ? experiments.hashCode() : 0 );
        result = 31 * result + ( experimentRefs != null ? experimentRefs.hashCode() : 0 );
        result = 31 * result + ( xmlRanges != null ? xmlRanges.hashCode() : 0 );
        result = 31 * result + ( attributes != null ? attributes.hashCode() : 0 );
        return result;
    }

    protected class FeatureNames extends Names{

        public String getShortLabel() {
            return shortName;
        }

        public boolean hasShortLabel() {
            return shortName != null;
        }

        public void setShortLabel( String value ) {
            shortName = value;
        }

        public String getFullName() {
            return fullName;
        }

        public boolean hasFullName() {
            return fullName != null;
        }

        public void setFullName( String value ) {
            fullName = value;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Names" );
            sb.append( "{shortLabel='" ).append( shortName ).append( '\'' );
            sb.append( ", fullName='" ).append( fullName ).append( '\'' );
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
            if ( fullName != null ? !fullName.equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( shortName != null ? !shortName.equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( shortName != null ? shortName.hashCode() : 0 );
            result = 31 * result + ( fullName != null ? fullName.hashCode() : 0 );
            result = 31 * result + ( getAliases() != null ? getAliases().hashCode() : 0 );
            return result;
        }
    }

    protected class FeatureXref extends Xref{

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
                secondaryRef.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((FeatureIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((FeatureXrefList)xrefs).addOnly(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((FeatureIdentifierList)identifiers).removeOnly(getPrimaryRef());
                processRemovedIdentifier(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((FeatureIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((FeatureXrefList)xrefs).addOnly(value);
                    }
                }
            }
            else {
                ((FeatureXrefList)xrefs).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((FeatureIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((FeatureXrefList)xrefs).addOnly(value);
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
                    ((FeatureIdentifierList)identifiers).addOnly(added);
                    processAddedIdentifier(added);
                }
                else {
                    ((FeatureXrefList)xrefs).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((FeatureIdentifierList)identifiers).removeOnly(removed);
                    processRemovedIdentifier(removed);
                }
                else {
                    ((FeatureXrefList)xrefs).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((FeatureIdentifierList)identifiers).retainAllOnly(primary);
                clearPropertiesLinkedToIdentifiers();
                ((FeatureXrefList)xrefs).retainAllOnly(primary);
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
                        processAddedIdentifier(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.setPrimaryRefOnly(fixedRef);
                        processAddedIdentifier(fixedRef);
                    }
                }
                else {
                    if (added instanceof DbReference){
                        reference.extendedSecondaryRefList.addOnly((DbReference) added);
                        processAddedIdentifier(added);
                    }
                    else {
                        DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                                added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.addOnly(fixedRef);
                        processAddedIdentifier(fixedRef);
                    }
                }
            }
            else {
                if (added instanceof DbReference){
                    xref = new FeatureXref();
                    ((FeatureXref) xref).setPrimaryRefOnly((DbReference) added);
                    processAddedIdentifier(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new FeatureXref();
                    ((FeatureXref) xref).setPrimaryRefOnly(fixedRef);
                    processAddedIdentifier(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                FeatureXref reference = (FeatureXref) xref;

                if (reference.getPrimaryRef() == removed){
                    reference.setPrimaryRefOnly(null);
                    processRemovedIdentifier(removed);
                }
                else {
                    if (removed instanceof DbReference){
                        reference.extendedSecondaryRefList.removeOnly((DbReference) removed);
                        processRemovedIdentifier(removed);

                    }
                    else {
                        DbReference fixedRef = new DbReference(removed.getDatabase().getShortName(), removed.getDatabase().getMIIdentifier(), removed.getId(),
                                removed.getQualifier() != null ? removed.getQualifier().getShortName() : null, removed.getQualifier() != null ? removed.getQualifier().getMIIdentifier() : null);

                        reference.extendedSecondaryRefList.removeOnly(fixedRef);
                        processRemovedIdentifier(removed);
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToIdentifiers();

            if (xref != null){
                FeatureXref reference = (FeatureXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(xrefs);

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
                reference.extendedSecondaryRefList.retainAllOnly(identifiers);

                if (!reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    protected class FeatureAnnotationList extends AbstractListHavingPoperties<Annotation> {
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
                ((FeatureXmlAnnotationList)attributes).removeOnly(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((FeatureXmlAnnotationList)attributes).removeOnly(att);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((FeatureXmlAnnotationList)attributes).clearOnly();
        }
    }

    protected class FeatureXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public FeatureXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {

            // we added a annotation, needs to add it in annotations
            ((FeatureAnnotationList)annotations).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            // we removed a annotation, needs to remove it in annotations
            ((FeatureAnnotationList)annotations).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((FeatureAnnotationList)annotations).clearOnly();
        }
    }

    protected class FeatureRangeList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Range> {
        public FeatureRangeList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Range added) {
            if (added instanceof Range){
                ((FeatureXmlRangeList)xmlRanges).addOnly((Range) added);
            }
            else {
                Range range = new Range();

                RangeCloner.copyAndOverrideRangeProperties(added, range);
                ((FeatureXmlRangeList)xmlRanges).addOnly(range);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Range removed) {
            if (removed instanceof Range){
                ((FeatureXmlRangeList)xmlRanges).removeOnly(removed);
            }
            else {
                Range range = new Range();

                RangeCloner.copyAndOverrideRangeProperties(removed, range);
                ((FeatureXmlRangeList) xmlRanges).removeOnly(range);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((FeatureXmlRangeList) xmlRanges).clearOnly();
        }
    }

    protected class FeatureXmlRangeList extends AbstractListHavingPoperties<Range> {
        public FeatureXmlRangeList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Range added) {

            // we added a annotation, needs to add it in annotations
            ((FeatureRangeList) ranges).addOnly(added);
        }

        @Override
        protected void processRemovedObjectEvent(Range removed) {

            // we removed a annotation, needs to remove it in annotations
            ((FeatureRangeList) ranges).removeOnly(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((FeatureRangeList)ranges).clearOnly();
        }
    }
}