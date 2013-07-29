package psidev.psi.mi.jami.enricher.impl.participant;


import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

/**
 * The minimum participant updater has default MinimumUpdaters for Proteins, CvTerms, and Features.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 13/06/13
 */
public class ParticipantUpdaterMinimum<P extends Participant , F extends Feature>
        extends AbstractParticipantEnricher <P , F>{


    /*
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new ProteinUpdaterMinimum();
        return proteinEnricher;
    }


    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MinimumCvTermUpdater();
        return cvTermEnricher;
    }


    public FeatureEnricher<F> getFeatureEnricher(){
        if(featureEnricher == null){
            featureEnricher = new FeatureUpdaterMinimum<F>();
            EnricherUtil.linkFeatureEnricherToProteinEnricher(featureEnricher, getProteinEnricher());
        }
        return featureEnricher;
    } */
}
