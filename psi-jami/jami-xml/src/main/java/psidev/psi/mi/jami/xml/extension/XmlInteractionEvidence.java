package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.AbstractAvailabilityRef;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * Xml implementation of InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlRootElement(name = "interaction", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "interactionEvidence", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBAvailability",
        "JAXBAvailabilityRef",
        "JAXBExperimentDescriptions",
        "JAXBExperimentRefs",
        "JAXBParticipants",
        "JAXBInferredInteractions",
        "JAXBInteractionTypes",
        "JAXBModelled",
        "JAXBIntraMolecular",
        "JAXBNegative",
        "JAXBConfidences",
        "JAXBParameters",
        "JAXBAttributes"
})
public class XmlInteractionEvidence extends AbstractXmlInteraction<ParticipantEvidence> implements InteractionEvidence{

    private Availability availability;
    private Collection<Parameter> parameters;
    private boolean isInferred = false;
    private Collection<Confidence> confidences;
    private boolean isNegative;

    private Collection<VariableParameterValueSet> variableParameterValueSets;
    private List<Experiment> experiments;
    private Boolean modelled;
    @XmlLocation
    @XmlTransient
    protected Locator locator;

    private ArrayList<XmlExperiment> originalExperiments;

    public XmlInteractionEvidence() {
        super();
    }

    public XmlInteractionEvidence(String shortName) {
        super(shortName);
    }

    public XmlInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    protected void initialiseExperimentalConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseExperimentalConfidencesWith(Collection<Confidence> confidences){
        if (confidences == null){
            this.confidences = Collections.EMPTY_LIST;
        }
        else {
            this.confidences = confidences;
        }
    }

    protected void initialiseVariableParameterValueSets(){
        this.variableParameterValueSets = new ArrayList<VariableParameterValueSet>();
    }

    protected void initialiseVariableParameterValueSetsWith(Collection<VariableParameterValueSet> variableValues){
        if (variableValues == null){
            this.variableParameterValueSets = Collections.EMPTY_LIST;
        }
        else {
            this.variableParameterValueSets = variableValues;
        }
    }

    protected void initialiseExperimentalParameters(){
        this.parameters = new ArrayList<Parameter>();
    }

    protected void initialiseExperimentalParametersWith(Collection<Parameter> parameters){
        if (parameters == null){
            this.parameters = Collections.EMPTY_LIST;
        }
        else {
            this.parameters = parameters;
        }
    }

    public String getImexId() {
        return getJAXBXref() != null ? this.getJAXBXref().getImexId() : null;
    }

    public void assignImexId(String identifier) {
        if (getJAXBXref() == null && identifier != null){
            setJAXBXref(new InteractionXrefContainer());
        }
        getJAXBXref().assignImexId(identifier);
    }

    public Experiment getExperiment() {
        return (this.getExperiments() != null && !this.experiments.isEmpty())? this.experiments.iterator().next() : null;
    }

    public void setExperiment(Experiment experiment) {
        if (getExperiments() == null && experiment != null){
            this.experiments = new ArrayList<Experiment>();
            this.experiments.add(experiment);
        }
        else if (this.experiments != null){
            if (!this.experiments.isEmpty() && experiment == null){
                this.experiments.remove(0);
            }
            else if (experiment != null){
                this.experiments.remove(0);
                this.experiments.add(0, experiment);
            }
        }
    }

