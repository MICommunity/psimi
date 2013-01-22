package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.publication.UnambiguousPublicationComparator;

import java.util.*;

/**
 * Default implementation for a Publication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultPublication implements Publication {

    private ExternalIdentifier identifier;
    private String imexId;
    private String title;
    private String journal;
    private Date publicationDate;
    private List<String> authors;
    private Set<Xref> xrefs;
    private Set<Annotation> annotations;
    private Collection<Experiment> experiments;
    private CurationDepth curationDepth;
    private Date releasedDate;

    public DefaultPublication(ExternalIdentifier identifier){
        this.identifier = identifier;

        this.authors = new ArrayList<String>();
        this.xrefs = new HashSet<Xref>();
        this.annotations = new HashSet<Annotation>();
        this.experiments = new ArrayList<Experiment>();
        this.curationDepth = CurationDepth.undefined;
    }

    public DefaultPublication(ExternalIdentifier identifier, CurationDepth curationDepth){
        this(identifier);
        if (curationDepth != null){
            this.curationDepth = curationDepth;
        }
    }

    public DefaultPublication(ExternalIdentifier identifier, String imexId){
        this(identifier, CurationDepth.IMEx);
        this.imexId = imexId;
    }

    public DefaultPublication(String title, String journal, Date publicationDate){
        this.title = title;
        this.journal = journal;
        this.publicationDate = publicationDate;

        this.authors = new ArrayList<String>();
        this.xrefs = new HashSet<Xref>();
        this.annotations = new HashSet<Annotation>();
        this.experiments = new ArrayList<Experiment>();
        this.curationDepth = CurationDepth.undefined;
    }

    public DefaultPublication(String title, String journal, Date publicationDate, CurationDepth curationDepth){
        this(title, journal, publicationDate);
        if (curationDepth != null){
            this.curationDepth = curationDepth;
        }
    }

    public DefaultPublication(String title, String journal, Date publicationDate, String imexId){
        this(title, journal, publicationDate, CurationDepth.IMEx);
        this.imexId = imexId;
    }

    public ExternalIdentifier getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(ExternalIdentifier identifier) {
        this.identifier = identifier;
    }

    public String getImexId() {
        return this.imexId;
    }

    public void assignImexId(String identifier) {
        if (this.imexId == null){
           this.imexId = identifier;
           this.curationDepth = CurationDepth.IMEx;
        }
        else if (!this.imexId.equals(identifier)){
            throw new IllegalArgumentException("It is forbidden to assign a new IMEx id to a publication which already has an IMEx id.");
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

    public Set<Xref> getXrefs() {
        return this.xrefs;
    }

    public Set<Annotation> getAnnotations() {
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
        return (imexId != null ? imexId : (identifier != null ? identifier.toString() : (title != null ? title : "-")));
    }
}
