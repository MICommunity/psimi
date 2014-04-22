package psidev.psi.mi.jami.xml.model.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.model.reference.AbstractExperimentRef;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xml implementation of ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractXmlModelledInteraction extends AbstractXmlInteraction<ModelledParticipant> implements ModelledInteraction{

    private Collection<InteractionEvidence> interactionEvidences;
    private Source source;
    private Collection<CooperativeEffect> cooperativeEffects;
    private JAXBConfidenceWrapper jaxbConfidenceWrapper;
    private JAXBParameterWrapper jaxbParameterWrapper;
    private JAXBExperimentWrapper jaxbExperimentWrapper;
    private CvTerm evidenceType;

    @XmlLocation
    @XmlTransient
    protected Locator locator;

    public AbstractXmlModelledInteraction() {
        super();
        if (getEntry() != null){
            this.source = getEntry().getSource();
        }
    }

    public AbstractXmlModelledInteraction(String shortName) {
        super(shortName);
        XmlEntryContext context = XmlEntryContext.getInstance();
        setEntry(context.getCurrentEntry());
        if (context.getCurrentEntry() != null){
            this.source = context.getCurrentEntry().getSource();
        }
    }

    public AbstractXmlModelledInteraction(String shortName, CvTerm type) {
        super(shortName, type);
        XmlEntryContext context = XmlEntryContext.getInstance();
        setEntry(context.getCurrentEntry());
        if (context.getCurrentEntry() != null){
            this.source = context.getCurrentEntry().getSource();
        }
    }

    protected void initialiseInteractionEvidences(){
        this.interactionEvidences = new ArrayList<InteractionEvidence>();
    }

    protected void initialiseCooperativeEffects(){
        this.cooperativeEffects = new ArrayList<CooperativeEffect>();
    }

    protected void initialiseModelledConfidenceWrapper(){
        this.jaxbConfidenceWrapper = new JAXBConfidenceWrapper();
    }

    protected void initialiseModelledParameterWrapper(){
        this.jaxbParameterWrapper = new JAXBParameterWrapper();
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        if (interactionEvidences == null){
            initialiseInteractionEvidences();
        }
        return this.interactionEvidences;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        if (this.jaxbConfidenceWrapper == null){
            initialiseModelledConfidenceWrapper();
        }
        return this.jaxbConfidenceWrapper.confidences;
    }

    public Collection<ModelledParameter> getModelledParameters() {
        if (jaxbParameterWrapper == null){
            initialiseModelledParameterWrapper();
        }
        return this.jaxbParameterWrapper.parameters;
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        if (cooperativeEffects == null){
            initialiseCooperativeEffects();
        }
        return this.cooperativeEffects;
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

    @XmlElement(name = "intraMolecular", defaultValue = "false", type = Boolean.class)
    public void setJAXBIntraMolecular(boolean intra) {
        super.setIntraMolecular(intra);
    }

    @Override
    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        super.setJAXBAttributeWrapper(jaxbAttributeWrapper);
        // build the modelled cooperative effects from annotations
        // if possible
        Collection<Annotation> annotations = getAnnotations();
        CooperativeEffect effect = PsiXml25Utils.extractCooperativeEffectFrom(annotations, this.jaxbExperimentWrapper != null ? this.jaxbExperimentWrapper.experiments : null, XmlEntryContext.getInstance().getListener());
        if (effect != null){
            getCooperativeEffects().add(effect);
        }
    }

    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int value) {
        super.setId(value);
    }

    @XmlElement(name="participantList", required = true)
    public void setJAXBParticipantWrapper(JAXBParticipantWrapper jaxbParticipantWrapper) {
        super.setParticipantWrapper(jaxbParticipantWrapper);
    }

    @XmlElement(name="confidenceList")
    public void setJAXBConfidenceWrapper(JAXBConfidenceWrapper wrapper) {
        this.jaxbConfidenceWrapper = wrapper;
    }

    @XmlElement(name="parameterList")
    public void setJAXBParameterWrapper(JAXBParameterWrapper wrapper) {
        this.jaxbParameterWrapper = wrapper;
    }

    @XmlElement(name="experimentList")
    public void setJAXBExperimentWrapper(JAXBExperimentWrapper value) {
        this.jaxbExperimentWrapper = value;
    }

    public CvTerm getEvidenceType() {
        return this.evidenceType;
    }

    public void setEvidenceType(CvTerm evidenceType) {
        this.evidenceType = evidenceType;
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

    @Override
    protected void initialiseParticipantWrapper() {
        super.setParticipantWrapper(new JAXBParticipantWrapper());
    }

    ////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="modelledParticipantWrapper")
    public static class JAXBParticipantWrapper extends AbstractXmlInteraction.JAXBParticipantWrapper<ModelledParticipant> {

        public JAXBParticipantWrapper(){
            super();
        }

        @XmlElement(type=XmlModelledParticipant.class, name="participant", required = true)
        public List<ModelledParticipant> getJAXBParticipants() {
            return super.getJAXBParticipants();
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="modelledConfidenceWrapper")
    public static class JAXBConfidenceWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<ModelledConfidence> confidences;

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
        public List<ModelledConfidence> getJAXBConfidences() {
            return this.confidences;
        }

        protected void initialiseConfidences(){
            this.confidences = new ArrayList<ModelledConfidence>();
        }

        @Override
        public String toString() {
            return "Interaction Confidence List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="modelledParameterWrapper")
    public static class JAXBParameterWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<ModelledParameter> parameters;

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

        @XmlElement(type=XmlModelledParameter.class, name="parameter", required = true)
        public List<ModelledParameter> getJAXBParameters() {
            return this.parameters;
        }

        protected void initialiseParameters(){
            this.parameters = new ArrayList<ModelledParameter>();
        }

        @Override
        public String toString() {
            return "Interaction Parameter List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "modelledExperimentListType")
    public static class JAXBExperimentWrapper implements Locatable, FileSourceContext{

        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Experiment> experiments;
        private JAXBExperimentRefList jaxbExperimentRefList;

        public JAXBExperimentWrapper(){
            this.experiments = new ArrayList<Experiment>();
        }

        @XmlElement(name="experimentDescription", required = true, type = XmlExperiment.class)
        public List<Experiment> getJAXBExperimentDescriptions() {
            return experiments;
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
            private class ExperimentRef extends AbstractExperimentRef {
                public ExperimentRef(int ref) {
                    super(ref);
                }

                public boolean resolve(PsiXmlIdCache parsedObjects) {
                    if (parsedObjects.contains(this.ref)){
                        Experiment obj = parsedObjects.getExperiment(this.ref);
                        if (obj != null){
                            experiments.remove(this);
                            experiments.add(obj);

                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public String toString() {
                    return "Interaction Experiment Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
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
