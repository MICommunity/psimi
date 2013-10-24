package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Xml im
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlRootElement(name = "experimentDescription", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "experimentType", propOrder = {
        "names",
        "JAXBPublication",
        "JAXBXref",
        "JAXBHostOrganisms",
        "JAXBInteractionDetectionMethod",
        "participantIdentificationMethod",
        "featureDetectionMethod",
        "JAXBConfidenceList",
        "JAXBAttributes"
})
public class XmlExperiment implements Experiment, FileSourceContext, Locatable{

    private NamesContainer namesContainer;
    private ExperimentXrefContainer xrefContainer;
    private ArrayList<Organism> hostOrganisms;
    private XmlCvTerm participantIdentificationMethod;
    private XmlCvTerm featureDetectionMethod;
    private int id;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private PsiXmLocator sourceLocator;
    private Publication publication;
    private Collection<Annotation> annotations;
    private CvTerm interactionDetectionMethod;
    private Collection<InteractionEvidence> interactions;
    private Collection<Confidence> confidences;
    private Collection<VariableParameter> variableParameters;
    private JAXBAttributeList jaxbAttributeList;

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
            this.hostOrganisms = new ArrayList<Organism>();
            this.hostOrganisms.add(organism);
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
        return this.xrefContainer.getAllXrefs();
    }

    public Organism getHostOrganism() {
        return (this.hostOrganisms != null && !this.hostOrganisms.isEmpty())? this.hostOrganisms.iterator().next() : null;
    }

    public void setHostOrganism(Organism organism) {
        if (this.hostOrganisms == null && organism != null){
            this.hostOrganisms = new ArrayList<Organism>();
            this.hostOrganisms.add(organism);
        }
        else if (this.hostOrganisms != null){
            if (!this.hostOrganisms.isEmpty() && organism == null){
                this.hostOrganisms.remove(0);
            }
            else if (organism != null){
                this.hostOrganisms.remove(0);
                this.hostOrganisms.add(0, organism);
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
        if (confidences == null){
            initialiseConfidences();
        }
        return confidences;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
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
    @XmlElement(name = "names")
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
    public void setNames(NamesContainer value) {
        this.namesContainer = value;
    }

    @XmlElement(name = "bibref", required = true, type = BibRef.class)
    public Publication getJAXBPublication() {
        return this.publication;
    }

    public void setJAXBPublication(Publication publication) {
        if (publication != null){
            if (!publication.getIdentifiers().isEmpty()){
                Xref firstIdentifier = publication.getIdentifiers().iterator().next();

                XmlEntryContext context = XmlEntryContext.getInstance();
                Map<Xref, Publication> mapOfPublications = context.getMapOfPublications();
                XmlEntry entry = context.getCurrentEntry();
                if (entry != null){
                    publication.setSource(entry.getSource());
                    if (entry.getSource() != null){
                       publication.setReleasedDate(entry.getSource().getJAXBReleaseDate().toGregorianCalendar().getTime());
                    }
                }

                if (mapOfPublications.containsKey(firstIdentifier)){
                    setPublicationAndAddExperiment(mapOfPublications.get(firstIdentifier));
                }
                else {
                    setPublicationAndAddExperiment(publication);
                    mapOfPublications.put(firstIdentifier, publication);
                }
            }
            else {
                setPublicationAndAddExperiment(publication);
            }
        }
        else {
            setPublicationAndAddExperiment(publication);
        }
    }

    /**
     * Gets the value of the xref property.
     *
     * @return
     *     possible object is
     *     {@link XrefContainer }
     *
     */
    @XmlElement(name = "xref")
    public ExperimentXrefContainer getJAXBXref() {
        if (xrefContainer != null && xrefContainer.isEmpty()){
            return null;
        }
        return xrefContainer;
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value
     *     allowed object is
     *     {@link XrefContainer }
     *
     */
    public void setJAXBXref(ExperimentXrefContainer value) {
        this.xrefContainer = value;
        if (value != null){
            this.xrefContainer.setPublication(this.publication);
        }
    }

    /**
     * Gets the value of the hostOrganismList property.
     *
     * @return
     *     possible object is
     *     {@link HostOrganism }
     *
     */
    @XmlElementWrapper(name="hostOrganismList")
    @XmlElements({ @XmlElement(type=HostOrganism.class, name="hostOrganism", required = true)})
    public ArrayList<Organism> getJAXBHostOrganisms() {
        return this.hostOrganisms;
    }

    /**
     * Sets the value of the hostOrganismList property.
     *
     * @param value
     *     allowed object is
     *     {@link HostOrganism }
     *
     */
    public void setJAXBHostOrganisms(ArrayList<Organism> value) {
        this.hostOrganisms = value;
    }

    /**
     * Gets the value of the interactionDetectionMethod property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElement(name = "interactionDetectionMethod", required = true, type = XmlCvTerm.class)
    public CvTerm getJAXBInteractionDetectionMethod() {
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
    @XmlElement(name = "participantIdentificationMethod")
    public XmlCvTerm getParticipantIdentificationMethod() {
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
    public void setParticipantIdentificationMethod(XmlCvTerm value) {
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
    @XmlElement(name = "featureDetectionMethod")
    public XmlCvTerm getFeatureDetectionMethod() {
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
    public void setFeatureDetectionMethod(XmlCvTerm value) {
        this.featureDetectionMethod = value;
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return
     *     possible object is
     *     {@link XmlConfidence }
     *
     */
    @XmlElementWrapper(name="confidenceList")
    @XmlElements({ @XmlElement(type=XmlConfidence.class, name="confidence", required = true)})
    public ArrayList<Confidence> getJAXBConfidenceList() {
        return (ArrayList<Confidence>)confidences;
    }

    /**
     * Sets the value of the confidenceList property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlConfidence }
     *
     */
    public void setJAXBConfidenceList(ArrayList<Confidence> value) {
        this.confidences = value;
    }

    @XmlElementWrapper(name="attributeList")
    @XmlElements({ @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)})
    public JAXBAttributeList getJAXBAttributes() {
        return jaxbAttributeList;
    }

    public void setJAXBAttributes(JAXBAttributeList annotations) {
        this.jaxbAttributeList = annotations;
    }

    /**
     * Gets the value of the id property.
     *
     */
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setJAXBId(int value) {
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

    protected void initialiseAnnotations(){
        if (jaxbAttributeList != null){
            this.annotations = new ArrayList<Annotation>(jaxbAttributeList);
            this.jaxbAttributeList = null;
        }else{
            this.annotations = new ArrayList<Annotation>();
        }
    }

    protected void initialiseInteractions(){
        this.interactions = new ArrayList<InteractionEvidence>();
    }

    protected void initialiseConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseVariableParameters(){
        this.variableParameters = new ArrayList<VariableParameter>();
    }

    ////////////////////////////////////////////////////////////////// classes

    /**
     * The attribute list used by JAXB to populate experiment annotations
     */
    public class JAXBAttributeList extends ArrayList<Annotation>{

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
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setTitle(annot.getValue());
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setJournal(annot.getValue());
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
                if (annot.getValue() == null){
                    if (publication != null){
                        publication.setPublicationDate(null);
                    }
                    return false;
                }
                else {
                    if (publication == null){
                        publication = new BibRef();
                    }
                    try {
                        publication.setPublicationDate(PsiXmlUtils.YEAR_FORMAT.parse(annot.getValue().trim()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        publication.setPublicationDate(null);
                        publication.getAnnotations().add(annot);
                    }
                    return false;
                }
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.IMEX_CURATION_MI, Annotation.IMEX_CURATION)){
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setCurationDepth(CurationDepth.IMEx);
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.MIMIX_CURATION_MI, Annotation.MIMIX_CURATION)){
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setCurationDepth(CurationDepth.MIMIx);
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.RAPID_CURATION_MI, Annotation.RAPID_CURATION)){
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setCurationDepth(CurationDepth.rapid_curation);
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                if (annot.getValue() == null){
                    if (publication != null){
                        publication.getAuthors().clear();
                    }
                    return false;
                }
                else if (annot.getValue().contains(",")){
                    if (publication == null){
                        publication = new BibRef();
                    }
                    publication.getAuthors().addAll(Arrays.asList(annot.getValue().split(",")));
                    return false;
                }
                else {
                    if (publication == null){
                        publication = new BibRef();
                    }
                    publication.getAuthors().add(annot.getValue());
                    return false;
                }
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
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setTitle(annot.getValue());
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setJournal(annot.getValue());
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
                if (annot.getValue() == null){
                    if (publication != null){
                        publication.setPublicationDate(null);
                    }
                    return false;
                }
                else {
                    if (publication == null){
                        publication = new BibRef();
                    }
                    try {
                        publication.setPublicationDate(PsiXmlUtils.YEAR_FORMAT.parse(annot.getValue().trim()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        publication.setPublicationDate(null);
                        publication.getAnnotations().add(annot);
                    }
                    return false;
                }
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.IMEX_CURATION_MI, Annotation.IMEX_CURATION)){
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setCurationDepth(CurationDepth.IMEx);
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.MIMIX_CURATION_MI, Annotation.MIMIX_CURATION)){
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setCurationDepth(CurationDepth.MIMIx);
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.RAPID_CURATION_MI, Annotation.RAPID_CURATION)){
                if (publication == null){
                    publication = new BibRef();
                }
                publication.setCurationDepth(CurationDepth.rapid_curation);
                return false;
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                if (annot.getValue() == null){
                    if (publication != null){
                        publication.getAuthors().clear();
                    }
                    return false;
                }
                else if (annot.getValue().contains(",")){
                    if (publication == null){
                        publication = new BibRef();
                    }
                    publication.getAuthors().addAll(Arrays.asList(annot.getValue().split(",")));
                    return false;
                }
                else {
                    if (publication == null){
                        publication = new BibRef();
                    }
                    publication.getAuthors().add(annot.getValue());
                    return false;
                }
            }
            else {
                super.add(index, annot);
                return true;
            }
        }
    }
}
