package psidev.psi.mi.jami.utils.comparator.feature;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.model.impl.DefaultModelledFeature;

/**
 * Unit tester for UnambiguousFeatureComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class UnambiguousFeatureComparatorTest {

    private UnambiguousFeatureComparator comparator = new UnambiguousFeatureComparator();

    @Test
    public void test_feature_null_after(){
        Feature feature1 = null;
        Feature feature2 = new DefaultFeatureEvidence("test", null);

        Assert.assertTrue(comparator.compare(feature1, feature2) > 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) < 0);

        Assert.assertFalse(UnambiguousFeatureComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));

    }

    @Test
    public void test_biological_features_before(){
        Feature feature1 = new DefaultModelledFeature("test", null, null);
        Feature feature2 = new DefaultFeatureEvidence("test", null);

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));

    }

    @Test
    public void test_same_biological_features(){
        Feature feature1 = new DefaultModelledFeature("test", null, null);
        Feature feature2 = new DefaultModelledFeature("test", null, null);

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));

    }

    @Test
    public void test_same_feature_evidences(){
        Feature feature1 = new DefaultFeatureEvidence("test", null, null);
        Feature feature2 = new DefaultFeatureEvidence("test", null, null);

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));

    }
}
