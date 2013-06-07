package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * A wrapper for ModelledInteraction which contains two participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class ModelledBinaryInteractionWrapper extends AbstractBinaryInteractionWrapper<ModelledParticipant> implements ModelledBinaryInteraction{

    private ModelledInteraction modelledInteraction;

    public ModelledBinaryInteractionWrapper(ModelledInteraction interaction) {
        super(interaction);
    }

    public ModelledBinaryInteractionWrapper(ModelledInteraction interaction, CvTerm complexExpansion) {
        super(interaction, complexExpansion);
    }

    @Override
    protected ModelledInteraction getInteraction() {
        if (modelledInteraction == null){
            modelledInteraction = (ModelledInteraction) super.getInteraction();
        }
        return modelledInteraction;
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
            Iterator<ModelledParticipant> participantIterator = getInteraction().getParticipants().iterator();
            participantIterator.next();
            super.setParticipantB(participantIterator.next());
        }
    }

    @Override
    public void setParticipantA(ModelledParticipant participantA) {
        getInteraction().getParticipants().remove(getParticipantA());
        super.setParticipantA(participantA);
        if (participantA != null){
            getInteraction().getParticipants().add(getParticipantA());
        }
    }

    @Override
    public void setParticipantB(ModelledParticipant participantB) {
        getInteraction().getParticipants().remove(getParticipantB());
        super.setParticipantB(participantB);
        if (participantB != null){
            getInteraction().getParticipants().add(getParticipantB());
        }
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        return getInteraction().getInteractionEvidences();
    }

    public Source getSource() {
        return getInteraction().getSource();
    }

    public void setSource(Source source) {
        getInteraction().setSource(source);
    }

    public Collection<ModelledParticipant> getParticipants() {
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
     * Adds a new ModelledParticipant and set the modelledInteraction of this participant if added (use the wrapped interaction, not the binary).
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param part
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants
     */
    public boolean addModelledParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }
        if (getParticipantB() != null && getParticipantA() != null){
            throw new IllegalArgumentException("A ModelledBinaryInteraction cannot have more than two participants.");
        }
        else if (getParticipantB() != null){
            part.setModelledInteraction(modelledInteraction);
            setParticipantA(part);
            return true;
        }
        else{
            part.setModelledInteraction(modelledInteraction);
            setParticipantA(part);
            return true;
        }
    }

    /**
     * Removes the modelledParticipant from this binary interaction
     * @param part
     * @return
     */
    public boolean removeModelledParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }

        if (getParticipantA() != null && getParticipantA().equals(part)){
            part.setModelledInteraction(null);
            setParticipantA(null);
            return true;
        }
        else if (getParticipantB() != null && getParticipantB().equals(part)){
            part.setModelledInteraction(null);
            setParticipantB(null);
            return true;
        }
        return false;
    }

    /**
     * Adds the participants and set the modelledInteraction of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param participants
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants or the given participants contain more than two participants
     */
    public boolean addAllModelledParticipants(Collection<? extends ModelledParticipant> participants) {
        if (participants == null){
            return false;
        }
        if (participants.size() > 2){
            throw new IllegalArgumentException("A ModelledBinaryInteraction cannot have more than two participants and we try to add " + participants.size() + " participants");
        }

        boolean added = false;
        for (ModelledParticipant p : participants){
            if (addModelledParticipant(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllModelledParticipants(Collection<? extends ModelledParticipant> participants) {
        if (participants == null){
            return false;
        }

        boolean removed = false;
        for (ModelledParticipant p : participants){
            if (removeModelledParticipant(p)){
                removed = true;
            }
        }
        return removed;
    }

    public Collection<ModelledConfidence> getModelledConfidences() {
        return getInteraction().getModelledConfidences();
    }

    public Collection<ModelledParameter> getModelledParameters() {
        return getInteraction().getModelledParameters();
    }

    public Collection<CooperativeEffect> getCooperativeEffects() {
        return getInteraction().getCooperativeEffects();
    }
}
