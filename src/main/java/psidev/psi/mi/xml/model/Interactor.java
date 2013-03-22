/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */


package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.CvTermCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * Describes a molecular interactor.
 * <p/>
 * <p>Java class for interactorElementType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="interactorElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="interactorType" type="{net:sf:psidev:mi}cvType"/>
 *         &lt;element name="organism" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{net:sf:psidev:mi}bioSourceType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="sequence" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Interactor extends DefaultInteractor implements Polymer, HasId, NamesContainer, XrefContainer, AttributeContainer, FileSourceContext {

    private Names names = new InteractorNames();

    private Xref xref;

    private String sequence;

    private Collection<Attribute> attributes;

    private int id;

    private final static String UNSPECIFIED = "unspecified";

    private PsiXmlFileLocator locator;

    ///////////////////////////
    // Constructors

    public Interactor() {
        super(UNSPECIFIED, new InteractorType());
        getType().setShortName(Interactor.UNKNOWN_INTERACTOR);
        getType().setMIIdentifier(Interactor.UNKNOWN_INTERACTOR_MI);
    }

    @Override
    protected void initialiseAliases() {
        initialiseAliasesWith(new InteractorAliasList());
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new InteractorXrefList());
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new InteractorIdentifierList());
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new InteractorAnnotationList());
    }

    @Override
    protected void initialiseChecksums() {
        initialiseChecksumsWith(new InteractorChecksumList());
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

    public PsiXmlFileLocator getSourceLocator() {
        return this.locator;
    }

    public void setSourceLocator(PsiXmlFileLocator locator) {
        this.locator = locator;
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
                names = new InteractorNames();
            }
            else {
                getAliases().clear();
            }
            super.setShortName(value.getShortLabel() != null ? value.getShortLabel() : UNSPECIFIED);
            super.setFullName(value.getFullName());
            getAliases().addAll(value.getAliases());
        }
        else if (names != null) {
            getAliases().clear();
            super.setShortName(UNSPECIFIED);
            super.setFullName(null);
            this.names = null;
        }
        else {
            this.names = new InteractorNames();
            super.setShortName(UNSPECIFIED);
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
            this.xref = new InteractorXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            getIdentifiers().clear();
            getXrefs().clear();
            this.xref = null;
        }
    }

    /**
     * Gets the value of the interactorType property.
     *
     * @return possible object is {@link CvType }
     */
    public InteractorType getInteractorType() {
        return (InteractorType) getType();
    }

    /**
     * Sets the value of the interactorType property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setInteractorType( InteractorType value ) {
        if (value == null){
            super.setType(new InteractorType());
            getType().setShortName(UNKNOWN_INTERACTOR);
            getType().setMIIdentifier(UNKNOWN_INTERACTOR_MI);
        }
        else {
            super.setType(value);
        }
    }

    /**
     * Check if the optional organism is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasOrganism() {
        return getOrganism() != null;
    }

    /**
     * Gets the value of the organism property.
     *
     * @return possible object is {@link Organism }
     */
    public Organism getOrganism() {
        return (Organism) super.getOrganism();
    }

    /**
     * Sets the value of the organism property.
     *
     * @param value allowed object is {@link Organism }
     */
    public void setOrganism( Organism value ) {
        super.setOrganism(value);
    }

    /**
     * Check if the optional sequence is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasSequence() {
        return sequence != null && sequence.trim().length() > 0;
    }

    /**
     * Gets the value of the sequence property.
     *
     * @return possible object is {@link String }
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * Sets the value of the sequence property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSequence( String value ) {
        this.sequence = value;
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
           attributes = new InteractorXmlAnnotationList();
        }
        return attributes;
    }

    /////////////////////////
    // Object override

    @Override
    public void setShortName(String name) {
        if (names != null){
            super.setShortName(name != null ? name : UNSPECIFIED);
        }
        else if (name != null) {
            names = new InteractorNames();
            super.setShortName(name);
        }
        else {
            names = new InteractorNames();
            super.setShortName(UNSPECIFIED);
        }
    }

    @Override
    public void setFullName(String name) {
        if (names != null){
            if (names.getShortLabel().equals(UNSPECIFIED)){
                super.setShortName(name != null ? name : UNSPECIFIED);
            }
            super.setFullName(name);
        }
        else if (name != null) {
            names = new InteractorNames();
            super.setShortName(name);
            super.setFullName(name);
        }
        else {
            names = new InteractorNames();
            super.setShortName(UNSPECIFIED);
            super.setFullName(name);
        }
    }

    protected void setShortNameOnly(String name) {
        super.setShortName(name != null ? name : UNSPECIFIED);
    }

    protected void setFullNameOnly(String name) {
        super.setFullName(name);
    }

    protected String getInteractorFullName(){
        return super.getFullName();
    }

    protected Collection<psidev.psi.mi.jami.model.Alias> getInteractorAliases(){
        return super.getAliases();
    }

    @Override
    public void setType(CvTerm type) {
        if (type == null){
            super.setType(new InteractorType());
            getType().setShortName(Interactor.UNKNOWN_INTERACTOR);
            getType().setMIIdentifier(Interactor.UNKNOWN_INTERACTOR_MI);
        }
        else if (type instanceof InteractorType){
            super.setType(type);
        }
        else {
            super.setType(new InteractorType());
            CvTermCloner.copyAndOverrideCvTermProperties(type, getType());
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Interactor" );
        sb.append( "{ id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", interactorType=" ).append( getType() );
        sb.append( ", organism=" ).append( getOrganism() );
        sb.append( ", sequence='" ).append( sequence ).append( '\'' );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Interactor that = ( Interactor ) o;

        if ( getType() != null ? !getType().equals(that.getType()) : that.getType() != null )
            return false;
        if ( getOrganism() != null ? !getOrganism().equals(that.getOrganism()) : that.getOrganism() != null ) return false;
        //if (names != null ? !names.equals(that.names) : that.names != null) return false;
        if ( xref != null ? !xref.equals( that.xref ) : that.xref != null ) return false;
        if ( sequence != null ? !sequence.equals( that.sequence ) : that.sequence != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31;
        //result = (names != null ? names.hashCode() : 0);
        result = 31 * result + ( xref != null ? xref.hashCode() : 0 );
        result = 31 * result + ( getType() != null ? getType().hashCode() : 0 );
        result = 31 * result + ( getOrganism() != null ? getOrganism().hashCode() : 0 );
        result = 31 * result + ( sequence != null ? sequence.hashCode() : 0 );
        return result;
    }

    protected class InteractorNames extends Names{

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

        public String getFullName() {
            return getInteractorFullName();
        }

        public boolean hasFullName() {
            return getInteractorFullName() != null;
        }

        public void setFullName( String value ) {
            setFullNameOnly(value);
        }

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
            sb.append( "{shortLabel='" ).append( getShortName() ).append( '\'' );
            sb.append( ", fullName='" ).append( getInteractorFullName() ).append( '\'' );
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
            if ( getInteractorFullName() != null ? !getInteractorFullName().equals(names.getFullName()) : names.getFullName() != null ) return false;
            if ( getShortName() != null ? !getShortName().equals(names.getShortLabel()) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( getShortName() != null ? getShortName().hashCode() : 0 );
            result = 31 * result + ( getFullName() != null ? getFullName().hashCode() : 0 );
            result = 31 * result + ( extendedAliases != null ? extendedAliases.hashCode() : 0 );
            return result;
        }

        protected class AliasList extends AbstractListHavingPoperties<Alias> {

            @Override
            protected void processAddedObjectEvent(Alias added) {
                ((InteractorAliasList) getInteractorAliases()).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((InteractorAliasList)getInteractorAliases()).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((InteractorAliasList)getInteractorAliases()).clearOnly();
            }
        }
    }

    private class InteractorAliasList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Alias> {
        public InteractorAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Alias added) {

            if (names != null){
                InteractorNames name = (InteractorNames) names;

                if (added instanceof Alias){
                    ((InteractorNames.AliasList) name.getAliases()).addOnly((Alias) added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    ((InteractorNames.AliasList) name.getAliases()).addOnly(fixedAlias);
                }
            }
            else {
                if (added instanceof Alias){
                    names = new InteractorNames();
                    names.setShortLabel(UNSPECIFIED);
                    ((InteractorNames.AliasList) names.getAliases()).addOnly((Alias) added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    names = new InteractorNames();
                    names.setShortLabel(UNSPECIFIED);
                    ((InteractorNames.AliasList) names.getAliases()).addOnly((Alias) fixedAlias);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Alias removed) {

            if (names != null){
                InteractorNames name = (InteractorNames) names;

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
                InteractorNames name = (InteractorNames) names;
                name.extendedAliases.clearOnly();
            }
        }
    }

    protected class InteractorXref extends Xref{

        protected boolean isPrimaryAnIdentity = false;
        protected SecondaryRefList extendedSecondaryRefList = new SecondaryRefList();

        public InteractorXref() {
            super();
        }

        public InteractorXref(DbReference primaryRef) {
            super(primaryRef);
        }

        public InteractorXref(DbReference primaryRef, Collection<DbReference> secondaryRef) {
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
                        ((InteractorIdentifierList)getIdentifiers()).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractorXrefList)getXrefs()).addOnly(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((InteractorIdentifierList)getIdentifiers()).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractorIdentifierList)getIdentifiers()).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractorXrefList)getXrefs()).addOnly(value);
                    }
                }
            }
            else {
                ((InteractorXrefList)getXrefs()).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractorIdentifierList)getIdentifiers()).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractorXrefList)getXrefs()).addOnly(value);
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
                    ((InteractorIdentifierList)getIdentifiers()).addOnly(added);
                }
                else {
                    ((InteractorXrefList)getXrefs()).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((InteractorIdentifierList)getIdentifiers()).removeOnly(removed);
                }
                else {
                    ((InteractorXrefList)getXrefs()).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((InteractorIdentifierList)getIdentifiers()).retainAllOnly(primary);
                ((InteractorXrefList)getXrefs()).retainAllOnly(primary);
            }
        }
    }

    private class InteractorIdentifierList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public InteractorIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                InteractorXref reference = (InteractorXref) xref;

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
                    xref = new InteractorXref();
                    ((InteractorXref) xref).setPrimaryRefOnly((DbReference) added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new InteractorXref();
                    ((InteractorXref) xref).setPrimaryRefOnly(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                InteractorXref reference = (InteractorXref) xref;

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
                InteractorXref reference = (InteractorXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(getXrefs());

                if (reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    private class InteractorXrefList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public InteractorXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                InteractorXref reference = (InteractorXref) xref;

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
                    xref = new InteractorXref();
                    ((InteractorXref) xref).setPrimaryRefOnly((DbReference) added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new InteractorXref(fixedRef);
                    ((InteractorXref) xref).setPrimaryRefOnly(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (xref != null){
                InteractorXref reference = (InteractorXref) xref;

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
                InteractorXref reference = (InteractorXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(getIdentifiers());

                if (!reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    protected class InteractorAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public InteractorAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((InteractorXmlAnnotationList)getAttributes()).addOnly((Attribute) added);
            }
            else {
                ((InteractorXmlAnnotationList)getAttributes()).addOnly(new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue()));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((InteractorXmlAnnotationList)getAttributes()).removeOnly(removed);
            }
            else {
                ((InteractorXmlAnnotationList)getAttributes()).removeOnly(new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue()));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorXmlAnnotationList)getAttributes()).clearOnly();
        }
    }

    protected class InteractorXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public InteractorXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {
            // we have a checksum
            if (added.getNameAc() != null
                    && (added.getNameAc().equals(Checksum.ROGID_MI)
                    || added.getNameAc().equals(Checksum.INCHI_KEY_MI)
                    || added.getNameAc().equals(Checksum.INCHI_MI))
                    || added.getNameAc().equals(Checksum.SMILE_MI)
                    || added.getNameAc().equals("MI:1212")
                    || added.getNameAc().equals("MI:0970")){
                 ((InteractorChecksumList) getChecksums()).addOnly(new DefaultChecksum(new DefaultCvTerm(added.getName(), added.getNameAc()), added.getValue()));
            }
            else if (added.getNameAc() == null
                    && (added.getName().equals(Checksum.ROGID)
                    || added.getName().equals(Checksum.INCHI_KEY)
                    || added.getName().equals(Checksum.INCHI))
                    || added.getName().equals(Checksum.SMILE)
                    || added.getName().equals("checksum")
                    || added.getName().equals("inchi key")){
                ((InteractorChecksumList) getChecksums()).addOnly(new DefaultChecksum(new DefaultCvTerm(added.getName()), added.getValue()));
            }
            else {
                // we added a annotation, needs to add it in annotations
                ((InteractorAnnotationList)getAnnotations()).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            if (removed.getNameAc() != null
                    && (removed.getNameAc().equals(Checksum.ROGID_MI)
                    || removed.getNameAc().equals(Checksum.INCHI_KEY_MI)
                    || removed.getNameAc().equals(Checksum.INCHI_MI))
                    || removed.getNameAc().equals(Checksum.SMILE_MI)
                    || removed.getNameAc().equals("MI:1212")
                    || removed.getNameAc().equals("MI:0970")){
                ((InteractorChecksumList) getChecksums()).removeOnly(new DefaultChecksum(new DefaultCvTerm(removed.getName(), removed.getNameAc()), removed.getValue()));
            }
            else if (removed.getNameAc() == null
                    && (removed.getName().equals(Checksum.ROGID)
                    || removed.getName().equals(Checksum.INCHI_KEY)
                    || removed.getName().equals(Checksum.INCHI))
                    || removed.getName().equals(Checksum.SMILE)
                    || removed.getName().equals("checksum")
                    || removed.getName().equals("inchi key")){
                ((InteractorChecksumList) getChecksums()).removeOnly(new DefaultChecksum(new DefaultCvTerm(removed.getName()), removed.getValue()));
            }
            else {
                // we removed a annotation, needs to remove it in annotations
                ((InteractorAnnotationList)getAnnotations()).removeOnly(removed);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorAnnotationList)getAnnotations()).clearOnly();
            ((InteractorChecksumList)getChecksums()).clearOnly();
        }
    }

    private class InteractorChecksumList extends AbstractListHavingPoperties<Checksum> {
        public InteractorChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Checksum added) {

            ((InteractorXmlAnnotationList)getAttributes()).addOnly(new Attribute(added.getMethod().getMIIdentifier(), added.getMethod().getShortName(), added.getValue()));
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Checksum removed) {
            ((InteractorXmlAnnotationList)getAttributes()).removeOnly(new Attribute(removed.getMethod().getMIIdentifier(), removed.getMethod().getShortName(), removed.getValue()));
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorXmlAnnotationList)getAttributes()).clearOnly();
        }
    }
}