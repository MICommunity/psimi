package psidev.psi.mi.jami.xml;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

/**
 * Xml implementation of a Publication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "bibref", propOrder = {
        "attributes",
        "xrefContainer"
})
public class BibRef
        implements Publication, FileSourceContext, Serializable
{

    private PublicationXrefContainer xrefContainer;
    private PsiXmLocator sourceLocator;

    private String title;
    private String journal;
    private Date publicationDate;
    private List<String> authors;
    private Collection<Annotation> annotations;
    private Collection<Experiment> experiments;
    private CurationDepth curationDepth;
    private Date releasedDate;
    private Source source;

    public BibRef(){
        this.curationDepth = CurationDepth.undefined;
    }

    public BibRef(Xref identifier){
        this();

        if (identifier != null){
            getIdentifiers().add(identifier);
        }
    }

    public BibRef(Xref identifier, CurationDepth curationDepth, Source source){
        this(identifier);
        if (curationDepth != null){
            this.curationDepth = curationDepth;
        }
        this.source = source;
    }

    public BibRef(Xref identifier, String imexId, Source source){
        this(identifier, CurationDepth.IMEx, source);
        assignImexId(imexId);
    }

    public BibRef(String pubmed){
        this.curationDepth = CurationDepth.undefined;

        if (pubmed != null){
            setPubmedId(pubmed);
        }
    }

    public BibRef(String pubmed, CurationDepth curationDepth, Source source){
        this(pubmed);
        if (curationDepth != null){
            this.curationDepth = curationDepth;
        }
        this.source = source;
    }

    public BibRef(String pubmed, String imexId, Source source){
        this(pubmed, CurationDepth.IMEx, source);
        assignImexId(imexId);
    }

    public BibRef(String title, String journal, Date publicationDate){
        this.title = title;
        this.journal = journal;
        this.publicationDate = publicationDate;
        this.curationDepth = CurationDepth.undefined;
    }

    public BibRef(String title, String journal, Date publicationDate, CurationDepth curationDepth, Source source){
        this(title, journal, publicationDate);
        if (curationDepth != null){
            this.curationDepth = curationDepth;
        }
        this.source = source;
    }

    public BibRef(String title, String journal, Date publicationDate, String imexId, Source source){
        this(title, journal, publicationDate, CurationDepth.IMEx, source);
        assignImexId(imexId);
    }

    protected void initialiseAuthors(){
        this.authors = new ArrayList<String>();
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseExperiments(){
        this.experiments = new ArrayList<Experiment>();
    }

    protected void initialiseAuthorsWith(List<String> authors){
        if (authors == null){
            this.authors = Collections.EMPTY_LIST;
        }
        else {
            this.authors = authors;
        }
    }

    protected void initialiseAnnotationsWith(Collection<Annotation> annotations){
        if (annotations == null){
            this.annotations = Collections.EMPTY_LIST;
        }
        else {
            this.annotations = annotations;
        }
    }

    protected void initialiseExperimentsWith(Collection<Experiment> experiments){
        if (experiments == null){
            this.experiments = Collections.EMPTY_LIST;
        }
        else {
            this.experiments = experiments;
        }
    }

    @XmlElement(name = "xref")
    public PublicationXrefContainer getXrefContainer() {
        if (xrefContainer != null && xrefContainer.isEmpty()){
            return null;
        }
        return xrefContainer;
    }

    public void setXrefContainer(PublicationXrefContainer xrefContainer) {
        this.xrefContainer = xrefContainer;
    }

    @XmlTransient
    public String getPubmedId() {
        return xrefContainer != null ? xrefContainer.getPubmedId() : null;
    }

    public void setPubmedId(String pubmedId) {
        if (xrefContainer == null && pubmedId != null){
            xrefContainer = new PublicationXrefContainer();
        }
        xrefContainer.setPubmedId(pubmedId);
    }

    @XmlTransient
    public String getDoi() {
        return xrefContainer != null ? xrefContainer.getDoi() : null;
    }

    public void setDoi(String doi) {
        if (xrefContainer == null && doi != null){
            xrefContainer = new PublicationXrefContainer();
        }
        xrefContainer.setDoi(doi);
    }

    @XmlTransient
    public Collection<Xref> getIdentifiers() {
        if (xrefContainer == null){
            xrefContainer = new PublicationXrefContainer();
        }
        return xrefContainer.getAllIdentifiers();
    }

    @XmlTransient
    public String getImexId() {
        return this.xrefContainer != null ? this.xrefContainer.getImexId() : null;
    }

    public void assignImexId(String identifier) {
        if (xrefContainer == null && identifier != null){
            xrefContainer = new PublicationXrefContainer();
            this.curationDepth = CurationDepth.IMEx;
        }
        this.xrefContainer.assignImexId(identifier);
    }

    @XmlTransient
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlTransient
    public String getJournal() {
        return this.journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    @XmlTransient
    public Date getPublicationDate() {
        return this.publicationDate;
    }

    public void setPublicationDate(Date date) {
        this.publicationDate = date;
    }

    @XmlTransient
    public List<String> getAuthors() {
        if (authors == null){
            initialiseAuthors();
        }
        return this.authors;
    }

    @XmlTransient
    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            xrefContainer = new PublicationXrefContainer();
        }
        return xrefContainer.getAllIdentifiers();
    }

    @XmlTransient
    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    @XmlElementWrapper(name="attributeList")
    @XmlElement(name="attribute")
    @XmlElementRefs({ @XmlElementRef(type=XmlAnnotation.class)})
    public Collection<Annotation> getAttributes() {
        // return null if we have Xref because schema does not allow attributes and xref
        if (xrefContainer != null && !xrefContainer.isEmpty()){
            return null;
        }
        else if (getAnnotations().isEmpty() && this.title == null && this.journal == null && this.publicationDate == null 
                && getAuthors().isEmpty() && this.curationDepth == CurationDepth.undefined){
            return  null;
        }
        else {
            Collection<Annotation> annots = new ArrayList<Annotation>(getAnnotations().size());
            annots.addAll(getAnnotations());

            if (this.title != null){
                annots.add(new XmlAnnotation(new DefaultCvTerm(Annotation.PUBLICATION_TITLE, Annotation.PUBLICATION_TITLE_MI), this.title));
            }
            if (this.journal != null){
                annots.add(new XmlAnnotation(new DefaultCvTerm(Annotation.PUBLICATION_JOURNAL, Annotation.PUBLICATION_JOURNAL_MI), this.journal));
            }
            if (this.publicationDate != null){
                annots.add(new XmlAnnotation(new DefaultCvTerm(Annotation.PUBLICATION_YEAR, Annotation.PUBLICATION_YEAR_MI), PsiXmlUtils.YEAR_FORMAT.format(this.publicationDate)));
            }
            
            switch (this.curationDepth){
                case IMEx:
                    annots.add(new XmlAnnotation(new DefaultCvTerm(Annotation.IMEX_CURATION, Annotation.IMEX_CURATION_MI)));
                    break;
                case MIMIx:
                    annots.add(new XmlAnnotation(new DefaultCvTerm(Annotation.MIMIX_CURATION, Annotation.MIMIX_CURATION_MI)));
                    break;
                case rapid_curation:
                    annots.add(new XmlAnnotation(new DefaultCvTerm(Annotation.RAPID_CURATION, Annotation.RAPID_CURATION_MI)));
                    break;
                default:
                    break;
            }
            
            if (!getAuthors().isEmpty()){
               annots.add(new XmlAnnotation(new DefaultCvTerm(Annotation.AUTHOR, Annotation.AUTHOR_MI), StringUtils.join(getAuthors(), ",")));
            }
            
            return annots;
        }
    }

    public void setAttributes(Collection<Annotation> annotations) {
        if (annotations == null){
            initialiseAnnotations();
        }

        // we have a bibref. Some annotations can be processed
        for (Annotation annot : annotations){
             if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE)){
                 this.title = annot.getValue();
             }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
                 this.journal = annot.getValue();
             }
             else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
                 if (annot.getValue() == null){
                     this.publicationDate = null;
                 }
                 else {
                     try {
                         this.publicationDate = PsiXmlUtils.YEAR_FORMAT.parse(annot.getValue().trim());
                     } catch (ParseException e) {
                         e.printStackTrace();
                         this.publicationDate = null;
                         this.annotations.add(annot);
                     }
                 }
             }
             else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.IMEX_CURATION_MI, Annotation.IMEX_CURATION)){
                 this.curationDepth = CurationDepth.IMEx;
             }
             else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.MIMIX_CURATION_MI, Annotation.MIMIX_CURATION)){
                 this.curationDepth = CurationDepth.MIMIx;
             }
             else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.RAPID_CURATION_MI, Annotation.RAPID_CURATION)){
                 this.curationDepth = CurationDepth.rapid_curation;
             }
             else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                 if (annot.getValue() == null){
                     getAuthors().clear();
                 }
                 else if (annot.getValue().contains(",")){
                     getAuthors().addAll(Arrays.asList(annot.getValue().split(",")));
                 }
                 else {
                     getAuthors().add(annot.getValue());
                 }
             }
            else {
                 this.annotations.add(annot); 
             }
        }
    }

    @XmlTransient
    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            initialiseExperiments();
        }
        return this.experiments;
    }

    @XmlTransient
    public CurationDepth getCurationDepth() {
        return this.curationDepth;
    }

    public void setCurationDepth(CurationDepth curationDepth) {

        if (getImexId() != null && curationDepth != null && !curationDepth.equals(CurationDepth.IMEx)){
            throw new IllegalArgumentException("The curationDepth " + curationDepth.toString() + " is not allowed because the publication has an IMEx id so it has IMEx curation depth.");
        }
        else if (getImexId() != null && curationDepth == null){
            throw new IllegalArgumentException("The curationDepth cannot be null/not specified because the publication has an IMEx id so it has IMEx curation depth.");
        }

        if (curationDepth == null) {
            this.curationDepth = CurationDepth.undefined;
        }
        else {
            this.curationDepth = curationDepth;
        }
    }

    @XmlTransient
    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public void setReleasedDate(Date released) {
        this.releasedDate = released;
    }

    @XmlTransient
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
            if (getExperiments().add(exp)){
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
            if (getExperiments().remove(exp)){
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

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
    }
}