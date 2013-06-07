package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;

import java.util.*;

/**
 * Abstract class for BinaryInteractionWrapper
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class AbstractBinaryInteractionWrapper<T extends Participant> implements BinaryInteraction<T>{

    private Interaction interaction;
    private T participantA;
    private T participantB;
    private CvTerm complexExpansion;

    private boolean hasInitialisedA;
    private boolean hasInitialisedB;

    public AbstractBinaryInteractionWrapper(Interaction interaction){
        if (interaction == null){
            throw new IllegalArgumentException("The interaction of a AbstractBinaryInteractionWrapper cannot be null");
        }
        if (interaction.getParticipants().size() > 2){
            throw new IllegalArgumentException("The interaction of a AbstractBinaryInteractionWrapper cannot have more than two participants");
        }
        this.interaction = interaction;
    }

    public AbstractBinaryInteractionWrapper(Interaction interaction, CvTerm complexExpansion){
        this(interaction);
        this.complexExpansion = complexExpansion;
    }

    public T getParticipantA() {
        if (!hasInitialisedA){
            initialiseParticipantA();
        }

        return participantA;
    }

    protected void initialiseParticipantA() {
        hasInitialisedA = true;
    }

    public T getParticipantB() {
        if (!hasInitialisedB){
            initialiseParticipantB();
        }

        return participantB;
    }

    protected void initialiseParticipantB() {
        hasInitialisedB = true;
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

    public String getShortName() {
        return interaction.getShortName();
    }

    public void setShortName(String name) {
        interaction.setShortName(name);
    }

    public String getRigid() {
        return interaction.getRigid();
    }

    public void setRigid(String rigid) {
        interaction.setRigid(rigid);
    }

    public Collection<Xref> getIdentifiers() {
        return interaction.getIdentifiers();
    }

    public Collection<Xref> getXrefs() {
        return interaction.getXrefs();
    }

    public Collection<Checksum> getChecksums() {
        return interaction.getChecksums();
    }

    public Collection<Annotation> getAnnotations() {
        return interaction.getAnnotations();
    }

    public Date getUpdatedDate() {
        return interaction.getUpdatedDate();
    }

    public void setUpdatedDate(Date updated) {
        interaction.setUpdatedDate(updated);
    }

    public Date getCreatedDate() {
        return interaction.getCreatedDate();
    }

    public void setCreatedDate(Date created) {
        interaction.setCreatedDate(created);
    }

    public CvTerm getInteractionType() {
        return interaction.getInteractionType();
    }

    public void setInteractionType(CvTerm term) {
        interaction.setInteractionType(term);
    }

    /**
     * The collection of participants for this binary interaction.
     * It cannot be changed.
     * @return
     */
    public Collection<? extends Participant> getParticipants() {
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

    protected Interaction getInteraction() {
        return interaction;
    }
}
