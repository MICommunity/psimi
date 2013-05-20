package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * It will compare the interactors using DefaultInteractorComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultParticipantInteractorComparator extends ParticipantInteractorComparator {

    private static DefaultParticipantInteractorComparator defaultInteractorParticipantComparator;

    /**
     * Creates a new DefaultParticipantInteractorComparator. It will use a DefaultInteractorBaseComparator to compare
     * interactors.
     */
    public DefaultParticipantInteractorComparator() {
        super(new DefaultInteractorComparator());
    }

    @Override
    public DefaultInteractorComparator getInteractorComparator() {
        return (DefaultInteractorComparator) this.interactorComparator;
    }

    @Override
    /**
     * It will compare the interactors using DefaultInteractorComparator.
     *
     * This comparator will ignore all the other properties of a participant.
     */
    public int compare(Participant participant1, Participant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use DefaultParticipantInteractorComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (defaultInteractorParticipantComparator == null){
            defaultInteractorParticipantComparator = new DefaultParticipantInteractorComparator();
        }

        return defaultInteractorParticipantComparator.compare(participant1, participant2) == 0;
    }
}
