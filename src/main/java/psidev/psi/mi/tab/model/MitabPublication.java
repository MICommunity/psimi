package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

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

public class MitabPublication extends DefaultPublication implements FileSourceContext{

    /**
     * Associated publications of that interaction.
     */
    private List<CrossReference> publications;


    /**
     * First author surname(s) of the publication(s).
     */
    private List<Author> mitabAuthors ;

    /**
     * Source databases.
     */
    private List<CrossReference> sourceDatabases;

    private MitabSourceLocator locator;

    public MitabPublication(){
        super((String)null);
    }

    @Override
    protected void initialiseIdentifiers() {
        initialiseIdentifiersWith(new PublicationIdentifierList());
    }

    @Override
    protected void initialiseAuthors() {
        initialiseAuthorsWith(new PublicationAuthorsList());
    }

    @Override
    protected void initialiseXrefs() {
        initialiseXrefsWith(new PublicationXrefList());
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getPublications() {
        if (publications == null){
            publications = new PublicationMitabIdentifiersList();
        }
        return publications;
    }

    /**
     * {@inheritDoc}
     */
    public void setPublications(List<CrossReference> publications) {
        getPublications().clear();
        if (publications != null) {
            this.publications.addAll(publications);
        }
    }

    public MitabSourceLocator getSourceLocator() {
        return locator;
    }

    public void setLocator(MitabSourceLocator locator) {
        this.locator = locator;
    }

    /**
     * {@inheritDoc}
     */
    public List<Author> getMitabAuthors() {
        if (mitabAuthors == null){
           mitabAuthors = new PublicationMitabAuthorsList();
        }
        return mitabAuthors;
    }

    /**
     * {@inheritDoc}
     */
    public void setMitabAuthors(List<Author> authors) {
        getMitabAuthors().clear();
        if (authors != null) {
            this.mitabAuthors.addAll(authors);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<CrossReference> getSourceDatabases() {
        if (sourceDatabases == null){
           sourceDatabases = new PublicationSourcesList();
        }
        return sourceDatabases;
    }

    /**
     * {@inheritDoc}
     */
    public void setSourceDatabases(List<CrossReference> sourceDatabases) {
        getSourceDatabases().clear();
        if (sourceDatabases != null) {
            this.sourceDatabases.addAll(sourceDatabases);
        }
    }

    protected void resetSourceNameFromMiReferences(){
        if (!getSourceDatabases().isEmpty()){
            Xref ref = XrefUtils.collectFirstIdentifierWithDatabase(new ArrayList<Xref>(sourceDatabases), CvTerm.PSI_MI_MI, CvTerm.PSI_MI);

            if (ref != null){
                String name = ref.getQualifier() != null ? ref.getQualifier().getShortName() : "unknown";
                getSource().setShortName(name);
                getSource().setFullName(name);
            }
        }
    }

    protected void resetSourceNameFromFirstReferences(){
        if (!getSourceDatabases().isEmpty()){
            Iterator<CrossReference> methodsIterator = sourceDatabases.iterator();
            String name = null;

            while (name == null && methodsIterator.hasNext()){
                CrossReference ref = methodsIterator.next();

                if (ref.getText() != null){
                    name = ref.getText();
                }
            }

            getSource().setShortName(name != null ? name : "unknown");
            getSource().setFullName(name != null ? name : "unknown");
        }
    }

    @Override
    public void setSource(Source source) {
        super.setSource(source);
        processNewSourceDatabasesList(source);
    }

    private void processNewSourceDatabasesList(Source source) {
        ((PublicationSourcesList)getSourceDatabases()).clearOnly();
        if (source != null){
            if (source.getMIIdentifier() != null){
                ((PublicationSourcesList)getSourceDatabases()).addOnly(new CrossReferenceImpl(CvTerm.PSI_MI, source.getMIIdentifier(), source.getFullName() != null ? source.getFullName() : source.getShortName()));
            }
            else{
                if (!source.getIdentifiers().isEmpty()){
                    Xref ref = source.getIdentifiers().iterator().next();
                    ((PublicationSourcesList)getSourceDatabases()).addOnly(new CrossReferenceImpl(ref.getDatabase().getShortName(), ref.getId(), source.getFullName() != null ? source.getFullName() : source.getShortName()));
                }
                else {
                    ((PublicationSourcesList)getSourceDatabases()).addOnly(new CrossReferenceImpl("unknown", "-", source.getFullName() != null ? source.getFullName() : source.getShortName()));
                }
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
                ((PublicationMitabIdentifiersList)getPublications()).addOnly(modified);
            }
            else {
                modified = new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier() != null ? added.getQualifier().getShortName() : null);
                ((PublicationMitabIdentifiersList)getPublications()).addOnly(modified);
            }

            processAddedIdentifierEvent(modified);
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {

            CrossReference modified = null;
            if (removed instanceof CrossReference){
                modified = (CrossReference) removed;
                ((PublicationMitabIdentifiersList)getPublications()).removeOnly(modified);
            }
            else {
                modified = new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier()!= null ? removed.getQualifier().getShortName() : null);
                ((PublicationMitabIdentifiersList)getPublications()).removeOnly(modified);
            }

            processRemovedIdentifierEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToIdentifiers();

            // clear all excepted imex which is in xref
            retainAllOnly(getXrefs());
        }
    }

    private class PublicationMitabIdentifiersList extends AbstractListHavingPoperties<CrossReference> {
        public PublicationMitabIdentifiersList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            // imex
            if (added.getDatabase().getShortName().toLowerCase().trim().equals(Xref.IMEX) && getImexId() == null){
                assignImexId(added.getId());
            }
            else {
                ((PublicationIdentifierList)getIdentifiers()).addOnly(added);
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {
            // imex
            if (removed.getDatabase().getShortName().toLowerCase().trim().equals(Xref.IMEX) && getImexId() != null && getImexId().equals(removed.getId())){
                getXrefs().remove(removed);
            }
            else {
                ((PublicationIdentifierList)getIdentifiers()).removeOnly(removed);
            }

        }

        @Override
        protected void clearProperties() {
            // clear properties
            clearPropertiesLinkedToIdentifiers();

            // remove imex id from xrefs
            clearPropertiesLinkedToXrefs();

            // clear all identifiers
            ((PublicationIdentifierList)getIdentifiers()).clearOnly();
        }
    }

    private class PublicationAuthorsList extends AbstractListHavingPoperties<String> {
        public PublicationAuthorsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(String added) {
            ((PublicationMitabAuthorsList)getMitabAuthors()).addOnly(new AuthorImpl(added));
        }

        @Override
        protected void processRemovedObjectEvent(String removed) {
            ((PublicationMitabAuthorsList)getMitabAuthors()).removeOnly(new AuthorImpl(removed));
        }

        @Override
        protected void clearProperties() {
            ((PublicationMitabAuthorsList)getMitabAuthors()).clearOnly();
        }
    }

    private class PublicationMitabAuthorsList extends AbstractListHavingPoperties<Author> {
        public PublicationMitabAuthorsList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Author added) {

            ((PublicationAuthorsList)getAuthors()).addOnly(added.getName());
        }

        @Override
        protected void processRemovedObjectEvent(Author removed) {

            ((PublicationAuthorsList)getAuthors()).removeOnly(removed.getName());
        }

        @Override
        protected void clearProperties() {
            ((PublicationAuthorsList)getAuthors()).clearOnly();
        }
    }

    private class PublicationXrefList extends AbstractListHavingPoperties<Xref> {
        public PublicationXrefList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {

            // the added identifier is imex and the current imex is not set
            if (getImexId() == null && XrefUtils.isXrefFromDatabase(added, Xref.IMEX_MI, Xref.IMEX)){
                // the added xref is imex-primary
                if (XrefUtils.doesXrefHaveQualifier(added, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                    if (added instanceof CrossReference){
                        assignImexId(added.getId());
                        ((PublicationMitabIdentifiersList) getPublications()).addOnly((CrossReference)added);
                    }
                    else {
                        CrossReference imex = new CrossReferenceImpl(added.getDatabase().getShortName(), added.getId(), added.getQualifier().getShortName());
                        assignImexId(added.getId());
                        ((PublicationMitabIdentifiersList) getPublications()).addOnly(imex);
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            if (getImexId() != null && getImexId().equals(removed.getId())){
                if (removed instanceof CrossReference){
                    ((PublicationMitabIdentifiersList) getPublications()).removeOnly(removed);
                }
                else {
                    CrossReference imex = new CrossReferenceImpl(removed.getDatabase().getShortName(), removed.getId(), removed.getQualifier().getShortName());
                    ((PublicationMitabIdentifiersList) getPublications()).removeOnly(imex);
                }
            }
            // the removed identifier is pubmed
            processRemovedXrefEvent(removed);
        }

        @Override
        protected void clearProperties() {
            if (getImexId() != null){
                CvTerm imexDatabase = CvTermFactory.createImexDatabase();
                CvTerm imexPrimaryQualifier = CvTermFactory.createImexPrimaryQualifier();
                Xref imex = new DefaultXref(imexDatabase, getImexId(), imexPrimaryQualifier);
                ((PublicationMitabIdentifiersList) getPublications()).removeOnly(imex);
            }

            ((PublicationMitabIdentifiersList) getPublications()).retainAllOnly(getIdentifiers());
        }
    }

    protected class PublicationSourcesList extends AbstractListHavingPoperties<CrossReference> {
        public PublicationSourcesList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(CrossReference added) {

            if (getSource() == null){
                String name = added.getText() != null ? added.getText() : "unknown";
                setSource(new DefaultSource(name, name, added));
            }
            else {
                getSource().getXrefs().add(added);
                // reset shortname
                if (getSource().getMIIdentifier() != null && getSource().getMIIdentifier().equals(added.getId())){
                    String name = added.getText();

                    if (name != null){
                        getSource().setShortName(name);
                    }
                    else {
                        resetSourceNameFromMiReferences();
                        if (getSource().getShortName().equals("unknown")){
                            resetSourceNameFromFirstReferences();
                        }
                    }
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(CrossReference removed) {

            if (getSource() != null){
                getSource().getXrefs().remove(removed);

                if (removed.getText() != null && getSource().getShortName().equals(removed.getText())){
                    if (getSource().getMIIdentifier() != null){
                        resetSourceNameFromMiReferences();
                        if (getSource().getShortName().equals("unknown")){
                            resetSourceNameFromFirstReferences();
                        }
                    }
                    else {
                        resetSourceNameFromFirstReferences();
                    }
                }
            }

            if (isEmpty()){
                setSource(null);
            }
        }

        @Override
        protected void clearProperties() {
            setSource(null);
        }
    }
}
