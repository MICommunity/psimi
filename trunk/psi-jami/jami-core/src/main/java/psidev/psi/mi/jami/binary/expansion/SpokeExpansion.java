package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.ParticipantUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

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

public class SpokeExpansion extends AbstractComplexExpansionMethod {
    public SpokeExpansion() {
        super(CvTermUtils.createMICvTerm(ComplexExpansionMethod.SPOKE_EXPANSION, ComplexExpansionMethod.SPOKE_EXPANSION_MI));
    }

    @Override
    protected Collection<BinaryInteractionEvidence> expandInteractionEvidence(InteractionEvidence interaction) {
        Collection<BinaryInteractionEvidence> binaryInteractions = new ArrayList<BinaryInteractionEvidence>(interaction.getParticipants().size()-1);

        ParticipantEvidence bait = ParticipantUtils.collectParticipantEvidenceToBeUsedAsABaitForSpokeExpansion(interaction.getParticipants());

        for ( ParticipantEvidence p : interaction.getParticipants() ) {
            if (p != bait){
                // build a new interaction
                BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence(getMethod());
                InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, binary, false, true);

                // set participants
                initialiseBinaryInteractionParticipantsWith(bait, p, binary);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    @Override
    protected Collection<ModelledBinaryInteraction> expandModelledInteraction(ModelledInteraction interaction) {
        Collection<ModelledBinaryInteraction> binaryInteractions = new ArrayList<ModelledBinaryInteraction>(interaction.getParticipants().size()-1);

        Participant bait = ParticipantUtils.collectParticipantToBeUsedAsABaitForSpokeExpansion(interaction.getParticipants());

        for ( Participant p : interaction.getParticipants() ) {
            if (p != bait){
                // build a new interaction
                ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction(getMethod());
                InteractionCloner.copyAndOverrideModelledInteractionProperties(interaction, binary, false, true);

                // set participants
                initialiseBinaryInteractionParticipantsWith(bait, p, binary);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    @Override
    protected Collection<BinaryInteraction> expandDefaultInteraction(Interaction interaction) {
        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>(interaction.getParticipants().size()-1);

        Participant bait = ParticipantUtils.collectParticipantToBeUsedAsABaitForSpokeExpansion(interaction.getParticipants());

        for ( Participant p : interaction.getParticipants() ) {
            if (p != bait){
                // build a new interaction
                BinaryInteraction binary = new DefaultBinaryInteraction(getMethod());
                InteractionCloner.copyAndOverrideInteractionProperties(interaction, binary, false, true);

                // set participants
                initialiseBinaryInteractionParticipantsWith(bait, p, binary);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }
}
