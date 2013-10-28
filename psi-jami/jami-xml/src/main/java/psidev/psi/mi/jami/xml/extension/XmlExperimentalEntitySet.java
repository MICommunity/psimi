package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * XML implementation of a set of ExperimentalEntity that form a single participant evidence
 * Notes: The equals and hashcode methods have NOT been overridden because the XmlExperimentalEntitySet object is a complex object.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "participantEvidenceSet", propOrder = {
        "JAXBNames",
        "JAXBXref",
        "JAXBInteractionRef",
        "JAXBInteractor",
        "JAXBInteractorRef",
        "JAXBParticipantIdentificationMethods",
        "JAXBBiologicalRole",
        "JAXBExperimentalRoles",
        "JAXBExperimentalPreparations",
        "experimentalInteractors",
        "JAXBFeatures",
        "JAXBHostOrganisms",
        "JAXBConfidences",
        "JAXBParameters",
        "JAXBAttributes"
})
public class XmlExperimentalEntitySet extends AbstractXmlEntitySet<InteractionEvidence, FeatureEvidence, ExperimentalEntity> implements ExperimentalEntitySet {

    private Collection<CvTerm> identificationMethods;
    private Collection<CvTerm> experimentalPreparations;
    private Collection<Confidence> confidences;
    private Collection<Parameter> parameters;

    private List<CvTerm> experimentalRoles;
    private List<ExperimentalInteractor> experimentalInteractors;
    private List<Organism> hostOrganisms;

    @XmlLocation
    @XmlTransient
    private Locator locator;

    public XmlExperimentalEntitySet() {
        super();
    }

