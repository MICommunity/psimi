package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.interactor.InteractorBaseComparator;

import java.util.Comparator;

/**
 * Participant comparator only based on the interactor.
 * It will compare the interactors using InteractorComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class ParticipantInteractorComparator implements Comparator<Participant> {

    protected InteractorBaseComparator interactorComparator;

    /**
     * Creates a new ParticipantInteractorComparator.
     * @param interactorComparator : the interactor comparator required to compare the interactor
     */
    public ParticipantInteractorComparator(InteractorBaseComparator interactorComparator){
        if (interactorComparator == null){
            throw new IllegalArgumentException("The Interactor comparator is required to compare interactors. It cannot be null");
        }
        this.interactorComparator = interactorComparator;
    }

    public InteractorBaseComparator getInteractorComparator() {
        return interactorComparator;
    }

    /**
     * It will compare the interactors using InteractorComparator.
     *
     * This comparator will ignore all the other properties of a participant.
     * @param participant1
     * @param participant2
     * @return
     */
    public int compare(Participant participant1, Participant participant2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (participant1 == null && participant2 == null){
            return EQUAL;
        }
        else if (participant1 == null){
            return AFTER;
        }
        else if (participant2 == null){
            return BEFORE;
        }
        else {
            // first compares interactors
            Interactor interactor1 = participant1.getInteractor();
            Interactor interactor2 = participant2.getInteractor();

            return interactorComparator.compare(interactor1, interactor2);
        }
    }
}

