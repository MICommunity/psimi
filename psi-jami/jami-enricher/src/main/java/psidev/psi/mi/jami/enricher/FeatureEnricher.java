package psidev.psi.mi.jami.enricher;


import psidev.psi.mi.jami.bridges.fetcher.FeatureFetcher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 */
public interface FeatureEnricher {

    public boolean enrichFeature(Feature featureToEnrich, String sequenceOld, String sequenceNew)
            throws MissingServiceException, BadToEnrichFormException;


    public void setFetcher(FeatureFetcher fetcher);
    public FeatureFetcher getFetcher();


    public void setFeatureEnricherListener(FeatureEnricherListener listener);
    public FeatureEnricherListener getFeatureEnricherListener();

    //public void setFeatureEvidenceEnricher(FeatureEvidenceEnricher featureEvidenceEnricher);
    //public FeatureEvidenceEnricher getFeatureEvidenceEnricher();
}
