package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorPool;

/**
 * Unambiguous exact InteractorPoolComparator.
 * It will first compare the basic interactor properties using UnambiguousExactInteractorBaseComparator
 * Then it will compare the collection of Interactors using UnambiguousExactInteractorBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousExactInteractorPoolComparator extends InteractorPoolComparator {

    private static UnambiguousExactInteractorPoolComparator unambiguousExactInteractorCandidatesComparator;

    /**
     * Creates a new UnambiguousExactInteractorPoolComparator. It will use a UnambiguousExactInteractorBaseComparator.
     */
    public UnambiguousExactInteractorPoolComparator() {
        super(new UnambiguousExactInteractorComparator());
    }

    @Override
    /**
     * It will first compare the basic interactor properties using UnambiguousExactInteractorBaseComparator
     * Then it will compare the collection of Interactors using UnambiguousExactInteractorBaseComparator
     */
    public int compare(InteractorPool candidat1, InteractorPool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousExactInteractorBaseComparator getInteractorBaseComparator() {
        return (UnambiguousExactInteractorBaseComparator) this.interactorBaseComparator;
    }

    /**
     * Use UnambiguousExactInteractorPoolComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorPool candidat1, InteractorPool candidat2){
        if (unambiguousExactInteractorCandidatesComparator == null){
            unambiguousExactInteractorCandidatesComparator = new UnambiguousExactInteractorPoolComparator();
        }

        return unambiguousExactInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
