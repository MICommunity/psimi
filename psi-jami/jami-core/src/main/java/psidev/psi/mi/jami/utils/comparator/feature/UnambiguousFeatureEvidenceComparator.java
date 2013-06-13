package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Unambiguous FeatureEvidence comparator.
 * It will first compare feature detection methods using UnambiguousCvTermComparator. If both feature detection methods are the same,
 * it will use a UnambiguousFeatureBaseComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of an experimental feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class UnambiguousFeatureEvidenceComparator extends FeatureEvidenceComparator {

    private static UnambiguousFeatureEvidenceComparator unambiguousExperimentalFeatureComparator;

    /**
     * Creates a new UnambiguousFeatureEvidenceComparator. It will use a UnambiguousCvTermComparator to
     * compare feature detection methods and a UnambiguousFeatureBaseComparator to compare basic feature properties
     */
    public UnambiguousFeatureEvidenceComparator() {
        super(new UnambiguousFeatureBaseComparator(), new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousFeatureBaseComparator getFeatureComparator() {
        return (UnambiguousFeatureBaseComparator) this.featureComparator;
    }

    @Override
    /**
     * It will first compare feature detection methods using UnambiguousCvTermComparator. If both feature detection methods are the same,
     * it will use a UnambiguousFeatureBaseComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of an experimental feature.
     */
    public int compare(FeatureEvidence experimentalFeature1, FeatureEvidence experimentalFeature2) {
        return super.compare(experimentalFeature1, experimentalFeature2);
    }

    /**
     * Use UnambiguousFeatureEvidenceComparator to know if two experimental features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two experimental features are equal
     */
    public static boolean areEquals(FeatureEvidence feature1, FeatureEvidence feature2){
        if (unambiguousExperimentalFeatureComparator == null){
            unambiguousExperimentalFeatureComparator = new UnambiguousFeatureEvidenceComparator();
        }

        return unambiguousExperimentalFeatureComparator.compare(feature1, feature2) == 0;
    }
}
