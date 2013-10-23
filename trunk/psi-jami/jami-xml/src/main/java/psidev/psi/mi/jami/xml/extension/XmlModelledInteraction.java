package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Xml implementation of ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlRootElement(name = "interaction", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "modelledInteraction", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBParticipants",
        "JAXBInferredInteractions",
        "JAXBInteractionTypes",
        "JAXBIntraMolecular",
        "JAXBConfidences",
        "JAXBParameters",
        "JAXBAttributes"
})
public class XmlModelledInteraction extends AbstractXmlInteraction<ModelledParticipant> implements ModelledInteraction{

    private Collection<InteractionEvidence> interactionEvidences;
    private Source source;
    private Collection<ModelledConfidence> modelledConfidences;
    private Collection<ModelledParameter> modelledParameters;
    private Collection<CooperativeEffect> cooperativeEffects;
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

    protected void initialiseCooperativeEffectsWith(Collection<CooperativeEffect> cooperativeEffects){
        if (cooperativeEffects == null){
            this.cooperativeEffects = Collections.EMPTY_LIST;
        }
        else{
            this.cooperativeEffects = cooperativeEffects;
        }
    }

    protected void initialiseModelledConfidences(){
        this.modelledConfidences = new ArrayList<ModelledConfidence>();
    }

    protected void initialiseModelledConfidencesWith(Collection<ModelledConfidence> confidences){
        if (confidences == null){
            this.modelledConfidences = Collections.EMPTY_LIST;
        }
        else {
            this.modelledConfidences = confidences;
        }
    }

    protected void initialiseInteractionEvidencesWith(Collection<InteractionEvidence> evidences){
        if (evidences == null){
            this.interactionEvidences = Collections.EMPTY_LIST;
        }
        else {
            this.interactionEvidences = evidences;
        }
    }

    protected void initialiseModelledParameters(){
        this.modelledParameters = new ArrayList<ModelledParameter>();
    }

    protected void initialiseModelledParametersWith(Collection<ModelledParameter> parameters){
        if (parameters == null){
            this.modelledParameters = Collections.EMPTY_LIST;
        }
        else {
            this.modelledParameters = parameters;
        }
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
        if (modelledConfidences == null){
            initialiseModelledConfidences();
        }
        return this.modelledConfidences;
    }

    public Collection<ModelledParameter> getModelledParameters() {
        if (modelledParameters == null){
            initialiseModelledParameters();
        }
        return this.modelledParameters;
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        if (cooperativeEffects == null){
            initialiseCooperativeEffects();
        }
        return this.cooperativeEffects;
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
    @XmlElements({ @XmlElement(type=XmlModelledParticipant.class, name="participant", required = true)})
    public ArrayList<ModelledParticipant> getJAXBParticipants() {
        if (getParticipants().isEmpty()){
            return null;
        }
        return new ArrayList<ModelledParticipant>(getParticipants());
    }

    @Override
    @XmlElementWrapper(name="inferredInteractionList")
    @XmlElement(name="inferredInteraction", required = true)
    public ArrayList<InferredInteraction> getJAXBInferredInteractions() {
        return super.getJAXBInferredInteractions();
    }

    @Override
    @XmlElement(name="interactionType")
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
    @XmlElements({ @XmlElement(type=XmlModelledConfidence.class, name="confidence", required = true)})
    public ArrayList<ModelledConfidence> getJAXBConfidences() {
        if (this.modelledConfidences == null || this.modelledConfidences.isEmpty()){
            return null;
        }
        return new ArrayList<ModelledConfidence>(getModelledConfidences());
    }

    /**
     * Sets the value of the confidenceList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Confidence> }
     *
     */
    public void setJAXBConfidences(ArrayList<XmlModelledConfidence> value) {
        getModelledConfidences().clear();
        if (value != null && !value.isEmpty()){
            getModelledConfidences().addAll(value);
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
    @XmlElements({ @XmlElement(type=XmlModelledParameter.class, name="parameter", required = true)})
    public ArrayList<ModelledParameter> getJAXBParameters() {
        if (this.modelledParameters == null || this.modelledParameters.isEmpty()){
            return null;
        }
        return new ArrayList<ModelledParameter>(getModelledParameters());
    }

    /**
     * Sets the value of the parameterList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<ModelledParameter> }
     *
     */
    public void setJAXBParameters(ArrayList<XmlModelledParameter> value) {
        getModelledParameters().clear();
        if (value != null && !value.isEmpty()){
            getModelledParameters().addAll(value);
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
    public void setJAXBParticipants(ArrayList<XmlModelledParticipant> value) {
        removeAllParticipants(getParticipants());
        if (value != null && !value.isEmpty()){
            addAllParticipants(value);
        }
    }
}
