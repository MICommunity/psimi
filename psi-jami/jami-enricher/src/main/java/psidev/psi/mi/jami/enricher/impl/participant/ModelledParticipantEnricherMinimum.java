package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.ModelledFeatureEnricherMinimum;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.ParticipantEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/07/13
 */
public class ModelledParticipantEnricherMinimum
    extends ParticipantEnricherMinimum<ModelledParticipant , ModelledFeature> {


    @Override
    public FeatureEnricher<ModelledFeature> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new ModelledFeatureEnricherMinimum();
        return featureEnricher;
    }
}
