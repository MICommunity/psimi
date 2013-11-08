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
public class BibRef
        implements Publication, FileSourceContext, Locatable
{

    private PublicationXrefContainer xrefContainer;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private PsiXmLocator sourceLocator;
    private Collection<Experiment> experiments;
    private Date releasedDate;
    private Source source;

    private JAXBAttributeWrapper jaxbAttributeWrapper;

    public BibRef(){
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
            setCurationDepth(curationDepth);
        }
        this.source = source;
    }

    public BibRef(Xref identifier, String imexId, Source source){
        this(identifier, CurationDepth.IMEx, source);
        assignImexId(imexId);
    }

    public BibRef(String pubmed){

        if (pubmed != null){
            setPubmedId(pubmed);
        }
    }

    public BibRef(String pubmed, CurationDepth curationDepth, Source source){
        this(pubmed);
        if (curationDepth != null){
            setCurationDepth(curationDepth);
        }
        this.source = source;
    }

    public BibRef(String pubmed, String imexId, Source source){
        this(pubmed, CurationDepth.IMEx, source);
        assignImexId(imexId);
    }

    public BibRef(String title, String journal, Date publicationDate){
        setTitle(title);
        setJournal(journal);
        setPublicationDate(publicationDate);
    }

    public BibRef(String title, String journal, Date publicationDate, CurationDepth curationDepth, Source source){
        this(title, journal, publicationDate);
        setCurationDepth(curationDepth);
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
        return xrefContainer.getIdentifiers();
    }

    public String getImexId() {
        return this.xrefContainer != null ? this.xrefContainer.getImexId() : null;
    }

    public void assignImexId(String identifier) {
        if (xrefContainer == null && identifier != null){
            xrefContainer = new PublicationXrefContainer();
            setCurationDepth(CurationDepth.IMEx);
        }
        this.xrefContainer.assignImexId(identifier);
    }

    public String getTitle() {
        return this.jaxbAttributeWrapper != null ? this.jaxbAttributeWrapper.title : null;
    }

    public void setTitle(String title) {
        if (jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        this.jaxbAttributeWrapper.title = title;
    }

    public String getJournal() {
        return this.jaxbAttributeWrapper != null ? this.jaxbAttributeWrapper.journal : null;
    }

    public void setJournal(String journal) {
        if (jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        this.jaxbAttributeWrapper.journal = journal;
    }

    public Date getPublicationDate() {
        return this.jaxbAttributeWrapper != null ? this.jaxbAttributeWrapper.publicationDate : null;
    }

    public void setPublicationDate(Date date) {
        if (jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        this.jaxbAttributeWrapper.publicationDate = date;
    }

    public List<String> getAuthors() {
        if (jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper.authors;
    }

    public Collection<Xref> getXrefs() {
        if (xrefContainer == null){
            xrefContainer = new PublicationXrefContainer();
        }
        return xrefContainer.getXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        if (jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper.annotations;
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            initialiseExperiments();
        }
        return this.experiments;
    }

    public CurationDepth getCurationDepth() {
        return this.jaxbAttributeWrapper != null ? this.jaxbAttributeWrapper.curationDepth : CurationDepth.undefined;
    }

    public void setCurationDepth(CurationDepth curationDepth) {

        if (getImexId() != null && curationDepth != null && !curationDepth.equals(CurationDepth.IMEx)){
            throw new IllegalArgumentException("The curationDepth " + curationDepth.toString() + " is not allowed because the publication has an IMEx id so it has IMEx curation depth.");
        }
        else if (getImexId() != null && curationDepth == null){
            throw new IllegalArgumentException("The curationDepth cannot be null/not specified because the publication has an IMEx id so it has IMEx curation depth.");
        }
        if (jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        if (curationDepth == null) {
            this.jaxbAttributeWrapper.curationDepth = CurationDepth.undefined;
        }
        else {
            this.jaxbAttributeWrapper.curationDepth = curationDepth;
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
    public void setJAXBXref(PublicationXrefContainer xrefContainer) {
        this.xrefContainer = xrefContainer;
    }

    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper wrapper) {
        this.jaxbAttributeWrapper = wrapper;
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

    @Override
    public String toString() {
        return "Bibref: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }

    protected void initialiseAnnotationWrapper(){
        this.jaxbAttributeWrapper = new JAXBAttributeWrapper();
    }

    protected void initialiseExperiments(){
        this.experiments = new ArrayList<Experiment>();
    }

    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="bibrefAttributeWrapper")
    public static class JAXBAttributeWrapper implements Locatable, FileSourceContext{
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Annotation> annotations;
        private JAXBAttributeList jaxbAttributeList;
        private String title;
        private String journal;
        private Date publicationDate;
        private List<String> authors;
        private CurationDepth curationDepth;

        public JAXBAttributeWrapper(){
            initialiseAnnotations();
            initialiseAuthors();
            this.curationDepth = CurationDepth.undefined;
            this.title = null;
            this.journal = null;
            this.publicationDate = null;
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

        protected void initialiseAnnotations(){
            annotations = new ArrayList<Annotation>();
        }

        protected void initialiseAuthors(){
            this.authors = new ArrayList<String>();
        }

        @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
        public List<Annotation> getJAXBAttributes() {
            if (this.jaxbAttributeList == null){
                this.jaxbAttributeList = new JAXBAttributeList();
            }
            return this.jaxbAttributeList;
        }

        /**
         * The attribute list used by JAXB to populate participant annotations
         */
        private class JAXBAttributeList extends ArrayList<Annotation>{

            public JAXBAttributeList(){
                super();
            }

            @Override
            public boolean add(Annotation annot) {
                return processAnnotation(null, annot);
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
                processAnnotation(index, element);
            }

            @Override
            public boolean addAll(int index, Collection<? extends Annotation> c) {
                int newIndex = index;
                if (c == null){
                    return false;
                }
                boolean add = false;
                for (Annotation a : c){
                    if (processAnnotation(newIndex, a)){
                        newIndex++;
                        add = true;
                    }
                }
                return add;
            }

            private boolean processAnnotation(Integer index, Annotation annot) {
                if (annot == null){
                    return false;
                }
                if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE)){
                    title = annot.getValue();
                    return false;
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
                    journal = annot.getValue();
                    return false;
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
                    if (annot.getValue() == null){
                        publicationDate = null;
                        return false;
                    }
                    else {
                        try {
                            publicationDate = PsiXmlUtils.YEAR_FORMAT.parse(annot.getValue().trim());
                            return false;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            publicationDate = null;
                            return addAnnotation(index, annot);
                        }
                    }
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.IMEX_CURATION_MI, Annotation.IMEX_CURATION)){
                    curationDepth = CurationDepth.IMEx;
                    return false;
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.MIMIX_CURATION_MI, Annotation.MIMIX_CURATION)){
                    curationDepth = CurationDepth.MIMIx;
                    return false;
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.RAPID_CURATION_MI, Annotation.RAPID_CURATION)){
                    curationDepth = CurationDepth.rapid_curation;
                    return false;
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                    if (annot.getValue() == null){
                        authors.clear();
                    }
                    else if (annot.getValue().contains(",")){
                        authors.addAll(Arrays.asList(annot.getValue().split(",")));
                    }
                    else {
                        authors.add(annot.getValue());
                    }
                    return false;
                }
                else {
                    return addAnnotation(index, annot);
                }
            }

            private boolean addAnnotation(Integer index, Annotation annot) {
                if (index == null){
                    return annotations.add(annot);
                }
                annotations.add(index, annot);
                return true;
            }
        }

        @Override
        public String toString() {
            return "BibRef Attribute List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }
}