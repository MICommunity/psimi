package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.feature.FeatureEvidenceUpdaterMinimum;
import psidev.psi.mi.jami.enricher.impl.feature.ModelledFeatureUpdaterMaximum;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class ParticipantEvidenceUpdaterMinimum
        extends ParticipantUpdaterMinimum<ParticipantEvidence , FeatureEvidence> {


    @Override
    public FeatureEnricher<FeatureEvidence> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new FeatureEvidenceUpdaterMinimum();
        return featureEnricher;
    }
}
