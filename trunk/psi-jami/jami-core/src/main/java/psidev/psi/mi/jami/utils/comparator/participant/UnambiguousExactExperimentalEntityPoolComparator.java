package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalEntityPool;

/**
 * Unambiguous exact ExperimentalEntityPoolComparator.
 * It will first compare the basic entity properties using UnambiguousExactParticipantBaseComparator
 * Then it will compare the collection of Interactors using UnambiguousExactParticipantBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class UnambiguousExactExperimentalEntityPoolComparator extends ExperimentalEntityPoolComparator {

    private static UnambiguousExactExperimentalEntityPoolComparator unambiguousExactEntityCandidatesComparator;

    /**
     * Creates a new UnambiguousExactEntityPoolComparator. It will use a UnambiguousExactParticipantBaseComparator.
     */
    public UnambiguousExactExperimentalEntityPoolComparator() {
        super(new UnambiguousExactParticipantComparator());
    }

    @Override
    /**
     * It will first compare the basic entities properties using UnambiguousExactEntityBaseComparator
     * Then it will compare the collection of entities using UnambiguousExactEntityBaseComparator
     */
    public int compare(ExperimentalEntityPool candidat1, ExperimentalEntityPool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousExactParticipantEvidenceComparator getEntityComparator() {
        return (UnambiguousExactParticipantEvidenceComparator) this.entityBaseComparator;
    }

    /**
     * Use UnambiguousExactEntityPoolComparator to know if two entityCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(ExperimentalEntityPool candidat1, ExperimentalEntityPool candidat2){
        if (unambiguousExactEntityCandidatesComparator == null){
            unambiguousExactEntityCandidatesComparator = new UnambiguousExactExperimentalEntityPoolComparator();
        }

        return unambiguousExactEntityCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
