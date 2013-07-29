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
public class MinimumParticipantEvidenceUpdater
        extends MinimumParticipantUpdater<ParticipantEvidence , FeatureEvidence>
        implements ParticipantEvidenceEnricher {

    @Override
    protected void processParticipant(ParticipantEvidence participantEvidenceToEnrich)
            throws EnricherException {

        super.processParticipant(participantEvidenceToEnrich);

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

    /*
    @Override
    public FeatureEnricher<FeatureEvidence> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new MinimumFeatureEvidenceUpdater();
        return featureEnricher;
    } */
}
