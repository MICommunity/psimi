package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
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

public class DefaultParticipantBaseComparator extends ParticipantBaseComparator {

    private static DefaultParticipantBaseComparator defaultParticipantComparator;

    /**
     * Creates a new DefaultParticipantBaseComparator. It will use a DefaultInteractorBaseComparator to compare
     * interactors, a DefaultCvTermComparator to compare biological roles.
     */
    public DefaultParticipantBaseComparator() {
        super(new DefaultInteractorComparator(), new DefaultCvTermComparator());
    }

    public DefaultParticipantBaseComparator(DefaultInteractorComparator comparator) {
        super(comparator != null ? comparator : new DefaultInteractorComparator(), new DefaultCvTermComparator());
    }

    @Override
    public DefaultInteractorComparator getInteractorComparator() {
        return (DefaultInteractorComparator) this.interactorComparator;
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare the interactors using DefaultInteractorBaseComparator. If both interactors are the same,
     * it will compare the biological roles using DefaultCvTermComparator. If both biological roles are the same, it
     * will look at the stoichiometry (participant with lower stoichiometry will come first).
     *
     * This comparator will ignore all the other properties of a participant.
     */
    public int compare(Participant participant1, Participant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use DefaultParticipantBaseComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (defaultParticipantComparator == null){
            defaultParticipantComparator = new DefaultParticipantBaseComparator();
        }

        return defaultParticipantComparator.compare(participant1, participant2) == 0;
    }
}
