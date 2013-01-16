package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.ExperimentalFeature;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous ExperimentalFeature comparator.
 * It will first compare feature detection methods using UnambiguousCvTermComparator. If both feature detection methods are the same,
 * it will use a UnambiguousFeatureComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of an experimental feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class UnambiguousExperimentalFeatureComparator extends ExperimentalFeatureComparator{

    private static UnambiguousExperimentalFeatureComparator unambiguousExperimentalFeatureComparator;

    /**
     * Creates a new UnambiguousExperimentalFeatureComparator. It will use a UnambiguousCvTermComparator to
     * compare feature detection methods and a UnambiguousFeatureComparator to compare basic feature properties
     */
    public UnambiguousExperimentalFeatureComparator() {
        super(new UnambiguousFeatureComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousFeatureComparator getFeatureComparator() {
        return (UnambiguousFeatureComparator) this.featureComparator;
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare feature detection methods using UnambiguousCvTermComparator. If both feature detection methods are the same,
     * it will use a UnambiguousFeatureComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of an experimental feature.
     */
    public int compare(ExperimentalFeature experimentalFeature1, ExperimentalFeature experimentalFeature2) {
        return super.compare(experimentalFeature1, experimentalFeature2);
    }

    /**
     * Use UnambiguousExperimentalFeatureComparator to know if two experimental features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two experimental features are equal
     */
    public static boolean areEquals(ExperimentalFeature feature1, ExperimentalFeature feature2){
        if (unambiguousExperimentalFeatureComparator == null){
            unambiguousExperimentalFeatureComparator = new UnambiguousExperimentalFeatureComparator();
        }

        return unambiguousExperimentalFeatureComparator.compare(feature1, feature2) == 0;
    }
}
