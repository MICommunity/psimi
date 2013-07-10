package psidev.psi.mi.jami.enricher.impl.participant.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:57
 */
public interface ParticipantEnricherListener extends EnricherListener {

    public void onParticipantEnriched(Participant participant, EnrichmentStatus status , String message);

}
