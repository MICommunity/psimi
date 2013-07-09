package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.MaximumCvTermUpdater;
import psidev.psi.mi.jami.enricher.impl.feature.MaximumFeatureUpdater;
import psidev.psi.mi.jami.enricher.impl.protein.MaximumProteinUpdater;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public class MaximumParticipantUpdater <P extends Participant, F extends Feature>
        extends MinimumParticipantUpdater <P , F>{

    @Override
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new MaximumProteinUpdater();
        return proteinEnricher;
    }

    @Override
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MaximumCvTermUpdater();
        return cvTermEnricher;
    }

    @Override
    public FeatureEnricher<F> getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new MaximumFeatureUpdater<F>();
        return featureEnricher;
    }
}
