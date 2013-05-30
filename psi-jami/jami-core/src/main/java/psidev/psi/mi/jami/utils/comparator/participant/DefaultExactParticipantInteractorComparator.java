package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
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

public class DefaultExactParticipantInteractorComparator extends ParticipantInteractorComparator<Participant> {

    private static DefaultExactParticipantInteractorComparator defaultInteractorParticipantComparator;

    /**
     * Creates a new DefaultExactParticipantInteractorComparator. It will use a DefaultExactInteractorComparator to compare
     * interactors.
     */
    public DefaultExactParticipantInteractorComparator() {
        super(new DefaultExactInteractorComparator());
    }

    @Override
    public DefaultExactInteractorComparator getInteractorComparator() {
        return (DefaultExactInteractorComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will compare the interactors using DefaultExactInteractorComparator.
     *
     * This comparator will ignore all the other properties of a participant.
     */
    public int compare(Participant participant1, Participant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use DefaultExactParticipantInteractorComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (defaultInteractorParticipantComparator == null){
            defaultInteractorParticipantComparator = new DefaultExactParticipantInteractorComparator();
        }

        return defaultInteractorParticipantComparator.compare(participant1, participant2) == 0;
    }
}
