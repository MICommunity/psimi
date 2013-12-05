package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;
import psidev.psi.mi.jami.xml.Entry;
import psidev.psi.mi.jami.xml.Xml25EntryContext;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import java.util.*;

/**
 * Wrapper of basic interactions
 *
 * If we add new modelled participants/remove participants, they will be added/removed from the list of participants of the
 * wrapped interaction.
 *
 * However, the interaction that is in the back references of new participants will be this wrapper.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public class XmlBasicInteractionComplexWrapper implements Complex,FileSourceContext, ExtendedPsi25Interaction<ModelledParticipant> {

    private AbstractXmlBasicInteraction interaction;
    private SynchronizedModelledParticipantList modelledParticipants;
    private Collection<InteractionEvidence> interactionEvidences;
    private Collection<ModelledConfidence> modelledConfidences;
    private Collection<ModelledParameter> modelledParameters;
    private Collection<CooperativeEffect> cooperativeEffects;
    private Source source;
    private Organism organism;
    private CvTerm interactorType;

    public XmlBasicInteractionComplexWrapper(AbstractXmlBasicInteraction interaction){
        if (interaction == null){
            throw new IllegalArgumentException("The complex wrapper needs a non null basic interaction");
        }
        this.interaction = interaction;
        Xml25EntryContext.getInstance().registerComplex(interaction.getId(), this);
    }

    public Date getUpdatedDate() {
        return this.interaction.getUpdatedDate();
    }

    public void setUpdatedDate(Date updated) {
        this.interaction.setUpdatedDate(updated);
    }

    public Date getCreatedDate() {
        return this.interaction.getCreatedDate();
    }

    public void setCreatedDate(Date created) {
        this.interaction.setCreatedDate(created);
    }

    public CvTerm getInteractionType() {
        return this.interaction.getInteractionType();
    }

    public void setInteractionType(CvTerm term) {
        this.interaction.setInteractionType(term);
    }

    @Override
    public boolean isIntraMolecular() {
        return this.interaction.isIntraMolecular();
    }

    @Override
    public void setIntraMolecular(boolean intra) {
        this.interaction.setIntraMolecular(intra);
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

    public Collection<InteractionEvidence> getInteractionEvidences() {
        if (this.interactionEvidences == null){
            this.interactionEvidences = new ArrayList<InteractionEvidence>();
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
        if (this.modelledConfidences == null){
            this.modelledConfidences = new ArrayList<ModelledConfidence>();
        }
        return this.modelledConfidences;
    }

    public Collection<ModelledParameter> getModelledParameters() {
        if (this.modelledParameters == null){
            this.modelledParameters = new ArrayList<ModelledParameter>();
        }
        return this.modelledParameters;
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        if (this.cooperativeEffects == null){
           this.cooperativeEffects = new ArrayList<CooperativeEffect>();
            // collect cooperative effects from interaction evidence annotations
            Collection<Annotation> annotations = new ArrayList<Annotation>(this.interaction.getAnnotations());
            CooperativeEffect effect = PsiXml25Utils.extractCooperativeEffectFrom(annotations, Collections.EMPTY_LIST, Xml25EntryContext.getInstance().getListener());
            if (effect != null){
                getCooperativeEffects().add(effect);
            }
        }
        return this.cooperativeEffects;
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.interaction.getAnnotations();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        return this.interaction.getChecksums();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.interaction.getXrefs();
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return this.interaction.getIdentifiers();
    }

    @Override
    public String getShortName() {
        return this.interaction.getShortName() != null ? this.interaction.getShortName() : PsiXml25Utils.UNSPECIFIED;
    }

    @Override
    public void setShortName(String name) {
        this.interaction.setShortName(name);
    }

    @Override
    public String getFullName() {
        return this.interaction.getFullName();
    }

    @Override
    public void setFullName(String name) {
        this.interaction.setFullName(name);
    }

    @Override
    public Xref getPreferredIdentifier() {
        return !this.interaction.getIdentifiers().isEmpty()?this.interaction.getIdentifiers().iterator().next():null;
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
        return this.interaction.getRigid();
    }

    @Override
    public void setRigid(String rigid) {
        this.interaction.setRigid(rigid);
    }

    @Override
    public String getPhysicalProperties() {
        Annotation properties = AnnotationUtils.collectFirstAnnotationWithTopic(this.interaction.getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        return properties != null ? properties.getValue() : null;
    }

    @Override
    public void setPhysicalProperties(String properties) {
        Annotation propertiesAnnot = AnnotationUtils.collectFirstAnnotationWithTopic(this.interaction.getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        if (propertiesAnnot != null){
            propertiesAnnot.setValue(properties);
        }
        else{
            this.interaction.getAnnotations().add(new XmlAnnotation(CvTermUtils.createMICvTerm(Annotation.COMPLEX_PROPERTIES, Annotation.COMPLEX_PROPERTIES_MI), properties));
        }
    }

    @Override
    public List<Alias> getAliases() {
        return this.interaction.getAliases();
    }

    @Override
    public List<CvTerm> getInteractionTypes() {
        return this.interaction.getInteractionTypes();
    }

    @Override
    public Entry getEntry() {
        return this.interaction.getEntry();
    }

    @Override
    public void setEntry(Entry entry) {
        this.interaction.setEntry(entry);
    }

    @Override
    public List<InferredInteraction> getInferredInteractions() {
        return this.interaction.getInferredInteractions();
    }

    @Override
    public int getId() {
        return this.interaction.getId();
    }

    @Override
    public void setId(int id) {
        this.interaction.setId(id);
        Xml25EntryContext.getInstance().registerComplex(id, this);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        return this.interaction.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator locator) {
        this.interaction.setSourceLocator(locator);
    }

    public AbstractXmlBasicInteraction getWrappedInteraction(){
        return this.interaction;
    }

    protected void initialiseParticipants(){
        this.modelledParticipants = new SynchronizedModelledParticipantList();
        for (Participant part : this.interaction.getParticipants()){
            this.modelledParticipants.addOnly(new XmlParticipantWrapper(part, this));
        }
    }

    @Override
    public String toString() {
        return interaction.toString();
    }

    ////////////////////////////////////// classes
    private class SynchronizedModelledParticipantList extends AbstractListHavingProperties<ModelledParticipant>{

        @Override
        protected void processAddedObjectEvent(ModelledParticipant added) {
            interaction.getParticipants().add(added);
        }

        @Override
        protected void processRemovedObjectEvent(ModelledParticipant removed) {
            interaction.getParticipants().remove(removed);
        }

        @Override
        protected void clearProperties() {
            interaction.getParticipants().clear();
        }
    }
}

