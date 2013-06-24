package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;

import java.util.Collection;

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

public class DefaultFeatureEvidenceComparator {

    /**
     * Use DefaultFeatureEvidenceComparator to know if two experimental features are equals.
     * @param experimentalFeature1
     * @param experimentalFeature2
     * @return true if the two experimental features are equal
     */
    public static boolean areEquals(FeatureEvidence experimentalFeature1, FeatureEvidence experimentalFeature2){
        if (experimentalFeature1 == null && experimentalFeature2 == null){
            return true;
        }
        else if (experimentalFeature1 == null || experimentalFeature2 == null){
            return false;
        }
        else {
            // first compares basic feature properties
            if (!DefaultFeatureBaseComparator.areEquals(experimentalFeature1, experimentalFeature2)){
                return false;
            }

            // then compare feature detection methods
            Collection<CvTerm> detMethod1 = experimentalFeature1.getDetectionMethods();
            Collection<CvTerm> detMethod2 = experimentalFeature2.getDetectionMethods();

            return ComparatorUtils.areCvTermsEqual(detMethod1, detMethod2);
        }
    }
}
