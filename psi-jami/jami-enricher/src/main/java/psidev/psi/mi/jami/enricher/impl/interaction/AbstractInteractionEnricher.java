package psidev.psi.mi.jami.enricher.impl.interaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.InteractionEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.interaction.listener.InteractionEnricherListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class AbstractInteractionEnricher<I extends Interaction, P extends Participant, F extends Feature>
        implements InteractionEnricher<I , P , F> {

    protected final Logger log = LoggerFactory.getLogger(AbstractInteractionEnricher.class.getName());

    protected InteractionEnricherListener listener;
    protected ParticipantEnricher<P , F> participantEnricher;
    protected CvTermEnricher cvTermEnricher;

    public void enrichInteraction(I interactionToEnrich) throws EnricherException {
        if ( interactionToEnrich == null) throw new IllegalArgumentException("Attempted to enrich null interactor.") ;

        if(getCvTermEnricher() != null) getCvTermEnricher().enrichCvTerm(interactionToEnrich.getInteractionType());

        if(getParticipantEnricher() != null)
            getParticipantEnricher().enrichParticipants(interactionToEnrich.getParticipants());

        if(getParticipantEnricher() != null){
            for(Object participant : interactionToEnrich.getParticipants()){
                getParticipantEnricher().enrichParticipant((P) participant);
            }
        }
    }


    public void setCvTermEnricher(CvTermEnricher cvTermEnricher){
        this.cvTermEnricher = cvTermEnricher;
    }

    public CvTermEnricher getCvTermEnricher(){
        return cvTermEnricher;
    }

    public void setParticipantEnricher(ParticipantEnricher<P , F> participantEnricher){
        this.participantEnricher = participantEnricher;
    }


    public ParticipantEnricher<P , F> getParticipantEnricher(){
        return participantEnricher;
    }

    public InteractionEnricherListener getInteractionEnricherListener() {
        return listener;
    }

    public void setInteractionEnricherListener(InteractionEnricherListener listener) {
        this.listener = listener;
    }
}
