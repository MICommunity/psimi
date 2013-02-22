/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */


package psidev.psi.mi.xml.model;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.CvTermCloner;
import psidev.psi.mi.jami.utils.clone.PublicationCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
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

public class ExperimentDescription extends DefaultExperiment implements HasId, NamesContainer, XrefContainer, AttributeContainer {

    private int id;

    private Names names;

    private Xref xref;

    private Collection<Organism> hostOrganisms = new ExperimentHostOrganismList();

    private ParticipantIdentificationMethod participantIdentificationMethod;

    private FeatureDetectionMethod featureDetectionMethod;

    private Collection<Confidence> confidences;

    private Collection<Attribute> attributes=new ExperimentXmlAnnotationList();

    ///////////////////////////
    // Constructors

    public ExperimentDescription() {
        super(new Bibref(), new InteractionDetectionMethod());
        interactionDetectionMethod.setShortName(UNSPECIFIED_METHOD);
        interactionDetectionMethod.setMIIdentifier(UNSPECIFIED_METHOD_MI);

        publication.getExperiments().add(this);
    }

    public ExperimentDescription( Bibref bibref, InteractionDetectionMethod interactionDetectionMethod ) {
        super(bibref, interactionDetectionMethod != null ? interactionDetectionMethod : new InteractionDetectionMethod());
        if (interactionDetectionMethod == null){
            interactionDetectionMethod.setShortName(UNSPECIFIED_METHOD);
            interactionDetectionMethod.setMIIdentifier(UNSPECIFIED_METHOD_MI);
        }

        publication.getExperiments().add(this);
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new ExperimentXrefList();
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new ExperimentAnnotationList();
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
        if (value != null){
            if (names == null){
                names = new ExperimentNames();
            }
            this.names.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            this.names.getAliases().addAll(value.getAliases());
        }
        else if (this.names != null) {
            this.shortLabel = null;
            this.names = null;
        }
    }

    /**
     * Gets the value of the bibref property.
     *
     * @return possible object is {@link Bibref }
     */
    public Bibref getBibref() {
        return (Bibref) publication;
    }

