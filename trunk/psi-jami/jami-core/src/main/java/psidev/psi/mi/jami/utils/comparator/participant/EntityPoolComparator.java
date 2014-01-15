package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.EntityPool;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

import java.util.Comparator;

/**
 * Basic EntityPoolComparator.
 *
 * It will first compare the collection of Entities using ParticipantComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */

public class EntityPoolComparator implements Comparator<EntityPool> {

    protected CollectionComparator<Entity> entityCollectionComparator;
    protected Comparator<Entity> entityBaseComparator;

    /**
     * Creates a new InteractorPoolComparator
     * @param interactorComparator : the interactor comparator required to compare interactors
     */
    public EntityPoolComparator(ParticipantComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The participant/entity comparator is required to compare entities. It cannot be null");
        }
        this.entityBaseComparator = interactorComparator.getParticipantBaseComparator();
        this.entityCollectionComparator = new CollectionComparator<Entity>(interactorComparator);
    }

    public CollectionComparator<Entity> getEntityCollectionComparator() {
        return entityCollectionComparator;
    }

    public Comparator<Entity> getEntityComparator(){
        return entityBaseComparator;
    }

    /**
     * It will first compare the collection of Interactors using InteractorComparator
     * @param interactorCandidates1
     * @param interactorCandidates2
     * @return
     */
    public int compare(EntityPool interactorCandidates1, EntityPool interactorCandidates2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interactorCandidates1 == null && interactorCandidates2 == null){
            return EQUAL;
        }
        else if (interactorCandidates1 == null){
            return AFTER;
        }
        else if (interactorCandidates2 == null){
            return BEFORE;
        }
        else {

            int comp = entityBaseComparator.compare(interactorCandidates1, interactorCandidates2);
            if (comp != 0){
                return comp;
            }
            // compare collections
            return entityCollectionComparator.compare(interactorCandidates1, interactorCandidates2);
        }
    }
}
