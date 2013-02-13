package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.BiologicalParticipant;

/**
 * Default biological participant comparator based on the interactor only.
 * It will compare the basic properties of a biological participant using DefaultParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultBiologicalParticipantInteractorComparator extends BiologicalParticipantComparator {

    private static DefaultBiologicalParticipantInteractorComparator defaultBiologicalParticipantInteractorComparator;

    /**
     * Creates a new DefaultComponentInteractorComparator. It will use a DefaultParticipantInteractorComparator to compare
     * the basic properties of a participant.
     */
    public DefaultBiologicalParticipantInteractorComparator() {
        super(new DefaultParticipantInteractorComparator());
    }

    @Override
    public DefaultParticipantInteractorComparator getParticipantComparator() {
        return (DefaultParticipantInteractorComparator) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a biological participant using DefaultParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(BiologicalParticipant component1, BiologicalParticipant component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use DefaultBiologicalParticipantInteractorComparator to know if two biological participants are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(BiologicalParticipant component1, BiologicalParticipant component2){
        if (defaultBiologicalParticipantInteractorComparator == null){
            defaultBiologicalParticipantInteractorComparator = new DefaultBiologicalParticipantInteractorComparator();
        }

        return defaultBiologicalParticipantInteractorComparator.compare(component1, component2) == 0;
    }
}