    public List<Experiment> getExperiments() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
    }

    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        Experiment current = getExperiment();
        if (current != null){
            current.removeInteractionEvidence(this);
        }

        if (experiment != null){
            experiment.addInteractionEvidence(this);
        }
    }

    public Collection<VariableParameterValueSet> getVariableParameterValues() {

        if (variableParameterValueSets == null){
            initialiseVariableParameterValueSets();
        }
        return this.variableParameterValueSets;
    }

    public Collection<Confidence> getConfidences() {
        if (confidences == null){
            initialiseExperimentalConfidences();
        }
        return this.confidences;
    }

    public String getAvailability() {
        return this.availability!= null ? this.availability.getJAXBValue() : null;
    }

    public void setAvailability(String availability) {
        if (this.availability == null && this.availability != null){
            this.availability = new Availability();
        }
        this.availability.setJAXBValue(availability);
    }

    public boolean isNegative() {
        return this.isNegative;
    }

    public void setNegative(boolean negative) {
        this.isNegative = negative;
    }

    public Collection<Parameter> getParameters() {
        if (parameters == null){
            initialiseExperimentalParameters();
        }
        return this.parameters;
    }

    public boolean isInferred() {
        return this.isInferred;
    }

    public void setInferred(boolean inferred) {
        this.isInferred = inferred;
    }

    @Override
    public String toString() {
        return getImexId() != null ? getImexId() : super.toString();
    }

    @Override
    @XmlElement(name = "names")
    public NamesContainer getJAXBNames() {
        return super.getJAXBNames();
    }

    @Override
    @XmlElement(name = "xref")
    public InteractionXrefContainer getJAXBXref() {
        return super.getJAXBXref();
    }

    @Override
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return super.getJAXBId();
    }

    @Override
    @XmlElementWrapper(name="attributeList")
    @XmlElements({ @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)})
    public ArrayList<Annotation> getJAXBAttributes() {
        return super.getJAXBAttributes();
    }

    @Override
    @XmlElement(name = "intraMolecular", defaultValue = "false")
    public Boolean getJAXBIntraMolecular() {
        return super.getJAXBIntraMolecular();
    }

    @XmlElementWrapper(name="participantList")
    @XmlElements({ @XmlElement(type=XmlParticipantEvidence.class, name="participant", required = true)})
    /**
     * Gets the value of the participantList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Participant> }
     *
     */
    public ArrayList<ParticipantEvidence> getJAXBParticipants() {
        if (getParticipants().isEmpty()){
            return null;
        }
        return new ArrayList<ParticipantEvidence>(getParticipants());
    }

    @Override
    @XmlElementWrapper(name="inferredInteractionList")
    @XmlElement(name="inferredInteraction", required = true)
    public ArrayList<InferredInteraction> getJAXBInferredInteractions() {
        return super.getJAXBInferredInteractions();
    }

    @Override
    @XmlElement(name="interactionType", type = XmlCvTerm.class)
    public ArrayList<CvTerm> getJAXBInteractionTypes() {
        return super.getJAXBInteractionTypes();
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), getJAXBId()));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), getJAXBId()));
        }
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Confidence> }
     *
     */
    @XmlElementWrapper(name="confidenceList")
    @XmlElements({ @XmlElement(type=XmlConfidence.class, name="confidence", required = true)})
    public ArrayList<Confidence> getJAXBConfidences() {
        if (this.confidences == null || this.confidences.isEmpty()){
            return null;
        }
        return new ArrayList<Confidence>(getConfidences());
    }

    /**
     * Sets the value of the confidenceList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Confidence> }
     *
     */
    public void setJAXBConfidences(ArrayList<XmlConfidence> value) {
        getConfidences().clear();
        if (value != null && !value.isEmpty()){
            getConfidences().addAll(value);
        }
    }

    /**
     * Gets the value of the parameterList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<ModelledParameter> }
     *
     */
    @XmlElementWrapper(name="parameterList")
    @XmlElements({ @XmlElement(type=XmlParameter.class,name="parameter", required = true)})
    public ArrayList<Parameter> getJAXBParameters() {
        if (this.parameters == null || this.parameters.isEmpty()){
            return null;
        }
        return new ArrayList<Parameter>(getParameters());
    }

    /**
     * Sets the value of the parameterList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<ModelledParameter> }
     *
     */
    public void setJAXBParameters(ArrayList<XmlParameter> value) {
        getParameters().clear();
        if (value != null && !value.isEmpty()){
            getParameters().addAll(value);
        }
    }

    /**
     * Gets the value of the availability property.
     *
     * @return
     *     possible object is
     *     {@link Availability }
     *
     */
    @XmlElement(name = "availability")
    public Availability getJAXBAvailability() {
        return availability;
    }

    /**
     * Sets the value of the availability property.
     *
     * @param value
     *     allowed object is
     *     {@link Availability }
     *
     */
    public void setJAXBAvailability(Availability value) {
        this.availability = value;
    }

    /**
     * Gets the value of the availabilityRef property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    @XmlElement(name = "availabilityRef")
    public Integer getJAXBAvailabilityRef() {
        if (availability != null){
            return availability.getJAXBId();
        }
        return null;
    }

    /**
     * Sets the value of the availabilityRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setJAXBAvailabilityRef(Integer value) {
        if (value != null){
            this.availability = new AbstractAvailabilityRef(value) {
                public boolean resolve(Map<Integer, Object> parsedObjects) {
                    if (parsedObjects.containsKey(this.ref)){
                        Object obj = parsedObjects.get(this.ref);
                        if (obj instanceof Availability){
                            availability = (Availability)obj;
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public FileSourceLocator getSourceLocator() {
                    return getInteractionLocator();
                }

                @Override
                public void setSourceLocator(FileSourceLocator sourceLocator) {
                    throw new UnsupportedOperationException("Cannot set the source locator to an availability ref");
                }

                @Override
                public Locator sourceLocation() {
                    return getInteractionSaxLocator();
                }

                @Override
                public String toString() {
                    return "Availability reference: "+ref+" in interaction "+(getInteractionLocator() != null? getInteractionLocator().toString():"") ;
                }
            };
        }
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<XmlExperiment> }
     *
     */
    @XmlElementWrapper(name="experimentList")
    @XmlElements({@XmlElement(name="experimentDescription", required = true, type = XmlExperiment.class)})
    public ArrayList<Experiment> getJAXBExperimentDescriptions() {
        if (experiments == null || experiments.isEmpty()){
           return null;
        }
        return new ArrayList<Experiment>(experiments);
    }

    /**
     * Sets the value of the experimentList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<XmlExperiment> }
     *
     */
    public void setJAXBExperimentDescriptions(ArrayList<XmlExperiment> value) {
        this.originalExperiments = value;
        getExperiments().clear();
        if (value != null && !value.isEmpty()){
            Iterator<Experiment> expIterator = experiments.iterator();
            setExperimentAndAddInteractionEvidence(expIterator.next());
            while(expIterator.hasNext()){
                experiments.add(expIterator.next());
            }
        }
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Integer> }
     *
     */
    @XmlElementWrapper(name="experimentList")
    @XmlElements({@XmlElement(name="experimentRef", required = true)})
    public ArrayList<Integer> getJAXBExperimentRefs() {
        if (experiments == null || experiments.isEmpty()){
            return null;
        }
        ArrayList<Integer> references = new ArrayList<Integer>(experiments.size());
        for (Experiment exp : experiments){
            if (exp instanceof XmlExperiment){
                references.add(((XmlExperiment) exp).getJAXBId());
            }
        }
        return references;
    }

    /**
     * Sets the value of the experimentList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Integer> }
     *
     */
    public void setJAXBExperimentRefs(ArrayList<Integer> value) {
        if (value != null && !value.isEmpty()){
            originalExperiments = new ArrayList<XmlExperiment>();
            for (Integer val : value){
                getExperiments().add(new AbstractExperimentRef(val) {
                    public boolean resolve(Map<Integer, Object> parsedObjects) {
                        if (parsedObjects.containsKey(this.ref)){
                            Object obj = parsedObjects.get(this.ref);
                            if (obj instanceof XmlExperiment){
                                experiments.remove(this);
                                experiments.add((Experiment)obj);
                                originalExperiments.add((XmlExperiment)obj);
                                return true;
                            }
                            else if (obj instanceof Experiment){
                                experiments.remove(this);
                                experiments.add((Experiment)obj);
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public String toString() {
                        return "Experiment reference: "+ref+" in interaction "+(getInteractionLocator() != null? getInteractionLocator().toString():"") ;
                    }

                    public FileSourceLocator getSourceLocator() {
                        return getInteractionLocator();
                    }

                    public void setSourceLocator(FileSourceLocator locator) {
                        throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
                    }
                });
            }
        }
    }

    /**
     * Gets the value of the modelled property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    @XmlElement(name = "modelled", defaultValue = "false")
    public Boolean getJAXBModelled() {
        return modelled;
    }

    /**
     * Sets the value of the modelled property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setJAXBModelled(Boolean value) {
        this.modelled = value;
    }

    /**
     * Gets the value of the negative property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    @XmlElement(name = "negative")
    public Boolean getJAXBNegative() {
        return isNegative;
    }

    /**
     * Sets the value of the negative property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setJAXBNegative(Boolean value) {
        if (value == null){
            this.isNegative = false;
        }
        else{
            this.isNegative = value;
        }
    }

    /**
     * Sets the value of the participantList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Participant> }
     *
     */
    public void setJAXBParticipants(ArrayList<XmlParticipantEvidence> value) {
        removeAllParticipants(getParticipants());
        if (value != null && !value.isEmpty()){
            addAllJAXBParticipants(value);
        }
    }

    protected List<XmlExperiment> getOriginalExperiments(){
        return originalExperiments;
    }

    private void addAllJAXBParticipants(Collection<? extends ParticipantEvidence> participants) {
        if (participants != null){
            for (ParticipantEvidence p : participants){
                addJAXBParticipant((XmlParticipantEvidence)p);
            }
        }
    }

    private void addJAXBParticipant(XmlParticipantEvidence part) {
        if (part != null){
            if (getParticipants().add(part)){
                part.setOriginalXmlInteraction(this);
            }
        }
    }

    private FileSourceLocator getInteractionLocator(){
        return getSourceLocator();
    }
    private Locator getInteractionSaxLocator(){
        return sourceLocation();
    }

}