    /**
     * Sets the value of the bibref property.
     *
     * @param value allowed object is {@link Bibref }
     */
    public void setBibref( Bibref value ) {
        if (this.publication != null){
            this.publication.getExperiments().remove(this);
        }
        this.publication = value;
        this.publication.getExperiments().add(this);
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
            this.xref = new ExperimentXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            xrefs.clear();
            this.xref = null;
        }
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
        return hostOrganisms;
    }

    /**
     * Gets the value of the interactionDetectionMethod property.
     *
     * @return possible object is {@link CvType }
     */
    public InteractionDetectionMethod getInteractionDetectionMethod() {
        return (InteractionDetectionMethod) interactionDetectionMethod;
    }

    /**
     * Sets the value of the interactionDetectionMethod property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setInteractionDetectionMethod( InteractionDetectionMethod value ) {
        if (value != null){
            this.interactionDetectionMethod = value;
        }
        else {
            this.interactionDetectionMethod = new InteractionDetectionMethod();
            interactionDetectionMethod.setShortName(UNSPECIFIED_METHOD);
            interactionDetectionMethod.setMIIdentifier(UNSPECIFIED_METHOD_MI);
        }
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
    public void setShortLabel(String name) {
        if (names != null){
            names.setShortLabel(name);
        }
        else if (name == null){
            shortLabel = null;
            names = null;
        }
        else {
            names = new ExperimentNames();
            names.setShortLabel(name);
        }
    }

    @Override
    public void setPublication(Publication publication) {
        if (publication == null){
            if (this.publication != null){
                this.publication.getExperiments().remove(this);
            }

            super.setPublication(null);
        }
        else if (publication instanceof Bibref){
            super.setPublication(publication);
        }
        else {

            Bibref convertedPublication = new Bibref();

            PublicationCloner.copyAndOverridePublicationProperties(publication, convertedPublication);
            super.setPublication(convertedPublication);
        }
    }

    @Override
    public void setPublicationAndAddExperiment(Publication publication) {
        if (publication == null){

            super.setPublication(null);
        }
        else if (publication instanceof Bibref){

            super.setPublication(publication);
            publication.getExperiments().add(this);
        }
        else {

            Bibref convertedPublication = new Bibref();

            PublicationCloner.copyAndOverridePublicationProperties(publication, convertedPublication);
            super.setPublication(convertedPublication);

            publication.getExperiments().add(this);
        }
    }

    @Override
    public void setHostOrganism(psidev.psi.mi.jami.model.Organism organism) {
        if (organism != null){
            // first remove old psi mi if not null
            if (this.hostOrganism != null){
                hostOrganisms.remove(this.hostOrganism);
            }
            this.hostOrganism = organism;
            ((ExperimentHostOrganismList)hostOrganisms).addOnly((Organism) this.hostOrganism);
        }
        // remove all organisms if the collection is not empty
        else if (!this.hostOrganisms.isEmpty()) {
            this.hostOrganism = null;
            ((ExperimentHostOrganismList)hostOrganisms).clearOnly();
        }
    }

    @Override
    public void setInteractionDetectionMethod(CvTerm term) {
        if (interactionDetectionMethod == null){
            this.interactionDetectionMethod = new InteractionDetectionMethod();
            interactionDetectionMethod.setShortName(UNSPECIFIED_METHOD);
            interactionDetectionMethod.setMIIdentifier(UNSPECIFIED_METHOD_MI);
        }
        else if (interactionDetectionMethod instanceof InteractionDetectionMethod){
            this.interactionDetectionMethod = term;
        }
        else {
            this.interactionDetectionMethod = new InteractionDetectionMethod();
            CvTermCloner.copyAndOverrideCvTermProperties(term, this.interactionDetectionMethod);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ExperimentDescription" );
        sb.append( "{id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", bibref=" ).append( publication );
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
        if ( publication != null ? !publication.equals( that.publication ) : that.publication != null ) {
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
        result = 29 * result + ( publication != null ? publication.hashCode() : 0 );
        result = 29 * result + ( interactionDetectionMethod != null ? interactionDetectionMethod.hashCode() : 0 );
        return result;
    }

    protected class ExperimentNames extends Names{

        public String getShortLabel() {
            return shortLabel;
        }

        public boolean hasShortLabel() {
            return shortLabel != null;
        }

        public void setShortLabel( String value ) {
            shortLabel = value;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Names" );
            sb.append( "{shortLabel='" ).append( shortLabel ).append( '\'' );
            sb.append( ", fullName='" ).append( super.getFullName() ).append( '\'' );
            sb.append( ", aliases=" ).append( super.getAliases() );
            sb.append( '}' );
            return sb.toString();
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            Names names = ( Names ) o;

            if ( super.getAliases() != null ? !super.getAliases().equals( names.getAliases() ) : names.getAliases() != null ) return false;
            if ( super.getFullName() != null ? !super.getFullName().equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( shortLabel != null ? !shortLabel.equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( shortLabel != null ? shortLabel.hashCode() : 0 );
            result = 31 * result + ( super.getFullName() != null ? super.getFullName().hashCode() : 0 );
            result = 31 * result + ( super.getAliases() != null ? super.getAliases().hashCode() : 0 );
            return result;
        }
    }

    protected class ExperimentXref extends Xref{

        protected SecondaryRefList extendedSecondaryRefList = new SecondaryRefList();

        public ExperimentXref() {
            super();
        }

        public ExperimentXref(DbReference primaryRef) {
            super(primaryRef);
        }

        public ExperimentXref(DbReference primaryRef, Collection<DbReference> secondaryRef) {
            super( primaryRef );

            if (secondaryRef != null && !secondaryRef.isEmpty()){
                secondaryRef.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    // patch for imex primary
                    if (XrefUtils.isXrefFromDatabase(value, psidev.psi.mi.jami.model.Xref.IMEX_MI, psidev.psi.mi.jami.model.Xref.IMEX)
                            && XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY)){
                        if (publication == null){
                            publication = new Bibref();
                        }
                        publication.getXrefs().add(value);
                    }
                    // patch for primary-reference
                    else if (XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                        if (publication == null){
                            publication = new Bibref();
                        }
                        publication.getIdentifiers().add(value);
                    }
                    else {
                        ((ExperimentXrefList)xrefs).addOnly(value);
                    }
                }
            }
            else {
                ((ExperimentXrefList)xrefs).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    // patch for imex primary
                    if (XrefUtils.isXrefFromDatabase(value, psidev.psi.mi.jami.model.Xref.IMEX_MI, psidev.psi.mi.jami.model.Xref.IMEX)
                            && XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY)){
                        if (publication == null){
                            publication = new Bibref();
                        }
                        publication.getXrefs().add(value);
                    }
                    // patch for primary-reference
                    else if (XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                        if (publication == null){
                            publication = new Bibref();
                        }
                        publication.getIdentifiers().add(value);
                    }
                    else {
                        ((ExperimentXrefList)xrefs).addOnly(value);
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
                // patch for imex primary
                if (XrefUtils.isXrefFromDatabase(added, psidev.psi.mi.jami.model.Xref.IMEX_MI, psidev.psi.mi.jami.model.Xref.IMEX)
                        && XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY)){
                    if (publication == null){
                        publication = new Bibref();
                    }
                    publication.getXrefs().add(added);
                }
                // patch for primary-reference
                else if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                    if (publication == null){
                        publication = new Bibref();
                    }
                    publication.getIdentifiers().add(added);
                }
                else {
                    ((ExperimentXrefList)xrefs).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                // patch for imex primary
                if (XrefUtils.isXrefFromDatabase(removed, psidev.psi.mi.jami.model.Xref.IMEX_MI, psidev.psi.mi.jami.model.Xref.IMEX)
                        && XrefUtils.doesXrefHaveQualifier(removed, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY)){
                    if (publication != null){
                        publication.getXrefs().remove(removed);
                    }
                    else {
                        ((ExperimentXrefList)xrefs).removeOnly(removed);
                    }
                }
                // patch for primary-reference
                else if (XrefUtils.doesXrefHaveQualifier(removed, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                    if (publication != null){
                        publication.getIdentifiers().remove(removed);
                    }
                    else {
                        ((ExperimentXrefList)xrefs).removeOnly(removed);
                    }
                }
                else {
                    ((ExperimentXrefList)xrefs).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((ExperimentXrefList)xrefs).retainAllOnly(primary);
            }
        }
    }

    private class ExperimentXrefList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public ExperimentXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                ExperimentXref reference = (ExperimentXref) xref;

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
                    xref = new ExperimentXref();
                    ((ExperimentXref) xref).setPrimaryRefOnly((DbReference) added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new ExperimentXref(fixedRef);
                    ((ExperimentXref) xref).setPrimaryRefOnly(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (xref != null){
                ExperimentXref reference = (ExperimentXref) xref;

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
                ExperimentXref reference = (ExperimentXref) xref;
                reference.extendedSecondaryRefList.clearOnly();
                reference.setPrimaryRefOnly(null);
            }
        }
    }

    private class ExperimentHostOrganismList extends AbstractListHavingPoperties<Organism> {
        public ExperimentHostOrganismList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Organism added) {

            if (hostOrganism == null){
                hostOrganism = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(Organism removed) {

            if (isEmpty()){
                hostOrganism = null;
            }
            else if (hostOrganism != null && removed.equals(hostOrganism)){
                hostOrganism = iterator().next();
            }
        }

        @Override
        protected void clearProperties() {

            hostOrganism = null;
        }
    }

    protected class ExperimentAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public ExperimentAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((ExperimentXmlAnnotationList)attributes).addOnly((Attribute) added);
            }
            else {
                ((ExperimentXmlAnnotationList)attributes).addOnly(new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue()));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((ExperimentXmlAnnotationList)attributes).removeOnly(removed);
            }
            else {
                ((ExperimentXmlAnnotationList)attributes).removeOnly(new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue()));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ExperimentXmlAnnotationList)attributes).clearOnly();
        }
    }

    protected class ExperimentXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public ExperimentXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {
            if (AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE) ||
                    AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL) ||
                    AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR) ||
                    AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                ((Bibref) publication).getAttributes().add(added);
            }
            else {
                // we added a annotation, needs to add it in annotations
                ((ExperimentAnnotationList)annotations).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            if (AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE) ||
                    AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL) ||
                    AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR) ||
                    AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                ((Bibref) publication).getAttributes().remove(removed);
            }
            else {
                // we removed a annotation, needs to remove it in annotations
                ((ExperimentAnnotationList)annotations).removeOnly(removed);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ExperimentAnnotationList)annotations).clearOnly();
        }
    }
}
