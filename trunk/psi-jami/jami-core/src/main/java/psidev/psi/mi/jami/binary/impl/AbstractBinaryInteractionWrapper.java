package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.InteractionUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingProperties;

import java.util.*;

/**
 * Abstract class for binary interactions that wrap an Interaction.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class AbstractBinaryInteractionWrapper<I extends Interaction<T>, T extends Participant> implements BinaryInteraction<T>{

    private I wrappedInteraction;
    private T participantA;
    private T participantB;
    private CvTerm complexExpansion;

    private boolean hasInitialisedA;
    private boolean hasInitialisedB;

    private Collection<Annotation> annotations;

    /**
     *
     * @param interaction : the interaction to wrap
     */
    public AbstractBinaryInteractionWrapper(I interaction){
        if (interaction == null){
            throw new IllegalArgumentException("The wrappedInteraction of a AbstractBinaryInteractionWrapper cannot be null");
        }
        if (interaction.getParticipants().size() > 2){
            throw new IllegalArgumentException("The wrappedInteraction of a AbstractBinaryInteractionWrapper cannot have more than two participants");
        }
        this.wrappedInteraction = interaction;

        Annotation annot = InteractionUtils.collectComplexExpansionMethodFromAnnotations(interaction.getAnnotations());
        this.annotations = new AnnotationList();
        ((AnnotationList)this.annotations).addAllOnly(interaction.getAnnotations());

        if (annot != null){
            this.complexExpansion = annot.getTopic();
            ((AnnotationList)this.annotations).removeOnly(complexExpansion);
        }
    }

    /**
     *
     * @param interaction: the interaction to wrap
     * @param complexExpansion: the complex expansion method
     */
    public AbstractBinaryInteractionWrapper(I interaction, CvTerm complexExpansion){
        this(interaction);
        this.complexExpansion = complexExpansion;
    }

    public T getParticipantA() {
        if (!hasInitialisedA){
            hasInitialisedA = true;
            this.participantA = !wrappedInteraction.getParticipants().isEmpty() ? wrappedInteraction.getParticipants().iterator().next() : null;
        }

        return participantA;
    }

    public T getParticipantB() {
        if (!hasInitialisedB){
            hasInitialisedB = true;
            if (wrappedInteraction.getParticipants().size() >= 2){
                Iterator<T> participantIterator = wrappedInteraction.getParticipants().iterator();
                participantIterator.next();
                this.participantB = participantIterator.next();
            }
        }

        return participantB;
    }

    public void setParticipantA(T participantA) {
        getWrappedInteraction().getParticipants().remove(this.participantA);
        this.participantA = participantA;
        if (participantA != null){
            getWrappedInteraction().getParticipants().add(this.participantA);
        }
    }

    public void setParticipantB(T participantB) {
        getWrappedInteraction().getParticipants().remove(this.participantB);
        this.participantB = participantB;
        if (participantB != null){
            getWrappedInteraction().getParticipants().add(this.participantB);
        }
    }

    public CvTerm getComplexExpansion() {
        return this.complexExpansion;
    }

    public void setComplexExpansion(CvTerm expansion) {
        this.complexExpansion = expansion;
    }

    public String getShortName() {
        return wrappedInteraction.getShortName();
    }

    public void setShortName(String name) {
        wrappedInteraction.setShortName(name);
    }

    public String getRigid() {
        return wrappedInteraction.getRigid();
    }

    public void setRigid(String rigid) {
        wrappedInteraction.setRigid(rigid);
    }

    public Collection<Xref> getIdentifiers() {
        return wrappedInteraction.getIdentifiers();
    }

    public Collection<Xref> getXrefs() {
        return wrappedInteraction.getXrefs();
    }

    public Collection<Checksum> getChecksums() {
        return wrappedInteraction.getChecksums();
    }

    public Collection<Annotation> getAnnotations() {
        return this.annotations;
    }

    public Date getUpdatedDate() {
        return wrappedInteraction.getUpdatedDate();
    }

    public void setUpdatedDate(Date updated) {
        wrappedInteraction.setUpdatedDate(updated);
    }

    public Date getCreatedDate() {
        return wrappedInteraction.getCreatedDate();
    }

    public void setCreatedDate(Date created) {
        wrappedInteraction.setCreatedDate(created);
    }

    public CvTerm getInteractionType() {
        return wrappedInteraction.getInteractionType();
    }

    public void setInteractionType(CvTerm term) {
        wrappedInteraction.setInteractionType(term);
    }

    /**
     * The collection of participants for this binary interaction.
     * It cannot be changed.
     * @return The collection of participants
     */
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
     * @param part : participant to add
     * @return true if the participant has been added, false otherwise
     * @throws IllegalArgumentException if this Binary interaction already contains two participants
     */
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
     * @param part : participant to remove
     * @return true if the participant has been removed, false otherwise
     */
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
     * @param participants : participants to add
     * @return true if at least one participant has been added, false otherwise
     * @throws IllegalArgumentException if this Binary interaction already contains two participants or the given participants contain more than two participants
     */
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

    @Override
    public String toString() {
        return "Binary interaction: " +
                "participant A=["+(getParticipantA() != null ? getParticipantA().toString() : "") + "], participant B=[" +
                (getParticipantB() != null ? getParticipantB().toString() : "")+"], Complex expansion=["+
                (getComplexExpansion() != null ? getComplexExpansion().toString() : "")+"]";
    }

    protected I getWrappedInteraction() {
        return wrappedInteraction;
    }

    ////////////////////////////////////// Inner class
    private class AnnotationList extends AbstractListHavingProperties<Annotation>{

        private AnnotationList() {
        }

        @Override
        protected void processAddedObjectEvent(Annotation added) {
            wrappedInteraction.getAnnotations().add(added);
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {
            wrappedInteraction.getAnnotations().remove(removed);
        }

        @Override
        protected void clearProperties() {
            wrappedInteraction.getAnnotations().clear();
        }
    }
}
