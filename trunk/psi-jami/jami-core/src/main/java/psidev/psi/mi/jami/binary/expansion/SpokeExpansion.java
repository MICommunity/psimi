package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.utils.ParticipantUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The spoke expansion
 * Complex n-ary data has been expanded to binary using the spoke model. This assumes that all molecules in the complex interact with a single designated molecule, usually the bait.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class SpokeExpansion extends AbstractSpokeExpansion<Interaction<? extends Participant>, BinaryInteraction> {
    private InteractionEvidenceSpokeExpansion interactionEvidenceExpansion;
    private ModelledInteractionSpokeExpansion modelledInteractionExpansion;

    public SpokeExpansion(){
        super();
        this.interactionEvidenceExpansion = new InteractionEvidenceSpokeExpansion();
        this.modelledInteractionExpansion = new ModelledInteractionSpokeExpansion();
    }

    @Override
    public Collection<BinaryInteraction> expand(Interaction interaction) {

        if (interaction instanceof InteractionEvidence){
            Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>(interaction.getParticipants().size());
            binaryInteractions.addAll(interactionEvidenceExpansion.expand((InteractionEvidence) interaction));
            return binaryInteractions;
        }
        else if (interaction instanceof ModelledInteraction){
            Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>(interaction.getParticipants().size());
            binaryInteractions.addAll(modelledInteractionExpansion.expand((ModelledInteraction) interaction));
            return binaryInteractions;
        }
        else {
            return super.expand(interaction);
        }
    }

    @Override
    protected BinaryInteraction createBinaryInteraction(Interaction interaction, Participant c1, Participant c2) {
        return getBinaryInteractionFactory().createBasicBinaryInteractionFrom(interaction, c1, c2, getMethod());
    }

    @Override
    protected Participant collectBestBaitForSpokeExpansion(Interaction interaction) {
        return ParticipantUtils.collectBestBaitParticipantForSpokeExpansion(interaction.getParticipants());
    }
}
