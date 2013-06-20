package psidev.psi.mi.jami.enricher.impl.participantevidence;

import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ParticipantEvidenceFetcher;
import psidev.psi.mi.jami.bridges.fetcher.ParticipantFetcher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEvidenceEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.BadEnrichedFormException;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.participant.listener.ParticipantEnricherListener;
import psidev.psi.mi.jami.enricher.impl.participantevidence.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.*;
import uk.ac.ebi.intact.irefindex.seguid.SeguidException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 19/06/13
 */
public class AbstractParticipantEvidenceEnricher
        implements ParticipantEvidenceEnricher {


    private ParticipantEvidenceFetcher fetcher;
    protected ParticipantEvidenceEnricherListener listener;

    protected ProteinEnricher proteinEnricher;


    public void enrichParticipantEvidence(ParticipantEvidence participantToEnrich)
            throws BadToEnrichFormException, MissingServiceException, BridgeFailedException,
            SeguidException, BadSearchTermException, BadResultException,
            BadEnrichedFormException {

        if(participantToEnrich == null) throw new BadToEnrichFormException("Attempted to enrich a null participant.");



        if(participantToEnrich.getInteractor() instanceof Polymer){
            //Get the old sequence to allow for feature enrichment
            String sequenceOld = ((Polymer)participantToEnrich.getInteractor()).getSequence();

            if(participantToEnrich.getInteractor() instanceof Protein){
                getProteinEnricher().enrichProtein( (Protein) participantToEnrich.getInteractor() );
            }
            else {
                if(listener != null) listener.onParticipantEvidenceEnriched(participantToEnrich , "Failed. " +
                        "Found interactor without service.");
                return;
            }


            //ELSE Polymer
        }//ELSE
        else {
            if(listener != null) listener.onParticipantEvidenceEnriched(participantToEnrich , "Failed. " +
                    "Found interactor without service.");
            return;
        }


        if(listener != null) listener.onParticipantEvidenceEnriched(participantToEnrich , "Success.");

    }

    public void setParticipantEvidenceFetcher(ParticipantEvidenceFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public ParticipantEvidenceFetcher getParticipantEvidenceFetcher() {
        //TODO lazy load
        return fetcher;
    }

    public void setParticipantEvidenceEnricherListener(ParticipantEvidenceEnricherListener participantEnricherListener) {
        this.listener = participantEnricherListener;
    }

    public ParticipantEvidenceEnricherListener getParticipantEvidenceEnricherListener() {
        return listener;
    }

    public void setProteinEnricher(ProteinEnricher proteinEnricher) {
        this.proteinEnricher = proteinEnricher;
    }

    public ProteinEnricher getProteinEnricher() {
        return proteinEnricher;
    }
}
