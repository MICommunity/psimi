package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.AbstractInteractionAttributeList;
import psidev.psi.mi.jami.xml.AbstractInteractionParticipantList;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

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
    private Locator locator;
    private JAXBAttributeList jaxbAttributeList;
    private JAXBParticipantList jaxbParticipantList;

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

    protected void initialiseModelledConfidences(){
        this.modelledConfidences = new ArrayList<ModelledConfidence>();
    }

    protected void initialiseModelledParameters(){
        this.modelledParameters = new ArrayList<ModelledParameter>();
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
    @XmlElement(type=XmlModelledParticipant.class, name="participant", required = true)
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
    @XmlElement(type=XmlModelledConfidence.class, name="confidence", required = true)
    public ArrayList<ModelledConfidence> getJAXBConfidences() {
        return (ArrayList<ModelledConfidence>)modelledConfidences;
    }

    /**
     * Sets the value of the confidenceList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<Confidence> }
     *
     */
    public void setJAXBConfidences(ArrayList<ModelledConfidence> value) {
        this.modelledConfidences = value;
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
    @XmlElement(type=XmlModelledParameter.class, name="parameter", required = true)
    public ArrayList<ModelledParameter> getJAXBParameters() {
        return (ArrayList<ModelledParameter>)this.modelledParameters;
    }

    /**
     * Sets the value of the parameterList property.
     *
     * @param value
     *     allowed object is
     *     {@link ArrayList<ModelledParameter> }
     *
     */
    public void setJAXBParameters(ArrayList<ModelledParameter> value) {
        this.modelledParameters = value;
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
            super.initialiseParticipantsWith(new ArrayList<ModelledParticipant>(jaxbParticipantList));
            this.jaxbParticipantList = null;
        }else{
            super.initialiseParticipants();
        }
    }

    ////////////////////////////////////////////////////////////////////////// classes

    /**
     * The attribute list used by JAXB to populate interaction annotations
     */
    public static class JAXBAttributeList extends AbstractInteractionAttributeList<XmlModelledInteraction> {

        public JAXBAttributeList(){
            super();
        }
    }

    /**
     * The participant list used by JAXB to populate interaction participants
     */
    public static class JAXBParticipantList<T extends Participant> extends AbstractInteractionParticipantList<ModelledParticipant, XmlModelledInteraction> {

        public JAXBParticipantList(){
            super();
        }
    }
}
