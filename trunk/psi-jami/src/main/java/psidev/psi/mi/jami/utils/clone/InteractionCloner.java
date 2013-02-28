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
            target.setType(source.getType());
            target.setUpdatedDate(source.getUpdatedDate());

            // copy collections
            target.getAnnotations().clear();
            target.getAnnotations().addAll(source.getAnnotations());
            target.getChecksums().clear();
            target.getChecksums().addAll(source.getChecksums());
            target.getExperimentalConfidences().clear();
            target.getExperimentalConfidences().addAll(source.getExperimentalConfidences());
            target.getXrefs().clear();
            target.getXrefs().addAll(source.getXrefs());
            target.getIdentifiers().clear();
            target.getIdentifiers().addAll(source.getIdentifiers());
            target.getParticipantEvidences().clear();
            target.addAllParticipantEvidences(source.getParticipantEvidences());
            target.getExperimentalParameters().clear();
            target.getExperimentalParameters().addAll(source.getExperimentalParameters());
        }
    }
}
