package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;

/**
 * Unambiguous biological participant comparator based on the interactor only.
 * It will compare the basic properties of a biological participant using UnambiguousParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class UnambiguousModelledParticipantInteractorComparator extends ModelledParticipantComparator {

    private static UnambiguousModelledParticipantInteractorComparator unambiguousParticipantInteractorComparator;

    /**
     * Creates a new UnambiguousModelledParticipantInteractorComparator. It will use a UnambiguousParticipantInteractorComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousModelledParticipantInteractorComparator() {
        super(new UnambiguousParticipantInteractorComparator());
    }

    @Override
    public UnambiguousParticipantInteractorComparator getParticipantComparator() {
        return (UnambiguousParticipantInteractorComparator) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a biological participant using UnambiguousParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(ModelledParticipant component1, ModelledParticipant component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousModelledParticipantInteractorComparator to know if two biological participants are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(ModelledParticipant component1, ModelledParticipant component2){
        if (unambiguousParticipantInteractorComparator == null){
            unambiguousParticipantInteractorComparator = new UnambiguousModelledParticipantInteractorComparator();
        }

        return unambiguousParticipantInteractorComparator.compare(component1, component2) == 0;
    }
}
