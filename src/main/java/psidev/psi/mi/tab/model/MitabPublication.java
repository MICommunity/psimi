package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;

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
            xrefs.remove(imexId);

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
}
