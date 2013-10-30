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
public class XmlInteractionEvidence extends AbstractXmlInteraction<ParticipantEvidence> implements ExtendedPsi25InteractionEvidence{

    private Availability availability;
    private boolean isInferred;
    private boolean isNegative;
    private Collection<VariableParameterValueSet> variableParameterValueSets;
    private Boolean modelled;
    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBExperimentWrapper jaxbExperimentWrapper;

    private JAXBConfidenceWrapper jaxbConfidenceWrapper;
    private JAXBParameterWrapper jaxbParameterWrapper;

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
        return getJAXBXref() != null ? getJAXBXref().getImexId() : null;
    }

    public void assignImexId(String identifier) {
        if (getJAXBXref() == null && identifier != null){
            setJAXBXref(new InteractionXrefContainer());
        }
        getJAXBXref().assignImexId(identifier);
    }

    public Experiment getExperiment() {
        if (this.jaxbExperimentWrapper == null || this.jaxbExperimentWrapper.experiments.isEmpty()){
            return null;
        }
        return this.jaxbExperimentWrapper.experiments.iterator().next();
    }

    public void setExperiment(Experiment experiment) {
        if (this.jaxbExperimentWrapper == null && experiment != null){
            this.jaxbExperimentWrapper = new JAXBExperimentWrapper();
            this.jaxbExperimentWrapper.experiments.add(experiment);
        }
        else if (experiment != null){
            if (!this.jaxbExperimentWrapper.experiments.isEmpty()){
                this.jaxbExperimentWrapper.experiments.remove(0);
            }
            this.jaxbExperimentWrapper.experiments.add(0, experiment);
        }
        else{
            if (!this.jaxbExperimentWrapper.experiments.isEmpty()){
                this.jaxbExperimentWrapper.experiments.remove(0);
            }
        }
    }

    public List<Experiment> getExperiments() {
        if (jaxbExperimentWrapper == null){
            jaxbExperimentWrapper = new JAXBExperimentWrapper();
        }
        return jaxbExperimentWrapper.experiments;
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
        if (this.jaxbConfidenceWrapper == null){
            initialiseConfidenceWrapper();
        }
        return this.jaxbConfidenceWrapper.confidences;
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
        if (jaxbParameterWrapper == null){
            initialiseParameterWrapper();
        }
        return this.jaxbParameterWrapper.parameters;
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
    public void setJAXBNames(NamesContainer value) {
        super.setJAXBNames(value);
    }

    @Override
    @XmlElement(name = "xref")
    public void setJAXBXref(InteractionXrefContainer value) {
        super.setJAXBXref(value);
    }

    @Override
    public boolean isIntraMolecular() {
        return super.isIntraMolecular();
    }

    @Override
    @XmlElement(name = "intraMolecular", defaultValue = "false")
    public void setIntraMolecular(Boolean intra) {
        super.setIntraMolecular(intra);
    }

    @Override
    @XmlAttribute(name = "id", required = true)
    public void setId(int value) {
        super.setId(value);
    }

    @Override
    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        super.setJAXBAttributeWrapper(jaxbAttributeWrapper);
    }

    @XmlElement(name="participantList", required = true)
    public void setJAXBParticipantWrapper(JAXBParticipantWrapper jaxbParticipantWrapper) {
        super.setParticipantWrapper(jaxbParticipantWrapper);
    }

    @Override
    @XmlElement(name="inferredInteractionList")
    public void setJAXBInferredInteractionWrapper(JAXBInferredInteractionWrapper jaxbInferredWrapper) {
        super.setJAXBInferredInteractionWrapper(jaxbInferredWrapper);
    }

    @Override
    @XmlElement(name="interactionType", type = XmlCvTerm.class)
    public List<CvTerm> getInteractionTypes() {
        return super.getInteractionTypes();
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), getId()));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), getId()));
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
    @XmlElement(name="confidenceList")
    public void setJAXBConfidenceWrapper(JAXBConfidenceWrapper wrapper) {
        this.jaxbConfidenceWrapper = wrapper;
    }

    /**
     * Gets the value of the parameterList property.
     *
     * @return
     *     possible object is
     *     {@link ArrayList<ModelledParameter> }
     *
     */
    @XmlElement(name="parameterList")
    public void setJAXBParameterWrapper(JAXBParameterWrapper wrapper) {
        this.jaxbParameterWrapper = wrapper;
    }

    /**
     * Sets the value of the availability property.
     *
     * @param value
     *     allowed object is
     *     {@link Availability }
     *
     */
    @XmlElement(name = "availability")
    public void setJAXBAvailability(Availability value) {
        this.availability = value;
    }

    /**
     * Sets the value of the availabilityRef property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    @XmlElement(name = "availabilityRef")
    public void setJAXBAvailabilityRef(Integer value) {
        if (value != null){
            this.availability = new AvailabilityRef(value);
        }
    }

    /**
     * Sets the value of the experimentList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<XmlExperiment> }
     *
     */
    @XmlElement(name="experimentList")
    public void setJAXBExperimentWrapper(JAXBExperimentWrapper value) {
        this.jaxbExperimentWrapper = value;
        // experiment list is set. Because we use back references, we need to post process.
        if (this.jaxbExperimentWrapper != null){
            // set the parent for future references
            this.jaxbExperimentWrapper.parent = this;
            // we don't have experiment refs, need to prepare each loaded experiment
            if (this.jaxbExperimentWrapper.jaxbExperimentRefList == null &&
                    this.jaxbExperimentWrapper.jaxbExperiments != null &&
                    !this.jaxbExperimentWrapper.jaxbExperiments.isEmpty()){
                for (Experiment exp : this.jaxbExperimentWrapper.jaxbExperiments){
                    jaxbExperimentWrapper.experiments.add(exp);
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
    public boolean isModelled() {
        return modelled != null ? modelled : false;
    }

    @Override
    public Availability getXmlAvailability() {
        return this.availability;
    }

    @Override
    public void setXmlAvailability(Availability availability) {
        this.availability = availability;
    }

    /**
     * Sets the value of the modelled property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    @XmlElement(name = "modelled", defaultValue = "false")
    public void setModelled(Boolean value) {
        this.modelled = value;
    }

    /**
     * Sets the value of the negative property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    @XmlElement(name = "negative", defaultValue = "false")
    public void setJAXBNegative(Boolean value) {
        if (value == null){
            this.isNegative = false;
        }
        else{
            this.isNegative = value;
        }
    }

    public JAXBConfidenceWrapper getJAXBConfidenceWrapper() {
        return jaxbConfidenceWrapper;
    }

    public JAXBParameterWrapper getJAXBParameterWrapper() {
        return jaxbParameterWrapper;
    }

    public Availability getJAXBAvailability() {
        return availability;
    }

    public JAXBExperimentWrapper getJAXBExperimentWrapper() {
        return jaxbExperimentWrapper;
    }

    protected List<XmlExperiment> getOriginalExperiments(){
        return jaxbExperimentWrapper != null ? jaxbExperimentWrapper.jaxbExperiments : Collections.EMPTY_LIST;
    }

    protected void initialiseConfidenceWrapper(){
        this.jaxbConfidenceWrapper = new JAXBConfidenceWrapper();
    }


    protected void initialiseVariableParameterValueSets(){
        this.variableParameterValueSets = new ArrayList<VariableParameterValueSet>();
    }

    protected void initialiseParameterWrapper(){
        this.jaxbParameterWrapper = new JAXBParameterWrapper();
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
    protected void initialiseParticipantWrapper() {
        super.setParticipantWrapper(new JAXBParticipantWrapper());
    }

    ////////////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="participantEvidenceWrapper")
    public static class JAXBParticipantWrapper extends AbstractXmlInteraction.JAXBParticipantWrapper<ParticipantEvidence> {

        public JAXBParticipantWrapper(){
            super();
        }

        @XmlElement(type=XmlParticipantEvidence.class, name="participant", required = true)
        public List<ParticipantEvidence> getJAXBParticipants() {
            return super.getJAXBParticipants();
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalConfidenceWrapper")
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

        @XmlElement(type=XmlConfidence.class, name="confidence", required = true)
        public List<Confidence> getJAXBConfidences() {
            return this.confidences;
        }

        protected void initialiseConfidences(){
            this.confidences = new ArrayList<Confidence>();
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalParameterWrapper")
    public static class JAXBParameterWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Parameter> parameters;

        public JAXBParameterWrapper(){
            initialiseParameters();
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

        @XmlElement(type=XmlParameter.class, name="parameter", required = true)
        public List<Parameter> getJAXBParameters() {
            return this.parameters;
        }

        protected void initialiseParameters(){
            this.parameters = new ArrayList<Parameter>();
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
    @XmlType(name = "experimentListType")
    public static class JAXBExperimentWrapper implements Locatable, FileSourceContext{

        private List<XmlExperiment> jaxbExperiments;
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Experiment> experiments;
        private JAXBExperimentRefList jaxbExperimentRefList;
        private XmlInteractionEvidence parent;

        public JAXBExperimentWrapper(){
            this.experiments = new ArrayList<Experiment>();
        }

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
