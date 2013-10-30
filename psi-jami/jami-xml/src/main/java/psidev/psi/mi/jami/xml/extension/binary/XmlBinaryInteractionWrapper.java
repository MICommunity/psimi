package psidev.psi.mi.jami.xml.extension.binary;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.impl.BinaryInteractionWrapper;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlEntry;
import psidev.psi.mi.jami.xml.extension.ExtendedPsi25Interaction;
import psidev.psi.mi.jami.xml.extension.InferredInteraction;
import psidev.psi.mi.jami.xml.extension.XmlBasicInteraction;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Wrapper for ModelledBinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlBinaryInteractionWrapper implements BinaryInteraction<Participant>, FileSourceContext, ExtendedPsi25Interaction<Participant>{
    private XmlBasicInteraction wrappedInteraction;
    private BinaryInteractionWrapper binaryWrapper;

    public XmlBinaryInteractionWrapper(XmlBasicInteraction interaction){
        this.wrappedInteraction = interaction;
        this.binaryWrapper = new BinaryInteractionWrapper(interaction);
    }

    public XmlBinaryInteractionWrapper(XmlBasicInteraction interaction, CvTerm complexExpansion){
        this(interaction);
        this.binaryWrapper = new BinaryInteractionWrapper(interaction, complexExpansion);
    }

    public Participant getParticipantA() {
        return this.binaryWrapper.getParticipantA();
    }

    public Participant getParticipantB() {
        return this.binaryWrapper.getParticipantB();
    }

    public void setParticipantA(Participant participantA) {
        this.binaryWrapper.setParticipantA(participantA);
    }

    public void setParticipantB(Participant participantB) {
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
    public Collection<Participant> getParticipants() {
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
    public boolean addParticipant(Participant part) {
        return this.binaryWrapper.addParticipant(part);
    }

    /**
     * Removes the Participant from this binary interaction
     * @param part
     * @return
     */
    @Override
    public boolean removeParticipant(Participant part) {
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
    public boolean addAllParticipants(Collection<? extends Participant> participants) {
        return this.binaryWrapper.addAllParticipants(participants);
    }

    @Override
    public boolean removeAllParticipants(Collection<? extends Participant> participants) {
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
    public List<Alias> getAliases() {
        return this.wrappedInteraction.getAliases();
    }

    @Override
    public List<CvTerm> getInteractionTypes() {
        return this.wrappedInteraction.getInteractionTypes();
    }

    @Override
    public XmlEntry getEntry() {
        return this.wrappedInteraction.getEntry();
    }

    @Override
    public void setEntry(XmlEntry entry) {
        this.wrappedInteraction.setEntry(entry);
    }

    @Override
    public List<InferredInteraction> getInferredInteractions() {
        return this.wrappedInteraction.getInferredInteractions();
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
    public boolean isIntraMolecular() {
        return this.wrappedInteraction.isIntraMolecular();
    }

    @Override
    public void setIntraMolecular(Boolean intra) {
        this.wrappedInteraction.setIntraMolecular(intra);
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
}
