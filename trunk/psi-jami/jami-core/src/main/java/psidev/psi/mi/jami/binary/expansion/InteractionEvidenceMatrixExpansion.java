package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.InteractionUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

import java.util.Collection;
import java.util.Collections;

/**
 * Matrix expansion for InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class InteractionEvidenceMatrixExpansion extends AbstractMatrixExpansion<InteractionEvidence, BinaryInteractionEvidence, ParticipantEvidence>{

    @Override
    protected Collection<BinaryInteractionEvidence> createNewSelfBinaryInteractionsFrom(InteractionEvidence interaction) {
        return Collections.singletonList(InteractionUtils.createAndAddNewSelfBinaryInteractionEvidence(interaction));
    }

    @Override
    protected Collection<BinaryInteractionEvidence> createBinaryInteractionsFrom(InteractionEvidence interaction) {
        return Collections.singletonList(InteractionUtils.createBinaryInteractionEvidenceFrom(interaction));
    }

    @Override
    protected InteractionCategory findInteractionCategory(InteractionEvidence interaction) {
        return InteractionUtils.findInteractionEvidenceCategoryOf(interaction);
    }

    @Override
    protected BinaryInteractionEvidence createBinaryInteraction(InteractionEvidence interaction, ParticipantEvidence c1, ParticipantEvidence c2) {
        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence(getMethod());
        InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, binary, false, true);
        binary.setParticipantA(c1);
        binary.setParticipantB(c2);
        return binary;
    }

    @Override
    protected ParticipantEvidence[] createParticipantsArray(InteractionEvidence interaction) {
        return interaction.getParticipants().toArray(new DefaultParticipantEvidence[]{});
    }
}
