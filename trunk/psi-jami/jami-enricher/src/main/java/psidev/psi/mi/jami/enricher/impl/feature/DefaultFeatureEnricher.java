package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.bridges.fetcher.FeatureFetcher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:54
 */
public class DefaultFeatureEnricher
        implements FeatureEnricher{

    private FeatureFetcher featureFetcher;
    protected FeatureEnricherListener listener;


    public void enrichFeature(Feature featureToEnrich) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFeatureFetcher(FeatureFetcher featureFetcher) {
        this.featureFetcher = featureFetcher;
    }

    public FeatureFetcher getFeatureFetcher() {
        //TODO lazy load
        return featureFetcher;
    }

    public void setFeatureEnricherListener(FeatureEnricherListener featureEnricherListener) {
        this.listener = featureEnricherListener;
    }

    public FeatureEnricherListener getFeatureEnricherListener() {
        return listener;
    }
}
