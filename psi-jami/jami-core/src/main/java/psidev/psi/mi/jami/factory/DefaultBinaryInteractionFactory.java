package psidev.psi.mi.jami.factory;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.*;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

/**
 * A factory to create default BinaryInteractions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public class DefaultBinaryInteractionFactory implements BinaryInteractionFactory{

    public BinaryInteraction createBasicBinaryInteractionFrom(Interaction interaction, Participant p1, Participant p2, CvTerm expansionMethod){
        BinaryInteraction binary = instantiateNewBinaryInteraction();
        binary.setComplexExpansion(expansionMethod);
        InteractionCloner.copyAndOverrideBasicInteractionProperties(interaction, binary, false, true);
        binary.setParticipantA(p1);
        binary.setParticipantB(p2);
        return binary;
    }

    public BinaryInteractionEvidence createBinaryInteractionEvidenceFrom(InteractionEvidence interaction, ParticipantEvidence p1, ParticipantEvidence p2, CvTerm expansionMethod) {
        BinaryInteractionEvidence binary = instantiateNewBinaryInteractionEvidence();
        binary.setComplexExpansion(expansionMethod);
        InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, binary, false, true);
        binary.setParticipantA(p1);
        binary.setParticipantB(p2);
        return binary;
    }

    public ModelledBinaryInteraction createModelledBinaryInteractionFrom(ModelledInteraction interaction, ModelledParticipant p1, ModelledParticipant p2, CvTerm expansionMethod) {
        ModelledBinaryInteraction binary = instantiateNewModelledBinaryInteraction();
        binary.setComplexExpansion(expansionMethod);
        InteractionCloner.copyAndOverrideModelledInteractionProperties(interaction, binary, false, true);
        binary.setParticipantA(p1);
        binary.setParticipantB(p2);
        return binary;    }

    public BinaryInteraction createSelfBinaryInteractionFrom(Interaction interaction) {
        BinaryInteraction<Participant> binary = instantiateNewBinaryInteraction();
        InteractionCloner.copyAndOverrideBasicInteractionProperties(interaction, binary, false, true);
        InteractionCloner.copyAndOverrideBasicParticipantsToBinary(interaction, binary, false, true);
        return binary;
    }

    public BinaryInteractionEvidence createSelfBinaryInteractionEvidenceFrom(InteractionEvidence interaction) {
        BinaryInteractionEvidence binary = instantiateNewBinaryInteractionEvidence();
        InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, binary, false, true);
        InteractionCloner.copyAndOverrideParticipantsEvidencesToBinary(interaction, binary, false, true);

        return binary;
    }

    public ModelledBinaryInteraction createSelfModelledBinaryInteractionFrom(ModelledInteraction interaction) {
        ModelledBinaryInteraction binary = instantiateNewModelledBinaryInteraction();
        InteractionCloner.copyAndOverrideModelledInteractionProperties(interaction, binary, false, true);
        InteractionCloner.copyAndOverrideModelledParticipantsToBinary(interaction, binary, false, true);

        return binary;
    }

    public BinaryInteractionWrapper createBinaryInteractionWrapperFrom(Interaction interaction) {
        return new BinaryInteractionWrapper(interaction);
    }

    public BinaryInteractionEvidenceWrapper createBinaryInteractionEvidenceWrapperFrom(InteractionEvidence interaction) {
        return new BinaryInteractionEvidenceWrapper(interaction);
    }

    public ModelledBinaryInteractionWrapper createModelledBinaryInteractionWrapperFrom(ModelledInteraction interaction) {
        return new ModelledBinaryInteractionWrapper(interaction);
    }

    public BinaryInteraction instantiateNewBinaryInteraction() {
        return new DefaultBinaryInteraction();
    }

    public BinaryInteractionEvidence instantiateNewBinaryInteractionEvidence() {
        return new DefaultBinaryInteractionEvidence();
    }

    public ModelledBinaryInteraction instantiateNewModelledBinaryInteraction() {
        return new DefaultModelledBinaryInteraction();
    }
}
