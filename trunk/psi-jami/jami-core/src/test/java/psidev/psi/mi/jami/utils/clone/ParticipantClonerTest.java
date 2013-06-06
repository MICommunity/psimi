package psidev.psi.mi.jami.utils.clone;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.feature.DefaultFeatureBaseComparator;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Unit tester for ParticipantCloner
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class ParticipantClonerTest {

    @Test
    public void test_copy_basic_participant_properties(){

        Participant sourceParticipant = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());
        sourceParticipant.setStoichiometry(new DefaultStoichiometry(3));
        sourceParticipant.setCausalRelationship(new DefaultCausalRelationship(new DefaultCvTerm("decrease"), new DefaultParticipant(new DefaultProtein("p1"))));
        sourceParticipant.setBiologicalRole(new DefaultCvTerm("enzyme"));
        sourceParticipant.getAliases().add(new DefaultAlias("test alias"));
        sourceParticipant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceParticipant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test comment"), "comment"));
        ((Collection<Feature>)sourceParticipant.getFeatures()).add(new DefaultFeature("test", "test feature"));

        Participant targetParticipant = new DefaultParticipant(new DefaultProtein("p2"));

        ParticipantCloner.copyAndOverrideBasicParticipantProperties(sourceParticipant, targetParticipant, false);

        Assert.assertEquals(1, targetParticipant.getXrefs().size());
        Assert.assertEquals(1, targetParticipant.getAnnotations().size());
        Assert.assertEquals(1, targetParticipant.getAliases().size());
        Assert.assertEquals(1, targetParticipant.getFeatures().size());
        Assert.assertTrue(targetParticipant.getXrefs().iterator().next() == sourceParticipant.getXrefs().iterator().next());
        Assert.assertTrue(targetParticipant.getAliases().iterator().next() == sourceParticipant.getAliases().iterator().next());
        Assert.assertTrue(targetParticipant.getAnnotations().iterator().next() == sourceParticipant.getAnnotations().iterator().next());
        Assert.assertTrue(targetParticipant.getFeatures().iterator().next() == sourceParticipant.getFeatures().iterator().next());
        Assert.assertTrue(targetParticipant.getInteractor() == sourceParticipant.getInteractor());
        Assert.assertTrue(targetParticipant.getStoichiometry() == sourceParticipant.getStoichiometry());
        Assert.assertTrue(targetParticipant.getBiologicalRole() == sourceParticipant.getBiologicalRole());
        Assert.assertTrue(targetParticipant.getCausalRelationship() == sourceParticipant.getCausalRelationship());
    }

    @Test
    public void test_copy_basic_participant_properties_create_new_features(){

        Participant sourceParticipant = new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor());
        sourceParticipant.setStoichiometry(new DefaultStoichiometry(3));
        sourceParticipant.setCausalRelationship(new DefaultCausalRelationship(new DefaultCvTerm("decrease"), new DefaultParticipant(new DefaultProtein("p1"))));
        sourceParticipant.setBiologicalRole(new DefaultCvTerm("enzyme"));
        sourceParticipant.getAliases().add(new DefaultAlias("test alias"));
        sourceParticipant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceParticipant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test comment"), "comment"));
        ((Collection<Feature>)sourceParticipant.getFeatures()).add(new DefaultFeature("test", "test feature"));

        Participant targetParticipant = new DefaultParticipant(new DefaultProtein("p2"));

        ParticipantCloner.copyAndOverrideBasicParticipantProperties(sourceParticipant, targetParticipant, true);

        Assert.assertEquals(1, targetParticipant.getFeatures().size());
        Assert.assertFalse(targetParticipant.getFeatures().iterator().next() == sourceParticipant.getFeatures().iterator().next());
        Assert.assertTrue(DefaultFeatureBaseComparator.areEquals((Feature)targetParticipant.getFeatures().iterator().next(), (Feature)sourceParticipant.getFeatures().iterator().next()));
    }

    @Test
    public void test_copy_modelled_participant_properties(){

        ModelledParticipant sourceParticipant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        sourceParticipant.setStoichiometry(new DefaultStoichiometry(3));
        sourceParticipant.setCausalRelationship(new DefaultCausalRelationship(new DefaultCvTerm("decrease"), new DefaultParticipant(new DefaultProtein("p1"))));
        sourceParticipant.setBiologicalRole(new DefaultCvTerm("enzyme"));
        sourceParticipant.getAliases().add(new DefaultAlias("test alias"));
        sourceParticipant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceParticipant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test comment"), "comment"));
        sourceParticipant.addModelledFeature(new DefaultModelledFeature("test", "test feature"));
        sourceParticipant.setModelledInteraction(new DefaultModelledInteraction());

        ModelledParticipant targetParticipant = new DefaultModelledParticipant(new DefaultProtein("p2"));

        ParticipantCloner.copyAndOverrideModelledParticipantProperties(sourceParticipant, targetParticipant, false);

        Assert.assertEquals(1, targetParticipant.getXrefs().size());
        Assert.assertEquals(1, targetParticipant.getAnnotations().size());
        Assert.assertEquals(1, targetParticipant.getAliases().size());
        Assert.assertEquals(1, targetParticipant.getFeatures().size());
        Assert.assertNull(targetParticipant.getModelledInteraction());
        Assert.assertTrue(targetParticipant.getXrefs().iterator().next() == sourceParticipant.getXrefs().iterator().next());
        Assert.assertTrue(targetParticipant.getAliases().iterator().next() == sourceParticipant.getAliases().iterator().next());
        Assert.assertTrue(targetParticipant.getAnnotations().iterator().next() == sourceParticipant.getAnnotations().iterator().next());
        Assert.assertTrue(targetParticipant.getFeatures().iterator().next() == sourceParticipant.getFeatures().iterator().next());
        Assert.assertTrue(targetParticipant.getInteractor() == sourceParticipant.getInteractor());
        Assert.assertTrue(targetParticipant.getStoichiometry() == sourceParticipant.getStoichiometry());
        Assert.assertTrue(targetParticipant.getBiologicalRole() == sourceParticipant.getBiologicalRole());
        Assert.assertTrue(targetParticipant.getCausalRelationship() == sourceParticipant.getCausalRelationship());
        Assert.assertTrue(targetParticipant.getFeatures().iterator().next().getModelledParticipant() == sourceParticipant);
    }

    @Test
    public void test_copy_modelled_participant_properties_create_new_features(){

        ModelledParticipant sourceParticipant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        sourceParticipant.setStoichiometry(new DefaultStoichiometry(3));
        sourceParticipant.setCausalRelationship(new DefaultCausalRelationship(new DefaultCvTerm("decrease"), new DefaultParticipant(new DefaultProtein("p1"))));
        sourceParticipant.setBiologicalRole(new DefaultCvTerm("enzyme"));
        sourceParticipant.getAliases().add(new DefaultAlias("test alias"));
        sourceParticipant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceParticipant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test comment"), "comment"));
        sourceParticipant.addModelledFeature(new DefaultModelledFeature("test", "test feature"));
        sourceParticipant.setModelledInteraction(new DefaultModelledInteraction());

        ModelledParticipant targetParticipant = new DefaultModelledParticipant(new DefaultProtein("p2"));

        ParticipantCloner.copyAndOverrideModelledParticipantProperties(sourceParticipant, targetParticipant, true);

        Assert.assertEquals(1, targetParticipant.getFeatures().size());
        Assert.assertFalse(targetParticipant.getFeatures().iterator().next() == sourceParticipant.getFeatures().iterator().next());
        Assert.assertTrue(DefaultFeatureBaseComparator.areEquals(targetParticipant.getFeatures().iterator().next(), sourceParticipant.getFeatures().iterator().next()));
        Assert.assertTrue(targetParticipant.getFeatures().iterator().next().getModelledParticipant() == targetParticipant);
    }

    @Test
    public void test_copy_participant_evidence_properties(){

        ParticipantEvidence sourceParticipant = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        sourceParticipant.setStoichiometry(new DefaultStoichiometry(3));
        sourceParticipant.setCausalRelationship(new DefaultCausalRelationship(new DefaultCvTerm("decrease"), new DefaultParticipant(new DefaultProtein("p1"))));
        sourceParticipant.setBiologicalRole(new DefaultCvTerm("enzyme"));
        sourceParticipant.getAliases().add(new DefaultAlias("test alias"));
        sourceParticipant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceParticipant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test comment"), "comment"));
        sourceParticipant.addFeatureEvidence(new DefaultFeatureEvidence("test", "test feature"));
        sourceParticipant.setInteractionEvidence(new DefaultInteractionEvidence());
        sourceParticipant.setExperimentalRole(new DefaultCvTerm("bait"));
        sourceParticipant.setExpressedInOrganism(new DefaultOrganism(-1));
        sourceParticipant.getExperimentalPreparations().add(new DefaultCvTerm("test preparation"));
        sourceParticipant.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceParticipant.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceParticipant.getIdentificationMethods().add(new DefaultCvTerm("test method"));

        ParticipantEvidence targetParticipant = new DefaultParticipantEvidence(new DefaultProtein("p2"));

        ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(sourceParticipant, targetParticipant, false);

        Assert.assertEquals(1, targetParticipant.getXrefs().size());
        Assert.assertEquals(1, targetParticipant.getAnnotations().size());
        Assert.assertEquals(1, targetParticipant.getAliases().size());
        Assert.assertEquals(1, targetParticipant.getFeatures().size());
        Assert.assertEquals(1, targetParticipant.getParameters().size());
        Assert.assertEquals(1, targetParticipant.getConfidences().size());
        Assert.assertEquals(1, targetParticipant.getExperimentalPreparations().size());
        Assert.assertEquals(1, targetParticipant.getIdentificationMethods().size());
        Assert.assertNull(targetParticipant.getInteractionEvidence());
        Assert.assertTrue(targetParticipant.getXrefs().iterator().next() == sourceParticipant.getXrefs().iterator().next());
        Assert.assertTrue(targetParticipant.getAliases().iterator().next() == sourceParticipant.getAliases().iterator().next());
        Assert.assertTrue(targetParticipant.getAnnotations().iterator().next() == sourceParticipant.getAnnotations().iterator().next());
        Assert.assertTrue(targetParticipant.getFeatures().iterator().next() == sourceParticipant.getFeatures().iterator().next());
        Assert.assertTrue(targetParticipant.getInteractor() == sourceParticipant.getInteractor());
        Assert.assertTrue(targetParticipant.getStoichiometry() == sourceParticipant.getStoichiometry());
        Assert.assertTrue(targetParticipant.getBiologicalRole() == sourceParticipant.getBiologicalRole());
        Assert.assertTrue(targetParticipant.getCausalRelationship() == sourceParticipant.getCausalRelationship());
        Assert.assertTrue(targetParticipant.getFeatures().iterator().next().getParticipantEvidence() == sourceParticipant);
        Assert.assertTrue(targetParticipant.getExperimentalRole() == sourceParticipant.getExperimentalRole());
        Assert.assertTrue(targetParticipant.getExpressedInOrganism() == sourceParticipant.getExpressedInOrganism());
        Assert.assertTrue(targetParticipant.getExperimentalPreparations().iterator().next() == sourceParticipant.getExperimentalPreparations().iterator().next() );
        Assert.assertTrue(targetParticipant.getConfidences().iterator().next() == sourceParticipant.getConfidences().iterator().next() );
        Assert.assertTrue(targetParticipant.getParameters().iterator().next() == sourceParticipant.getParameters().iterator().next() );
        Assert.assertTrue(targetParticipant.getIdentificationMethods().iterator().next() == sourceParticipant.getIdentificationMethods().iterator().next() );

    }

    @Test
    public void test_copy_participant_evidence_properties_create_new_features(){

        ParticipantEvidence sourceParticipant = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        sourceParticipant.setStoichiometry(new DefaultStoichiometry(3));
        sourceParticipant.setCausalRelationship(new DefaultCausalRelationship(new DefaultCvTerm("decrease"), new DefaultParticipant(new DefaultProtein("p1"))));
        sourceParticipant.setBiologicalRole(new DefaultCvTerm("enzyme"));
        sourceParticipant.getAliases().add(new DefaultAlias("test alias"));
        sourceParticipant.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceParticipant.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("test comment"), "comment"));
        sourceParticipant.addFeatureEvidence(new DefaultFeatureEvidence("test", "test feature"));
        sourceParticipant.setInteractionEvidence(new DefaultInteractionEvidence());
        sourceParticipant.setExperimentalRole(new DefaultCvTerm("bait"));
        sourceParticipant.setExpressedInOrganism(new DefaultOrganism(-1));
        sourceParticipant.getExperimentalPreparations().add(new DefaultCvTerm("test preparation"));
        sourceParticipant.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceParticipant.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));

        ParticipantEvidence targetParticipant = new DefaultParticipantEvidence(new DefaultProtein("p2"));

        ParticipantCloner.copyAndOverrideParticipantEvidenceProperties(sourceParticipant, targetParticipant, true);

        Assert.assertEquals(1, targetParticipant.getFeatures().size());
        Assert.assertFalse(targetParticipant.getFeatures().iterator().next() == sourceParticipant.getFeatures().iterator().next());
        Assert.assertTrue(DefaultFeatureBaseComparator.areEquals(targetParticipant.getFeatures().iterator().next(), sourceParticipant.getFeatures().iterator().next()));
        Assert.assertTrue(targetParticipant.getFeatures().iterator().next().getParticipantEvidence() == targetParticipant);
    }
}
