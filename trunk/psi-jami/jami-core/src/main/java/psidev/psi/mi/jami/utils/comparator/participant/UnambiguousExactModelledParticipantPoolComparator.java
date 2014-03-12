package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipantPool;

/**
 * Unambiguous exact ModelledEntityPoolComparator.
 * It will first compare the basic entity properties using UnambiguousExactParticipantBaseComparator
 * Then it will compare the collection of Interactors using UnambiguousExactParticipantBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class UnambiguousExactModelledParticipantPoolComparator extends ModelledEntityPoolComparator {

    private static UnambiguousExactModelledParticipantPoolComparator unambiguousExactEntityCandidatesComparator;

    /**
     * Creates a new UnambiguousExactParticipantPoolComparator. It will use a UnambiguousExactParticipantBaseComparator.
     */
    public UnambiguousExactModelledParticipantPoolComparator() {
        super(new UnambiguousExactParticipantComparator());
    }

    @Override
    /**
     * It will first compare the basic entities properties using UnambiguousExactEntityBaseComparator
     * Then it will compare the collection of entities using UnambiguousExactEntityBaseComparator
     */
    public int compare(ModelledParticipantPool candidat1, ModelledParticipantPool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousExactModelledParticipantComparator getEntityComparator() {
        return (UnambiguousExactModelledParticipantComparator) this.entityBaseComparator;
    }

    /**
     * Use UnambiguousExactParticipantPoolComparator to know if two entityCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(ModelledParticipantPool candidat1, ModelledParticipantPool candidat2){
        if (unambiguousExactEntityCandidatesComparator == null){
            unambiguousExactEntityCandidatesComparator = new UnambiguousExactModelledParticipantPoolComparator();
        }

        return unambiguousExactEntityCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
