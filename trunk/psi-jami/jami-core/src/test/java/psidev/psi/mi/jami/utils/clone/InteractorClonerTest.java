package psidev.psi.mi.jami.utils.clone;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultParticipantBaseComparator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Unit tester for InteractorCloner
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class InteractorClonerTest {

    @Test
    public void test_copy_basic_interactor_properties(){

        Interactor sourceInteractor = new DefaultInteractor("source interactor");
        sourceInteractor.setFullName("source interactor full");
        sourceInteractor.setOrganism(new DefaultOrganism(9606));
        sourceInteractor.getChecksums().add(new DefaultChecksum(new DefaultCvTerm("test method"), "xxxx"));
        sourceInteractor.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xx2"));
        sourceInteractor.getAliases().add(new DefaultAlias("test alias"));
        sourceInteractor.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));

        Interactor targetInteractor = new DefaultInteractor("target interactor");

        InteractorCloner.copyAndOverrideBasicInteractorProperties(sourceInteractor, targetInteractor);
        Assert.assertEquals("source interactor", targetInteractor.getShortName());
        Assert.assertEquals("source interactor full", targetInteractor.getFullName());
        Assert.assertEquals(1, targetInteractor.getXrefs().size());
        Assert.assertEquals(1, targetInteractor.getAnnotations().size());
        Assert.assertEquals(1, targetInteractor.getAliases().size());
        Assert.assertEquals(1, targetInteractor.getChecksums().size());
        Assert.assertTrue(sourceInteractor.getOrganism() == targetInteractor.getOrganism());
        Assert.assertTrue(sourceInteractor.getInteractorType() == targetInteractor.getInteractorType());
        Assert.assertTrue(sourceInteractor.getXrefs().iterator().next() == targetInteractor.getXrefs().iterator().next());
        Assert.assertTrue(sourceInteractor.getAliases().iterator().next() == targetInteractor.getAliases().iterator().next());
        Assert.assertTrue(sourceInteractor.getAnnotations().iterator().next() == targetInteractor.getAnnotations().iterator().next());
        Assert.assertTrue(sourceInteractor.getChecksums().iterator().next() == targetInteractor.getChecksums().iterator().next());
    }

    @Test
    public void test_copy_basic_complex_properties_ignore_participants(){

        Complex sourceInteractor = new DefaultComplex("source interactor");
        sourceInteractor.setFullName("source interactor full");
        sourceInteractor.setOrganism(new DefaultOrganism(9606));
        sourceInteractor.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xx2"));
        sourceInteractor.getAliases().add(new DefaultAlias("test alias"));
        sourceInteractor.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteractor.setSource(new DefaultSource("mint"));
        sourceInteractor.setInteractionType(new DefaultCvTerm("association"));
        sourceInteractor.setUpdatedDate(new Date());
        sourceInteractor.setCreatedDate(new Date(1));
        sourceInteractor.setRigid("xxxxx");
        sourceInteractor.getParticipants().add(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteractor.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteractor.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteractor.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteractor.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteractor.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));

        Complex targetInteractor = new DefaultComplex("target interactor");

        InteractorCloner.copyAndOverrideComplexProperties(sourceInteractor, targetInteractor, false, true);
        Assert.assertEquals("source interactor", targetInteractor.getShortName());
        Assert.assertEquals("source interactor full", targetInteractor.getFullName());
        Assert.assertEquals(1, targetInteractor.getXrefs().size());
        Assert.assertEquals(1, targetInteractor.getAnnotations().size());
        Assert.assertEquals(1, targetInteractor.getAliases().size());
        Assert.assertEquals(1, targetInteractor.getChecksums().size());
        Assert.assertTrue(sourceInteractor.getOrganism() == targetInteractor.getOrganism());
        Assert.assertTrue(sourceInteractor.getInteractorType() == targetInteractor.getInteractorType());
        Assert.assertTrue(sourceInteractor.getXrefs().iterator().next() == targetInteractor.getXrefs().iterator().next());
        Assert.assertTrue(sourceInteractor.getAliases().iterator().next() == targetInteractor.getAliases().iterator().next());
        Assert.assertTrue(sourceInteractor.getAnnotations().iterator().next() == targetInteractor.getAnnotations().iterator().next());
        Assert.assertTrue(sourceInteractor.getChecksums().iterator().next() == targetInteractor.getChecksums().iterator().next());
        Assert.assertEquals("xxxxx", targetInteractor.getRigid());
        Assert.assertEquals(0, targetInteractor.getParticipants().size());
        Assert.assertEquals(1, targetInteractor.getCooperativeEffects().size());
        Assert.assertEquals(1, targetInteractor.getIdentifiers().size());
        Assert.assertEquals(1, targetInteractor.getInteractionEvidences().size());
        Assert.assertEquals(1, targetInteractor.getModelledConfidences().size());
        Assert.assertEquals(1, targetInteractor.getModelledParameters().size());
        Assert.assertTrue(targetInteractor.getSource() == targetInteractor.getSource());
        Assert.assertTrue(targetInteractor.getCreatedDate() == targetInteractor.getCreatedDate());
        Assert.assertTrue(targetInteractor.getUpdatedDate() == targetInteractor.getUpdatedDate());
        Assert.assertTrue(targetInteractor.getInteractionType() == targetInteractor.getInteractionType());
        Assert.assertTrue(targetInteractor.getIdentifiers().iterator().next() == targetInteractor.getIdentifiers().iterator().next());
        Assert.assertTrue(targetInteractor.getCooperativeEffects().iterator().next() == targetInteractor.getCooperativeEffects().iterator().next());
        Assert.assertTrue(targetInteractor.getModelledConfidences().iterator().next() == targetInteractor.getModelledConfidences().iterator().next());
        Assert.assertTrue(targetInteractor.getModelledParameters().iterator().next() == targetInteractor.getModelledParameters().iterator().next());
        Assert.assertTrue(targetInteractor.getInteractionEvidences().iterator().next() == targetInteractor.getInteractionEvidences().iterator().next());
    }

    @Test
    public void test_copy_complex_participants(){

        Complex sourceInteractor = new DefaultComplex("source interactor");
        sourceInteractor.setFullName("source interactor full");
        sourceInteractor.setOrganism(new DefaultOrganism(9606));
        sourceInteractor.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xx2"));
        sourceInteractor.getAliases().add(new DefaultAlias("test alias"));
        sourceInteractor.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteractor.setSource(new DefaultSource("mint"));
        sourceInteractor.setInteractionType(new DefaultCvTerm("association"));
        sourceInteractor.setUpdatedDate(new Date());
        sourceInteractor.setCreatedDate(new Date(1));
        sourceInteractor.setRigid("xxxxx");
        sourceInteractor.addModelledParticipant(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteractor.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteractor.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteractor.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteractor.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteractor.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));

        Complex targetInteractor = new DefaultComplex("target interactor");

        InteractorCloner.copyAndOverrideComplexProperties(sourceInteractor, targetInteractor, false, false);

        Assert.assertEquals(1, targetInteractor.getParticipants().size());
        Assert.assertTrue(targetInteractor.getParticipants().iterator().next() == sourceInteractor.getParticipants().iterator().next());
        Assert.assertTrue(targetInteractor.getParticipants().iterator().next().getModelledInteraction() == sourceInteractor);
    }

    @Test
    public void test_copy_complex_properties_create_participants(){

        Complex sourceInteractor = new DefaultComplex("source interactor");
        sourceInteractor.setFullName("source interactor full");
        sourceInteractor.setOrganism(new DefaultOrganism(9606));
        sourceInteractor.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xx2"));
        sourceInteractor.getAliases().add(new DefaultAlias("test alias"));
        sourceInteractor.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteractor.setSource(new DefaultSource("mint"));
        sourceInteractor.setInteractionType(new DefaultCvTerm("association"));
        sourceInteractor.setUpdatedDate(new Date());
        sourceInteractor.setCreatedDate(new Date(1));
        sourceInteractor.setRigid("xxxxx");
        sourceInteractor.addModelledParticipant(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteractor.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteractor.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteractor.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteractor.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteractor.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));

        Complex targetInteractor = new DefaultComplex("target interactor");

        InteractorCloner.copyAndOverrideComplexProperties(sourceInteractor, targetInteractor, true, false);

        Assert.assertEquals("source interactor", targetInteractor.getShortName());
        Assert.assertEquals(1, targetInteractor.getParticipants().size());
        Assert.assertFalse(sourceInteractor.getParticipants().iterator().next() == targetInteractor.getParticipants().iterator().next());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(sourceInteractor.getParticipants().iterator().next(), targetInteractor.getParticipants().iterator().next()));
        Assert.assertTrue(targetInteractor.getParticipants().iterator().next().getModelledInteraction() == targetInteractor);
    }

    @Test
    public void test_copy_basic_interaction_properties_to_complex(){

        Interaction sourceInteraction = new DefaultInteraction("source interaction");
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xx2"));
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(new DefaultParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));

        Complex targetInteractor = new DefaultComplex("target interactor");

        InteractorCloner.copyAndOverrideBasicComplexPropertiesWithInteractionProperties(sourceInteraction, targetInteractor);
        Assert.assertEquals("source interaction", targetInteractor.getShortName());
        Assert.assertNull(targetInteractor.getFullName());
        Assert.assertEquals(1, targetInteractor.getXrefs().size());
        Assert.assertEquals(1, targetInteractor.getAnnotations().size());
        Assert.assertEquals(1, targetInteractor.getChecksums().size());
        Assert.assertNull(targetInteractor.getOrganism());
        Assert.assertEquals(CvTermUtils.createComplexInteractorType(), targetInteractor.getInteractorType());
        Assert.assertTrue(sourceInteraction.getXrefs().iterator().next() == targetInteractor.getXrefs().iterator().next());
        Assert.assertTrue(sourceInteraction.getAnnotations().iterator().next() == targetInteractor.getAnnotations().iterator().next());
        Assert.assertTrue(sourceInteraction.getChecksums().iterator().next() == targetInteractor.getChecksums().iterator().next());
        Assert.assertEquals("xxxxx", targetInteractor.getRigid());
        Assert.assertEquals(0, targetInteractor.getParticipants().size());
        Assert.assertEquals(0, targetInteractor.getCooperativeEffects().size());
        Assert.assertEquals(1, targetInteractor.getIdentifiers().size());
        Assert.assertEquals(0, targetInteractor.getInteractionEvidences().size());
        Assert.assertEquals(0, targetInteractor.getModelledConfidences().size());
        Assert.assertEquals(0, targetInteractor.getModelledParameters().size());
        Assert.assertNull(targetInteractor.getSource());
        Assert.assertTrue(targetInteractor.getCreatedDate() == targetInteractor.getCreatedDate());
        Assert.assertTrue(targetInteractor.getUpdatedDate() == targetInteractor.getUpdatedDate());
        Assert.assertTrue(targetInteractor.getInteractionType() == targetInteractor.getInteractionType());
        Assert.assertTrue(targetInteractor.getIdentifiers().iterator().next() == targetInteractor.getIdentifiers().iterator().next());
    }

    @Test
    public void test_copy_modelled_interaction_properties_to_complex(){

        ModelledInteraction sourceInteractor = new DefaultModelledInteraction("source interaction");
        sourceInteractor.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xx2"));
        sourceInteractor.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteractor.setSource(new DefaultSource("mint"));
        sourceInteractor.setInteractionType(new DefaultCvTerm("association"));
        sourceInteractor.setUpdatedDate(new Date());
        sourceInteractor.setCreatedDate(new Date(1));
        sourceInteractor.setRigid("xxxxx");
        sourceInteractor.getParticipants().add(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteractor.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteractor.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteractor.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteractor.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteractor.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));

        Complex targetInteractor = new DefaultComplex("target interactor");

        InteractorCloner.copyAndOverrideBasicComplexPropertiesWithModelledInteractionProperties(sourceInteractor, targetInteractor);
        Assert.assertEquals("source interaction", targetInteractor.getShortName());
        Assert.assertEquals(1, targetInteractor.getXrefs().size());
        Assert.assertEquals(1, targetInteractor.getAnnotations().size());
        Assert.assertEquals(1, targetInteractor.getChecksums().size());
        Assert.assertNull(targetInteractor.getOrganism());
        Assert.assertEquals(CvTermUtils.createComplexInteractorType(), targetInteractor.getInteractorType());
        Assert.assertTrue(sourceInteractor.getXrefs().iterator().next() == targetInteractor.getXrefs().iterator().next());
        Assert.assertTrue(sourceInteractor.getAnnotations().iterator().next() == targetInteractor.getAnnotations().iterator().next());
        Assert.assertTrue(sourceInteractor.getChecksums().iterator().next() == targetInteractor.getChecksums().iterator().next());
        Assert.assertEquals("xxxxx", targetInteractor.getRigid());
        Assert.assertEquals(0, targetInteractor.getParticipants().size());
        Assert.assertEquals(1, targetInteractor.getCooperativeEffects().size());
        Assert.assertEquals(1, targetInteractor.getIdentifiers().size());
        Assert.assertEquals(1, targetInteractor.getInteractionEvidences().size());
        Assert.assertEquals(1, targetInteractor.getModelledConfidences().size());
        Assert.assertEquals(1, targetInteractor.getModelledParameters().size());
        Assert.assertTrue(targetInteractor.getSource() == targetInteractor.getSource());
        Assert.assertTrue(targetInteractor.getCreatedDate() == targetInteractor.getCreatedDate());
        Assert.assertTrue(targetInteractor.getUpdatedDate() == targetInteractor.getUpdatedDate());
        Assert.assertTrue(targetInteractor.getInteractionType() == targetInteractor.getInteractionType());
        Assert.assertTrue(targetInteractor.getIdentifiers().iterator().next() == targetInteractor.getIdentifiers().iterator().next());
        Assert.assertTrue(targetInteractor.getCooperativeEffects().iterator().next() == targetInteractor.getCooperativeEffects().iterator().next());
        Assert.assertTrue(targetInteractor.getModelledConfidences().iterator().next() == targetInteractor.getModelledConfidences().iterator().next());
        Assert.assertTrue(targetInteractor.getModelledParameters().iterator().next() == targetInteractor.getModelledParameters().iterator().next());
        Assert.assertTrue(targetInteractor.getInteractionEvidences().iterator().next() == targetInteractor.getInteractionEvidences().iterator().next());
    }
}
