package psidev.psi.mi.jami.xml.extension.binary;

import org.xml.sax.Locator;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.BinaryInteractionEvidenceWrapper;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.XmlEntry;
import psidev.psi.mi.jami.xml.extension.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Xml implementation of BinaryInteractionWrapper with a source locator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlBinaryInteractionEvidenceWrapper extends XmlInteractionEvidence implements BinaryInteractionEvidence{
    private XmlInteractionEvidence wrappedInteraction;
    private BinaryInteractionEvidenceWrapper binaryWrapper;

    public XmlBinaryInteractionEvidenceWrapper(XmlInteractionEvidence interaction){
        this.wrappedInteraction = interaction;
        this.binaryWrapper = new BinaryInteractionEvidenceWrapper(interaction);
    }

    public XmlBinaryInteractionEvidenceWrapper(XmlInteractionEvidence interaction, CvTerm complexExpansion){
        this(interaction);
        this.binaryWrapper = new BinaryInteractionEvidenceWrapper(interaction, complexExpansion);
    }

    public ParticipantEvidence getParticipantA() {
        return this.binaryWrapper.getParticipantA();
    }

    public ParticipantEvidence getParticipantB() {
        return this.binaryWrapper.getParticipantB();
    }

    public void setParticipantA(ParticipantEvidence participantA) {
        this.binaryWrapper.setParticipantA(participantA);
    }

    public void setParticipantB(ParticipantEvidence participantB) {
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
    public Collection<ParticipantEvidence> getParticipants() {
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
    public boolean addParticipant(ParticipantEvidence part) {
        return this.binaryWrapper.addParticipant(part);
    }

    /**
     * Removes the Participant from this binary interaction
     * @param part
     * @return
     */
    @Override
    public boolean removeParticipant(ParticipantEvidence part) {
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
    public boolean addAllParticipants(Collection<? extends ParticipantEvidence> participants) {
        return this.binaryWrapper.addAllParticipants(participants);
    }

    @Override
    public boolean removeAllParticipants(Collection<? extends ParticipantEvidence> participants) {
        return this.binaryWrapper.removeAllParticipants(participants);
    }

    @Override
    public int getJAXBId() {
        return this.wrappedInteraction.getJAXBId();
    }

    @Override
    public List<CvTerm> getJAXBInteractionTypes() {
        return this.wrappedInteraction.getJAXBInteractionTypes();
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

    @Override
    public void setJAXBNames(NamesContainer value) {
        this.wrappedInteraction.setJAXBNames(value);
    }

    @Override
    public void setJAXBXref(InteractionXrefContainer value) {
        super.setJAXBXref(value);
    }

    @Override
    public void setJAXBIntraMolecular(Boolean value) {
        this.wrappedInteraction.setJAXBIntraMolecular(value);
    }

    @Override
    public void setJAXBId(int value) {
        this.wrappedInteraction.setJAXBIdOnly(value);
    }
    @Override
    public void setJAXBIdOnly(int value) {
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

    @Override
    public XmlEntry getEntry() {
        return this.wrappedInteraction.getEntry();
    }

    @Override
    public void setEntry(XmlEntry entry) {
        this.wrappedInteraction.setEntry(entry);
    }

    @Override
    public String toString() {
        return this.wrappedInteraction.toString();
    }

    @Override
    public String getImexId() {
        return this.wrappedInteraction.getImexId();
    }

    @Override
    public void assignImexId(String identifier) {
        this.wrappedInteraction.assignImexId(identifier);
    }

    @Override
    public Experiment getExperiment() {
        return this.wrappedInteraction.getExperiment();
    }

    @Override
    public void setExperiment(Experiment experiment) {
        this.wrappedInteraction.setExperiment(experiment);
    }

    @Override
    public List<Experiment> getExperiments() {
        return this.wrappedInteraction.getExperiments();
    }

    @Override
    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        this.wrappedInteraction.setExperimentAndAddInteractionEvidence(experiment);
    }

    @Override
    public Collection<VariableParameterValueSet> getVariableParameterValues() {
        return this.wrappedInteraction.getVariableParameterValues();
    }

    @Override
    public Collection<Confidence> getConfidences() {
        return this.wrappedInteraction.getConfidences();
    }

    @Override
    public String getAvailability() {
        return this.wrappedInteraction.getAvailability();
    }

    @Override
    public void setAvailability(String availability) {
        this.wrappedInteraction.setAvailability(availability);
    }

    @Override
    public boolean isNegative() {
        return this.wrappedInteraction.isNegative();
    }

    @Override
    public void setNegative(boolean negative) {
        this.wrappedInteraction.setNegative(negative);
    }

    @Override
    public Collection<Parameter> getParameters() {
        return this.wrappedInteraction.getParameters();
    }

    @Override
    public boolean isInferred() {
        return this.wrappedInteraction.isInferred();
    }

    @Override
    public void setInferred(boolean inferred) {
        this.wrappedInteraction.setInferred(inferred);
    }

    @Override
    public void setJAXBAvailability(Availability value) {
        this.wrappedInteraction.setJAXBAvailability(value);
    }

    @Override
    public void setJAXBAvailabilityRef(Integer value) {
        this.wrappedInteraction.setJAXBAvailabilityRef(value);
    }

    @Override
    public void setJAXBExperimentWrapper(JAXBExperimentWrapper value) {
        this.wrappedInteraction.setJAXBExperimentWrapper(value);
    }

    @Override
    public void setModelled(Boolean value) {
        this.wrappedInteraction.setModelled(value);
    }

    @Override
    public void setJAXBNegative(Boolean value) {
        this.wrappedInteraction.setJAXBNegative(value);
    }
}
