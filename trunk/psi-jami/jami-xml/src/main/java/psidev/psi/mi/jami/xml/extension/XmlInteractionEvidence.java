package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.AbstractAvailabilityRef;
import psidev.psi.mi.jami.xml.AbstractExperimentRef;
import psidev.psi.mi.jami.xml.AbstractInteractionAttributeList;
import psidev.psi.mi.jami.xml.AbstractInteractionParticipantList;

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
    private JAXBAttributeList jaxbAttributeList;
    private JAXBParticipantList jaxbParticipantList;

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
        List<Experiment> experiments = getExperiments();
        return (!experiments.isEmpty())? experiments.iterator().next() : null;
    }

    public void setExperiment(Experiment experiment) {
        List<Experiment> experiments = getExperiments();
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
    public JAXBAttributeList getJAXBAttributes() {
        return jaxbAttributeList;
    }

    /**
     * Sets the value of the attributeList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList< psidev.psi.mi.jami.model.Annotation >  }
     *
     */
    public void setJAXBAttributes(JAXBAttributeList  value) {
        this.jaxbAttributeList = value;
        if (value != null){
            this.jaxbAttributeList.setParent(this);
        }
    }

    @Override
    @XmlElement(name = "intraMolecular", defaultValue = "false")
    public Boolean getJAXBIntraMolecular() {
        return super.getJAXBIntraMolecular();
    }

    @XmlElementWrapper(name="participantList")
    @XmlElement(type=XmlParticipantEvidence.class, name="participant", required = true)
    /**
     * Gets the value of the participantList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<Participant> }
     *
     */
    public JAXBParticipantList getJAXBParticipants() {
        return jaxbParticipantList;
    }

    public void setJAXBParticipants(JAXBParticipantList jaxbParticipantList) {
        this.jaxbParticipantList = jaxbParticipantList;
        if (jaxbParticipantList != null){
            this.jaxbParticipantList.setParent(this);
        }
    }

    @Override
    @XmlElementWrapper(name="inferredInteractionList")
    @XmlElement(name="inferredInteraction", required = true)
    public ArrayList<InferredInteraction> getJAXBInferredInteractions() {
        return super.getJAXBInferredInteractions();
    }

    @Override
    @XmlElements({@XmlElement(name="interactionType", type = XmlCvTerm.class)})
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
    @XmlElement(type=XmlConfidence.class, name="confidence", required = true)
    public ArrayList<Confidence> getJAXBConfidences() {
        return (ArrayList<Confidence>)confidences;
    }

    /**
     * Sets the value of the confidenceList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Confidence> }
     *
     */
    public void setJAXBConfidences(ArrayList<Confidence> value) {
        this.confidences = value;
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
    public ArrayList<Parameter> getJAXBParameters() {
        return (ArrayList<Parameter>)parameters;
    }

    /**
     * Sets the value of the parameterList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<ModelledParameter> }
     *
     */
    public void setJAXBParameters(ArrayList<Parameter> value) {
        this.parameters = value;
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
        if (value != null){
            this.jaxbExperimentList.parent = this;
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
        return jaxbExperimentList != null ? jaxbExperimentList.originalExperiments : Collections.EMPTY_LIST;
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

    @Override
    protected void initialiseAnnotations(){
        if (jaxbAttributeList != null){
            super.initialiseAnnotationsWith(new ArrayList<Annotation>(jaxbAttributeList));
            this.jaxbAttributeList = null;
        }else{
            super.initialiseAnnotations();
        }
    }

    @Override
    protected void initialiseParticipants(){
        if (jaxbParticipantList != null){
            super.initialiseParticipantsWith(new ArrayList<ParticipantEvidence>(jaxbParticipantList));
            this.jaxbParticipantList = null;
        }else{
            super.initialiseParticipants();
        }
    }

    ////////////////////////////////////////////////////////////////////////// classes

    /**
     * The attribute list used by JAXB to populate interaction annotations
     */
    public static class JAXBAttributeList extends AbstractInteractionAttributeList<XmlInteractionEvidence> {

        public JAXBAttributeList(){
            super();
        }
    }

    /**
     * The participant list used by JAXB to populate interaction participants
     */
    public static class JAXBParticipantList<T extends Participant> extends AbstractInteractionParticipantList<ParticipantEvidence, XmlInteractionEvidence> {

        public JAXBParticipantList(){
            super();
        }
    }

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

        private List<Experiment> experiments;
        private ArrayList<XmlExperiment> originalExperiments;
        private JAXBExperimentRefList jaxbExperimentRefList;
        private XmlInteractionEvidence parent;
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;

        @XmlElements({@XmlElement(name="experimentDescription", required = true, type = XmlExperiment.class)})
        public JAXBExperimentDescriptionList getJAXBExperimentDescriptions() {
            if (originalExperiments == null || originalExperiments.isEmpty()){
                return null;
            }
            return originalExperiments instanceof JAXBExperimentDescriptionList ? (JAXBExperimentDescriptionList)originalExperiments : null;
        }

        /**
         * Sets the value of the experimentList property.
         *
         * @param value
         *     allowed object is
         *     {@link ArrayList<XmlExperiment> }
         *
         */
        public void setJAXBExperimentDescriptions(JAXBExperimentDescriptionList value) {
            this.originalExperiments = value;
            if (value != null){
               experiments = new ArrayList<Experiment>();
               value.parent = this;
            }
        }

        @XmlElements({@XmlElement(name="experimentRef", required = true)})
        public JAXBExperimentRefList getJAXBExperimentRefs() {
            return jaxbExperimentRefList;
        }

        /**
         * Sets the value of the experimentList property.
         *
         * @param value
         *     allowed object is
         *     {@link ArrayList<Integer> }
         *
         */
        public void setJAXBExperimentRefs(JAXBExperimentRefList value) {
            this.jaxbExperimentRefList = value;
            if (value != null){
                experiments = new ArrayList<Experiment>();
                value.parent = this;
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

        private FileSourceLocator getListLocator(){
            return getSourceLocator();
        }

        ////////////////////////////////////////////////// Inner classes of ExperimentList
        /**
         * The experiment ref list used by JAXB to populate experiment refs
         */
        public static class JAXBExperimentDescriptionList extends ArrayList<XmlExperiment>{

            private JAXBExperimentList parent;

            public JAXBExperimentDescriptionList(){
                super();
            }

            public JAXBExperimentDescriptionList(int initialCapacity) {
                super();
            }

            public JAXBExperimentDescriptionList(Collection<? extends XmlExperiment> c) {
                super();
                addAll(c);
            }

            @Override
            public boolean add(XmlExperiment val) {
                if (val == null){
                    return false;
                }
                if(parent.experiments.add(val)){
                    val.getInteractionEvidences().add(parent.parent);
                    return true;
                }
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends XmlExperiment> c) {
                if (c == null){
                    return false;
                }
                boolean added = false;

                for (XmlExperiment a : c){
                    if (add(a)){
                        added = true;
                    }
                }
                return added;
            }

            @Override
            public void add(int index, XmlExperiment element) {
                addToSpecificIndex(index, element);
            }

            @Override
            public boolean addAll(int index, Collection<? extends XmlExperiment> c) {
                int newIndex = index;
                if (c == null){
                    return false;
                }
                boolean add = false;
                for (XmlExperiment a : c){
                    if (addToSpecificIndex(newIndex, a)){
                        newIndex++;
                        add = true;
                    }
                }
                return add;
            }

            private boolean addToSpecificIndex(int index, XmlExperiment val) {
                if (val == null){
                    return false;
                }
                parent.experiments.add(index, val);
                val.getInteractionEvidences().add(parent.parent);
                return true;
            }
        }
        /**
         * The experiment ref list used by JAXB to populate experiment refs
         */
        public static class JAXBExperimentRefList extends ArrayList<Integer>{
            private JAXBExperimentList parent;

            public JAXBExperimentRefList(){
            }

            public JAXBExperimentRefList(int initialCapacity) {
            }

            public JAXBExperimentRefList(Collection<? extends Integer> c) {
                addAll(c);
            }

            @Override
            public boolean add(Integer val) {
                if (val == null){
                    return false;
                }
                return parent.experiments.add(new ExperimentRef(val));
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
                ((ArrayList<Experiment>)parent.experiments).add(index, new ExperimentRef(val));
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
                            parent.experiments.remove(this);
                            parent.experiments.add(exp);
                            parent.originalExperiments.add(exp);
                            exp.getInteractionEvidences().add(parent.parent);

                            return true;
                        }
                        else if (obj instanceof Experiment){
                            Experiment exp = (Experiment)obj;
                            parent.experiments.remove(this);
                            parent.experiments.add(exp);
                            exp.getInteractionEvidences().add(parent.parent);
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public String toString() {
                    return "Experiment reference: "+ref+" in interaction "+(parent.getListLocator() != null? parent.getListLocator().toString():"") ;
                }

                public FileSourceLocator getSourceLocator() {
                    return parent.getListLocator();
                }

                public void setSourceLocator(FileSourceLocator locator) {
                    throw new UnsupportedOperationException("Cannot set the source locator of an experiment ref");
                }
            }
        }
    }
}
