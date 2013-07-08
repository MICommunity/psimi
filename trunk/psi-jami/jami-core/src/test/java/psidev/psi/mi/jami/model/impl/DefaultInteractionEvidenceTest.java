package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for DefaultInteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultInteractionEvidenceTest {

    @Test
    public void create_empty_interaction_evidence(){

        InteractionEvidence interaction = new DefaultInteractionEvidence();

        Assert.assertNull(interaction.getExperiment());
        Assert.assertNotNull(interaction.getParticipants());
        Assert.assertNotNull(interaction.getVariableParameterValues());
        Assert.assertNotNull(interaction.getConfidences());
        Assert.assertNotNull(interaction.getParameters());
        Assert.assertNull(interaction.getImexId());
    }

    @Test
    public void test_add_remove_participantEvidences(){
        InteractionEvidence interaction = new DefaultInteractionEvidence("test interaction");
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
        interaction.getParticipants().add(participant);
        Assert.assertNull(participant.getInteractionEvidence());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // simply remove modelled participant
        interaction.getParticipants().remove(participant);
        Assert.assertNull(participant.getInteractionEvidence());
        Assert.assertEquals(0, interaction.getParticipants().size());
    }

    @Test
    public void test_set_unset_experiment(){
        InteractionEvidence interaction = new DefaultInteractionEvidence("test");
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
        InteractionEvidence pub = new DefaultInteractionEvidence();
        pub.assignImexId("IM-1-1");

        Assert.assertEquals("IM-1-1", pub.getImexId());
        Assert.assertEquals(1, pub.getXrefs().size());
        Assert.assertEquals(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-1-1", CvTermUtils.createImexPrimaryQualifier()), pub.getXrefs().iterator().next());
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_assign_imex_id_reset_imex_null(){
        InteractionEvidence pub = new DefaultInteractionEvidence();
        pub.assignImexId("IM-1-1");

        pub.assignImexId(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_assign_imex_id_reset_empty_imex(){
        InteractionEvidence pub = new DefaultInteractionEvidence();
        pub.assignImexId("IM-1-1");

        pub.assignImexId("");
    }

    @Test
    public void test_remove_imex_id(){
        // assign IMEx id
        InteractionEvidence pub = new DefaultInteractionEvidence();
        pub.assignImexId("IM-1-1");
        Assert.assertEquals("IM-1-1", pub.getImexId());

        // remove IMEx id xref
        pub.getXrefs().remove(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-1-1", CvTermUtils.createImexPrimaryQualifier()));
        Assert.assertNull(pub.getImexId());

        // add new IMEx id xref
        pub.getXrefs().add(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-2-1", CvTermUtils.createImexPrimaryQualifier()));
        Assert.assertEquals("IM-2-1", pub.getImexId());

        // reset xrefs
        pub.getXrefs().clear();
        Assert.assertNull(pub.getImexId());

        // add new IMEX xref which is not imex-primary
        pub.getXrefs().add(new DefaultXref(CvTermUtils.createImexDatabase(), "IM-2-1"));
        Assert.assertNull(pub.getImexId());
    }

}
