package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.interactor.InteractorComparator;

import java.util.Comparator;

/**
 * Basic entity comparator.
 * It will first compare the interactors using InteractorComparator. If both interactors are the same,
 * it will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
 * it will compare the features using a Comparator<Feature>.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class EntityBaseComparator implements Comparator<Entity> {

    private StoichiometryComparator stoichiometryComparator;
    private InteractorComparator interactorComparator;

    private boolean ignoreInteractors = false;

    /**
     * Creates a new EntityBaseComparator
     * @param interactorComparator : interactor comparator required for comparing the molecules
     */
    public EntityBaseComparator(InteractorComparator interactorComparator){

        if (interactorComparator == null){
            throw new IllegalArgumentException("The Interactor comparator is required to compare interactors. It cannot be null");
        }
        this.interactorComparator = interactorComparator;

        this.stoichiometryComparator = new StoichiometryComparator();
    }

    public StoichiometryComparator getStoichiometryComparator() {
        return stoichiometryComparator;
    }

    public InteractorComparator getInteractorComparator() {
        return interactorComparator;
    }

    public boolean isIgnoreInteractors() {
        return ignoreInteractors;
    }

    public void setIgnoreInteractors(boolean ignoreInteractors) {
        this.ignoreInteractors = ignoreInteractors;
    }

    /**
     * It will first compare the interactors using InteractorComparator. If both interactors are the same,
     * it will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
     * it will compare the features using a Comparator<Feature>.
     *
     * This comparator will ignore all the other properties of a participant.
     * @param participant1
     * @param participant2
     * @return
     */
    public int compare(Entity participant1, Entity participant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (participant1 == participant2){
            return EQUAL;
        }
        else if (participant1 == null){
            return AFTER;
        }
        else if (participant2 == null){
            return BEFORE;
        }
        else {
            int comp;
            // first compares interactors
            if (!ignoreInteractors){
                Interactor interactor1 = participant1.getInteractor();
                Interactor interactor2 = participant2.getInteractor();

                comp = interactorComparator.compare(interactor1, interactor2);
                if (comp != 0){
                    return comp;
                }
            }

            // then compares the stoichiometry
            Stoichiometry stc1 = participant1.getStoichiometry();
            Stoichiometry stc2 = participant2.getStoichiometry();

            return stoichiometryComparator.compare(stc1, stc2);
        }
    }
}
