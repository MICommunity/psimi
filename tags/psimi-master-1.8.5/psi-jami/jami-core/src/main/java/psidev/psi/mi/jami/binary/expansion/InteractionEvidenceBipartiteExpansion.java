package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultParticipantEvidence;
import psidev.psi.mi.jami.utils.InteractionUtils;

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
        return Collections.singletonList(getBinaryInteractionFactory().createSelfBinaryInteractionEvidenceFrom(interaction));
    }

    @Override
    protected Collection<BinaryInteractionEvidence> createBinaryInteractionWrappersFrom(InteractionEvidence interaction) {
        return Collections.singletonList(getBinaryInteractionFactory().createBinaryInteractionEvidenceWrapperFrom(interaction));
    }

    @Override
    protected ComplexType findInteractionCategory(InteractionEvidence interaction) {
        return InteractionUtils.findInteractionEvidenceCategoryOf(interaction);
    }

    @Override
    protected Collection<BinaryInteractionEvidence> collectBinaryInteractionsFromNary(InteractionEvidence interaction){
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
        return getBinaryInteractionFactory().createBinaryInteractionEvidenceFrom(interaction, (ParticipantEvidence)c1, (ParticipantEvidence)c2, getMethod());
    }

    @Override
    protected ParticipantEvidence createParticipantForComplexEntity(Complex complexEntity){
        return new DefaultParticipantEvidence(complexEntity);
    }
}
