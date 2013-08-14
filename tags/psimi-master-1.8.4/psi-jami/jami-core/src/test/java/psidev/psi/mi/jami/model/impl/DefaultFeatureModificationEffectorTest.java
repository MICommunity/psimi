package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.AllostericEffectorType;
import psidev.psi.mi.jami.model.FeatureModificationEffector;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultModelledFeatureComparator;

/**
 * Unit tester for DefaultFeatureModificationEffector
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/13</pre>
 */

public class DefaultFeatureModificationEffectorTest {

    @Test
    public void test_create_feature_modification_effector(){

        FeatureModificationEffector effector = new DefaultFeatureModificationEffector(new DefaultModelledFeature("test", "test feature"));

        Assert.assertEquals(AllostericEffectorType.feature_modification, effector.getEffectorType());
        Assert.assertTrue(DefaultModelledFeatureComparator.areEquals(new DefaultModelledFeature("test", "test feature"), effector.getFeatureModification()));
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_feature_modification_effector_feature_null() throws Exception {
        FeatureModificationEffector effector = new DefaultFeatureModificationEffector(null);
    }
}
