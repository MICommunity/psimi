package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Entity;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorComparator;

/**
 * It will compare the interactors using DefaultExactInteractorComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultExactParticipantInteractorComparator {

    /**
     * Use DefaultExactParticipantInteractorComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Entity participant1, Entity participant2){

        if (participant1 == participant2){
            return true;
        }
        else if (participant1 == null || participant2 == null){
            return false;
        }
        else {
            // first compares interactors
            Interactor interactor1 = participant1.getInteractor();
            Interactor interactor2 = participant2.getInteractor();

            return DefaultExactInteractorComparator.areEquals(interactor1, interactor2);
        }
    }
}
