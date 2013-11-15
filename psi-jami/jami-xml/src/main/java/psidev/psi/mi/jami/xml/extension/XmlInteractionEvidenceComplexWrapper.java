package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.xml.Entry;
import psidev.psi.mi.jami.xml.Xml25EntryContext;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Xml wrapper for interaction evidences used as complex
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public class XmlInteractionEvidenceComplexWrapper implements Complex,FileSourceContext, ExtendedPsi25Interaction<ModelledParticipant> {
    private XmlInteractionEvidence interactionEvidence;
    private Organism organism;
    private CvTerm interactorType;
    private Collection<InteractionEvidence> interactionEvidences;
    private Collection<ModelledConfidence> modelledConfidences;
    private Collection<ModelledParameter> modelledParameters;
    private Collection<CooperativeEffect> cooperativeEffects;
    private Collection<ModelledParticipant> modelledParticipants;

    public XmlInteractionEvidenceComplexWrapper(XmlInteractionEvidence interaction){
        if (interaction == null){
            throw new IllegalArgumentException("The complex wrapper needs a non null basic interaction");
        }
        this.interactionEvidence = interaction;
        Xml25EntryContext.getInstance().registerComplex(interaction.getId(), this);
    }

    public Date getUpdatedDate() {
        return this.interactionEvidence.getUpdatedDate();
    }

    public void setUpdatedDate(Date updated) {
        this.interactionEvidence.setUpdatedDate(updated);
    }

    public Date getCreatedDate() {
        return this.interactionEvidence.getCreatedDate();
    }

    public void setCreatedDate(Date created) {
        this.interactionEvidence.setCreatedDate(created);
    }

    public CvTerm getInteractionType() {
        return this.interactionEvidence.getInteractionType();
    }

    public void setInteractionType(CvTerm term) {
        this.interactionEvidence.setInteractionType(term);
    }

    public boolean addParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }
        if (this.modelledParticipants == null){
            initialiseParticipants();
        }
        if (this.modelledParticipants.add(part)){
            part.setInteraction(this);
            return true;
        }
        return false;
    }

    public boolean removeParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }
        if (this.modelledParticipants == null){
            initialiseParticipants();
        }
        if (this.modelledParticipants.remove(part)){
            part.setInteraction(null);
            return true;
        }
        return false;
    }

    public boolean addAllParticipants(Collection<? extends ModelledParticipant> participants) {
        if (participants == null){
            return false;
        }

        boolean added = false;
        for (ModelledParticipant p : participants){
            if (addParticipant(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllParticipants(Collection<? extends ModelledParticipant> participants) {
        if (participants == null){
            return false;
        }

        boolean removed = false;
        for (ModelledParticipant p : participants){
            if (removeParticipant(p)){
                removed = true;
            }
        }
        return removed;
    }

    public Collection<ModelledParticipant> getParticipants() {
        if (this.modelledParticipants == null){
            initialiseParticipants();
        }
        return this.modelledParticipants;
    }

    @Override
    public Collection<InteractionEvidence> getInteractionEvidences() {
        if (this.interactionEvidences == null){
            this.interactionEvidences = new ArrayList<InteractionEvidence>();
        }
        return this.interactionEvidences;
    }

    public Source getSource() {
        if (this.interactionEvidence.getExperiment() != null){
            Experiment exp =this.interactionEvidence.getExperiment();
            if (exp.getPublication() != null){
                return exp.getPublication().getSource();
            }
        }
        return null;
    }

    public void setSource(Source source) {
        if (this.interactionEvidence.getExperiment() != null){
            Experiment exp =this.interactionEvidence.getExperiment();
            if (exp.getPublication() != null){
                exp.getPublication().setSource(source);
            }
            else{
                exp.setPublicationAndAddExperiment(new BibRef());
                exp.getPublication().setSource(source);
            }
        }
        else{
            this.interactionEvidence.setExperimentAndAddInteractionEvidence(new XmlExperiment(new BibRef()));
            this.interactionEvidence.getExperiment().getPublication().setSource(source);
        }
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        if (this.modelledConfidences == null){
            initialiseModelledConfidences();
        }
        return this.modelledConfidences;
    }

    public Collection<ModelledParameter> getModelledParameters() {
        if (this.modelledParameters == null){
            initialiseModelledParameters();
        }
        return this.modelledParameters;
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        if (this.cooperativeEffects == null){
            this.cooperativeEffects = new ArrayList<CooperativeEffect>();
            // collect cooperative effects from interaction evidence annotations
            Collection<Annotation> annotations = new ArrayList<Annotation>(this.interactionEvidence.getAnnotations());
            CooperativeEffect effect = PsiXml25Utils.extractCooperativeEffectFrom(annotations, this.interactionEvidence.getExperiments(), Xml25EntryContext.getInstance().getListener());
            if (effect != null){
                getCooperativeEffects().add(effect);
            }
        }
        return this.cooperativeEffects;
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.interactionEvidence.getAnnotations();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        return this.interactionEvidence.getChecksums();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.interactionEvidence.getXrefs();
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return this.interactionEvidence.getIdentifiers();
    }

    @Override
    public String getShortName() {
        return this.interactionEvidence.getShortName() != null ? this.interactionEvidence.getShortName() : PsiXml25Utils.UNSPECIFIED;
    }

    @Override
    public void setShortName(String name) {
        this.interactionEvidence.setShortName(name);
    }

    @Override
    public String getFullName() {
        return this.interactionEvidence.getFullName();
    }

    @Override
    public void setFullName(String name) {
        this.interactionEvidence.setFullName(name);
    }

    @Override
    public Xref getPreferredIdentifier() {
        return !this.interactionEvidence.getIdentifiers().isEmpty()?this.interactionEvidence.getIdentifiers().iterator().next():null;
    }

    @Override
    public Organism getOrganism() {
        return this.organism;
    }

    @Override
    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    @Override
    public CvTerm getInteractorType() {
        return this.interactorType;
    }

    @Override
    public void setInteractorType(CvTerm type) {
        if (type == null){
            this.interactorType = new XmlCvTerm(Complex.COMPLEX, Complex.COMPLEX_MI);
        }
        else{
            this.interactorType = type;
        }
    }

    @Override
    public String getRigid() {
        return this.interactionEvidence.getRigid();
    }

    @Override
    public void setRigid(String rigid) {
        this.interactionEvidence.setRigid(rigid);
    }

    protected void initialiseModelledParameters(){
        this.modelledParameters = new ArrayList<ModelledParameter>();
        for (Parameter part : this.interactionEvidence.getParameters()){
            this.modelledParameters.add(new XmlParameterWrapper(part));
        }
    }

    protected void initialiseModelledConfidences(){
        this.modelledConfidences = new ArrayList<ModelledConfidence>();
        for (Confidence part : this.interactionEvidence.getConfidences()){
            this.modelledConfidences.add(new XmlConfidenceWrapper(part));
        }
    }

    protected void initialiseParticipants(){
        this.modelledParticipants = new ArrayList<ModelledParticipant>();
        for (ParticipantEvidence part : this.interactionEvidence.getParticipants()){
            this.modelledParticipants.add(new XmlParticipantEvidenceWrapper(part, this));
        }
    }

    @Override
    public String getPhysicalProperties() {
        Annotation properties = AnnotationUtils.collectFirstAnnotationWithTopic(this.interactionEvidence.getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        return properties != null ? properties.getValue() : null;
    }

    @Override
    public void setPhysicalProperties(String properties) {
        Annotation propertiesAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(this.interactionEvidence.getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        if (propertiesAnnot != null){
            propertiesAnnot.setValue(properties);
        }
        else{
            this.interactionEvidence.getAnnotations().add(new XmlAnnotation(CvTermUtils.createMICvTerm(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_MI), properties));
        }
    }

    @Override
    public List<Alias> getAliases() {
        return this.interactionEvidence.getAliases();
    }

    @Override
    public List<CvTerm> getInteractionTypes() {
        return this.interactionEvidence.getInteractionTypes();
    }

    @Override
    public Entry getEntry() {
        return this.interactionEvidence.getEntry();
    }

    @Override
    public void setEntry(Entry entry) {
        this.interactionEvidence.setEntry(entry);
    }

    @Override
    public List<InferredInteraction> getInferredInteractions() {
        return this.interactionEvidence.getInferredInteractions();
    }

    @Override
    public int getId() {
        return this.interactionEvidence.getId();
    }

    @Override
    public void setId(int id) {
        this.interactionEvidence.setId(id);
        Xml25EntryContext.getInstance().registerComplex(id, this);
    }

    @Override
    public boolean isIntraMolecular() {
        return this.interactionEvidence.isIntraMolecular();
    }

    @Override
    public void setIntraMolecular(boolean intra) {
        this.setIntraMolecular(intra);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        return this.interactionEvidence.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator locator) {
        this.interactionEvidence.setSourceLocator(locator);
    }

    @Override
    public String toString() {
        return this.interactionEvidence.toString();
    }
}
