package psidev.psi.mi.jami.binary;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

/**
 * A Binary interaction is an interaction only composed of two participants
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public interface BinaryInteraction<T extends Participant> extends Interaction {

    /**
     * The first particiipant of the binary interaction.
     * @return
     */
    public T getParticipantA();

    /**
     * The second participant of the binary interaction
     * @return
     */
    public T getParticipantB();

    /**
     * Sets the first participant of this interaction
     * @param participantA
     */
    public void setParticipantA(T participantA);

    /**
     * Sets the second participant of this interaction
     * @param participantB
     */
    public void setParticipantB(T participantB);
}
