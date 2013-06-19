package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.utils.InteractionUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Bipartite expansion for ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class ModelledInteractionBipartiteExpansion extends AbstractBipartiteExpansion<ModelledInteraction> {

    @Override
    protected Collection<ModelledBinaryInteraction> createNewSelfBinaryInteractionsFrom(ModelledInteraction interaction) {
        return Collections.singletonList(InteractionUtils.createAndAddNewSelfModelledBinaryInteraction(interaction));
    }

    @Override
    protected Collection<ModelledBinaryInteraction> createBinaryInteractionsFrom(ModelledInteraction interaction) {
        return Collections.singletonList(InteractionUtils.createModelledBinaryInteractionFrom(interaction));
    }

    @Override
    protected InteractionCategory findInteractionCategory(ModelledInteraction interaction) {
        return InteractionUtils.findModelledInteractionCategoryOf(interaction);
    }

    @Override
    protected Collection<ModelledBinaryInteraction> collectBinaryInteractionsFrom(ModelledInteraction interaction){
        ModelledParticipant externalEntity =  createParticipantForComplexEntity(createComplexEntity(interaction));

        Collection<ModelledBinaryInteraction> binaryInteractions = new ArrayList<ModelledBinaryInteraction>(interaction.getParticipants().size());
        for ( ModelledParticipant p : interaction.getParticipants() ) {

            // build a new interaction
            ModelledBinaryInteraction binary = createBinaryInteraction(interaction, externalEntity, p);

            binaryInteractions.add(binary);
        }

        return binaryInteractions;
    }

    @Override
    protected ModelledBinaryInteraction createBinaryInteraction(ModelledInteraction interaction, Participant p1, Participant p2){
        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction(getMethod());
        InteractionCloner.copyAndOverrideModelledInteractionProperties(interaction, binary, false, true);
        binary.setParticipantA((ModelledParticipant)p1);
        binary.setParticipantB((ModelledParticipant)p2);
        return binary;
    }

    @Override
    protected ModelledParticipant createParticipantForComplexEntity(Complex complexEntity){
        return new DefaultModelledParticipant(complexEntity);
    }
}
