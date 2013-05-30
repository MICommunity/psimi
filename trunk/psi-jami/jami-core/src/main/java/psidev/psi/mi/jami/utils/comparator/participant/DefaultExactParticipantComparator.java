package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;

/**
 * Generic default exact participant comparator.
 * Components come first and then experimental participants.
 * - It uses DefaultExactComponentComparator to compare components
 * - It uses DefaultExactParticipantEvidenceComparator to compare experimental participants
 * - It uses DefaultExactParticipantBaseComparator to compare basic participant properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultExactParticipantComparator extends ParticipantComparator {

    private static DefaultExactParticipantComparator defaultExactParticipantComparator;

    /**
     * Creates a DefaultExactParticipantComparator. It will use a DefaultExactParticipantBaseComparator to compare basic feature properties
     */
    public DefaultExactParticipantComparator() {
        super(new DefaultExactParticipantBaseComparator(), new DefaultExactParticipantEvidenceComparator(),
                new DefaultExactModelledParticipantComparator());
    }

    @Override
    public DefaultExactParticipantBaseComparator getParticipantBaseComparator() {
        return (DefaultExactParticipantBaseComparator) this.participantBaseComparator;
    }

    @Override
    public DefaultExactParticipantEvidenceComparator getExperimentalParticipantComparator() {
        return (DefaultExactParticipantEvidenceComparator) this.experimentalParticipantComparator;
    }

    @Override
    public DefaultExactModelledParticipantComparator getBiologicalParticipantComparator() {
        return (DefaultExactModelledParticipantComparator) super.getBiologicalParticipantComparator();
    }

    @Override
    /**
     * Components come first and then experimental participants.
     * - It uses DefaultExactComponentComparator to compare components
     * - It uses DefaultExactParticipantEvidenceComparator to compare experimental participants
     * - It uses DefaultExactParticipantBaseComparator to compare basic participant properties
     *
     */
    public int compare(Participant participant1, Participant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use DefaultExactParticipantComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (defaultExactParticipantComparator == null){
            defaultExactParticipantComparator = new DefaultExactParticipantComparator();
        }

        return defaultExactParticipantComparator.compare(participant1, participant2) == 0;
    }
}
