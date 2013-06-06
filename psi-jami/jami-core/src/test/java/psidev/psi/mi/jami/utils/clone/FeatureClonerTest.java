package psidev.psi.mi.jami.utils.clone;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for FeatureCloner
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class FeatureClonerTest {

    @Test
    public void test_copy_basic_properties_defaultFeature(){

        ModelledFeature sourceFeature = new DefaultModelledFeature("source feature", "test full name");
        sourceFeature.setModelledParticipant(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceFeature.setInteractionEffect(new DefaultCvTerm("test effect"));
        sourceFeature.setInteractionDependency(new DefaultCvTerm("test dependency"));
        sourceFeature.setInterpro("INTERPRO-xxx");
        sourceFeature.setType(new DefaultCvTerm("binding site"));
        sourceFeature.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceFeature.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceFeature.getLinkedModelledFeatures().add(new DefaultModelledFeature());
        sourceFeature.getRanges().add(new DefaultRange(new DefaultPosition(1), new DefaultPosition(4)));

        ModelledFeature targetFeature = new DefaultModelledFeature("target feature", null);
        targetFeature.setInterpro("INTERPRO-xxx2");

        FeatureCloner.copyAndOverrideFeaturesProperties(sourceFeature, targetFeature);
        Assert.assertEquals("source feature", targetFeature.getShortName());
        Assert.assertEquals("test full name", targetFeature.getFullName());
        Assert.assertEquals("INTERPRO-xxx", targetFeature.getInterpro());
        Assert.assertEquals(1, targetFeature.getIdentifiers().size());
        Assert.assertEquals(1, targetFeature.getAnnotations().size());
        Assert.assertEquals(1, targetFeature.getXrefs().size());
        Assert.assertEquals(1, targetFeature.getRanges().size());
        Assert.assertEquals(0, targetFeature.getLinkedModelledFeatures().size());
        Assert.assertNull(targetFeature.getModelledParticipant());
        Assert.assertTrue(targetFeature.getType() == sourceFeature.getType());
        Assert.assertTrue(targetFeature.getInteractionDependency() == sourceFeature.getInteractionDependency());
        Assert.assertTrue(targetFeature.getInteractionEffect() == sourceFeature.getInteractionEffect());
        Assert.assertTrue(targetFeature.getXrefs().iterator().next() == sourceFeature.getXrefs().iterator().next());
        Assert.assertTrue(targetFeature.getAnnotations().iterator().next() == sourceFeature.getAnnotations().iterator().next());
        Assert.assertTrue(targetFeature.getRanges().iterator().next() == sourceFeature.getRanges().iterator().next());
    }

    @Test
    public void test_copy_basic_properties_modelledFeature(){

        ModelledFeature sourceFeature = new DefaultModelledFeature("source feature", "test full name");
        sourceFeature.setModelledParticipant(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceFeature.setInteractionEffect(new DefaultCvTerm("test effect"));
        sourceFeature.setInteractionDependency(new DefaultCvTerm("test dependency"));
        sourceFeature.setInterpro("INTERPRO-xxx");
        sourceFeature.setType(new DefaultCvTerm("binding site"));
        sourceFeature.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceFeature.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceFeature.getLinkedModelledFeatures().add(new DefaultModelledFeature());
        sourceFeature.getRanges().add(new DefaultRange(new DefaultPosition(1), new DefaultPosition(4)));

        ModelledFeature targetFeature = new DefaultModelledFeature("target feature", null);
        targetFeature.setInterpro("INTERPRO-xxx2");

        FeatureCloner.copyAndOverrideModelledFeaturesProperties(sourceFeature, targetFeature);
        Assert.assertEquals("source feature", targetFeature.getShortName());
        Assert.assertEquals("test full name", targetFeature.getFullName());
        Assert.assertEquals("INTERPRO-xxx", targetFeature.getInterpro());
        Assert.assertEquals(1, targetFeature.getIdentifiers().size());
        Assert.assertEquals(1, targetFeature.getAnnotations().size());
        Assert.assertEquals(1, targetFeature.getXrefs().size());
        Assert.assertEquals(1, targetFeature.getRanges().size());
        Assert.assertEquals(1, targetFeature.getLinkedModelledFeatures().size());
        Assert.assertNull(targetFeature.getModelledParticipant());
        Assert.assertTrue(targetFeature.getType() == sourceFeature.getType());
        Assert.assertTrue(targetFeature.getInteractionDependency() == sourceFeature.getInteractionDependency());
        Assert.assertTrue(targetFeature.getInteractionEffect() == sourceFeature.getInteractionEffect());
        Assert.assertTrue(targetFeature.getXrefs().iterator().next() == sourceFeature.getXrefs().iterator().next());
        Assert.assertTrue(targetFeature.getAnnotations().iterator().next() == sourceFeature.getAnnotations().iterator().next());
        Assert.assertTrue(targetFeature.getRanges().iterator().next() == sourceFeature.getRanges().iterator().next());
        Assert.assertTrue(targetFeature.getLinkedModelledFeatures().iterator().next() == sourceFeature.getLinkedModelledFeatures().iterator().next());
    }

    @Test
    public void test_copy_basic_properties_FeatureEvidence(){

        FeatureEvidence sourceFeature = new DefaultFeatureEvidence("source feature", "test full name");
        sourceFeature.setParticipantEvidence(new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor()));
        sourceFeature.setInteractionEffect(new DefaultCvTerm("test effect"));
        sourceFeature.setInteractionDependency(new DefaultCvTerm("test dependency"));
        sourceFeature.setInterpro("INTERPRO-xxx");
        sourceFeature.setType(new DefaultCvTerm("binding site"));
        sourceFeature.getDetectionMethods().add(new DefaultCvTerm("test method"));
        sourceFeature.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceFeature.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceFeature.getLinkedFeatureEvidences().add(new DefaultFeatureEvidence());
        sourceFeature.getRanges().add(new DefaultRange(new DefaultPosition(1), new DefaultPosition(4)));

        FeatureEvidence targetFeature = new DefaultFeatureEvidence("target feature", null);
        targetFeature.setInterpro("INTERPRO-xxx2");

        FeatureCloner.copyAndOverrideFeatureEvidenceProperties(sourceFeature, targetFeature);
        Assert.assertEquals("source feature", targetFeature.getShortName());
        Assert.assertEquals("test full name", targetFeature.getFullName());
        Assert.assertEquals("INTERPRO-xxx", targetFeature.getInterpro());
        Assert.assertEquals(1, targetFeature.getIdentifiers().size());
        Assert.assertEquals(1, targetFeature.getAnnotations().size());
        Assert.assertEquals(1, targetFeature.getXrefs().size());
        Assert.assertEquals(1, targetFeature.getRanges().size());
        Assert.assertEquals(1, targetFeature.getDetectionMethods().size());
        Assert.assertEquals(1, targetFeature.getLinkedFeatureEvidences().size());
        Assert.assertNull(targetFeature.getParticipantEvidence());
        Assert.assertTrue(targetFeature.getType() == sourceFeature.getType());
        Assert.assertTrue(targetFeature.getInteractionDependency() == sourceFeature.getInteractionDependency());
        Assert.assertTrue(targetFeature.getInteractionEffect() == sourceFeature.getInteractionEffect());
        Assert.assertTrue(targetFeature.getXrefs().iterator().next() == sourceFeature.getXrefs().iterator().next());
        Assert.assertTrue(targetFeature.getAnnotations().iterator().next() == sourceFeature.getAnnotations().iterator().next());
        Assert.assertTrue(targetFeature.getRanges().iterator().next() == sourceFeature.getRanges().iterator().next());
        Assert.assertTrue(targetFeature.getLinkedFeatureEvidences().iterator().next() == sourceFeature.getLinkedFeatureEvidences().iterator().next());
        Assert.assertTrue(targetFeature.getDetectionMethods().iterator().next() == sourceFeature.getDetectionMethods().iterator().next());

    }
}
