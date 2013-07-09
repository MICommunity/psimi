package psidev.psi.mi.jami.binary.impl;

import junit.framework.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.InteractorUtils;

/**
 * Unit tester for BinaryInteractionEvidenceWrapper
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class BinaryInteractionEvidenceWrapperTest {

    @Test
    public void test_add_remove_participantEvidences(){
        InteractionEvidence interaction = new DefaultInteractionEvidence("test interaction");
        BinaryInteractionEvidence binary = new BinaryInteractionEvidenceWrapper(interaction);
        ParticipantEvidence participant = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());

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
