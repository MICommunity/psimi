package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorCandidates;

/**
 * Default InteractorCandidatesComparator.
 *
 * It will first compare the collection of Interactors using DefaultInteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultInteractorCandidatesComparator extends InteractorCandidatesComparator {

    private static DefaultInteractorCandidatesComparator defaultInteractorCandidatesComparator;

    /**
     * Creates a new DefaultInteractorCandidatesComparator. It will use a DefaultInteractorComparator.
     */
    public DefaultInteractorCandidatesComparator() {
        super(new DefaultInteractorComparator());
    }

    @Override
    /**
     * It will first compare the collection of Interactors using DefaultInteractorComparator
     */
    public int compare(InteractorCandidates candidat1, InteractorCandidates candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public DefaultInteractorComparator getInteractorComparator() {
        return (DefaultInteractorComparator) this.interactorCollectionComparator.getObjectComparator();
    }

    /**
     * Use DefaultInteractorCandidatesComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorCandidates candidat1, InteractorCandidates candidat2){
        if (defaultInteractorCandidatesComparator == null){
            defaultInteractorCandidatesComparator = new DefaultInteractorCandidatesComparator();
        }

        return defaultInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
