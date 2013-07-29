package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.enricher.ModelledParticipantEnricher;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.ModelledParticipant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 01/07/13
 */
public class ModelledParticipantUpdaterMaximum
    extends ParticipantUpdaterMaximum<ModelledParticipant , ModelledFeature>
        implements ModelledParticipantEnricher {

    /*
    @Override
    public FeatureEnricher<ModelledFeature> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new MaximumModelledFeatureUpdater();
        return featureEnricher;
    } */
}
