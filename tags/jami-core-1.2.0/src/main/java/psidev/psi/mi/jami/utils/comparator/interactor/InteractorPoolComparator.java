package psidev.psi.mi.jami.utils.comparator.interactor;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.InteractorPool;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

import java.util.*;

/**
 * Basic InteractorPoolComparator.
 *
 * It will first compare the collection of Interactors using InteractorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class InteractorPoolComparator implements Comparator<InteractorPool> {

    private CollectionComparator<Interactor> interactorCollectionComparator;
    private Comparator<Interactor> interactorBaseComparator;

    /**
     * Creates a new InteractorPoolComparator
     * @param interactorComparator : the interactor comparator required to compare interactors
     */
    public InteractorPoolComparator(InteractorComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The Interactor comparator is required to compare interactors. It cannot be null");
        }
        this.interactorBaseComparator = interactorComparator.getInteractorBaseComparator();
        this.interactorCollectionComparator = new CollectionComparator<Interactor>(interactorComparator);
    }

    /**
     * Creates a new InteractorPoolComparator
     * @param interactorComparator : the interactor comparator required to compare interactors
     */
    public InteractorPoolComparator(InteractorComparator interactorComparator, CollectionComparator<Interactor> interactorCollectionComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The Interactor comparator is required to compare interactors. It cannot be null");
        }
        this.interactorBaseComparator = interactorComparator.getInteractorBaseComparator();
        this.interactorCollectionComparator = interactorCollectionComparator != null ? interactorCollectionComparator : new CollectionComparator<Interactor>(interactorComparator);
    }

    public CollectionComparator<Interactor> getInteractorCollectionComparator() {
        return interactorCollectionComparator;
    }

    public Comparator<Interactor> getInteractorBaseComparator(){
        return interactorBaseComparator;
    }

    /**
     * It will first compare the collection of Interactors using InteractorComparator
     * @param interactorCandidates1
     * @param interactorCandidates2
     * @return
     */
    public int compare(InteractorPool interactorCandidates1, InteractorPool interactorCandidates2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interactorCandidates1 == interactorCandidates2){
            return EQUAL;
        }
        else if (interactorCandidates1 == null){
            return AFTER;
        }
        else if (interactorCandidates2 == null){
            return BEFORE;
        }
        else {

            int comp = interactorBaseComparator.compare(interactorCandidates1, interactorCandidates2);
            if (comp != 0){
                return comp;
            }
            // compare collections
            return interactorCollectionComparator.compare(interactorCandidates1, interactorCandidates2);
        }
    }
}
