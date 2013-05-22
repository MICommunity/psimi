package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorSet;

/**
 * Default InteractorCandidatesComparator.
 *
 * It will first compare the collection of Interactors using DefaultInteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultInteractorSetComparator extends InteractorCandidatesComparator {

    private static DefaultInteractorSetComparator defaultInteractorCandidatesComparator;

    /**
     * Creates a new DefaultInteractorSetComparator. It will use a DefaultInteractorComparator.
     */
    public DefaultInteractorSetComparator() {
        super(new DefaultInteractorComparator());
    }

    @Override
    /**
     * It will first compare the collection of Interactors using DefaultInteractorComparator
     */
    public int compare(InteractorSet candidat1, InteractorSet candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public DefaultInteractorComparator getInteractorComparator() {
        return (DefaultInteractorComparator) this.interactorCollectionComparator.getObjectComparator();
    }

    /**
     * Use DefaultInteractorSetComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorSet candidat1, InteractorSet candidat2){
        if (defaultInteractorCandidatesComparator == null){
            defaultInteractorCandidatesComparator = new DefaultInteractorSetComparator();
        }

        return defaultInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
