package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.Feature;

/**
 * Generic unambiguous feature comparator.
 * Biological features come first and then experimental features.
 * - It uses UnambiguousBiologicalFeatureComparator to compare biological features
 * - It uses UnambiguousExperimentalFeatureComparator to compare experimental features
 * - It uses UnambiguousFeatureBaseComparator to compare basic feature properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class UnambiguousFeatureComparator extends FeatureComparator {

    private static UnambiguousFeatureComparator unambiguousFeatureComparator;

    /**
     * Creates a UnambiguousFeatureComparator. It will use a UnambiguousFeatureBaseComparator to compare basic feature properties
     */
    public UnambiguousFeatureComparator() {
        super(new UnambiguousFeatureBaseComparator());
    }

    @Override
    public UnambiguousFeatureBaseComparator getFeatureBaseComparator() {
        return (UnambiguousFeatureBaseComparator) this.featureBaseComparator;
    }

    @Override
    /**
     * Biological features come first and then experimental features.
     * - It uses UnambiguousBiologicalFeatureComparator to compare biological features
     * - It uses UnambiguousExperimentalFeatureComparator to compare experimental features
     * - It uses UnambiguousFeatureBaseComparator to compare basic feature properties
     *
     */
    public int compare(Feature feature1, Feature feature2) {
        return super.compare(feature1, feature2);
    }

    /**
     * Use UnambiguousFeatureComparator to know if two features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two features are equal
     */
    public static boolean areEquals(Feature feature1, Feature feature2){
        if (unambiguousFeatureComparator == null){
            unambiguousFeatureComparator = new UnambiguousFeatureComparator();
        }

        return unambiguousFeatureComparator.compare(feature1, feature2) == 0;
    }
}
