package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import java.util.*;

/**
 * Unit tester for AnnotationUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class AnnotationUtilsTest {

    @Test
    public void test_annotation_null_does_not_have_topic(){

        Assert.assertFalse(AnnotationUtils.doesAnnotationHaveTopic(null, "MI:xxxx", "comment"));
        Assert.assertFalse(AnnotationUtils.doesAnnotationHaveTopic(new DefaultAnnotation(new DefaultCvTerm("comment")), null, null));
    }

    @Test
    public void test_retrieve_annotation_topic_with_identifier(){

        CvTerm type = CvTermUtils.createMICvTerm(Annotation.CAUTION, Annotation.CAUTION_MI);
        Annotation annotation = new DefaultAnnotation(type, "warning");

        Assert.assertTrue(AnnotationUtils.doesAnnotationHaveTopic(annotation, Annotation.CAUTION_MI, null));
        Assert.assertFalse(AnnotationUtils.doesAnnotationHaveTopic(annotation, Annotation.COMMENT_MI, null));
    }

    @Test
    public void test_retrieve_annotation_topic_with_name(){

        CvTerm type = CvTermUtils.createMICvTerm(Annotation.CAUTION, Annotation.CAUTION_MI);
        Annotation annotation = new DefaultAnnotation(type, "warning");

        Assert.assertTrue(AnnotationUtils.doesAnnotationHaveTopic(annotation, null, Annotation.CAUTION));
        Assert.assertFalse(AnnotationUtils.doesAnnotationHaveTopic(annotation, null, Annotation.COMMENT));
    }

    @Test
    public void test_retrieve_annotation_topic_with_identifier_ignore_name(){

        CvTerm type = CvTermUtils.createMICvTerm("comment", Annotation.CAUTION_MI);
        Annotation annotation = new DefaultAnnotation(type, "warning");

        Assert.assertTrue(AnnotationUtils.doesAnnotationHaveTopic(annotation, Annotation.CAUTION_MI, Annotation.CAUTION));
        Assert.assertFalse(AnnotationUtils.doesAnnotationHaveTopic(annotation, Annotation.COMMENT_MI, Annotation.COMMENT));
    }

    @Test
    public void test_retrieve_annotation_topic_with_name_no_identifier(){

        CvTerm type = new DefaultCvTerm("caution");
        Annotation annotation = new DefaultAnnotation(type, "warning");

        Assert.assertTrue(AnnotationUtils.doesAnnotationHaveTopic(annotation, Annotation.CAUTION_MI, Annotation.CAUTION));
        Assert.assertFalse(AnnotationUtils.doesAnnotationHaveTopic(annotation, Annotation.COMMENT_MI, Annotation.COMMENT));
    }

    @Test
    public void test_collect_annotation_empty_input(){

        Assert.assertTrue(AnnotationUtils.collectAllAnnotationsHavingTopic(Collections.EMPTY_LIST, Annotation.CAUTION_MI, Annotation.CAUTION).isEmpty());
    }

    @Test
    public void test_collect_annotations(){
        CvTerm topic_name_and_id = CvTermUtils.createMICvTerm(Annotation.CAUTION, Annotation.CAUTION_MI);
        CvTerm topic_id_wrong_name = CvTermUtils.createMICvTerm("comment", Annotation.CAUTION_MI);
        CvTerm topic_name_no_id = new DefaultCvTerm(Annotation.CAUTION);
        CvTerm topic2 = CvTermUtils.createMICvTerm(Annotation.COMMENT, Annotation.COMMENT_MI);
        Annotation annot1 = new DefaultAnnotation(topic_name_and_id, "test_caution 1");
        Annotation annot2 = new DefaultAnnotation(topic_id_wrong_name, "test_caution 2");
        Annotation annot3 = new DefaultAnnotation(topic_name_no_id, "test_caution 3");
        Annotation annot4 = new DefaultAnnotation(topic2, "test_comment 4");

        List<Annotation> annotations = Arrays.asList(annot1, annot2, annot3, annot4);

        Collection<Annotation> selection = AnnotationUtils.collectAllAnnotationsHavingTopic(annotations, Annotation.CAUTION_MI, Annotation.CAUTION);
        Assert.assertEquals(3, selection.size());
        Assert.assertFalse(selection.contains(annot4));

        Collection<Annotation> selection2 = AnnotationUtils.collectAllAnnotationsHavingTopic(annotations, Annotation.COMMENT_MI, Annotation.COMMENT);
        Assert.assertEquals(1, selection2.size());
        Assert.assertTrue(selection2.contains(annot4));
    }

    @Test
    public void test_retrieve_first_alias_with_type(){
        CvTerm topic_name_and_id = CvTermUtils.createMICvTerm(Annotation.CAUTION, Annotation.CAUTION_MI);
        CvTerm topic_id_wrong_name = CvTermUtils.createMICvTerm("comment", Annotation.CAUTION_MI);
        CvTerm topic_name_no_id = new DefaultCvTerm(Annotation.CAUTION);
        CvTerm topic2 = CvTermUtils.createMICvTerm(Annotation.COMMENT, Annotation.COMMENT_MI);
        Annotation annot1 = new DefaultAnnotation(topic_name_and_id, "test_caution 1");
        Annotation annot2 = new DefaultAnnotation(topic_id_wrong_name, "test_caution 2");
        Annotation annot3 = new DefaultAnnotation(topic_name_no_id, "test_caution 3");
        Annotation annot4 = new DefaultAnnotation(topic2, "test_comment 4");

        List<Annotation> annotations = Arrays.asList(annot1, annot2, annot3, annot4);

        Annotation selection = AnnotationUtils.collectFirstAnnotationWithTopic(annotations, Annotation.CAUTION_MI, Annotation.CAUTION);
        Assert.assertNotNull(selection);
        Assert.assertEquals(annot1, selection);

        Annotation selection2 = AnnotationUtils.collectFirstAnnotationWithTopic(annotations, Annotation.COMMENT_MI, Annotation.COMMENT);
        Assert.assertNotNull(selection2);
        Assert.assertEquals(annot4, selection2);
    }

    @Test
    public void test_remove_all_aliases_with_type(){
        CvTerm topic_name_and_id = CvTermUtils.createMICvTerm(Annotation.CAUTION, Annotation.CAUTION_MI);
        CvTerm topic_id_wrong_name = CvTermUtils.createMICvTerm("comment", Annotation.CAUTION_MI);
        CvTerm topic_name_no_id = new DefaultCvTerm(Annotation.CAUTION);
        CvTerm topic2 = CvTermUtils.createMICvTerm(Annotation.COMMENT, Annotation.COMMENT_MI);
        Annotation annot1 = new DefaultAnnotation(topic_name_and_id, "test_caution 1");
        Annotation annot2 = new DefaultAnnotation(topic_id_wrong_name, "test_caution 2");
        Annotation annot3 = new DefaultAnnotation(topic_name_no_id, "test_caution 3");
        Annotation annot4 = new DefaultAnnotation(topic2, "test_comment 4");

        List<Annotation> annotations = new ArrayList<Annotation>(Arrays.asList(annot1, annot2, annot3, annot4));

        AnnotationUtils.removeAllAnnotationsWithTopic(annotations, Annotation.CAUTION_MI, Annotation.CAUTION);
        Assert.assertEquals(1, annotations.size());
        Assert.assertTrue(annotations.contains(annot4));
    }
}
