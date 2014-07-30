package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipantPool;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousModelledFeaturecomparator;

/**
 * unambiguous biological participant pool comparator.
 * It will compare the basic properties of a biological participant using UnambiguousParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class UnambiguousModelledParticipantPoolComparator extends ModelledParticipantPoolComparator {

    private static UnambiguousModelledParticipantPoolComparator defaultParticipantComparator;

    /**
     * Creates a new UnambiguousModelledParticipantPoolComparator. It will use a UnambiguousParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousModelledParticipantPoolComparator() {
        super(new UnambiguousParticipantBaseComparator(), new UnambiguousModelledFeaturecomparator(),
                new UnambiguousModelledEntityComparator());
    }

    public UnambiguousModelledParticipantPoolComparator(UnambiguousEntityBaseComparator interactorComparator) {
        super(new UnambiguousParticipantBaseComparator(interactorComparator),
                new UnambiguousModelledFeaturecomparator(),
                new UnambiguousModelledEntityComparator(interactorComparator));

    }

    @Override
    public UnambiguousParticipantBaseComparator getParticipantBaseComparator() {
        return (UnambiguousParticipantBaseComparator) this.participantBaseComparator;
    }

    @Override
    public UnambiguousModelledEntityComparator getModelledEntityComparator() {
        return (UnambiguousModelledEntityComparator) super.getModelledEntityComparator();
    }

    @Override
    /**
     * It will compare the basic properties of a component using UnambiguousParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(ModelledParticipantPool component1, ModelledParticipantPool component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousModelledParticipantComparator to know if two biological participant are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participant are equal
     */
    public static boolean areEquals(ModelledParticipantPool component1, ModelledParticipantPool component2){
        if (defaultParticipantComparator == null){
            defaultParticipantComparator = new UnambiguousModelledParticipantPoolComparator();
        }

        return defaultParticipantComparator.compare(component1, component2) == 0;
    }
}
