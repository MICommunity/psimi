package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ParticipantEvidencePool;

/**
 * Unambiguous exact ExperimentalEntityPoolComparator.
 * It will first compare the basic entity properties using UnambiguousExactParticipantBaseComparator
 * Then it will compare the collection of Interactors using UnambiguousExactParticipantBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class UnambiguousExactParticipantEvidencePoolComparator extends ExperimentalEntityPoolComparator {

    private static UnambiguousExactParticipantEvidencePoolComparator unambiguousExactEntityCandidatesComparator;

    /**
     * Creates a new UnambiguousExactParticipantPoolComparator. It will use a UnambiguousExactParticipantBaseComparator.
     */
    public UnambiguousExactParticipantEvidencePoolComparator() {
        super(new UnambiguousExactParticipantComparator());
    }

    @Override
    /**
     * It will first compare the basic entities properties using UnambiguousExactEntityBaseComparator
     * Then it will compare the collection of entities using UnambiguousExactEntityBaseComparator
     */
    public int compare(ParticipantEvidencePool candidat1, ParticipantEvidencePool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousExactParticipantEvidenceComparator getEntityComparator() {
        return (UnambiguousExactParticipantEvidenceComparator) this.entityBaseComparator;
    }

    /**
     * Use UnambiguousExactParticipantPoolComparator to know if two entityCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(ParticipantEvidencePool candidat1, ParticipantEvidencePool candidat2){
        if (unambiguousExactEntityCandidatesComparator == null){
            unambiguousExactEntityCandidatesComparator = new UnambiguousExactParticipantEvidencePoolComparator();
        }

        return unambiguousExactEntityCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
