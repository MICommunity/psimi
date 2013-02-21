/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */


package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultInteractor;
import psidev.psi.mi.jami.utils.XrefUtils;
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

public class Interactor extends DefaultInteractor implements HasId, NamesContainer, XrefContainer, AttributeContainer {

    private Names names = new InteractorNames();

    private Xref xref;

    private String sequence;

    private Collection<Attribute> attributes = new InteractorXmlAnnotationList();

    private int id;

    private final static String UNSPECIFIED = "unspecified";

    ///////////////////////////
    // Constructors

    public Interactor() {
        super(UNSPECIFIED, new InteractorType());
        type.setShortName(Interactor.UNKNOWN_INTERACTOR);
        type.setMIIdentifier(Interactor.UNKNOWN_INTERACTOR_MI);
    }

    @Override
    protected void initializeAliases() {
        this.aliases = new InteractorAliasList();
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new InteractorXrefList();
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new InteractorIdentifierList();
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new InteractorAnnotationList();
    }

    @Override
    protected void initializeChecksums() {
        this.checksums = new InteractorChecksumList();
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
            this.names.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            this.names.getAliases().addAll(value.getAliases());
        }
        else {
            aliases.clear();
            this.shortName = UNSPECIFIED;
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
            this.xref = new InteractorXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else {
            identifiers.clear();
            xrefs.clear();
            this.xref = null;
        }
    }

    /**
     * Gets the value of the interactorType property.
     *
     * @return possible object is {@link CvType }
     */
    public InteractorType getInteractorType() {
        return (InteractorType) type;
    }

    /**
     * Sets the value of the interactorType property.
     *
     * @param value allowed object is {@link CvType }
     */
    public void setInteractorType( InteractorType value ) {
        if (value == null){
            this.type = new InteractorType();
            this.type.setShortName(UNKNOWN_INTERACTOR);
            this.type.setMIIdentifier(UNKNOWN_INTERACTOR_MI);
        }
        else {
            this.type = value;
        }
    }

    /**
     * Check if the optional organism is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasOrganism() {
        return organism != null;
    }

    /**
     * Gets the value of the organism property.
     *
     * @return possible object is {@link Organism }
     */
    public Organism getOrganism() {
        return (Organism) organism;
    }

    /**
     * Sets the value of the organism property.
     *
     * @param value allowed object is {@link Organism }
     */
    public void setOrganism( Organism value ) {
        this.organism = value;
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
        return attributes;
    }

    /////////////////////////
    // Object override

    @Override
    public void setShortName(String name) {
        if (names != null){
            names.setShortLabel(name);
        }
        else {
            names = new InteractorNames();
            names.setShortLabel(name);
        }
    }

    @Override
    public void setFullName(String name) {
        if (names != null){
            if (names.getShortLabel().equals(UNSPECIFIED)){
                names.setShortLabel(name);
            }
            names.setFullName(name);
        }
        else {
            names = new InteractorNames();
            names.setShortLabel(name);
            names.setFullName(name);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Interactor" );
        sb.append( "{ id=" ).append( id );
        sb.append( ", names=" ).append( names );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", interactorType=" ).append( type );
        sb.append( ", organism=" ).append( organism );
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

        if ( type != null ? !type.equals( that.type ) : that.type != null )
            return false;
        if ( organism != null ? !organism.equals( that.organism ) : that.organism != null ) return false;
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
        result = 31 * result + ( type != null ? type.hashCode() : 0 );
        result = 31 * result + ( organism != null ? organism.hashCode() : 0 );
        result = 31 * result + ( sequence != null ? sequence.hashCode() : 0 );
        return result;
    }

    protected class InteractorNames extends Names{

        protected AliasList extendedAliases = new AliasList();

        public String getShortLabel() {
            return shortName;
        }

        public boolean hasShortLabel() {
            return shortName != null;
        }

