package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * Full enricher for ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullModelledInteractionEnricher extends MinimalModelledInteractionEnricher{

    private FullInteractionEnricher interactionEnricher;

    public FullModelledInteractionEnricher() {
        this.interactionEnricher = new FullInteractionEnricher();
    }

    protected FullModelledInteractionEnricher(FullInteractionEnricher interactionEnricher) {
        this.interactionEnricher = interactionEnricher != null ? interactionEnricher : new FullInteractionEnricher();
    }

    @Override
    protected void processInteraction(ModelledInteraction interactionToEnrich) throws EnricherException {
        super.processInteraction(interactionToEnrich);

        // PROCESS RIGID
        this.interactionEnricher.processRigid(interactionToEnrich);
    }

    @Override
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {
        super.setCvTermEnricher(cvTermEnricher);
        this.interactionEnricher.setCvTermEnricher(cvTermEnricher);
    }

    @Override
    public void setParticipantEnricher(ParticipantEnricher<ModelledParticipant, ModelledFeature> participantEnricher) {
        super.setParticipantEnricher(participantEnricher);
        this.interactionEnricher.setParticipantEnricher(participantEnricher);
    }

    @Override
    public void setInteractionEnricherListener(InteractionEnricherListener listener) {
        super.setInteractionEnricherListener(listener);
        this.interactionEnricher.setInteractionEnricherListener(listener);
    }


}
