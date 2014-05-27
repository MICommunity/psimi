package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.model.extension.*;
import psidev.psi.mi.jami.xml.model.extension.XmlAnnotation;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xml 3.0.0 implementation of ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlRootElement(name = "abstractInteraction", namespace = "http://psi.hupo.org/mi/mif300")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledInteraction extends AbstractXmlInteraction<ModelledParticipant> implements ExtendedPsiXmlModelledInteraction{

    private CvTerm interactionType;
    private Collection<InteractionEvidence> interactionEvidences;
    private Source source;
    private Collection<CooperativeEffect> cooperativeEffects;
    private JAXBConfidenceWrapper jaxbConfidenceWrapper;
    private JAXBParameterWrapper jaxbParameterWrapper;
    private CvTerm evidenceType;
    private JAXBBindingFeaturesWrapper jaxbBindingFeaturesWrapper;

    @XmlLocation
    @XmlTransient
    protected Locator locator;

    public XmlModelledInteraction() {
        super();
        if (getEntry() != null){
            this.source = getEntry().getSource();
        }
    }

    public XmlModelledInteraction(String shortName) {
        super(shortName);
        XmlEntryContext context = XmlEntryContext.getInstance();
        setEntry(context.getCurrentEntry());
        if (context.getCurrentEntry() != null){
            this.source = context.getCurrentEntry().getSource();
        }
    }

    public XmlModelledInteraction(String shortName, CvTerm type) {
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

    public String getPhysicalProperties() {
        Annotation prop = getAttributeWrapper().physicalProperties;
        return prop != null ? prop.getValue() : null;
    }

    public void setPhysicalProperties(String properties) {
        JAXBAttributeWrapper.ComplexAnnotationList complexAnnotationList = (JAXBAttributeWrapper.ComplexAnnotationList)getAnnotations();
        Annotation propAnnot = getAttributeWrapper().physicalProperties;

        // add new properties if not null
        if (propAnnot != null){
            CvTerm propTopic = CvTermUtils.createMICvTerm(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_MI);
            // first remove properties if not null
            if (propAnnot != null){
                complexAnnotationList.removeOnly(propAnnot);
            }
            getAttributeWrapper().physicalProperties = new XmlAnnotation(propTopic, properties);
            complexAnnotationList.addOnly(getAttributeWrapper().physicalProperties);
        }
        // remove all url if the collection is not empty
        else if (!complexAnnotationList.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(complexAnnotationList, Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
            getAttributeWrapper().physicalProperties = null;
        }
    }

    public String getRecommendedName() {
        return getNamesContainer().getRecommendedName();
    }

    public void setRecommendedName(String name) {
        getNamesContainer().setRecommendedName(name);
    }

    public String getSystematicName() {
        return getNamesContainer().getSystematicName();
    }

    public void setSystematicName(String name) {
        getNamesContainer().setSystematicName(name);
    }

    @Override
    public Xref getPreferredIdentifier() {
        return null;
    }

    @Override
    public Organism getOrganism() {
        return null;
    }

    @Override
    public void setOrganism(Organism organism) {

    }

    @Override
    public CvTerm getInteractorType() {
        return null;
    }

    @Override
    public void setInteractorType(CvTerm type) {

    }

    @Override
    public List<Alias> getAliases() {
        return super.getAliases();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        return super.getChecksums();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return super.getXrefs();
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return super.getIdentifiers();
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return super.getAnnotations();
    }

    public List<BindingFeatures> getBindingFeatures() {
        if (jaxbBindingFeaturesWrapper == null){
            jaxbBindingFeaturesWrapper = new JAXBBindingFeaturesWrapper();
        }
        return jaxbBindingFeaturesWrapper.bindingFeatures;
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

    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        super.setJAXBAttributeWrapper(jaxbAttributeWrapper);
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

    public CvTerm getEvidenceType() {
        return this.evidenceType;
    }

    public void setEvidenceType(CvTerm evidenceType) {
        this.evidenceType = evidenceType;
    }

    @Override
    public CvTerm getInteractionType() {
        return this.interactionType;
    }

    @Override
    public void setInteractionType(CvTerm term) {
        this.interactionType = term;
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

    @XmlElement(name="interactionType")
    public void setJAXBInteractionType(XmlCvTerm term) {
        setInteractionType(term);
    }

    @XmlElement(name="bindingFeaturesList")
    public void setJAXBBindingFeaturesWrapper(JAXBBindingFeaturesWrapper jaxbInferredWrapper) {
        this.jaxbBindingFeaturesWrapper = jaxbInferredWrapper;
    }

    @Override
    protected void initialiseParticipantWrapper() {
        super.setParticipantWrapper(new JAXBParticipantWrapper());
    }

    @Override
    protected void initialiseNamesContainer() {
        super.setJAXBNames(new ComplexNamesContainer());
    }

    @Override
    protected ComplexNamesContainer getNamesContainer() {
        return (ComplexNamesContainer) super.getNamesContainer();
    }

    protected JAXBAttributeWrapper getAttributeWrapper() {
        return (JAXBAttributeWrapper) super.getAttributeWrapper();
    }

    @Override
    protected void initialiseAnnotationWrapper() {
        super.setJAXBAttributeWrapper(new JAXBAttributeWrapper());
    }

    ////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="modelledParticipantWrapper")
    public static class JAXBParticipantWrapper extends AbstractPsiXmlInteraction.JAXBParticipantWrapper<ModelledParticipant> {

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


    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="bindingFeaturesWrapper")
    public static class JAXBBindingFeaturesWrapper implements Locatable, FileSourceContext{
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<BindingFeatures> bindingFeatures;

        public JAXBBindingFeaturesWrapper(){
            initialiseBindingFeatures();
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

        protected void initialiseBindingFeatures(){
            bindingFeatures = new ArrayList<BindingFeatures>();
        }

        @XmlElement(name="bindingFeatures", type = BindingFeatures.class, required = true)
        public List<BindingFeatures> getJAXBBindingFeatures() {
            return bindingFeatures;
        }

        @Override
        public String toString() {
            return "Binding features List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="complexAttributeWrapper")
    public static class JAXBAttributeWrapper extends AbstractXmlInteraction.JAXBAttributeWrapper{
        private Annotation physicalProperties;

        public JAXBAttributeWrapper(){
            super();
        }

        @Override
        protected void initialiseAnnotations() {
            super.initialiseAnnotationsWith(new ComplexAnnotationList());
        }

        private void processAddedAnnotationEvent(Annotation added) {
            if (physicalProperties == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES)){
                physicalProperties = added;
            }
        }

        private void processRemovedAnnotationEvent(Annotation removed) {
            if (physicalProperties != null && physicalProperties.equals(removed)){
                physicalProperties = AnnotationUtils.collectFirstAnnotationWithTopic(getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
            }
        }

        private void clearPropertiesLinkedToAnnotations() {
            physicalProperties = null;
        }

        private class ComplexAnnotationList extends AbstractListHavingProperties<Annotation> {
            public ComplexAnnotationList(){
                super();
            }

            @Override
            protected void processAddedObjectEvent(Annotation added) {
                processAddedAnnotationEvent(added);
            }

            @Override
            protected void processRemovedObjectEvent(Annotation removed) {
                processRemovedAnnotationEvent(removed);
            }

            @Override
            protected void clearProperties() {
                clearPropertiesLinkedToAnnotations();
            }
        }

        @Override
        public String toString() {
            return "Com-lex Attribute List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }
}
