package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorSet;

/**
 * Unambiguous exact InteractorSetComparator.
 * It will first compare the basic interactor properties using UnambiguousExactInteractorBaseComparator
 * Then it will compare the collection of Interactors using UnambiguousExactInteractorBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactInteractorSetComparator extends InteractorSetComparator {

    private static UnambiguousExactInteractorSetComparator unambiguousExactInteractorCandidatesComparator;

    /**
     * Creates a new UnambiguousExactInteractorSetComparator. It will use a UnambiguousExactInteractorBaseComparator.
     */
    public UnambiguousExactInteractorSetComparator() {
        super(new UnambiguousExactInteractorBaseComparator());
    }

    @Override
    /**
     * It will first compare the basic interactor properties using UnambiguousExactInteractorBaseComparator
     * Then it will compare the collection of Interactors using UnambiguousExactInteractorBaseComparator
     */
    public int compare(InteractorSet candidat1, InteractorSet candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousExactInteractorBaseComparator getInteractorBaseComparator() {
        return (UnambiguousExactInteractorBaseComparator) this.interactorBaseComparator;
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
