/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */


package psidev.psi.mi.xml.model;

import psidev.psi.mi.jami.datasource.FileSourceContext;
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

public class ExperimentDescription extends DefaultExperiment implements HasId, NamesContainer, XrefContainer, AttributeContainer, FileSourceContext {

    private int id;

    private Names names;

    private Xref xref;

    private Collection<Organism> hostOrganisms;

    private ParticipantIdentificationMethod participantIdentificationMethod;

    private FeatureDetectionMethod featureDetectionMethod;

    private Collection<Confidence> confidences;

    private Collection<Attribute> attributes;

    private PsiXmlFileLocator locator;

    ///////////////////////////
    // Constructors

    public ExperimentDescription() {
        super(new Bibref(), new InteractionDetectionMethod());
        getInteractionDetectionMethod().setShortName(UNSPECIFIED_METHOD);
        getInteractionDetectionMethod().setMIIdentifier(UNSPECIFIED_METHOD_MI);

        getPublication().getExperiments().add(this);
    }

    public ExperimentDescription( Bibref bibref, InteractionDetectionMethod interactionDetectionMethod ) {
        super(bibref, interactionDetectionMethod != null ? interactionDetectionMethod : new InteractionDetectionMethod());
        if (interactionDetectionMethod == null){
            getInteractionDetectionMethod().setShortName(UNSPECIFIED_METHOD);
            getInteractionDetectionMethod().setMIIdentifier(UNSPECIFIED_METHOD_MI);
        }

        getPublication().getExperiments().add(this);
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new ExperimentXrefList());
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new ExperimentAnnotationList());
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
            super.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            if (this.getBibref() != null){
                this.getBibref().setTitle(value.getFullName());
            }
            this.names.getAliases().addAll(value.getAliases());
        }
        else if (this.names != null) {
            super.setShortLabel(null);
            this.names = null;
        }
    }

    /**
     * Gets the value of the bibref property.
     *
     * @return possible object is {@link Bibref }
     */
    public Bibref getBibref() {
        return getPublication();
    }

    @Override
    public Bibref getPublication() {
        return (Bibref)super.getPublication();
    }

    /**
     * Sets the value of the bibref property.
     *
     * @param value allowed object is {@link Bibref }
     */
    public void setBibref( Bibref value ) {
        if (getPublication() != null){
            getPublication().getExperiments().remove(this);
        }
        super.setPublication(value);
        getPublication().getExperiments().add(this);
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

    public PsiXmlFileLocator getSourceLocator() {
        return this.locator;
    }

    public void setSourceLocator(PsiXmlFileLocator locator) {
        this.locator = locator;
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
            this.xref = new ExperimentXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            getXrefs().clear();
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
        if (hostOrganisms == null){
           hostOrganisms  = new ExperimentHostOrganismList();
        }
        return hostOrganisms;
    }

    /**
     * Gets the value of the interactionDetectionMethod property.
     *
     * @return possible object is {@link CvType }
     */
    public InteractionDetectionMethod getInteractionDetectionMethod() {
        return (InteractionDetectionMethod) super.getInteractionDetectionMethod();
    }

    /**
     * Sets the value of the interactionDetectionMethod property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setInteractionDetectionMethod( InteractionDetectionMethod value ) {
        if (value != null){
            super.setInteractionDetectionMethod(value);
        }
        else {
            InteractionDetectionMethod interactionDetectionMethod = new InteractionDetectionMethod();
            interactionDetectionMethod.setShortName(UNSPECIFIED_METHOD);
            interactionDetectionMethod.setMIIdentifier(UNSPECIFIED_METHOD_MI);
            super.setInteractionDetectionMethod(interactionDetectionMethod);
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
        if (attributes == null){
            attributes =new ExperimentXmlAnnotationList();
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
    public Organism getHostOrganism() {
        return (Organism) super.getHostOrganism();
    }

    @Override
    public void setShortLabel(String name) {
        if (names != null){
            super.setShortLabel(name);
        }
        else if (name == null){
            super.setShortLabel(null);
            names = null;
        }
        else {
            names = new ExperimentNames();
            super.setShortLabel(name);
        }
    }

    protected void setShortLabelOnly(String name) {
        super.setShortLabel(name);
    }

    @Override
    public void setPublication(Publication publication) {
        if (publication == null){
            if (getPublication() != null){
                getPublication().getExperiments().remove(this);
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
            if (getHostOrganism() != null){
                getHostOrganisms().remove(getHostOrganism());
            }
            super.setHostOrganism(organism);
            ((ExperimentHostOrganismList)getHostOrganisms()).addOnly(getHostOrganism());
        }
        // remove all organisms if the collection is not empty
        else if (!getHostOrganisms().isEmpty()) {
            super.setHostOrganism(null);
            ((ExperimentHostOrganismList)getHostOrganisms()).clearOnly();
        }
    }

    protected void setHostOrganismOnly(psidev.psi.mi.jami.model.Organism organism) {
        super.setHostOrganism(organism);
    }

    @Override
    public void setInteractionDetectionMethod(CvTerm term) {
        if (getInteractionDetectionMethod() == null){
            super.setInteractionDetectionMethod(new InteractionDetectionMethod());
            getInteractionDetectionMethod().setShortName(UNSPECIFIED_METHOD);
            getInteractionDetectionMethod().setMIIdentifier(UNSPECIFIED_METHOD_MI);
        }
        else if (term instanceof InteractionDetectionMethod){
            super.setInteractionDetectionMethod(term);
        }
        else {
            super.setInteractionDetectionMethod(new InteractionDetectionMethod());
            CvTermCloner.copyAndOverrideCvTermProperties(term, getInteractionDetectionMethod());
        }
    }

    protected String getExperimentShortLabel(){
        return super.getShortLabel();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "ExperimentDescription" );
        sb.append( "{id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", bibref=" ).append( getPublication() );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", hostOrganisms=" ).append( hostOrganisms );
        sb.append( ", interactionDetectionMethod=" ).append( getInteractionDetectionMethod() );
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
        if ( getPublication() != null ? !getPublication().equals( that.getPublication() ) : that.getPublication() != null ) {
            return false;
        }
        if ( getInteractionDetectionMethod() != null ? !getInteractionDetectionMethod().equals( that.getInteractionDetectionMethod() ) : that.getInteractionDetectionMethod() != null ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = id;
        result = 29 * result + ( getPublication() != null ? getPublication().hashCode() : 0 );
        result = 29 * result + ( getInteractionDetectionMethod() != null ? getInteractionDetectionMethod().hashCode() : 0 );
        return result;
    }

    protected class ExperimentNames extends Names{

        public String getShortLabel() {
            return getExperimentShortLabel();
        }

        public boolean hasShortLabel() {
            return getShortLabel() != null;
        }

        public void setShortLabel( String value ) {
            setShortLabelOnly(value);
        }

        public String getFullName() {
            if (getPublication() == null){
                setBibref(new Bibref());
            }

            return getPublication().getTitle();
        }

        /**
         * Check if the optional fullName is defined.
         *
         * @return true if defined, false otherwise.
         */
        public boolean hasFullName() {
            if (getPublication() == null){
               return false;
            }
            else {
                return getPublication().getTitle() != null;
            }
        }

        /**
         * Sets the value of the fullName property.
         *
         * @param value allowed object is {@link String }
         */
        public void setFullName( String value ) {
            if (getPublication() == null){
                setBibref(new Bibref());
            }

            getPublication().setTitle(value);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append( "Names" );
            sb.append( "{shortLabel='" ).append( getShortLabel() ).append( '\'' );
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
            if ( getExperimentShortLabel() != null ? !getExperimentShortLabel().equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( getExperimentShortLabel() != null ? getExperimentShortLabel().hashCode() : 0 );
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
                extendedSecondaryRefList.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    // patch for imex primary
                    if (XrefUtils.isXrefFromDatabase(value, psidev.psi.mi.jami.model.Xref.IMEX_MI, psidev.psi.mi.jami.model.Xref.IMEX)
                            && XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY)){
                        if (getPublication() == null){
                            setBibref(new Bibref());
                        }
                        getPublication().getXrefs().add(value);
                    }
                    // patch for primary-reference
                    else if (XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                        if (getPublication() == null){
                            setBibref(new Bibref());
                        }
                        getPublication().getIdentifiers().add(value);
                    }
                    else {
                        ((ExperimentXrefList)getXrefs()).addOnly(value);
                    }
                }
            }
            else {
                ((ExperimentXrefList)getXrefs()).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    // patch for imex primary
                    if (XrefUtils.isXrefFromDatabase(value, psidev.psi.mi.jami.model.Xref.IMEX_MI, psidev.psi.mi.jami.model.Xref.IMEX)
                            && XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY)){
                        if (getPublication() == null){
                            setBibref(new Bibref());
                        }
                        getPublication().getXrefs().add(value);
                    }
                    // patch for primary-reference
                    else if (XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                        if (getPublication() == null){
                            setBibref(new Bibref());
                        }
                        getPublication().getIdentifiers().add(value);
                    }
                    else {
                        ((ExperimentXrefList)getXrefs()).addOnly(value);
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
                    if (getPublication() == null){
                        setBibref(new Bibref());
                    }
                    getPublication().getXrefs().add(added);
                }
                // patch for primary-reference
                else if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                    if (getPublication() == null){
                        setBibref(new Bibref());
                    }
                    getPublication().getIdentifiers().add(added);
                }
                else {
                    ((ExperimentXrefList)getXrefs()).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                // patch for imex primary
                if (XrefUtils.isXrefFromDatabase(removed, psidev.psi.mi.jami.model.Xref.IMEX_MI, psidev.psi.mi.jami.model.Xref.IMEX)
                        && XrefUtils.doesXrefHaveQualifier(removed, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY)){
                    if (getPublication() != null){
                        getPublication().getXrefs().remove(removed);
                    }
                    else {
                        ((ExperimentXrefList)getXrefs()).removeOnly(removed);
                    }
                }
                // patch for primary-reference
                else if (XrefUtils.doesXrefHaveQualifier(removed, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                    if (getPublication() != null){
                        getPublication().getIdentifiers().remove(removed);
                    }
                    else {
                        ((ExperimentXrefList)getXrefs()).removeOnly(removed);
                    }
                }
                else {
                    ((ExperimentXrefList)getXrefs()).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((ExperimentXrefList)getXrefs()).retainAllOnly(primary);
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

            if (getHostOrganism() == null){
                setHostOrganismOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Organism removed) {

            if (isEmpty()){
                setHostOrganismOnly(null);
            }
            else if (getHostOrganism() != null && removed.equals(getHostOrganism())){
                setHostOrganism(iterator().next());
            }
        }

        @Override
        protected void clearProperties() {

            setHostOrganism(null);
        }
    }

    protected class ExperimentAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public ExperimentAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((ExperimentXmlAnnotationList)getAttributes()).addOnly((Attribute) added);
            }
            else {
                ((ExperimentXmlAnnotationList)getAttributes()).addOnly(new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue()));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((ExperimentXmlAnnotationList)getAttributes()).removeOnly(removed);
            }
            else {
                ((ExperimentXmlAnnotationList)getAttributes()).removeOnly(new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue()));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ExperimentXmlAnnotationList)getAttributes()).clearOnly();
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
                    AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.AUTHOR_MI, Annotation.AUTHOR) || AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.CONTACT_EMAIL_MI, Annotation.CONTACT_EMAIL)){
                getPublication().getAttributes().add(added);
            }
            else {
                // we added a annotation, needs to add it in annotations
                ((ExperimentAnnotationList)getAnnotations()).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            if (AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE) ||
                    AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL) ||
                    AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR) ||
                    AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.AUTHOR_MI, Annotation.AUTHOR) || AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.CONTACT_EMAIL_MI, Annotation.CONTACT_EMAIL)){
                getPublication().getAttributes().remove(removed);
            }
            else {
                // we removed a annotation, needs to remove it in annotations
                ((ExperimentAnnotationList)getAnnotations()).removeOnly(removed);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((ExperimentAnnotationList)getAnnotations()).clearOnly();
        }
    }
}
