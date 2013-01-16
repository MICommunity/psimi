package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.BiologicalFeature;

/**
 * Default BiologicalFeature comparator.
 * It will use a DefaultFeatureComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of a biological feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultBiologicalFeatureComparator extends BiologicalFeatureComparator {

    private static DefaultBiologicalFeatureComparator defaultBiologicalFeatureComparator;

    /**
     * Creates a new DefaultBiologicalFeatureComparator. It will use a DefaultFeatureComparator to compare basic feature properties
     */
    public DefaultBiologicalFeatureComparator() {
        super(new DefaultFeatureComparator());
    }

    @Override
    public DefaultFeatureComparator getFeatureComparator() {
        return (DefaultFeatureComparator) this.featureComparator;
    }

    @Override
    /**
     * It will use a DefaultFeatureComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of a biological feature.
     */
    public int compare(BiologicalFeature biologicalFeature1, BiologicalFeature biologicalFeature2) {
        return super.compare(biologicalFeature1, biologicalFeature2);
    }

    /**
     * Use DefaultBiologicalFeatureComparator to know if two biological features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two biological features are equal
     */
    public static boolean areEquals(BiologicalFeature feature1, BiologicalFeature feature2){
        if (defaultBiologicalFeatureComparator == null){
            defaultBiologicalFeatureComparator = new DefaultBiologicalFeatureComparator();
        }

        return defaultBiologicalFeatureComparator.compare(feature1, feature2) == 0;
    }
}
