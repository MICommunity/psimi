package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.cv.CvTermsCollectionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic FeatureEvidence comparator.
 * It will first compare feature detection methods using AbstractCvTermComparator. If both feature detection methods are the same,
 * it will use a AbstractFeatureBaseComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of an experimental feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class FeatureEvidenceComparator implements Comparator<FeatureEvidence>{

    private Comparator<Feature> featureComparator;
    private CollectionComparator<CvTerm> cvTermCollectionComparators;

    /**
     * Creates a new FeatureEvidenceComparator.
     * @param featureComparator : feature comparator required for comparing basic feature properties
     */
    public FeatureEvidenceComparator(Comparator<Feature> featureComparator, Comparator<CvTerm> cvTermComparator){
        if (featureComparator == null){
            throw new IllegalArgumentException("The Feature comparator is required to compare general feature properties. It cannot be null");
        }
        this.featureComparator = featureComparator;
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The Cv comparator is required to compare general feature properties. It cannot be null");
        }
        this.cvTermCollectionComparators = new CvTermsCollectionComparator(cvTermComparator);
    }

    public FeatureEvidenceComparator(Comparator<Feature> featureComparator, CollectionComparator<CvTerm> cvTermComparator){
        if (featureComparator == null){
            throw new IllegalArgumentException("The Feature comparator is required to compare general feature properties. It cannot be null");
        }
        this.featureComparator = featureComparator;
        if (cvTermComparator == null){
            throw new IllegalArgumentException("The Cv comparator is required to compare general feature properties. It cannot be null");
        }
        this.cvTermCollectionComparators = cvTermComparator;
    }

    public Comparator<Feature> getFeatureComparator() {
        return featureComparator;
    }

    public CollectionComparator<CvTerm> getCvTermCollectionComparators() {
        return cvTermCollectionComparators;
    }

    /**
     * It will first use a AbstractFeatureBaseComparator to compare basic properties of a feature.
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

        if (experimentalFeature1 == experimentalFeature2){
            return 0;
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
            Collection<CvTerm> detMethod1 = experimentalFeature1.getDetectionMethods();
            Collection<CvTerm> detMethod2 = experimentalFeature2.getDetectionMethods();

           return cvTermCollectionComparators.compare(detMethod1, detMethod2);
        }
    }
}
