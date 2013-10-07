package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledEntity;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousModelledFeaturecomparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactComplexComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

/**
 * unambiguous exact biological participant comparator.
 * It will compare the basic properties of a biological participant using UnambiguousExactParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class UnambiguousExactModelledParticipantComparator extends ModelledParticipantComparator {

    private static UnambiguousExactModelledParticipantComparator defaultParticipantComparator;

    /**
     * Creates a new UnambiguousExactModelledParticipantComparator. It will use a UnambiguousExactParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousExactModelledParticipantComparator() {
        super(new UnambiguousModelledFeaturecomparator());
        setParticipantBaseComparator(new UnambiguousExactParticipantBaseComparator(new UnambiguousExactInteractorComparator(new UnambiguousExactComplexComparator(this))));
    }

    @Override
    public UnambiguousExactParticipantBaseComparator getParticipantBaseComparator() {
        return (UnambiguousExactParticipantBaseComparator) this.participantBaseComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a component using UnambiguousExactParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(ModelledEntity component1, ModelledEntity component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousExactModelledParticipantComparator to know if two biological participant are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participant are equal
     */
    public static boolean areEquals(ModelledEntity component1, ModelledEntity component2){
        if (defaultParticipantComparator == null){
            defaultParticipantComparator = new UnambiguousExactModelledParticipantComparator();
        }

        return defaultParticipantComparator.compare(component1, component2) == 0;
    }
}
