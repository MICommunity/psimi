package psidev.psi.mi.jami.binary.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.util.Collection;
import java.util.Iterator;

/**
 * Wrapper for an Interaction which is binary
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class BinaryInteractionWrapper extends AbstractBinaryInteractionWrapper<Participant>{

    public BinaryInteractionWrapper(Interaction interaction){
        super(interaction);
    }

    public BinaryInteractionWrapper(Interaction interaction, CvTerm complexExpansion){
        super(interaction, complexExpansion);
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
            Iterator<? extends Participant> participantIterator = getInteraction().getParticipants().iterator();
            participantIterator.next();
            super.setParticipantB(participantIterator.next());
        }
    }

    @Override
    public void setParticipantA(Participant participantA) {
        getInteraction().getParticipants().remove(getParticipantA());
        super.setParticipantA(participantA);
        if (participantA != null){
            ((Collection<Participant>) getInteraction().getParticipants()).add(getParticipantA());
        }
    }

    @Override
    public void setParticipantB(Participant participantB) {
        getInteraction().getParticipants().remove(getParticipantB());
        super.setParticipantB(participantB);
        if (participantB != null){
            ((Collection<Participant>) getInteraction().getParticipants()).add(getParticipantB());
        }
    }
}
