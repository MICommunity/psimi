package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorSet;

/**
 * Default exact InteractorSetComparator.
 * It will first compare the basic interactor properties using DefaultExactInteractorBaseComparator
 * Then it will compare the collection of Interactors using DefaultExactInteractorBaseComparator
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactInteractorSetComparator extends InteractorSetComparator {

    private static DefaultExactInteractorSetComparator defaultExactInteractorCandidatesComparator;

    /**
     * Creates a new DefaultInteractorSetComparator. It will use a DefaultExactInteractorBaseComparator.
     */
    public DefaultExactInteractorSetComparator() {
        super(new DefaultExactInteractorBaseComparator());
    }

    @Override
    /**
     * It will first compare the basic interactor properties using DefaultExactInteractorBaseComparator
     * Then it will compare the collection of Interactors using DefaultExactInteractorBaseComparator
     */
    public int compare(InteractorSet candidat1, InteractorSet candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public DefaultExactInteractorBaseComparator getInteractorBaseComparator() {
        return (DefaultExactInteractorBaseComparator) this.interactorBaseComparator;
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
