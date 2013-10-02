package psidev.psi.mi.jami.enricher.impl;


import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class FullParticipantEvidenceEnricher extends MinimalParticipantEvidenceEnricher {

    @Override
    protected void processOtherProperties(ParticipantEvidence participantEvidenceToEnrich)
            throws EnricherException {
        super.processOtherProperties(participantEvidenceToEnrich);

        if(!participantEvidenceToEnrich.getExperimentalPreparations().isEmpty() && getCvTermEnricher() != null){
            getCvTermEnricher().enrich(participantEvidenceToEnrich.getExperimentalPreparations());
        }
    }
}
