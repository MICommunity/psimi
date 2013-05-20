package psidev.psi.mi.jami.utils.comparator.participant;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.ComponentFeature;
import psidev.psi.mi.jami.utils.comparator.feature.FeatureCollectionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic component comparator.
 * It will compare the basic properties of a component using ParticipantInteractorComparator.
 *
 * This comparator will ignore all the other properties of a component.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/01/13</pre>
 */

public class ComponentComparator implements Comparator<Component> {

    protected ParticipantInteractorComparator participantComparator;
    protected FeatureCollectionComparator featureCollectionComparator;

    /**
     * Creates a new ComponentComparator
     * @param participantComparator : the participant comparator required to compare basic participant properties
     */
    public ComponentComparator(ParticipantInteractorComparator participantComparator){
        if (participantComparator == null){
            throw new IllegalArgumentException("The participant comparator is required to compare basic participant properties. It cannot be null");
        }
        this.participantComparator = participantComparator;
        this.featureCollectionComparator = new FeatureCollectionComparator(participantComparator);
    }

    public ParticipantInteractorComparator getParticipantComparator() {
        return participantComparator;
    }

    public FeatureCollectionComparator getFeatureCollectionComparator() {
        return featureCollectionComparator;
    }

    /**
     * It will compare the basic properties of a component using ParticipantInteractorComparator.
     *
     * This comparator will ignore all the other properties of a component.
     * @param component1
     * @param component2
     * @return
     */
    public int compare(Component component1, Component component2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (component1 == null && component2 == null){
            return EQUAL;
        }
        else if (component1 == null){
            return AFTER;
        }
        else if (component2 == null){
            return BEFORE;
        }
        else {
            int comp = participantComparator.compare(component1, component2);

            // then compares the features
            Collection<? extends ComponentFeature> features1 = component1.getComponentFeatures();
            Collection<? extends ComponentFeature> features2 = component2.getComponentFeatures();

            return featureCollectionComparator.compare(features1, features2);
        }
    }
}
