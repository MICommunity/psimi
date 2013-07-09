package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class MinimumParticipantEvidenceEnricher
        extends MinimumParticipantEnricher<ParticipantEvidence , FeatureEvidence> {

    @Override
    public void enrichParticipant(ParticipantEvidence participantEvidenceToEnrich)
            throws EnricherException {

        super.enrichParticipant(participantEvidenceToEnrich);

        if(getCvTermEnricher() != null){

            getCvTermEnricher().enrichCvTerm(
                    participantEvidenceToEnrich.getExperimentalRole());

            for(CvTerm cvTerm : participantEvidenceToEnrich.getIdentificationMethods()){
                getCvTermEnricher().enrichCvTerm(cvTerm);
            }

            for(CvTerm cvTerm : participantEvidenceToEnrich.getExperimentalPreparations()){
                getCvTermEnricher().enrichCvTerm(cvTerm);
            }
        }
    }

}
