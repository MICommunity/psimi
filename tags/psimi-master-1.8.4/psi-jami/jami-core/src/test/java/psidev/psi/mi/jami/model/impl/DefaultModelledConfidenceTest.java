package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledConfidence;

/**
 * Unit tester for DefaultModelledConfidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultModelledConfidenceTest {

    @Test
    public void test_create_confidence_method_and_value() throws Exception {
        ModelledConfidence confidence = new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high");

        Assert.assertEquals(new DefaultCvTerm("author-score"), confidence.getType());
        Assert.assertEquals("high", confidence.getValue());
        Assert.assertNotNull(confidence.getPublications());
    }

    @Test
    public void test_create_confidence_with_unit() throws Exception {
        ModelledConfidence confidence = new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high");

        Assert.assertEquals(new DefaultCvTerm("author-score"), confidence.getType());
        Assert.assertEquals("high", confidence.getValue());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_confidence_no_type() throws Exception {
        ModelledConfidence confidence = new DefaultModelledConfidence(null, "high");
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_confidence_no_value() throws Exception {
        ModelledConfidence confidence = new DefaultModelledConfidence(new DefaultCvTerm("author-score"), null);
    }
}
