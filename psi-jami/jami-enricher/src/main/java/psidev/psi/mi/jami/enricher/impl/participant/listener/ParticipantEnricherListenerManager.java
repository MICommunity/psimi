package psidev.psi.mi.jami.enricher.impl.participant.listener;

import psidev.psi.mi.jami.enricher.listener.AbstractEnricherListenerManager;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 10/07/13
 */
public class ParticipantEnricherListenerManager
        extends AbstractEnricherListenerManager <ParticipantEnricherListener>
        implements ParticipantEnricherListener{


    public void onParticipantEnriched(Participant participant, EnrichmentStatus status, String message) {
        for(ParticipantEnricherListener listener : listenersList){
            listener.onParticipantEnriched(participant, status, message);
        }
    }
}
