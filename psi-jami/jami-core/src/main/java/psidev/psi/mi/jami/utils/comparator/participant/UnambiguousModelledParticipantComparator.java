package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledEntity;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousModelledFeaturecomparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousComplexComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousInteractorComparator;

/**
 * Unambiguous biological participant comparator.
 * It will compare the basic properties of a biological participant using UnambiguousParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class UnambiguousModelledParticipantComparator extends ModelledParticipantComparator {

    private static UnambiguousModelledParticipantComparator unambiguousParticipantComparator;

    /**
     * Creates a new UnambiguousModelledParticipantComparator. It will use a UnambiguousParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousModelledParticipantComparator() {
        super(new UnambiguousModelledFeaturecomparator());
        setParticipantBaseComparator(new UnambiguousParticipantBaseComparator(new UnambiguousInteractorComparator(new UnambiguousComplexComparator(this))));
    }

    @Override
    public UnambiguousParticipantBaseComparator getParticipantBaseComparator() {
        return (UnambiguousParticipantBaseComparator) this.participantBaseComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a biological participant using UnambiguousParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a biological participant.
     */
    public int compare(ModelledEntity component1, ModelledEntity component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousModelledParticipantComparator to know if two biological participants are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participants are equal
     */
    public static boolean areEquals(ModelledEntity component1, ModelledEntity component2){
        if (unambiguousParticipantComparator == null){
            unambiguousParticipantComparator = new UnambiguousModelledParticipantComparator();
        }

        return unambiguousParticipantComparator.compare(component1, component2) == 0;
    }
}
