package psidev.psi.mi.jami.utils.clone;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.ExperimentUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.comparator.participant.DefaultParticipantBaseComparator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Unit tester for InteractionCloner
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/06/13</pre>
 */

public class InteractionClonerTest {

    @Test
    public void test_copy_basic_interaction_properties_ignore_participants(){

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.setSource(new DefaultSource("mint"));
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        sourceInteraction.getParticipants().add(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteraction.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceInteraction.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteraction.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));
        ModelledInteraction targetInteraction = new DefaultModelledInteraction("target interaction");
        targetInteraction.setRigid("xxxx1");

        InteractionCloner.copyAndOverrideBasicInteractionProperties(sourceInteraction, targetInteraction, false, true);

        Assert.assertEquals("source interaction", targetInteraction.getShortName());
        Assert.assertEquals("xxxxx", targetInteraction.getRigid());
        Assert.assertEquals(0, targetInteraction.getParticipants().size());
        Assert.assertEquals(0, targetInteraction.getCooperativeEffects().size());
        Assert.assertEquals(1, targetInteraction.getChecksums().size());
        Assert.assertEquals(1, targetInteraction.getXrefs().size());
        Assert.assertEquals(1, targetInteraction.getAnnotations().size());
        Assert.assertEquals(1, targetInteraction.getIdentifiers().size());
        Assert.assertEquals(0, targetInteraction.getInteractionEvidences().size());
        Assert.assertEquals(0, targetInteraction.getModelledConfidences().size());
        Assert.assertEquals(0, targetInteraction.getModelledParameters().size());
        Assert.assertNull(targetInteraction.getSource());
        Assert.assertTrue(sourceInteraction.getCreatedDate() == targetInteraction.getCreatedDate());
        Assert.assertTrue(sourceInteraction.getUpdatedDate() == targetInteraction.getUpdatedDate());
        Assert.assertTrue(sourceInteraction.getInteractionType() == targetInteraction.getInteractionType());
        Assert.assertTrue(sourceInteraction.getXrefs().iterator().next() == targetInteraction.getXrefs().iterator().next());
        Assert.assertTrue(sourceInteraction.getAnnotations().iterator().next() == targetInteraction.getAnnotations().iterator().next());
        Assert.assertTrue(sourceInteraction.getChecksums().iterator().next() == targetInteraction.getChecksums().iterator().next());
        Assert.assertTrue(sourceInteraction.getIdentifiers().iterator().next() == targetInteraction.getIdentifiers().iterator().next());
    }

    @Test
    public void test_copy_basic_interaction__participants(){

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.setSource(new DefaultSource("mint"));
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        sourceInteraction.getParticipants().add(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteraction.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceInteraction.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteraction.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));
        ModelledInteraction targetInteraction = new DefaultModelledInteraction("target interaction");
        targetInteraction.setRigid("xxxx1");

        InteractionCloner.copyAndOverrideBasicInteractionProperties(sourceInteraction, targetInteraction, false, false);

        Assert.assertEquals(1, targetInteraction.getParticipants().size());
        Assert.assertTrue(sourceInteraction.getParticipants().iterator().next() == targetInteraction.getParticipants().iterator().next());
    }

    @Test
    public void test_copy_basic_interaction_properties_create_participants(){

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.setSource(new DefaultSource("mint"));
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        sourceInteraction.getParticipants().add(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteraction.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceInteraction.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteraction.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));
        ModelledInteraction targetInteraction = new DefaultModelledInteraction("target interaction");
        targetInteraction.setRigid("xxxx1");

        InteractionCloner.copyAndOverrideBasicInteractionProperties(sourceInteraction, targetInteraction, true, false);

        Assert.assertEquals("source interaction", targetInteraction.getShortName());
        Assert.assertEquals(1, targetInteraction.getParticipants().size());
        Assert.assertFalse(sourceInteraction.getParticipants().iterator().next() == targetInteraction.getParticipants().iterator().next());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(sourceInteraction.getParticipants().iterator().next(), targetInteraction.getParticipants().iterator().next(), true));
    }

    @Test
    public void test_copy_modelled_interaction_properties_ignore_participants(){

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.setSource(new DefaultSource("mint"));
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        sourceInteraction.getParticipants().add(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteraction.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceInteraction.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteraction.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));
        ModelledInteraction targetInteraction = new DefaultModelledInteraction("target interaction");
        targetInteraction.setRigid("xxxx1");

        InteractionCloner.copyAndOverrideModelledInteractionProperties(sourceInteraction, targetInteraction, false, true);

        Assert.assertEquals("source interaction", targetInteraction.getShortName());
        Assert.assertEquals("xxxxx", targetInteraction.getRigid());
        Assert.assertEquals(0, targetInteraction.getParticipants().size());
        Assert.assertEquals(1, targetInteraction.getCooperativeEffects().size());
        Assert.assertEquals(1, targetInteraction.getChecksums().size());
        Assert.assertEquals(1, targetInteraction.getXrefs().size());
        Assert.assertEquals(1, targetInteraction.getAnnotations().size());
        Assert.assertEquals(1, targetInteraction.getIdentifiers().size());
        Assert.assertEquals(1, targetInteraction.getInteractionEvidences().size());
        Assert.assertEquals(1, targetInteraction.getModelledConfidences().size());
        Assert.assertEquals(1, targetInteraction.getModelledParameters().size());
        Assert.assertTrue(targetInteraction.getSource() == sourceInteraction.getSource());
        Assert.assertTrue(sourceInteraction.getCreatedDate() == targetInteraction.getCreatedDate());
        Assert.assertTrue(sourceInteraction.getUpdatedDate() == targetInteraction.getUpdatedDate());
        Assert.assertTrue(sourceInteraction.getInteractionType() == targetInteraction.getInteractionType());
        Assert.assertTrue(sourceInteraction.getXrefs().iterator().next() == targetInteraction.getXrefs().iterator().next());
        Assert.assertTrue(sourceInteraction.getAnnotations().iterator().next() == targetInteraction.getAnnotations().iterator().next());
        Assert.assertTrue(sourceInteraction.getChecksums().iterator().next() == targetInteraction.getChecksums().iterator().next());
        Assert.assertTrue(sourceInteraction.getIdentifiers().iterator().next() == targetInteraction.getIdentifiers().iterator().next());
        Assert.assertTrue(sourceInteraction.getCooperativeEffects().iterator().next() == targetInteraction.getCooperativeEffects().iterator().next());
        Assert.assertTrue(sourceInteraction.getModelledConfidences().iterator().next() == targetInteraction.getModelledConfidences().iterator().next());
        Assert.assertTrue(sourceInteraction.getModelledParameters().iterator().next() == targetInteraction.getModelledParameters().iterator().next());
        Assert.assertTrue(sourceInteraction.getInteractionEvidences().iterator().next() == targetInteraction.getInteractionEvidences().iterator().next());
    }

    @Test
    public void test_copy_modelled_interaction_participants(){

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.setSource(new DefaultSource("mint"));
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        sourceInteraction.addModelledParticipant(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteraction.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceInteraction.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteraction.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));
        ModelledInteraction targetInteraction = new DefaultModelledInteraction("target interaction");
        targetInteraction.setRigid("xxxx1");

        InteractionCloner.copyAndOverrideModelledInteractionProperties(sourceInteraction, targetInteraction, false, false);

        Assert.assertEquals(1, targetInteraction.getParticipants().size());
        Assert.assertTrue(sourceInteraction.getParticipants().iterator().next() == targetInteraction.getParticipants().iterator().next());
        Assert.assertTrue(targetInteraction.getParticipants().iterator().next().getModelledInteraction() == sourceInteraction);
    }

    @Test
    public void test_copy_modelled_interaction_properties_create_participants(){

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.setSource(new DefaultSource("mint"));
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        sourceInteraction.addModelledParticipant(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getCooperativeEffects().add(new DefaultCooperativeEffect(new DefaultCvTerm("test outcome")));
        sourceInteraction.getInteractionEvidences().add(new DefaultInteractionEvidence());
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceInteraction.getModelledConfidences().add(new DefaultModelledConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteraction.getModelledParameters().add(new DefaultModelledParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));
        ModelledInteraction targetInteraction = new DefaultModelledInteraction("target interaction");
        targetInteraction.setRigid("xxxx1");

        InteractionCloner.copyAndOverrideModelledInteractionProperties(sourceInteraction, targetInteraction, true, false);

        Assert.assertEquals("source interaction", targetInteraction.getShortName());
        Assert.assertEquals(1, targetInteraction.getParticipants().size());
        Assert.assertFalse(sourceInteraction.getParticipants().iterator().next() == targetInteraction.getParticipants().iterator().next());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(sourceInteraction.getParticipants().iterator().next(), targetInteraction.getParticipants().iterator().next(), true));
        Assert.assertTrue(targetInteraction.getParticipants().iterator().next().getModelledInteraction() == targetInteraction);
    }

    @Test
    public void test_copy_interaction_evidences_properties_ignore_participants(){

        InteractionEvidence sourceInteraction = new DefaultInteractionEvidence("source interaction");
        sourceInteraction.setNegative(true);
        sourceInteraction.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        sourceInteraction.setInferred(false);
        sourceInteraction.setAvailability("copyright");
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        sourceInteraction.getParticipants().add(new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getVariableParameterValues().add(new DefaultVariableParameterValueSet());
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceInteraction.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteraction.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));
        InteractionEvidence targetInteraction = new DefaultInteractionEvidence("target interaction");
        targetInteraction.setRigid("xxxx1");
        targetInteraction.setInferred(true);

        InteractionCloner.copyAndOverrideInteractionEvidenceProperties(sourceInteraction, targetInteraction, false, true);

        Assert.assertEquals("source interaction", targetInteraction.getShortName());
        Assert.assertEquals("xxxxx", targetInteraction.getRigid());
        Assert.assertEquals("copyright", targetInteraction.getAvailability());
        Assert.assertTrue(targetInteraction.isNegative());
        Assert.assertFalse(targetInteraction.isInferred());
        Assert.assertTrue(targetInteraction.getExperiment() == sourceInteraction.getExperiment());
        Assert.assertTrue(targetInteraction.getExperiment().getInteractionEvidences().isEmpty());
        Assert.assertEquals(0, targetInteraction.getParticipants().size());
        Assert.assertEquals(1, targetInteraction.getChecksums().size());
        Assert.assertEquals(1, targetInteraction.getXrefs().size());
        Assert.assertEquals(1, targetInteraction.getAnnotations().size());
        Assert.assertEquals(1, targetInteraction.getIdentifiers().size());
        Assert.assertEquals(1, targetInteraction.getConfidences().size());
        Assert.assertEquals(1, targetInteraction.getParameters().size());
        Assert.assertTrue(sourceInteraction.getCreatedDate() == targetInteraction.getCreatedDate());
        Assert.assertTrue(sourceInteraction.getUpdatedDate() == targetInteraction.getUpdatedDate());
        Assert.assertTrue(sourceInteraction.getInteractionType() == targetInteraction.getInteractionType());
        Assert.assertTrue(sourceInteraction.getXrefs().iterator().next() == targetInteraction.getXrefs().iterator().next());
        Assert.assertTrue(sourceInteraction.getAnnotations().iterator().next() == targetInteraction.getAnnotations().iterator().next());
        Assert.assertTrue(sourceInteraction.getChecksums().iterator().next() == targetInteraction.getChecksums().iterator().next());
        Assert.assertTrue(sourceInteraction.getIdentifiers().iterator().next() == targetInteraction.getIdentifiers().iterator().next());
        Assert.assertTrue(sourceInteraction.getConfidences().iterator().next() == targetInteraction.getConfidences().iterator().next());
        Assert.assertTrue(sourceInteraction.getParameters().iterator().next() == targetInteraction.getParameters().iterator().next());
    }

    @Test
    public void test_copy_interaction_evidences_participants(){

        InteractionEvidence sourceInteraction = new DefaultInteractionEvidence("source interaction");
        sourceInteraction.setNegative(true);
        sourceInteraction.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        sourceInteraction.setInferred(false);
        sourceInteraction.setAvailability("copyright");
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        sourceInteraction.addParticipantEvidence(new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getVariableParameterValues().add(new DefaultVariableParameterValueSet());
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceInteraction.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteraction.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));
        InteractionEvidence targetInteraction = new DefaultInteractionEvidence("target interaction");
        targetInteraction.setRigid("xxxx1");
        targetInteraction.setInferred(true);

        InteractionCloner.copyAndOverrideInteractionEvidenceProperties(sourceInteraction, targetInteraction, false, false);

        Assert.assertEquals(1, targetInteraction.getParticipants().size());
        Assert.assertTrue(sourceInteraction.getParticipants().iterator().next() == targetInteraction.getParticipants().iterator().next());
        Assert.assertTrue(targetInteraction.getParticipants().iterator().next().getInteractionEvidence()== sourceInteraction);
    }

    @Test
    public void test_copy_interaction_evidences_properties_create_participants(){

        InteractionEvidence sourceInteraction = new DefaultInteractionEvidence("source interaction");
        sourceInteraction.setNegative(true);
        sourceInteraction.setExperiment(ExperimentUtils.createUnknownBasicExperiment());
        sourceInteraction.setInferred(false);
        sourceInteraction.setAvailability("copyright");
        sourceInteraction.setInteractionType(new DefaultCvTerm("association"));
        sourceInteraction.setUpdatedDate(new Date());
        sourceInteraction.setCreatedDate(new Date(1));
        sourceInteraction.setRigid("xxxxx");
        sourceInteraction.addParticipantEvidence(new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor()));
        sourceInteraction.getVariableParameterValues().add(new DefaultVariableParameterValueSet());
        sourceInteraction.getAnnotations().add(new DefaultAnnotation(new DefaultCvTerm("comment"), "test comment"));
        sourceInteraction.getXrefs().add(new DefaultXref(new DefaultCvTerm("test database"), "xxxx"));
        sourceInteraction.getConfidences().add(new DefaultConfidence(new DefaultCvTerm("author-score"), "high"));
        sourceInteraction.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        sourceInteraction.getIdentifiers().add(new DefaultXref(new DefaultCvTerm("test database"), "xx1"));
        InteractionEvidence targetInteraction = new DefaultInteractionEvidence("target interaction");
        targetInteraction.setRigid("xxxx1");
        targetInteraction.setInferred(true);

        InteractionCloner.copyAndOverrideInteractionEvidenceProperties(sourceInteraction, targetInteraction, true, false);

        Assert.assertEquals("source interaction", targetInteraction.getShortName());
        Assert.assertEquals(1, targetInteraction.getParticipants().size());
        Assert.assertFalse(sourceInteraction.getParticipants().iterator().next() == targetInteraction.getParticipants().iterator().next());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(sourceInteraction.getParticipants().iterator().next(), targetInteraction.getParticipants().iterator().next(), true));
        Assert.assertTrue(targetInteraction.getParticipants().iterator().next().getInteractionEvidence() == targetInteraction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_too_many_participant_evidences_to_copy_in_binary(){

        InteractionEvidence sourceInteraction = new DefaultInteractionEvidence("source interaction");
        sourceInteraction.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        sourceInteraction.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("p2")));
        sourceInteraction.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("p3")));

        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence();

        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, false, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_too_many_participant_evidences_to_copy_in_binary_self(){

        InteractionEvidence sourceInteraction = new DefaultInteractionEvidence("source interaction");
        sourceInteraction.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("p1")));
        sourceInteraction.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("p2")));

        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence();

        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, false, true);
    }

    @Test
    public void test_copy_participant_evidences(){

        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("p1"));
        ParticipantEvidence p2 = new DefaultParticipantEvidence(new DefaultProtein("p2"));

        InteractionEvidence sourceInteraction = new DefaultInteractionEvidence("source interaction");
        sourceInteraction.addParticipantEvidence(p1);
        sourceInteraction.addParticipantEvidence(p2);

        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence();

        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, false, false);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertTrue(p2 == binary.getParticipantB());
        Assert.assertTrue(p1.getInteractionEvidence() == sourceInteraction);
        Assert.assertTrue(p2.getInteractionEvidence() == sourceInteraction);

        // no participants
        sourceInteraction.getParticipants().clear();
        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, false, false);

        Assert.assertNull(binary.getParticipantA());
        Assert.assertNull(binary.getParticipantB());

        // one participant
        sourceInteraction.addParticipantEvidence(p1);
        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, false, false);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertNull(binary.getParticipantB());
        Assert.assertTrue(p1.getInteractionEvidence() == sourceInteraction);
    }

    @Test
    public void test_copy_participant_evidences_self(){

        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("p1"));

        InteractionEvidence sourceInteraction = new DefaultInteractionEvidence("source interaction");
        sourceInteraction.addParticipantEvidence(p1);

        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence();

        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, false, true);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertTrue(p1.getInteractionEvidence() == sourceInteraction);
        Assert.assertTrue(binary.getParticipantB().getInteractionEvidence() == binary);

        p1.setStoichiometry(3);
        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, false, true);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertFalse(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertEquals(new DefaultStoichiometry(0), binary.getParticipantB().getStoichiometry());
        Assert.assertTrue(p1.getInteractionEvidence() == sourceInteraction);
        Assert.assertTrue(binary.getParticipantB().getInteractionEvidence() == binary);
    }

    @Test
    public void test_create_participant_evidences(){

        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("p1"));
        ParticipantEvidence p2 = new DefaultParticipantEvidence(new DefaultProtein("p2"));

        InteractionEvidence sourceInteraction = new DefaultInteractionEvidence("source interaction");
        sourceInteraction.addParticipantEvidence(p1);
        sourceInteraction.addParticipantEvidence(p2);

        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence();

        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, true, false);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertFalse(p2 == binary.getParticipantB());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p2, binary.getParticipantB(), true));
        Assert.assertTrue(binary.getParticipantA().getInteractionEvidence() == binary);
        Assert.assertTrue(binary.getParticipantB().getInteractionEvidence() == binary);

        // no participants
        sourceInteraction.getParticipants().clear();
        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, true, false);

        Assert.assertNull(binary.getParticipantA());
        Assert.assertNull(binary.getParticipantB());

        // one participant
        sourceInteraction.addParticipantEvidence(p1);
        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, true, false);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertNull(binary.getParticipantB());
        Assert.assertTrue(binary.getParticipantA().getInteractionEvidence() == binary);
    }

    @Test
    public void test_create_participant_evidences_self(){

        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("p1"));

        InteractionEvidence sourceInteraction = new DefaultInteractionEvidence("source interaction");
        sourceInteraction.addParticipantEvidence(p1);

        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence();

        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, true, true);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertTrue(p1.getInteractionEvidence() == sourceInteraction);
        Assert.assertTrue(binary.getParticipantB().getInteractionEvidence() == binary);

        p1.setStoichiometry(3);
        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(sourceInteraction, binary, true, true);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertFalse(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertEquals(new DefaultStoichiometry(0), binary.getParticipantB().getStoichiometry());
        Assert.assertTrue(p1.getInteractionEvidence() == sourceInteraction);
        Assert.assertTrue(binary.getParticipantB().getInteractionEvidence() == binary);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_too_many_modelled_participants_to_copy_in_binary(){

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("p1")));
        sourceInteraction.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("p2")));
        sourceInteraction.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("p3")));

        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction();

        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, false, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_too_many_modelled_participants_to_copy_in_binary_self(){

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("p1")));
        sourceInteraction.addModelledParticipant(new DefaultModelledParticipant(new DefaultProtein("p2")));

        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction();

        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, false, true);
    }

    @Test
    public void test_copy_modelled_participants(){

        ModelledParticipant p1 = new DefaultModelledParticipant(new DefaultProtein("p1"));
        ModelledParticipant p2 = new DefaultModelledParticipant(new DefaultProtein("p2"));

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.addModelledParticipant(p1);
        sourceInteraction.addModelledParticipant(p2);

        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction();

        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, false, false);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertTrue(p2 == binary.getParticipantB());
        Assert.assertTrue(p1.getModelledInteraction() == sourceInteraction);
        Assert.assertTrue(p2.getModelledInteraction() == sourceInteraction);

        // no participants
        sourceInteraction.getParticipants().clear();
        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, false, false);

        Assert.assertNull(binary.getParticipantA());
        Assert.assertNull(binary.getParticipantB());

        // one participant
        sourceInteraction.addModelledParticipant(p1);
        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, false, false);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertNull(binary.getParticipantB());
        Assert.assertTrue(p1.getModelledInteraction() == sourceInteraction);
    }

    @Test
    public void test_copy_modelled_participants_self(){

        ModelledParticipant p1 = new DefaultModelledParticipant(new DefaultProtein("p1"));

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.addModelledParticipant(p1);

        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction();

        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, false, true);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertTrue(p1.getModelledInteraction() == sourceInteraction);
        Assert.assertTrue(binary.getParticipantB().getModelledInteraction() == binary);

        p1.setStoichiometry(3);
        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, false, true);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertFalse(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertEquals(new DefaultStoichiometry(0), binary.getParticipantB().getStoichiometry());
        Assert.assertTrue(p1.getModelledInteraction() == sourceInteraction);
        Assert.assertTrue(binary.getParticipantB().getModelledInteraction() == binary);
    }

    @Test
    public void test_create_modelled_participants(){

        ModelledParticipant p1 = new DefaultModelledParticipant(new DefaultProtein("p1"));
        ModelledParticipant p2 = new DefaultModelledParticipant(new DefaultProtein("p2"));

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.addModelledParticipant(p1);
        sourceInteraction.addModelledParticipant(p2);

        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction();

        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, true, false);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertFalse(p2 == binary.getParticipantB());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p2, binary.getParticipantB(), true));
        Assert.assertTrue(binary.getParticipantA().getModelledInteraction() == binary);
        Assert.assertTrue(binary.getParticipantB().getModelledInteraction() == binary);

        // no participants
        sourceInteraction.getParticipants().clear();
        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, true, false);

        Assert.assertNull(binary.getParticipantA());
        Assert.assertNull(binary.getParticipantB());

        // one participant
        sourceInteraction.addModelledParticipant(p1);
        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, true, false);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertNull(binary.getParticipantB());
        Assert.assertTrue(binary.getParticipantA().getModelledInteraction() == binary);
    }

    @Test
    public void test_create_modelled_participants_self(){

        ModelledParticipant p1 = new DefaultModelledParticipant(new DefaultProtein("p1"));

        ModelledInteraction sourceInteraction = new DefaultModelledInteraction("source interaction");
        sourceInteraction.addModelledParticipant(p1);

        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction();

        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, true, true);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertTrue(p1.getModelledInteraction() == sourceInteraction);
        Assert.assertTrue(binary.getParticipantB().getModelledInteraction() == binary);

        p1.setStoichiometry(3);
        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(sourceInteraction, binary, true, true);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertFalse(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertEquals(new DefaultStoichiometry(0), binary.getParticipantB().getStoichiometry());
        Assert.assertTrue(p1.getModelledInteraction() == sourceInteraction);
        Assert.assertTrue(binary.getParticipantB().getModelledInteraction() == binary);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_too_many_participants_to_copy_in_binary(){

        Interaction sourceInteraction = new DefaultInteraction("source interaction");
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(new DefaultParticipant(new DefaultProtein("p1")));
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(new DefaultParticipant(new DefaultProtein("p2")));
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(new DefaultParticipant(new DefaultProtein("p3")));

        BinaryInteraction binary = new DefaultBinaryInteraction();

        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, false, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_too_many_participants_to_copy_in_binary_self(){

        Interaction sourceInteraction = new DefaultInteraction("source interaction");
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(new DefaultParticipant(new DefaultProtein("p1")));
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(new DefaultParticipant(new DefaultProtein("p2")));

        BinaryInteraction binary = new DefaultBinaryInteraction();

        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, false, true);
    }

    @Test
    public void test_copy_participants(){

        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));
        Participant p2 = new DefaultParticipant(new DefaultProtein("p2"));

        Interaction sourceInteraction = new DefaultInteraction("source interaction");
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(p1);
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(p2);

        BinaryInteraction binary = new DefaultBinaryInteraction();

        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, false, false);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertTrue(p2 == binary.getParticipantB());

        // no participants
        sourceInteraction.getParticipants().clear();
        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, false, false);

        Assert.assertNull(binary.getParticipantA());
        Assert.assertNull(binary.getParticipantB());

        // one participant
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(p1);
        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, false, false);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertNull(binary.getParticipantB());
    }

    @Test
    public void test_copy_participants_self(){

        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));

        Interaction sourceInteraction = new DefaultInteraction("source interaction");
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(p1);

        BinaryInteraction binary = new DefaultBinaryInteraction();

        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, false, true);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));

        p1.setStoichiometry(3);
        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, false, true);

        Assert.assertTrue(p1 == binary.getParticipantA());
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertFalse(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertEquals(new DefaultStoichiometry(0), binary.getParticipantB().getStoichiometry());
    }

    @Test
    public void test_create_participants(){

        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));
        Participant p2 = new DefaultParticipant(new DefaultProtein("p2"));

        Interaction sourceInteraction = new DefaultInteraction("source interaction");
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(p1);
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(p2);

        BinaryInteraction binary = new DefaultBinaryInteraction();

        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, true, false);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertFalse(p2 == binary.getParticipantB());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p2, binary.getParticipantB(), true));

        // no participants
        sourceInteraction.getParticipants().clear();
        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, true, false);

        Assert.assertNull(binary.getParticipantA());
        Assert.assertNull(binary.getParticipantB());

        // one participant
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(p1);
        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, true, false);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertNull(binary.getParticipantB());
    }

    @Test
    public void test_create_participants_self(){

        Participant p1 = new DefaultParticipant(new DefaultProtein("p1"));

        Interaction sourceInteraction = new DefaultInteraction("source interaction");
        ((Collection<Participant>)sourceInteraction.getParticipants()).add(p1);

        BinaryInteraction binary = new DefaultBinaryInteraction();

        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, true, true);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));

        p1.setStoichiometry(3);
        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(sourceInteraction, binary, true, true);

        Assert.assertFalse(p1 == binary.getParticipantA());
        Assert.assertTrue(DefaultParticipantBaseComparator.areEquals(p1, binary.getParticipantA(), true));
        Assert.assertFalse(binary.getParticipantB() == p1);
        Assert.assertFalse(DefaultParticipantBaseComparator.areEquals(binary.getParticipantA(), binary.getParticipantB(), true));
        Assert.assertEquals(new DefaultStoichiometry(0), binary.getParticipantB().getStoichiometry());
    }
}
