package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.ParticipantEvidenceFetcher;
import psidev.psi.mi.jami.enricher.impl.participantevidence.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:32
 */
public interface ParticipantEvidenceEnricher {

    public void erichParticipantEvidence(ParticipantEvidence participantEvidence);


    public void setParticipantEvidenceFetcher(ParticipantEvidenceFetcher participantEvidenceFetcher);
    public ParticipantEvidenceFetcher getParticipantEvidenceFetcher();


    public void setParticipantEvidenceEnricherListener(
            ParticipantEvidenceEnricherListener participantEvidenceEnricherListener);

    public ParticipantEvidenceEnricherListener getParticipantEvidenceEnricherListener();
}
