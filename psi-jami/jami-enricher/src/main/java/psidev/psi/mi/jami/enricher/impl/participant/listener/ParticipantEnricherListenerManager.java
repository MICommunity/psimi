package psidev.psi.mi.jami.enricher.impl.participant.listener;


import psidev.psi.mi.jami.enricher.listener.EnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class ParticipantEnricherListenerManager
        extends EnricherListenerManager<ParticipantEnricherListener>
        implements ParticipantEnricherListener{


    public ParticipantEnricherListenerManager(ParticipantEnricherListener... listeners){
        super(listeners);
    }



    public void onParticipantEnriched(Participant participant, EnrichmentStatus status, String message) {
        for(ParticipantEnricherListener listener : listenersList){
            listener.onParticipantEnriched(participant, status, message);
        }
    }
}
