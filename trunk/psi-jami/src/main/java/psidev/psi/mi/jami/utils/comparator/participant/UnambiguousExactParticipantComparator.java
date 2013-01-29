package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

/**
 * Unambiguous exact participant comparator
 * It will first compare the interactors using UnambiguousExactInteractorComparator. If both interactors are the same,
 * it will compare the biological roles using UnambiguousCvTermComparator. If both biological roles are the same, it
 * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
 * it will compare the features using a UnambiguousFeatureComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class UnambiguousExactParticipantComparator extends ParticipantComparator {

    private static UnambiguousExactParticipantComparator unambiguousExactParticipantComparator;

    /**
     * Creates a new UnambiguousExactParticipantComparator. It will use a UnambiguousExactInteractorComparator to compare
     * interactors, a UnambiguousCvTermComparator to compare biological roles, a UnambiguousFeatureComparator to
     * compare features.
     */
    public UnambiguousExactParticipantComparator() {
        super(new UnambiguousExactInteractorComparator(), new UnambiguousCvTermComparator(), new UnambiguousFeatureComparator());
    }

    @Override
    public UnambiguousExactInteractorComparator getInteractorComparator() {
        return (UnambiguousExactInteractorComparator) this.interactorComparator;
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare the interactors using UnambiguousExactInteractorComparator. If both interactors are the same,
     * it will compare the biological roles using UnambiguousCvTermComparator. If both biological roles are the same, it
     * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
     * it will compare the features using a UnambiguousFeatureComparator.
     * This comparator will ignore all the other properties of a participant.
     */
    public int compare(Participant participant1, Participant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use UnambiguousExactParticipantComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (unambiguousExactParticipantComparator == null){
            unambiguousExactParticipantComparator = new UnambiguousExactParticipantComparator();
        }

        return unambiguousExactParticipantComparator.compare(participant1, participant2) == 0;
    }
}
