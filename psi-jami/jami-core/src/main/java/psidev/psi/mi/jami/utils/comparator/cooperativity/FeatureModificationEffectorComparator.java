package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.FeatureModificationEffector;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.utils.comparator.feature.ModelledFeatureComparator;

import java.util.Comparator;

/**
 * Basic comparator for FeatureModificationEffector.
 *
 * It will use a ModelledFeatureComparator to compare the feature.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class FeatureModificationEffectorComparator implements Comparator<FeatureModificationEffector> {

    private ModelledFeatureComparator featureComparator;

    public FeatureModificationEffectorComparator(ModelledFeatureComparator featureComparator){

        if (featureComparator == null){
            throw new IllegalArgumentException("The featureComparator is required to compare the features of featureModificationEffectors");
        }
        this.featureComparator = featureComparator;
    }

    public ModelledFeatureComparator getFeatureComparator() {
        return featureComparator;
    }

    public int compare(FeatureModificationEffector featureModificationEffector1, FeatureModificationEffector featureModificationEffector2) {

        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (featureModificationEffector1 == null && featureModificationEffector2 == null){
            return EQUAL;
        }
        else if (featureModificationEffector1 == null){
            return AFTER;
        }
        else if (featureModificationEffector2 == null){
            return BEFORE;
        }
        else {

            ModelledFeature feature1 = featureModificationEffector1.getFeatureModification();
            ModelledFeature feature2 = featureModificationEffector2.getFeatureModification();

            return featureComparator.compare(feature1, feature2);
        }
    }
}
