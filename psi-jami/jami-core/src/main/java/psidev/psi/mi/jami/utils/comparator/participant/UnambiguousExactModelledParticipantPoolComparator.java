package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.ModelledParticipantPool;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousModelledFeaturecomparator;

/**
 * unambiguous exact biological participant pool comparator.
 * It will compare the basic properties of a biological participant using UnambiguousExactParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a biological participant.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class UnambiguousExactModelledParticipantPoolComparator extends ModelledParticipantPoolComparator {

    private static UnambiguousExactModelledParticipantPoolComparator defaultParticipantComparator;

    /**
     * Creates a new UnambiguousExactModelledParticipantPoolComparator. It will use a UnambiguousExactParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousExactModelledParticipantPoolComparator() {
        super(new UnambiguousExactParticipantBaseComparator(), new UnambiguousModelledFeaturecomparator(),
                new UnambiguousExactModelledEntityComparator());
    }

    public UnambiguousExactModelledParticipantPoolComparator(UnambiguousExactEntityBaseComparator interactorComparator) {
        super(new UnambiguousExactParticipantBaseComparator(interactorComparator),
                new UnambiguousModelledFeaturecomparator(),
                new UnambiguousExactModelledEntityComparator(interactorComparator));

    }

    @Override
    public UnambiguousExactParticipantBaseComparator getParticipantBaseComparator() {
        return (UnambiguousExactParticipantBaseComparator) super.getParticipantBaseComparator();
    }

    @Override
    public UnambiguousExactModelledEntityComparator getModelledEntityComparator() {
        return (UnambiguousExactModelledEntityComparator) super.getModelledEntityComparator();
    }

    @Override
    /**
     * It will compare the basic properties of a component using UnambiguousExactParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(ModelledParticipantPool component1, ModelledParticipantPool component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousExactModelledParticipantComparator to know if two biological participant are equals.
     * @param component1
     * @param component2
     * @return true if the two biological participant are equal
     */
    public static boolean areEquals(ModelledParticipantPool component1, ModelledParticipantPool component2){
        if (defaultParticipantComparator == null){
            defaultParticipantComparator = new UnambiguousExactModelledParticipantPoolComparator();
        }

        return defaultParticipantComparator.compare(component1, component2) == 0;
    }
}
