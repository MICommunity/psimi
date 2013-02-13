package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;

/**
 * Unambiguous exact generic Participant comparator
 * Components come first and then experimental participants.
 * - It uses UnambiguousExactComponentComparator to compare components
 * - It uses UnambiguousExactParticipantEvidenceComparator to compare experimental participants
 * - It uses UnambiguousExactParticipantBaseComparator to compare basic participant properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class UnambiguousExactParticipantComparator extends ParticipantComparator {

    private static UnambiguousExactParticipantComparator unambiguousExactParticipantComparator;

    /**
     * Creates a UnambiguousExactParticipantComparator. It will use a UnambiguousExactParticipantBaseComparator to compare basic feature properties
     */
    public UnambiguousExactParticipantComparator() {
        super(new UnambiguousExactParticipantBaseComparator(), new UnambiguousExactParticipantEvidenceComparator());
    }

    @Override
    public UnambiguousExactParticipantBaseComparator getParticipantBaseComparator() {
        return (UnambiguousExactParticipantBaseComparator) this.participantBaseComparator;
    }

    @Override
    public UnambiguousExactParticipantEvidenceComparator getExperimentalParticipantComparator() {
        return (UnambiguousExactParticipantEvidenceComparator) this.experimentalParticipantComparator;
    }

    @Override
    /**
     * Components come first and then experimental participants.
     * - It uses UnambiguousExactComponentComparator to compare components
     * - It uses UnambiguousExactParticipantEvidenceComparator to compare experimental participants
     * - It uses UnambiguousExactParticipantBaseComparator to compare basic participant properties
     *
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
