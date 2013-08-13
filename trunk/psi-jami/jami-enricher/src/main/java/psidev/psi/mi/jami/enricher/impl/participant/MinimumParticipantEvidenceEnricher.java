package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.enricher.ParticipantEvidenceEnricher;
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
        extends AbstractParticipantEnricher<ParticipantEvidence , FeatureEvidence>
        implements ParticipantEvidenceEnricher {

    @Override
    protected void processParticipant(ParticipantEvidence participantEvidenceToEnrich)
            throws EnricherException {

        if(getCvTermEnricher() != null){
            getCvTermEnricher().enrichCvTerm(participantEvidenceToEnrich.getExperimentalRole());
            getCvTermEnricher().enrichCvTerms(participantEvidenceToEnrich.getIdentificationMethods());
        }
    }
}
