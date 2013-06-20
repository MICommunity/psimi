package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The bipartite expansion.
 *
 * Complex n-ary data has been expanded to binary using the bipartite model.
 * This assumes that all molecules in the complex interact with a single externally designated entity.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public class BipartiteExpansion extends AbstractBipartiteExpansion<Interaction, BinaryInteraction, Participant> {

    private InteractionEvidenceBipartiteExpansion interactionEvidenceExpansion;
    private ModelledInteractionBipartiteExpansion modelledInteractionExpansion;

    public BipartiteExpansion(){
        super();
        this.interactionEvidenceExpansion = new InteractionEvidenceBipartiteExpansion();
        this.modelledInteractionExpansion = new ModelledInteractionBipartiteExpansion();
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
    protected Participant createParticipantForComplexEntity(Complex complexEntity) {
        return new DefaultParticipant(complexEntity);
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

}