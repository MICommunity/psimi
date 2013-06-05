package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultInteraction;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for BinaryInteraction
 *
 * Note: the methods equals and hashcode have not been overriden. Use the same comparators as for DefaultInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class DefaultBinaryInteraction<T extends Participant> extends DefaultInteraction implements BinaryInteraction<T> {

    private T participantA;
    private T participantB;
    private CvTerm complexExpansion;

    public DefaultBinaryInteraction(){
        super();
    }

    public DefaultBinaryInteraction(String shortName){
        super(shortName);
    }

    public DefaultBinaryInteraction(String shortName, CvTerm type){
        super(shortName, type);
    }

    public DefaultBinaryInteraction(T participantA, T participantB){
        super();
        this.participantA = participantA;
        this.participantB = participantB;
    }

    public DefaultBinaryInteraction(String shortName, T participantA, T participantB){
        super(shortName);
    }

    public DefaultBinaryInteraction(String shortName, CvTerm type, T participantA, T participantB){
        super(shortName, type);
        this.participantA = participantA;
        this.participantB = participantB;
    }

    public DefaultBinaryInteraction(CvTerm complexExpansion){
        super();
        this.complexExpansion = complexExpansion;
    }

    public DefaultBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion){
        super(shortName, type);
        this.complexExpansion = complexExpansion;
    }

    public DefaultBinaryInteraction(T participantA, T participantB, CvTerm complexExpansion){
        super();
        this.participantA = participantA;
        this.participantB = participantB;
        this.complexExpansion = complexExpansion;
    }

    public DefaultBinaryInteraction(String shortName, T participantA, T participantB, CvTerm complexExpansion){
        super(shortName);
        this.complexExpansion = complexExpansion;
    }

    public DefaultBinaryInteraction(String shortName, CvTerm type, T participantA, T participantB, CvTerm complexExpansion){
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
}
