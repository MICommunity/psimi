package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ParticipantFetcher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.Protein;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 19/06/13
 */
public class AbstractParticipantEnricher
        implements ParticipantEnricher {


    private ParticipantFetcher fetcher;
    protected ParticipantEnricherListener listener;

    protected ProteinEnricher proteinEnricher;


    public void enrichParticipant(Participant participantToEnrich)
            throws BadToEnrichFormException, MissingServiceException, BridgeFailedException,
            SeguidException, BadSearchTermException, BadResultException,
            BadEnrichedFormException {


        if(participantToEnrich == null) throw new BadToEnrichFormException("Attempted to enrich a null participant.");

        CvTerm interactorType = participantToEnrich.getInteractor().getInteractorType();
        if(interactorType.getMIIdentifier().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR_MI)
                || interactorType.getShortName().equalsIgnoreCase(Interactor.UNKNOWN_INTERACTOR)
                || interactorType.getMIIdentifier().equalsIgnoreCase(Protein.PROTEIN_MI)
                || interactorType.getShortName().equalsIgnoreCase(Protein.PROTEIN)){

            if(getProteinEnricher() == null){
                throw new MissingServiceException("ProteinEnricher has not been provided.");
            }
            else {
                if(participantToEnrich.getInteractor() instanceof Protein){
                    getProteinEnricher().enrichProtein( (Protein) participantToEnrich.getInteractor() );
                } else {
                    if(listener != null) listener.onParticipantEnriched(participantToEnrich , "Failed. " +
                            "Found interactor of type "+interactorType.getShortName()+
                            " ("+interactorType.getMIIdentifier()+") "+
                            "but was not an instance of 'Protein, " +
                            "was "+participantToEnrich.getInteractor().getClass()+" instead.");
                    return;
                }

            }
        }
        if(listener != null) listener.onParticipantEnriched(participantToEnrich , "Success.");

    }

    public void setParticipantFetcher(ParticipantFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public ParticipantFetcher getParticipantFetcher() {
        //TODO lazy load
        return fetcher;
    }

    public void setParticipantListener(ParticipantEnricherListener participantEnricherListener) {
        this.listener = participantEnricherListener;
    }

    public ParticipantEnricherListener getParticipantEnricherListener() {
        return listener;
    }

    public void setProteinEnricher(ProteinEnricher proteinEnricher) {
        this.proteinEnricher = proteinEnricher;
    }

    public ProteinEnricher getProteinEnricher() {
        return proteinEnricher;
    }
}
