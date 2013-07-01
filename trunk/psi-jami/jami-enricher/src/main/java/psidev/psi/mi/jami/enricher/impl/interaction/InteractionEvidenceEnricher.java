package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/07/13
 */
public class InteractionEvidenceEnricher
        extends AbstractInteractionEnricher <InteractionEvidence>{

    protected ParticipantEnricher<ParticipantEvidence> participantEnricher;


    public void enrichInteraction(InteractionEvidence interactionToEnrich) throws EnricherException {
        super.enrichInteraction(interactionToEnrich);
    }

    /*public void setParticipantEnricher(ParticipantEnricher<ParticipantEvidence> participantEnricher){
        this.participantEnricher = participantEnricher;
    } */
}
