package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface InteractionEnricher <T extends Interaction> {

    public void enrichInteraction(T interactionToEnrich) throws EnricherException;

    public void setParticipantEnricher(ParticipantEnricher<? extends Participant> participantEnricher);


}
