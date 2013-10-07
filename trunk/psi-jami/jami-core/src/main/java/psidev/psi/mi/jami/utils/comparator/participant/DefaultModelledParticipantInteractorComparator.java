package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.ModelledEntity;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Default biological participant comparator based on the interactor only.
 * It will compare the basic properties of a biological participant using DefaultParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultModelledParticipantInteractorComparator {

    /**
     * Use DefaultModelledParticipantInteractorComparator to know if two biological participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(ModelledEntity participant1, ModelledEntity participant2){

        if (participant1 == null && participant2 == null){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            // first compares interactors
            Interactor interactor1 = participant1.getInteractor();
            Interactor interactor2 = participant2.getInteractor();

            return DefaultInteractorComparator.areEquals(interactor1, interactor2);
        }
    }
}
