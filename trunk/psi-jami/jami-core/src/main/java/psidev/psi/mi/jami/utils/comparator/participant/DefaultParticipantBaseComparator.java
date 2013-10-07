package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Default participant comparator
 * It will first compare the interactors using DefaultInteractorComparator. If both interactors are the same,
 * it will compare the biological roles using DefaultCvTermComparator. If both biological roles are the same, it
 * will look at the stoichiometry (participant with lower stoichiometry will come first).
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultParticipantBaseComparator {

    /**
     * Use DefaultParticipantBaseComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Entity participant1, Entity participant2, boolean ignoreInteractors){

        if (participant1 == null && participant2 == null){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            int comp;
            // first compares interactors
            if (!ignoreInteractors){
                Interactor interactor1 = participant1.getInteractor();
                Interactor interactor2 = participant2.getInteractor();

                if (!DefaultInteractorComparator.areEquals(interactor1, interactor2)){
                     return false;
                }
            }

            // then compares the biological role
            CvTerm role1 = participant1.getBiologicalRole();
            CvTerm role2 = participant2.getBiologicalRole();

            if (!DefaultCvTermComparator.areEquals(role1, role2)){
                return false;
            }

            // then compares the stoichiometry
            Stoichiometry stc1 = participant1.getStoichiometry();
            Stoichiometry stc2 = participant2.getStoichiometry();

            return StoichiometryComparator.areEquals(stc1, stc2);
        }
    }
}
