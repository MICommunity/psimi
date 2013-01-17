package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorCandidates;

/**
 * Unambiguous exact InteractorCandidatesComparator.
 *
 * It will first compare the collection of Interactors using UnambiguousExactInteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactInteractorCandidatesComparator extends InteractorCandidatesComparator{

    private static UnambiguousExactInteractorCandidatesComparator unambiguousExactInteractorCandidatesComparator;

    /**
     * Creates a new UnambiguousExactInteractorCandidatesComparator. It will use a UnambiguousExactInteractorComparator.
     */
    public UnambiguousExactInteractorCandidatesComparator() {
        super(new UnambiguousExactInteractorComparator());
    }

    @Override
    /**
     * It will first compare the collection of Interactors using UnambiguousExactInteractorComparator
     */
    public int compare(InteractorCandidates candidat1, InteractorCandidates candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousExactInteractorComparator getInteractorComparator() {
        return (UnambiguousExactInteractorComparator) this.interactorCollectionComparator.getObjectComparator();
    }

    /**
     * Use UnambiguousExactInteractorCandidatesComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorCandidates candidat1, InteractorCandidates candidat2){
        if (unambiguousExactInteractorCandidatesComparator == null){
            unambiguousExactInteractorCandidatesComparator = new UnambiguousExactInteractorCandidatesComparator();
        }

        return unambiguousExactInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
