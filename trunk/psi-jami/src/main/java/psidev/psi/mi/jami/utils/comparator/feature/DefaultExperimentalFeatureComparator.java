package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.ExperimentalFeature;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default ExperimentalFeature comparator.
 * It will first compare feature detection methods using DefaultCvTermComparator. If both feature detection methods are the same,
 * it will use a DefaultFeatureComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of an experimental feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultExperimentalFeatureComparator extends ExperimentalFeatureComparator{

    private static DefaultExperimentalFeatureComparator defaultExperimentalFeatureComparator;

    /**
     * Creates a new DefaultExperimentalFeatureComparator. It will use a DefaultCvTermComparator to
     * compare feature detection methods and a DefaultFeatureComparator to compare basic feature properties
     */
    public DefaultExperimentalFeatureComparator() {
        super(new DefaultFeatureComparator(), new DefaultCvTermComparator());
    }

    @Override
    public DefaultFeatureComparator getFeatureComparator() {
        return (DefaultFeatureComparator) this.featureComparator;
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    /**
     * It will first compare feature detection methods using DefaultCvTermComparator. If both feature detection methods are the same,
     * it will use a DefaultFeatureComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of an experimental feature.
     */
    public int compare(ExperimentalFeature experimentalFeature1, ExperimentalFeature experimentalFeature2) {
        return super.compare(experimentalFeature1, experimentalFeature2);
    }

    /**
     * Use DefaultExperimentalFeatureComparator to know if two experimental features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two experimental features are equal
     */
    public static boolean areEquals(ExperimentalFeature feature1, ExperimentalFeature feature2){
        if (defaultExperimentalFeatureComparator == null){
            defaultExperimentalFeatureComparator = new DefaultExperimentalFeatureComparator();
        }

        return defaultExperimentalFeatureComparator.compare(feature1, feature2) == 0;
    }
}
