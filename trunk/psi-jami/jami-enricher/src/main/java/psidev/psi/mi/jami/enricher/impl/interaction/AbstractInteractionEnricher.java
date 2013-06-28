package psidev.psi.mi.jami.enricher.impl.interaction;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.interaction.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public abstract class AbstractInteractionEnricher
        implements InteractionEnricher {


    protected InteractionEnricherListener listener;

    protected ParticipantEnricher participantEnricher;
    protected CvTermEnricher cvTermEnricher;


    public void enrichInteraction(Interaction interactionToEnrich) throws EnricherException{

        if ( interactionToEnrich == null) throw new IllegalArgumentException("Attempted to enrich null interactor.") ;

        if(getCvTermEnricher() != null) getCvTermEnricher().enrichCvTerm(interactionToEnrich.getInteractionType());

        if(getParticipantEnricher() != null){
            for(Participant participant : interactionToEnrich.getParticipants())
            getParticipantEnricher().enrichParticipant(participant);
        }
    }


    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }

    public void setParticipantEnricher(ParticipantEnricher participantEnricher){
        this.participantEnricher = participantEnricher;
    }

    public ParticipantEnricher getParticipantEnricher(){
        return participantEnricher;
    }

    public InteractionEnricherListener getInteractionEnricherListener() {
        return listener;
    }

    public void setInteractionEnricherListener(InteractionEnricherListener listener) {
        this.listener = listener;
    }


}
