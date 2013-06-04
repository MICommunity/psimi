package psidev.psi.mi.jami.utils.clone;

import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class InteractionCloner {

    /***
     * This method will copy properties of interaction source in interaction target and will override all the other properties of Target interaction.
     * This method will set the experiment of this interaction evidence but it will not add this interaction to the list of interactionEvidences
     * This method will add all the participant evidences of the source but will not set their interactionEvidence to the target
     * @param source
     * @param target
     */
    public static void copyAndOverrideInteractionEvidenceProperties(InteractionEvidence source, InteractionEvidence target){
        if (source != null && target != null){
            target.setAvailability(source.getAvailability());
            target.setExperiment(source.getExperiment());
            target.setInferred(source.isInferred());
            target.setCreatedDate(source.getCreatedDate());
            target.setNegative(source.isNegative());
            target.setShortName(source.getShortName());
            target.setInteractionType(source.getInteractionType());
            target.setUpdatedDate(source.getUpdatedDate());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getChecksums().clear();
            target.getChecksums().addAll(source.getChecksums());
            target.getConfidences().clear();
            target.getConfidences().addAll(source.getConfidences());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getParticipants().clear();
            target.getParticipants().addAll(source.getParticipants());
            target.getParameters().clear();
            target.getParameters().addAll(source.getParameters());
        }
    }
}
