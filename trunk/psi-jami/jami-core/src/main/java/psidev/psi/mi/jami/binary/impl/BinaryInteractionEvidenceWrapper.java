package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * A wrapper for InteractionEvidence which contains two participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class BinaryInteractionEvidenceWrapper extends AbstractBinaryInteractionWrapper<ParticipantEvidence> implements BinaryInteractionEvidence {

    private InteractionEvidence interactionEvidence;

    public BinaryInteractionEvidenceWrapper(InteractionEvidence interaction) {
        super(interaction);
    }

    public BinaryInteractionEvidenceWrapper(InteractionEvidence interaction, CvTerm complexExpansion) {
        super(interaction, complexExpansion);
    }

    @Override
    protected InteractionEvidence getInteraction() {
        if (interactionEvidence == null){
            interactionEvidence = (InteractionEvidence) super.getInteraction();
        }
        return interactionEvidence;
    }

    @Override
    protected void initialiseParticipantA() {
        super.initialiseParticipantA();
        super.setParticipantA(!getInteraction().getParticipants().isEmpty() ? getInteraction().getParticipants().iterator().next() : null);
    }

    @Override
    protected void initialiseParticipantB() {
        super.initialiseParticipantB();

        if (getInteraction().getParticipants().size() >= 2){
            Iterator<ParticipantEvidence> participantIterator = getInteraction().getParticipants().iterator();
            participantIterator.next();
            super.setParticipantB(participantIterator.next());
        }
    }

    @Override
    public void setParticipantA(ParticipantEvidence participantA) {
        getInteraction().getParticipants().remove(getParticipantA());
        super.setParticipantA(participantA);
        if (participantA != null){
            getInteraction().getParticipants().add(getParticipantA());
        }
    }

    @Override
    public void setParticipantB(ParticipantEvidence participantB) {
        getInteraction().getParticipants().remove(getParticipantB());
        super.setParticipantB(participantB);
        if (participantB != null){
            getInteraction().getParticipants().add(getParticipantB());
        }
    }

    public String getImexId() {
        return getInteraction().getImexId();
    }

    public void assignImexId(String identifier) {
        getInteraction().assignImexId(identifier);
    }

    public Experiment getExperiment() {
        return getInteraction().getExperiment();
    }

    public void setExperiment(Experiment experiment) {
        getInteraction().setExperiment(experiment);
    }

    public void setExperimentAndAddInteractionEvidence(Experiment experiment) {
        getInteraction().setExperimentAndAddInteractionEvidence(experiment);
    }

    public Collection<VariableParameterValueSet> getVariableParameterValues() {
        return getInteraction().getVariableParameterValues();
    }

    public String getAvailability() {
        return getInteraction().getAvailability();
    }

    public void setAvailability(String availability) {
        getInteraction().setAvailability(availability);
    }

    public Collection<Parameter> getParameters() {
        return getInteraction().getParameters();
    }

    public boolean isInferred() {
        return getInteraction().isInferred();
    }

    public void setInferred(boolean inferred) {
        getInteraction().setInferred(inferred);
    }

    public Collection<ParticipantEvidence> getParticipants() {
        if (getParticipantA() == null && getParticipantB() == null){
            return Collections.EMPTY_LIST;
        }
        else if (getParticipantB() == null){
            return Arrays.asList(getParticipantA());
        }
        else if (getParticipantA() == null){
            return Arrays.asList(getParticipantB());
        }
        else{
            return Arrays.asList(getParticipantA(), getParticipantB());
        }
    }

    /**
     * Adds a new ParticipantEvidence and set the interactionEvidence of this participant if added (use the wrapped interaction, not the binary).
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param part
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants
     */
    public boolean addParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }
        if (getParticipantB() != null && getParticipantA() != null){
            throw new IllegalArgumentException("A BinaryInteractionEvidence cannot have more than two participants.");
        }
        else if (getParticipantB() != null){
            part.setInteractionEvidence(interactionEvidence);
            setParticipantA(part);
            return true;
        }
        else{
            part.setInteractionEvidence(interactionEvidence);
            setParticipantA(part);
            return true;
        }
    }

    /**
     * Removes the participantEvidence from this binary interaction
     * @param part
     * @return
     */
    public boolean removeParticipantEvidence(ParticipantEvidence part) {
        if (part == null){
            return false;
        }

        if (getParticipantA() != null && getParticipantA().equals(part)){
            part.setInteractionEvidence(null);
            setParticipantA(null);
            return true;
        }
        else if (getParticipantB() != null && getParticipantB().equals(part)){
            part.setInteractionEvidence(null);
            setParticipantB(null);
            return true;
        }
        return false;
    }

    /**
     * Adds the participants and set the interactionEvidence of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param participants
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants or the given participants contain more than two participants
     */
    public boolean addAllParticipantEvidences(Collection<? extends ParticipantEvidence> participants) {
        if (participants == null){
            return false;
        }
        if (participants.size() > 2){
            throw new IllegalArgumentException("A BinaryInteractionEvidence cannot have more than two participants and we try to add " + participants.size() + " participants");
        }

        boolean added = false;
        for (ParticipantEvidence p : participants){
            if (addParticipantEvidence(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllParticipantEvidences(Collection<? extends ParticipantEvidence> participants) {
        if (participants == null){
            return false;
        }

        boolean removed = false;
        for (ParticipantEvidence p : participants){
            if (removeParticipantEvidence(p)){
                removed = true;
            }
        }
        return removed;
    }

    public Collection<Confidence> getConfidences() {
        return getInteraction().getConfidences();
    }

    public boolean isNegative() {
        return getInteraction().isNegative();
    }

    public void setNegative(boolean negative) {
        getInteraction().setNegative(negative);
    }
}
