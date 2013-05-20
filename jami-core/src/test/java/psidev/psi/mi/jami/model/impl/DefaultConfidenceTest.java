package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Confidence;

/**
 * Unit tester for DefaultConfidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultConfidenceTest {

    @Test
    public void test_create_confidence_method_and_value() throws Exception {
        Confidence confidence = new DefaultConfidence(new DefaultCvTerm("author-score"), "high");

        Assert.assertEquals(new DefaultCvTerm("author-score"), confidence.getType());
        Assert.assertEquals("high", confidence.getValue());
        Assert.assertNull(confidence.getUnit());
    }

    @Test
    public void test_create_confidence_with_unit() throws Exception {
        Confidence confidence = new DefaultConfidence(new DefaultCvTerm("author-score"), "high", new DefaultCvTerm("percent"));

        Assert.assertEquals(new DefaultCvTerm("author-score"), confidence.getType());
        Assert.assertEquals("high", confidence.getValue());
        Assert.assertNotNull(confidence.getUnit());
        Assert.assertEquals(new DefaultCvTerm("percent"), confidence.getUnit());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_confidence_no_type() throws Exception {
        Confidence confidence = new DefaultConfidence(null, "high");
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_confidence_no_value() throws Exception {
        Confidence confidence = new DefaultConfidence(new DefaultCvTerm("author-score"), null);
    }
}
