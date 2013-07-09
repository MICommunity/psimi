package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public interface InteractionEnricher <I extends Interaction ,  P extends Participant , F extends Feature> {

    public void enrichInteraction(I interactionToEnrich) throws EnricherException;

    public void setParticipantEnricher(ParticipantEnricher<P , F> participantEnricher);

}
