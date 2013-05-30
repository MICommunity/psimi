package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.FeatureModificationEffector;
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

public class DefaultFeatureModificationEffectorComparator extends FeatureModificationEffectorComparator{

    private static DefaultFeatureModificationEffectorComparator defaultFeatureModificationEffectorComparator;

    public DefaultFeatureModificationEffectorComparator() {
        super(new DefaultModelledFeatureComparator());
    }

    @Override
    public DefaultModelledFeatureComparator getFeatureComparator() {
        return (DefaultModelledFeatureComparator) super.getFeatureComparator();
    }

    /**
     * It will use a DefaultModelledFeatureComparator to compare the feature.
     * @param effector1
     * @param effector2
     * @return
     */
    public int compare(FeatureModificationEffector effector1, FeatureModificationEffector effector2) {
        return super.compare(effector1, effector2);
    }

    /**
     * Use DefaultFeatureModificationEffectorComparator to know if two FeatureModificationEffector are equals.
     * @param effector1
     * @param effector2
     * @return true if the two FeatureModificationEffector are equal
     */
    public static boolean areEquals(FeatureModificationEffector effector1, FeatureModificationEffector effector2){
        if (defaultFeatureModificationEffectorComparator == null){
            defaultFeatureModificationEffectorComparator = new DefaultFeatureModificationEffectorComparator();
        }

        return defaultFeatureModificationEffectorComparator.compare(effector1, effector2) == 0;
    }
}
