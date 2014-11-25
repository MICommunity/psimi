package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default participant comparator
 * It will first compare the interactors and stoichiometry using DefaultEntityBaseComparator
 * it will then compare the biological roles using DefaultCvTermComparator.
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
    public static boolean areEquals(Participant participant1, Participant participant2, boolean ignoreInteractors){

        if (participant1 == participant2){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            int comp;
            // first compares interactors
            if (!DefaultEntityBaseComparator.areEquals(participant1, participant2, ignoreInteractors)){
                return false;
            }

            // then compares the biological role
            CvTerm role1 = participant1.getBiologicalRole();
            CvTerm role2 = participant2.getBiologicalRole();

            return DefaultCvTermComparator.areEquals(role1, role2);
        }
    }
}
