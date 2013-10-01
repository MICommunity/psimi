package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.InteractionEvidence;

/**
 * Full enricher for Interaction evidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullInteractionEvidenceEnricher extends MinimalInteractionEvidenceEnricher{

    private FullInteractionEnricher interactionEnricher;

    public FullInteractionEvidenceEnricher() {
        this.interactionEnricher = new FullInteractionEnricher();
    }

    protected FullInteractionEvidenceEnricher(FullInteractionEnricher interactionEnricher) {
        this.interactionEnricher = interactionEnricher != null ? interactionEnricher : new FullInteractionEnricher();
    }

    @Override
    protected void processInteraction(InteractionEvidence interactionToEnrich) throws EnricherException {
        super.processInteraction(interactionToEnrich);

        // PROCESS RIGID
        this.interactionEnricher.processRigid(interactionToEnrich);
    }
}