package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.ComponentFeature;

/**
 * Unambiguous ComponentFeature comparator.
 * It will use a UnambiguousFeatureBaseComparator to compare basic properties of a component feature.
 *
 * This comparator will ignore all the other properties of a component feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class UnambiguousComponentFeatureComparator extends ComponentFeatureComparator {

    private static UnambiguousComponentFeatureComparator unambiguousComponentFeatureComparator;

    /**
     * Creates a new UnambiguousComponentFeatureComparator. It will use a UnambiguousFeatureBaseComparator to compare basic feature properties
     */
    public UnambiguousComponentFeatureComparator() {
        super(new UnambiguousFeatureBaseComparator());
    }

    @Override
    public UnambiguousFeatureBaseComparator getFeatureComparator() {
        return (UnambiguousFeatureBaseComparator) this.featureComparator;
    }

    @Override
    /**
     * It will use a UnambiguousFeatureBaseComparator to compare basic properties of a component feature.
     *
     * This comparator will ignore all the other properties of a component feature.
     *
     */
    public int compare(ComponentFeature biologicalFeature1, ComponentFeature biologicalFeature2) {
        return super.compare(biologicalFeature1, biologicalFeature2);
    }

    /**
     * Use UnambiguousComponentFeatureComparator to know if two component features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two component features are equal
     */
    public static boolean areEquals(ComponentFeature feature1, ComponentFeature feature2){
        if (unambiguousComponentFeatureComparator == null){
            unambiguousComponentFeatureComparator = new UnambiguousComponentFeatureComparator();
        }

        return unambiguousComponentFeatureComparator.compare(feature1, feature2) == 0;
    }
}
