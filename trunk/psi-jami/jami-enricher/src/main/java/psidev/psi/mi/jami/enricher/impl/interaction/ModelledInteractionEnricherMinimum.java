package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.participant.ModelledParticipantUpdaterMaximum;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class ModelledInteractionEnricherMinimum
    extends InteractionEnricherMinimum<ModelledInteraction, ModelledParticipant , ModelledFeature> {

    public ParticipantEnricher<ModelledParticipant, ModelledFeature> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new ModelledParticipantUpdaterMaximum();
        return participantEnricher;
    }
}
