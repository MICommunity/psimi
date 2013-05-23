package psidev.psi.mi.jami.utils.comparator.annotation;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.CvTermUtils;

/**
 * Unit tester for UnambiguousAnnotationComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class UnambiguousAnnotationComparatorTest {

    private UnambiguousAnnotationComparator comparator = new UnambiguousAnnotationComparator();

    @Test
    public void test_annotation_null_after() throws Exception {
        Annotation annotation1 = null;
        Annotation annotation2 = new DefaultAnnotation(CvTermUtils.createComplexPhysicalProperties(), "Molecular mass = 154 kDa");

        Assert.assertTrue(comparator.compare(annotation1, annotation2) > 0);
        Assert.assertTrue(comparator.compare(annotation2, annotation1) < 0);

        Assert.assertFalse(UnambiguousAnnotationComparator.areEquals(annotation1, annotation2));
        Assert.assertTrue(UnambiguousAnnotationComparator.hashCode(annotation1) != UnambiguousAnnotationComparator.hashCode(annotation2));
    }

    @Test
    public void test_topic_comparison() throws Exception {
        Annotation annotation1 = new DefaultAnnotation(new DefaultCvTerm("comment"), "Molecular mass = 154 kDa");
        Annotation annotation2 = new DefaultAnnotation(CvTermUtils.createComplexPhysicalProperties(), "Molecular mass = 154 kDa");

        Assert.assertTrue(comparator.compare(annotation1, annotation2) != 0);

        Assert.assertFalse(UnambiguousAnnotationComparator.areEquals(annotation1, annotation2));
        Assert.assertTrue(UnambiguousAnnotationComparator.hashCode(annotation1) != UnambiguousAnnotationComparator.hashCode(annotation2));
    }

    @Test
    public void test_annotation_value_comparison() throws Exception {
        Annotation annotation1 = new DefaultAnnotation(CvTermUtils.createComplexPhysicalProperties(), "Molecular mass = 170 kDa");
        Annotation annotation2 = new DefaultAnnotation(CvTermUtils.createComplexPhysicalProperties(), "Molecular mass = 154 kDa");

        Assert.assertTrue(comparator.compare(annotation1, annotation2) != 0);

        Assert.assertFalse(UnambiguousAnnotationComparator.areEquals(annotation1, annotation2));
        Assert.assertTrue(UnambiguousAnnotationComparator.hashCode(annotation1) != UnambiguousAnnotationComparator.hashCode(annotation2));
    }

    @Test
    public void test_annotation_value_case_insensitive() throws Exception {
        Annotation annotation1 = new DefaultAnnotation(CvTermUtils.createComplexPhysicalProperties(), "Molecular mass = 170 kDa");
        Annotation annotation2 = new DefaultAnnotation(CvTermUtils.createComplexPhysicalProperties(), "Molecular MASS = 170 kDa ");

        Assert.assertTrue(comparator.compare(annotation1, annotation2) == 0);

        Assert.assertTrue(UnambiguousAnnotationComparator.areEquals(annotation1, annotation2));
        Assert.assertTrue(UnambiguousAnnotationComparator.hashCode(annotation1) == UnambiguousAnnotationComparator.hashCode(annotation2));
    }
}
