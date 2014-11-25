package psidev.psi.mi.jami.utils.comparator.cooperativity;

import psidev.psi.mi.jami.model.FeatureModificationEffector;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousFeatureBaseComparator;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousModelledFeaturecomparator;

/**
 * Unambiguous comparator for FeatureModificationEffector.
 *
 * It will use a UnambiguousModelledFeatureComparator to compare the feature.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousFeatureModificationEffectorComparator extends FeatureModificationEffectorComparator {

    private static UnambiguousFeatureModificationEffectorComparator unambiguousFeatureModificationEffectorComparator;

    public UnambiguousFeatureModificationEffectorComparator() {
        super(new UnambiguousModelledFeaturecomparator());
    }

    @Override
    public UnambiguousModelledFeaturecomparator getFeatureComparator() {
        return (UnambiguousModelledFeaturecomparator) super.getFeatureComparator();
    }

    /**
     * It will use a UnambiguousModelledFeaturecomparator to compare the feature.
     * @param effector1
     * @param effector2
     * @return
     */
    public int compare(FeatureModificationEffector effector1, FeatureModificationEffector effector2) {
        return super.compare(effector1, effector2);
    }

    /**
     * Use UnambiguousFeatureModificationEffectorComparator to know if two FeatureModificationEffector are equals.
     * @param effector1
     * @param effector2
     * @return true if the two FeatureModificationEffector are equal
     */
    public static boolean areEquals(FeatureModificationEffector effector1, FeatureModificationEffector effector2){
        if (unambiguousFeatureModificationEffectorComparator == null){
            unambiguousFeatureModificationEffectorComparator = new UnambiguousFeatureModificationEffectorComparator();
        }

        return unambiguousFeatureModificationEffectorComparator.compare(effector1, effector2) == 0;
    }

    /**
     *
     * @param effector
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(FeatureModificationEffector effector){
        if (unambiguousFeatureModificationEffectorComparator == null){
            unambiguousFeatureModificationEffectorComparator = new UnambiguousFeatureModificationEffectorComparator();
        }

        if (effector == null){
            return 0;
        }

        int hashcode = 31;
        ModelledFeature feature = effector.getFeatureModification();
        hashcode = 31*hashcode + UnambiguousFeatureBaseComparator.hashCode(feature);
        return hashcode;
    }
}
