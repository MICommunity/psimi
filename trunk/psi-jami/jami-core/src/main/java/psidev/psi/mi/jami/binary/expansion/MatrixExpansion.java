package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.*;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipant;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.InteractionUtils;
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

public class MatrixExpansion implements ComplexExpansionMethod {

    private CvTerm method;

    public MatrixExpansion(){
        this.method = CvTermUtils.createMICvTerm(ComplexExpansionMethod.MATRIX_EXPANSION, ComplexExpansionMethod.MATRIX_EXPANSION_MI);
    }

    public CvTerm getMethod() {
        return this.method;
    }

    public boolean isExpandable(Interaction interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isExpandable(InteractionEvidence interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isExpandable(ModelledInteraction interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public Collection<BinaryInteraction> expand(Interaction interaction) {
        if (!isExpandable(interaction)){
            throw new IllegalArgumentException("The interaction cannot be expanded with this method ");
        }

        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>();

        if (interaction instanceof BinaryInteraction){
            binaryInteractions.add((BinaryInteraction) interaction);
        }
        else{
            InteractionCategory category = InteractionUtils.findInteractionCategoryOf(interaction);

            switch (category){
                case binary:
                    binaryInteractions.add(InteractionUtils.createAndAddBinaryWrapperFor(interaction));
                    break;
                case self_intra_molecular:
                    binaryInteractions.add(InteractionUtils.createAndAddBinaryWrapperFor(interaction));
                    break;
                case self_inter_molecular:
                    binaryInteractions.add(InteractionUtils.createAndAddNewSelfBinaryInteraction(interaction));
                    break;
                case n_ary:
                    binaryInteractions.addAll(expandInteraction(interaction));
                    break;
                default:
                    break;
            }
        }

        return binaryInteractions;
    }

    public Collection<BinaryInteractionEvidence> expand(InteractionEvidence interaction) {
        if (!isExpandable(interaction)){
            throw new IllegalArgumentException("The interaction evidence cannot be expanded with this method ");
        }

        Collection<BinaryInteractionEvidence> binaryInteractions = new ArrayList<BinaryInteractionEvidence>();

        if (interaction instanceof BinaryInteractionEvidence){
            binaryInteractions.add((BinaryInteractionEvidence) interaction);
        }
        else{
            InteractionCategory category = InteractionUtils.findInteractionCategoryOf(interaction);

            switch (category){
                case binary:
                    binaryInteractions.add(InteractionUtils.createAndAddBinaryEvidenceWrapperFor(interaction));
                    break;
                case self_intra_molecular:
                    binaryInteractions.add(InteractionUtils.createAndAddBinaryEvidenceWrapperFor(interaction));
                    break;
                case self_inter_molecular:
                    binaryInteractions.add(InteractionUtils.createAndAddNewSelfBinaryInteractionEvidence(interaction));
                    break;
                case n_ary:
                    binaryInteractions.addAll(expandInteractionEvidence(interaction));
                    break;
                default:
                    break;
            }
        }

        return binaryInteractions;
    }

    public Collection<ModelledBinaryInteraction> expand(ModelledInteraction interaction) {
        if (!isExpandable(interaction)){
            throw new IllegalArgumentException("The modelled interaction cannot be expanded with this method ");
        }

        Collection<ModelledBinaryInteraction> binaryInteractions = new ArrayList<ModelledBinaryInteraction>();

        if (interaction instanceof ModelledBinaryInteraction){
            binaryInteractions.add((ModelledBinaryInteraction) interaction);
        }
        else{
            InteractionCategory category = InteractionUtils.findInteractionCategoryOf(interaction);

            switch (category){
                case binary:
                    binaryInteractions.add(InteractionUtils.createAndAddModelledBinaryeWrapperFor(interaction));
                    break;
                case self_intra_molecular:
                    binaryInteractions.add(InteractionUtils.createAndAddModelledBinaryeWrapperFor(interaction));
                    break;
                case self_inter_molecular:
                    binaryInteractions.add(InteractionUtils.createAndAddNewSelfModelledBinaryInteraction(interaction));
                    break;
                case n_ary:
                    binaryInteractions.addAll(expandModelledInteraction(interaction));
                    break;
                default:
                    break;
            }
        }

        return binaryInteractions;
    }

    protected Collection<BinaryInteractionEvidence> expandInteractionEvidence(InteractionEvidence interaction){
        ParticipantEvidence[] participants = interaction.getParticipants().toArray(new DefaultParticipantEvidence[]{});

        Collection<BinaryInteractionEvidence> binaryInteractions = new ArrayList<BinaryInteractionEvidence>(interaction.getParticipants().size() - 1);
        for ( int i = 0; i < interaction.getParticipants().size(); i++ ) {
            ParticipantEvidence c1 = participants[i];
            for ( int j = ( i + 1 ); j < participants.length; j++ ) {
                ParticipantEvidence c2 = participants[j];
                // build a new interaction
                BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence(this.method);
                InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, binary, false, true);

                // set participants
                initialiseBinaryInteractionParticipantsWith(c1, c2, binary);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    protected Collection<ModelledBinaryInteraction> expandModelledInteraction(ModelledInteraction interaction){
        ModelledParticipant[] participants = interaction.getParticipants().toArray(new DefaultModelledParticipant[]{});

        Collection<ModelledBinaryInteraction> binaryInteractions = new ArrayList<ModelledBinaryInteraction>(interaction.getParticipants().size() - 1);
        for ( int i = 0; i < interaction.getParticipants().size(); i++ ) {
            ModelledParticipant c1 = participants[i];
            for ( int j = ( i + 1 ); j < participants.length; j++ ) {
                ModelledParticipant c2 = participants[j];
                // build a new interaction
                ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction(this.method);
                InteractionCloner.copyAndOverrideModelledInteractionProperties(interaction, binary, false, true);

                // set participants
                initialiseBinaryInteractionParticipantsWith(c1, c2, binary);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    protected Collection<BinaryInteraction> expandDefaultInteraction(Interaction interaction){
        Participant[] participants = interaction.getParticipants().toArray(new DefaultParticipant[]{});

        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>(interaction.getParticipants().size() - 1);
        for ( int i = 0; i < interaction.getParticipants().size(); i++ ) {
            Participant c1 = participants[i];
            for ( int j = ( i + 1 ); j < participants.length; j++ ) {
                Participant c2 = participants[j];
                // build a new interaction
                BinaryInteraction binary = new DefaultBinaryInteraction(this.method);
                InteractionCloner.copyAndOverrideInteractionProperties(interaction, binary, false, true);

                // set participants
                initialiseBinaryInteractionParticipantsWith(c1, c2, binary);

                binaryInteractions.add(binary);
            }
        }

        return binaryInteractions;
    }

    protected Collection<? extends BinaryInteraction> expandInteraction(Interaction interaction){

        if (interaction instanceof InteractionEvidence){
            return expandInteractionEvidence((InteractionEvidence) interaction);
        }
        else if (interaction instanceof ModelledInteraction){
            return expandModelledInteraction((ModelledInteraction) interaction);
        }
        else {
            return expandDefaultInteraction(interaction);
        }
    }

    protected void initialiseBinaryInteractionParticipantsWith(Participant c1, Participant c2, BinaryInteraction binary) {
        binary.setParticipantA(c1);
        binary.setParticipantB(c2);
    }
}
