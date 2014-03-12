package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipantPool;

/**
 * Unambiguous ModelledEntityPoolComparator.
 *
 * It will first compare the basic entity properties using UnambiguousEntityBaseComparator
 * Then it will compare the collection of Entities using UnambiguousEntityBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class UnambiguousModelledParticipantPoolComparator extends ModelledEntityPoolComparator {

    private static UnambiguousModelledParticipantPoolComparator unambiguousEntitySetComparator;

    /**
     * Creates a new UnambiguousParticipantPoolComparator. It will use a UnambiguousParticipantComparator.
     */
    public UnambiguousModelledParticipantPoolComparator() {
        super(new UnambiguousParticipantComparator());
    }

    @Override
    /**
     * It will first compare the basic entity properties using UnambiguousEntityBaseComparator
     * Then it will compare the collection of entities using UnambiguousEntityBaseComparator
     */
    public int compare(ModelledParticipantPool candidat1, ModelledParticipantPool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousModelledParticipantComparator getEntityComparator() {
        return (UnambiguousModelledParticipantComparator) this.entityBaseComparator;
    }

    /**
     * Use UnambiguousParticipantPoolComparator to know if two EntityCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(ModelledParticipantPool candidat1, ModelledParticipantPool candidat2){
        if (unambiguousEntitySetComparator == null){
            unambiguousEntitySetComparator = new UnambiguousModelledParticipantPoolComparator();
        }

        return unambiguousEntitySetComparator.compare(candidat1, candidat2) == 0;
    }
}
