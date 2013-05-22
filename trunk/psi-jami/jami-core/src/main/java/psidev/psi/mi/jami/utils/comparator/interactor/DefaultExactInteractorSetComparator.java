package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorSet;

/**
 * Default exact InteractorCandidatesComparator.
 *
 * It will first compare the collection of Interactors using DefaultExactInteractorComparator
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactInteractorSetComparator extends InteractorCandidatesComparator{

    private static DefaultExactInteractorSetComparator defaultExactInteractorCandidatesComparator;

    /**
     * Creates a new DefaultInteractorSetComparator. It will use a DefaultExactInteractorComparator.
     */
    public DefaultExactInteractorSetComparator() {
        super(new DefaultExactInteractorComparator());
    }

    @Override
    /**
     * It will first compare the collection of Interactors using DefaultExactInteractorComparator
     */
    public int compare(InteractorSet candidat1, InteractorSet candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public DefaultExactInteractorComparator getInteractorComparator() {
        return (DefaultExactInteractorComparator) this.interactorCollectionComparator.getObjectComparator();
    }

    /**
     * Use DefaultExactInteractorSetComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorSet candidat1, InteractorSet candidat2){
        if (defaultExactInteractorCandidatesComparator == null){
            defaultExactInteractorCandidatesComparator = new DefaultExactInteractorSetComparator();
        }

        return defaultExactInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
