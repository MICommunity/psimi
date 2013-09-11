package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

import java.util.Collection;

/**
 * The featureEnricher can enrich either a single feature or a collection.
 * It has no fetcher and only enrich through subEnrichers.
 * Sub enricher: CvTerm.
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
    /**
     * Enriches a collection of features.
     * @param featuresToEnrich   The features which are to be enriched.
     * @throws EnricherException    Thrown if problems are encountered in a fetcher
     */
    public void enrichFeatures(Collection<F> featuresToEnrich) throws EnricherException;

    public void setFeaturesToEnrich(Participant participant);

    /**
     * Sets the listener of feature changes. Can be null.
     * @param featureEnricherListener   The listener of feature changes.
     */
    public void setFeatureEnricherListener(FeatureEnricherListener featureEnricherListener);
    /**
     * Retrieves the listener of feature changes.
     * May be null if changes are not being listened to.
     * @return  The current listener of feature changes.
     */
    public FeatureEnricherListener getFeatureEnricherListener();

    /**
     * Sets the subEnricher for CvTerms. Can be null.
     * @param cvTermEnricher    The CvTerm enricher to be used
     */
    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);
    /**
     * Gets the subEnricher for CvTerms. Can be null.
     * @return  The CvTerm enricher which is being used.
     */
    public CvTermEnricher getCvTermEnricher();

}
