package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.utils.InteractionUtils;
import psidev.psi.mi.jami.utils.ParticipantUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Spoke expansion for interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class InteractionEvidenceSpokeExpansion extends AbstractSpokeExpansion<InteractionEvidence>{

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
    protected Collection<BinaryInteractionEvidence> collectBinaryInteractionsFrom(InteractionEvidence interaction) {
        Collection<BinaryInteractionEvidence> binaryInteractions = new ArrayList<BinaryInteractionEvidence>(interaction.getParticipants().size()-1);

        ParticipantEvidence bait = collectBestBaitForSpokeExpansion(interaction);

        for ( ParticipantEvidence p : interaction.getParticipants() ) {
            if (p != bait){
                // build a new interaction
                BinaryInteractionEvidence binary = createBinaryInteraction(interaction, bait, p);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    @Override
    protected BinaryInteractionEvidence createBinaryInteraction(InteractionEvidence interaction, Participant c1, Participant c2) {
        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence(getMethod());
        InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, binary, false, true);
        binary.setParticipantA((ParticipantEvidence)c1);
        binary.setParticipantB((ParticipantEvidence)c2);
        return binary;
    }

    @Override
    protected ParticipantEvidence collectBestBaitForSpokeExpansion(InteractionEvidence interaction) {
        return ParticipantUtils.collectBestParticipantEvidenceAsBaitForSpokeExpansion(interaction.getParticipants());
    }
}
