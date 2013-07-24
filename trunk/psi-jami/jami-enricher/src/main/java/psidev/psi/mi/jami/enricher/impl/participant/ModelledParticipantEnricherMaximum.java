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
public class ModelledParticipantEnricherMaximum
    extends ParticipantEnricherMaximum<ModelledParticipant , ModelledFeature>
    implements  ModelledParticipantEnricher

    /*
    @Override
    public FeatureEnricher<ModelledFeature> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new ModelledFeatureEnricherMaximum();
        return featureEnricher;
    } */
}
