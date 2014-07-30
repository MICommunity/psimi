package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ParticipantPool;

/**
 * Unambiguous exact participant pool comparator
 * It will first compares basic participant properties using UnambiguousExactParticipantBaseComparator, then it will compare participant pool type using cv term comparator and then it will compare
 * each participant candidate using UnambiguousExactEntityBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class UnambiguousExactParticipantPoolComparator extends ParticipantPoolComparator {
    private static UnambiguousExactParticipantPoolComparator unambiguousParticipantComparator;

    /**
     * Creates a new UnambiguousParticipantBaseComparator. It will use a UnambiguousInteractorComparator to compare
     * interactors, a UnambiguousCvTermComparator to compare biological roles
     */
    public UnambiguousExactParticipantPoolComparator() {
        super(new UnambiguousExactParticipantBaseComparator());
    }

    @Override
    public UnambiguousExactParticipantBaseComparator getParticipantBaseComparator() {
        return (UnambiguousExactParticipantBaseComparator) this.participantBaseComparator;
    }

    @Override
    /**
     * It will first compares basic participant properties using UnambiguousExactParticipantBaseComparator, then it will compare participant pool type using cv term comparator and then it will compare
     * each participant candidate using UnambiguousExactEntityBaseComparator
     */
    public int compare(ParticipantPool participant1, ParticipantPool participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use UnambiguousExactParticipantPoolComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(ParticipantPool participant1, ParticipantPool participant2){
        if (unambiguousParticipantComparator == null){
            unambiguousParticipantComparator = new UnambiguousExactParticipantPoolComparator();
        }

        return unambiguousParticipantComparator.compare(participant1, participant2) == 0;
    }
}
