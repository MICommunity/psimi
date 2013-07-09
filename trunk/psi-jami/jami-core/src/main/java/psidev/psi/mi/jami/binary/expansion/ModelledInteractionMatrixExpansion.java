package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.impl.DefaultModelledBinaryInteraction;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultModelledParticipant;
import psidev.psi.mi.jami.utils.InteractionUtils;
import psidev.psi.mi.jami.utils.clone.InteractionCloner;

import java.util.Collection;
import java.util.Collections;

/**
 * Matrix expansion for ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/06/13</pre>
 */

public class ModelledInteractionMatrixExpansion extends AbstractMatrixExpansion<ModelledInteraction, ModelledBinaryInteraction>{

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
    protected <P extends Participant> ModelledBinaryInteraction createBinaryInteraction(ModelledInteraction interaction, P p1, P p2) {
        ModelledBinaryInteraction binary = new DefaultModelledBinaryInteraction(getMethod());
        InteractionCloner.copyAndOverrideModelledInteractionProperties(interaction, binary, false, true);
        binary.setParticipantA((ModelledParticipant)p1);
        binary.setParticipantB((ModelledParticipant)p2);
        return binary;
    }

    @Override
    protected ModelledParticipant[] createParticipantsArray(ModelledInteraction interaction) {
        return interaction.getParticipants().toArray(new DefaultModelledParticipant[]{});
    }
}
