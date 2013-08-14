package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.AbstractInteraction;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Abstract class for BinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>09/07/13</pre>
 */

public abstract class AbstractBinaryInteraction<T extends Participant> extends AbstractInteraction<T> implements BinaryInteraction<T> {
    private T participantA;
    private T participantB;
    private CvTerm complexExpansion;

    public AbstractBinaryInteraction(){
        super();
    }

    public AbstractBinaryInteraction(String shortName){
        super(shortName);
    }

    public AbstractBinaryInteraction(String shortName, CvTerm type){
        super(shortName, type);
    }

    public AbstractBinaryInteraction(T participantA, T participantB){
        super();
        this.participantA = participantA;
        this.participantB = participantB;
    }

    public AbstractBinaryInteraction(String shortName, T participantA, T participantB){
        super(shortName);
        this.participantA = participantA;
        this.participantB = participantB;
    }

    public AbstractBinaryInteraction(String shortName, CvTerm type, T participantA, T participantB){
        super(shortName, type);
        this.participantA = participantA;
        this.participantB = participantB;
    }

    public AbstractBinaryInteraction(CvTerm complexExpansion){
        super();
        this.complexExpansion = complexExpansion;
    }

    public AbstractBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion){
        super(shortName, type);
        this.complexExpansion = complexExpansion;
    }

    public AbstractBinaryInteraction(T participantA, T participantB, CvTerm complexExpansion){
        super();
        this.participantA = participantA;
        this.participantB = participantB;
        this.complexExpansion = complexExpansion;
    }

    public AbstractBinaryInteraction(String shortName, T participantA, T participantB, CvTerm complexExpansion){
        super(shortName);
        this.complexExpansion = complexExpansion;
        this.participantA = participantA;
        this.participantB = participantB;
    }

    public AbstractBinaryInteraction(String shortName, CvTerm type, T participantA, T participantB, CvTerm complexExpansion){
        super(shortName, type);
        this.participantA = participantA;
        this.participantB = participantB;
        this.complexExpansion = complexExpansion;
    }

    public T getParticipantA() {
        return participantA;
    }

    public T getParticipantB() {
        return participantB;
    }

    public void setParticipantA(T participantA) {
        this.participantA = participantA;
    }

    public void setParticipantB(T participantB) {
        this.participantB = participantB;
    }

    public CvTerm getComplexExpansion() {
        return this.complexExpansion;
    }

    public void setComplexExpansion(CvTerm expansion) {
        this.complexExpansion = expansion;
    }

    /**
     * The collection of participants for this binary interaction.
     * It cannot be changed.
     * @return
     */
    @Override
    public Collection<T> getParticipants() {
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
     * Adds a new Participant and set the Interaction of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param part
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants
     */
    @Override
    public boolean addParticipant(T part) {
        if (part == null){
            return false;
        }
        if (getParticipantB() != null && getParticipantA() != null){
            throw new IllegalStateException("A ModelledBinaryInteraction cannot have more than two participants.");
        }
        else if (getParticipantA() != null){
            part.setInteraction(this);
            setParticipantB(part);
            return true;
        }
        else{
            part.setInteraction(this);
            setParticipantA(part);
            return true;
        }
    }

    /**
     * Removes the Participant from this binary interaction
     * @param part
     * @return
     */
    @Override
    public boolean removeParticipant(T part) {
        if (part == null){
            return false;
        }

        if (getParticipantA() != null && getParticipantA().equals(part)){
            part.setInteraction(null);
            setParticipantA(null);
            return true;
        }
        else if (getParticipantB() != null && getParticipantB().equals(part)){
            part.setInteraction(null);
            setParticipantB(null);
            return true;
        }
        return false;
    }

    /**
     * Adds the participants and set the Interaction of this participant if added.
     * If the participant B and A are null, it will first set the participantA. If the participantA is set, it will set the ParticipantB
     * @param participants
     * @return
     * @throws IllegalArgumentException if this Binary interaction already contains two participants or the given participants contain more than two participants
     */
    @Override
    public boolean addAllParticipants(Collection<? extends T> participants) {
        if (participants == null){
            return false;
        }
        if (participants.size() > 2){
            throw new IllegalArgumentException("A ModelledBinaryInteraction cannot have more than two participants and we try to add " + participants.size() + " participants");
        }

        boolean added = false;
        for (T p : participants){
            if (addParticipant(p)){
                added = true;
            }
        }
        return added;
    }

    @Override
    public boolean removeAllParticipants(Collection<? extends T> participants) {
        if (participants == null){
            return false;
        }

        boolean removed = false;
        for (T p : participants){
            if (removeParticipant(p)){
                removed = true;
            }
        }
        return removed;
    }
}
