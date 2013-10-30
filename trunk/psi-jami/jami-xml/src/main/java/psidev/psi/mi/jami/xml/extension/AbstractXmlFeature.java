package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class for Xml features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlFeature<P extends Entity, F extends Feature> implements
        Feature<P,F>, FileSourceContext, Locatable, ExtendedPsi25Feature<P,F>{

    private CvTerm interactionEffect;
    private CvTerm interactionDependency;
    private P participant;
    private Collection<F> linkedFeatures;
    private PsiXmLocator sourceLocator;
    private NamesContainer namesContainer;
    private FeatureXrefContainer xrefContainer;
    private CvTerm type;
    private int id;

    private JAXBAttributeWrapper jaxbAttributeWrapper;
    private JAXBRangeWrapper jaxbRangeWrapper;

    public AbstractXmlFeature(){
    }

    public AbstractXmlFeature(String shortName, String fullName){
        this();
        this.namesContainer = new NamesContainer();
        this.namesContainer.setShortLabel(shortName);
        this.namesContainer.setFullName(fullName);
    }

    public AbstractXmlFeature(CvTerm type){
        this();
        this.type = type;
    }

    public AbstractXmlFeature(String shortName, String fullName, CvTerm type){
        this(shortName, fullName);
        this.type =type;
    }

    public AbstractXmlFeature(String shortName, String fullName, String interpro){
        this(shortName, fullName);
        setInterpro(interpro);
    }

    public AbstractXmlFeature(CvTerm type, String interpro){
        this(type);
        setInterpro(interpro);
    }

    public AbstractXmlFeature(String shortName, String fullName, CvTerm type, String interpro){
        this(shortName, fullName, type);
        setInterpro(interpro);
    }

    protected void initialiseAnnotationWrapper(){
        this.jaxbAttributeWrapper = new JAXBAttributeWrapper();
    }

    protected void initialiseRangeWrapper(){
        this.jaxbRangeWrapper = new JAXBRangeWrapper();
    }

    protected void initialiseLinkedFeatures(){
        this.linkedFeatures = new ArrayList<F>();
    }

    protected void initialiseLinkedFeaturesWith(Collection<F> features){
        if (features == null){
            this.linkedFeatures = Collections.EMPTY_LIST;
        }
        else {
            this.linkedFeatures = features;
        }
    }

    /**
     * Sets the value of the names property.
     *
     * @param value
     *     allowed object is
     *     {@link NamesContainer }
     *
     */
    public void setJAXBNames(NamesContainer value) {
        this.namesContainer = value;
    }

    public String getShortName() {
        return this.namesContainer != null ? this.namesContainer.getShortLabel() : null;
    }

    public void setShortName(String name) {
        if (this.namesContainer == null){
            this.namesContainer = new NamesContainer();
        }
        this.namesContainer.setShortLabel(name);
    }

    public String getFullName() {
        return this.namesContainer != null ? this.namesContainer.getFullName() : null;

    }

    public void setFullName(String name) {
        if (this.namesContainer == null){
            this.namesContainer = new NamesContainer();
        }
        this.namesContainer.setFullName(name);
    }

    @Override
    public List<Alias> getAliases() {
        if (this.namesContainer == null){
            this.namesContainer = new NamesContainer();
        }
        return this.namesContainer.getAliases();
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value
     *     allowed object is
     *     {@link FeatureXrefContainer }
     *
     */
    public void setJAXBXref(FeatureXrefContainer value) {
        this.xrefContainer = value;
    }

    public String getInterpro() {
        return this.xrefContainer != null ? this.xrefContainer.getInterpro() : null;
    }

    public void setInterpro(String interpro) {
        if (this.xrefContainer == null){
            this.xrefContainer = new FeatureXrefContainer();
        }
        this.xrefContainer.setInterpro(interpro);
    }

    public Collection<Xref> getIdentifiers() {
        if (this.xrefContainer == null){
            this.xrefContainer = new FeatureXrefContainer();
        }
        return xrefContainer.getAllIdentifiers();
    }

    public Collection<Xref> getXrefs() {
        if (this.xrefContainer == null){
            this.xrefContainer = new FeatureXrefContainer();
        }
        return xrefContainer.getAllXrefs();
    }

    public Collection<Annotation> getAnnotations() {
        if (this.jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper.annotations;
    }

    public CvTerm getType() {
        return this.type;
    }

    public void setType(CvTerm type) {
        this.type = type;
    }

    public void setJAXBType(CvTerm type) {
        this.type = type;
    }

    public Collection<Range> getRanges() {
        if (this.jaxbRangeWrapper == null){
            initialiseRangeWrapper();
        }
        return this.jaxbRangeWrapper.ranges;
    }

    public CvTerm getInteractionEffect() {
        return this.interactionEffect;
    }

    public void setInteractionEffect(CvTerm effect) {
        this.interactionEffect = effect;
    }

    public CvTerm getInteractionDependency() {
        return this.interactionDependency;
    }

    public void setInteractionDependency(CvTerm interactionDependency) {
        this.interactionDependency = interactionDependency;
    }

    public P getParticipant() {
        return this.participant;
    }

    public void setParticipant(P participant) {
        this.participant = participant;
    }

    public void setParticipantAndAddFeature(P participant) {
        if (this.participant != null){
            this.participant.removeFeature(this);
        }

        if (participant != null){
            participant.addFeature(this);
        }
    }

    public Collection<F> getLinkedFeatures() {
        if(linkedFeatures == null){
            initialiseLinkedFeatures();
        }
        return this.linkedFeatures;
    }

    @Override
    public String toString() {
        return type != null ? type.toString() : (jaxbRangeWrapper != null && !jaxbRangeWrapper.ranges.isEmpty() ? "("+jaxbRangeWrapper.ranges.iterator().next().toString()+"...)" : " (-)");
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = (PsiXmLocator)sourceLocator;
    }

    /**
     * Gets the value of the id property.
     *
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * Adds this object in the mapOfReferencedObjects of this entry
     *
     */
    public void setId(int value) {
        this.id = value;
        XmlEntryContext.getInstance().getMapOfReferencedObjects().put(this.id, this);
        if (getSourceLocator() != null){
            sourceLocator.setObjectId(this.id);
        }
    }

    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        this.jaxbAttributeWrapper = jaxbAttributeWrapper;
    }

    public void setJAXBRangeWrapper(JAXBRangeWrapper jaxbRangeWrapper) {
        this.jaxbRangeWrapper = jaxbRangeWrapper;
    }

    protected JAXBRangeWrapper getJAXBRangeWrapper() {
        if (this.jaxbRangeWrapper == null){
            initialiseRangeWrapper();
        }
        return this.jaxbRangeWrapper;
    }

    protected JAXBAttributeWrapper getJAXBAttributeWrapper() {
        if (this.jaxbAttributeWrapper == null){
            initialiseAnnotationWrapper();
        }
        return this.jaxbAttributeWrapper;
    }

    //////////////////////////////// classes
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="featureAttributeWrapper")
    public static class JAXBAttributeWrapper implements Locatable, FileSourceContext{
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Annotation> annotations;

        public JAXBAttributeWrapper(){
            initialiseAnnotations();
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

        protected void initialiseAnnotations(){
            annotations = new ArrayList<Annotation>();
        }

        @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
        public List<Annotation> getJAXBAttributes() {
            return annotations;
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="featureRangeWrapper")
    public static class JAXBRangeWrapper implements Locatable, FileSourceContext{
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Range> ranges;

        public JAXBRangeWrapper(){
            initialiseRanges();
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

        protected void initialiseRanges(){
            ranges = new ArrayList<Range>();
        }

        @XmlElement(type=XmlRange.class, name="featureRange", required = true)
        public List<Range> getJAXBRanges() {
            return ranges;
        }
    }
}