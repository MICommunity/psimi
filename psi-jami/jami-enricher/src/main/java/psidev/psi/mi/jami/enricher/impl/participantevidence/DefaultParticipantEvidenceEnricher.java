package psidev.psi.mi.jami.enricher.impl.participantevidence;

import psidev.psi.mi.jami.bridges.fetcher.ParticipantEvidenceFetcher;
import psidev.psi.mi.jami.enricher.ParticipantEvidenceEnricher;
import psidev.psi.mi.jami.enricher.impl.participantevidence.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public class DefaultParticipantEvidenceEnricher
        implements ParticipantEvidenceEnricher{

    private ParticipantEvidenceFetcher participantEvidenceFetcher;
    protected ParticipantEvidenceEnricherListener listener;

    public void erichParticipantEvidence(ParticipantEvidence participantEvidence) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setParticipantEvidenceFetcher(ParticipantEvidenceFetcher participantEvidenceFetcher) {
        this.participantEvidenceFetcher = participantEvidenceFetcher;
    }


    public ParticipantEvidenceFetcher getParticipantEvidenceFetcher() {
        //Todo lazy load
        return participantEvidenceFetcher;
    }

    public void setParticipantEvidenceEnricherListener(ParticipantEvidenceEnricherListener participantEvidenceEnricherListener) {
        this.listener = participantEvidenceEnricherListener;
    }

    public ParticipantEvidenceEnricherListener getParticipantEvidenceEnricherListener() {
        return listener;
    }
}
