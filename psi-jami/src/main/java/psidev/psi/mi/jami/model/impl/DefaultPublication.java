package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.publication.UnambiguousPublicationComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Default implementation for a Publication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultPublication implements Publication, Serializable {

    protected String title;
    protected String journal;
    protected Date publicationDate;
    protected List<String> authors;
    protected Collection<Xref> identifiers;
    protected Collection<Xref> xrefs;
    protected Collection<Annotation> annotations;
    protected Collection<Experiment> experiments;
    protected CurationDepth curationDepth;
    protected Date releasedDate;
    protected Source source;

    protected Xref pubmedId;
    protected Xref doi;
    protected Xref imexId;

    public DefaultPublication(Xref identifier){
        initializeAuthors();
        initializeXrefs();
        initialiseAnnotations();
        initialiseExperiments();
        initialiseIdentifiers();
        this.curationDepth = CurationDepth.undefined;

        if (identifier != null){
            this.identifiers.add(identifier);
        }
    }

    public DefaultPublication(Xref identifier, CurationDepth curationDepth, Source source){
        this(identifier);
        if (curationDepth != null){
            this.curationDepth = curationDepth;
        }
        this.source = source;
    }

    public DefaultPublication(Xref identifier, String imexId, Source source){
        this(identifier, CurationDepth.IMEx, source);
        assignImexId(imexId);
    }

    public DefaultPublication(String pubmed){
        initializeAuthors();
        initializeXrefs();
        initialiseAnnotations();
        initialiseExperiments();
        initialiseIdentifiers();
        this.curationDepth = CurationDepth.undefined;

        if (pubmed != null){
            setPubmedId(pubmed);
        }
    }

    public DefaultPublication(String pubmed, CurationDepth curationDepth, Source source){
        this(pubmed);
        if (curationDepth != null){
            this.curationDepth = curationDepth;
        }
        this.source = source;
    }

    public DefaultPublication(String pubmed, String imexId, Source source){
        this(pubmed, CurationDepth.IMEx, source);
        assignImexId(imexId);
    }

    public DefaultPublication(String title, String journal, Date publicationDate){
        this.title = title;
        this.journal = journal;
        this.publicationDate = publicationDate;

        initializeAuthors();
        initializeXrefs();
        initialiseAnnotations();
        initialiseExperiments();
        initialiseIdentifiers();
        this.curationDepth = CurationDepth.undefined;
    }

    public DefaultPublication(String title, String journal, Date publicationDate, CurationDepth curationDepth, Source source){
        this(title, journal, publicationDate);
        if (curationDepth != null){
            this.curationDepth = curationDepth;
        }
        this.source = source;
    }

    public DefaultPublication(String title, String journal, Date publicationDate, String imexId, Source source){
        this(title, journal, publicationDate, CurationDepth.IMEx, source);
        assignImexId(imexId);
    }

    protected void initializeAuthors(){
        this.authors = new ArrayList<String>();
    }

    protected void initializeXrefs(){
        this.xrefs = new PublicationXrefList();
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseExperiments(){
        this.experiments = new ArrayList<Experiment>();
    }

    protected void initialiseIdentifiers(){
        this.identifiers = new PublicationIdentifierList();
    }

    public String getPubmedId() {
        return this.pubmedId != null ? this.pubmedId.getId() : null;
    }

    public void setPubmedId(String pubmedId) {
        // add new pubmed if not null
        if (pubmedId != null){
            CvTerm pubmedDatabase = CvTermFactory.createPubmedDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old pubmed if not null
            if (this.pubmedId != null){
                identifiers.remove(this.pubmedId);
            }
            this.pubmedId = new DefaultXref(pubmedDatabase, pubmedId, identityQualifier);
            this.identifiers.add(this.pubmedId);
        }
        // remove all pubmed if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, Xref.PUBMED_MI, Xref.PUBMED);
            this.pubmedId = null;
        }
    }

    public String getDoi() {
        return this.doi != null ? this.doi.getId() : null;
    }

    public void setDoi(String doi) {
        // add new doi if not null
        if (doi != null){
            CvTerm doiDatabase = CvTermFactory.createDoiDatabase();
            CvTerm identityQualifier = CvTermFactory.createIdentityQualifier();
            // first remove old doi if not null
            if (this.doi != null){
                identifiers.remove(this.doi);
            }
            this.doi = new DefaultXref(doiDatabase, doi, identityQualifier);
            this.identifiers.add(this.doi);
        }
        // remove all doi if the collection is not empty
        else if (!this.identifiers.isEmpty()) {
            XrefUtils.removeAllXrefsWithDatabase(identifiers, Xref.DOI_MI, Xref.DOI);
            this.doi = null;
        }
    }

    public Collection<Xref> getIdentifiers() {
        return this.identifiers;
    }

    public String getImexId() {
        return this.imexId != null ? this.imexId.getId() : null;
    }

    public void assignImexId(String identifier) {
        // add new imex if not null
        if (identifier != null){
            CvTerm imexDatabase = CvTermFactory.createImexDatabase();
            CvTerm imexPrimaryQualifier = CvTermFactory.createImexPrimaryQualifier();
            // first remove old doi if not null
            if (this.imexId != null){
                xrefs.remove(this.imexId);
            }
            this.imexId = new DefaultXref(imexDatabase, identifier, imexPrimaryQualifier);
            this.xrefs.add(this.imexId);
        }
        else {
            throw new IllegalArgumentException("The imex id has to be non null.");
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJournal() {
        return this.journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }

    public void setPublicationDate(Date date) {
        this.publicationDate = date;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public Collection<Xref> getXrefs() {
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
        return this.annotations;
    }

    public Collection<Experiment> getExperiments() {
        return this.experiments;
    }

    public CurationDepth getCurationDepth() {
        return this.curationDepth;
    }

    public void setCurationDepth(CurationDepth curationDepth) {
        if (imexId == null){
            this.curationDepth = curationDepth;
        }
        else if (imexId != null && !curationDepth.equals(CurationDepth.IMEx)){
            throw new IllegalArgumentException("The curationDepth " + curationDepth.toString() + " is not allowed because the publication has an IMEx id so it has IMEx curation depth.");
        }
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public void setReleasedDate(Date released) {
        this.releasedDate = released;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean addExperiment(Experiment exp) {
        if (exp == null){
            return false;
        }
        else {
            if (experiments.add(exp)){
                exp.setPublication(this);
                return true;
            }
            return false;
        }
    }

    public boolean removeExperiment(Experiment exp) {
        if (exp == null){
            return false;
        }
        else {
            if (experiments.remove(exp)){
                exp.setPublication(null);
                return true;
            }
            return false;
        }
    }

    public boolean addAllExperiments(Collection<? extends Experiment> exps) {
        if (exps == null){
            return false;
        }
        else {
            boolean added = false;

            for (Experiment exp : exps){
                if (addExperiment(exp)){
                    added = true;
                }
            }
            return added;
        }
    }

    public boolean removeAllExperiments(Collection<? extends Experiment> exps) {
        if (exps == null){
            return false;
        }
        else {
            boolean removed = false;

            for (Experiment exp : exps){
                if (removeExperiment(exp)){
                    removed = true;
                }
            }
            return removed;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Publication)){
            return false;
        }

        return UnambiguousPublicationComparator.areEquals(this, (Publication) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousPublicationComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return (imexId != null ? imexId.getId() : (pubmedId != null ? pubmedId.getId() : (doi != null ? doi.getId() : (title != null ? title : "-"))));
    }

    private class PublicationIdentifierList extends AbstractListHavingPoperties<Xref> {
        public PublicationIdentifierList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Xref added) {

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

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            // the removed identifier is pubmed
            if (pubmedId == removed){
                pubmedId = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.PUBMED_MI, Xref.PUBMED);
            }
            // the removed identifier is doi
            else if (doi == removed){
                doi = XrefUtils.collectFirstIdentifierWithDatabase(this, Xref.DOI_MI, Xref.DOI);
            }
        }

        @Override
        protected void clearProperties() {
            pubmedId = null;
            doi = null;
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
                    imexId = added;
                }
            }
        }

        @Override
        protected void processRemovedObjectEvent(Xref removed) {
            // the removed identifier is pubmed
            if (imexId == removed){
                imexId = null;
            }
        }

        @Override
        protected void clearProperties() {
            imexId = null;
        }
    }
}
