package psidev.psi.mi.jami.utils.comparator.cooperativity;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.FeatureModificationEffector;
import psidev.psi.mi.jami.model.impl.DefaultFeatureModificationEffector;
import psidev.psi.mi.jami.model.impl.DefaultModelledFeature;

/**
 * Unit tester for UnambiguousFeatureModificationEffectorComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class UnambiguousFeatureModificationEffectorComparatorTest {

    private UnambiguousFeatureModificationEffectorComparator comparator = new UnambiguousFeatureModificationEffectorComparator();

    @Test
    public void test_feature_effector_null_after() throws Exception {
        FeatureModificationEffector mol1 = null;
        FeatureModificationEffector mol2 = new DefaultFeatureModificationEffector(new DefaultModelledFeature("test", "test feature"));

        Assert.assertTrue(comparator.compare(mol1, mol2) > 0);
        Assert.assertTrue(comparator.compare(mol2, mol1) < 0);

        Assert.assertFalse(UnambiguousFeatureModificationEffectorComparator.areEquals(mol1, mol2));
        Assert.assertTrue(UnambiguousFeatureModificationEffectorComparator.hashCode(mol1) != UnambiguousFeatureModificationEffectorComparator.hashCode(mol2));
    }

    @Test
    public void test_different_feature_effector() throws Exception {
        FeatureModificationEffector mol1 = new DefaultFeatureModificationEffector(new DefaultModelledFeature("test1", "test feature 1"));
        FeatureModificationEffector mol2 = new DefaultFeatureModificationEffector(new DefaultModelledFeature("test2", "test feature 2"));

        Assert.assertTrue(comparator.compare(mol1, mol2) < 0);
        Assert.assertTrue(comparator.compare(mol2, mol1) > 0);

        Assert.assertFalse(UnambiguousFeatureModificationEffectorComparator.areEquals(mol1, mol2));
        Assert.assertTrue(UnambiguousFeatureModificationEffectorComparator.hashCode(mol1) != UnambiguousFeatureModificationEffectorComparator.hashCode(mol2));
    }

    @Test
    public void test_same_feature_effector() throws Exception {
        FeatureModificationEffector mol1 = new DefaultFeatureModificationEffector(new DefaultModelledFeature("test1", "test feature 1"));
        FeatureModificationEffector mol2 = new DefaultFeatureModificationEffector(new DefaultModelledFeature("test1", "test feature 1"));

        Assert.assertTrue(comparator.compare(mol1, mol2) == 0);
        Assert.assertTrue(comparator.compare(mol2, mol1) == 0);

        Assert.assertTrue(UnambiguousFeatureModificationEffectorComparator.areEquals(mol1, mol2));
        Assert.assertTrue(UnambiguousFeatureModificationEffectorComparator.hashCode(mol1) == UnambiguousFeatureModificationEffectorComparator.hashCode(mol2));
    }
}
