package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.ModelledFeature;

import java.util.Comparator;

/**
 * Basic ModelledFeature comparator.
 * It will use a AbstractFeatureBaseComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of a biological feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class ModelledFeatureComparator implements Comparator<ModelledFeature> {

    protected AbstractFeatureBaseComparator featureComparator;

    /**
     * Creates a new ModelledFeatureComparator.
     * @param featureComparator : feature comparator required for comparing basic feature properties
     */
    public ModelledFeatureComparator(AbstractFeatureBaseComparator featureComparator){
        if (featureComparator == null){
            throw new IllegalArgumentException("The Feature comparator is required to compare general feature properties. It cannot be null");
        }
        this.featureComparator = featureComparator;
    }

    public AbstractFeatureBaseComparator getFeatureComparator() {
        return featureComparator;
    }

    /**
     * It will use a AbstractFeatureBaseComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of a biological feature.
     *
     * @param biologicalFeature1
     * @param biologicalFeature2
     * @return
     */
    public int compare(ModelledFeature biologicalFeature1, ModelledFeature biologicalFeature2) {
        return featureComparator.compare(biologicalFeature1, biologicalFeature2);
    }
}
