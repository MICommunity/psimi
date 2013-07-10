package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantEvidenceEnricherMaximum;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionEvidenceEnricherMaximum
    extends InteractionEnricherMaximum<InteractionEvidence, ParticipantEvidence, FeatureEvidence> {


    public ParticipantEnricher<ParticipantEvidence, FeatureEvidence> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new ParticipantEvidenceEnricherMaximum();
        return participantEnricher;
    }
}
