package psidev.psi.mi.jami.enricher.impl.interaction;


import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.participant.ParticipantEvidenceEnricherMinimum;
import psidev.psi.mi.jami.model.*;

/**
 * An extension of the interaction enricher which only accepts InteractionEvidence
 * Overrides the default ParticipantEnricher to the evidence only form.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class InteractionEvidenceEnricherMinimum
    extends InteractionEnricherMinimum<InteractionEvidence, ParticipantEvidence, FeatureEvidence> {



    @Override
    public ParticipantEnricher<ParticipantEvidence, FeatureEvidence> getParticipantEnricher(){
        if(participantEnricher == null) participantEnricher = new ParticipantEvidenceEnricherMinimum();
        return participantEnricher;
    }
}
