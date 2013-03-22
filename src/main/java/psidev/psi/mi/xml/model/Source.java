/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package psidev.psi.mi.xml.model;


import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Annotation;
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

public class Source extends DefaultSource implements NamesContainer, XrefContainer, AttributeContainer, FileSourceContext {

    private Names names = new SourceNames();

    private Xref xref;

    private Collection<Attribute> attributes;

    private String release;

    private Date releaseDate;

    private final static String UNKNOWN="unknown";
    private final static String POSTAL_ADDRESS="postalAddress";

    private FileSourceLocator locator;

    ///////////////////////////
    // Constructors

    public Source() {
        super(UNKNOWN);
    }

    @Override
    protected void initialiseSynonyms() {
        initialiseSynonymsWith(new SourceAliasList());
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new SourceXrefList());
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new SourceIdentifierList());
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new SourceAnnotationList());
    }

    ///////////////////////////
    // Getters and Setters

    public FileSourceLocator getSourceLocator() {
        return this.locator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
        this.locator = locator;
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
            if (names == null){
                names = new SourceNames();
            }
            else {
                getSynonyms().clear();
            }
            super.setShortName(value.getShortLabel() != null ? value.getShortLabel() : UNKNOWN);
            super.setFullName(value.getFullName());
            getSynonyms().addAll(value.getAliases());
        }
        else if (this.names != null) {
            getSynonyms().clear();
            super.setShortName(UNKNOWN);
            super.setFullName(null);
            this.names = null;
        }
    }

    /**
     * Check if the optional bibref is defined.
     *
     * @return true if defined, false otherwise.
     */
    public boolean hasBibref() {
        return getPublication() != null;
    }

    /**
     * Gets the value of the bibref property.
     *
     * @return possible object is {@link Bibref }
     */
    public Bibref getBibref() {
        return (Bibref)getPublication();
    }

    /**
     * Sets the value of the bibref property.
     *
     * @param value allowed object is {@link Bibref }
     */
    public void setBibref( Bibref value ) {
        super.setPublication(value);
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
            this.xref = new SourceXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            getIdentifiers().clear();
            getXrefs().clear();
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
            attributes=new SourceXmlAnnotationList();
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

    /////////////////////
    // Object override

    @Override
    public void setShortName(String name) {
        if (names != null){
            super.setShortName(name);
        }
        else if (name != null) {
            names = new SourceNames();
            super.setShortName(name);
        }
        else {
            names = new SourceNames();
            super.setShortName(UNKNOWN);
        }
    }

    @Override
    public void setFullName(String name) {
        if (names != null){
            if (getShortName().equals(UNKNOWN)){
                super.setShortName(name != null ? name : UNKNOWN);
            }
            super.setFullName(name);
        }
        else if (name != null) {
            names = new SourceNames();
            super.setShortName(name);
            super.setFullName(name);
        }
        else {
            names = new SourceNames();
            super.setShortName(UNKNOWN);
            super.setFullName(name);
        }
    }

    protected void setShortNameOnly(String name) {
        super.setShortName(name != null ? name : UNKNOWN);

    }

    protected void setFullNameOnly(String name) {
        super.setFullName(name);
    }

    protected String getSourceFullName(){
        return super.getFullName();
    }

    @Override
    public void setPublication(Publication ref) {
        if (ref == null){
            super.setPublication(null);
        }
        else if (ref instanceof Bibref){
            super.setPublication(ref);
        }
        else {
            Bibref bibRef = new Bibref();
            PublicationCloner.copyAndOverridePublicationProperties(ref, bibRef);
            super.setPublication(bibRef);
        }
    }

    @Override
    public void setPostalAddress(String address) {
        if (getPostalAddress() != null){
            if (getPostalAddress() != null){
                getAnnotations().remove(new Attribute(POSTAL_ADDRESS, address));
            }
            super.setPostalAddress(address);
            getAnnotations().add(new Attribute(POSTAL_ADDRESS, address));
        }
        else if (!getAnnotations().isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(getAnnotations(), null, POSTAL_ADDRESS);
            super.setPostalAddress(null);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Source" );
        sb.append( "{names=" ).append( names );
        sb.append( ", bibref=" ).append( getPublication() );
        sb.append( ", xref=" ).append( xref );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( ", release='" ).append( release ).append( '\'' );
        sb.append( ", releaseDate=" ).append( releaseDate );
        sb.append( '}' );
        return sb.toString();
    }

    //TODO equals and hashCode
    private class SourceNames extends Names{

        protected AliasList extendedAliases = new AliasList();

        public String getShortLabel() {
            return getShortName();
        }

        public boolean hasShortLabel() {
            return getShortName() != null;
        }

        public void setShortLabel( String value ) {
            if (value != null){
                setShortNameOnly(value);
            }
            else {
                setShortNameOnly(UNKNOWN);
            }
        }

        public String getFullName() {
            return getSourceFullName();
        }

        public boolean hasFullName() {
            return getSourceFullName() != null;
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
            sb.append( ", fullName='" ).append( getSourceFullName() ).append( '\'' );
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
            if ( getSourceFullName() != null ? !getSourceFullName().equals( names.getFullName() ) : names.getFullName() != null ) return false;
            if ( getShortName() != null ? !getShortName() .equals( names.getShortLabel() ) : names.getShortLabel() != null ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            result = ( getShortName()  != null ? getShortName() .hashCode() : 0 );
            result = 31 * result + ( getSourceFullName() != null ? getSourceFullName().hashCode() : 0 );
            result = 31 * result + ( extendedAliases != null ? extendedAliases.hashCode() : 0 );
            return result;
        }

        protected class AliasList extends AbstractListHavingPoperties<Alias> {

            @Override
            protected void processAddedObjectEvent(Alias added) {
                ((SourceAliasList) getSynonyms()).addOnly(added);
            }

            @Override
            protected void processRemovedObjectEvent(Alias removed) {
                ((SourceAliasList)getSynonyms()).removeOnly(removed);
            }

            @Override
            protected void clearProperties() {
                ((SourceAliasList)getSynonyms()).clearOnly();
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

    private class SourceXref extends Xref{

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
                extendedSecondaryRefList.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((SourceIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((SourceXrefList)getXrefs()).addOnly(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((SourceIdentifierList)getIdentifiers()).removeOnly(getPrimaryRef());
                processRemovedIdentifierEvent(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((SourceIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((SourceXrefList)getXrefs()).addOnly(value);
                    }
                }
            }
            else {
                ((SourceXrefList)getXrefs()).removeOnly(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((SourceIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((SourceXrefList)getXrefs()).addOnly(value);
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
                    ((SourceIdentifierList)getIdentifiers()).addOnly(added);
                    processAddedIdentifierEvent(added);
                }
                else {
                    ((SourceXrefList)getXrefs()).addOnly(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((SourceIdentifierList)getIdentifiers()).removeOnly(removed);
                    processRemovedIdentifierEvent(removed);
                }
                else {
                    ((SourceXrefList)getXrefs()).removeOnly(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((SourceIdentifierList)getIdentifiers()).retainAllOnly(primary);
                clearPropertiesLinkedToIdentifiers();
                ((SourceXrefList)getXrefs()).retainAllOnly(primary);
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
                    xref = new SourceXref();
                    ((SourceXref) xref).setPrimaryRefOnly((DbReference) added);
                    processAddedIdentifierEvent(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new SourceXref();
                    ((SourceXref) xref).setPrimaryRefOnly(fixedRef);
                    processAddedIdentifierEvent(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                SourceXref reference = (SourceXref) xref;

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
                SourceXref reference = (SourceXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(getXrefs());

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
                reference.extendedSecondaryRefList.retainAllOnly(getIdentifiers());

                if (!reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    private class SourceAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public SourceAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((SourceXmlAnnotationList)getAttributes()).addOnly((Attribute) added);
                processAddedAnnotationEvent(added);
            }
            else {
                Attribute att = new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue());
                ((SourceXmlAnnotationList)getAttributes()).addOnly(att);
                processAddedAnnotationEvent(att);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((SourceXmlAnnotationList)getAttributes()).removeOnly(removed);
                processRemovedAnnotationEvent(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((SourceXmlAnnotationList)getAttributes()).removeOnly(att);
                processRemovedAnnotationEvent(att);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((SourceXmlAnnotationList)getAttributes()).clearOnly();
            clearPropertiesLinkedToAnnotations();
        }
    }

    private class SourceXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public SourceXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {

            // we added a annotation, needs to add it in annotations
            ((SourceAnnotationList)getAnnotations()).addOnly(added);
            processAddedAnnotationEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            // we removed a annotation, needs to remove it in annotations
            ((SourceAnnotationList)getAnnotations()).removeOnly(removed);
            processRemovedAnnotationEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((SourceAnnotationList)getAnnotations()).clearOnly();
            clearPropertiesLinkedToAnnotations();
        }
    }
}