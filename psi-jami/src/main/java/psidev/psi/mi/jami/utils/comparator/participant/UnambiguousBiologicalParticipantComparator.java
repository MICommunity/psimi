package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.BiologicalFeature;
import psidev.psi.mi.jami.model.BiologicalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousBiologicalFeaturecomparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousInteractorComparator;

/**
 * Unambiguous biological participant comparator.
 * It will compare the basic properties of a biological participant using UnambiguousParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class UnambiguousBiologicalParticipantComparator extends BiologicalParticipantComparator {

    private static UnambiguousBiologicalParticipantComparator unambiguousParticipantComparator;

    /**
     * Creates a new UnambiguousBiologicalParticipantComparator. It will use a UnambiguousParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousBiologicalParticipantComparator() {
        super(new ParticipantBaseComparator<BiologicalFeature>(new UnambiguousInteractorComparator(), new UnambiguousCvTermComparator(), new UnambiguousBiologicalFeaturecomparator()));
    }

    @Override
    public ParticipantBaseComparator<BiologicalFeature> getParticipantComparator() {
        return (ParticipantBaseComparator<BiologicalFeature>) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a biological participant using UnambiguousParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(BiologicalParticipant component1, BiologicalParticipant component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousBiologicalParticipantComparator to know if two biological participants are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(BiologicalParticipant component1, BiologicalParticipant component2){
        if (unambiguousParticipantComparator == null){
            unambiguousParticipantComparator = new UnambiguousBiologicalParticipantComparator();
        }

        return unambiguousParticipantComparator.compare(component1, component2) == 0;
    }
}
