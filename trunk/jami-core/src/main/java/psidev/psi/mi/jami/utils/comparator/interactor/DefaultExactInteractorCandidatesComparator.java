package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.InteractorCandidates;

/**
 * Default exact InteractorCandidatesComparator.
 *
 * It will first compare the collection of Interactors using DefaultExactInteractorComparator
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class DefaultExactInteractorCandidatesComparator extends InteractorCandidatesComparator{

    private static DefaultExactInteractorCandidatesComparator defaultExactInteractorCandidatesComparator;

    /**
     * Creates a new DefaultInteractorCandidatesComparator. It will use a DefaultExactInteractorComparator.
     */
    public DefaultExactInteractorCandidatesComparator() {
        super(new DefaultExactInteractorComparator());
    }

    @Override
    /**
     * It will first compare the collection of Interactors using DefaultExactInteractorComparator
     */
    public int compare(InteractorCandidates candidat1, InteractorCandidates candidat2) {
        return super.compare(candidat1, candidat2);
    }

    @Override
    public DefaultExactInteractorComparator getInteractorComparator() {
        return (DefaultExactInteractorComparator) this.interactorCollectionComparator.getObjectComparator();
    }

    /**
     * Use DefaultExactInteractorCandidatesComparator to know if two interactorCandidates are equals.
     * @param candidat1
     * @param candidat2
     * @return true if the two interactorCandidates are equal
     */
    public static boolean areEquals(InteractorCandidates candidat1, InteractorCandidates candidat2){
        if (defaultExactInteractorCandidatesComparator == null){
            defaultExactInteractorCandidatesComparator = new DefaultExactInteractorCandidatesComparator();
        }

        return defaultExactInteractorCandidatesComparator.compare(candidat1, candidat2) == 0;
    }
}
