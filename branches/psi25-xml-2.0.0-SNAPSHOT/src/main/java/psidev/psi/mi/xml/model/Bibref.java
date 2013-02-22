/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */


package psidev.psi.mi.xml.model;


import org.apache.commons.lang.StringUtils;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Bibliographic reference.
 * <p/>
 * <p>Java class for bibrefType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="bibrefType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType"/>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

public class Bibref extends DefaultPublication implements XrefContainer, AttributeContainer {

    private Xref xref;

    private Collection<Attribute> attributes=new BibRefXmlAnnotationList();

    ///////////////////////////
    // Constructors

    public Bibref() {
        super();
    }

    public Bibref( Xref xref ) {
        super();
        setXref( xref );
    }

    public Bibref( Collection<Attribute> attributes ) {
        super();
        if (attributes != null){
            this.attributes.addAll(attributes);
        }
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new PublicationXrefList();
    }

    @Override
    protected void initialiseIdentifiers() {
        this.identifiers = new PublicationIdentifierList();
    }

    @Override
    protected void initialiseAnnotations() {
        this.annotations = new PublicationAnnotationList();
    }

    @Override
    protected void initializeAuthors() {
        this.authors = new PublicationAuthorList();
    }

    ///////////////////////////
    // Getters and Setters

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
            this.xref = new PublicationXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            identifiers.clear();
            xrefs.clear();
            this.xref = null;
        }
    }

    public boolean hasAttributes() {
        return attributes != null && !attributes.isEmpty();
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
        // the added identifier is pubmed and it is not the current pubmed identifier
        if (pubmedId != added && XrefUtils.isXrefFromDatabase(added, psidev.psi.mi.jami.model.Xref.PUBMED_MI, psidev.psi.mi.jami.model.Xref.PUBMED)){
            // the current pubmed identifier is not identity, we may want to set pubmed Identifier
            if (!XrefUtils.doesXrefHaveQualifier(pubmedId, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                // the pubmed identifier is not set, we can set the pubmed
                if (pubmedId == null){
                    pubmedId = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                    pubmedId = added;
                }
                // the added xref is secondary object and the current pubmed is not a secondary object, we reset pubmed identifier
                else if (!XrefUtils.doesXrefHaveQualifier(pubmedId, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)){
                    pubmedId = added;
                }
            }
        }
        // the added identifier is doi and it is not the current doi identifier
        else if (doi != added && XrefUtils.isXrefFromDatabase(added, psidev.psi.mi.jami.model.Xref.DOI_MI, psidev.psi.mi.jami.model.Xref.DOI)){
            // the current doi identifier is not identity, we may want to set doi
            if (!XrefUtils.doesXrefHaveQualifier(doi, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                // the doi is not set, we can set the doi
                if (doi == null){
                    doi = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.IDENTITY_MI, psidev.psi.mi.jami.model.Xref.IDENTITY)){
                    doi = added;
                }
                // the added xref is secondary object and the current doi is not a secondary object, we reset doi
                else if (!XrefUtils.doesXrefHaveQualifier(doi, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.SECONDARY_MI, psidev.psi.mi.jami.model.Xref.SECONDARY)){
                    doi = added;
                }
            }
        }
    }

    protected void processRemovedIdentifier(psidev.psi.mi.jami.model.Xref removed){
        // the removed identifier is pubmed
        if (pubmedId != null && pubmedId.equals(removed)){
            pubmedId = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, psidev.psi.mi.jami.model.Xref.PUBMED_MI, psidev.psi.mi.jami.model.Xref.PUBMED);
        }
        // the removed identifier is doi
        else if (doi != null && doi.equals(removed)){
            doi = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, psidev.psi.mi.jami.model.Xref.DOI_MI, psidev.psi.mi.jami.model.Xref.DOI);
        }
    }

    protected void clearPropertiesLinkedToIdentifiers(){
        pubmedId = null;
        doi = null;
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

    protected void processAddedAnnotationEvent(Annotation added) {
        if (title == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE)){
            title = added.getValue();
        }
        else if (journal == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
            journal = added.getValue();
        }
        else if (publicationDate == null && added.getValue() != null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            try {
                publicationDate = format.parse(added.getValue());
            } catch (ParseException e) {
                e.printStackTrace();
                publicationDate = null;
            }
        }
        else if (authors.isEmpty() && added.getValue() != null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
            if (added.getValue().contains(",")){
                authors.addAll(Arrays.asList(added.getValue().split(",")));
            }
            else {
                authors.add(added.getValue());
            }
        }
        else {
            ((PublicationAnnotationList)annotations).addOnly(added);
        }
    }

    protected void processRemovedAnnotationEvent(Annotation removed) {
        if (title != null && title.equals(removed.getValue()) && AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE)){
            Annotation titleAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(attributes, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE);
            title = titleAnnot != null ? titleAnnot.getValue() : null;
        }
        else if (journal != null && journal.equals(removed.getValue()) && AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
            Annotation journalAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(attributes, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL);
            journal = journalAnnot != null ? journalAnnot.getValue() : null;
        }
        else if (publicationDate != null && AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy");

            if (format.format(publicationDate).equals(removed.getValue())){
                Annotation dateAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(attributes, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR);
                try {
                    publicationDate = dateAnnot != null ? format.parse(dateAnnot.getValue()) : null;
                } catch (ParseException e) {
                    e.printStackTrace();
                    publicationDate = null;
                }
            }
        }
        else if (!authors.isEmpty() && removed.getValue() != null && AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.AUTHOR_MI, Annotation.AUTHOR)){

            if (removed.getValue().equals(org.apache.commons.lang.StringUtils.join(authors, ","))){
                Annotation authorAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(attributes, Annotation.AUTHOR_MI, Annotation.AUTHOR);
                if (authorAnnot != null){
                    ((PublicationAuthorList)authors).addAllOnly(Arrays.asList(authorAnnot.getValue().split(",")));
                }
                else {
                    ((PublicationAuthorList)authors).clearOnly();
                }
            }
        }
        else {
            ((PublicationAnnotationList)annotations).removeOnly(removed);
        }
    }

    protected void clearPropertiesLinkedToAnnotations() {
        this.title = null;
        this.journal=null;
        this.publicationDate=null;
        ((PublicationAuthorList)authors).clearOnly();
        ((PublicationAnnotationList)annotations).clearOnly();
    }

    /////////////////////////
    // Object override

    @Override
    public void setPubmedId(String pubmedId) {
        // add new pubmed if not null
        if (pubmedId != null){
            // first remove old pubmed if not null
            if (this.pubmedId != null){
                identifiers.remove(this.pubmedId);
            }
            this.pubmedId = new DbReference(psidev.psi.mi.jami.model.Xref.PUBMED, psidev.psi.mi.jami.model.Xref.PUBMED_MI, pubmedId, psidev.psi.mi.jami.model.Xref.PRIMARY, psidev.psi.mi.jami.model.Xref.PRIMARY_MI);
            this.identifiers.add(this.pubmedId);
        }
        // remove all pubmed if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, psidev.psi.mi.jami.model.Xref.PUBMED_MI, psidev.psi.mi.jami.model.Xref.PUBMED);
            this.pubmedId = null;
        }
    }

    @Override
    public void setDoi(String doi) {
        // add new doi if not null
        if (doi != null){
            // first remove old doi if not null
            if (this.doi != null){
                identifiers.remove(this.doi);
            }
            this.doi = new DbReference(psidev.psi.mi.jami.model.Xref.DOI, psidev.psi.mi.jami.model.Xref.DOI_MI, doi, psidev.psi.mi.jami.model.Xref.PRIMARY, psidev.psi.mi.jami.model.Xref.PRIMARY_MI);
            this.identifiers.add(this.doi);
        }
        // remove all doi if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, psidev.psi.mi.jami.model.Xref.DOI_MI, psidev.psi.mi.jami.model.Xref.DOI);
            this.doi = null;
        }
    }

    @Override
    public void assignImexId(String identifier) {
        // add new imex if not null
        if (identifier != null){
            // first remove old doi if not null
            if (this.imexId != null){
                xrefs.remove(this.imexId);
            }
            this.imexId = new DbReference(psidev.psi.mi.jami.model.Xref.IMEX, psidev.psi.mi.jami.model.Xref.IMEX_MI, identifier, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY, psidev.psi.mi.jami.model.Xref.IMEX_PRIMARY_MI);
            this.xrefs.add(this.imexId);
        }
        else {
            throw new IllegalArgumentException("The imex id has to be non null.");
        }
    }

    @Override
    public void setTitle(String title) {
        if (title != null){
            if (this.title != null){
                attributes.remove(new Attribute(Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE, this.title));
            }
            this.title = title;
            Attribute titleAtt = new Attribute(Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE, this.title);
            this.attributes.add(titleAtt);
        }
        else if (!this.identifiers.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(attributes, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE);
            this.title = null;
        }
    }

    @Override
    public void setJournal(String journal) {
        if (journal != null){
            if (this.journal != null){
                attributes.remove(new Attribute(Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL, this.journal));
            }
            this.journal = journal;
            Attribute journalAtt = new Attribute(Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL, this.journal);
            this.attributes.add(journalAtt);
        }
        else if (!this.identifiers.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(attributes, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL);
            this.journal = null;
        }
    }

    @Override
    public void setPublicationDate(Date date) {
        if (date != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            String formattedDate = format.format(date);

            if (this.publicationDate != null){
                attributes.remove(new Attribute(Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR, formattedDate));
            }
            this.publicationDate = date;
            Attribute pubDateAtt = new Attribute(Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR, formattedDate);
            this.attributes.add(pubDateAtt);
        }
        else if (!this.identifiers.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(attributes, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR);
            this.publicationDate = null;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append( "Bibref" );
        sb.append( "{xref=" ).append( xref );
        sb.append( ", attributes=" ).append( attributes );
        sb.append( '}' );
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        final Bibref bibref = ( Bibref ) o;

        if ( attributes != null ? !attributes.equals( bibref.attributes ) : bibref.attributes != null ) return false;
        if ( xref != null ? !xref.equals( bibref.xref ) : bibref.xref != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = ( xref != null ? xref.hashCode() : 0 );
        result = 29 * result + ( attributes != null ? attributes.hashCode() : 0 );
        return result;
    }

    protected class PublicationXref extends Xref{

        protected boolean isPrimaryAnIdentity = false;
        protected SecondaryRefList extendedSecondaryRefList = new SecondaryRefList();

        public PublicationXref() {
            super();
        }

        public PublicationXref(DbReference primaryRef) {
            super(primaryRef);
        }

        public PublicationXref(DbReference primaryRef, Collection<DbReference> secondaryRef) {
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
                        ((PublicationIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((PublicationXrefList)xrefs).addOnly(value);
                        processAddedXrefEvent(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((PublicationIdentifierList)identifiers).removeOnly(getPrimaryRef());
                processRemovedIdentifier(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((PublicationIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((PublicationXrefList)xrefs).addOnly(value);
                        processAddedXrefEvent(value);
                    }
                }
            }
            else {
                ((PublicationXrefList)xrefs).removeOnly(getPrimaryRef());
                processRemovedXrefEvent(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value)){
                        ((PublicationIdentifierList)identifiers).addOnly(value);
                        processAddedIdentifier(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((PublicationXrefList)xrefs).addOnly(value);
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

        protected class SecondaryRefList extends AbstractListHavingPoperties<DbReference> {

            @Override
            protected void processAddedObjectEvent(DbReference added) {
                if (XrefUtils.isXrefAnIdentifier(added)){
                    ((PublicationIdentifierList)identifiers).addOnly(added);
                    processAddedIdentifier(added);
                }
                else {
                    ((PublicationXrefList)xrefs).addOnly(added);
                    processAddedXrefEvent(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed)){
                    ((PublicationIdentifierList)identifiers).removeOnly(removed);
                    processRemovedIdentifier(removed);
                }
                else {
                    ((PublicationXrefList)xrefs).removeOnly(removed);
                    processRemovedXrefEvent(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((PublicationIdentifierList)identifiers).retainAllOnly(primary);
                clearPropertiesLinkedToIdentifiers();
                ((PublicationXrefList)xrefs).retainAllOnly(primary);
                clearPropertiesLinkedToXrefs();
            }
        }
    }

    private class PublicationIdentifierList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public PublicationIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                PublicationXref reference = (PublicationXref) xref;

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
                    xref = new PublicationXref();
                    ((PublicationXref)xref).setPrimaryRefOnly((DbReference) added);
                    processAddedIdentifier(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new Xref();
                    ((PublicationXref)xref).setPrimaryRefOnly(fixedRef);
                    processAddedIdentifier(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                PublicationXref reference = (PublicationXref) xref;

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
                PublicationXref reference = (PublicationXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(xrefs);

                if (reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    private class PublicationXrefList extends AbstractListHavingPoperties<psidev.psi.mi.jami.model.Xref> {
        public PublicationXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Xref added) {

            if (xref != null){
                PublicationXref reference = (PublicationXref) xref;

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
                    xref = new PublicationXref((DbReference) added);
                    ((PublicationXref)xref).setPrimaryRefOnly((DbReference) added);
                    processAddedXrefEvent(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new PublicationXref(fixedRef);
                    ((PublicationXref)xref).setPrimaryRefOnly(fixedRef);
                    processAddedXrefEvent(added);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {
            if (xref != null){
                PublicationXref reference = (PublicationXref) xref;

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
                        processRemovedXrefEvent(removed);
                    }
                }
            }
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToXrefs();
            if (xref != null){
                PublicationXref reference = (PublicationXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(identifiers);

                if (!reference.isPrimaryAnIdentity){
                    reference.setPrimaryRefOnly(null);
                }
            }
        }
    }

    protected class PublicationAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public PublicationAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(psidev.psi.mi.jami.model.Annotation added) {
            if (added instanceof Attribute){
                ((BibRefXmlAnnotationList)attributes).addOnly((Attribute) added);
            }
            else {
                Attribute att = new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue());
                ((BibRefXmlAnnotationList)attributes).addOnly(att);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((BibRefXmlAnnotationList)attributes).removeOnly(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((BibRefXmlAnnotationList)attributes).removeOnly(att);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((BibRefXmlAnnotationList)attributes).clearOnly();
        }
    }

    protected class BibRefXmlAnnotationList extends AbstractListHavingPoperties<Attribute> {
        public BibRefXmlAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Attribute added) {

            // we added a annotation, needs to add it in annotations
            processAddedAnnotationEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Attribute removed) {

            // we removed a annotation, needs to remove it in annotations
            processRemovedAnnotationEvent(removed);
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            clearPropertiesLinkedToAnnotations();
        }
    }

    protected class PublicationAuthorList extends AbstractListHavingPoperties<String> {
        public PublicationAuthorList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(String added) {

            BibRefXmlAnnotationList attributeList = (BibRefXmlAnnotationList) attributes;

            String newAuthor = StringUtils.join(authors, ",");
            Collection<Attribute> authorsCopy = new ArrayList<Attribute>(attributes);

            for (Attribute att : authorsCopy){
                if (AnnotationUtils.doesAnnotationHaveTopic(att, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                    attributeList.removeOnly(att);
                }
            }

            attributeList.addOnly(new Attribute(Annotation.AUTHOR_MI, Annotation.AUTHOR, newAuthor));
        }

        @Override
        protected void processRemovedObjectEvent(String removed) {

            BibRefXmlAnnotationList attributeList = (BibRefXmlAnnotationList) attributes;

            String newAuthor = StringUtils.join(authors, ",");
            Collection<Attribute> authorsCopy = new ArrayList<Attribute>(attributes);

            for (Attribute att : authorsCopy){
                if (AnnotationUtils.doesAnnotationHaveTopic(att, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                    attributeList.removeOnly(att);
                }
            }

            attributeList.addOnly(new Attribute(Annotation.AUTHOR_MI, Annotation.AUTHOR, newAuthor));
        }

        @Override
        protected void clearProperties() {

           ((BibRefXmlAnnotationList)attributes).clearOnly();
        }
    }
}