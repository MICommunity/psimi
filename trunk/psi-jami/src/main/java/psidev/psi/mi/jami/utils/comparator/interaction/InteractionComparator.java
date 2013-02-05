package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.ComponentComparator;

import java.util.Comparator;

/**
 * Generic interaction comparator.
 * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
 * - It uses ExperimentalInteractionComparator to compare experimental interactions
 * - It uses ModelledInteractionComparator to compare modelled interactions
 * - It uses CooperativeInteractionComparator to compare cooperative interactions
 * - It uses AllostericInteractionComparator to compare allosteric interactions
 * - It uses InteractionBaseComparator to compare basic interaction properties
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class InteractionComparator implements Comparator<Interaction> {

    protected InteractionBaseComparator interactionBaseComparator;
    protected ExperimentalInteractionComparator experimentalInteractionComparator;
    protected ModelledInteractionComparator modelledInteractionComparator;
    protected CooperativeInteractionComparator cooperativeInteractionComparator;
    protected AllostericInteractionComparator allostericInteractionComparator;

    public InteractionComparator(InteractionBaseComparator interactionBaseComparator, ExperimentalInteractionComparator experimentalInteractionComparator,
                                 CooperativeInteractionComparator cooperativeInteractionComparator, AllostericInteractionComparator allostericInteractionComparator){
        if (interactionBaseComparator == null){
            throw new IllegalArgumentException("The interactionBaseComparator is required to create more specific interaction comparators and compares basic interaction properties. It cannot be null");
        }
        this.interactionBaseComparator = interactionBaseComparator;
        if (experimentalInteractionComparator == null){
            throw new IllegalArgumentException("The experimentalInteractionComparator is required to compare experimental interactions. It cannot be null");
        }
        this.experimentalInteractionComparator = experimentalInteractionComparator;
        this.modelledInteractionComparator = new ModelledInteractionComparator(this.interactionBaseComparator);
        if (cooperativeInteractionComparator == null){
            throw new IllegalArgumentException("The cooperativeInteraction is required to compare cooperative interactions. It cannot be null");
        }
        this.cooperativeInteractionComparator = cooperativeInteractionComparator;
        if (allostericInteractionComparator == null){
            throw new IllegalArgumentException("The allostericInteraction is required to compare allosteric interactions. It cannot be null");
        }
        this.allostericInteractionComparator = allostericInteractionComparator;
    }

    public InteractionBaseComparator getInteractionBaseComparator() {
        return interactionBaseComparator;
    }

    public ExperimentalInteractionComparator getExperimentalInteractionComparator() {
        return experimentalInteractionComparator;
    }

    public ModelledInteractionComparator getModelledInteractionComparator() {
        return modelledInteractionComparator;
    }

    public CooperativeInteractionComparator getCooperativeInteractionComparator() {
        return cooperativeInteractionComparator;
    }

    public AllostericInteractionComparator getAllostericInteractionComparator() {
        return allostericInteractionComparator;
    }

    /**
     * Experimental interactions come first, then allosteric interactions, then cooperative interactions, then modelled interactions.
     * - It uses ExperimentalInteractionComparator to compare experimental interactions
     * - It uses ModelledInteractionComparator to compare modelled interactions
     * - It uses CooperativeInteractionComparator to compare cooperative interactions
     * - It uses AllostericInteractionComparator to compare allosteric interactions
     * - It uses InteractionBaseComparator to compare basic interaction properties
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
            boolean isExperimentalInteraction1 = interaction1 instanceof ExperimentalInteraction;
            boolean isExperimentalInteraction2 = interaction2 instanceof ExperimentalInteraction;
            if (isExperimentalInteraction1 && isExperimentalInteraction2){
                return experimentalInteractionComparator.compare((ExperimentalInteraction) interaction1, (ExperimentalInteraction) interaction2);
            }
            // the experimental interaction is before
            else if (isExperimentalInteraction1){
                return BEFORE;
            }
            else if (isExperimentalInteraction2){
                return AFTER;
            }
            else {
                // both are allosteric interactions
                boolean isAllostericInteraction1 = interaction1 instanceof AllostericInteraction;
                boolean isAllostericInteraction2 = interaction2 instanceof AllostericInteraction;
                if (isAllostericInteraction1 && isAllostericInteraction2){
                    return allostericInteractionComparator.compare((AllostericInteraction) interaction1, (AllostericInteraction) interaction2);
                }
                // the allosteric interaction is before
                else if (isAllostericInteraction1){
                    return BEFORE;
                }
                else if (isAllostericInteraction2){
                    return AFTER;
                }
                else {

                    // both are cooperative interactions
                    boolean isCooperativeInteraction1 = interaction1 instanceof CooperativeInteraction;
                    boolean isCooperativeInteraction2 = interaction2 instanceof CooperativeInteraction;
                    if (isCooperativeInteraction1 && isCooperativeInteraction2){
                        return cooperativeInteractionComparator.compare((CooperativeInteraction) interaction1, (CooperativeInteraction) interaction2);
                    }
                    // the cooperative interaction is before
                    else if (isCooperativeInteraction1){
                        return BEFORE;
                    }
                    else if (isCooperativeInteraction2){
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
    }
}
