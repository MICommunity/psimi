package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorPool;

/**
 * Unambiguous InteractorPoolComparator.
 *
 * It will first compare the basic interactor properties using UnambiguousInteractorBaseComparator
 * Then it will compare the collection of Interactors using UnambiguousInteractorBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousInteractorPoolComparator extends InteractorPoolComparator {

    private static UnambiguousInteractorPoolComparator unambiguousInteractorCandidatesComparator;

    /**
     * Creates a new UnambiguousInteractorPoolComparator. It will use a UnambiguousInteractorComparator.
     */
    public UnambiguousInteractorPoolComparator() {
        super(new UnambiguousInteractorComparator());
    }

    @Override
    /**
     * It will first compare the basic interactor properties using UnambiguousInteractorBaseComparator
     * Then it will compare the collection of Interactors using UnambiguousInteractorBaseComparator
     */
    public int compare(InteractorPool candidat1, InteractorPool candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public UnambiguousInteractorBaseComparator getInteractorBaseComparator() {
        return (UnambiguousInteractorBaseComparator) this.interactorBaseComparator;
    }

    /**
     * Use UnambiguousInteractorPoolComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorPool candidat1, InteractorPool candidat2){
        if (unambiguousInteractorCandidatesComparator == null){
            unambiguousInteractorCandidatesComparator = new UnambiguousInteractorPoolComparator();
        }

        return unambiguousInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
