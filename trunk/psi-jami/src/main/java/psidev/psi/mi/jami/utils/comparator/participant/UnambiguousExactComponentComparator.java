package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.ComponentFeature;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousComponentFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactInteractorComparator;

/**
 * unambiguous exact component comparator.
 * It will compare the basic properties of a component using UnambiguousExactParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a component.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class UnambiguousExactComponentComparator extends ComponentComparator{

    private static UnambiguousExactComponentComparator defaultParticipantComparator;

    /**
     * Creates a new UnambiguousExactComponentComparator. It will use a UnambiguousExactParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousExactComponentComparator() {
        super(new ParticipantBaseComparator<ComponentFeature>(new UnambiguousExactInteractorComparator(), new UnambiguousCvTermComparator(), new UnambiguousComponentFeatureComparator()));
    }

    @Override
    public ParticipantBaseComparator<ComponentFeature> getParticipantComparator() {
        return (ParticipantBaseComparator<ComponentFeature>) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a component using UnambiguousExactParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(Component component1, Component component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use UnambiguousExactComponentComparator to know if two components are equals.
     * @param component1
     * @param component2
     * @return true if the two components are equal
     */
    public static boolean areEquals(Component component1, Component component2){
        if (defaultParticipantComparator == null){
            defaultParticipantComparator = new UnambiguousExactComponentComparator();
        }

        return defaultParticipantComparator.compare(component1, component2) == 0;
    }
}
