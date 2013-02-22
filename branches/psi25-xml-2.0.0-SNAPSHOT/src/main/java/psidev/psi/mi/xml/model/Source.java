/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.PublicationCloner;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *         &lt;element name="bibref" type="{net:sf:psidev:mi}bibrefType" minOccurs="0"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="release">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="releaseDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Source extends DefaultSource implements NamesContainer, XrefContainer, AttributeContainer {

    private Names names = new SourceNames();

    private Xref xref;

    private Collection<Attribute> attributes=new SourceXmlAnnotationList();

    private String release;

    private Date releaseDate;

    private final static String UNKNOWN="unknown";
    private final static String POSTAL_ADDRESS="postalAddress";

    ///////////////////////////
    // Constructors

    public Source() {
        super(UNKNOWN);
    }

    @Override
    protected void initializeSynonyms() {
        this.synonyms = new SourceAliasList();
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new SourceXrefList();
    }

    @Override
    protected void initializeIdentifiers() {
        this.identifiers = new SourceIdentifierList();
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new SourceAnnotationList();
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
                names = new SourceNames();
            }
            else {
                synonyms.clear();
            }
            this.names.setShortLabel(value.getShortLabel());
            this.names.setFullName(value.getFullName());
            this.names.getAliases().addAll(value.getAliases());
        }
        else if (this.names != null) {
            synonyms.clear();
            this.shortName = UNKNOWN;
            this.fullName = null;
            this.names = null;
        }
    }

    /**
     * Check if the optional bibref is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBibref() {
        return bibRef != null;
    }

    /**
     * Gets the value of the bibref property.
     *
     * @return possible object is {@link Bibref }
     */
    public Bibref getBibref() {
        return (Bibref)bibRef;
    }

    /**
     * Sets the value of the bibref property.
     *
     * @param value allowed object is {@link Bibref }
     */
    public void setBibref( Bibref value ) {
        this.bibRef = value;
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
            this.xref = new SourceXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            identifiers.clear();
            xrefs.clear();
            this.xref = null;
        }
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
        if ( attributes == null ) {
            attributes = new ArrayList<Attribute>();
        }
        return attributes;
    }

    /**
     * Check if the optional release is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasRelease() {
        return release != null;
    }

    /**
     * Gets the value of the release property.
     *
     * @return possible object is {@link String }
     */
    public String getRelease() {
        return release;
    }

    /**
     * Sets the value of the release property.
     *
     * @param value allowed object is {@link String }
     */
    public void setRelease( String value ) {
        this.release = value;
    }

    /**
     * Check if the optional releaseDate is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasReleaseDate() {
        return releaseDate != null;
    }

    /**
     * Gets the value of the releaseDate property.
     *
     * @return possible object is {@link Date }
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the value of the releaseDate property.
     *
     * @param value allowed object is {@link Date }
     */
    public void setReleaseDate( Date value ) {
        this.releaseDate = value;
    }

    protected void processAddedAnnotationEvent(Annotation added) {
        if (url == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.URL_MI, Annotation.URL)){
            url = added;
        }
        else if (postalAddress == null && added.getValue() != null && AnnotationUtils.doesAnnotationHaveTopic(added, null, POSTAL_ADDRESS)){
            postalAddress = added.getValue();
        }
    }

    protected void processRemovedAnnotationEvent(Annotation removed) {
        if (url != null && url.equals(removed)){
            url = null;
        }
        else if (postalAddress != null && removed.getValue() != null && POSTAL_ADDRESS.equalsIgnoreCase(removed.getTopic().getShortName().toLowerCase())
                && postalAddress.equals(removed.getValue())){
            postalAddress = null;
        }
    }

    protected void clearPropertiesLinkedToAnnotations() {
        this.url = null;
        this.postalAddress=null;
    }

    /////////////////////
    // Object override

    @Override
    public void setShortName(String name) {
        if (names != null){
            names.setShortLabel(name);
        }
        else if (name != null) {
            names = new SourceNames();
            names.setShortLabel(name);
        }
        else {
            names = new SourceNames();
            names.setShortLabel(UNKNOWN);
        }
    }

    @Override
    public void setFullName(String name) {
        if (names != null){
            if (names.getShortLabel().equals(UNKNOWN)){
                names.setShortLabel(name);
            }
            names.setFullName(name);
        }
        else if (name != null) {
            names = new SourceNames();
            names.setShortLabel(name);
            names.setFullName(name);
        }
        else {
            names = new SourceNames();
            names.setShortLabel(UNKNOWN);
            names.setFullName(name);
        }
    }

    @Override
    public void setPublication(Publication ref) {
        if (ref == null){
            this.bibRef = null;
        }
        else if (ref instanceof Bibref){
            this.bibRef = ref;
        }
        else {
            this.bibRef = new Bibref();
            PublicationCloner.copyAndOverridePublicationProperties(ref, bibRef);
        }
    }

    @Override
    public void setMIIdentifier(String mi) {
        // add new mi if not null
        if (mi != null){
            // first remove old psi mi if not null
            if (this.miIdentifier != null){
                identifiers.remove(this.miIdentifier);
            }
            this.miIdentifier = new DbReference(CvTerm.PSI_MI, CvTerm.PSI_MI_MI, mi, psidev.psi.mi.jami.model.Xref.IDENTITY, psidev.psi.mi.jami.model.Xref.IDENTITY_MI);
            this.identifiers.add(this.miIdentifier);
        }
        // remove all mi if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
            this.miIdentifier = null;
        }
    }

    @Override
    public void setMODIdentifier(String mod) {
        // add new mod if not null
        if (mod != null){
            // first remove old psi mod if not null
            if (this.modIdentifier != null){
                identifiers.remove(this.modIdentifier);
            }
            this.modIdentifier = new DbReference(CvTerm.PSI_MOD, CvTerm.PSI_MOD_MI, mod, psidev.psi.mi.jami.model.Xref.IDENTITY, psidev.psi.mi.jami.model.Xref.IDENTITY_MI);
            this.identifiers.add(this.modIdentifier);
        }
        // remove all mod if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
            this.modIdentifier = null;
        }
    }

    @Override
    public void setPostalAddress(String address) {
        if (postalAddress != null){
            if (this.postalAddress != null){
                annotations.remove(this.url);
            }
            this.postalAddress = address;
            this.annotations.add(new Attribute(POSTAL_ADDRESS, address));
        }
        else if (!this.annotations.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(annotations, null, POSTAL_ADDRESS);
            this.postalAddress = null;
        }
    }

    protected void processAddedIdentifier(psidev.psi.mi.jami.model.Xref added){
        // the added identifier is psi-mi and it is not the current mi identifier
        if (miIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MI_MI, CvTerm.PSI_MI)){
            // the current psi-mi identifier is not identity, we may want to set miIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                // the miidentifier is not set, we can set the miidentifier
                if (miIdentifier == null){
                    miIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                    miIdentifier = added;
                }
                // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(miIdentifier, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)){
                    miIdentifier = added;
                }
            }
        }
        // the added identifier is psi-mod and it is not the current mod identifier
        else if (modIdentifier != added && XrefUtils.isXrefFromDatabase(added, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD)){
            // the current psi-mod identifier is not identity, we may want to set modIdentifier
            if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                // the modIdentifier is not set, we can set the modIdentifier
                if (modIdentifier == null){
                    modIdentifier = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                    modIdentifier = added;
                }
                // the added xref is secondary object and the current mi is not a secondary object, we reset miidentifier
                else if (!XrefUtils.doesXrefHaveQualifier(modIdentifier, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)){
                    modIdentifier = added;
                }
            }
        }
    }

    protected void processRemovedIdentifier(psidev.psi.mi.jami.model.Xref removed){
        // the removed identifier is psi-mi
        if (miIdentifier != null && miIdentifier.equals(removed)){
            miIdentifier = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, CvTerm.PSI_MI_MI, CvTerm.PSI_MI);
        }
        // the removed identifier is psi-mod
        else if (modIdentifier != null && modIdentifier.equals(removed)){
            modIdentifier = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, CvTerm.PSI_MOD_MI, CvTerm.PSI_MOD);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers(){
        miIdentifier = null;
        modIdentifier = null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Source" );
        sb.append( "{names=" ).append( names );
        sb.append( ", bibref=" ).append( bibRef );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( ", release='" ).append( release ).append( '\'' );
        sb.append( ", releaseDate=" ).append( releaseDate );
        sb.append( '}' );
        return sb.toString();
    }

    //TODO equals and hashCode
    protected class SourceNames extends Names{

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
                shortName = UNKNOWN;
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
                ((SourceAliasList) synonyms).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((SourceAliasList)synonyms).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((SourceAliasList)synonyms).clearOnly();
            }
        }
    }

    private class SourceAliasList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Alias> {
        public SourceAliasList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Alias added) {

            if (names != null){
                SourceNames name = (SourceNames) names;

                if (added instanceof Alias){
                    ((SourceNames.AliasList) name.getAliases()).addOnly((Alias) added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    ((SourceNames.AliasList) name.getAliases()).addOnly(fixedAlias);
                }
            }
            else {
                if (added instanceof Alias){
                    names = new SourceNames();
                    names.setShortLabel(UNKNOWN);
                    ((SourceNames.AliasList) names.getAliases()).addOnly((Alias) added);
                }
                else {
                    Alias fixedAlias = new Alias(added.getName(), added.getType() != null ? added.getType().getShortName() : null, added.getType() != null ? added.getType().getMIIdentifier() : null);

                    names = new SourceNames();
                    names.setShortLabel(UNKNOWN);
                    ((SourceNames.AliasList) names.getAliases()).addOnly((Alias) fixedAlias);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Alias removed) {

            if (names != null){
                SourceNames name = (SourceNames) names;

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
                SourceNames name = (SourceNames) names;
                name.extendedAliases.clearOnly();
            }
        }
    }

    protected class SourceXref extends Xref{

        protected boolean isPrimaryAnIdentity = false;
        protected SecondaryRefList extendedSecondaryRefList = new SecondaryRefList();

        public SourceXref() {
            super();
        }

        public SourceXref(DbReference primaryRef) {
            super(primaryRef);
        }

        public SourceXref(DbReference primaryRef, Collection<DbReference> secondaryRef) {
            super(primaryRef);

            if (secondaryRef != null && !secondaryRef.isEmpty()){
                secondaryRef.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((SourceIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((SourceXrefList)xrefs).addOnly(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((SourceIdentifierList)identifiers).removeOnly(getPrimaryRef());
                processRemovedIdentifier(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((SourceIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((SourceXrefList)xrefs).addOnly(value);
                    }
                }
            }
            else {
                ((SourceXrefList)xrefs).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((SourceIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((SourceXrefList)xrefs).addOnly(value);
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
                    ((SourceIdentifierList)identifiers).addOnly(added);
                    processAddedIdentifier(added);
                }
                else {
                    ((SourceXrefList)xrefs).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((SourceIdentifierList)identifiers).removeOnly(removed);
                    processRemovedIdentifier(removed);
                }
                else {
                    ((SourceXrefList)xrefs).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((SourceIdentifierList)identifiers).retainAllOnly(primary);
                clearPropertiesLinkedToIdentifiers();
                ((SourceXrefList)xrefs).retainAllOnly(primary);
            }
        }
    }

    private class SourceIdentifierList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public SourceIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                SourceXref reference = (SourceXref) xref;

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
                    xref = new SourceXref();
                    ((SourceXref) xref).setPrimaryRefOnly((DbReference) added);
                    processAddedIdentifier(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new SourceXref();
                    ((SourceXref) xref).setPrimaryRefOnly(fixedRef);
                    processAddedIdentifier(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                SourceXref reference = (SourceXref) xref;

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
                SourceXref reference = (SourceXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(xrefs);

                if (reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    private class SourceXrefList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public SourceXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                SourceXref reference = (SourceXref) xref;

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
                    xref = new SourceXref();
                    ((SourceXref) xref).setPrimaryRefOnly((DbReference) added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new SourceXref();
                    ((SourceXref) xref).setPrimaryRefOnly(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (xref != null){
                SourceXref reference = (SourceXref) xref;

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
                SourceXref reference = (SourceXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(identifiers);

                if (!reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    protected class SourceAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public SourceAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((SourceXmlAnnotationList)attributes).addOnly((Attribute) added);
                processAddedAnnotationEvent(added);
            }
            else {
                Attribute att = new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue());
                ((SourceXmlAnnotationList)attributes).addOnly(att);
                processAddedAnnotationEvent(att);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((SourceXmlAnnotationList)attributes).removeOnly(removed);
                processRemovedAnnotationEvent(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((SourceXmlAnnotationList)attributes).removeOnly(att);
                processRemovedAnnotationEvent(att);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((SourceXmlAnnotationList)attributes).clearOnly();
            clearPropertiesLinkedToAnnotations();
        }
    }

    protected class SourceXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public SourceXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {

            // we added a annotation, needs to add it in annotations
            ((SourceAnnotationList)annotations).addOnly(added);
            processAddedAnnotationEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            // we removed a annotation, needs to remove it in annotations
            ((SourceAnnotationList)annotations).removeOnly(removed);
            processRemovedAnnotationEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((SourceAnnotationList)annotations).clearOnly();
            clearPropertiesLinkedToAnnotations();
        }
    }
}