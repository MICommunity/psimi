package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.BiologicalFeature;
import psidev.psi.mi.jami.model.BiologicalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultBiologicalFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Default biological participant comparator.
 * It will compare the basic properties of a biological participant using DefaultParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultBiologicalParticipantComparator extends BiologicalParticipantComparator {

    private static DefaultBiologicalParticipantComparator defaultBiologicalParticipantComparator;

    /**
     * Creates a new DefaultBiologicalParticipantComparator. It will use a DefaultParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public DefaultBiologicalParticipantComparator() {
        super(new ParticipantBaseComparator<BiologicalFeature>(new DefaultInteractorComparator(), new DefaultCvTermComparator(), new DefaultBiologicalFeatureComparator()));
    }

    @Override
    public ParticipantBaseComparator<BiologicalFeature> getParticipantComparator() {
        return (ParticipantBaseComparator<BiologicalFeature>) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a biological participant using DefaultParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(BiologicalParticipant participant1, BiologicalParticipant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use DefaultBiologicalParticipantComparator to know if two components are equals.
     * @param participant1
     * @param participant2
     * @return true if the two components are equal
     */
    public static boolean areEquals(BiologicalParticipant participant1, BiologicalParticipant participant2){
        if (defaultBiologicalParticipantComparator == null){
            defaultBiologicalParticipantComparator = new DefaultBiologicalParticipantComparator();
        }

        return defaultBiologicalParticipantComparator.compare(participant1, participant2) == 0;
    }
}
