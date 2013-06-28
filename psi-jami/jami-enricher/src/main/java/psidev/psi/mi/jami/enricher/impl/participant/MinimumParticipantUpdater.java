package psidev.psi.mi.jami.enricher.impl.participant;

import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.ParticipantEnricher;
import psidev.psi.mi.jami.enricher.ProteinEnricher;
import psidev.psi.mi.jami.enricher.impl.cvterm.MinimumCvTermUpdater;
import psidev.psi.mi.jami.enricher.impl.feature.MinimumFeatureUpdater;
import psidev.psi.mi.jami.enricher.impl.protein.MinimumProteinUpdater;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public class MinimumParticipantUpdater
        extends AbstractParticipantEnricher
        implements ParticipantEnricher {


    @Override
    public ProteinEnricher getProteinEnricher(){
        if(proteinEnricher == null) proteinEnricher = new MinimumProteinUpdater();
        return proteinEnricher;
    }

    @Override
    public CvTermEnricher getCvTermEnricher(){
        if(cvTermEnricher == null) cvTermEnricher = new MinimumCvTermUpdater();
        return cvTermEnricher;
    }

    @Override
    public FeatureEnricher getFeatureEnricher(){
        if(featureEnricher == null) featureEnricher = new MinimumFeatureUpdater();
        return featureEnricher;
    }
}
