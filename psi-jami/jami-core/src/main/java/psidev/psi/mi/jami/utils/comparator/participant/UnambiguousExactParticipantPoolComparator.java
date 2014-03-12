package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ParticipantPool;

/**
 * Unambiguous exact EntityPoolComparator.
 * It will first compare the basic entity properties using UnambiguousExactParticipantBaseComparator
 * Then it will compare the collection of Interactors using UnambiguousExactParticipantBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class UnambiguousExactParticipantPoolComparator extends EntityPoolComparator {

    private static UnambiguousExactParticipantPoolComparator unambiguousExactEntityCandidatesComparator;

    /**
     * Creates a new UnambiguousExactParticipantPoolComparator. It will use a UnambiguousExactParticipantBaseComparator.
     */
    public UnambiguousExactParticipantPoolComparator() {
        super(new UnambiguousExactParticipantComparator());
    }

    @Override
    /**
     * It will first compare the basic entities properties using UnambiguousExactEntityBaseComparator
     * Then it will compare the collection of entities using UnambiguousExactEntityBaseComparator
     */
    public int compare(ParticipantPool candidat1, ParticipantPool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousExactParticipantBaseComparator getEntityComparator() {
        return (UnambiguousExactParticipantBaseComparator) this.entityBaseComparator;
    }

    /**
     * Use UnambiguousExactParticipantPoolComparator to know if two entityCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(ParticipantPool candidat1, ParticipantPool candidat2){
        if (unambiguousExactEntityCandidatesComparator == null){
            unambiguousExactEntityCandidatesComparator = new UnambiguousExactParticipantPoolComparator();
        }

        return unambiguousExactEntityCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
