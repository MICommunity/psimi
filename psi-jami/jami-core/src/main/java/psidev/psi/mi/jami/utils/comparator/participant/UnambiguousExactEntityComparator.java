package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Entity;

/**
 * Unambiguous exact generic entity comparator
 * Modelled participants come first and then experimental participants.
 * - It uses UnambiguousExactParticipantPoolComparator to compare participant sets
 * - It uses UnambiguousExactModelledParticipantComparator to compare components
 * - It uses UnambiguousExactParticipantEvidenceComparator to compare experimental participants
 * - It uses UnambiguousExactParticipantBaseComparator to compare basic participant properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class UnambiguousExactEntityComparator extends EntityComparator {

    private static UnambiguousExactEntityComparator unambiguousExactParticipantComparator;

    /**
     * Creates a UnambiguousExactParticipantComparator. It will use a UnambiguousExactParticipantBaseComparator to compare basic feature properties
     */
    public UnambiguousExactEntityComparator() {
        super(new UnambiguousExactEntityBaseComparator(), new UnambiguousExactExperimentalEntityComparator(),
                new UnambiguousExactModelledEntityComparator(),
                new UnambiguousExactParticipantComparator());
    }

    @Override
    public UnambiguousExactEntityBaseComparator getEntityBaseComparator() {
        return (UnambiguousExactEntityBaseComparator) this.entityBaseComparator;
    }

    @Override
    public UnambiguousExactExperimentalEntityComparator getExperimentalEntityComparator() {
        return (UnambiguousExactExperimentalEntityComparator) this.experimentalEntityComparator;
    }

    @Override
    public UnambiguousExactModelledEntityComparator getBiologicalEntityComparator() {
        return (UnambiguousExactModelledEntityComparator) super.biologicalEntityComparator;
    }

    @Override
    public UnambiguousExactParticipantComparator getParticipantComparator() {
        return (UnambiguousExactParticipantComparator) super.participantComparator;
    }

    @Override
    /**
     * Modelled participants come first and then experimental participants.
     * - It uses UnambiguousExactParticipantPoolComparator to compare participant sets
     * - It uses UnambiguousExactModelledParticipantComparator to compare components
     * - It uses UnambiguousExactParticipantEvidenceComparator to compare experimental participants
     * - It uses UnambiguousExactParticipantBaseComparator to compare basic participant properties
     *
     */
    public int compare(Entity participant1, Entity participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use UnambiguousExactEntityComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Entity participant1, Entity participant2){
        if (unambiguousExactParticipantComparator == null){
            unambiguousExactParticipantComparator = new UnambiguousExactEntityComparator();
        }

        return unambiguousExactParticipantComparator.compare(participant1, participant2) == 0;
    }
}
