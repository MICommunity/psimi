package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.BiologicalFeature;
import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultBiologicalFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultExactInteractorComparator;

/**
 * Default exact component comparator.
 * It will compare the basic properties of a component using DefaultExactParticipantBaseComparator.
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
     * Creates a new UnambiguousExactComponentComparator. It will use a DefaultExactParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public UnambiguousExactComponentComparator() {
        super(new ParticipantBaseComparator<BiologicalFeature>(new DefaultExactInteractorComparator(), new DefaultCvTermComparator(), new DefaultBiologicalFeatureComparator()));
    }

    @Override
    public ParticipantBaseComparator<BiologicalFeature> getParticipantComparator() {
        return (ParticipantBaseComparator<BiologicalFeature>) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a component using DefaultExactParticipantBaseComparator.
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
