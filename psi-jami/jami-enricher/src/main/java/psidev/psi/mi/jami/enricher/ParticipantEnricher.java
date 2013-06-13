package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.ParticipantFetcher;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:30
 */
public interface ParticipantEnricher {

    public void enrichParticipant(Participant participantToEnrich);


    public void setParticipantFetcher(ParticipantFetcher participantFetcher);
    public ParticipantFetcher getParticipantFetcher();

    public void setParticipantListener(ParticipantEnricherListener participantEnricherListener);
    public ParticipantEnricherListener getParticipantEnricherListener();


}
