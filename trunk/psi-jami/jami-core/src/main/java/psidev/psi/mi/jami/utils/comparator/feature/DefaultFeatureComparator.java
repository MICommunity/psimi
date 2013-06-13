package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

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

public class DefaultFeatureComparator extends FeatureComparator {

    private static DefaultFeatureComparator defaultFeatureComparator;

    /**
     * Creates a DefaultFeatureComparator. It will use a DefaultFeatureBaseComparator to compare basic feature properties
     */
    public DefaultFeatureComparator() {
        super(new DefaultFeatureBaseComparator(), new DefaultCvTermComparator());
    }

    @Override
    public DefaultFeatureBaseComparator getFeatureBaseComparator() {
        return (DefaultFeatureBaseComparator) this.featureBaseComparator;
    }

    @Override
    /**
     * Biological features come first and then experimental features.
     * - It uses DefaultModelledFeatureComparator to compare biological features
     * - It uses DefaultFeatureEvidenceComparator to compare experimental features
     * - It uses DefaultFeatureBaseComparator to compare basic feature properties
     *
     */
    public int compare(Feature feature1, Feature feature2) {
        return super.compare(feature1, feature2);
    }

    /**
     * Use DefaultFeatureComparator to know if two features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two features are equal
     */
    public static boolean areEquals(Feature feature1, Feature feature2){
        if (defaultFeatureComparator == null){
            defaultFeatureComparator = new DefaultFeatureComparator();
        }

        return defaultFeatureComparator.compare(feature1, feature2) == 0;
    }
}
