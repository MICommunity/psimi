package psidev.psi.mi.jami.binary.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultModelledInteraction;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for ModelledBinaryInteractionWrapper
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class ModelledBinaryInteractionWrapperTest {

    @Test
    public void test_add_remove_participantEvidences(){
        ModelledInteraction interaction = new DefaultModelledInteraction("test interaction");
        ModelledBinaryInteraction binary = new ModelledBinaryInteractionWrapper(interaction);
        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());

        Assert.assertNull(participant.getInteraction());

        // add participant and set modelledInteraction
        interaction.addParticipant(participant);
        Assert.assertEquals(interaction, participant.getInteraction());
        Assert.assertEquals(1, binary.getParticipants().size());

        // remove modelled participant and set interaction to null
        interaction.removeParticipant(participant);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(0, interaction.getParticipants().size());

        // simply add modelled participant
        binary.setParticipantB(participant);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(1, interaction.getParticipants().size());

        // simply remove modelled participant
        binary.setParticipantB(null);
        Assert.assertNull(participant.getInteraction());
        Assert.assertEquals(0, interaction.getParticipants().size());
    }
}
