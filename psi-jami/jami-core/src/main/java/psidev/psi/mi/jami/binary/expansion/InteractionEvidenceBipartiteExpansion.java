package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.impl.DefaultBinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.InteractionUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * BipartiteExpansion for InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class InteractionEvidenceBipartiteExpansion extends AbstractBipartiteExpansion<InteractionEvidence, BinaryInteractionEvidence>{

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
    protected Collection<BinaryInteractionEvidence> collectBinaryInteractionsFrom(InteractionEvidence interaction){
        ParticipantEvidence externalEntity =  createParticipantForComplexEntity(createComplexEntity(interaction));

        Collection<BinaryInteractionEvidence> binaryInteractions = new ArrayList<BinaryInteractionEvidence>(interaction.getParticipants().size());
        for ( ParticipantEvidence p : interaction.getParticipants() ) {

            // build a new interaction
            BinaryInteractionEvidence binary = createBinaryInteraction(interaction, externalEntity, p);

            binaryInteractions.add(binary);
        }

        return binaryInteractions;
    }

    @Override
    protected <P extends Participant> BinaryInteractionEvidence createBinaryInteraction(InteractionEvidence interaction, P c1, P c2){
        BinaryInteractionEvidence binary = new DefaultBinaryInteractionEvidence(getMethod());
        InteractionCloner.copyAndOverrideInteractionEvidenceProperties(interaction, binary, false, true);
        binary.setParticipantA((ParticipantEvidence)c1);
        binary.setParticipantB((ParticipantEvidence)c2);
        return binary;
    }

    @Override
    protected ParticipantEvidence createParticipantForComplexEntity(Complex complexEntity){
        return new DefaultParticipantEvidence(complexEntity);
    }
}
