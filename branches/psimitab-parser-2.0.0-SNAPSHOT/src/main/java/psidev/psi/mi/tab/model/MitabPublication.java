package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Default MITAB publication implementation which is a patch for backward compatibility.
 * It only contains publication information such as created date, publication id and authors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class MitabPublication extends DefaultPublication{

    /**
     * Associated publications of that interaction.
     */
    private List<CrossReference> publications
            = new PublicationMitabIdentifiersList();

    /**
     * First author surname(s) of the publication(s).
     */
    private List<Author> mitabAuthors = new PublicationMitabAuthorsList();

    /**
     * Source databases.
     */
    private List<CrossReference> sourceDatabases
            = new PublicationSourcesList();


    public MitabPublication(){
        super((String)null);
    }

    @Override
    protected void initialiseIdentifiers() {
        this.identifiers = new PublicationIdentifierList();
    }

    @Override
    protected void initializeAuthors() {
        this.authors = new PublicationAuthorsList();
    }

    @Override
    protected void initializeXrefs() {
        this.xrefs = new PublicationXrefList();
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getPublications() {
        return publications;
    }

    /**
     * {@inheritDoc}
     */
    public void setPublications(List<CrossReference> publications) {
        this.publications.clear();
        if (publications != null) {
            this.publications.addAll(publications);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Author> getMitabAuthors() {
        return mitabAuthors;
    }

    /**
     * {@inheritDoc}
     */
    public void setMitabAuthors(List<Author> authors) {
        this.mitabAuthors.clear();
        if (authors != null) {
            this.mitabAuthors.addAll(authors);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getSourceDatabases() {
        return sourceDatabases;
    }

    /**
     * {@inheritDoc}
     */
    public void setSourceDatabases(List<CrossReference> sourceDatabases) {
        this.sourceDatabases.clear();
        if (sourceDatabases != null) {
            this.sourceDatabases.addAll(sourceDatabases);
        }
    }

    protected void processAddedIdentifier(Xref added){
        // the added identifier is pubmed and it is not the current pubmed identifier
        if (pubmedId != added && XrefUtils.isXrefFromDatabase(added, Xref.PUBMED_MI, Xref.PUBMED)){
            // the current pubmed identifier is not identity, we may want to set pubmed Identifier
            if (!XrefUtils.doesXrefHaveQualifier(pubmedId, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the pubmed identifier is not set, we can set the pubmed
                if (pubmedId == null){
                    pubmedId = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    pubmedId = added;
                }
                // the added xref is secondary object and the current pubmed is not a secondary object, we reset pubmed identifier
                else if (!XrefUtils.doesXrefHaveQualifier(pubmedId, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    pubmedId = added;
                }
            }
        }
        // the added identifier is doi and it is not the current doi identifier
        else if (doi != added && XrefUtils.isXrefFromDatabase(added, Xref.DOI_MI, Xref.DOI)){
            // the current doi identifier is not identity, we may want to set doi
            if (!XrefUtils.doesXrefHaveQualifier(doi, Xref.IDENTITY_MI, Xref.IDENTITY)){
                // the doi is not set, we can set the doi
                if (doi == null){
                    doi = added;
                }
                else if (XrefUtils.doesXrefHaveQualifier(added, Xref.IDENTITY_MI, Xref.IDENTITY)){
                    doi = added;
                }
                // the added xref is secondary object and the current doi is not a secondary object, we reset doi
                else if (!XrefUtils.doesXrefHaveQualifier(doi, Xref.SECONDARY_MI, Xref.SECONDARY)
                        && XrefUtils.doesXrefHaveQualifier(added, Xref.SECONDARY_MI, Xref.SECONDARY)){
                    doi = added;
                }
            }
        }
    }

    protected void processRemovedIdentifier(Xref removed){
        // the removed identifier is pubmed
        if (pubmedId == removed){
            pubmedId = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, Xref.PUBMED_MI, Xref.PUBMED);
        }
        // the removed identifier is doi
        else if (doi == removed){
            doi = XrefUtils.collectFirstIdentifierWithDatabase(identifiers, Xref.DOI_MI, Xref.DOI);
        }
    }

    protected void resetSourceNameFromMiReferences(){
        if (!sourceDatabases.isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(sourceDatabases), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                source.setShortName(name);
                source.setFullName(name);
            }
        }
    }

    protected void resetSourceNameFromFirstReferences(){
        if (!sourceDatabases.isEmpty()){
            Iterator<CrossReference> methodsIterator = sourceDatabases.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            source.setShortName(name != null ? name : "unknown");
            source.setFullName(name != null ? name : "unknown");
        }
    }

    @Override
    public void setSource(Source source) {
        super.setSource(source);
        processNewSourceDatabasesList(source);
    }

    private void processNewSourceDatabasesList(Source source) {
        ((PublicationSourcesList)sourceDatabases).clearOnly();
        if (source.getMIIdentifier() != null){
            ((PublicationSourcesList)sourceDatabases).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, source.getMIIdentifier(), source.getFullName() != null ? source.getFullName() : source.getShortName()));
        }
        else{
            if (!source.getIdentifiers().isEmpty()){
                Xref ref = source.getIdentifiers().iterator().next();
                ((PublicationSourcesList)sourceDatabases).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), source.getFullName() != null ? source.getFullName() : source.getShortName()));
            }
            else {
                ((PublicationSourcesList)sourceDatabases).addOnly(new CrossReferenceImpl("unknown", "-", source.getFullName() != null ? source.getFullName() : source.getShortName()));
            }
        }
    }

    private class PublicationIdentifierList extends AbstractListHavingPoperties<Xref> {
        public PublicationIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {

            CrossReference modified = null;
            if (added instanceof CrossReference){
                modified = (CrossReference) added;
                ((PublicationMitabIdentifiersList)publications).addOnly(modified);
            }
            else {
                modified = new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier() != null ? added.getQualifier().getShortName() : null);
                ((PublicationMitabIdentifiersList)publications).addOnly(modified);
            }

            processAddedIdentifier(modified);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {

            CrossReference modified = null;
            if (removed instanceof CrossReference){
                modified = (CrossReference) removed;
                ((PublicationMitabIdentifiersList)publications).removeOnly(modified);
            }
            else {
                modified = new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier()!= null ? removed.getQualifier().getShortName() : null);
                ((PublicationMitabIdentifiersList)publications).removeOnly(modified);
            }

            processRemovedIdentifier(removed);
        }

        @Override
        protected void clearProperties() {
            pubmedId = null;
            doi = null;

            // clear all excepted imex which is in xref
            retainAllOnly(xrefs);
        }
    }

    private class PublicationMitabIdentifiersList extends AbstractListHavingPoperties<CrossReference> {
        public PublicationMitabIdentifiersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // imex
            if (added.getDatabase().getShortName().toLowerCase().trim().equals(Xref.IMEX) && imexId == null){
                assignImexId(imexId.getId());
            }
            else {
                ((PublicationIdentifierList)identifiers).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            // imex
            if (removed.getDatabase().getShortName().toLowerCase().trim().equals(Xref.IMEX) && imexId != null && imexId.equals(removed.getId())){
                xrefs.remove(removed);
            }
            else {
                ((PublicationIdentifierList)identifiers).removeOnly(removed);
            }

        }

        @Override
        protected void clearProperties() {
            // clear properties
            pubmedId = null;
            doi = null;

            // remove imex id from xrefs
            if (imexId != null){
                xrefs.remove(imexId);
            }

            // clear all identifiers
            ((PublicationIdentifierList)identifiers).clearOnly();
        }
    }

    private class PublicationAuthorsList extends AbstractListHavingPoperties<String> {
        public PublicationAuthorsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(String added) {
            ((PublicationMitabAuthorsList)mitabAuthors).addOnly(new AuthorImpl(added));
        }

        @Override
        protected void processRemovedObjectEvent(String removed) {
            ((PublicationMitabAuthorsList)mitabAuthors).removeOnly(new AuthorImpl(removed));
        }

        @Override
        protected void clearProperties() {
            ((PublicationMitabAuthorsList)mitabAuthors).clearOnly();
        }
    }

    private class PublicationMitabAuthorsList extends AbstractListHavingPoperties<Author> {
        public PublicationMitabAuthorsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Author added) {

            ((PublicationAuthorsList)authors).addOnly(added.getName());
        }

        @Override
        protected void processRemovedObjectEvent(Author removed) {

            ((PublicationAuthorsList)authors).removeOnly(removed.getName());
        }

        @Override
        protected void clearProperties() {
            ((PublicationAuthorsList)authors).clearOnly();
        }
    }

    private class PublicationXrefList extends AbstractListHavingPoperties<Xref> {
        public PublicationXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {

            // the added identifier is imex and the current imex is not set
            if (imexId == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
                // the added xref is imex-primary
                if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                    if (added instanceof CrossReference){
                        imexId = added;
                        ((PublicationMitabIdentifiersList) publications).addOnly((CrossReference) imexId);
                    }
                    else {
                        CrossReference imex = new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier().getShortName());
                        imexId = imex;
                        ((PublicationMitabIdentifiersList) publications).addOnly((CrossReference)imexId);
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            // the removed identifier is pubmed
            if (imexId == removed){
                ((PublicationMitabIdentifiersList) publications).removeOnly(imexId);
                imexId = null;
            }
        }

        @Override
        protected void clearProperties() {
            ((PublicationMitabIdentifiersList) publications).removeOnly(imexId);
            imexId = null;
        }
    }

    protected class PublicationSourcesList extends AbstractListHavingPoperties<CrossReference> {
        public PublicationSourcesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (source == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                source = new DefaultSource(name, name, added);
            }
            else {
                source.getXrefs().add(added);
                // reset shortname
                if (source.getMIIdentifier() != null && source.getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        source.setShortName(name);
                    }
                    else {
                        resetSourceNameFromMiReferences();
                        if (source.getShortName().equals("unknown")){
                            resetSourceNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (source != null){
                source.getXrefs().remove(removed);

                if (removed.getText() != null && source.getShortName().equals(removed.getText())){
                    if (source.getMIIdentifier() != null){
                        resetSourceNameFromMiReferences();
                        if (source.getShortName().equals("unknown")){
                            resetSourceNameFromFirstReferences();
                        }
                    }
                    else {
                        resetSourceNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                source = null;
            }
        }

        @Override
        protected void clearProperties() {
            source = null;
        }
    }
}
