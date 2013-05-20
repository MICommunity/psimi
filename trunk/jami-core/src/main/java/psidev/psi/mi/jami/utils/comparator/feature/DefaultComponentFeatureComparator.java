package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.ComponentFeature;

/**
 * Default ComponentFeature comparator.
 * It will use a DefaultFeatureBaseComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of a component feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultComponentFeatureComparator extends ComponentFeatureComparator {
    private static DefaultComponentFeatureComparator defaultBiologicalFeatureComparator;

    /**
     * Creates a new DefaultComponentFeatureComparator. It will use a DefaultFeatureBaseComparator to compare basic feature properties
     */
    public DefaultComponentFeatureComparator() {
        super(new DefaultFeatureBaseComparator());
    }

    @Override
    public DefaultFeatureBaseComparator getFeatureComparator() {
        return (DefaultFeatureBaseComparator) this.featureComparator;
    }

    @Override
    /**
     * It will use a DefaultFeatureBaseComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of a component feature.
     */
    public int compare(ComponentFeature biologicalFeature1, ComponentFeature biologicalFeature2) {
        return super.compare(biologicalFeature1, biologicalFeature2);
    }

    /**
     * Use DefaultComponentFeatureComparator to know if two biological features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two biological features are equal
     */
    public static boolean areEquals(ComponentFeature feature1, ComponentFeature feature2){
        if (defaultBiologicalFeatureComparator == null){
            defaultBiologicalFeatureComparator = new DefaultComponentFeatureComparator();
        }

        return defaultBiologicalFeatureComparator.compare(feature1, feature2) == 0;
    }
}
