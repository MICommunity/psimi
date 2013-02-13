package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.BiologicalFeature;
import psidev.psi.mi.jami.model.BiologicalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultBiologicalFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorComparator;

/**
 * unambiguous exact biological participant comparator.
 * It will compare the basic properties of a biological participant using UnambiguousExactParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class UnambiguousExactBiologicalParticipantComparator extends BiologicalParticipantComparator {

    private static UnambiguousExactBiologicalParticipantComparator defaultParticipantComparator;

    /**
     * Creates a new UnambiguousExactBiologicalParticipantComparator. It will use a UnambiguousExactParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousExactBiologicalParticipantComparator() {
        super(new ParticipantBaseComparator<BiologicalFeature>(new DefaultExactInteractorComparator(), new DefaultCvTermComparator(), new DefaultBiologicalFeatureComparator()));
    }

    @Override
    public ParticipantBaseComparator<BiologicalFeature> getParticipantComparator() {
        return (ParticipantBaseComparator<BiologicalFeature>) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a component using UnambiguousExactParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(BiologicalParticipant component1, BiologicalParticipant component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousExactBiologicalParticipantComparator to know if two biological participant are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participant are equal
     */
    public static boolean areEquals(BiologicalParticipant component1, BiologicalParticipant component2){
        if (defaultParticipantComparator == null){
            defaultParticipantComparator = new UnambiguousExactBiologicalParticipantComparator();
        }

        return defaultParticipantComparator.compare(component1, component2) == 0;
    }
}
