package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The matrix Expansion method.
 * 	Complex n-ary data has been expanded to binary using the spoke model.
 * 	This assumes that all molecules in the complex interact with each other.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/06/13</pre>
 */

public class MatrixExpansion extends AbstractMatrixExpansion<Interaction, BinaryInteraction, Participant> {

    private InteractionEvidenceMatrixExpansion interactionEvidenceExpansion;
    private ModelledInteractionMatrixExpansion modelledInteractionExpansion;

    public MatrixExpansion(){
        super();
        this.interactionEvidenceExpansion = new InteractionEvidenceMatrixExpansion();
        this.modelledInteractionExpansion = new ModelledInteractionMatrixExpansion();
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
        BinaryInteraction binary = new DefaultBinaryInteraction(getMethod());
        InteractionCloner.copyAndOverrideBasicInteractionProperties(interaction, binary, false, true);
        binary.setParticipantA(c1);
        binary.setParticipantB(c2);
        return binary;
    }

    @Override
    protected Participant[] createParticipantsArray(Interaction interaction) {
        return interaction.getParticipants().toArray(new DefaultParticipant[]{});
    }
}
