package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorComparator;

/**
 * Default exact experimental participant comparator based on the interactor only.
 * It will compare the basic properties of aninteractor using DefaultExactInteractorComparator.
 *
 * This comparator will ignore all the other properties of an experimental participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultExactParticipantEvidenceInteractorComparator {

    /**
     * Use DefaultExactParticipantEvidenceInteractorComparator to know if two experimental participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two experimental participants are equal
     */
    public static boolean areEquals(ParticipantEvidence participant1, ParticipantEvidence participant2){

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

            return DefaultExactInteractorComparator.areEquals(interactor1, interactor2);
        }
    }
}