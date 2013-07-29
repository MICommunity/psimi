package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.model.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class MinimumParticipantEnricher<P extends Participant , F extends Feature>
        extends AbstractParticipantEnricher<P , F>  {




    /*
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new MinimumProteinEnricher();
        return proteinEnricher;
    }


    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MinimumCvTermEnricher();
        return cvTermEnricher;
    }


    public FeatureEnricher<F> getFeatureEnricher(){
        if(featureEnricher == null){
            featureEnricher = new FeatureEnricherMinimum<F>();
            EnricherUtil.linkFeatureEnricherToProteinEnricher(featureEnricher, getProteinEnricher());
        }
        return featureEnricher;
    } */
}
