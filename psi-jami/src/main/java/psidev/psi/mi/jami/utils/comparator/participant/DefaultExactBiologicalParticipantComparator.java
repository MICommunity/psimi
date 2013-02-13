package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.BiologicalFeature;
import psidev.psi.mi.jami.model.BiologicalParticipant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultBiologicalFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Default exact biological participant comparator.
 * It will compare the basic properties of a biological participant using DefaultExactParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultExactBiologicalParticipantComparator extends BiologicalParticipantComparator {

    private static DefaultExactBiologicalParticipantComparator defaultExactBiologicalParticipantComparator;

    /**
     * Creates a new DefaultExactBiologicalParticipantComparator. It will use a DefaultExactParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public DefaultExactBiologicalParticipantComparator() {
        super(new ParticipantBaseComparator<BiologicalFeature>(new DefaultInteractorComparator(), new DefaultCvTermComparator(), new DefaultBiologicalFeatureComparator()));
    }

    @Override
    public ParticipantBaseComparator<BiologicalFeature> getParticipantComparator() {
        return (ParticipantBaseComparator<BiologicalFeature>) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a biological participant using DefaultExactParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(BiologicalParticipant component1, BiologicalParticipant component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use DefaultExactBiologicalParticipantComparator to know if two biological participants are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(BiologicalParticipant component1, BiologicalParticipant component2){
        if (defaultExactBiologicalParticipantComparator == null){
            defaultExactBiologicalParticipantComparator = new DefaultExactBiologicalParticipantComparator();
        }

        return defaultExactBiologicalParticipantComparator.compare(component1, component2) == 0;
    }
}
