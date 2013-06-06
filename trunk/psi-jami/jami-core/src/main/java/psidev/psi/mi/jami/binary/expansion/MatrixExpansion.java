package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.CvTermUtils;
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

public class MatrixExpansion extends AbstractComplexExpansionMethod {

    public MatrixExpansion(){
        super(CvTermUtils.createMICvTerm(ComplexExpansionMethod.MATRIX_EXPANSION, ComplexExpansionMethod.MATRIX_EXPANSION_MI));
    }

    @Override
    protected Collection<BinaryInteractionEvidence> expandInteractionEvidence(InteractionEvidence interaction){
        ParticipantEvidence[] participants = interaction.getParticipants().toArray(new DefaultParticipantEvidence[]{});

        Collection<BinaryInteractionEvidence> binaryInteractions = new ArrayList<BinaryInteractionEvidence>(interaction.getParticipants().size() - 1);
        for ( int i = 0; i < interaction.getParticipants().size(); i++ ) {
            ParticipantEvidence c1 = participants[i];
            for ( int j = ( i + 1 ); j < participants.length; j++ ) {
                ParticipantEvidence c2 = participants[j];
                // build a new interaction
                BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence(getMethod());
                InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, binary, false, true);

                // set participants
                initialiseBinaryInteractionParticipantsWith(c1, c2, binary);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    @Override
    protected Collection<ModelledBinaryInteraction> expandModelledInteraction(ModelledInteraction interaction){
        ModelledParticipant[] participants = interaction.getParticipants().toArray(new DefaultModelledParticipant[]{});

        Collection<ModelledBinaryInteraction> binaryInteractions = new ArrayList<ModelledBinaryInteraction>((interaction.getParticipants().size() - 1)*(interaction.getParticipants().size() - 1));
        for ( int i = 0; i < interaction.getParticipants().size(); i++ ) {
            ModelledParticipant c1 = participants[i];
            for ( int j = ( i + 1 ); j < participants.length; j++ ) {
                ModelledParticipant c2 = participants[j];
                // build a new interaction
                ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction(getMethod());
                InteractionCloner.copyAndOverrideModelledInteractionProperties(interaction, binary, false, true);

                // set participants
                initialiseBinaryInteractionParticipantsWith(c1, c2, binary);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    @Override
    protected Collection<BinaryInteraction> expandDefaultInteraction(Interaction interaction){
        Participant[] participants = interaction.getParticipants().toArray(new DefaultParticipant[]{});

        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>((interaction.getParticipants().size() - 1)*(interaction.getParticipants().size() - 1));
        for ( int i = 0; i < interaction.getParticipants().size(); i++ ) {
            Participant c1 = participants[i];
            for ( int j = ( i + 1 ); j < participants.length; j++ ) {
                Participant c2 = participants[j];
                // build a new interaction
                BinaryInteraction binary = new DefaultBinaryInteraction(getMethod());
                InteractionCloner.copyAndOverrideInteractionProperties(interaction, binary, false, true);

                // set participants
                initialiseBinaryInteractionParticipantsWith(c1, c2, binary);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }
}
