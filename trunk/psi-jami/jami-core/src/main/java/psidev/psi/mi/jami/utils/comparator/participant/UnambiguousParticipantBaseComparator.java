package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureBaseComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousInteractorBaseComparator;

/**
 * Unambiguous participant comparator
 * It will first compare the interactors using UnambiguousInteractorComparator. If both interactors are the same,
 * it will compare the biological roles using UnambiguousCvTermComparator. If both biological roles are the same, it
 * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
 * it will compare the features using a UnambiguousFeatureBaseComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class UnambiguousParticipantBaseComparator extends ParticipantBaseComparator {
    private static UnambiguousParticipantBaseComparator unambiguousParticipantComparator;

    /**
     * Creates a new UnambiguousParticipantBaseComparator. It will use a UnambiguousInteractorComparator to compare
     * interactors, a UnambiguousCvTermComparator to compare biological roles, a UnambiguousFeatureBaseComparator to
     * compare features.
     */
    public UnambiguousParticipantBaseComparator() {
        super(new UnambiguousInteractorBaseComparator(), new UnambiguousCvTermComparator(), new UnambiguousFeatureBaseComparator());
    }

    @Override
    public UnambiguousInteractorBaseComparator getInteractorComparator() {
        return (UnambiguousInteractorBaseComparator) this.interactorComparator;
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare the interactors using UnambiguousInteractorComparator. If both interactors are the same,
     * it will compare the biological roles using UnambiguousCvTermComparator. If both biological roles are the same, it
     * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
     * it will compare the features using a UnambiguousFeatureBaseComparator.
     *
     * This comparator will ignore all the other properties of a participant.
     */
    public int compare(Participant participant1, Participant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use UnambiguousParticipantBaseComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (unambiguousParticipantComparator == null){
            unambiguousParticipantComparator = new UnambiguousParticipantBaseComparator();
        }

        return unambiguousParticipantComparator.compare(participant1, participant2) == 0;
    }

    /**
     *
     * @param participant
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Participant participant){
        if (unambiguousParticipantComparator == null){
            unambiguousParticipantComparator = new UnambiguousParticipantBaseComparator();
        }

        if (participant == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + UnambiguousInteractorBaseComparator.hashCode(participant.getInteractor());
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(participant.getBiologicalRole());
        hashcode = 31*hashcode + StoichiometryComparator.hashCode(participant.getStoichiometry());

        return hashcode;
    }
}
