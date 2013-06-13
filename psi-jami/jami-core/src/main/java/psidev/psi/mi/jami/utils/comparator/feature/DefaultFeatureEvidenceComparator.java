package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default FeatureEvidence comparator.
 * It will first compare feature detection methods using DefaultCvTermComparator. If both feature detection methods are the same,
 * it will use a DefaultFeatureBaseComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of an experimental feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultFeatureEvidenceComparator extends FeatureEvidenceComparator {

    private static DefaultFeatureEvidenceComparator defaultExperimentalFeatureComparator;

    /**
     * Creates a new DefaultFeatureEvidenceComparator. It will use a DefaultCvTermComparator to
     * compare feature detection methods and a DefaultFeatureBaseComparator to compare basic feature properties
     */
    public DefaultFeatureEvidenceComparator() {
        super(new DefaultFeatureBaseComparator(), new DefaultCvTermComparator());
    }

    @Override
    public DefaultFeatureBaseComparator getFeatureComparator() {
        return (DefaultFeatureBaseComparator) this.featureComparator;
    }

    @Override
    /**
     * It will first compare feature detection methods using DefaultCvTermComparator. If both feature detection methods are the same,
     * it will use a DefaultFeatureBaseComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of an experimental feature.
     */
    public int compare(FeatureEvidence experimentalFeature1, FeatureEvidence experimentalFeature2) {
        return super.compare(experimentalFeature1, experimentalFeature2);
    }

    /**
     * Use DefaultFeatureEvidenceComparator to know if two experimental features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two experimental features are equal
     */
    public static boolean areEquals(FeatureEvidence feature1, FeatureEvidence feature2){
        if (defaultExperimentalFeatureComparator == null){
            defaultExperimentalFeatureComparator = new DefaultFeatureEvidenceComparator();
        }

        return defaultExperimentalFeatureComparator.compare(feature1, feature2) == 0;
    }
}
