package psidev.psi.mi.jami.binary.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for DefaultBinaryInteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class DefaultBinaryInteractionEvidenceTest {

    @Test
    public void create_empty_interaction_evidence(){

        BinaryInteractionEvidence interaction = new DefaultBinaryInteractionEvidence();

        Assert.assertNull(interaction.getExperiment());
        Assert.assertNotNull(interaction.getParticipants());
        Assert.assertNotNull(interaction.getVariableParameterValues());
        Assert.assertNotNull(interaction.getConfidences());
        Assert.assertNotNull(interaction.getParameters());
        Assert.assertNull(interaction.getImexId());
    }

    @Test
    public void test_add_remove_participantEvidences(){
        BinaryInteractionEvidence interaction = new DefaultBinaryInteractionEvidence("test interaction");
        ParticipantEvidence participant = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertNull(participant.getInteractionEvidence());

        // add participant and set modelledInteraction
        interaction.addParticipantEvidence(participant);
        Assert.assertEquals(interaction, participant.getInteractionEvidence());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // remove modelled participant and set interaction to null
        interaction.removeParticipantEvidence(participant);
        Assert.assertNull(participant.getInteractionEvidence());
        Assert.assertEquals(0, interaction.getParticipants().size());

        // simply add modelled participant
        interaction.setParticipantB(participant);
        Assert.assertNull(participant.getInteractionEvidence());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // simply remove modelled participant
        interaction.setParticipantB(null);
        Assert.assertNull(participant.getInteractionEvidence());
        Assert.assertEquals(0, interaction.getParticipants().size());
    }

    @Test
    public void test_set_unset_experiment(){
        BinaryInteractionEvidence interaction = new DefaultBinaryInteractionEvidence("test");
        Experiment exp = new DefaultExperiment(new DefaultPublication("12345"));
        Assert.assertNull(interaction.getExperiment());

        interaction.setExperimentAndAddInteractionEvidence(exp);
        Assert.assertEquals(exp, interaction.getExperiment());
        Assert.assertEquals(1, exp.getInteractionEvidences().size());

        interaction.setExperimentAndAddInteractionEvidence(null);
        Assert.assertNull(interaction.getExperiment());
        Assert.assertEquals(0, exp.getInteractionEvidences().size());
    }

    @Test
    public void test_assign_imex_id(){
        BinaryInteractionEvidence pub = new DefaultBinaryInteractionEvidence();
        pub.assignImexId("IM-1-1");

        Assert.assertEquals("IM-1-1", pub.getImexId());
        Assert.assertEquals(1, pub.getXrefs().size());
        Assert.assertEquals(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-1-1", CvTermUtils.createImexPrimaryQualifier()), pub.getXrefs().iterator().next());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_assign_imex_id_reset_imex_null(){
        BinaryInteractionEvidence pub = new DefaultBinaryInteractionEvidence();
        pub.assignImexId("IM-1-1");

        pub.assignImexId(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_assign_imex_id_reset_empty_imex(){
        BinaryInteractionEvidence pub = new DefaultBinaryInteractionEvidence();
        pub.assignImexId("IM-1-1");

        pub.assignImexId("");
    }

    @Test
    public void test_remove_imex_id(){
        // assign IMEx id
        BinaryInteractionEvidence pub = new DefaultBinaryInteractionEvidence();
        pub.assignImexId("IM-1-1");
        Assert.assertEquals("IM-1-1", pub.getImexId());

        // remove IMEx id xref
        pub.getXrefs().remove(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-1-1", CvTermUtils.createImexPrimaryQualifier()));
        Assert.assertNull(pub.getImexId());

        // add new IMEx id xref
        pub.getXrefs().add(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-2-1", CvTermUtils.createImexPrimaryQualifier()));
        Assert.assertEquals("IM-2-1", pub.getImexId());

        // clear xrefs
        pub.getXrefs().clear();
        Assert.assertNull(pub.getImexId());

        // add new IMEX xref which is not imex-primary
        pub.getXrefs().add(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-2-1"));
        Assert.assertNull(pub.getImexId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_cannot_add_participants(){
        ParticipantEvidence p1 = new DefaultParticipantEvidence(new DefaultProtein("p1"));
        ParticipantEvidence p2 = new DefaultParticipantEvidence(new DefaultProtein("p2"));

        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence(p1,
                p2, new DefaultCvTerm("spoke expansion"));

        binary.getParticipants().add(new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor()));
    }
}
