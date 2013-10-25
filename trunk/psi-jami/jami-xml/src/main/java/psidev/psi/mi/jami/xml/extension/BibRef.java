package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.text.ParseException;
import java.util.*;

/**
 * Xml implementation of a Publication
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/07/13</pre>
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "bibref", propOrder = {
        "JAXBAttributes",
        "JAXBXref"
})
public class BibRef
        implements Publication, FileSourceContext, Locatable
{

    private PublicationXrefContainer xrefContainer;
    @XmlLocation
    @XmlTransient
    private Locator locator;
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

    private JAXBAttributeList jaxbAttributeList;

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

    public String getPubmedId() {
        return xrefContainer != null ? xrefContainer.getPubmedId() : null;
    }

    public void setPubmedId(String pubmedId) {
        if (xrefContainer == null && pubmedId != null){
            xrefContainer = new PublicationXrefContainer();
        }
        xrefContainer.setPubmedId(pubmedId);
    }

    public String getDoi() {
        return xrefContainer != null ? xrefContainer.getDoi() : null;
    }

    public void setDoi(String doi) {
        if (xrefContainer == null && doi != null){
            xrefContainer = new PublicationXrefContainer();
        }
        xrefContainer.setDoi(doi);
    }

    public Collection<Xref> getIdentifiers() {
        if (xrefContainer == null){
            xrefContainer = new PublicationXrefContainer();
        }
        return xrefContainer.getAllIdentifiers();
    }

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
        if (authors == null){
            initialiseAuthors();
        }
        return this.authors;
    }

    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            xrefContainer = new PublicationXrefContainer();
        }
        return xrefContainer.getAllIdentifiers();
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            initialiseExperiments();
        }
        return this.experiments;
    }

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

    @XmlElement(name = "xref")
    public PublicationXrefContainer getJAXBXref() {
        if (xrefContainer != null && xrefContainer.isEmpty()){
            return null;
        }
        return xrefContainer;
    }

    public void setJAXBXref(PublicationXrefContainer xrefContainer) {
        this.xrefContainer = xrefContainer;
    }

    @XmlElementWrapper(name="attributeList")
    @XmlElement(type=XmlAnnotation.class, name = "attribute", required = true)
    public ArrayList<Annotation> getJAXBAttributes() {
        return this.jaxbAttributeList;
    }

    public void setJAXBAttributes(JAXBAttributeList annotations) {
        this.jaxbAttributeList = annotations;
        if (annotations != null){
            this.jaxbAttributeList.parent = this;
        }
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    protected void initialiseAuthors(){
        this.authors = new ArrayList<String>();
    }

    protected void initialiseAnnotations(){
        if (jaxbAttributeList != null){
            this.annotations = new ArrayList<Annotation>(jaxbAttributeList);
            this.jaxbAttributeList = null;
        }else{
            this.annotations = new ArrayList<Annotation>();
        }
    }

    protected void initialiseExperiments(){
        this.experiments = new ArrayList<Experiment>();
    }

    ////////////////////////////////////////////////////////////////// classes

    /**
     * The attribute list used by JAXB to populate participant annotations
     */
    public static class JAXBAttributeList extends ArrayList<Annotation>{

        private BibRef parent;

        public JAXBAttributeList(){
        }

        public JAXBAttributeList(int initialCapacity) {
            super(initialCapacity);
        }

        public JAXBAttributeList(Collection<? extends Annotation> c) {
            super(c);
        }

        @Override
        public boolean add(Annotation annot) {
            if (annot == null){
                return false;
            }
            if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE)){
                parent.title = annot.getValue();
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
                parent.journal = annot.getValue();
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
                if (annot.getValue() == null){
                    parent.publicationDate = null;
                    return false;
                }
                else {
                    try {
                        parent.publicationDate = PsiXmlUtils.YEAR_FORMAT.parse(annot.getValue().trim());
                        return false;
                    } catch (ParseException e) {
                        e.printStackTrace();
                        parent.publicationDate = null;
                        return super.add(annot);
                    }
                }
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.IMEX_CURATION_MI, Annotation.IMEX_CURATION)){
                parent.curationDepth = CurationDepth.IMEx;
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.MIMIX_CURATION_MI, Annotation.MIMIX_CURATION)){
                parent.curationDepth = CurationDepth.MIMIx;
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.RAPID_CURATION_MI, Annotation.RAPID_CURATION)){
                parent.curationDepth = CurationDepth.rapid_curation;
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                if (annot.getValue() == null){
                    parent.getAuthors().clear();
                }
                else if (annot.getValue().contains(",")){
                    parent.getAuthors().addAll(Arrays.asList(annot.getValue().split(",")));
                }
                else {
                    parent.getAuthors().add(annot.getValue());
                }
                return false;
            }
            else {
                return super.add(annot);
            }
        }

        @Override
        public boolean addAll(Collection<? extends Annotation> c) {
            if (c == null){
                return false;
            }
            boolean added = false;

            for (Annotation a : c){
                if (add(a)){
                    added = true;
                }
            }
            return added;
        }

        @Override
        public void add(int index, Annotation element) {
            addToSpecificIndex(index, element);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Annotation> c) {
            int newIndex = index;
            if (c == null){
                return false;
            }
            boolean add = false;
            for (Annotation a : c){
                if (addToSpecificIndex(newIndex, a)){
                    newIndex++;
                    add = true;
                }
            }
            return add;
        }

        private boolean addToSpecificIndex(int index, Annotation annot) {
            if (annot == null){
                return false;
            }
            if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE)){
                parent.title = annot.getValue();
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
                parent.journal = annot.getValue();
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
                if (annot.getValue() == null){
                    parent.publicationDate = null;
                    return false;
                }
                else {
                    try {
                        parent.publicationDate = PsiXmlUtils.YEAR_FORMAT.parse(annot.getValue().trim());
                        return false;
                    } catch (ParseException e) {
                        e.printStackTrace();
                        parent.publicationDate = null;
                        super.add(index, annot);
                        return true;
                    }
                }
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.IMEX_CURATION_MI, Annotation.IMEX_CURATION)){
                parent.curationDepth = CurationDepth.IMEx;
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.MIMIX_CURATION_MI, Annotation.MIMIX_CURATION)){
                parent.curationDepth = CurationDepth.MIMIx;
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.RAPID_CURATION_MI, Annotation.RAPID_CURATION)){
                parent.curationDepth = CurationDepth.rapid_curation;
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                if (annot.getValue() == null){
                    parent.getAuthors().clear();
                }
                else if (annot.getValue().contains(",")){
                    parent.getAuthors().addAll(Arrays.asList(annot.getValue().split(",")));
                }
                else {
                    parent.getAuthors().add(annot.getValue());
                }
                return false;
            }
            else {
                super.add(index, annot);
                return true;
            }
        }
    }
}