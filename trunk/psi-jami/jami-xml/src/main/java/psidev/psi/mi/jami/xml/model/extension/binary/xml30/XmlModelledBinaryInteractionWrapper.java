package psidev.psi.mi.jami.xml.model.extension.binary.xml30;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.ModelledBinaryInteractionWrapper;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.model.Entry;
import psidev.psi.mi.jami.xml.model.extension.xml300.BindingFeatures;
import psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlCausalRelationship;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Wrapper for Xml binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlModelledBinaryInteractionWrapper implements ModelledBinaryInteraction, FileSourceContext,
        psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlModelledInteraction, Serializable {
    private psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlModelledInteraction wrappedInteraction;
    private ModelledBinaryInteractionWrapper binaryWrapper;

    public XmlModelledBinaryInteractionWrapper(psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlModelledInteraction interaction){
        this.wrappedInteraction = interaction;
        this.binaryWrapper = new ModelledBinaryInteractionWrapper(interaction);
    }

    public XmlModelledBinaryInteractionWrapper(psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlModelledInteraction interaction, CvTerm complexExpansion){
        this(interaction);
        this.binaryWrapper = new ModelledBinaryInteractionWrapper(interaction, complexExpansion);
    }

    public ModelledParticipant getParticipantA() {
        return this.binaryWrapper.getParticipantA();
    }

    public ModelledParticipant getParticipantB() {
        return this.binaryWrapper.getParticipantB();
    }

    public void setParticipantA(ModelledParticipant participantA) {
        this.binaryWrapper.setParticipantA(participantA);
    }

    public void setParticipantB(ModelledParticipant participantB) {
        this.binaryWrapper.setParticipantB(participantB);
    }

    public CvTerm getComplexExpansion() {
        return this.binaryWrapper.getComplexExpansion();
    }

    public void setComplexExpansion(CvTerm expansion) {
        this.binaryWrapper.setComplexExpansion(expansion);
    }

    /**
     * The collection of participants for this binary interaction.
     * It cannot be changed.
     * @return
     */
    @Override
    public Collection<ModelledParticipant> getParticipants() {
        return this.binaryWrapper.getParticipants();
    }

    /**
     * Adds a new Participant and set the Interaction of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param part
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants
     */
    @Override
    public boolean addParticipant(ModelledParticipant part) {
        return this.binaryWrapper.addParticipant(part);
    }

    /**
     * Removes the Participant from this binary interaction
     * @param part
     * @return
     */
    @Override
    public boolean removeParticipant(ModelledParticipant part) {
        return this.binaryWrapper.removeParticipant(part);
    }

    /**
     * Adds the participants and set the Interaction of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param participants
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants or the given participants contains more than two participants
     */
    @Override
    public boolean addAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.binaryWrapper.addAllParticipants(participants);
    }

    @Override
    public boolean removeAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.binaryWrapper.removeAllParticipants(participants);
    }

    @Override
    public String getFullName() {
        return this.wrappedInteraction.getFullName();
    }

    @Override
    public void setFullName(String name) {
        this.wrappedInteraction.setFullName(name);
    }

    @Override
    public Xref getPreferredIdentifier() {
        return this.wrappedInteraction.getPreferredIdentifier();
    }

    @Override
    public Organism getOrganism() {
        return this.wrappedInteraction.getOrganism();
    }

    @Override
    public void setOrganism(Organism organism) {
        this.wrappedInteraction.setOrganism(organism);
    }

    @Override
    public CvTerm getInteractorType() {
        return this.wrappedInteraction.getInteractorType();
    }

    @Override
    public void setInteractorType(CvTerm type) {
         this.wrappedInteraction.setInteractorType(type);
    }

    @Override
    public List<BindingFeatures> getBindingFeatures() {
        return this.wrappedInteraction.getBindingFeatures();
    }

    @Override
    public List<ExtendedPsiXmlCausalRelationship> getCausalRelationships() {
        return this.wrappedInteraction.getCausalRelationships();
    }

    @Override
    public Collection<Alias> getAliases() {
        return this.wrappedInteraction.getAliases();
    }

    @Override
    public boolean isIntraMolecular() {
        return this.wrappedInteraction.isIntraMolecular();
    }

    @Override
    public void setIntraMolecular(boolean intra) {
        this.wrappedInteraction.setIntraMolecular(intra);
    }

    @Override
    public Entry getEntry() {
        return this.wrappedInteraction.getEntry();
    }

    @Override
    public void setEntry(Entry entry) {
        this.wrappedInteraction.setEntry(entry);
    }

    @Override
    public int getId() {
        return this.wrappedInteraction.getId();
    }

    @Override
    public void setId(int id) {
        this.wrappedInteraction.setId(id);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        return this.wrappedInteraction.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator locator) {
        this.wrappedInteraction.setSourceLocator(locator);
    }

    @Override
    public String getShortName() {
        return this.wrappedInteraction.getShortName();
    }

    @Override
    public void setShortName(String name) {
        this.wrappedInteraction.setShortName(name);
    }

    @Override
    public String getRigid() {
        return this.wrappedInteraction.getRigid();
    }

    @Override
    public void setRigid(String rigid) {
        this.wrappedInteraction.setRigid(rigid);
    }

    @Override
    public Collection<Xref> getIdentifiers() {
        return this.wrappedInteraction.getIdentifiers();
    }

    @Override
    public Collection<Xref> getXrefs() {
        return this.wrappedInteraction.getXrefs();
    }

    @Override
    public Collection<Checksum> getChecksums() {
        return this.wrappedInteraction.getChecksums();
    }

    @Override
    public Collection<Annotation> getAnnotations() {
        return this.wrappedInteraction.getAnnotations();
    }

    @Override
    public Date getUpdatedDate() {
        return this.wrappedInteraction.getUpdatedDate();
    }

    @Override
    public void setUpdatedDate(Date updated) {
        this.wrappedInteraction.setUpdatedDate(updated);
    }

    @Override
    public Date getCreatedDate() {
        return this.wrappedInteraction.getCreatedDate();
    }

    @Override
    public void setCreatedDate(Date created) {
        this.wrappedInteraction.setCreatedDate(created);
    }

    @Override
    public CvTerm getInteractionType() {
        return this.wrappedInteraction.getInteractionType();
    }

    @Override
    public void setInteractionType(CvTerm term) {
        this.wrappedInteraction.setInteractionType(term);
    }

    @Override
    public String toString() {
        return this.wrappedInteraction.toString();
    }

    @Override
    public Collection<InteractionEvidence> getInteractionEvidences() {
        return this.wrappedInteraction.getInteractionEvidences();
    }

    @Override
    public Source getSource() {
        return this.wrappedInteraction.getSource();
    }

    @Override
    public void setSource(Source source) {
        this.wrappedInteraction.setSource(source);
    }

    @Override
    public CvTerm getEvidenceType() {
        return this.wrappedInteraction.getEvidenceType();
    }

    @Override
    public void setEvidenceType(CvTerm eco) {
        this.wrappedInteraction.setEvidenceType(eco);
    }

    @Override
    public Collection<ModelledConfidence> getModelledConfidences() {
        return this.wrappedInteraction.getModelledConfidences();
    }

    @Override
    public Collection<ModelledParameter> getModelledParameters() {
        return this.wrappedInteraction.getModelledParameters();
    }

    @Override
    public Collection<CooperativeEffect> getCooperativeEffects() {
        return this.wrappedInteraction.getCooperativeEffects();
    }

    @Override
    public String getPhysicalProperties() {
        return this.wrappedInteraction.getPhysicalProperties();
    }

    @Override
    public void setPhysicalProperties(String properties) {
        this.wrappedInteraction.setPhysicalProperties(properties);
    }

    @Override
    public String getRecommendedName() {
        return this.wrappedInteraction.getRecommendedName();
    }

    @Override
    public void setRecommendedName(String name) {
         this.wrappedInteraction.setRecommendedName(name);
    }

    @Override
    public String getSystematicName() {
        return this.wrappedInteraction.getSystematicName();
    }

    @Override
    public void setSystematicName(String name) {
         this.wrappedInteraction.setSystematicName(name);
    }
}
