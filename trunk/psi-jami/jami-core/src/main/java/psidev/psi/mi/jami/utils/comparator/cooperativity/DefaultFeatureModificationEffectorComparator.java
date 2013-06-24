package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.FeatureModificationEffector;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultModelledFeatureComparator;

/**
 * Default comparator for FeatureModificationEffector.
 *
 * It will use a DefaultModelledFeatureComparator to compare the feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultFeatureModificationEffectorComparator {

    /**
     * Use DefaultFeatureModificationEffectorComparator to know if two FeatureModificationEffector are equals.
     * @param featureModificationEffector1
     * @param featureModificationEffector2
     * @return true if the two FeatureModificationEffector are equal
     */
    public static boolean areEquals(FeatureModificationEffector featureModificationEffector1, FeatureModificationEffector featureModificationEffector2){
        if (featureModificationEffector1 == null && featureModificationEffector2 == null){
            return true;
        }
        else if (featureModificationEffector1 == null || featureModificationEffector2 == null){
            return false;
        }
        else {

            ModelledFeature feature1 = featureModificationEffector1.getFeatureModification();
            ModelledFeature feature2 = featureModificationEffector2.getFeatureModification();

            return DefaultModelledFeatureComparator.areEquals(feature1, feature2);
        }
    }
}
