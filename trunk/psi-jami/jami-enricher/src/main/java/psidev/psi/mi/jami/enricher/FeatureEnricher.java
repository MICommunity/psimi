package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface FeatureEnricher {

    public boolean enrichFeature(Feature featureToEnrich, String sequenceOld, String sequenceNew);


    public void setFeatureEnricherListener(FeatureEnricherListener listener);
    public FeatureEnricherListener getFeatureEnricherListener();

    //public void setFeatureEvidenceEnricher(FeatureEvidenceEnricher featureEvidenceEnricher);
    //public FeatureEvidenceEnricher getFeatureEvidenceEnricher();
}
