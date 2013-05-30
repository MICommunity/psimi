package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultModelledFeatureComparator;

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

public class DefaultModelledParticipantComparator extends ModelledParticipantComparator {

    private static DefaultModelledParticipantComparator defaultBiologicalParticipantComparator;

    /**
     * Creates a new DefaultModelledParticipantComparator. It will use a DefaultParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public DefaultModelledParticipantComparator() {
        super(new DefaultModelledFeatureComparator());
        setParticipantBaseComparator(new DefaultParticipantBaseComparator(this));
    }

    @Override
    public DefaultParticipantBaseComparator getParticipantBaseComparator() {
        return (DefaultParticipantBaseComparator) this.participantBaseComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a biological participant using DefaultParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(ModelledParticipant participant1, ModelledParticipant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use DefaultModelledParticipantComparator to know if two components are equals.
     * @param participant1
     * @param participant2
     * @return true if the two components are equal
     */
    public static boolean areEquals(ModelledParticipant participant1, ModelledParticipant participant2){
        if (defaultBiologicalParticipantComparator == null){
            defaultBiologicalParticipantComparator = new DefaultModelledParticipantComparator();
        }

        return defaultBiologicalParticipantComparator.compare(participant1, participant2) == 0;
    }
}
