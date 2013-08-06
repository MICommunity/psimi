package psidev.psi.mi.jami.enricher.impl.participant.listener;

import psidev.psi.mi.jami.enricher.listener.EnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public interface ParticipantEnricherListener extends EnricherListener<Participant> {

    public void onEnrichmentComplete(Participant participant, EnrichmentStatus status , String message);

}
