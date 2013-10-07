package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Entity;

/**
 * Unambiguous exact generic Participant comparator
 * Modelled participants come first and then experimental participants.
 * - It uses UnambiguousExactEntitySetComparator to compare participant sets
 * - It uses UnambiguousExactModelledParticipantComparator to compare components
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
        super(new UnambiguousExactParticipantBaseComparator(), new UnambiguousExactParticipantEvidenceComparator(),
                new UnambiguousExactModelledParticipantComparator());
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
    public UnambiguousExactModelledParticipantComparator getBiologicalParticipantComparator() {
        return (UnambiguousExactModelledParticipantComparator) super.getBiologicalParticipantComparator();
    }

    @Override
    /**
     * Modelled participants come first and then experimental participants.
     * - It uses UnambiguousExactEntitySetComparator to compare participant sets
     * - It uses UnambiguousExactModelledParticipantComparator to compare components
     * - It uses UnambiguousExactParticipantEvidenceComparator to compare experimental participants
     * - It uses UnambiguousExactParticipantBaseComparator to compare basic participant properties
     *
     */
    public int compare(Entity participant1, Entity participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use UnambiguousExactParticipantComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Entity participant1, Entity participant2){
        if (unambiguousExactParticipantComparator == null){
            unambiguousExactParticipantComparator = new UnambiguousExactParticipantComparator();
        }

        return unambiguousExactParticipantComparator.compare(participant1, participant2) == 0;
    }
}
