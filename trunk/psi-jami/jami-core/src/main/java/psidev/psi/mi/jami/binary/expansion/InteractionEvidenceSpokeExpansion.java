package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.InteractionUtils;
import psidev.psi.mi.jami.utils.ParticipantUtils;

import java.util.Collection;
import java.util.Collections;

/**
 * Spoke expansion for interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class InteractionEvidenceSpokeExpansion extends AbstractSpokeExpansion<InteractionEvidence, BinaryInteractionEvidence>{

    @Override
    protected Collection<BinaryInteractionEvidence> createNewSelfBinaryInteractionsFrom(InteractionEvidence interaction) {
        return Collections.singletonList(getBinaryInteractionFactory().createSelfBinaryInteractionEvidenceFrom(interaction));
    }

    @Override
    protected Collection<BinaryInteractionEvidence> createBinaryInteractionsFrom(InteractionEvidence interaction) {
        return Collections.singletonList((BinaryInteractionEvidence)getBinaryInteractionFactory().createBinaryInteractionEvidenceWrapperFrom(interaction));
    }

    @Override
    protected InteractionCategory findInteractionCategory(InteractionEvidence interaction) {
        return InteractionUtils.findInteractionEvidenceCategoryOf(interaction);
    }

    @Override
    protected <P extends Participant> BinaryInteractionEvidence createBinaryInteraction(InteractionEvidence interaction, P c1, P c2) {
        return getBinaryInteractionFactory().createBinaryInteractionEvidenceFrom(interaction, (ParticipantEvidence)c1, (ParticipantEvidence)c2, getMethod());
    }

    @Override
    protected ParticipantEvidence collectBestBaitForSpokeExpansion(InteractionEvidence interaction) {
        return ParticipantUtils.collectBestParticipantEvidenceAsBaitForSpokeExpansion(interaction.getParticipants());
    }
}
