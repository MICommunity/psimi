package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.feature.FeatureEvidenceEnricherMaximum;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class ParticipantEvidenceEnricherMaximum
        extends ParticipantEnricherMaximum<ParticipantEvidence , FeatureEvidence> {

    @Override
    public FeatureEnricher<FeatureEvidence> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new FeatureEvidenceEnricherMaximum();
        return featureEnricher;
    }

}
