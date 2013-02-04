package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.BiologicalFeature;

/**
 * Unambiguous BiologicalFeature comparator.
 * It will use a UnambiguousFeatureBaseComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of a biological feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class UnambiguousBiologicalFeaturecomparator extends BiologicalFeatureComparator{

    private static UnambiguousBiologicalFeaturecomparator unambiguousBiologicalFeatureComparator;

    /**
     * Creates a new UnambiguousBiologicalFeaturecomparator. It will use a UnambiguousFeatureBaseComparator to compare basic feature properties
     */
    public UnambiguousBiologicalFeaturecomparator() {
        super(new UnambiguousFeatureBaseComparator());
    }

    @Override
    public UnambiguousFeatureBaseComparator getFeatureComparator() {
        return (UnambiguousFeatureBaseComparator) this.featureComparator;
    }

    @Override
    /**
     * It will use a UnambiguousFeatureBaseComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of a biological feature.
     */
    public int compare(BiologicalFeature biologicalFeature1, BiologicalFeature biologicalFeature2) {
        return super.compare(biologicalFeature1, biologicalFeature2);
    }

    /**
     * Use UnambiguousBiologicalFeaturecomparator to know if two biological features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two biological features are equal
     */
    public static boolean areEquals(BiologicalFeature feature1, BiologicalFeature feature2){
        if (unambiguousBiologicalFeatureComparator == null){
            unambiguousBiologicalFeatureComparator = new UnambiguousBiologicalFeaturecomparator();
        }

        return unambiguousBiologicalFeatureComparator.compare(feature1, feature2) == 0;
    }
}
