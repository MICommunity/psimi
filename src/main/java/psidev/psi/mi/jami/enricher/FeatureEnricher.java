package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.enricher.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;

import java.util.Collection;

/**
 * The featureEnricher can enrich either a single feature or a collection.
 * It has no fetcher and only enrich through subEnrichers.
 * Sub enricher: CvTerm.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  13/06/13
 */
public interface FeatureEnricher <F extends Feature> extends MIEnricher<F>{

    public void setFeaturesWithRangesToUpdate(Collection<F> features);

    /**
     * Retrieves the listener of feature changes.
     * May be null if changes are not being listened to.
     * @return  The current listener of feature changes.
     */
    public FeatureEnricherListener<F> getFeatureEnricherListener();

    /**
     * Gets the subEnricher for CvTerms. Can be null.
     * @return  The CvTerm enricher which is being used.
     */
    public CvTermEnricher<CvTerm> getCvTermEnricher();

    public void setFeatureEnricherListener(FeatureEnricherListener<F> listener);

    public void setCvTermEnricher(CvTermEnricher<CvTerm> cvEnricher);

}
