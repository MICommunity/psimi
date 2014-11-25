package psidev.psi.mi.jami.xml.model.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.model.reference.AbstractAvailabilityRef;
import psidev.psi.mi.jami.xml.model.reference.AbstractExperimentRef;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Xml implementation of InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractXmlInteractionEvidence extends AbstractPsiXmlInteraction<ParticipantEvidence> implements ExtendedPsiXmlInteractionEvidence {

    private AbstractAvailability availability;
    private boolean isInferred;
    private boolean isNegative;
    private Collection<VariableParameterValueSet> variableParameterValueSets;
    private JAXBExperimentWrapper jaxbExperimentWrapper;

    private JAXBConfidenceWrapper jaxbConfidenceWrapper;
    private JAXBParameterWrapper jaxbParameterWrapper;

    @XmlLocation
    @XmlTransient
    protected Locator locator;

    public AbstractXmlInteractionEvidence() {
        super();
    }

    public AbstractXmlInteractionEvidence(String shortName) {
        super(shortName);
    }

    public AbstractXmlInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
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
    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        super.setJAXBAttributeWrapper(jaxbAttributeWrapper);
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
        if (this.availability == null && availability != null){
            this.availability = new Availability();
            this.availability.setValue(availability);
        }
        else if (availability != null){
            this.availability.setValue(availability);
        }
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

    @XmlElement(name = "intraMolecular", defaultValue = "false")
    public void setJAXBIntraMolecular(Boolean intra) {
        super.setIntraMolecular(intra);
    }

    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int value) {
        super.setId(value);
    }

    @XmlElement(name="participantList", required = true)
    public void setJAXBParticipantWrapper(JAXBParticipantWrapper jaxbParticipantWrapper) {
        super.setParticipantWrapper(jaxbParticipantWrapper);
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return
     *     possible object is
     *     {@link java.util.ArrayList< psidev.psi.mi.jami.model.Confidence> }
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
     *     {@link java.util.ArrayList< psidev.psi.mi.jami.model.ModelledParameter> }
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
     *     {@link psidev.psi.mi.jami.xml.model.extension.Availability }
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
     *     {@link java.util.ArrayList<  psidev.psi.mi.jami.xml.model.extension.XmlExperiment> }
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

            if (this.jaxbExperimentWrapper.experiments.size() > 1 ){
                PsiXmlParserListener listener = XmlEntryContext.getInstance().getListener();
                if (listener != null){
                    listener.onSeveralExperimentsFound(this.jaxbExperimentWrapper.experiments, this.jaxbExperimentWrapper.getSourceLocator());
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
        return isInferred();
    }

    @Override
    public AbstractAvailability getXmlAvailability() {
        return this.availability;
    }

    @Override
    public void setXmlAvailability(AbstractAvailability availability) {
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
    public void setModelled(boolean value) {
        setInferred(value);
    }

    @XmlElement(name = "modelled", defaultValue = "false", type = Boolean.class)
    public void setJAXBModelled(Boolean value) {
        setModelled(value);
    }

    /**
     * Sets the value of the negative property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    @XmlElement(name = "negative", defaultValue = "false", type = Boolean.class)
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

    public JAXBExperimentWrapper getJAXBExperimentWrapper() {
        return jaxbExperimentWrapper;
    }

    public List<ExtendedPsiXmlExperiment> getOriginalExperiments(){
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

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), getId()));
        }
        return super.getSourceLocator();
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
    public static class JAXBParticipantWrapper extends AbstractPsiXmlInteraction.JAXBParticipantWrapper<ParticipantEvidence> {

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
        private PsiXmlLocator sourceLocator;
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
                sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else if (sourceLocator instanceof PsiXmlLocator){
                this.sourceLocator = (PsiXmlLocator)sourceLocator;
            }
            else {
                this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        @XmlElement(type=XmlConfidence.class, name="confidence", required = true)
        public List<Confidence> getJAXBConfidences() {
            return this.confidences;
        }

        protected void initialiseConfidences(){
            this.confidences = new ArrayList<Confidence>();
        }

        @Override
        public String toString() {
            return "Interaction Confidence List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalParameterWrapper")
    public static class JAXBParameterWrapper implements Locatable, FileSourceContext {
        private PsiXmlLocator sourceLocator;
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
                sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else if (sourceLocator instanceof PsiXmlLocator){
                this.sourceLocator = (PsiXmlLocator)sourceLocator;
            }
            else {
                this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        @XmlElement(type=XmlParameter.class, name="parameter", required = true)
        public List<Parameter> getJAXBParameters() {
            return this.parameters;
        }

        protected void initialiseParameters(){
            this.parameters = new ArrayList<Parameter>();
        }

        @Override
        public String toString() {
            return "Interaction Parameter List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    private class AvailabilityRef extends AbstractAvailabilityRef{
        public AvailabilityRef(int ref) {
            super(ref);
        }

        public boolean resolve(PsiXmlIdCache parsedObjects) {
            if (parsedObjects.containsAvailability(this.ref)){
                AbstractAvailability av = parsedObjects.getAvailability(this.ref);
                if (av != null){
                   availability = av;
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
            return "Interaction Availability Reference: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "experimentListType")
    public static class JAXBExperimentWrapper implements Locatable, FileSourceContext{

        private List<ExtendedPsiXmlExperiment> jaxbExperiments;
        private PsiXmlLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Experiment> experiments;
        private JAXBExperimentRefList jaxbExperimentRefList;
        private ExtendedPsiXmlInteractionEvidence parent;

        public JAXBExperimentWrapper(){
            this.experiments = new ArrayList<Experiment>();
        }

        @XmlElement(name="experimentDescription", required = true, type = XmlExperiment.class)
        public List<ExtendedPsiXmlExperiment> getJAXBExperimentDescriptions() {
            if (jaxbExperiments == null){
                jaxbExperiments = new ArrayList<ExtendedPsiXmlExperiment>();
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
                sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
            return sourceLocator;
        }

        public void setSourceLocator(FileSourceLocator sourceLocator) {
            if (sourceLocator == null){
                this.sourceLocator = null;
            }
            else if (sourceLocator instanceof PsiXmlLocator){
                this.sourceLocator = (PsiXmlLocator)sourceLocator;
            }
            else {
                this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
            }
        }

        @Override
        public String toString() {
            return "Interaction Experiment List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }

        ////////////////////////////////////////////////// Inner classes of ExperimentList

        /**
         * The experiment ref list used by JAXB to populate experiment refs
         */
        private class JAXBExperimentRefList extends ArrayList<Integer>{

            public JAXBExperimentRefList(){
                super();
                jaxbExperiments = new ArrayList<ExtendedPsiXmlExperiment>();
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

            @Override
            public String toString() {
                return "Interaction Experiment Reference List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
            }

            /**
             * Experiment ref for experimental interactor
             */
            private class ExperimentRef extends AbstractExperimentRef{
                public ExperimentRef(int ref) {
                    super(ref);
                }

                public boolean resolve(PsiXmlIdCache parsedObjects) {
                    if (parsedObjects.containsExperiment(this.ref)){
                        Experiment obj = parsedObjects.getExperiment(this.ref);
                        if (obj == null){
                           return false;
                        }
                        else if (obj instanceof ExtendedPsiXmlExperiment){
                            ExtendedPsiXmlExperiment exp = (ExtendedPsiXmlExperiment)obj;
                            experiments.remove(this);
                            experiments.add(exp);
                            jaxbExperiments.add(exp);

                            exp.getInteractionEvidences().add(parent);
                            return true;
                        }
                        else {
                            experiments.remove(this);
                            experiments.add(obj);

                            obj.getInteractionEvidences().add(parent);
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public String toString() {
                    return "Interaction Experiment Reference: "+(ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString()));
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
