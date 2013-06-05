package psidev.psi.mi.jami.binary;

import psidev.psi.mi.jami.model.CvTerm;
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

    /**
     * The complex expansion method if this binary interaction is expanded from a complex or n-ary interaction.
     * This is a controlled vocabulary term and can be null if the binary interaction has not been expanded.
     * @return the complex expansion method
     */
    public CvTerm getComplexExpansion();

    /**
     * Sets the complex expansion of this binary interaction
     * @param expansion : the complex expansion
     */
    public void setComplexExpansion(CvTerm expansion);
}
