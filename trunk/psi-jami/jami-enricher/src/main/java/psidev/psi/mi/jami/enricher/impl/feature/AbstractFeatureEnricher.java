package psidev.psi.mi.jami.enricher.impl.feature;

import psidev.psi.mi.jami.bridges.fetcher.FeatureFetcher;
import psidev.psi.mi.jami.enricher.FeatureEnricher;
import psidev.psi.mi.jami.enricher.exception.BadToEnrichFormException;
import psidev.psi.mi.jami.enricher.exception.MissingServiceException;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.model.Feature;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/06/13
 * Time: 16:54
 */
public abstract class AbstractFeatureEnricher
        implements FeatureEnricher{

    private FeatureFetcher featureFetcher;
    protected FeatureEnricherListener listener;

    protected Feature featureFetched;


    public boolean enrichFeature(Feature featureToEnrich) throws MissingServiceException, BadToEnrichFormException {

        featureFetched = fetchFeature(featureToEnrich);
        if(featureFetched == null){
            return false;
        }

        /*if (! areNoConflicts(proteinToEnrich) ) return false;

        if (getOrganismEnricher().getFetcher() instanceof MockOrganismFetcher){
            MockOrganismFetcher organismFetcher = (MockOrganismFetcher)getOrganismEnricher().getFetcher();
            organismFetcher.clearOrganisms();
            organismFetcher.addNewOrganism(""+proteinToEnrich.getOrganism().getTaxId(), proteinFetched.getOrganism());
        }

        getOrganismEnricher().enrichOrganism(proteinToEnrich.getOrganism());
         */
        processFeature(featureToEnrich);

        if(listener != null) listener.onFeatureEnriched(featureToEnrich, "Success. Feature enriched.");
        return true;
    }

    public abstract boolean processFeature(Feature featureToEnrich);

    public Feature fetchFeature(Feature featureToEnrich) throws MissingServiceException, BadToEnrichFormException {
        if(getFetcher() == null) throw new MissingServiceException("FeatureFetcher has not been provided.");
        if(featureToEnrich == null) throw new BadToEnrichFormException("Attempted to enrich a null feature.");

        if(listener != null) listener.onFeatureEnriched(featureToEnrich,
                "Failed. No feature fetcher has been implemented.");
        return null;
    }

    public void setFetcher(FeatureFetcher featureFetcher) {
        this.featureFetcher = featureFetcher;
    }

    public FeatureFetcher getFetcher() {
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
