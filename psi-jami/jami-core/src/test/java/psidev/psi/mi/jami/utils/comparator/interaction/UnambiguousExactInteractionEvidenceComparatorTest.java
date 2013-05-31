package psidev.psi.mi.jami.utils.comparator.interaction;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.math.BigDecimal;

/**
 * Unit tester for UnambiguousExactInteractionEvidenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class UnambiguousExactInteractionEvidenceComparatorTest {

    private UnambiguousExactInteractionEvidenceComparator comparator = new UnambiguousExactInteractionEvidenceComparator();

    @Test
    public void test_interaction_null_after(){
        InteractionEvidence interaction1 = null;
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test");

        Assert.assertTrue(comparator.compare(interaction1, interaction2) > 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) < 0);

        Assert.assertFalse(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_basic_properties_interaction(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("direct interaction"));
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) > 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) < 0);

        Assert.assertFalse(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_basic_properties_interaction(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_imex(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.assignImexId("IM-1-1");
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.assignImexId("IM-1-2");

        Assert.assertTrue(comparator.compare(interaction1, interaction2) < 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) > 0);

        Assert.assertFalse(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_imex(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.assignImexId("IM-1-1");
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.assignImexId("IM-1-1");

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_ignore_imex_null(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.assignImexId("IM-1-1");
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_negative_after(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setNegative(true);
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) > 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) < 0);

        Assert.assertFalse(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_experiment(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12346")));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) < 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) > 0);

        Assert.assertFalse(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_experiment(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_participant_evidences(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction1.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12345"))));
        interaction1.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12346"))));

        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction2.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12345"))));
        interaction2.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12346"))));
        interaction2.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12347"))));
        interaction2.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12348"))));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) < 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) > 0);

        Assert.assertFalse(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_participant_evidences(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction1.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12347"))));
        interaction1.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12348"))));
        interaction1.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12345"))));
        interaction1.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12346"))));

        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction2.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12345"))));
        interaction2.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12346"))));
        interaction2.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 1", XrefUtils.createUniprotIdentity("P12347"))));
        interaction2.addParticipantEvidence(new DefaultParticipantEvidence(new DefaultProtein("test 2", XrefUtils.createUniprotIdentity("P12348"))));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_parameters(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction1.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(3))));
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction2.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) < 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) > 0);

        Assert.assertFalse(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_parameters(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction1.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction2.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_different_parameter_value_set(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction1.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));
        interaction1.getVariableParameterValues().add(new DefaultVariableParameterValueSet());
        interaction1.getVariableParameterValues().iterator().next().add(new DefaultVariableParameterValue("S phase", null));

        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction2.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));
        interaction2.getVariableParameterValues().add(new DefaultVariableParameterValueSet());
        interaction2.getVariableParameterValues().iterator().next().add(new DefaultVariableParameterValue("G phase", null));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) > 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) < 0);

        Assert.assertFalse(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_same_parameter_value_set(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction1.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));
        interaction1.getVariableParameterValues().add(new DefaultVariableParameterValueSet());
        interaction1.getVariableParameterValues().iterator().next().add(new DefaultVariableParameterValue("G phase", null));

        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction2.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));
        interaction2.getVariableParameterValues().add(new DefaultVariableParameterValueSet());
        interaction2.getVariableParameterValues().iterator().next().add(new DefaultVariableParameterValue("G phase", null));

        Assert.assertTrue(comparator.compare(interaction1, interaction2) == 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) == 0);

        Assert.assertTrue(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }

    @Test
    public void test_inferred_after(){
        InteractionEvidence interaction1 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction1.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction1.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));
        InteractionEvidence interaction2 = new DefaultInteractionEvidence("test", new DefaultCvTerm("association"));
        interaction2.setExperimentAndAddInteractionEvidence(new DefaultExperiment(new DefaultPublication("12345")));
        interaction2.getParameters().add(new DefaultParameter(new DefaultCvTerm("kd"), new ParameterValue(new BigDecimal(4))));
        interaction2.setInferred(true);

        Assert.assertTrue(comparator.compare(interaction1, interaction2) < 0);
        Assert.assertTrue(comparator.compare(interaction2, interaction1) > 0);

        Assert.assertFalse(UnambiguousExactInteractionEvidenceComparator.areEquals(interaction1, interaction2));
    }
}
