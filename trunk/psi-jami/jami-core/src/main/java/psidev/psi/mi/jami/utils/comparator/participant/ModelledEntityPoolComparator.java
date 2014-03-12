package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipantPool;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

import java.util.Comparator;

/**
 * Basic ExperimentalEntityPoolComparator.
 *
 * It will first compare the collection of Entities using ParticipantComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/10/13</pre>
 */

public class ModelledEntityPoolComparator implements Comparator<ModelledParticipantPool> {

    protected CollectionComparator<Participant> entityCollectionComparator;
    protected ModelledParticipantComparator entityBaseComparator;

    /**
     * Creates a new InteractorPoolComparator
     * @param interactorComparator : the interactor comparator required to compare interactors
     */
    public ModelledEntityPoolComparator(ParticipantComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The participant/entity comparator is required to compare entities. It cannot be null");
        }
        this.entityBaseComparator = interactorComparator.getBiologicalParticipantComparator();
        this.entityCollectionComparator = new CollectionComparator<Participant>(interactorComparator);
    }

    public CollectionComparator<Participant> getEntityCollectionComparator() {
        return entityCollectionComparator;
    }

    public ModelledParticipantComparator getEntityComparator(){
        return entityBaseComparator;
    }

    /**
     * It will first compare the collection of Interactors using InteractorComparator
     * @param interactorCandidates1
     * @param interactorCandidates2
     * @return
     */
    public int compare(ModelledParticipantPool interactorCandidates1, ModelledParticipantPool interactorCandidates2) {
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
            this.entityBaseComparator.getParticipantBaseComparator().setIgnoreInteractors(true);
            int comp = entityBaseComparator.compare(interactorCandidates1, interactorCandidates2);
            if (comp != 0){
                return comp;
            }

            this.entityBaseComparator.getParticipantBaseComparator().setIgnoreInteractors(false);
            // compare collections
            return entityCollectionComparator.compare(interactorCandidates1, interactorCandidates2);
        }
    }
}
