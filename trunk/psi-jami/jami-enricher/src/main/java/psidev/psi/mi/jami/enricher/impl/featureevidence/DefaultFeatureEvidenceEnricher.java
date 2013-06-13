package psidev.psi.mi.jami.enricher.impl.featureevidence;

import psidev.psi.mi.jami.bridges.fetcher.FeatureEvidenceFetcher;
import psidev.psi.mi.jami.enricher.FeatureEvidenceEnricher;
import psidev.psi.mi.jami.enricher.impl.featureevidence.listener.FeatureEvidenceEnricherListener;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:55
 */
public class DefaultFeatureEvidenceEnricher
        implements FeatureEvidenceEnricher{

    private FeatureEvidenceFetcher featureEvidenceFetcher;
    protected FeatureEvidenceEnricherListener listener;

    public void enrichFeatureEvidence(FeatureEvidence featureEvidence) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFeatureEvidenceFetcher(FeatureEvidenceFetcher featureEvidenceFetcher) {
        this.featureEvidenceFetcher = featureEvidenceFetcher;
    }

    public FeatureEvidenceFetcher getFeatureEvidenceFetcher() {
        //Todo lazy load
        return featureEvidenceFetcher;
    }

    public void setFeatureEvidenceEnricherListener(FeatureEvidenceEnricherListener featureEvidenceEnricherListener) {
        this.listener = featureEvidenceEnricherListener;
    }

    public FeatureEvidenceEnricherListener getFeatureEvidenceEnricherListener() {
        return listener;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
