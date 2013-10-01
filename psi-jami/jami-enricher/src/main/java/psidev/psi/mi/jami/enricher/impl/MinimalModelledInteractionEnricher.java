package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/08/13
 */
public class MinimalModelledInteractionEnricher extends MinimalInteractionEnricher<ModelledInteraction, ModelledParticipant, ModelledFeature> {

    @Override
    protected void processInteraction(ModelledInteraction interactionToEnrich) throws EnricherException {

        // PROCESS SOURCE
        processSource(interactionToEnrich);
    }

    protected void processSource(ModelledInteraction interactionToEnrich) throws EnricherException {
        if (interactionToEnrich.getSource() != null && getCvTermEnricher() != null){
             getCvTermEnricher().enrich(interactionToEnrich.getSource());
        }
    }
}
