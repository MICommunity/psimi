package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ModelledFeature;

/**
 * Generic default feature comparator.
 * Biological features come first and then experimental features.
 * - It uses DefaultModelledFeatureComparator to compare biological features
 * - It uses DefaultFeatureEvidenceComparator to compare experimental features
 * - It uses DefaultFeatureBaseComparator to compare basic feature properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultFeatureComparator {

    /**
     * Use DefaultFeatureComparator to know if two features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two features are equal
     */
    public static boolean areEquals(Feature feature1, Feature feature2){
        if (feature1 == null && feature2 == null){
            return true;
        }
        else if (feature1 == null || feature2 == null){
            return false;
        }
        else {
            // first check if both features are from the same interface

            // both are biological features
            boolean isBiologicalFeature1 = feature1 instanceof ModelledFeature;
            boolean isBiologicalFeature2 = feature2 instanceof ModelledFeature;
            if (isBiologicalFeature1 && isBiologicalFeature2){
                return DefaultModelledFeatureComparator.areEquals((ModelledFeature) feature1, (ModelledFeature) feature2);
            }
            // the biological feature is before
            else if (isBiologicalFeature1 || isBiologicalFeature2){
                return false;
            }
            else {
                // both are experimental features
                boolean isExperimentalFeature1 = feature1 instanceof FeatureEvidence;
                boolean isExperimentalFeature2 = feature2 instanceof FeatureEvidence;
                if (isExperimentalFeature1 && isExperimentalFeature2){
                    return DefaultFeatureEvidenceComparator.areEquals((FeatureEvidence) feature1, (FeatureEvidence) feature2);
                }
                // the experimental feature is before
                else if (isExperimentalFeature1 || isExperimentalFeature2){
                    return false;
                }
                else {
                    return DefaultFeatureBaseComparator.areEquals(feature1, feature2);
                }
            }
        }
    }
}
