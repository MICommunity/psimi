package psidev.psi.mi.jami.binary.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultProtein;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for DefaultModelledBinaryInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class DefaultModelledBinaryInteractionTest {

    @Test
    public void create_empty_modelled_interaction(){

        ModelledBinaryInteraction interaction = new DefaultModelledBinaryInteraction();

        Assert.assertNull(interaction.getSource());
        Assert.assertNotNull(interaction.getParticipants());
        Assert.assertNotNull(interaction.getCooperativeEffects());
        Assert.assertNotNull(interaction.getInteractionEvidences());
        Assert.assertNotNull(interaction.getModelledConfidences());
        Assert.assertNotNull(interaction.getModelledParameters());
    }

    @Test
    public void createy_modelled_interaction_with_source(){

        ModelledBinaryInteraction interaction = new DefaultModelledBinaryInteraction("test");
        interaction.setSource(new DefaultSource("intact"));

        Assert.assertEquals(new DefaultSource("intact"), interaction.getSource());
        Assert.assertEquals("test", interaction.getShortName());
    }

    @Test
    public void test_add_remove_modelledParticipants(){
        ModelledBinaryInteraction interaction = new DefaultModelledBinaryInteraction("test interaction");
        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertNull(participant.getInteraction());

        // add participant and set modelledInteraction
        interaction.addParticipant(participant);
        Assert.assertEquals(interaction, participant.getInteraction());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // remove modelled participant and set interaction to null
        interaction.removeParticipant(participant);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(0, interaction.getParticipants().size());

        // simply add modelled participant
        interaction.setParticipantA(participant);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // simply remove modelled participant
        interaction.setParticipantA(null);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(0, interaction.getParticipants().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_cannot_add_participants(){
        ModelledParticipant p1 = new DefaultModelledParticipant(new DefaultProtein("p1"));
        ModelledParticipant p2 = new DefaultModelledParticipant(new DefaultProtein("p2"));

        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction(p1,
                p2, new DefaultCvTerm("spoke expansion"));

        binary.getParticipants().add(new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor()));
    }
}
