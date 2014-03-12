package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ParticipantEvidencePool;

/**
 * Unambiguous ExperimentalEntityPoolComparator.
 *
 * It will first compare the basic entity properties using UnambiguousEntityBaseComparator
 * Then it will compare the collection of Entities using UnambiguousEntityBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class UnambiguousParticipantEvidencePoolComparator extends ExperimentalEntityPoolComparator {

    private static UnambiguousParticipantEvidencePoolComparator unambiguousEntitySetComparator;

    /**
     * Creates a new UnambiguousParticipantPoolComparator. It will use a UnambiguousParticipantComparator.
     */
    public UnambiguousParticipantEvidencePoolComparator() {
        super(new UnambiguousParticipantComparator());
    }

    @Override
    /**
     * It will first compare the basic entity properties using UnambiguousEntityBaseComparator
     * Then it will compare the collection of entities using UnambiguousEntityBaseComparator
     */
    public int compare(ParticipantEvidencePool candidat1, ParticipantEvidencePool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousParticipantEvidenceComparator getEntityComparator() {
        return (UnambiguousParticipantEvidenceComparator) this.entityBaseComparator;
    }

    /**
     * Use UnambiguousParticipantPoolComparator to know if two EntityCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(ParticipantEvidencePool candidat1, ParticipantEvidencePool candidat2){
        if (unambiguousEntitySetComparator == null){
            unambiguousEntitySetComparator = new UnambiguousParticipantEvidencePoolComparator();
        }

        return unambiguousEntitySetComparator.compare(candidat1, candidat2) == 0;
    }
}
