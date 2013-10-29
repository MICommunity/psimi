package psidev.psi.mi.jami.xml.extension.binary;

import com.sun.xml.bind.Locatable;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.ModelledBinaryInteractionWrapper;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlEntry;
import psidev.psi.mi.jami.xml.extension.InteractionXrefContainer;
import psidev.psi.mi.jami.xml.extension.NamesContainer;
import psidev.psi.mi.jami.xml.extension.XmlModelledInteraction;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Date;

/**
 * Wrapper for Xml binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlModelledBinaryInteractionWrapper implements ModelledBinaryInteraction, FileSourceContext, Locatable{
    private XmlModelledInteraction wrappedInteraction;
    private ModelledBinaryInteractionWrapper binaryWrapper;

    public XmlModelledBinaryInteractionWrapper(XmlModelledInteraction interaction){
        this.wrappedInteraction = interaction;
        this.binaryWrapper = new ModelledBinaryInteractionWrapper(interaction);
    }

    public XmlModelledBinaryInteractionWrapper(XmlModelledInteraction interaction, CvTerm complexExpansion){
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
     * @throws IllegalArgumentException if this Binary interaction already contains two participants or the given participants contain more than two participants
     */
    @Override
    public boolean addAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.binaryWrapper.addAllParticipants(participants);
    }

    @Override
    public boolean removeAllParticipants(Collection<? extends ModelledParticipant> participants) {
        return this.binaryWrapper.removeAllParticipants(participants);
    }

    public int getJAXBId() {
        return this.wrappedInteraction.getJAXBId();
    }

    @Override
    public Locator sourceLocation() {
        return this.wrappedInteraction.sourceLocation();
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
    public void setCreatedDate(Date created) {
        this.wrappedInteraction.setCreatedDate(created);
    }

    @Override
    public Date getCreatedDate() {
        return this.wrappedInteraction.getCreatedDate();
    }

    @Override
    public CvTerm getInteractionType() {
        return this.wrappedInteraction.getInteractionType();
    }

    @Override
    public void setInteractionType(CvTerm term) {
        this.wrappedInteraction.setInteractionType(term);
    }

    public void setJAXBNames(NamesContainer value) {
        this.wrappedInteraction.setJAXBNames(value);
    }

    public void setJAXBXref(InteractionXrefContainer value) {
        this.wrappedInteraction.setJAXBXref(value);
    }

    public Boolean getJAXBIntraMolecular(){
        return this.wrappedInteraction.getJAXBIntraMolecular();
    }

    public void setJAXBIntraMolecular(Boolean value) {
        this.wrappedInteraction.setJAXBIntraMolecular(value);
    }

    public void setJAXBId(int value) {
        this.wrappedInteraction.setJAXBIdOnly(value);
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        return this.wrappedInteraction.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.wrappedInteraction.setSourceLocator(sourceLocator);
    }

    public XmlEntry getEntry() {
        return this.wrappedInteraction.getEntry();
    }

    public void setEntry(XmlEntry entry) {
        this.wrappedInteraction.setEntry(entry);
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
}
