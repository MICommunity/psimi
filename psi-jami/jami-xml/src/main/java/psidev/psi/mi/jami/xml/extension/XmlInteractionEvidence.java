package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
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
        "JAXBExperimentList",
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
    private Boolean modelled;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBExperimentList jaxbExperimentList;
    private List<Experiment> experiments;

    public XmlInteractionEvidence() {
        super();
    }

    public XmlInteractionEvidence(String shortName) {
        super(shortName);
    }

    public XmlInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
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
        if (this.experiments == null || this.experiments.isEmpty()){
            return null;
        }
        return experiments.iterator().next();
    }

    public void setExperiment(Experiment experiment) {
        if (this.experiments == null){
           this.experiments = new ArrayList<Experiment>();
        }
        if (!experiments.isEmpty()){
            experiments.remove(0);
        }
        if (experiment != null){
           experiments.add(0, experiment);
        }
    }

    public List<Experiment> getExperiments() {
        if (jaxbExperimentList == null){
            jaxbExperimentList = new JAXBExperimentList();
        }
        return jaxbExperimentList.experiments;
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
        return this.availability!= null ? this.availability.getValue() : null;
    }

    public void setAvailability(String availability) {
        if (this.availability == null && this.availability != null){
            this.availability = new Availability();
        }
        this.availability.setValue(availability);
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

    @XmlElementWrapper(name="attributeList")
    @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
    public List<Annotation> getJAXBAttributes() {
        return super.getJAXBAttributes();
    }

    @Override
    @XmlElement(name = "intraMolecular", defaultValue = "false")
    public Boolean getJAXBIntraMolecular() {
        return super.getJAXBIntraMolecular();
    }

    /**
     * Gets the value of the participantList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Participant> }
     *
     */
    @XmlElementWrapper(name="participantList")
    @XmlElement(type=XmlParticipantEvidence.class, name="participant", required = true)
    public List<ParticipantEvidence> getJAXBParticipants() {
        return super.getJAXBParticipants();
    }

    @Override
    @XmlElementWrapper(name="inferredInteractionList")
    @XmlElement(name="inferredInteraction", required = true, type = InferredInteraction.class)
    public List<InferredInteraction> getJAXBInferredInteractions() {
        return super.getJAXBInferredInteractions();
    }

    @Override
    @XmlElement(name="interactionType", type = XmlCvTerm.class)
    public List<CvTerm> getJAXBInteractionTypes() {
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
    @XmlElement(type=XmlConfidence.class, name="confidence", required = true)
    public List<Confidence> getJAXBConfidences() {
        if (this.confidences == null){
            this.confidences = new ArrayList<Confidence>();
        }
        return (List<Confidence>)confidences;
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
    @XmlElement(type=XmlParameter.class,name="parameter", required = true)
    public List<Parameter> getJAXBParameters() {
        if (this.parameters == null){
            this.parameters = new ArrayList<Parameter>();
        }
        return (List<Parameter>)parameters;
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
        if (availability instanceof AvailabilityRef){
            return ((AvailabilityRef)availability).getRef();
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
            this.availability = new AvailabilityRef(value);
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
    @XmlElement(name="experimentList")
    public JAXBExperimentList getJAXBExperimentList() {
        return this.jaxbExperimentList;
    }

    /**
     * Sets the value of the experimentList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<XmlExperiment> }
     *
     */
    public void setJAXBExperimentList(JAXBExperimentList value) {
        this.jaxbExperimentList = value;
        // experiment list is set. Because we use back references, we need to post process.
        if (this.jaxbExperimentList != null){
            this.jaxbExperimentList.parent = this;
            // we have experiment refs, will be resolved later
            if (this.jaxbExperimentList.jaxbExperimentRefList != null){
                this.experiments = this.jaxbExperimentList.experiments;
            }
            // we have experiment descriptions
            if (this.jaxbExperimentList.jaxbExperiments != null){
                this.experiments = new ArrayList<Experiment>(this.jaxbExperimentList.jaxbExperiments.size());
                for (XmlExperiment exp : this.jaxbExperimentList.jaxbExperiments){
                    experiments.add(exp);
                    exp.getInteractionEvidences().add(this);
                }
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
    @XmlElement(name = "negative", defaultValue = "false")
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

    protected List<XmlExperiment> getOriginalExperiments(){
        return jaxbExperimentList != null ? jaxbExperimentList.jaxbExperiments : Collections.EMPTY_LIST;
    }

    protected void initialiseExperimentalConfidences(){
        this.confidences = new ArrayList<Confidence>();
    }


    protected void initialiseVariableParameterValueSets(){
        this.variableParameterValueSets = new ArrayList<VariableParameterValueSet>();
    }

    protected void initialiseExperimentalParameters(){
        this.parameters = new ArrayList<Parameter>();
    }

    @Override
    public void processAddedParticipant(ParticipantEvidence participant) {
        ((XmlParticipantEvidence)participant).setOriginalXmlInteraction(this);
    }

    private FileSourceLocator getInteractionLocator(){
        return getSourceLocator();
    }
    private Locator getInteractionSaxLocator(){
        return sourceLocation();
    }

    ////////////////////////////////////////////////////////////////////////// classes

    private class AvailabilityRef extends AbstractAvailabilityRef{
        public AvailabilityRef(int ref) {
            super(ref);
        }

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
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "experimentListType", propOrder = {
            "JAXBExperimentRefs",
            "JAXBExperimentDescriptions"
    })
    public static class JAXBExperimentList implements Locatable, FileSourceContext{

        private List<XmlExperiment> jaxbExperiments;
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Experiment> experiments;
        private JAXBExperimentRefList jaxbExperimentRefList;
        private XmlInteractionEvidence parent;

        @XmlElement(name="experimentDescription", required = true, type = XmlExperiment.class)
        public List<XmlExperiment> getJAXBExperimentDescriptions() {
            if (jaxbExperiments == null){
                jaxbExperiments = new ArrayList<XmlExperiment>();
            }
            return jaxbExperiments;
        }

        @XmlElement(name="experimentRef", required = true, type = Integer.class)
        public List<Integer> getJAXBExperimentRefs() {
            if (this.jaxbExperimentRefList == null){
                this.jaxbExperimentRefList = new JAXBExperimentRefList();
            }
            return jaxbExperimentRefList;
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

        ////////////////////////////////////////////////// Inner classes of ExperimentList

        /**
         * The experiment ref list used by JAXB to populate experiment refs
         */
        private class JAXBExperimentRefList extends ArrayList<Integer>{

            public JAXBExperimentRefList(){
                super();
                jaxbExperiments = new ArrayList<XmlExperiment>();
                experiments = new ArrayList<Experiment>();
            }

            @Override
            public boolean add(Integer val) {
                if (val == null){
                    return false;
                }
                return experiments.add(new ExperimentRef(val));
            }

            @Override
            public boolean addAll(Collection<? extends Integer> c) {
                if (c == null){
                    return false;
                }
                boolean added = false;

                for (Integer a : c){
                    if (add(a)){
                        added = true;
                    }
                }
                return added;
            }

            @Override
            public void add(int index, Integer element) {
                addToSpecificIndex(index, element);
            }

            @Override
            public boolean addAll(int index, Collection<? extends Integer> c) {
                int newIndex = index;
                if (c == null){
                    return false;
                }
                boolean add = false;
                for (Integer a : c){
                    if (addToSpecificIndex(newIndex, a)){
                        newIndex++;
                        add = true;
                    }
                }
                return add;
            }

            private boolean addToSpecificIndex(int index, Integer val) {
                if (val == null){
                    return false;
                }
                experiments.add(index, new ExperimentRef(val));
                return true;
            }

            /**
             * Experiment ref for experimental interactor
             */
            private class ExperimentRef extends AbstractExperimentRef{
                public ExperimentRef(int ref) {
                    super(ref);
                }

                public boolean resolve(Map<Integer, Object> parsedObjects) {
                    if (parsedObjects.containsKey(this.ref)){
                        Object obj = parsedObjects.get(this.ref);
                        if (obj instanceof XmlExperiment){
                            XmlExperiment exp = (XmlExperiment)obj;
                            experiments.remove(this);
                            experiments.add(exp);
                            jaxbExperiments.add(exp);

                            exp.getInteractionEvidences().add(parent);
                            return true;
                        }
                        else if (obj instanceof Experiment){
                            Experiment exp = (Experiment)obj;
                            experiments.remove(this);
                            experiments.add(exp);

                            exp.getInteractionEvidences().add(parent);
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public String toString() {
                    return "Experiment reference: "+ref+" in interaction "+(sourceLocator != null? sourceLocator.toString():"") ;
                }

                public FileSourceLocator getSourceLocator() {
                    return sourceLocator;
                }

                public void setSourceLocator(FileSourceLocator locator) {
                    throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
                }
            }
        }
    }
}
