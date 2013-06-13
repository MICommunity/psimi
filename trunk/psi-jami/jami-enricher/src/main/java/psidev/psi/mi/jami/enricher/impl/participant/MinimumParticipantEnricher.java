package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.bridges.fetcher.ParticipantFetcher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:37
 */
public class MinimumParticipantEnricher
        implements ParticipantEnricher  {

    private ParticipantFetcher participantFetcher;
    protected ParticipantEnricherListener listener;


    public void enrichParticipant(Participant participantToEnrich) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setParticipantFetcher(ParticipantFetcher participantFetcher) {
        this.participantFetcher = participantFetcher;
    }

    public ParticipantFetcher getParticipantFetcher() {
        //TODO lazy load
        return participantFetcher;
    }

    public void setParticipantListener(ParticipantEnricherListener participantEnricherListener) {
        this.listener = participantEnricherListener;
    }

    public ParticipantEnricherListener getParticipantEnricherListener() {
        return listener;
    }
}
