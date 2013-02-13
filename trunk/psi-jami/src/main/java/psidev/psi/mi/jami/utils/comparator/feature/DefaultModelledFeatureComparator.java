package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.ModelledFeature;

/**
 * Default ModelledFeature comparator.
 * It will use a DefaultFeatureBaseComparator to compare basic properties of a feature.
 *
 * This comparator will ignore all the other properties of a biological feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultModelledFeatureComparator extends ModelledFeatureComparator {

    private static DefaultModelledFeatureComparator defaultBiologicalFeatureComparator;

    /**
     * Creates a new DefaultModelledFeatureComparator. It will use a DefaultFeatureBaseComparator to compare basic feature properties
     */
    public DefaultModelledFeatureComparator() {
        super(new DefaultFeatureBaseComparator());
    }

    @Override
    public DefaultFeatureBaseComparator getFeatureComparator() {
        return (DefaultFeatureBaseComparator) this.featureComparator;
    }

    @Override
    /**
     * It will use a DefaultFeatureBaseComparator to compare basic properties of a feature.
     *
     * This comparator will ignore all the other properties of a biological feature.
     */
    public int compare(ModelledFeature biologicalFeature1, ModelledFeature biologicalFeature2) {
        return super.compare(biologicalFeature1, biologicalFeature2);
    }

    /**
     * Use DefaultModelledFeatureComparator to know if two biological features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two biological features are equal
     */
    public static boolean areEquals(ModelledFeature feature1, ModelledFeature feature2){
        if (defaultBiologicalFeatureComparator == null){
            defaultBiologicalFeatureComparator = new DefaultModelledFeatureComparator();
        }

        return defaultBiologicalFeatureComparator.compare(feature1, feature2) == 0;
    }
}
