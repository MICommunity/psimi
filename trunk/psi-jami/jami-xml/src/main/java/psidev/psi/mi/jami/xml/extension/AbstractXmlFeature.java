package psidev.psi.mi.jami.xml.extension;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Abstract class for Xml features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlTransient
public abstract class AbstractXmlFeature<P extends Participant, F extends Feature> implements Feature<P,F>, FileSourceContext, Serializable{

    private Collection<Annotation> annotations;
    private Collection<Range> ranges;

    private CvTerm interactionEffect;
    private CvTerm interactionDependency;

    private P participant;
    private Collection<F> linkedFeatures;

    private PsiXmLocator sourceLocator;
    private NamesContainer namesContainer;
    private FeatureXrefContainer xrefContainer;
    private CvTerm type;
    private int id;

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

    protected void initialiseAnnotations(){
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseRanges(){
        this.ranges = new ArrayList<Range>();
    }

    protected void initialiseAnnotationsWith(Collection<Annotation> annotations){
        if (annotations == null){
            this.annotations = Collections.EMPTY_LIST;
        }
        else {
            this.annotations = annotations;
        }
    }

    protected void initialiseRangesWith(Collection<Range> ranges){
        if (ranges == null){
            this.ranges = Collections.EMPTY_LIST;
        }
        else {
            this.ranges = ranges;
        }
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

    public NamesContainer getNames() {
        if (namesContainer != null && namesContainer.isEmpty()){
            return null;
        }
        return namesContainer;
    }

    /**
     * Sets the value of the names property.
     *
     * @param value
     *     allowed object is
     *     {@link NamesContainer }
     *
     */
    public void setNames(NamesContainer value) {
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

    public FeatureXrefContainer getXref() {
        if (this.xrefContainer != null && this.xrefContainer.isEmpty()){
            return null;
        }
        return xrefContainer;
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value
     *     allowed object is
     *     {@link FeatureXrefContainer }
     *
     */
    public void setXref(FeatureXrefContainer value) {
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
        if (annotations == null){
            initialiseAnnotations();
        }
        return this.annotations;
    }

    public ArrayList<Annotation> getAttributes() {
        if (annotations == null || annotations.isEmpty()){
            return null;
        }
        return new ArrayList<Annotation>(this.annotations);
    }

    public void setAttributes(ArrayList<Annotation> attributes) {
        if (attributes != null){
            getAnnotations().addAll(attributes);
        }
    }

    public CvTerm getType() {
        return this.type;
    }

    public void setType(CvTerm type) {
        this.type = type;
    }

    public Collection<Range> getRanges() {
        if (ranges == null){
            initialiseRanges();
        }
        return this.ranges;
    }

    public ArrayList<Range> getFeatureRanges() {
        if (ranges == null || ranges.isEmpty()){
            return null;
        }
        return new ArrayList<Range>(this.ranges);
    }

    public void setFeatureRanges(ArrayList<Range> ranges) {
        if (ranges != null){
            getRanges().addAll(ranges);
        }
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
        return type != null ? type.toString() : (!ranges.isEmpty() ? "("+ranges.iterator().next().toString()+"...)" : " (-)");
    }

    public Locator getSaxLocator() {
        return sourceLocator;
    }

    public void setSaxLocator(Locator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
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
     *
     */
    public void setId(int value) {
        this.id = value;
    }
}