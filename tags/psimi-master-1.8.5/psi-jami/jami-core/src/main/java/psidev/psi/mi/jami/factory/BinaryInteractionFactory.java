package psidev.psi.mi.jami.factory;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;

/**
 * An interface for a factory of BinaryInteractions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */

public interface BinaryInteractionFactory {

    /**
     * Create a new BinaryInteraction object from an Interaction parent and two participants
     * @param interaction : the original interaction
     * @param p1 : the first participant
     * @param p2 : the second participant
     * @return the new BinaryInteraction object
     */
    public BinaryInteraction createBasicBinaryInteractionFrom(Interaction interaction, Participant p1, Participant p2, CvTerm expansionMethod);

    /**
     * Create a new BinaryInteractionEvidence object from an InteractionEvidence parent and two participants evidences
     * @param interaction : the original interaction
     * @param p1 : the first participant
     * @param p2 : the second participant
     * @param expansionMethod  : the expansion method
     * @return the new BinaryInteractionEvidence object
     */
    public BinaryInteractionEvidence createBinaryInteractionEvidenceFrom(InteractionEvidence interaction, ParticipantEvidence p1, ParticipantEvidence p2, CvTerm expansionMethod);

    /**
     * Create a new ModelledBinaryInteraction object from a ModelledInteraction parent and two modelled participants
     * @param interaction : the original interaction
     * @param p1 : the first participant
     * @param p2 : the second participant
     * @param expansionMethod : the expansion method
     * @return the new ModelledBinaryInteraction object
     */
    public ModelledBinaryInteraction createModelledBinaryInteractionFrom(ModelledInteraction interaction, ModelledParticipant p1, ModelledParticipant p2, CvTerm expansionMethod);

    /**
     * Create a binary interaction representing a self interaction
     * @param interaction : the original interaction
     * @return the resulting self binary interaction
     */
    public BinaryInteraction createSelfBinaryInteractionFrom(Interaction interaction);

    /**
     * Creates a BinaryInteractionEvidence representing a self interaction
     * @param interaction : the original interaction
     * @return the resulting self binary interaction
     */
    public BinaryInteractionEvidence createSelfBinaryInteractionEvidenceFrom(InteractionEvidence interaction);

    /**
     * Creates a ModelledInteraction representing a self interaction
     * @param interaction : the original interaction
     * @return the resulting self binary interaction
     */
    public ModelledBinaryInteraction createSelfModelledBinaryInteractionFrom(ModelledInteraction interaction);

    /**
     * Creates a BinaryInteraction that wraps the given interaction
     * @param interaction : the original interaction
     * @return the resulting binary interaction wrapper
     */
    public BinaryInteraction createBinaryInteractionWrapperFrom(Interaction interaction);

    /**
     * Creates a BinaryInteractionEvidence that wraps the given interaction
     * @param interaction : the original interaction
     * @return the resulting binary interaction wrapper
     */
    public BinaryInteractionEvidence createBinaryInteractionEvidenceWrapperFrom(InteractionEvidence interaction);

    /**
     * Creates a ModelledBinaryInteraction that wraps the given interaction
     * @param interaction : the original interaction
     * @return the resulting binary interaction wrapper
     */
    public ModelledBinaryInteraction createModelledBinaryInteractionWrapperFrom(ModelledInteraction interaction);

    /**
     * Instantiate a new BinaryInteraction
     * @return the binary interaction instance
     */
    public BinaryInteraction instantiateNewBinaryInteraction();

    /**
     * Instantiate a new BinaryInteraction evidence
     * @return the binary interaction instance
     */
    public BinaryInteractionEvidence instantiateNewBinaryInteractionEvidence();

    /**
     * Instantiate a new modelled binary interaction
     * @return the binary interaction instance
     */
    public ModelledBinaryInteraction instantiateNewModelledBinaryInteraction();
}
