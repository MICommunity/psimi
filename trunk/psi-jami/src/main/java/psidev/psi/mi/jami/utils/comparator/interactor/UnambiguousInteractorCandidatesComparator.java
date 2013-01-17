package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorCandidates;

/**
 * Unambiguous InteractorCandidatesComparator.
 *
 * It will first compare the collection of Interactors using UnambiguousInteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousInteractorCandidatesComparator extends InteractorCandidatesComparator {

    private static UnambiguousInteractorCandidatesComparator unambiguousInteractorCandidatesComparator;

    /**
     * Creates a new UnambiguousInteractorCandidatesComparator. It will use a UnambiguousInteractorComparator.
     */
    public UnambiguousInteractorCandidatesComparator() {
        super(new UnambiguousInteractorComparator());
    }

    @Override
    /**
     * It will first compare the collection of Interactors using UnambiguousInteractorComparator
     */
    public int compare(InteractorCandidates candidat1, InteractorCandidates candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousInteractorComparator getInteractorComparator() {
        return (UnambiguousInteractorComparator) this.interactorCollectionComparator.getObjectComparator();
    }

    /**
     * Use UnambiguousInteractorCandidatesComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorCandidates candidat1, InteractorCandidates candidat2){
        if (unambiguousInteractorCandidatesComparator == null){
            unambiguousInteractorCandidatesComparator = new UnambiguousInteractorCandidatesComparator();
        }

        return unambiguousInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
