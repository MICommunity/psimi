package psidev.psi.mi.jami.utils.comparator.interaction;

import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ModelledInteraction;

/**
 * Default exact curated Generic interaction comparator.
 * Modelled interactions come first and then experimental interactions
 * - It uses DefaultExactCuratedInteractionEvidenceComparator to compare experimental interactions
 * - It uses DefaultExactCuratedModelledInteractionComparator to compare modelled interactions
 * - It uses DefaultCuratedInteractionBaseComparator to compare basic interaction properties
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultExactCuratedInteractionComparator {

    /**
     * Use DefaultExactCuratedInteractionComparator to know if two interactions are equals.
     * @param interaction1
     * @param interaction2
     * @return true if the two interactions are equal
     */
    public static boolean areEquals(Interaction interaction1, Interaction interaction2){
        if (interaction1 == interaction2){
            return true;
        }
        else if (interaction1 == null || interaction2 == null){
            return false;
        }
        else {
            // first check if both interactions are from the same interface

            // both are modelled interactions
            boolean isModelledInteraction1 = interaction1 instanceof ModelledInteraction;
            boolean isModelledInteraction2 = interaction2 instanceof ModelledInteraction;
            if (isModelledInteraction1 && isModelledInteraction2){
                return DefaultExactCuratedModelledInteractionComparator.areEquals((ModelledInteraction) interaction1, (ModelledInteraction) interaction2);
            }
            // the modelled interaction is before
            else if (isModelledInteraction1 || isModelledInteraction2){
                return false;
            }
            else {
                // both are experimental interactions
                boolean isExperimentalInteraction1 = interaction1 instanceof InteractionEvidence;
                boolean isExperimentalInteraction2 = interaction2 instanceof InteractionEvidence;
                if (isExperimentalInteraction1 && isExperimentalInteraction2){
                    return DefaultExactCuratedInteractionEvidenceComparator.areEquals((InteractionEvidence) interaction1, (InteractionEvidence) interaction2);
                }
                // the experimental interaction is before
                else if (isExperimentalInteraction1 || isExperimentalInteraction2){
                    return false;
                }
                else {
                    return DefaultCuratedInteractionBaseComparator.areEquals(interaction1, interaction2);
                }
            }
        }
    }
}
