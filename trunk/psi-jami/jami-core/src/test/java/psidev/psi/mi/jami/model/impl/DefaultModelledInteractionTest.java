package psidev.psi.mi.jami.model.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for DefaultModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/05/13</pre>
 */

public class DefaultModelledInteractionTest {

    @Test
    public void create_empty_modelled_interaction(){

        ModelledInteraction interaction = new DefaultModelledInteraction();

        Assert.assertNull(interaction.getSource());
        Assert.assertNotNull(interaction.getParticipants());
        Assert.assertNotNull(interaction.getCooperativeEffects());
        Assert.assertNotNull(interaction.getInteractionEvidences());
        Assert.assertNotNull(interaction.getModelledConfidences());
        Assert.assertNotNull(interaction.getModelledParameters());
    }

    @Test
    public void createy_modelled_interaction_with_source(){

        ModelledInteraction interaction = new DefaultModelledInteraction("test", new DefaultSource("intact"));

        Assert.assertEquals(new DefaultSource("intact"), interaction.getSource());
        Assert.assertEquals("test", interaction.getShortName());
    }

    @Test
    public void test_add_remove_modelledParticipants(){
        ModelledInteraction interaction = new DefaultModelledInteraction("test interaction");
        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertNull(participant.getModelledInteraction());

        // add participant and set modelledInteraction
        interaction.addModelledParticipant(participant);
        Assert.assertEquals(interaction, participant.getModelledInteraction());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // remove modelled participant and set interaction to null
        interaction.removeModelledParticipant(participant);
        Assert.assertNull(participant.getModelledInteraction());
        Assert.assertEquals(0, interaction.getParticipants().size());

        // simply add modelled participant
        interaction.getParticipants().add(participant);
        Assert.assertNull(participant.getModelledInteraction());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // simply remove modelled participant
        interaction.getParticipants().remove(participant);
        Assert.assertNull(participant.getModelledInteraction());
        Assert.assertEquals(0, interaction.getParticipants().size());
    }
}