    public XmlExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName));
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole, stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole, stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole);
        setExpressedInOrganism(expressedIn);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntitySet(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole, stoichiometry);
        setExpressedInOrganism(expressedIn);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntitySet(String interactorSetName, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName));
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntitySet(String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole, stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole);
        setExpressedInOrganism(expressedIn);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntitySet(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorSet(interactorSetName), bioRole, stoichiometry);
        setExpressedInOrganism(expressedIn);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntitySet(String interactorSetName) {
        super(new XmlInteractorSet(interactorSetName));
    }

    public XmlExperimentalEntitySet(String interactorSetName, Stoichiometry stoichiometry) {
        super(new XmlInteractorSet(interactorSetName), stoichiometry);
    }

    protected void initialiseExperimentalPreparations() {
        this.experimentalPreparations = new ArrayList<CvTerm>();
    }

    protected void initialiseConfidences() {
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseParameters() {
        this.parameters = new ArrayList<Parameter>();
    }

    protected void initialiseIdentificationMethods(){
        this.identificationMethods = new ArrayList<CvTerm>();
    }

    public CvTerm getExperimentalRole() {
        if (this.experimentalRoles == null){
            this.experimentalRoles = new ArrayList<CvTerm>();
        }
        if (this.experimentalRoles.isEmpty()){
            this.experimentalRoles.add(new XmlCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI));
        }
        return this.experimentalRoles.get(0);
    }

    public void setExperimentalRole(CvTerm expRole) {
        if (this.experimentalRoles == null && expRole != null){
            this.experimentalRoles = new ArrayList<CvTerm>();
            this.experimentalRoles.add(expRole);
        }
        else if (this.experimentalRoles != null){
            if (!this.experimentalRoles.isEmpty() && expRole == null){
                this.experimentalRoles.remove(0);
            }
            else if (expRole != null){
                this.experimentalRoles.remove(0);
                this.experimentalRoles.add(0, expRole);
            }
        }
    }

    public Collection<CvTerm> getIdentificationMethods() {
        if (identificationMethods == null){
            initialiseIdentificationMethods();
        }
        return this.identificationMethods;
    }

    public Collection<CvTerm> getExperimentalPreparations() {
        if (experimentalPreparations == null){
            initialiseExperimentalPreparations();
        }
        return this.experimentalPreparations;
    }

    public Organism getExpressedInOrganism() {
        return (this.hostOrganisms != null && !this.hostOrganisms.isEmpty())? this.hostOrganisms.iterator().next() : null;
    }

    public void setExpressedInOrganism(Organism organism) {
        if (this.hostOrganisms == null && organism != null){
            this.hostOrganisms = new ArrayList<Organism>();
            this.hostOrganisms.add(organism);
        }
        else if (this.hostOrganisms != null){
            if (!this.hostOrganisms.isEmpty() && organism == null){
                this.hostOrganisms.remove(0);
            }
            else if (organism != null){
                this.hostOrganisms.remove(0);
                this.hostOrganisms.add(0, organism);
            }
        }
    }

    public Collection<Confidence> getConfidences() {
        if (confidences == null){
            initialiseConfidences();
        }
        return this.confidences;
    }

    public Collection<Parameter> getParameters() {
        if (parameters == null){
            initialiseParameters();
        }
        return this.parameters;
    }

    @Override
    public String toString() {
        return super.toString() + (getExperimentalRole() != null ? ", " + getExperimentalRole().toString() : "") + (getExpressedInOrganism() != null ? ", " + getExpressedInOrganism().toString() : "");
    }

    @Override
    @XmlElement(name = "names")
    public NamesContainer getJAXBNames() {
        return super.getJAXBNames();
    }

    @Override
    @XmlElement(name = "xref")
    public XrefContainer getJAXBXref() {
        return super.getJAXBXref();
    }

    @Override
    @XmlElement(name = "interactionRef")
    public Integer getJAXBInteractionRef() {
        return super.getJAXBInteractionRef();
    }

    @Override
    @XmlElement(name = "interactorRef")
    public Integer getJAXBInteractorRef() {
        return super.getJAXBInteractorRef();
    }

    @Override
    @XmlElement(name = "biologicalRole", type = XmlCvTerm.class)
    public CvTerm getJAXBBiologicalRole() {
        return super.getJAXBBiologicalRole();
    }

    @Override
    @XmlElement(name = "interactor", type = XmlInteractor.class)
    public XmlInteractor getJAXBInteractor() {
        return super.getJAXBInteractor();
    }

    @Override
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return super.getJAXBId();
    }

    /**
     * Gets the value of the jaxbAttributeList property.
     *
     * @return
     *     possible object is
     *     {@link XmlAnnotation }
     *
     */
    @XmlElementWrapper(name="attributeList")
    @XmlElement(type=XmlAnnotation.class, name="attribute", required = true)
    public List<Annotation> getJAXBAttributes() {
        return super.getJAXBAttributes();
    }

    /**
     * Gets the value of the featureList property.
     *
     * @return
     *     possible object is
     *     {@link AbstractXmlFeature }
     *
     */
    @XmlElementWrapper(name = "featureList")
    @XmlElement(type=XmlFeatureEvidence.class, name="feature", required = true)
    public List<FeatureEvidence> getJAXBFeatures() {
        return super.getJAXBFeatures();
    }

    /**
     * Gets the value of the participantIdentificationMethodList property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElementWrapper(name="participantIdentificationMethodList")
    @XmlElement(type=ExperimentalCvTerm.class, name="participantIdentificationMethod", required = true)
    public List<CvTerm> getJAXBParticipantIdentificationMethods() {
        if (this.identificationMethods == null){
            initialiseIdentificationMethods();
        }
        return (List<CvTerm>)this.identificationMethods;
    }

    /**
     * Gets the value of the experimentalRoleList property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElementWrapper(name="experimentalRoleList")
    @XmlElement(type=ExperimentalCvTerm.class, name="experimentalRole", required = true)
    public List<CvTerm> getJAXBExperimentalRoles() {
        if (this.experimentalRoles == null){
            this.experimentalRoles = new ArrayList<CvTerm>();
        }
        return this.experimentalRoles;
    }

    /**
     * Gets the value of the experimentalRoleList property.
     *
     * @return
     *     possible object is
     *     {@link XmlCvTerm }
     *
     */
    @XmlElementWrapper(name="experimentalPreparationList")
    @XmlElement(type=ExperimentalCvTerm.class, name="experimentalPreparation", required = true)
    public List<CvTerm> getJAXBExperimentalPreparations() {
        if (this.experimentalPreparations == null){
           initialiseExperimentalPreparations();
        }
        return (List<CvTerm>)this.experimentalPreparations;
    }

    /**
     * Gets the value of the experimentalInteractorList property.
     *
     * @return
     *     possible object is
     *     {@link ExperimentalInteractor }
     *
     */
    @XmlElementWrapper(name="experimentalInteractorList")
    @XmlElement(type=ExperimentalInteractor.class, name="experimentalInteractor", required = true)
    public List<ExperimentalInteractor> getExperimentalInteractors() {
        if (this.experimentalInteractors == null){
            this.experimentalInteractors = new ArrayList<ExperimentalInteractor>();
        }
        return this.experimentalInteractors;
    }

    /**
     * Gets the value of the hostOrganismList property.
     *
     * @return
     *     possible object is
     *     {@link HostOrganism }
     *
     */
    @XmlElementWrapper(name="hostOrganismList")
    @XmlElement(type=HostOrganism.class, name="hostOrganism", required = true)
    public List<Organism> getJAXBHostOrganisms() {
        if (this.hostOrganisms == null){
            this.hostOrganisms = new ArrayList<Organism>();
        }
        return this.hostOrganisms;
    }

    /**
     * Gets the value of the parameterList property.
     *
     * @return
     *     possible object is
     *     {@link XmlParameter }
     *
     */
    @XmlElementWrapper(name="parameterList")
    @XmlElement(type=XmlParameter.class, name="parameter", required = true)
    public List<Parameter> getJAXBParameters() {
        if (this.parameters == null){
           initialiseParameters();
        }
        return (List<Parameter>)this.parameters;
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return
     *     possible object is
     *     {@link XmlConfidence }
     *
     */
    @XmlElementWrapper(name="confidenceList")
    @XmlElement(type=XmlConfidence.class, name="confidence", required = true)
    public List<Confidence> getJAXBConfidences() {
        if (this.confidences == null){
            initialiseConfidences();
        }
        return (List<Confidence>)this.confidences;
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
}
