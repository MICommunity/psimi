package psidev.psi.mi.jami.utils.comparator.confidence;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.impl.DefaultConfidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

/**
 * Unit tester for UnambiguousConfidenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class UnambiguousConfidenceComparatorTest {

    private UnambiguousConfidenceComparator comparator = new UnambiguousConfidenceComparator();

    @Test
    public void test_confidence_null_after() throws Exception {
        Confidence confidence1 = null;
        Confidence confidence2 = new DefaultConfidence(new DefaultCvTerm("author-score"), "high");

        Assert.assertTrue(comparator.compare(confidence1, confidence2) > 0);
        Assert.assertTrue(comparator.compare(confidence2, confidence1) < 0);

        Assert.assertFalse(UnambiguousConfidenceComparator.areEquals(confidence1, confidence2));
        Assert.assertTrue(UnambiguousConfidenceComparator.hashCode(confidence1) != UnambiguousConfidenceComparator.hashCode(confidence2));
    }

    @Test
    public void test_confidence_method_comparison() throws Exception {
        Confidence confidence1 = new DefaultConfidence(new DefaultCvTerm("mi-score"), "high");
        Confidence confidence2 = new DefaultConfidence(new DefaultCvTerm("author-score"), "high");

        Assert.assertTrue(comparator.compare(confidence1, confidence2) > 0);
        Assert.assertTrue(comparator.compare(confidence2, confidence1) < 0);

        Assert.assertFalse(UnambiguousConfidenceComparator.areEquals(confidence1, confidence2));
        Assert.assertTrue(UnambiguousConfidenceComparator.hashCode(confidence1) != UnambiguousConfidenceComparator.hashCode(confidence2));
    }

    @Test
    public void test_confidence_value_comparison() throws Exception {
        Confidence confidence1 = new DefaultConfidence(new DefaultCvTerm("author-score"), "high");
        Confidence confidence2 = new DefaultConfidence(new DefaultCvTerm("author-score"), "high");

        Assert.assertTrue(comparator.compare(confidence1, confidence2) == 0);
        Assert.assertTrue(comparator.compare(confidence2, confidence1) == 0);

        Assert.assertTrue(UnambiguousConfidenceComparator.areEquals(confidence1, confidence2));
        Assert.assertTrue(UnambiguousConfidenceComparator.hashCode(confidence1) == UnambiguousConfidenceComparator.hashCode(confidence2));
    }

    @Test
    public void test_confidence_value_case_sensitive() throws Exception {
        Confidence confidence1 = new DefaultConfidence(new DefaultCvTerm("author-score"), "HIGH ");
        Confidence confidence2 = new DefaultConfidence(new DefaultCvTerm("author-score"), "high");

        Assert.assertTrue(comparator.compare(confidence1, confidence2) != 0);
        Assert.assertTrue(comparator.compare(confidence2, confidence1) != 0);

        Assert.assertFalse(UnambiguousConfidenceComparator.areEquals(confidence1, confidence2));
        Assert.assertTrue(UnambiguousConfidenceComparator.hashCode(confidence1) != UnambiguousConfidenceComparator.hashCode(confidence2));
    }
}
