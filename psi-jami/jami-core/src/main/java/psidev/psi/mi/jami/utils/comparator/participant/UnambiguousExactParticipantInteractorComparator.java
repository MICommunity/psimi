package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

/**
 * It will compare the interactors using UnambiguousExactInteractorComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousExactParticipantInteractorComparator extends ParticipantInteractorComparator<Participant> {
    private static UnambiguousExactParticipantInteractorComparator unambiguousExactInteractorParticipantComparator;

    /**
     * Creates a new UnambiguousExactParticipantInteractorComparator. It will use a UnambiguousExactInteractorBaseComparator to compare
     * interactors.
     */
    public UnambiguousExactParticipantInteractorComparator() {
        super(new UnambiguousExactInteractorComparator());
    }

    @Override
    public UnambiguousExactInteractorComparator getInteractorComparator() {
        return (UnambiguousExactInteractorComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will compare the interactors using UnambiguousInteractorComparator.
     *
     * This comparator will ignore all the other properties of a participant.
     */
    public int compare(Participant participant1, Participant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use UnambiguousExactParticipantInteractorComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (unambiguousExactInteractorParticipantComparator == null){
            unambiguousExactInteractorParticipantComparator = new UnambiguousExactParticipantInteractorComparator();
        }

        return unambiguousExactInteractorParticipantComparator.compare(participant1, participant2) == 0;
    }
}