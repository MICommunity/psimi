package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultFeatureBaseComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorBaseComparator;

/**
 * Default exact participant comparator
 * It will first compare the interactors using DefaultExactInteractorComparator. If both interactors are the same,
 * it will compare the biological roles using DefaultCvTermComparator. If both biological roles are the same, it
 * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
 * it will compare the features using a DefaultFeatureBaseComparator.
 *
 * This comparator will ignore all the other properties of a participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultExactParticipantBaseComparator extends ParticipantBaseComparator {

    private static DefaultExactParticipantBaseComparator defaultExactParticipantComparator;

    /**
     * Creates a new DefaultExactParticipantBaseComparator. It will use a DefaultExactInteractorComparator to compare
     * interactors, a DefaultCvTermComparator to compare biological roles, a DefaultFeatureBaseComparator to
     * compare features and a DefaultParameterComparator to compare parameters.
     */
    public DefaultExactParticipantBaseComparator() {
        super(new DefaultExactInteractorBaseComparator(), new DefaultCvTermComparator(), new DefaultFeatureBaseComparator());
    }

    @Override
    public DefaultExactInteractorBaseComparator getInteractorComparator() {
        return (DefaultExactInteractorBaseComparator) this.interactorComparator;
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare the interactors using DefaultExactInteractorComparator. If both interactors are the same,
     * it will compare the biological roles using DefaultCvTermComparator. If both biological roles are the same, it
     * will look at the stoichiometry (participant with lower stoichiometry will come first). If the stoichiometry is the same for both participants,
     * it will compare the features using a DefaultFeatureBaseComparator.
     *
     * This comparator will ignore all the other properties of a participant.
     */
    public int compare(Participant participant1, Participant participant2) {
        return super.compare(participant1, participant2);
    }

    /**
     * Use DefaultExactParticipantBaseComparator to know if two participants are equals.
     * @param participant1
     * @param participant2
     * @return true if the two participants are equal
     */
    public static boolean areEquals(Participant participant1, Participant participant2){
        if (defaultExactParticipantComparator == null){
            defaultExactParticipantComparator = new DefaultExactParticipantBaseComparator();
        }

        return defaultExactParticipantComparator.compare(participant1, participant2) == 0;
    }
}
