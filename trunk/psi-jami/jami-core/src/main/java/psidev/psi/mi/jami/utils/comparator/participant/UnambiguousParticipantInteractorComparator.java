package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousComplexComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousInteractorComparator;

/**
 * It will compare the interactors using UnambiguousInteractorComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class UnambiguousParticipantInteractorComparator extends ParticipantInteractorComparator<Participant> {
    private static UnambiguousParticipantInteractorComparator unambiguousInteractorParticipantComparator;

    /**
     * Creates a new UnambiguousParticipantInteractorComparator. It will use a UnambiguousInteractorBaseComparator to compare
     * interactors.
     */
    public UnambiguousParticipantInteractorComparator() {
        super(new UnambiguousInteractorComparator(new UnambiguousComplexComparator(new UnambiguousModelledParticipantInteractorComparator())));
    }

    @Override
    public UnambiguousInteractorComparator getInteractorComparator() {
        return (UnambiguousInteractorComparator) this.interactorComparator;
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
     * Use UnambiguousParticipantInteractorComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (unambiguousInteractorParticipantComparator == null){
            unambiguousInteractorParticipantComparator = new UnambiguousParticipantInteractorComparator();
        }

        return unambiguousInteractorParticipantComparator.compare(participant1, participant2) == 0;
    }
}
