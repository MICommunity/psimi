package psidev.psi.mi.jami.enricher.impl.participantevidence;


import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.participantevidence.listener.ParticipantEvidenceEnricherListener;
import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 19/06/13
 */
@Deprecated
public class AbstractParticipantEvidenceEnricher
       {



    protected ParticipantEvidenceEnricherListener listener;

    protected ProteinEnricher proteinEnricher;


    public void enrichParticipantEvidence(ParticipantEvidence participantToEnrich) throws EnricherException {

        if(participantToEnrich == null) throw new IllegalArgumentException("Attempted to enrich a null participant.");

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
