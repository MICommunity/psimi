package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.feature.ModelledFeatureEnricherMaximum;
import psidev.psi.mi.jami.enricher.impl.feature.ModelledFeatureUpdaterMinimum;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/07/13
 */
public class ModelledParticipantUpdaterMinimum
    extends ParticipantUpdaterMinimum<ModelledParticipant , ModelledFeature> {


    @Override
    public FeatureEnricher<ModelledFeature> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new ModelledFeatureUpdaterMinimum();
        return featureEnricher;
    }
}
