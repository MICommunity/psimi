package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.binary.expansion.InteractionCategory;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultInteractionEvidence;

/**
 * Factory for experimental interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/02/13</pre>
 */

public class InteractionUtils {
    
    public static InteractionEvidence createEmptyBasicExperimentalInteraction(){

        return new DefaultInteractionEvidence(ExperimentUtils.createUnknownBasicExperiment());
    }

    /**
     * The method will find the interactionCategory (binary, self, etc.)
     * @param interaction
     * @return the InteractionCategory, null if the given interaction is null
     */
    public static InteractionCategory findInteractionCategoryOf(Interaction interaction){
        if (interaction == null){
            return null;
        }

        // only one participant, check stoichiometry
        if (interaction.getParticipants().size() == 1) {
            Participant p = interaction.getParticipants().iterator().next();

            // the stoichiometry is not specified
            if (p.getStoichiometry() == null || p.getStoichiometry().getMaxValue() == 0){
                // if we have self participants, then it is self_intra_molecular
                if (ParticipantUtils.isSelfParticipant(p) || ParticipantUtils.isPutativeSelfParticipant(p)){
                    return InteractionCategory.self_intra_molecular;
                }
                // we can consider that we have self inter molecular
                else {
                    return InteractionCategory.self_inter_molecular;
                }
            }
            // intra molecular
            else if (p.getStoichiometry().getMaxValue() == 1){
                 return InteractionCategory.self_intra_molecular;
            }
            else{
                return InteractionCategory.self_inter_molecular;
            }
        }
        else if (interaction.getParticipants().size() == 2){
            return InteractionCategory.binary;
        }
        else if (interaction.getParticipants().size() > 2){
            return InteractionCategory.n_ary;
        }

        return null;
    }
}
