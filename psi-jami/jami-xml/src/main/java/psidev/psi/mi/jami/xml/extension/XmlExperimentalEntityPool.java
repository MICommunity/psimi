package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.Xml25EntryContext;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * XML implementation of a set of ExperimentalEntity that form a single participant evidence
 * Notes: The equals and hashcode methods have NOT been overridden because the XmlExperimentalEntityPool object is a complex object.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlExperimentalEntityPool extends AbstractXmlEntityPool<InteractionEvidence, FeatureEvidence, ExperimentalEntity> implements ExperimentalEntityPool {

    @XmlLocation
    @XmlTransient
    private Locator locator;

    private JAXBParticipantIdentificationWrapper jaxbParticipantionIdentificationWrapper;
    private JAXBExperimentalPreparationWrapper jaxbExperimentalPreparationWrapper;
    private JAXBExperimentalRoleWrapper jaxbExperimentalRoleWrapper;
    private JAXBExperimentalInteractorWrapper jaxbExperimentalInteractorWrapper;
    private JAXBHostOrganismWrapper jaxbHostOrganismWrapper;
    private JAXBConfidenceWrapper jaxbConfidenceWrapper;
    private JAXBParameterWrapper jaxbParameterWrapper;

    public XmlExperimentalEntityPool() {
        super();
    }

    public XmlExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName));
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole, stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole, stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole);
        setExpressedInOrganism(expressedIn);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntityPool(InteractionEvidence interaction, String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole, stoichiometry);
        setExpressedInOrganism(expressedIn);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
        setInteraction(interaction);
    }

    public XmlExperimentalEntityPool(String interactorSetName, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName));
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntityPool(String interactorSetName, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm expRole, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole, stoichiometry);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm expRole, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole);
        setExpressedInOrganism(expressedIn);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntityPool(String interactorSetName, CvTerm bioRole, CvTerm expRole, Stoichiometry stoichiometry, Organism expressedIn, CvTerm participantIdentificationMethod) {
        super(new XmlInteractorPool(interactorSetName), bioRole, stoichiometry);
        setExpressedInOrganism(expressedIn);
        if (participantIdentificationMethod != null){
            getIdentificationMethods().add(participantIdentificationMethod);
        }
    }

    public XmlExperimentalEntityPool(String interactorSetName) {
        super(new XmlInteractorPool(interactorSetName));
    }

    public XmlExperimentalEntityPool(String interactorSetName, Stoichiometry stoichiometry) {
        super(new XmlInteractorPool(interactorSetName), stoichiometry);
    }

    protected void initialiseExperimentalPreparationWrapper() {
        this.jaxbExperimentalPreparationWrapper = new JAXBExperimentalPreparationWrapper();
    }

    protected void initialiseExperimentalRoleWrapper() {
        this.jaxbExperimentalRoleWrapper = new JAXBExperimentalRoleWrapper();
    }

    protected void initialiseConfidenceWrapper() {
        this.jaxbConfidenceWrapper = new JAXBConfidenceWrapper();
    }

    protected void initialiseParameterWrapper() {
        this.jaxbParameterWrapper = new JAXBParameterWrapper();
    }

    protected void initialiseIdentificationMethodWrapper(){
        this.jaxbParticipantionIdentificationWrapper = new JAXBParticipantIdentificationWrapper();
    }

    protected void initialiseHostOrganismWrapper() {
        this.jaxbHostOrganismWrapper = new JAXBHostOrganismWrapper();
    }

    protected void initialiseExperimentalInteractorWrapper() {
        this.jaxbExperimentalInteractorWrapper = new JAXBExperimentalInteractorWrapper();
    }

    public CvTerm getExperimentalRole() {
        if (this.jaxbExperimentalRoleWrapper == null){
            initialiseExperimentalRoleWrapper();
        }
        if (this.jaxbExperimentalRoleWrapper.experimentalRoles.isEmpty()){
            this.jaxbExperimentalRoleWrapper.experimentalRoles.add(new XmlCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI));
        }
        return this.jaxbExperimentalRoleWrapper.experimentalRoles.get(0);
    }

    public void setExperimentalRole(CvTerm expRole) {
        if (this.jaxbExperimentalRoleWrapper == null && expRole != null){
            initialiseExperimentalRoleWrapper();
            this.jaxbExperimentalRoleWrapper.experimentalRoles.add(expRole);
        }
        else if (expRole != null){
            if (!this.jaxbExperimentalRoleWrapper.experimentalRoles.isEmpty()){
                this.jaxbExperimentalRoleWrapper.experimentalRoles.remove(0);
            }
            this.jaxbExperimentalRoleWrapper.experimentalRoles.add(0, expRole);
        }
        else{
            if (!this.jaxbExperimentalRoleWrapper.experimentalRoles.isEmpty()){
                this.jaxbExperimentalRoleWrapper.experimentalRoles.remove(0);
            }
        }
    }

    public Collection<CvTerm> getIdentificationMethods() {
        if (jaxbParticipantionIdentificationWrapper == null){
            initialiseIdentificationMethodWrapper();
        }
        return this.jaxbParticipantionIdentificationWrapper.identificationMethods;
    }

    public Collection<CvTerm> getExperimentalPreparations() {
        if (jaxbExperimentalPreparationWrapper == null){
            initialiseExperimentalPreparationWrapper();
        }
        return this.jaxbExperimentalPreparationWrapper.experimentalPreparations;
    }

    public Organism getExpressedInOrganism() {
        return (this.jaxbHostOrganismWrapper != null && !this.jaxbHostOrganismWrapper.hostOrganisms.isEmpty())? this.jaxbHostOrganismWrapper.hostOrganisms.iterator().next() : null;
    }

    public void setExpressedInOrganism(Organism organism) {
        if (this.jaxbHostOrganismWrapper == null && organism != null){
            initialiseHostOrganismWrapper();
            this.jaxbHostOrganismWrapper.hostOrganisms.add(organism);
        }
        else if (organism != null){
            if (!this.jaxbHostOrganismWrapper.hostOrganisms.isEmpty()){
                this.jaxbHostOrganismWrapper.hostOrganisms.remove(0);
            }
            this.jaxbHostOrganismWrapper.hostOrganisms.add(0, organism);
        }
        else{
            if (!this.jaxbHostOrganismWrapper.hostOrganisms.isEmpty()){
                this.jaxbHostOrganismWrapper.hostOrganisms.remove(0);
            }
        }
    }

    public Collection<Confidence> getConfidences() {
        if (jaxbConfidenceWrapper == null){
            initialiseConfidenceWrapper();
        }
        return this.jaxbConfidenceWrapper.confidences;
    }

    public Collection<Parameter> getParameters() {
        if (this.jaxbParameterWrapper == null){
            initialiseParameterWrapper();
        }
        return this.jaxbParameterWrapper.parameters;
    }

    public Collection<ExperimentalInteractor> getExperimentalInteractors() {
        if (this.jaxbExperimentalInteractorWrapper == null){
            initialiseExperimentalInteractorWrapper();
        }
        return this.jaxbExperimentalInteractorWrapper.experimentalInteractors;
    }

    @Override
    @XmlElement(name = "names")
    public void setJAXBNames(NamesContainer value) {
        super.setJAXBNames(value);
    }

    @Override
    @XmlElement(name = "xref")
    public void setJAXBXref(XrefContainer value) {
        super.setJAXBXref(value);
    }

    @Override
    @XmlElement(name = "interactor")
    public void setJAXBInteractor(XmlInteractor interactor) {
        super.setJAXBInteractor(interactor);
    }

    @Override
    @XmlElement(name = "interactionRef")
    public void setJAXBInteractionRef(Integer value) {
        super.setJAXBInteractionRef(value);
    }

    @Override
    @XmlElement(name = "interactorRef")
    public void setJAXBInteractorRef(Integer value) {
        super.setJAXBInteractorRef(value);
    }

    @Override
    @XmlElement(name = "biologicalRole", type = XmlCvTerm.class)
    public void setJAXBBiologicalRole(CvTerm bioRole) {
        super.setJAXBBiologicalRole(bioRole);
    }

    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int value) {
        super.setId(value);
    }

    @Override
    @XmlElement(name="attributeList")
    public void setJAXBAttributeWrapper(JAXBAttributeWrapper jaxbAttributeWrapper) {
        super.setJAXBAttributeWrapper(jaxbAttributeWrapper);
    }

    @XmlElement(name = "featureList")
    public void setJAXBFeatureWrapper(JAXBFeatureWrapper jaxbFeatureWrapper) {
        super.setFeatureWrapper(jaxbFeatureWrapper);
    }

    @XmlElement(name="participantIdentificationMethodList")
    public void setJAXBParticipantIdentificationMethodWrapper(JAXBParticipantIdentificationWrapper wrapper) {
        this.jaxbParticipantionIdentificationWrapper = wrapper;
    }

    @XmlElement(name="experimentalRoleList")
    public void setJAXBExperimentalRoleWrapper(JAXBExperimentalRoleWrapper wrapper) {
        this.jaxbExperimentalRoleWrapper = wrapper;
    }

    @XmlElement(name="experimentalPreparationList")
    public void setJAXBExperimentalPreparationWrapper(JAXBExperimentalPreparationWrapper wrapper) {
        this.jaxbExperimentalPreparationWrapper = wrapper;
    }

    @XmlElement(name="experimentalInteractorList")
    public void setExperimentalInteractorWrapper(JAXBExperimentalInteractorWrapper wrapper) {
        this.jaxbExperimentalInteractorWrapper = wrapper;
    }

    @XmlElement(name="hostOrganismList")
    public void setJAXBHostOrganismWrapper(JAXBHostOrganismWrapper wrapper) {
        this.jaxbHostOrganismWrapper = wrapper;
        if (this.jaxbHostOrganismWrapper != null && this.jaxbHostOrganismWrapper.hostOrganisms.size() > 1 ){
            PsiXmlParserListener listener = Xml25EntryContext.getInstance().getListener();
            if (listener != null){
                listener.onSeveralExpressedInOrganismFound(this.jaxbHostOrganismWrapper.hostOrganisms, this.jaxbHostOrganismWrapper.getSourceLocator());
            }
        }
    }

    @XmlElement(name="parameterList")
    public void setJAXBParameterWrapper(JAXBParameterWrapper wrapper) {
        this.jaxbParameterWrapper = wrapper;
    }

    @XmlElement(name="confidenceList")
    public void setJAXBConfidenceWrapper(JAXBConfidenceWrapper wrapper) {
        this.jaxbConfidenceWrapper = wrapper;
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
    protected void initialiseFeatureWrapper() {
        super.setFeatureWrapper(new JAXBFeatureWrapper());
    }

    ////////////////////////////////////////////////////// classes
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalEntitySetFeatureWrapper")
    public static class JAXBFeatureWrapper extends AbstractXmlEntity.JAXBFeatureWrapper<FeatureEvidence> {

        public JAXBFeatureWrapper(){
            super();
        }

        @XmlElement(type=XmlFeatureEvidence.class, name="feature", required = true)
        public List<FeatureEvidence> getJAXBFeatures() {
            return super.getJAXBFeatures();
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalEntitySetIdentificationWrapper")
    public static class JAXBParticipantIdentificationWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<CvTerm> identificationMethods;

        public JAXBParticipantIdentificationWrapper(){
            initialiseIdentificationMethods();
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

        @XmlElement(type=ExperimentalCvTerm.class, name="participantIdentificationMethod", required = true)
        public List<CvTerm> getJAXBParticipantIdentificationMethods() {
            return this.identificationMethods;
        }

        protected void initialiseIdentificationMethods(){
            this.identificationMethods = new ArrayList<CvTerm>();
        }

        @Override
        public String toString() {
            return "Participant Identification method List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalEntitySetPreparationWrapper")
    public static class JAXBExperimentalPreparationWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<CvTerm> experimentalPreparations;

        public JAXBExperimentalPreparationWrapper(){
            initialiseExperimentalPreparations();
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

        @XmlElement(type=ExperimentalCvTerm.class, name="experimentalPreparation", required = true)
        public List<CvTerm> getJAXBExperimentalPreparations() {
            return this.experimentalPreparations;
        }

        protected void initialiseExperimentalPreparations(){
            this.experimentalPreparations = new ArrayList<CvTerm>();
        }

        @Override
        public String toString() {
            return "Participant Experimental Preparation List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalEntitySetRoleWrapper")
    public static class JAXBExperimentalRoleWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<CvTerm> experimentalRoles;

        public JAXBExperimentalRoleWrapper(){
            initialiseExperimentalRoles();
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

        @XmlElement(type=ExperimentalCvTerm.class, name="experimentalRole", required = true)
        public List<CvTerm> getJAXBExperimentalRoles() {
            return this.experimentalRoles;
        }

        protected void initialiseExperimentalRoles(){
            this.experimentalRoles = new ArrayList<CvTerm>();
        }

        @Override
        public String toString() {
            return "Participant Experimental Role List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalEntitySetInteractorWrapper")
    public static class JAXBExperimentalInteractorWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<ExperimentalInteractor> experimentalInteractors;

        public JAXBExperimentalInteractorWrapper(){
            initialiseExperimentalIneteractors();
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

        @XmlElement(type=ExperimentalInteractor.class, name="experimentalInteractor", required = true)
        public List<ExperimentalInteractor> getJAXBExperimentalInteractors() {
            return this.experimentalInteractors;
        }

        protected void initialiseExperimentalIneteractors(){
            this.experimentalInteractors = new ArrayList<ExperimentalInteractor>();
        }

        @Override
        public String toString() {
            return "Participant Experimental Interactor List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalEntitySetOrganismWrapper")
    public static class JAXBHostOrganismWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<Organism> hostOrganisms;

        public JAXBHostOrganismWrapper(){
            initialiseHostOrganisms();
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

        @XmlElement(type=HostOrganism.class, name="hostOrganism", required = true)
        public List<Organism> getJAXBHostOrganisms() {
            return this.hostOrganisms;
        }

        protected void initialiseHostOrganisms(){
            this.hostOrganisms = new ArrayList<Organism>();
        }

        @Override
        public String toString() {
            return "Participant Host Organism List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalEntitySetConfidenceWrapper")
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

        @Override
        public String toString() {
            return "Participant Confidence List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="experimentalEntitySetParameterWrapper")
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

        @Override
        public String toString() {
            return "Participant Parameter List: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }
}
