package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.BiologicalFeature;
import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousBiologicalFeaturecomparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousInteractorComparator;

/**
 * Unambiguous component comparator.
 * It will compare the basic properties of a component using UnambiguousParticipantComparator.
 *
 * This comparator will ignore all the other properties of a component.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class UnambiguousComponentComparator extends ComponentComparator{

    private static UnambiguousComponentComparator unambiguousParticipantComparator;

    /**
     * Creates a new UnambiguousComponentComparator. It will use a UnambiguousParticipantComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousComponentComparator() {
        super(new ParticipantComparator<BiologicalFeature>(new UnambiguousInteractorComparator(), new UnambiguousCvTermComparator(), new UnambiguousBiologicalFeaturecomparator()));
    }

    @Override
    public ParticipantComparator<BiologicalFeature> getParticipantComparator() {
        return (ParticipantComparator<BiologicalFeature>) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a component using UnambiguousParticipantComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(Component component1, Component component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousComponentComparator to know if two components are equals.
     * @param component1
     * @param component2
     * @return true if the two components are equal
     */
    public static boolean areEquals(Component component1, Component component2){
        if (unambiguousParticipantComparator == null){
            unambiguousParticipantComparator = new UnambiguousComponentComparator();
        }

        return unambiguousParticipantComparator.compare(component1, component2) == 0;
    }
}
