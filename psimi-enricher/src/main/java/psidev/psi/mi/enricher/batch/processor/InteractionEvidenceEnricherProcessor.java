package psidev.psi.mi.enricher.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Spring batch processor that enriches the interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/07/13</pre>
 */

public class InteractionEvidenceEnricherProcessor implements ItemProcessor<InteractionEvidence, InteractionEvidence>{

    private InteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence> interactionEnricher;

    public InteractionEvidence process(InteractionEvidence item) throws Exception {
        if (this.interactionEnricher == null){
            throw new IllegalStateException("The InteractionEvidenceEnricherProcessor needs a non null InteractionEnricher.");
        }
        if (item == null){
            return null;
        }

        // enrich interaction
        interactionEnricher.enrichInteraction(item);

        return item;
    }

    public void setInteractionEnricher(InteractionEnricher<InteractionEvidence, ParticipantEvidence, FeatureEvidence> interactionEnricher) {
        this.interactionEnricher = interactionEnricher;
    }
}
