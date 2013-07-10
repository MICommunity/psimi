package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.CvTermUpdaterMinimum;
import psidev.psi.mi.jami.enricher.impl.participant.ModelledParticipantUpdaterMaximum;
import psidev.psi.mi.jami.enricher.impl.participant.ModelledParticipantUpdaterMinimum;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class ModelledInteractionUpdaterMinimum
        extends InteractionUpdaterMinimum<ModelledInteraction, ModelledParticipant, ModelledFeature> {



    public ParticipantEnricher<ModelledParticipant, ModelledFeature> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new ModelledParticipantUpdaterMinimum();
        return participantEnricher;
    }
}
