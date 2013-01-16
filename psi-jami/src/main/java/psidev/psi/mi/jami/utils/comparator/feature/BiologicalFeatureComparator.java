package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.BiologicalFeature;

import java.util.Comparator;

/**
 * Basic BiologicalFeature comparator.
 * It will use a FeatureComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of a biological feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class BiologicalFeatureComparator implements Comparator<BiologicalFeature> {

    protected FeatureComparator featureComparator;

    /**
     * Creates a new BiologicalFeatureComparator.
     * @param featureComparator : feature comparator required for comparing basic feature properties
     */
    public BiologicalFeatureComparator(FeatureComparator featureComparator){
        if (featureComparator == null){
            throw new IllegalArgumentException("The Feature comparator is required to compare general feature properties. It cannot be null");
        }
        this.featureComparator = featureComparator;
    }

    public FeatureComparator getFeatureComparator() {
        return featureComparator;
    }

    /**
     * It will use a FeatureComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of a biological feature.
     *
     * @param biologicalFeature1
     * @param biologicalFeature2
     * @return
     */
    public int compare(BiologicalFeature biologicalFeature1, BiologicalFeature biologicalFeature2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (biologicalFeature1 == null && biologicalFeature2 == null){
            return EQUAL;
        }
        else if (biologicalFeature1 == null){
            return AFTER;
        }
        else if (biologicalFeature2 == null){
            return BEFORE;
        }
        else {

            return featureComparator.compare(biologicalFeature1, biologicalFeature2);
        }
    }
}
