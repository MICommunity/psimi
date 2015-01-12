package psidev.psi.mi.jami.enricher.impl.minimal;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.CvTermEnricher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.AbstractMIEnricher;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;

/**
 * Provides minimum enrichment of a Organism.
 *
 * - enrich common name if not set. Does not override it with fetched organism common name if already set.
 * - enrich scientific name if not set. Does not override it with fetched organism scientific name if already set.
 *
 * The organism fetcher is required for enriching organism
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/05/13
 */
public class MinimalOrganismEnricher extends AbstractMIEnricher<Organism>
        implements OrganismEnricher {

    private int retryCount = 5;

    private OrganismFetcher fetcher = null;
    private OrganismEnricherListener listener = null;

    public MinimalOrganismEnricher(OrganismFetcher organismFetcher) {
        super();
        if (organismFetcher == null){
            throw new IllegalArgumentException("The organism fetcher is required");
        }
        this.fetcher = organismFetcher;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    /**
     *
     * @return  The current organism fetcher
     */
    public OrganismFetcher getOrganismFetcher() {
        return fetcher;
    }


    public void setOrganismEnricherListener(OrganismEnricherListener organismEnricherListener) {
        this.listener = organismEnricherListener;
    }

    public void setCvTermEnricher(CvTermEnricher<CvTerm> enricher) {
        // nothing to do
    }

    public OrganismEnricherListener getOrganismEnricherListener() {
        return listener;
    }

    /*
     * Cv enricher is not needed here
      */
    public CvTermEnricher<CvTerm> getCvTermEnricher() {
        return null;
    }

    @Override
    public Organism find(Organism objectToEnrich) throws EnricherException {
        Organism organismFetched = null;
        try {
            organismFetched = getOrganismFetcher().fetchByTaxID(objectToEnrich.getTaxId());
            return organismFetched;
        } catch (BridgeFailedException e) {
            int index = 0;
            while(index < retryCount){
                try {
                    organismFetched = getOrganismFetcher().fetchByTaxID(objectToEnrich.getTaxId());
                    return organismFetched;
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Retried "+retryCount+" times", e);
        }
    }

    @Override
    protected void onEnrichedVersionNotFound(Organism objectToEnrich) throws EnricherException {
        if(getOrganismEnricherListener() != null)
            getOrganismEnricherListener().onEnrichmentComplete(objectToEnrich, EnrichmentStatus.FAILED, "The organism does not exist.");
    }

    @Override
    public void enrich(Organism organismToEnrich, Organism organismFetched) throws EnricherException {

        processTaxid(organismToEnrich, organismFetched);

        // Scientific name
        processScientificName(organismToEnrich, organismFetched);

        //Common name
        processCommonName(organismToEnrich, organismFetched);

        //process other info
        processOtherProperties(organismToEnrich, organismFetched);

        if(listener != null)
            listener.onEnrichmentComplete(organismToEnrich, EnrichmentStatus.SUCCESS, "Organism successfully enriched.");
    }

    protected void processTaxid(Organism organismToEnrich, Organism organismFetched) throws EnricherException{
        // nothing to do
    }

    protected void processOtherProperties(Organism organismToEnrich, Organism organismFetched) throws EnricherException {
        // do nothing
    }

    protected void processCommonName(Organism organismToEnrich, Organism organismFetched) {
        if(organismToEnrich.getCommonName() == null
                && organismFetched.getCommonName() != null){
            organismToEnrich.setCommonName(organismFetched.getCommonName());
            if (getOrganismEnricherListener() != null)
                getOrganismEnricherListener().onCommonNameUpdate(organismToEnrich, null);
        }
    }

    protected void processScientificName(Organism organismToEnrich, Organism organismFetched) {
        //Scientific name
        if(organismToEnrich.getScientificName() == null
                && organismFetched.getScientificName() != null){
            organismToEnrich.setScientificName(organismFetched.getScientificName());
            if (getOrganismEnricherListener() != null)
                getOrganismEnricherListener().onScientificNameUpdate(organismToEnrich, null);
        }
    }
}