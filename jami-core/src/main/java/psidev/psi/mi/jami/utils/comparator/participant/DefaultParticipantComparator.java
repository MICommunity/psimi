package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;

/**
 * Generic default participant comparator.
 * Components come first and then experimental participants.
 * - It uses DefaultComponentComparator to compare components
 * - It uses DefaultParticipantEvidenceComparator to compare experimental participants
 * - It uses DefaultParticipantBaseComparator to compare basic participant properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultParticipantComparator extends ParticipantComparator {

    private static DefaultParticipantComparator defaultParticipantComparator;

    /**
     * Creates a DefaultParticipantComparator. It will use a DefaultParticipantBaseComparator to compare basic feature properties
     */
    public DefaultParticipantComparator() {
        super(new DefaultParticipantBaseComparator(), new DefaultParticipantEvidenceComparator());
    }

    @Override
    public DefaultParticipantBaseComparator getParticipantBaseComparator() {
        return (DefaultParticipantBaseComparator) this.participantBaseComparator;
    }

    @Override
    public DefaultParticipantEvidenceComparator getExperimentalParticipantComparator() {
        return (DefaultParticipantEvidenceComparator) this.experimentalParticipantComparator;
    }

    @Override
    /**
     * Components come first and then experimental participants.
     * - It uses DefaultComponentComparator to compare components
     * - It uses DefaultParticipantEvidenceComparator to compare experimental participants
     * - It uses DefaultParticipantBaseComparator to compare basic participant properties
     *
     *
     */
    public int compare(Participant participant1, Participant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use DefaultParticipantComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (defaultParticipantComparator == null){
            defaultParticipantComparator = new DefaultParticipantComparator();
        }

        return defaultParticipantComparator.compare(participant1, participant2) == 0;
    }
}
