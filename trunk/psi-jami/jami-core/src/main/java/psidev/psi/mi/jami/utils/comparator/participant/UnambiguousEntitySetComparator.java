package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.EntitySet;

/**
 * Unambiguous EntitySetComparator.
 *
 * It will first compare the basic entity properties using UnambiguousEntityBaseComparator
 * Then it will compare the collection of Entities using UnambiguousEntityBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class UnambiguousEntitySetComparator extends EntitySetComparator {

    private static UnambiguousEntitySetComparator unambiguousEntitySetComparator;

    /**
     * Creates a new UnambiguousEntitySetComparator. It will use a UnambiguousParticipantComparator.
     */
    public UnambiguousEntitySetComparator() {
        super(new UnambiguousParticipantComparator());
    }

    @Override
    /**
     * It will first compare the basic entity properties using UnambiguousEntityBaseComparator
     * Then it will compare the collection of entities using UnambiguousEntityBaseComparator
     */
    public int compare(EntitySet candidat1, EntitySet candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousParticipantComparator getEntityComparator() {
        return (UnambiguousParticipantComparator) this.entityBaseComparator;
    }

    /**
     * Use UnambiguousEntitySetComparator to know if two EntityCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(EntitySet candidat1, EntitySet candidat2){
        if (unambiguousEntitySetComparator == null){
            unambiguousEntitySetComparator = new UnambiguousEntitySetComparator();
        }

        return unambiguousEntitySetComparator.compare(candidat1, candidat2) == 0;
    }
}
