package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorSet;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

import java.util.*;

/**
 * Basic InteractorCandidatesComparator.
 *
 * It will first compare the collection of Interactors using InteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class InteractorCandidatesComparator implements Comparator<InteractorSet> {

    protected CollectionComparator<Interactor> interactorCollectionComparator;

    /**
     * Creates a new InteractorCandidatesComparator
     * @param interactorComparator : the interactor comparator required to compare interactors
     */
    public InteractorCandidatesComparator(InteractorComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The Interactor comparator is required to compare interactors. It cannot be null");
        }
        this.interactorCollectionComparator = new CollectionComparator<Interactor>(interactorComparator);
    }

    public InteractorComparator getInteractorComparator() {
        return (InteractorComparator) interactorCollectionComparator.getObjectComparator();
    }

    /**
     * It will first compare the collection of Interactors using InteractorComparator
     * @param interactorCandidates1
     * @param interactorCandidates2
     * @return
     */
    public int compare(InteractorSet interactorCandidates1, InteractorSet interactorCandidates2) {

        // compare collections
        return interactorCollectionComparator.compare(interactorCandidates1, interactorCandidates2);
    }
}
