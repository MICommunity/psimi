package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

import java.util.Collection;

/**
 * The featureEnricher can enrich either a single feature or a collection.
 * It has no fetcher and only enrich through subEnrichers.
 * It has a subEnricher for CvTerms.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public interface FeatureEnricher <F extends Feature>{

    /**
     * Enrichment of a single feature.
     * @param featureToEnrich       The feature which is to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
    public void enrichFeature(F featureToEnrich) throws EnricherException;
    public void enrichFeatures(Collection<F> featuresToEnrich) throws EnricherException;

    public void setFeaturesToEnrich(Participant participant);

    public void setFeatureEnricherListener(FeatureEnricherListener listener);
    public FeatureEnricherListener getFeatureEnricherListener();

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);
    public CvTermEnricher getCvTermEnricher();
}
