package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorSet;

/**
 * Default InteractorSetComparator.
 *
 * It will first compare the basic interactor properties using DefaultInteractorBaseComparator
 * Then it will compare the collection of Interactors using DefaultInteractorBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultInteractorSetComparator extends InteractorSetComparator {

    private static DefaultInteractorSetComparator defaultInteractorCandidatesComparator;

    /**
     * Creates a new DefaultInteractorSetComparator. It will use a DefaultInteractorBaseComparator.
     */
    public DefaultInteractorSetComparator() {
        super(new DefaultInteractorBaseComparator());
    }

    @Override
    /**
     * It will first compare the basic interactor properties using DefaultInteractorBaseComparator
     * Then it will compare the collection of Interactors using DefaultInteractorBaseComparator     */
    public int compare(InteractorSet candidat1, InteractorSet candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public DefaultInteractorBaseComparator getInteractorBaseComparator() {
        return (DefaultInteractorBaseComparator) this.interactorBaseComparator;
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
