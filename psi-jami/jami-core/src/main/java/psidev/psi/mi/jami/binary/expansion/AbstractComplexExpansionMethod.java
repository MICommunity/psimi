package psidev.psi.mi.jami.binary.expansion;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.InteractionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract class for ComplexExpansionMethod
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/06/13</pre>
 */

public abstract class AbstractComplexExpansionMethod implements ComplexExpansionMethod {

    private CvTerm method;

    public AbstractComplexExpansionMethod(CvTerm method){
        if (method == null){
           throw new IllegalArgumentException("The method is mandatory to define a new ComplexExpansionMethod");
        }
        this.method = method;
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
            InteractionCategory category = InteractionUtils.findInteractionCategoryOf(interaction, true);

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

    protected abstract Collection<BinaryInteractionEvidence> expandInteractionEvidence(InteractionEvidence interaction);

    protected abstract Collection<ModelledBinaryInteraction> expandModelledInteraction(ModelledInteraction interaction);

    protected abstract Collection<BinaryInteraction> expandDefaultInteraction(Interaction interaction);

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