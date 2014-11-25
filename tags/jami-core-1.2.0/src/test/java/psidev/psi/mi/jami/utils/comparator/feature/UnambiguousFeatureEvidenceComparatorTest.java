package psidev.psi.mi.jami.utils.comparator.feature;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.impl.DefaultFeatureEvidence;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for UnambiguousFeatureEvidenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/13</pre>
 */

public class UnambiguousFeatureEvidenceComparatorTest {

    UnambiguousFeatureEvidenceComparator comparator = new UnambiguousFeatureEvidenceComparator();

    @Test
    public void test_feature_null_after(){
        FeatureEvidence feature1 = null;
        FeatureEvidence feature2 = new DefaultFeatureEvidence("test", null);

        Assert.assertTrue(comparator.compare(feature1, feature2) > 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) < 0);

        Assert.assertFalse(UnambiguousFeatureEvidenceComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) != UnambiguousFeatureBaseComparator.hashCode(feature2));

    }

    @Test
    public void test_feature_different_detection_methods(){
        FeatureEvidence feature1 = new DefaultFeatureEvidence("test", null);
        feature1.getDetectionMethods().add(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        FeatureEvidence feature2 = new DefaultFeatureEvidence("test", null);
        feature2.getDetectionMethods().add(CvTermUtils.createMICvTerm("test2", "MI:xxx2"));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureEvidenceComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));

    }

    @Test
    public void test_feature_different_detection_methods2(){
        FeatureEvidence feature1 = new DefaultFeatureEvidence("test", null);
        feature1.getDetectionMethods().add(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        FeatureEvidence feature2 = new DefaultFeatureEvidence("test", null);
        feature2.getDetectionMethods().add(CvTermUtils.createMICvTerm("test2", "MI:xxx2"));
        feature2.getDetectionMethods().add(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureEvidenceComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_different_detection_methods3(){
        FeatureEvidence feature1 = new DefaultFeatureEvidence("test", null);
        FeatureEvidence feature2 = new DefaultFeatureEvidence("test", null);
        feature2.getDetectionMethods().add(CvTermUtils.createMICvTerm("test2", "MI:xxx2"));
        feature2.getDetectionMethods().add(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));

        Assert.assertTrue(comparator.compare(feature1, feature2) < 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) > 0);

        Assert.assertFalse(UnambiguousFeatureEvidenceComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }

    @Test
    public void test_feature_same_detection_methods2(){
        FeatureEvidence feature1 = new DefaultFeatureEvidence("test", null);
        feature1.getDetectionMethods().add(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));
        feature1.getDetectionMethods().add(CvTermUtils.createMICvTerm("test2", "MI:xxx2"));
        FeatureEvidence feature2 = new DefaultFeatureEvidence("test", null);
        feature2.getDetectionMethods().add(CvTermUtils.createMICvTerm("test2", "MI:xxx2"));
        feature2.getDetectionMethods().add(CvTermUtils.createMICvTerm("test1", "MI:xxx1"));

        Assert.assertTrue(comparator.compare(feature1, feature2) == 0);
        Assert.assertTrue(comparator.compare(feature2, feature1) == 0);

        Assert.assertTrue(UnambiguousFeatureEvidenceComparator.areEquals(feature1, feature2));
        Assert.assertTrue(UnambiguousFeatureBaseComparator.hashCode(feature1) == UnambiguousFeatureBaseComparator.hashCode(feature2));
    }
}
