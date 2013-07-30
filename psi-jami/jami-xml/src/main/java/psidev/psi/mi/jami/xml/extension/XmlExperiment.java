package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.internal.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import javax.annotation.Generated;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Xml im
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "experimentType", propOrder = {
        "names",
        "bibRef",
        "xref",
        "hostOrganismList",
        "interactionDetectionMethod",
        "participantIdentificationMethod",
        "featureDetectionMethod",
        "confidenceList",
        "attributeList"
})
public class XmlExperiment implements Experiment, FileSourceContext, Serializable{

    private NamesContainer namesContainer;
    private XrefContainer xrefContainer;
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    protected ExperimentType.HostOrganismList hostOrganismList;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    protected CvType interactionDetectionMethod;
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    protected CvType participantIdentificationMethod;
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    protected CvType featureDetectionMethod;
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    protected ConfidenceListType confidenceList;
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    protected AttributeListType attributeList;
    @XmlAttribute(name = "id", required = true)
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    protected int id;

    private PsiXmLocator sourceLocator;

    private Publication publication;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private CvTerm interactionDetectionMethod;
    private Organism hostOrganism;
    private Collection<InteractionEvidence> interactions;

    private Collection<Confidence> confidences;
    private Collection<VariableParameter> variableParameters;

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
        this.hostOrganism = organism;
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
    }

    @XmlElement(name = "bibref", required = true, type = BibRef.class)
    public Publication getBibRef() {
        return this.publication;
    }

    public void setBibRef(Publication publication) {
        setPublicationAndAddExperiment(publication);
    }

    public void setPublicationAndAddExperiment(Publication publication) {
        if (this.publication != null){
            this.publication.removeExperiment(this);
        }

        if (publication != null){
            publication.addExperiment(this);
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
    public XrefContainer getXref() {
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
    public void setXref(XrefContainer value) {
        this.xrefContainer = value;
    }

    public Collection<Xref> getXrefs() {
        if (xrefs == null){
            initialiseXrefs();
        }
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    public Collection<Confidence> getConfidences() {
        if (confidences == null){
            initialiseConfidences();
        }
        return confidences;
    }

    public CvTerm getInteractionDetectionMethod() {
        return this.interactionDetectionMethod;
    }

    public void setInteractionDetectionMethod(CvTerm term) {
        if (term == null){
            this.interactionDetectionMethod = CvTermUtils.createUnspecifiedMethod();
        }
        else{
            this.interactionDetectionMethod = term;
        }
    }

    public Organism getHostOrganism() {
        return this.hostOrganism;
    }

    public void setHostOrganism(Organism organism) {
        this.hostOrganism = organism;
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

    @Override
    public String toString() {
        return publication.toString() + "( " + interactionDetectionMethod.toString() + (hostOrganism != null ? ", " + hostOrganism.toString():"") + " )";
    }

    /**
     * Gets the value of the hostOrganismList property.
     *
     * @return
     *     possible object is
     *     {@link ExperimentType.HostOrganismList }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public ExperimentType.HostOrganismList getHostOrganismList() {
        return hostOrganismList;
    }

    /**
     * Sets the value of the hostOrganismList property.
     *
     * @param value
     *     allowed object is
     *     {@link ExperimentType.HostOrganismList }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public void setHostOrganismList(ExperimentType.HostOrganismList value) {
        this.hostOrganismList = value;
    }

    /**
     * Gets the value of the interactionDetectionMethod property.
     *
     * @return
     *     possible object is
     *     {@link CvType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public CvType getInteractionDetectionMethod() {
        return interactionDetectionMethod;
    }

    /**
     * Sets the value of the interactionDetectionMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link CvType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public void setInteractionDetectionMethod(CvType value) {
        this.interactionDetectionMethod = value;
    }

    /**
     * Gets the value of the participantIdentificationMethod property.
     *
     * @return
     *     possible object is
     *     {@link CvType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public CvType getParticipantIdentificationMethod() {
        return participantIdentificationMethod;
    }

    /**
     * Sets the value of the participantIdentificationMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link CvType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public void setParticipantIdentificationMethod(CvType value) {
        this.participantIdentificationMethod = value;
    }

    /**
     * Gets the value of the featureDetectionMethod property.
     *
     * @return
     *     possible object is
     *     {@link CvType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public CvType getFeatureDetectionMethod() {
        return featureDetectionMethod;
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value
     *     allowed object is
     *     {@link CvType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public void setFeatureDetectionMethod(CvType value) {
        this.featureDetectionMethod = value;
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return
     *     possible object is
     *     {@link ConfidenceListType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public ConfidenceListType getConfidenceList() {
        return confidenceList;
    }

    /**
     * Sets the value of the confidenceList property.
     *
     * @param value
     *     allowed object is
     *     {@link ConfidenceListType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public void setConfidenceList(ConfidenceListType value) {
        this.confidenceList = value;
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return
     *     possible object is
     *     {@link AttributeListType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public AttributeListType getAttributeList() {
        return attributeList;
    }

    /**
     * Sets the value of the attributeList property.
     *
     * @param value
     *     allowed object is
     *     {@link AttributeListType }
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public void setAttributeList(AttributeListType value) {
        this.attributeList = value;
    }

    /**
     * Gets the value of the id property.
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    @Generated(value = "com.sun.tools.xjc.Driver", date = "2013-04-03T12:49:45+01:00", comments = "JAXB RI vhudson-jaxb-ri-2.1-2")
    public void setId(int value) {
        this.id = value;
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
