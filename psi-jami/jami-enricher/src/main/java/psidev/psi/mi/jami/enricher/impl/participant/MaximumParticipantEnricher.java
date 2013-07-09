package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.MaximumCvTermEnricher;
import psidev.psi.mi.jami.enricher.impl.feature.MaximumFeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.protein.MaximumProteinEnricher;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public class MaximumParticipantEnricher <P extends Participant, F extends Feature>
        extends MinimumParticipantEnricher <P , F>{

    @Override
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new MaximumProteinEnricher();
        return proteinEnricher;
    }

    @Override
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MaximumCvTermEnricher();
        return cvTermEnricher;
    }

    @Override
    public FeatureEnricher<F> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new MaximumFeatureEnricher<F>();
        return featureEnricher;
    }
}
