package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorBaseComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

/**
 * Unambiguous exact entity comparator
 * It will first compare the interactors using UnambiguousExactInteractorComparator. If both interactors are the same,
 * it will look at the stoichiometry (participant with lower stoichiometry will come first).
 *
 * This comparator will ignore all the other properties of a participant.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class UnambiguousExactEntityBaseComparator extends EntityBaseComparator {

    private static UnambiguousExactEntityBaseComparator unambiguousExactParticipantComparator;

    /**
     * Creates a new UnambiguousExactParticipantBaseComparator. It will use a UnambiguousExactInteractorComparator to compare
     * interactors, a UnambiguousCvTermComparator to compare biological roles.
     */
    public UnambiguousExactEntityBaseComparator() {
        super(new UnambiguousExactInteractorComparator());
    }

    public UnambiguousExactEntityBaseComparator(UnambiguousExactInteractorComparator comparator) {
        super(comparator != null ? comparator : new UnambiguousExactInteractorComparator());
    }


    @Override
    public UnambiguousExactInteractorComparator getInteractorComparator() {
        return (UnambiguousExactInteractorComparator) super.getInteractorComparator();
    }

    @Override
    /**
     * It will first compare the interactors using UnambiguousExactInteractorComparator. If both interactors are the same,
     * it will look at the stoichiometry (participant with lower stoichiometry will come first).
     */
    public int compare(Entity participant1, Entity participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use UnambiguousExactParticipantBaseComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Entity participant1, Entity participant2){
        if (unambiguousExactParticipantComparator == null){
            unambiguousExactParticipantComparator = new UnambiguousExactEntityBaseComparator();
        }

        return unambiguousExactParticipantComparator.compare(participant1, participant2) == 0;
    }

    /**
     *
     * @param participant
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Entity participant){
        if (unambiguousExactParticipantComparator == null){
            unambiguousExactParticipantComparator = new UnambiguousExactEntityBaseComparator();
        }

        if (participant == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + UnambiguousExactInteractorBaseComparator.hashCode(participant.getInteractor());
        hashcode = 31*hashcode + StoichiometryComparator.hashCode(participant.getStoichiometry());

        return hashcode;
    }
}
