package psidev.psi.mi.jami.model.impl;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for DefaultAnnotation
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultAnnotationTest {

    @Test
    public void test_create_annotation_topic_and_value() throws Exception {
        Annotation annotation = new DefaultAnnotation(CvTermUtils.createComplexPhysicalProperties(), "Molecular mass = 154 kDa");

        Assert.assertEquals(CvTermUtils.createComplexPhysicalProperties(), annotation.getTopic());
        Assert.assertEquals("Molecular mass = 154 kDa", annotation.getValue());
    }

    @Test
    public void test_create_annotation_topic_only() throws Exception {
        Annotation annotation = new DefaultAnnotation(CvTermUtils.createComplexPhysicalProperties());

        Assert.assertEquals(CvTermUtils.createComplexPhysicalProperties(), annotation.getTopic());
        Assert.assertNull(annotation.getValue());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_create_annotation_no_topic() throws Exception {
        Annotation annotation = new DefaultAnnotation(null, "Molecular mass = 154 kDa");
    }
}
