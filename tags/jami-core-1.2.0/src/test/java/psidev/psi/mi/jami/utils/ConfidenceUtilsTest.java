package psidev.psi.mi.jami.utils;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultConfidence;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Unit tester for ConfidenceUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/06/13</pre>
 */

public class ConfidenceUtilsTest {

    @Test
    public void test_confidence_null_does_not_have_type(){

        Assert.assertFalse(ConfidenceUtils.doesConfidenceHaveType(null, "MI:xxxx", "author-score"));
        Assert.assertFalse(ConfidenceUtils.doesConfidenceHaveType(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"), null, null));
    }

    @Test
    public void test_retrieve_confidence_type_with_identifier(){

        CvTerm type = CvTermUtils.createMICvTerm(Confidence.AUTHOR_BASED_CONFIDENCE, Confidence.AUTHOR_BASED_CONFIDENCE_MI);
        Confidence confidence = new DefaultConfidence(type, "high");

        Assert.assertTrue(ConfidenceUtils.doesConfidenceHaveType(confidence, Confidence.AUTHOR_BASED_CONFIDENCE_MI, null));
        Assert.assertFalse(ConfidenceUtils.doesConfidenceHaveType(confidence, "MI:xxxx", null));
    }

    @Test
    public void test_retrieve_confidence_type_with_name(){

        CvTerm type = CvTermUtils.createMICvTerm(Confidence.AUTHOR_BASED_CONFIDENCE, Confidence.AUTHOR_BASED_CONFIDENCE_MI);
        Confidence confidence = new DefaultConfidence(type, "high");

        Assert.assertTrue(ConfidenceUtils.doesConfidenceHaveType(confidence, null, Confidence.AUTHOR_BASED_CONFIDENCE));
        Assert.assertFalse(ConfidenceUtils.doesConfidenceHaveType(confidence, null, "statistical score"));
    }

    @Test
    public void test_retrieve_confidence_type_with_identifier_ignore_name(){

        CvTerm type = CvTermUtils.createMICvTerm("statistical score", Confidence.AUTHOR_BASED_CONFIDENCE_MI);
        Confidence confidence = new DefaultConfidence(type, "high");

        Assert.assertTrue(ConfidenceUtils.doesConfidenceHaveType(confidence, Confidence.AUTHOR_BASED_CONFIDENCE_MI, Confidence.AUTHOR_BASED_CONFIDENCE));
        Assert.assertFalse(ConfidenceUtils.doesConfidenceHaveType(confidence, "MI:xxxx", "statistical score"));
    }

    @Test
    public void test_retrieve_confidence_type_with_name_no_identifier(){

        CvTerm type = new DefaultCvTerm(Confidence.AUTHOR_BASED_CONFIDENCE);
        Confidence confidence = new DefaultConfidence(type, "high");

        Assert.assertTrue(ConfidenceUtils.doesConfidenceHaveType(confidence, Confidence.AUTHOR_BASED_CONFIDENCE_MI, Confidence.AUTHOR_BASED_CONFIDENCE));
        Assert.assertFalse(ConfidenceUtils.doesConfidenceHaveType(confidence, "MI:xxxx", "statistical score"));
    }

    @Test
    public void test_collect_confidence_empty_input(){

        Assert.assertTrue(ConfidenceUtils.collectAllConfidencesHavingType(Collections.EMPTY_LIST, Confidence.AUTHOR_BASED_CONFIDENCE_MI, Confidence.AUTHOR_BASED_CONFIDENCE).isEmpty());
    }

    @Test
    public void test_collect_confidences(){
        CvTerm type_name_and_id = CvTermUtils.createMICvTerm(Confidence.AUTHOR_BASED_CONFIDENCE, Confidence.AUTHOR_BASED_CONFIDENCE_MI);
        CvTerm type_id_wrong_name = CvTermUtils.createMICvTerm("statistical score", Confidence.AUTHOR_BASED_CONFIDENCE_MI);
        CvTerm type_name_no_id = new DefaultCvTerm(Confidence.AUTHOR_BASED_CONFIDENCE);
        CvTerm type2 = CvTermUtils.createMICvTerm("statistical score", "MI:xxxx");
        Confidence conf1 = new DefaultConfidence(type_name_and_id, "1");
        Confidence conf2 = new DefaultConfidence(type_id_wrong_name, "2");
        Confidence conf3 = new DefaultConfidence(type_name_no_id, "3");
        Confidence conf4 = new DefaultConfidence(type2, "4");

        List<Confidence> confidences = Arrays.asList(conf1, conf2, conf3, conf4);

        Collection<Confidence> selection = ConfidenceUtils.collectAllConfidencesHavingType(confidences, Confidence.AUTHOR_BASED_CONFIDENCE_MI, Confidence.AUTHOR_BASED_CONFIDENCE);
        Assert.assertEquals(3, selection.size());
        Assert.assertFalse(selection.contains(conf4));

        Collection<Confidence> selection2 = ConfidenceUtils.collectAllConfidencesHavingType(confidences, "MI:xxxx", "statistical confidence");
        Assert.assertEquals(1, selection2.size());
        Assert.assertTrue(selection2.contains(conf4));
    }
}
