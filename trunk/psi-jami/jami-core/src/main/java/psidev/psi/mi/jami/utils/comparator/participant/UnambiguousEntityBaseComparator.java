package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousInteractorBaseComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousInteractorComparator;

/**
 * Unambiguous entity comparator
 * It will first compare the interactors using UnambiguousInteractorComparator. If both interactors are the same,
 * it will look at the stoichiometry (participant with lower stoichiometry will come first).
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class UnambiguousEntityBaseComparator extends EntityBaseComparator {
    private static UnambiguousEntityBaseComparator unambiguousParticipantComparator;

    /**
     * Creates a new UnambiguousEntityBaseComparator. It will use a UnambiguousInteractorComparator to compare
     * interactors
     */
    public UnambiguousEntityBaseComparator() {
        super(new UnambiguousInteractorComparator());
    }

    public UnambiguousEntityBaseComparator(UnambiguousInteractorComparator comparator) {
        super(comparator != null ? comparator : new UnambiguousInteractorComparator());
    }

    @Override
    public UnambiguousInteractorComparator getInteractorComparator() {
        return (UnambiguousInteractorComparator) super.getInteractorComparator();
    }

    @Override
    /**
     * It will first compare the interactors using UnambiguousInteractorComparator. If both interactors are the same,
     * it will look at the stoichiometry (participant with lower stoichiometry will come first).
     *
     * This comparator will ignore all the other properties of a participant.
     */
    public int compare(Entity participant1, Entity participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use UnambiguousParticipantBaseComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Entity participant1, Entity participant2){
        if (unambiguousParticipantComparator == null){
            unambiguousParticipantComparator = new UnambiguousEntityBaseComparator();
        }

        return unambiguousParticipantComparator.compare(participant1, participant2) == 0;
    }

    /**
     *
     * @param participant
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Entity participant){
        if (unambiguousParticipantComparator == null){
            unambiguousParticipantComparator = new UnambiguousEntityBaseComparator();
        }

        if (participant == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + UnambiguousInteractorBaseComparator.hashCode(participant.getInteractor());
        hashcode = 31*hashcode + StoichiometryComparator.hashCode(participant.getStoichiometry());

        return hashcode;
    }
}
