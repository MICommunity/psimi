package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ExperimentalEntityPool;

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

public class UnambiguousExperimentalEntityPoolComparator extends ExperimentalEntityPoolComparator {

    private static UnambiguousExperimentalEntityPoolComparator unambiguousEntitySetComparator;

    /**
     * Creates a new UnambiguousEntityPoolComparator. It will use a UnambiguousParticipantComparator.
     */
    public UnambiguousExperimentalEntityPoolComparator() {
        super(new UnambiguousParticipantComparator());
    }

    @Override
    /**
     * It will first compare the basic entity properties using UnambiguousEntityBaseComparator
     * Then it will compare the collection of entities using UnambiguousEntityBaseComparator
     */
    public int compare(ExperimentalEntityPool candidat1, ExperimentalEntityPool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousParticipantEvidenceComparator getEntityComparator() {
        return (UnambiguousParticipantEvidenceComparator) this.entityBaseComparator;
    }

    /**
     * Use UnambiguousEntityPoolComparator to know if two EntityCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(ExperimentalEntityPool candidat1, ExperimentalEntityPool candidat2){
        if (unambiguousEntitySetComparator == null){
            unambiguousEntitySetComparator = new UnambiguousExperimentalEntityPoolComparator();
        }

        return unambiguousEntitySetComparator.compare(candidat1, candidat2) == 0;
    }
}
