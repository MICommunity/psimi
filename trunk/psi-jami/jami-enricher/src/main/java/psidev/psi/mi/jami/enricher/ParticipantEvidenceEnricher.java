package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ParticipantEvidenceFetcher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.participantevidence.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface ParticipantEvidenceEnricher {

    public void enrichParticipantEvidence(ParticipantEvidence participantEvidence) throws BadToEnrichFormException, MissingServiceException, BridgeFailedException, SeguidException, BadSearchTermException, BadResultException, BadEnrichedFormException;


    public void setParticipantEvidenceFetcher(ParticipantEvidenceFetcher fetcher);
    public ParticipantEvidenceFetcher getParticipantEvidenceFetcher();


    public void setParticipantEvidenceEnricherListener(ParticipantEvidenceEnricherListener listener);
    public ParticipantEvidenceEnricherListener getParticipantEvidenceEnricherListener();

    public void setProteinEnricher(ProteinEnricher proteinEnricher);
    public ProteinEnricher getProteinEnricher();
}
