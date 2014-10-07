package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Default entity comparator
 * It will first compare the interactors using DefaultInteractorComparator. If both interactors are the same,
 * it will look at the stoichiometry (participant with lower stoichiometry will come first).
 *
 * This comparator will ignore all the other properties of an entity.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultEntityBaseComparator {

    /**
     * Use DefaultParticipantBaseComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Entity participant1, Entity participant2, boolean ignoreInteractors){

        if (participant1 == participant2){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            // first compares interactors
            if (!ignoreInteractors){
                Interactor interactor1 = participant1.getInteractor();
                Interactor interactor2 = participant2.getInteractor();

                if (!DefaultInteractorComparator.areEquals(interactor1, interactor2)){
                     return false;
                }
            }

            // then compares the stoichiometry
            Stoichiometry stc1 = participant1.getStoichiometry();
            Stoichiometry stc2 = participant2.getStoichiometry();

            return StoichiometryComparator.areEquals(stc1, stc2);
        }
    }
}