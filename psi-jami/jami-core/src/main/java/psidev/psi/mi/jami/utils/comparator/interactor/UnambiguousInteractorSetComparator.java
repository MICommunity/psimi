package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorSet;

/**
 * Unambiguous InteractorSetComparator.
 *
 * It will first compare the basic interactor properties using UnambiguousInteractorBaseComparator
 * Then it will compare the collection of Interactors using UnambiguousInteractorBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousInteractorSetComparator extends InteractorSetComparator {

    private static UnambiguousInteractorSetComparator unambiguousInteractorCandidatesComparator;

    /**
     * Creates a new UnambiguousInteractorSetComparator. It will use a UnambiguousInteractorComparator.
     */
    public UnambiguousInteractorSetComparator() {
        super(new UnambiguousInteractorBaseComparator());
    }

    @Override
    /**
     * It will first compare the basic interactor properties using UnambiguousInteractorBaseComparator
     * Then it will compare the collection of Interactors using UnambiguousInteractorBaseComparator
     */
    public int compare(InteractorSet candidat1, InteractorSet candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousInteractorBaseComparator getInteractorBaseComparator() {
        return (UnambiguousInteractorBaseComparator) this.interactorBaseComparator;
    }

    /**
     * Use UnambiguousInteractorSetComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorSet candidat1, InteractorSet candidat2){
        if (unambiguousInteractorCandidatesComparator == null){
            unambiguousInteractorCandidatesComparator = new UnambiguousInteractorSetComparator();
        }

        return unambiguousInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
