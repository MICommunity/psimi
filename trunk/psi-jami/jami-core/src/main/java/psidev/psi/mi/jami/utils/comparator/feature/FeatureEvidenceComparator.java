package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;

import java.util.Comparator;

/**
 * Basic FeatureEvidence comparator.
 * It will first compare feature detection methods using AbstractCvTermComparator. If both feature detection methods are the same,
 * it will use a FeatureBaseComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of an experimental feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class FeatureEvidenceComparator implements Comparator<FeatureEvidence>{

    protected FeatureBaseComparator featureComparator;
    protected AbstractCvTermComparator cvTermComparator;

    /**
     * Creates a new FeatureEvidenceComparator.
     * @param featureComparator : feature comparator required for comparing basic feature properties
     */
    public FeatureEvidenceComparator(FeatureBaseComparator featureComparator){
        if (featureComparator == null){
            throw new IllegalArgumentException("The Feature comparator is required to compare general feature properties. It cannot be null");
        }
        this.featureComparator = featureComparator;
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare feature detection methods . It cannot be null");
        }
        this.cvTermComparator = featureComparator.getCvTermComparator();
    }

    public FeatureBaseComparator getFeatureComparator() {
        return featureComparator;
    }

    /**
     * It will first use a FeatureBaseComparator to compare basic properties of a feature.
     * If the basic feature properties are the same, it will then compare feature detection methods using AbstractCvTermComparator.
     * This comparator will ignore all the other properties of an experimental feature.
     *
     * @param experimentalFeature1
     * @param experimentalFeature2
     * @return
     */
    public int compare(FeatureEvidence experimentalFeature1, FeatureEvidence experimentalFeature2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (experimentalFeature1 == null && experimentalFeature2 == null){
            return EQUAL;
        }
        else if (experimentalFeature1 == null){
            return AFTER;
        }
        else if (experimentalFeature2 == null){
            return BEFORE;
        }
        else {
            // first compares basic feature properties
            int comp = featureComparator.compare(experimentalFeature1, experimentalFeature2);
            if (comp != 0){
               return comp;
            }

            // then compare feature detection methods
            CvTerm detMethod1 = experimentalFeature1.getDetectionMethod();
            CvTerm detMethod2 = experimentalFeature2.getDetectionMethod();

           return cvTermComparator.compare(detMethod1, detMethod2);
        }
    }
}