        public void setShortLabel( String value ) {
            if (value != null){
                shortName = value;
            }
            else {
                shortName = UNSPECIFIED;
            }
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
            sb.append( "{shortLabel='" ).append( shortName ).append( '\'' );
            sb.append( ", fullName='" ).append( fullName ).append( '\'' );
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
            if ( fullName != null ? !fullName.equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( shortName != null ? !shortName.equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( shortName != null ? shortName.hashCode() : 0 );
            result = 31 * result + ( fullName != null ? fullName.hashCode() : 0 );
            result = 31 * result + ( extendedAliases != null ? extendedAliases.hashCode() : 0 );
            return result;
        }

        protected class AliasList extends AbstractListHavingPoperties<Alias> {

            @Override
            protected void processAddedObjectEvent(Alias added) {
                ((InteractorAliasList) aliases).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((InteractorAliasList)aliases).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((InteractorAliasList)aliases).clearOnly();
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
                secondaryRef.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractorIdentifierList)identifiers).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractorXrefList)xrefs).addOnly(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((InteractorIdentifierList)identifiers).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractorIdentifierList)identifiers).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractorXrefList)xrefs).addOnly(value);
                    }
                }
            }
            else {
                ((InteractorXrefList)xrefs).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((InteractorIdentifierList)identifiers).addOnly(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((InteractorXrefList)xrefs).addOnly(value);
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
                    ((InteractorIdentifierList)identifiers).addOnly(added);
                }
                else {
                    ((InteractorXrefList)xrefs).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((InteractorIdentifierList)identifiers).removeOnly(removed);
                }
                else {
                    ((InteractorXrefList)xrefs).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((InteractorIdentifierList)identifiers).retainAllOnly(primary);
                ((InteractorXrefList)xrefs).retainAllOnly(primary);
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
                reference.extendedSecondaryRefList.retainAllOnly(xrefs);

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
                reference.extendedSecondaryRefList.retainAllOnly(identifiers);

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
                ((InteractorXmlAnnotationList)attributes).addOnly((Attribute) added);
            }
            else {
                ((InteractorXmlAnnotationList)attributes).addOnly(new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue()));
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((InteractorXmlAnnotationList)attributes).removeOnly(removed);
            }
            else {
                ((InteractorXmlAnnotationList)attributes).removeOnly(new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue()));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorXmlAnnotationList)attributes).clearOnly();
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
                 ((InteractorChecksumList) checksums).addOnly(new DefaultChecksum(new DefaultCvTerm(added.getName(), added.getNameAc()), added.getValue()));
            }
            else if (added.getNameAc() == null
                    && (added.getName().equals(Checksum.ROGID)
                    || added.getName().equals(Checksum.INCHI_KEY)
                    || added.getName().equals(Checksum.INCHI))
                    || added.getName().equals(Checksum.SMILE)
                    || added.getName().equals("checksum")
                    || added.getName().equals("inchi key")){
                ((InteractorChecksumList) checksums).addOnly(new DefaultChecksum(new DefaultCvTerm(added.getName()), added.getValue()));
            }
            else {
                // we added a annotation, needs to add it in annotations
                ((InteractorAnnotationList)annotations).addOnly(added);
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
                ((InteractorChecksumList) checksums).removeOnly(new DefaultChecksum(new DefaultCvTerm(removed.getName(), removed.getNameAc()), removed.getValue()));
            }
            else if (removed.getNameAc() == null
                    && (removed.getName().equals(Checksum.ROGID)
                    || removed.getName().equals(Checksum.INCHI_KEY)
                    || removed.getName().equals(Checksum.INCHI))
                    || removed.getName().equals(Checksum.SMILE)
                    || removed.getName().equals("checksum")
                    || removed.getName().equals("inchi key")){
                ((InteractorChecksumList) checksums).removeOnly(new DefaultChecksum(new DefaultCvTerm(removed.getName()), removed.getValue()));
            }
            else {
                // we removed a annotation, needs to remove it in annotations
                ((InteractorAnnotationList)annotations).removeOnly(removed);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorAnnotationList)annotations).clearOnly();
        }
    }

    protected class InteractorChecksumList extends AbstractListHavingPoperties<Checksum> {
        public InteractorChecksumList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Checksum added) {

            ((InteractorXmlAnnotationList)attributes).addOnly(new Attribute(added.getMethod().getMIIdentifier(), added.getMethod().getShortName(), added.getValue()));
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Checksum removed) {
            if (removed instanceof Annotation){
                ((InteractorXmlAnnotationList)attributes).removeOnly(removed);
            }
            else {
                ((InteractorXmlAnnotationList)attributes).removeOnly(new Attribute(removed.getMethod().getMIIdentifier(), removed.getMethod().getShortName(), removed.getValue()));
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((InteractorXmlAnnotationList)attributes).clearOnly();
        }
    }
}