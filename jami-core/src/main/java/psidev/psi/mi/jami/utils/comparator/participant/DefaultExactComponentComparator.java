package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.ComponentFeature;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultComponentFeatureComparator;
import psidev.psi.mi.jami.utils.comparator.interactor.DefaultInteractorComparator;

/**
 * Default exact component comparator.
 * It will compare the basic properties of a component using DefaultExactParticipantBaseComparator.
 *
 * This comparator will ignore all the other properties of a component.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/01/13</pre>
 */

public class DefaultExactComponentComparator extends ComponentComparator{

    private static DefaultExactComponentComparator defaultExactParticipantComparator;

    /**
     * Creates a new DefaultExactComponentComparator. It will use a DefaultExactParticipantBaseComparator to compare
     * the basic properties of a participant.
     */
    public DefaultExactComponentComparator() {
        super(new ParticipantBaseComparator<ComponentFeature>(new DefaultInteractorComparator(), new DefaultCvTermComparator(), new DefaultComponentFeatureComparator()));
    }

    @Override
    public ParticipantBaseComparator<ComponentFeature> getParticipantComparator() {
        return (ParticipantBaseComparator<ComponentFeature>) this.participantComparator;
    }

    @Override
    /**
     * It will compare the basic properties of a component using DefaultParticipantBaseComparator.
     *
     * This comparator will ignore all the other properties of a component.
     */
    public int compare(Component component1, Component component2) {
        return super.compare(component1, component2);
    }

    /**
     * Use DefaultExactComponentComparator to know if two components are equals.
     * @param component1
     * @param component2
     * @return true if the two components are equal
     */
    public static boolean areEquals(Component component1, Component component2){
        if (defaultExactParticipantComparator == null){
            defaultExactParticipantComparator = new DefaultExactComponentComparator();
        }

        return defaultExactParticipantComparator.compare(component1, component2) == 0;
    }
}
