package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.InteractionCategory;
import psidev.psi.mi.jami.binary.impl.*;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

/**
 * Factory for experimental interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class InteractionUtils {
    
    public static InteractionEvidence createEmptyBasicExperimentalInteraction(){

        return new DefaultInteractionEvidence(ExperimentUtils.createUnknownBasicExperiment());
    }

    /**
     * The method will find the interactionCategory (binary, self, etc.)
     * @param interaction
     * @return the InteractionCategory, null if the given interaction is null
     */
    public static InteractionCategory findInteractionCategoryOf(Interaction interaction){
        if (interaction == null){
            return null;
        }

        // only one participant, check stoichiometry
        if (interaction.getParticipants().size() == 1) {
            Participant p = interaction.getParticipants().iterator().next();

            // the stoichiometry is not specified
            if (p.getStoichiometry() == null || p.getStoichiometry().getMaxValue() == 0){
                // if we have self participants, then it is self_intra_molecular
                if (ParticipantUtils.isSelfParticipant(p) || ParticipantUtils.isPutativeSelfParticipant(p)){
                    return InteractionCategory.self_intra_molecular;
                }
                // we can consider that we have self inter molecular
                else {
                    return InteractionCategory.self_inter_molecular;
                }
            }
            // intra molecular
            else if (p.getStoichiometry().getMaxValue() == 1){
                 return InteractionCategory.self_intra_molecular;
            }
            else{
                return InteractionCategory.self_inter_molecular;
            }
        }
        else if (interaction.getParticipants().size() == 2){
            return InteractionCategory.binary;
        }
        else if (interaction.getParticipants().size() > 2){
            return InteractionCategory.n_ary;
        }

        return null;
    }

    /**
     * The method will find the interactionCategory (binary, self, etc.)
     * @param interaction
     * @return the InteractionCategory, null if the given interaction is null
     */
    public static InteractionCategory findInteractionCategoryOf(InteractionEvidence interaction){
        if (interaction == null){
            return null;
        }

        // only one participant, check stoichiometry
        if (interaction.getParticipants().size() == 1) {
            Participant p = interaction.getParticipants().iterator().next();

            // the stoichiometry is not specified
            if (p.getStoichiometry() == null || p.getStoichiometry().getMaxValue() == 0){
                // if we have self participants, then it is self_intra_molecular
                if (ParticipantUtils.isSelfParticipant(p) || ParticipantUtils.isPutativeSelfParticipant(p)){
                    return InteractionCategory.self_intra_molecular;
                }
                // we can consider that we have self inter molecular
                else {
                    return InteractionCategory.self_inter_molecular;
                }
            }
            // intra molecular
            else if (p.getStoichiometry().getMaxValue() == 1){
                return InteractionCategory.self_intra_molecular;
            }
            else{
                return InteractionCategory.self_inter_molecular;
            }
        }
        else if (interaction.getParticipants().size() == 2){
            return InteractionCategory.binary;
        }
        else if (interaction.getParticipants().size() > 2){
            return InteractionCategory.n_ary;
        }

        return null;
    }

    /**
     * The method will find the interactionCategory (binary, self, etc.)
     * @param interaction
     * @return the InteractionCategory, null if the given interaction is null
     */
    public static InteractionCategory findInteractionCategoryOf(ModelledInteraction interaction){
        if (interaction == null){
            return null;
        }

        // only one participant, check stoichiometry
        if (interaction.getParticipants().size() == 1) {
            Participant p = interaction.getParticipants().iterator().next();

            // the stoichiometry is not specified
            if (p.getStoichiometry() == null || p.getStoichiometry().getMaxValue() == 0){
                // if we have self participants, then it is self_intra_molecular
                if (ParticipantUtils.isSelfParticipant(p) || ParticipantUtils.isPutativeSelfParticipant(p)){
                    return InteractionCategory.self_intra_molecular;
                }
                // we can consider that we have self inter molecular
                else {
                    return InteractionCategory.self_inter_molecular;
                }
            }
            // intra molecular
            else if (p.getStoichiometry().getMaxValue() == 1){
                return InteractionCategory.self_intra_molecular;
            }
            else{
                return InteractionCategory.self_inter_molecular;
            }
        }
        else if (interaction.getParticipants().size() == 2){
            return InteractionCategory.binary;
        }
        else if (interaction.getParticipants().size() > 2){
            return InteractionCategory.n_ary;
        }

        return null;
    }

    /**
     * Create a BinaryInteractionWrapper from the given interaction which should contain not more than two participants
     * @param interaction
     * @return the new BinaryInteractionWrapper for this interaction
     * @throws IllegalArgumentException if the interaction contains more than two participants
     */
    public static BinaryInteraction createAndAddBinaryWrapperFor(Interaction interaction) {
        if (interaction instanceof ModelledInteraction){
            return new ModelledBinaryInteractionWrapper((ModelledInteraction)interaction);
        }
        else if (interaction instanceof InteractionEvidence){
            return new BinaryInteractionEvidenceWrapper((InteractionEvidence)interaction);
        }
        else{
            return new BinaryInteractionWrapper(interaction);
        }
    }

    /**
     * Create a BinaryInteractionWrapper from the given interaction which should contain not more than two participants
     * @param interaction
     * @return the new BinaryInteractionWrapper for this interaction
     * @throws IllegalArgumentException if the interaction contains more than two participants
     */
    public static BinaryInteractionEvidence createAndAddBinaryEvidenceWrapperFor(InteractionEvidence interaction) {
        return new BinaryInteractionEvidenceWrapper(interaction);
    }

    /**
     * Create a BinaryInteractionWrapper from the given interaction which should contain not more than two participants
     * @param interaction
     * @return the new BinaryInteractionWrapper for this interaction
     * @throws IllegalArgumentException if the interaction contains more than two participants
     */
    public static ModelledBinaryInteraction createAndAddModelledBinaryeWrapperFor(ModelledInteraction interaction) {
        return new ModelledBinaryInteractionWrapper(interaction);
    }

    /**
     * Creates a new BinaryInteraction from the given interaction which should only contain one participant with a stoichiometry >= 2
     * @param interaction
     */
    public static BinaryInteraction createAndAddNewSelfBinaryInteraction(Interaction interaction) {
        if (interaction instanceof ModelledInteraction){
            ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction();
            ModelledInteraction modelledInteraction = (ModelledInteraction)interaction;
            InteractionCloner.copyAndOverrideModelledInteractionProperties(modelledInteraction, binary, true, true);
            InteractionCloner.copyAndOverrideModelledBinaryInteractionParticipants(modelledInteraction, binary, true, true);

            return binary;
        }
        else if (interaction instanceof InteractionEvidence){
            BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence();
            BinaryInteractionEvidence interactionEvidence = (BinaryInteractionEvidence)interaction;
            InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interactionEvidence, binary, true, true);
            InteractionCloner.copyAndOverrideBinaryInteractionEvidenceParticipants(interactionEvidence, binary, true, true);

            return binary;
        }
        else{
            BinaryInteraction<Participant> binary = new DefaultBinaryInteraction<Participant>();
            InteractionCloner.copyAndOverrideInteractionProperties(interaction, binary, true, true);
            InteractionCloner.copyAndOverrideBinaryInteractionParticipants(interaction, binary, true, true);
            return binary;
        }
    }

    /**
     * Creates a new BinaryInteraction from the given interaction which should only contain one participant with a stoichiometry >= 2
     * @param interaction
     */
    public static BinaryInteractionEvidence createAndAddNewSelfBinaryInteractionEvidence(InteractionEvidence interaction) {
        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence();
        BinaryInteractionEvidence interactionEvidence = (BinaryInteractionEvidence)interaction;
        InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interactionEvidence, binary, true, true);
        InteractionCloner.copyAndOverrideBinaryInteractionEvidenceParticipants(interactionEvidence, binary, true, true);

        return binary;
    }

    /**
     * Creates a new BinaryInteraction from the given interaction which should only contain one participant with a stoichiometry >= 2
     * @param interaction
     */
    public static ModelledBinaryInteraction createAndAddNewSelfModelledBinaryInteraction(ModelledInteraction interaction) {
        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction();
        ModelledInteraction modelledInteraction = (ModelledInteraction)interaction;
        InteractionCloner.copyAndOverrideModelledInteractionProperties(modelledInteraction, binary, true, true);
        InteractionCloner.copyAndOverrideModelledBinaryInteractionParticipants(modelledInteraction, binary, true, true);

        return binary;
    }
}
