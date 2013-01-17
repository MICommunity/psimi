package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalFeature;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;

import java.util.Comparator;

/**
 * Basic ExperimentalFeature comparator.
 * It will first compare feature detection methods using AbstractCvTermComparator. If both feature detection methods are the same,
 * it will use a FeatureComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of an experimental feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class ExperimentalFeatureComparator implements Comparator<ExperimentalFeature>{

    protected FeatureComparator featureComparator;
    protected AbstractCvTermComparator cvTermComparator;

    /**
     * Creates a new ExperimentalFeatureComparator.
     * @param featureComparator : feature comparator required for comparing basic feature properties
     */
    public ExperimentalFeatureComparator(FeatureComparator featureComparator){
        if (featureComparator == null){
            throw new IllegalArgumentException("The Feature comparator is required to compare general feature properties. It cannot be null");
        }
        this.featureComparator = featureComparator;
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare feature detection methods . It cannot be null");
        }
        this.cvTermComparator = featureComparator.getCvTermComparator();
    }

    public FeatureComparator getFeatureComparator() {
        return featureComparator;
    }

    /**
     * It will first compare feature detection methods using AbstractCvTermComparator. If both feature detection methods are the same,
     * it will use a FeatureComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of an experimental feature.
     *
     * @param experimentalFeature1
     * @param experimentalFeature2
     * @return
     */
    public int compare(ExperimentalFeature experimentalFeature1, ExperimentalFeature experimentalFeature2) {
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
            // first compare feature detection methods
            CvTerm detMethod1 = experimentalFeature1.getDetectionMethod();
            CvTerm detMethod2 = experimentalFeature2.getDetectionMethod();

            int comp = cvTermComparator.compare(detMethod1, detMethod2);
            if (comp != 0){
                return comp;
            }

            // compares basic feature properties
            return featureComparator.compare(experimentalFeature1, experimentalFeature2);
        }
    }
}
