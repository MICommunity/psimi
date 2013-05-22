package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorSet;

/**
 * Unambiguous exact InteractorCandidatesComparator.
 *
 * It will first compare the collection of Interactors using UnambiguousExactInteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactInteractorSetComparator extends InteractorCandidatesComparator{

    private static UnambiguousExactInteractorSetComparator unambiguousExactInteractorCandidatesComparator;

    /**
     * Creates a new UnambiguousExactInteractorSetComparator. It will use a UnambiguousExactInteractorComparator.
     */
    public UnambiguousExactInteractorSetComparator() {
        super(new UnambiguousExactInteractorComparator());
    }

    @Override
    /**
     * It will first compare the collection of Interactors using UnambiguousExactInteractorComparator
     */
    public int compare(InteractorSet candidat1, InteractorSet candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousExactInteractorComparator getInteractorComparator() {
        return (UnambiguousExactInteractorComparator) this.interactorCollectionComparator.getObjectComparator();
    }

    /**
     * Use UnambiguousExactInteractorSetComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorSet candidat1, InteractorSet candidat2){
        if (unambiguousExactInteractorCandidatesComparator == null){
            unambiguousExactInteractorCandidatesComparator = new UnambiguousExactInteractorSetComparator();
        }

        return unambiguousExactInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
