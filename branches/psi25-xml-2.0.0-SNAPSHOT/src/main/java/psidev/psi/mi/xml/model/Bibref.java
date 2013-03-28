/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */


package psidev.psi.mi.xml.model;


import org.apache.commons.lang.StringUtils;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
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

public class Bibref extends DefaultPublication implements XrefContainer, AttributeContainer, FileSourceContext {

    private Xref xref;

    private Collection<Attribute> attributes;

    private FileSourceLocator locator;

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
            getAttributes().addAll(attributes);
        }
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith( new PublicationXrefList());
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new PublicationIdentifierList());
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new PublicationAnnotationList());
    }

    @Override
    protected void initialiseAuthors() {
        initialiseAuthorsWith(new PublicationAuthorList());
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
            this.xref = new PublicationXref(value.getPrimaryRef(), value.getSecondaryRef());
        }
        else if (this.xref != null){
            getIdentifiers().clear();
            getXrefs().clear();
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
        if (attributes == null){
            attributes =new BibRefXmlAnnotationList();
        }
        return attributes;
    }

    protected void processAddedAnnotationEvent(Annotation added) {
        if (getTitle() == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE)){
            super.setTitle(added.getValue());
        }
        else if (getJournal() == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
            super.setJournal(added.getValue());
        }
        else if (getPublicationDate() == null && added.getValue() != null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            try {
                super.setPublicationDate(format.parse(added.getValue()));
            } catch (ParseException e) {
                e.printStackTrace();
                super.setPublicationDate(null);
            }
        }
        else if (getAuthors().isEmpty() && added.getValue() != null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
            if (added.getValue().contains(",")){
                getAuthors().addAll(Arrays.asList(added.getValue().split(",")));
            }
            else {
                getAuthors().add(added.getValue());
            }
        }
        else {
            ((PublicationAnnotationList)getAnnotations()).addOnly(added);
        }
    }

    protected void processRemovedAnnotationEvent(Annotation removed) {
        if (getTitle() != null && getTitle().equals(removed.getValue()) && AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE)){
            Annotation titleAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(attributes, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE);
            super.setTitle(titleAnnot != null ? titleAnnot.getValue() : null);
        }
        else if (getJournal() != null && getJournal().equals(removed.getValue()) && AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
            Annotation journalAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(attributes, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL);
            super.setJournal(journalAnnot != null ? journalAnnot.getValue() : null);
        }
        else if (getPublicationDate() != null && AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy");

            if (format.format(getPublicationDate()).equals(removed.getValue())){
                Annotation dateAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(attributes, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR);
                try {
                    super.setPublicationDate(dateAnnot != null ? format.parse(dateAnnot.getValue()) : null);
                } catch (ParseException e) {
                    e.printStackTrace();
                    super.setPublicationDate(null);
                }
            }
        }
        else if (!getAuthors().isEmpty() && removed.getValue() != null && AnnotationUtils.doesAnnotationHaveTopic(removed, Annotation.AUTHOR_MI, Annotation.AUTHOR)){

            if (removed.getValue().equals(org.apache.commons.lang.StringUtils.join(getAuthors(), ","))){
                Annotation authorAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(attributes, Annotation.AUTHOR_MI, Annotation.AUTHOR);
                if (authorAnnot != null){
                    ((PublicationAuthorList)getAuthors()).addAllOnly(Arrays.asList(authorAnnot.getValue().split(",")));
                }
                else {
                    ((PublicationAuthorList)getAuthors()).clearOnly();
                }
            }
        }
        else {
            ((PublicationAnnotationList)getAnnotations()).removeOnly(removed);
        }
    }

    protected void clearPropertiesLinkedToAnnotations() {
        super.setTitle(null);
        super.setJournal(null);
        super.setPublicationDate(null);
        ((PublicationAuthorList)getAuthors()).clearOnly();
        ((PublicationAnnotationList)getAnnotations()).clearOnly();
    }

    /////////////////////////
    // Object override

    @Override
    public void setTitle(String title) {
        if (title != null){
            if (this.getTitle() != null){
                getAttributes().remove(new Attribute(Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE, this.getTitle()));
            }
            super.setTitle(title);
            Attribute titleAtt = new Attribute(Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE, getTitle());
            getAttributes().add(titleAtt);
        }
        else if (!getAttributes().isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(attributes, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE);
            super.setTitle(null);
        }
    }

    @Override
    public void setJournal(String journal) {
        if (journal != null){
            if (getJournal() != null){
                getAttributes().remove(new Attribute(Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL, getJournal()));
            }
            super.setJournal(journal);
            Attribute journalAtt = new Attribute(Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL, getJournal());
            getAttributes().add(journalAtt);
        }
        else if (!getAttributes().isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(attributes, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL);
            super.setJournal(null);
        }
    }

    @Override
    public void setPublicationDate(Date date) {
        if (date != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            String formattedDate = format.format(date);

            if (getPublicationDate() != null){
                getAttributes().remove(new Attribute(Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR, formattedDate));
            }
            super.setPublicationDate(date);
            Attribute pubDateAtt = new Attribute(Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR, formattedDate);
            getAttributes().add(pubDateAtt);
        }
        else if (!getAttributes().isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(attributes, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR);
            super.setPublicationDate(null);
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

        if ( attributes != null ? !attributes.equals( bibref.attributes ) : (bibref.attributes != null && !bibref.attributes.isEmpty()) ) return false;
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
                extendedSecondaryRefList.addAll(secondaryRef);
            }
        }

        public void setPrimaryRef( DbReference value ) {
            if (getPrimaryRef() == null){
                super.setPrimaryRef(value);
                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value) || XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                        ((PublicationIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((PublicationXrefList)getXrefs()).addOnly(value);
                        processAddedXrefEvent(value);
                    }
                }
            }
            else if (isPrimaryAnIdentity){
                ((PublicationIdentifierList)getIdentifiers()).removeOnly(getPrimaryRef());
                processRemovedIdentifierEvent(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value) || XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                        ((PublicationIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((PublicationXrefList)getXrefs()).addOnly(value);
                        processAddedXrefEvent(value);
                    }
                }
            }
            else {
                ((PublicationXrefList)getXrefs()).removeOnly(getPrimaryRef());
                processRemovedXrefEvent(getPrimaryRef());
                super.setPrimaryRef(value);

                if (value != null){
                    if (XrefUtils.isXrefAnIdentifier(value) || XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                        ((PublicationIdentifierList)getIdentifiers()).addOnly(value);
                        processAddedIdentifierEvent(value);
                        isPrimaryAnIdentity = true;
                    }
                    else {
                        ((PublicationXrefList)getXrefs()).addOnly(value);
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
                if (XrefUtils.isXrefAnIdentifier(value) || XrefUtils.doesXrefHaveQualifier(value, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
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
                if (XrefUtils.isXrefAnIdentifier(added) || XrefUtils.doesXrefHaveQualifier(added, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                    ((PublicationIdentifierList)getIdentifiers()).addOnly(added);
                    processAddedIdentifierEvent(added);
                }
                else {
                    ((PublicationXrefList)getXrefs()).addOnly(added);
                    processAddedXrefEvent(added);
                }
            }

            @Override
            protected void processRemovedObjectEvent(DbReference removed) {
                if (XrefUtils.isXrefAnIdentifier(removed) || XrefUtils.doesXrefHaveQualifier(removed, psidev.psi.mi.jami.model.Xref.PRIMARY_MI, psidev.psi.mi.jami.model.Xref.PRIMARY)){
                    ((PublicationIdentifierList)getIdentifiers()).removeOnly(removed);
                    processRemovedIdentifierEvent(removed);
                }
                else {
                    ((PublicationXrefList)getXrefs()).removeOnly(removed);
                    processRemovedXrefEvent(removed);
                }
            }

            @Override
            protected void clearProperties() {
                Collection<DbReference> primary = Arrays.asList(getPrimaryRef());
                ((PublicationIdentifierList)getIdentifiers()).retainAllOnly(primary);
                clearPropertiesLinkedToIdentifiers();
                ((PublicationXrefList)getXrefs()).retainAllOnly(primary);
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
                    xref = new PublicationXref();
                    ((PublicationXref)xref).setPrimaryRefOnly((DbReference) added);
                    processAddedIdentifierEvent(added);
                }
                else {
                    DbReference fixedRef = new DbReference(added.getDatabase().getShortName(), added.getDatabase().getMIIdentifier(), added.getId(),
                            added.getQualifier() != null ? added.getQualifier().getShortName() : null, added.getQualifier() != null ? added.getQualifier().getMIIdentifier() : null);

                    xref = new Xref();
                    ((PublicationXref)xref).setPrimaryRefOnly(fixedRef);
                    processAddedIdentifierEvent(fixedRef);
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Xref removed) {

            if (xref != null){
                PublicationXref reference = (PublicationXref) xref;

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
                PublicationXref reference = (PublicationXref) xref;
                reference.extendedSecondaryRefList.retainAllOnly(getXrefs());

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
                reference.extendedSecondaryRefList.retainAllOnly(getIdentifiers());

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
                ((BibRefXmlAnnotationList)getAttributes()).addOnly((Attribute) added);
            }
            else {
                Attribute att = new Attribute(added.getTopic().getMIIdentifier(), added.getTopic().getShortName(), added.getValue());
                ((BibRefXmlAnnotationList)getAttributes()).addOnly(att);
            }
        }

        @Override
        protected void processRemovedObjectEvent(psidev.psi.mi.jami.model.Annotation removed) {
            if (removed instanceof Annotation){
                ((BibRefXmlAnnotationList)getAttributes()).removeOnly(removed);
            }
            else {
                Attribute att = new Attribute(removed.getTopic().getMIIdentifier(), removed.getTopic().getShortName(), removed.getValue());
                ((BibRefXmlAnnotationList)getAttributes()).removeOnly(att);
            }
        }

        @Override
        protected void clearProperties() {
            // clear all annotations
            ((BibRefXmlAnnotationList)getAttributes()).clearOnly();
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

            BibRefXmlAnnotationList attributeList = (BibRefXmlAnnotationList) getAttributes();

            String newAuthor = StringUtils.join(getAuthors(), ",");
            Collection<Attribute> authorsCopy = new ArrayList<Attribute>(getAttributes());

            for (Attribute att : authorsCopy){
                if (AnnotationUtils.doesAnnotationHaveTopic(att, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                    attributeList.removeOnly(att);
                }
            }

            attributeList.addOnly(new Attribute(Annotation.AUTHOR_MI, Annotation.AUTHOR, newAuthor));
        }

        @Override
        protected void processRemovedObjectEvent(String removed) {

            BibRefXmlAnnotationList attributeList = (BibRefXmlAnnotationList) getAttributes();

            String newAuthor = StringUtils.join(getAuthors(), ",");
            Collection<Attribute> authorsCopy = new ArrayList<Attribute>(getAttributes());

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