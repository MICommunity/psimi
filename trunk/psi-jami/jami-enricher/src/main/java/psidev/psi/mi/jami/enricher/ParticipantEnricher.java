package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ParticipantFetcher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.Participant;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface ParticipantEnricher {

    public void enrichParticipant(Participant participantToEnrich) throws BadToEnrichFormException, MissingServiceException, BridgeFailedException, SeguidException, BadSearchTermException, BadResultException, BadEnrichedFormException;


    public void setParticipantFetcher(ParticipantFetcher fetcher);
    public ParticipantFetcher getParticipantFetcher();

    public void setParticipantListener(ParticipantEnricherListener listener);
    public ParticipantEnricherListener getParticipantEnricherListener();

    public void setProteinEnricher(ProteinEnricher proteinEnricher);
    public ProteinEnricher getProteinEnricher();

}
