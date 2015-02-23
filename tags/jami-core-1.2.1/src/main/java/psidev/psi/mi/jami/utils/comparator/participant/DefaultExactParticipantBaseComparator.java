package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Stoichiometry;

/**
 * Default exact participant comparator
 * It will first compare the interactors and stoichiometry using DefaultExactEntityComparator. If both interactors are the same,
 * it will compare the biological roles using DefaultCvTermComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultExactParticipantBaseComparator {

    /**
     * Use DefaultExactParticipantBaseComparator to know if two participants are equals.
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

            if (!DefaultExactEntityBaseComparator.areEquals(participant1, participant2, ignoreInteractors)){
                return false;
            }

            // then compares the stoichiometry
            Stoichiometry stc1 = participant1.getStoichiometry();
            Stoichiometry stc2 = participant2.getStoichiometry();

            return StoichiometryComparator.areEquals(stc1, stc2);
        }
    }
}
