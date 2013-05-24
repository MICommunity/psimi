package psidev.psi.mi.jami.utils.comparator.feature;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.impl.DefaultFeature;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

/**
 * Unit tester for UnambiguousFeatureBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class UnambiguousFeatureBaseComparatorTest {

    private UnambiguousFeatureBaseComparator comparator = new UnambiguousFeatureBaseComparator();

    @Test
    public void test_feature_null_after(){
        Feature feature1 = null;
        Feature feature2 = new DefaultFeature("test", null);

        Assert.assertTrue(comparator.compare(feature1, feature2) > 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) < 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));

    }

    @Test
    public void test_feature_different_names(){
        Feature feature1 = new DefaultFeature("test", null);
        Feature feature2 = new DefaultFeature("test2", null);

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_null_name_after(){
        Feature feature1 = new DefaultFeature();
        Feature feature2 = new DefaultFeature("test2", null);

        Assert.assertTrue(comparator.compare(feature1, feature2) > 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) < 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_identical_names(){
        Feature feature1 = new DefaultFeature("test", null);
        Feature feature2 = new DefaultFeature("test", null);

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_identical_names_case_insensitive(){
        Feature feature1 = new DefaultFeature("test ", null);
        Feature feature2 = new DefaultFeature("tEST", null);

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_name_different_types(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("sufficient to bind", "MI:xxx2"));

        Assert.assertTrue(comparator.compare(feature1, feature2) > 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) < 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_identical_types(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_type_different_interaction_effects(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("decreasing interaction", "MI:xxx4"));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_identical_interaction_effects(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_interaction_effect_different_interaction_dependency(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature1.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature2.setInteractionDependency(CvTermUtils.createMICvTerm("test", "MI:xxx6"));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_identical_interaction_dependency(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature1.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature2.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_interaction_dependency_different_interpro(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature1.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature1.setInterpro("INTERPRO-TEST");
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature2.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature2.setInterpro("INTERPRO-TEST2");

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_identical_interpro(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature1.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature1.setInterpro("INTERPRO-TEST");
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature2.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature2.setInterpro("INTERPRO-TEST");

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_interaction_dependency_different_identifiers(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature1.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature1.getIdentifiers().add(XrefUtils.createIdentityXref("test-database", "xxx1"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature2.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature2.getIdentifiers().add(XrefUtils.createIdentityXref("test-database", "xxx2"));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_one_identifier_not_equals(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature1.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature1.getIdentifiers().add(XrefUtils.createIdentityXref("test-database", "xxx1"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature2.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature2.getIdentifiers().add(XrefUtils.createIdentityXref("test-database", "xxx1"));
        feature2.getIdentifiers().add(XrefUtils.createIdentityXref("test-database2", "xxx2"));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_with_one_identifier_list_empty(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature1.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature2.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature2.getIdentifiers().add(XrefUtils.createIdentityXref("test-database", "xxx1"));
        feature2.getIdentifiers().add(XrefUtils.createIdentityXref("test-database2", "xxx2"));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_identifiers(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature1.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature1.getIdentifiers().add(XrefUtils.createIdentityXref("test-database", "xxx1"));
        feature1.getIdentifiers().add(XrefUtils.createIdentityXref("test-database2", "xxx2"));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInteractionEffect(CvTermUtils.createMICvTerm("increasing interaction", "MI:xxx3"));
        feature2.setInteractionDependency(CvTermUtils.createMICvTerm("resulting-ptm", "MI:xxx5"));
        feature2.getIdentifiers().add(XrefUtils.createIdentityXref("test-database", "xxx1"));
        feature2.getIdentifiers().add(XrefUtils.createIdentityXref("test-database2", "xxx2"));

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_interpro_different_ranges(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInterpro("INTERPRO-TEST");
        feature1.getRanges().add(RangeUtils.createCertainRange(1));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInterpro("INTERPRO-TEST");
        feature2.getRanges().add(RangeUtils.createCertainRange(3));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_interpro_different_ranges2(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInterpro("INTERPRO-TEST");
        feature1.getRanges().add(RangeUtils.createCertainRange(1));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInterpro("INTERPRO-TEST");
        feature2.getRanges().add(RangeUtils.createCertainRange(1));
        feature2.getRanges().add(RangeUtils.createCertainRange(6));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_ranges(){
        Feature feature1 = new DefaultFeature("test", null, CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature1.setInterpro("INTERPRO-TEST");
        feature1.getRanges().add(RangeUtils.createCertainRange(6));
        feature1.getRanges().add(RangeUtils.createCertainRange(1));
        Feature feature2 = new DefaultFeature("test", " test feature", CvTermUtils.createMICvTerm("binding site", "MI:xxxx"));
        feature2.setInterpro("INTERPRO-TEST");
        feature2.getRanges().add(RangeUtils.createCertainRange(1));
        feature2.getRanges().add(RangeUtils.createCertainRange(6));

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureBaseComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }
}
