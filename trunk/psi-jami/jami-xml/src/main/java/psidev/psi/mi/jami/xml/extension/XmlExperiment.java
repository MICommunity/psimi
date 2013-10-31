package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.XmlEntry;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.text.ParseException;
import java.util.*;

/**
 * Xml im
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlRootElement(name = "experimentDescription", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlExperiment implements ExtendedPsi25Experiment, FileSourceContext, Locatable{

    private NamesContainer namesContainer;
    private ExperimentXrefContainer xrefContainer;
    private CvTerm participantIdentificationMethod;
    private CvTerm featureDetectionMethod;
    private int id;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private PsiXmLocator sourceLocator;
    private Publication publication;
    private CvTerm interactionDetectionMethod;
    private Collection<InteractionEvidence> interactions;
    private Collection<VariableParameter> variableParameters;

    private JAXBAttributeWrapper jaxbAttributeWrapper;
    private JAXBHostOrganismWrapper jaxbHostOrganismWrapper;
    private JAXBConfidenceWrapper jaxbConfidenceWrapper;

    public XmlExperiment(){
    }

    public XmlExperiment(Publication publication){

        this.publication = publication;
        this.interactionDetectionMethod = CvTermUtils.createUnspecifiedMethod();
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod){

        this.publication = publication;
        if (interactionDetectionMethod == null){
            this.interactionDetectionMethod = CvTermUtils.createUnspecifiedMethod();
        }
        else {
            this.interactionDetectionMethod = interactionDetectionMethod;
        }
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod, Organism organism){
        this(publication, interactionDetectionMethod);
        if (organism != null){
            this.jaxbHostOrganismWrapper = new JAXBHostOrganismWrapper();
            this.jaxbHostOrganismWrapper.hostOrganisms.add(organism);
        }
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
        if (this.xrefContainer == null){
            this.xrefContainer = new ExperimentXrefContainer();
        }
        this.xrefContainer.setPublication(this.publication);
    }

    public void setPublicationAndAddExperiment(Publication publication) {
        if (this.publication != null){
            this.publication.removeExperiment(this);
        }

        if (publication != null){
            publication.addExperiment(this);
        }

        if (this.xrefContainer == null){
            this.xrefContainer = new ExperimentXrefContainer();
        }
        this.xrefContainer.setPublication(this.publication);
    }

    public Collection<Xref> getXrefs() {
        if (this.xrefContainer == null){
            this.xrefContainer = new ExperimentXrefContainer();
            this.xrefContainer.setPublication(publication);
        }
        return this.xrefContainer.getXrefs();
    }

    public Organism getHostOrganism() {
        return (this.jaxbHostOrganismWrapper != null && !this.jaxbHostOrganismWrapper.hostOrganisms.isEmpty())? this.jaxbHostOrganismWrapper.hostOrganisms.iterator().next() : null;
    }

    public void setHostOrganism(Organism organism) {
        if (this.jaxbHostOrganismWrapper == null && organism != null){
            this.jaxbHostOrganismWrapper = new JAXBHostOrganismWrapper();
            this.jaxbHostOrganismWrapper.hostOrganisms.add(organism);
        }
        else if (organism != null){
            if (!this.jaxbHostOrganismWrapper.hostOrganisms.isEmpty()){
                this.jaxbHostOrganismWrapper.hostOrganisms.remove(0);
            }
            this.jaxbHostOrganismWrapper.hostOrganisms.add(0, organism);
        }
        else{
            if (!this.jaxbHostOrganismWrapper.hostOrganisms.isEmpty()){
                this.jaxbHostOrganismWrapper.hostOrganisms.remove(0);
            }
        }
    }

    /**
     * Gets the value of the interactionDetectionMethod property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    public CvTerm getInteractionDetectionMethod() {
        return interactionDetectionMethod;
    }

    /**
     * Sets the value of the interactionDetectionMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    public void setInteractionDetectionMethod(CvTerm value) {
        if (value == null){
            this.interactionDetectionMethod = new XmlCvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI);
        }
        else{
            this.interactionDetectionMethod = value;
        }
    }

    public Collection<Confidence> getConfidences() {
        if (jaxbConfidenceWrapper == null){
            initialiseConfidenceWrapper();
        }
        return this.jaxbConfidenceWrapper.confidences;
    }

    public Collection<Annotation> getAnnotations() {
        if (jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper.annotations;
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        if (interactions == null){
            initialiseInteractions();
        }
        return this.interactions;
    }

    public boolean addInteractionEvidence(InteractionEvidence evidence) {
        if (evidence == null){
            return false;
        }

        if (getInteractionEvidences().add(evidence)){
            evidence.setExperiment(this);
            return true;
        }
        return false;
    }

    public boolean removeInteractionEvidence(InteractionEvidence evidence) {
        if (evidence == null){
            return false;
        }

        if (getInteractionEvidences().remove(evidence)){
            evidence.setExperiment(null);
            return true;
        }
        return false;
    }

    public boolean addAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        if (evidences == null){
            return false;
        }

        boolean added = false;
        for (InteractionEvidence ev : evidences){
            if (addInteractionEvidence(ev)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        if (evidences == null){
            return false;
        }

        boolean removed = false;
        for (InteractionEvidence ev : evidences){
            if (removeInteractionEvidence(ev)){
                removed = true;
            }
        }
        return removed;
    }

    public Collection<VariableParameter> getVariableParameters() {
        if (variableParameters == null){
            initialiseVariableParameters();
        }
        return variableParameters;
    }

    public boolean addVariableParameter(VariableParameter variableParameter) {
        if (variableParameter == null){
            return false;
        }

        if (getVariableParameters().add(variableParameter)){
            variableParameter.setExperiment(this);
            return true;
        }
        return false;
    }

    public boolean removeVariableParameter(VariableParameter variableParameter) {
        if (variableParameter == null){
            return false;
        }

        if (getVariableParameters().remove(variableParameter)){
            variableParameter.setExperiment(null);
            return true;
        }
        return false;
    }

    public boolean addAllVariableParameters(Collection<? extends VariableParameter> variableParameters) {
        if (variableParameters == null){
            return false;
        }

        boolean added = false;
        for (VariableParameter param : variableParameters){
            if (addVariableParameter(param)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllVariableParameters(Collection<? extends VariableParameter> variableParameters) {
        if (variableParameters == null){
            return false;
        }

        boolean removed = false;
        for (VariableParameter param : variableParameters){
            if (removeVariableParameter(param)){
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Gets the value of the namesContainer property.
     *
     * @return
     *     possible object is
     *     {@link NamesContainer }
     *
     */
    public NamesContainer getNames() {
        return namesContainer;
    }

    /**
     * Sets the value of the namesContainer property.
     *
     * @param value
     *     allowed object is
     *     {@link NamesContainer }
     *
     */
    @XmlElement(name = "names")
    public void setNames(NamesContainer value) {
        this.namesContainer = value;
    }

    @XmlElement(name = "bibref", required = true, type = BibRef.class)
    public void setJAXBPublication(Publication publication) {
        setPublicationAndAddExperiment(publication);
        if (publication != null){
            XmlEntryContext context = XmlEntryContext.getInstance();
            XmlEntry entry = context.getCurrentEntry();
            if (entry != null){
                publication.setSource(entry.getSource());
                if (entry.getSource() != null){
                    publication.setReleasedDate(entry.getSource().getReleaseDate().toGregorianCalendar().getTime());
                }
            }
        }
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value
     *     allowed object is
     *     {@link XrefContainer }
     *
     */
    @XmlElement(name = "xref")
    public void setJAXBXref(ExperimentXrefContainer value) {
        this.xrefContainer = value;
        if (value != null){
            this.xrefContainer.setPublication(this.publication);
        }
    }

    @XmlElement(name="hostOrganismList")
    public void setJAXBHostOrganismWrapper(JAXBHostOrganismWrapper wrapper) {
        this.jaxbHostOrganismWrapper = wrapper;
    }

    /**
     * Sets the value of the interactionDetectionMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "interactionDetectionMethod", required = true, type = XmlCvTerm.class)
    public void setJAXBInteractionDetectionMethod(CvTerm value) {
        if (value == null){
            this.interactionDetectionMethod = new XmlCvTerm(Experiment.UNSPECIFIED_METHOD, Experiment.UNSPECIFIED_METHOD_MI);
        }
        else{
            this.interactionDetectionMethod = value;
        }
    }

    /**
     * Gets the value of the participantIdentificationMethod property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    public CvTerm getParticipantIdentificationMethod() {
        return participantIdentificationMethod;
    }

    /**
     * Sets the value of the participantIdentificationMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "participantIdentificationMethod", type = XmlCvTerm.class)
    public void setParticipantIdentificationMethod(CvTerm value) {
        this.participantIdentificationMethod = value;
    }

    /**
     * Gets the value of the featureDetectionMethod property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    public CvTerm getFeatureDetectionMethod() {
        return featureDetectionMethod;
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "featureDetectionMethod", type = XmlCvTerm.class)
    public void setFeatureDetectionMethod(CvTerm value) {
        this.featureDetectionMethod = value;
    }

    @XmlElement(name="confidenceList")
    public void setJAXBConfidenceWrapper(JAXBConfidenceWrapper wrapper) {
        this.jaxbConfidenceWrapper = wrapper;
    }

    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper wrapper) {
        this.jaxbAttributeWrapper = wrapper;
        // in case we have publication annotations, we can update publication
        if (this.jaxbAttributeWrapper != null){
            if (publication == null){
                publication = new BibRef();
                publication.setCurationDepth(this.jaxbAttributeWrapper.curationDepth);
                publication.setTitle(this.jaxbAttributeWrapper.title);
                publication.setPublicationDate(this.jaxbAttributeWrapper.publicationDate);
                publication.setJournal(this.jaxbAttributeWrapper.journal);
                if (!this.jaxbAttributeWrapper.authors.isEmpty()){
                    publication.getAuthors().addAll(this.jaxbAttributeWrapper.authors);
                }
            }
            else{
                // set curation depth
                if (this.jaxbAttributeWrapper.curationDepth != CurationDepth.undefined){
                    if (publication.getCurationDepth() == CurationDepth.undefined){
                        publication.setCurationDepth(this.jaxbAttributeWrapper.curationDepth);
                    }
                    else {
                        this.jaxbAttributeWrapper.annotations.add(new XmlAnnotation(CvTermUtils.createMICvTerm(
                                        Annotation.CURATION_DEPTH, Annotation.CURATION_DEPTH_MI),
                                        this.jaxbAttributeWrapper.curationDepth.toString()));
                        this.jaxbAttributeWrapper.curationDepth = null;
                    }
                }
                // authors
                if (!this.jaxbAttributeWrapper.authors.isEmpty() && this.publication.getAuthors().isEmpty()){
                    this.publication.getAuthors().addAll(this.jaxbAttributeWrapper.authors);
                    this.jaxbAttributeWrapper.authors = null;
                }
                else {
                    this.jaxbAttributeWrapper.annotations.add(new XmlAnnotation(CvTermUtils.createMICvTerm(
                            Annotation.AUTHOR, Annotation.AUTHOR_MI),
                            StringUtils.join(this.jaxbAttributeWrapper.authors,",")));
                    this.jaxbAttributeWrapper.authors = null;
                }
                // title
                if (this.jaxbAttributeWrapper.title != null && this.publication.getTitle() == null){
                    this.publication.setTitle(this.jaxbAttributeWrapper.title);
                    this.jaxbAttributeWrapper.title = null;
                }
                else if (getFullName() != null && this.publication.getTitle() == null){
                    this.publication.setTitle(getFullName());
                    this.jaxbAttributeWrapper.title = null;
                }
                else {
                    this.jaxbAttributeWrapper.annotations.add(new XmlAnnotation(CvTermUtils.createMICvTerm(
                            Annotation.PUBLICATION_TITLE, Annotation.PUBLICATION_TITLE_MI),
                            this.jaxbAttributeWrapper.title));
                    this.jaxbAttributeWrapper.title = null;
                }
                // journal
                if (this.jaxbAttributeWrapper.journal != null && this.publication.getJournal()== null){
                    this.publication.setJournal(this.jaxbAttributeWrapper.journal);
                    this.jaxbAttributeWrapper.journal = null;
                }
                else {
                    this.jaxbAttributeWrapper.annotations.add(new XmlAnnotation(CvTermUtils.createMICvTerm(
                            Annotation.PUBLICATION_JOURNAL, Annotation.PUBLICATION_JOURNAL_MI),
                            this.jaxbAttributeWrapper.journal));
                    this.jaxbAttributeWrapper.journal = null;
                }
                // publicationDate
                if (this.jaxbAttributeWrapper.publicationDate != null && this.publication.getPublicationDate() == null){
                    this.publication.setPublicationDate(this.jaxbAttributeWrapper.publicationDate);
                    this.jaxbAttributeWrapper.publicationDate = null;
                }
                else {
                    this.jaxbAttributeWrapper.annotations.add(new XmlAnnotation(CvTermUtils.createMICvTerm(
                            Annotation.PUBLICATION_YEAR, Annotation.PUBLICATION_YEAR_MI),
                            PsiXmlUtils.YEAR_FORMAT.format(this.jaxbAttributeWrapper.publicationDate)));
                    this.jaxbAttributeWrapper.publicationDate = null;
                }
            }
        }
    }

    /**
     * Gets the value of the id property.
     *
     */
    @XmlAttribute(name = "id", required = true)
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setId(int value) {
        this.id = value;
        XmlEntryContext.getInstance().getMapOfReferencedObjects().put(this.id, this);
        if (getSourceLocator() != null){
            sourceLocator.setObjectId(this.id);
        }
    }

    @Override
    public String toString() {
        return publication.toString() + "( " + interactionDetectionMethod.toString() + (getHostOrganism() != null ? ", " + getHostOrganism().toString():"") + " )";
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), id);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), id);
        }
    }

    @Override
    public String getShortLabel() {
        return this.namesContainer != null ? this.namesContainer.getShortLabel():null;
    }

    @Override
    public void setShortLabel(String name) {
        if (this.namesContainer == null){
            this.namesContainer = new NamesContainer();
        }
        this.namesContainer.setShortLabel(name);
    }

    @Override
    public String getFullName() {
        return this.namesContainer != null ? this.namesContainer.getFullName():null;
    }

    @Override
    public void setFullName(String name) {
        if (this.namesContainer == null){
            this.namesContainer = new NamesContainer();
        }
        this.namesContainer.setFullName(name);
    }

    @Override
    public List<Alias> getAliases() {
        if (this.namesContainer == null){
            this.namesContainer = new NamesContainer();
        }
        return this.namesContainer.getAliases();
    }

    @Override
    public List<Organism> getHostOrganisms() {
        if (this.jaxbHostOrganismWrapper == null){
            this.jaxbHostOrganismWrapper = new JAXBHostOrganismWrapper();
        }
        return this.jaxbHostOrganismWrapper.hostOrganisms;
    }

    protected void initialiseAnnotationWrapper(){
        this.jaxbAttributeWrapper = new JAXBAttributeWrapper();
    }

    protected void initialiseInteractions(){
        this.interactions = new ArrayList<InteractionEvidence>();
    }

    protected void initialiseConfidenceWrapper(){
        this.jaxbConfidenceWrapper = new JAXBConfidenceWrapper();
    }

    protected void initialiseVariableParameters(){
        this.variableParameters = new ArrayList<VariableParameter>();
    }

    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentAttributeWrapper")
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

        public boolean containsPublicationAnnotations(){
            return this.curationDepth != CurationDepth.undefined || !this.authors.isEmpty()
                    || this.title != null || this.journal != null || this.publicationDate != null;
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
         * The attribute list used by JAXB to populate experiment annotations
         */
        private class JAXBAttributeList extends ArrayList<Annotation>{

            public JAXBAttributeList(){
                super();
                annotations = new ArrayList<Annotation>();
            }

            public JAXBAttributeList(int initialCapacity) {
                super(initialCapacity);
            }

            public JAXBAttributeList(Collection<? extends Annotation> c) {
                super(c);
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
                            addAnnotation(index, annot);
                            return true;
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
                        return false;
                    }
                    else if (annot.getValue().contains(",")){
                        authors.addAll(Arrays.asList(annot.getValue().split(",")));
                        return false;
                    }
                    else {
                        authors.add(annot.getValue());
                        return false;
                    }
                }
                else {
                    return addAnnotation(index, annot);
                }
            }

            private boolean addAnnotation(Integer index, Annotation annot) {
                if (index == null){
                    return annotations.add(annot);
                }
                else{
                    annotations.add(index, annot);
                    return true;
                }
            }
        }

    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentOrganismWrapper")
    public static class JAXBHostOrganismWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Organism> hostOrganisms;

        public JAXBHostOrganismWrapper(){
            initialiseHostOrganisms();
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

        @XmlElement(type=XmlOrganism.class, name="hostOrganism", required = true)
        public List<Organism> getJAXBHostOrganisms() {
            return this.hostOrganisms;
        }

        protected void initialiseHostOrganisms(){
            this.hostOrganisms = new ArrayList<Organism>();
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentConfidenceWrapper")
    public static class JAXBConfidenceWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Confidence> confidences;

        public JAXBConfidenceWrapper(){
            initialiseConfidences();
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

        @XmlElement(type=XmlModelledConfidence.class, name="confidence", required = true)
        public List<Confidence> getJAXBConfidences() {
            return this.confidences;
        }

        protected void initialiseConfidences(){
            this.confidences = new ArrayList<Confidence>();
        }
    }
}
