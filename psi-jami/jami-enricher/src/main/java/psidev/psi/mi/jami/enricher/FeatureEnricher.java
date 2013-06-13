package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.bridges.fetcher.FeatureFetcher;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:31
 */
public interface FeatureEnricher {
    public void enrichFeature(Feature featureToEnrich);


    public void setFeatureFetcher(FeatureFetcher featureFetcher);
    public FeatureFetcher getFeatureFetcher();


    public void setFeatureEnricherListener(FeatureEnricherListener featureEnricherListener);
    public FeatureEnricherListener getFeatureEnricherListener();
}
