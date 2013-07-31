package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

/**
 * Xml im
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "experimentType", propOrder = {
        "names",
        "bibRef",
        "xref",
        "hostOrganisms",
        "interactionDetectionMethod",
        "participantIdentificationMethod",
        "featureDetectionMethod",
        "confidenceList",
        "attributes"
})
public class XmlExperiment implements Experiment, FileSourceContext, Serializable{

    private NamesContainer namesContainer;
    private ExperimentXrefContainer xrefContainer;
    private Map<Integer, Object> mapOfReferencedObjects;
    private Map<Xref, Publication> mapOfPublications;
    private ArrayList<Organism> hostOrganisms;
    private XmlCvTerm participantIdentificationMethod;
    private XmlCvTerm featureDetectionMethod;
    private int id;

    private PsiXmLocator sourceLocator;

    private Publication publication;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private CvTerm interactionDetectionMethod;
    private Collection<InteractionEvidence> interactions;

    private Collection<Confidence> confidences;
    private Collection<VariableParameter> variableParameters;

    public XmlExperiment(){
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        mapOfPublications = XmlEntryContext.getInstance().getMapOfPublications();
    }

    public XmlExperiment(Publication publication){

        this.publication = publication;
        this.interactionDetectionMethod = CvTermUtils.createUnspecifiedMethod();
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        mapOfPublications = XmlEntryContext.getInstance().getMapOfPublications();
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod){

        this.publication = publication;
        if (interactionDetectionMethod == null){
            this.interactionDetectionMethod = CvTermUtils.createUnspecifiedMethod();
        }
        else {
            this.interactionDetectionMethod = interactionDetectionMethod;
        }
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        mapOfPublications = XmlEntryContext.getInstance().getMapOfPublications();
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod, Organism organism){
        this(publication, interactionDetectionMethod);
        if (organism != null){
            this.hostOrganisms = new ArrayList<Organism>();
            this.hostOrganisms.add(organism);
        }
        mapOfReferencedObjects = XmlEntryContext.getInstance().getMapOfReferencedObjects();
        mapOfPublications = XmlEntryContext.getInstance().getMapOfPublications();
    }

    protected void initialiseXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseInteractions(){
        this.interactions = new ArrayList<InteractionEvidence>();
    }

    protected void initialiseXrefsWith(Collection<Xref> xrefs){
        if (xrefs == null){
            this.xrefs = Collections.EMPTY_LIST;
        }
        else{
            this.xrefs = xrefs;
        }
    }

    protected void initialiseAnnotationsWith(Collection<Annotation> annotations){
        if (annotations == null){
            this.annotations = Collections.EMPTY_LIST;
        }
        else{
            this.annotations = annotations;
        }
    }

    protected void initialiseInteractionsWith(Collection<InteractionEvidence> interactionEvidences){
        if (interactionEvidences == null){
            this.interactions = Collections.EMPTY_LIST;
        }
        else{
            this.interactions = interactionEvidences;
        }
    }

    protected void initialiseConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseVariableParameters(){
        this.variableParameters = new ArrayList<VariableParameter>();
    }

    protected void initialiseConfidencesWith(Collection<Confidence> confs){
        if (confs == null){
            this.confidences = Collections.EMPTY_LIST;
        }
        else{
            this.confidences = confs;
        }
    }

    protected void initialiseVariableParametersWith(Collection<VariableParameter> variableParameters){
        if (variableParameters == null){
            this.variableParameters = Collections.EMPTY_LIST;
        }
        else{
            this.variableParameters = variableParameters;
        }
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

    @XmlTransient
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

    @XmlElement(name = "bibref", required = true, type = BibRef.class)
    public Publication getBibRef() {
        return this.publication;
    }

    public void setBibRef(Publication publication) {
        if (publication != null){
            if (!publication.getIdentifiers().isEmpty()){
                Xref firstIdentifier = publication.getIdentifiers().iterator().next();
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

    /**
     * Gets the value of the xref property.
     *
     * @return
     *     possible object is
     *     {@link XrefContainer }
     *
     */
    @XmlElement(name = "xref")
    public ExperimentXrefContainer getXref() {
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
    public void setXref(ExperimentXrefContainer value) {
        if (value == null){
            if (this.xrefContainer == null && this.publication != null){
                this.xrefContainer.getSecondaryRefs().clear();
                this.xrefContainer.setPrimaryRef(null);
            }
            else {
                this.xrefContainer = null;
            }
        }
        this.xrefContainer = value;
        this.xrefContainer.setPublication(this.publication);

    }

    @XmlTransient
    public Collection<Xref> getXrefs() {
        if (this.xrefContainer == null){
            this.xrefContainer = new ExperimentXrefContainer();
            this.xrefContainer.setPublication(publication);
        }
        return this.xrefContainer.getAllXrefs();
    }

    @XmlTransient
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
     * Gets the value of the hostOrganismList property.
     *
     * @return
     *     possible object is
     *     {@link HostOrganism }
     *
     */
    @XmlElementWrapper(name="hostOrganismList")
    @XmlElement(name="hostOrganism", required = true)
    @XmlElementRefs({ @XmlElementRef(type=HostOrganism.class)})
    public ArrayList<Organism> getHostOrganisms() {
        if (this.hostOrganisms != null && this.hostOrganisms.isEmpty()){
            this.hostOrganisms = null;
        }
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
    public void setHostOrganisms(ArrayList<Organism> value) {
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

    @XmlTransient
    public Collection<Confidence> getConfidences() {
        if (confidences == null){
            initialiseConfidences();
        }
        return confidences;
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
    @XmlElement(name="confidence", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlConfidence.class)})
    public ArrayList<Confidence> getConfidenceList() {
        if (getConfidences().isEmpty()){
           return null;
        }
        return new ArrayList<Confidence>(getConfidences());
    }

    /**
     * Sets the value of the confidenceList property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlConfidence }
     *
     */
    public void setConfidenceList(ArrayList<Confidence> value) {
        getConfidences().clear();
        if (value != null){
            getConfidences().addAll(value);
        }
    }

    @XmlTransient
    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    @XmlElementWrapper(name="attributeList")
    @XmlElement(name="attribute", required = true)
    @XmlElementRefs({ @XmlElementRef(type=XmlAnnotation.class)})
    public ArrayList<Annotation> getAttributes() {
        if ((this.publication == null && getAnnotations().isEmpty())
                || (this.publication != null &&
                ((this.publication.getAnnotations().isEmpty() && this.publication.getTitle() == null && this.publication.getJournal() == null && this.publication.getPublicationDate() == null
                        && this.publication.getAuthors().isEmpty() && this.publication.getCurationDepth() == CurationDepth.undefined) ||
                        ((!this.publication.getAnnotations().isEmpty() || this.publication.getTitle() != null || this.publication.getJournal() != null || this.publication.getPublicationDate() != null
                                && !this.publication.getAuthors().isEmpty() || this.publication.getCurationDepth() != CurationDepth.undefined) && this.publication.getIdentifiers().isEmpty() && this.publication.getXrefs().isEmpty()))
                && getAnnotations().isEmpty())){
            return null;
        }
        else{
            ArrayList<Annotation> annots = new ArrayList<Annotation>(getAnnotations().size());
            annots.addAll(getAnnotations());

            if (this.publication != null){
                // basic publication annotation first
                for (Annotation annot : this.publication.getAnnotations()){
                    if (!annots.contains(annot)){
                        annots.add(annot);
                    }
                }
                if (this.publication.getTitle() != null){
                    Annotation annot = new XmlAnnotation(new DefaultCvTerm(Annotation.PUBLICATION_TITLE, Annotation.PUBLICATION_TITLE_MI), this.publication.getTitle());
                    if (!annots.contains(annot)){
                        annots.add(annot);
                    }
                }
                if (this.publication.getJournal() != null){
                    Annotation annot = new XmlAnnotation(new DefaultCvTerm(Annotation.PUBLICATION_JOURNAL, Annotation.PUBLICATION_JOURNAL_MI), this.publication.getJournal());
                    if (!annots.contains(annot)){
                        annots.add(annot);
                    }
                }
                if (this.publication.getPublicationDate() != null){
                    Annotation annot = new XmlAnnotation(new DefaultCvTerm(Annotation.PUBLICATION_YEAR, Annotation.PUBLICATION_YEAR_MI), PsiXmlUtils.YEAR_FORMAT.format(this.publication.getPublicationDate()));
                    if (!annots.contains(annot)){
                        annots.add(annot);
                    }
                }

                switch (this.publication.getCurationDepth()){
                    case IMEx:
                        Annotation annot = new XmlAnnotation(new DefaultCvTerm(Annotation.IMEX_CURATION, Annotation.IMEX_CURATION_MI));
                        if (!annots.contains(annot)){
                            annots.add(annot);
                        }
                        break;
                    case MIMIx:
                        annot = new XmlAnnotation(new DefaultCvTerm(Annotation.MIMIX_CURATION, Annotation.MIMIX_CURATION_MI));
                        if (!annots.contains(annot)){
                            annots.add(annot);
                        }
                        break;
                    case rapid_curation:
                        annot = new XmlAnnotation(new DefaultCvTerm(Annotation.RAPID_CURATION, Annotation.RAPID_CURATION_MI));
                        if (!annots.contains(annot)){
                            annots.add(annot);
                        }
                        break;
                    default:
                        break;
                }

                if (!this.publication.getAuthors().isEmpty()){
                    Annotation annot = new XmlAnnotation(new DefaultCvTerm(Annotation.AUTHOR, Annotation.AUTHOR_MI), StringUtils.join(this.publication.getAuthors(), ","));
                    if (!annots.contains(annot)){
                        annots.add(annot);
                    }
                }
            }

            return annots;
        }
    }

    public void setAttributes(ArrayList<Annotation> annotations) {
        getAnnotations().clear();
        if (annotations != null && !annotations.isEmpty()){
            // we have a bibref. Some annotations can be processed
            for (Annotation annot : annotations){
                if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_TITLE_MI, Annotation.PUBLICATION_TITLE)){
                    if (this.publication == null){
                       this.publication = new BibRef();
                    }
                    this.publication.setTitle(annot.getValue());
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_JOURNAL_MI, Annotation.PUBLICATION_JOURNAL)){
                    if (this.publication == null){
                        this.publication = new BibRef();
                    }
                    this.publication.setJournal(annot.getValue());
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.PUBLICATION_YEAR_MI, Annotation.PUBLICATION_YEAR)){
                    if (annot.getValue() == null){
                        if (this.publication != null){
                            this.publication.setPublicationDate(null);
                        }
                    }
                    else {
                        if (this.publication == null){
                            this.publication = new BibRef();
                        }
                        try {
                            this.publication.setPublicationDate(PsiXmlUtils.YEAR_FORMAT.parse(annot.getValue().trim()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            this.publication.setPublicationDate(null);
                            this.publication.getAnnotations().add(annot);
                        }
                    }
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.IMEX_CURATION_MI, Annotation.IMEX_CURATION)){
                    if (this.publication == null){
                        this.publication = new BibRef();
                    }
                    this.publication.setCurationDepth(CurationDepth.IMEx);
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.MIMIX_CURATION_MI, Annotation.MIMIX_CURATION)){
                    if (this.publication == null){
                        this.publication = new BibRef();
                    }
                    this.publication.setCurationDepth(CurationDepth.MIMIx);
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.RAPID_CURATION_MI, Annotation.RAPID_CURATION)){
                    if (this.publication == null){
                        this.publication = new BibRef();
                    }
                    this.publication.setCurationDepth(CurationDepth.rapid_curation);
                }
                else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.AUTHOR_MI, Annotation.AUTHOR)){
                    if (annot.getValue() == null){
                        if (this.publication != null){
                            this.publication.getAuthors().clear();
                        }
                    }
                    else if (annot.getValue().contains(",")){
                        if (this.publication == null){
                            this.publication = new BibRef();
                        }
                        this.publication.getAuthors().addAll(Arrays.asList(annot.getValue().split(",")));
                    }
                    else {
                        if (this.publication == null){
                            this.publication = new BibRef();
                        }
                        this.publication.getAuthors().add(annot.getValue());
                    }
                }
                else {
                    getAnnotations().add(annot);
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
        this.mapOfReferencedObjects.put(this.id, this);
    }

    @XmlTransient
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

    @XmlTransient
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

    @Override
    public String toString() {
        return publication.toString() + "( " + interactionDetectionMethod.toString() + (getHostOrganism() != null ? ", " + getHostOrganism().toString():"") + " )";
    }

    @XmlLocation
    @XmlTransient
    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
    }

    @XmlTransient
    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
    }
}
