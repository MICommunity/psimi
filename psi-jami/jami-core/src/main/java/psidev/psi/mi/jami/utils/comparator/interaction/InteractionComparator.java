package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.*;

import java.util.Comparator;

/**
 * Generic interaction comparator.
 * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
 * - It uses InteractionEvidenceComparator to compare experimental interactions
 * - It uses ModelledInteractionComparator to compare modelled interactions
 * - It uses CooperativeInteractionComparator to compare cooperative interactions
 * - It uses AllostericInteractionComparator to compare allosteric interactions
 * - It uses AbstractInteractionBaseComparator to compare basic interaction properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class InteractionComparator implements Comparator<Interaction> {

    protected Comparator<Interaction> interactionBaseComparator;
    protected InteractionEvidenceComparator experimentalInteractionComparator;
    protected ModelledInteractionComparator modelledInteractionComparator;

    public InteractionComparator(Comparator<Interaction> interactionBaseComparator, ModelledInteractionComparator modelledInteractionComparator, InteractionEvidenceComparator experimentalInteractionComparator){
        if (interactionBaseComparator == null){
            throw new IllegalArgumentException("The interactionBaseComparator is required to create more specific interaction comparators and compares basic interaction properties. It cannot be null");
        }
        this.interactionBaseComparator = interactionBaseComparator;
        if (experimentalInteractionComparator == null){
            throw new IllegalArgumentException("The experimentalInteractionComparator is required to compare experimental interactions. It cannot be null");
        }
        this.experimentalInteractionComparator = experimentalInteractionComparator;
        if (experimentalInteractionComparator == null){
            throw new IllegalArgumentException("The modlledInteractionComparator is required to compare modelled interactions. It cannot be null");
        }
        this.modelledInteractionComparator = modelledInteractionComparator;
    }

    public Comparator<Interaction> getInteractionBaseComparator() {
        return interactionBaseComparator;
    }

    public InteractionEvidenceComparator getExperimentalInteractionComparator() {
        return experimentalInteractionComparator;
    }

    public ModelledInteractionComparator getModelledInteractionComparator() {
        return modelledInteractionComparator;
    }

    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * - It uses InteractionEvidenceComparator to compare experimental interactions
     * - It uses ModelledInteractionComparator to compare modelled interactions
     * - It uses CooperativeInteractionComparator to compare cooperative interactions
     * - It uses AllostericInteractionComparator to compare allosteric interactions
     * - It uses AbstractInteractionBaseComparator to compare basic interaction properties
     * @param interaction1
     * @param interaction2
     * @return
     */
    public int compare(Interaction interaction1, Interaction interaction2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (interaction1 == null && interaction2 == null){
            return EQUAL;
        }
        else if (interaction1 == null){
            return AFTER;
        }
        else if (interaction2 == null){
            return BEFORE;
        }
        else {
            // first check if both interactions are from the same interface

            // both are experimental interactions
            boolean isExperimentalInteraction1 = interaction1 instanceof InteractionEvidence;
            boolean isExperimentalInteraction2 = interaction2 instanceof InteractionEvidence;
            if (isExperimentalInteraction1 && isExperimentalInteraction2){
                return experimentalInteractionComparator.compare((InteractionEvidence) interaction1, (InteractionEvidence) interaction2);
            }
            // the experimental interaction is before
            else if (isExperimentalInteraction1){
                return BEFORE;
            }
            else if (isExperimentalInteraction2){
                return AFTER;
            }
            else {
                // both are modelled interactions
                boolean isModelledInteraction1 = interaction1 instanceof ModelledInteraction;
                boolean isModelledInteraction2 = interaction2 instanceof ModelledInteraction;
                if (isModelledInteraction1 && isModelledInteraction2){
                    return modelledInteractionComparator.compare((ModelledInteraction) interaction1, (ModelledInteraction) interaction2);
                }
                // the modelled interaction is before
                else if (isModelledInteraction1){
                    return BEFORE;
                }
                else if (isModelledInteraction2){
                    return AFTER;
                }
                else {
                    return interactionBaseComparator.compare(interaction1, interaction2);
                }
            }
        }
    }
}
