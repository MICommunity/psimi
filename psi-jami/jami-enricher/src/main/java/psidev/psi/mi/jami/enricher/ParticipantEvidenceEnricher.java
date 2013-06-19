package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.ParticipantEvidenceFetcher;
import psidev.psi.mi.jami.enricher.impl.participantevidence.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface ParticipantEvidenceEnricher {

    public void erichParticipantEvidence(ParticipantEvidence participantEvidence);


    public void setParticipantEvidenceFetcher(ParticipantEvidenceFetcher fetcher);
    public ParticipantEvidenceFetcher getParticipantEvidenceFetcher();


    public void setParticipantEvidenceEnricherListener(ParticipantEvidenceEnricherListener listener);
    public ParticipantEvidenceEnricherListener getParticipantEvidenceEnricherListener();
}
