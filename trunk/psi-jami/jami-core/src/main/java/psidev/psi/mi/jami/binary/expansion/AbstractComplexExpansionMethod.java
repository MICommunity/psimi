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

    public boolean isInteractionExpandable(Interaction interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isInteractionEvidenceExpandable(InteractionEvidence interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public boolean isModelledInteractionExpandable(ModelledInteraction interaction) {
        if (interaction == null || interaction.getParticipants().isEmpty()){
            return false;
        }
        return true;
    }

    public Collection<BinaryInteraction> expandInteraction(Interaction interaction) {

        Collection<BinaryInteraction> binaryInteractions = new ArrayList<BinaryInteraction>();

        InteractionCategory category = InteractionUtils.findInteractionCategoryOf(interaction, true);

        switch (category){
            case binary:
                binaryInteractions.add(InteractionUtils.createBinaryInteractionFrom(interaction));
                break;
            case self_intra_molecular:
                binaryInteractions.add(InteractionUtils.createBinaryInteractionFrom(interaction));
                break;
            case self_inter_molecular:
                binaryInteractions.add(InteractionUtils.createNewSelfBinaryInteractionFrom(interaction));
                break;
            case n_ary:
                binaryInteractions.addAll(collectDefaultBinaryInteractionsFrom(interaction));
                break;
            default:
                break;
        }

        return binaryInteractions;
    }

    public Collection<BinaryInteractionEvidence> expandInteractionEvidence(InteractionEvidence interaction) {

        Collection<BinaryInteractionEvidence> binaryInteractions = new ArrayList<BinaryInteractionEvidence>();

        InteractionCategory category = InteractionUtils.findInteractionEvidenceCategoryOf(interaction);

        switch (category){
            case binary:
                binaryInteractions.add(InteractionUtils.createBinaryInteractionEvidenceFrom(interaction));
                break;
            case self_intra_molecular:
                binaryInteractions.add(InteractionUtils.createBinaryInteractionEvidenceFrom(interaction));
                break;
            case self_inter_molecular:
                binaryInteractions.add(InteractionUtils.createAndAddNewSelfBinaryInteractionEvidence(interaction));
                break;
            case n_ary:
                binaryInteractions.addAll(collectBinaryInteractionEvidencesFrom(interaction));
                break;
            default:
                break;
        }

        return binaryInteractions;
    }

    public Collection<ModelledBinaryInteraction> expandModelledInteraction(ModelledInteraction interaction) {

        Collection<ModelledBinaryInteraction> binaryInteractions = new ArrayList<ModelledBinaryInteraction>();

        InteractionCategory category = InteractionUtils.findModelledInteractionCategoryOf(interaction);

        switch (category){
            case binary:
                binaryInteractions.add(InteractionUtils.createModelledBinaryInteractionFrom(interaction));
                break;
            case self_intra_molecular:
                binaryInteractions.add(InteractionUtils.createModelledBinaryInteractionFrom(interaction));
                break;
            case self_inter_molecular:
                binaryInteractions.add(InteractionUtils.createAndAddNewSelfModelledBinaryInteraction(interaction));
                break;
            case n_ary:
                binaryInteractions.addAll(collectModelledBinaryInteractionsFrom(interaction));
                break;
            default:
                break;
        }

        return binaryInteractions;
    }

    protected abstract Collection<BinaryInteractionEvidence> collectBinaryInteractionEvidencesFrom(InteractionEvidence interaction);

    protected abstract Collection<ModelledBinaryInteraction> collectModelledBinaryInteractionsFrom(ModelledInteraction interaction);

    protected abstract Collection<BinaryInteraction> collectDefaultBinaryInteractionsFrom(Interaction interaction);

    protected void initialiseBinaryInteractionParticipantsWith(Participant c1, Participant c2, BinaryInteraction binary) {
        binary.setParticipantA(c1);
        binary.setParticipantB(c2);
    }
}