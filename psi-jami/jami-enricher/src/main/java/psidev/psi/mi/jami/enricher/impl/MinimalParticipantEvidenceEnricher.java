package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class MinimalParticipantEvidenceEnricher extends BasicParticipantEnricher<ParticipantEvidence , FeatureEvidence>{

    @Override
    protected void processOtherProperties(ParticipantEvidence participantEvidenceToEnrich)
            throws EnricherException {

        if(getCvTermEnricher() != null){
            if (participantEvidenceToEnrich.getExperimentalRole() != null){
                getCvTermEnricher().enrich(participantEvidenceToEnrich.getExperimentalRole());
            }
            if (!participantEvidenceToEnrich.getIdentificationMethods().isEmpty()){
                getCvTermEnricher().enrich(participantEvidenceToEnrich.getIdentificationMethods());
            }
        }
    }
}
