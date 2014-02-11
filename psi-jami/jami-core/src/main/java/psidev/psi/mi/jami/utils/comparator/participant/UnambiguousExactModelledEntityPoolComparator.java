package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledEntityPool;

/**
 * Unambiguous exact ModelledEntityPoolComparator.
 * It will first compare the basic entity properties using UnambiguousExactParticipantBaseComparator
 * Then it will compare the collection of Interactors using UnambiguousExactParticipantBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */

public class UnambiguousExactModelledEntityPoolComparator extends ModelledEntityPoolComparator {

    private static UnambiguousExactModelledEntityPoolComparator unambiguousExactEntityCandidatesComparator;

    /**
     * Creates a new UnambiguousExactEntityPoolComparator. It will use a UnambiguousExactParticipantBaseComparator.
     */
    public UnambiguousExactModelledEntityPoolComparator() {
        super(new UnambiguousExactParticipantComparator());
    }

    @Override
    /**
     * It will first compare the basic entities properties using UnambiguousExactEntityBaseComparator
     * Then it will compare the collection of entities using UnambiguousExactEntityBaseComparator
     */
    public int compare(ModelledEntityPool candidat1, ModelledEntityPool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousExactModelledParticipantComparator getEntityComparator() {
        return (UnambiguousExactModelledParticipantComparator) this.entityBaseComparator;
    }

    /**
     * Use UnambiguousExactEntityPoolComparator to know if two entityCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two entityCandidates are equal
     */
    public static boolean areEquals(ModelledEntityPool candidat1, ModelledEntityPool candidat2){
        if (unambiguousExactEntityCandidatesComparator == null){
            unambiguousExactEntityCandidatesComparator = new UnambiguousExactModelledEntityPoolComparator();
        }

        return unambiguousExactEntityCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
