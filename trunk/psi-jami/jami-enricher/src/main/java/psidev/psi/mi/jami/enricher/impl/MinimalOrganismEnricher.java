package psidev.psi.mi.jami.enricher.impl;


import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.model.Organism;

/**
 * Provides minimum enrichment of the organism.
 * Will enrich the common name, scientific name and identifier if null.
 * As an enricher, no values from the provided CvTerm to enrich will be changed.
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
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setOrganismFetcher(OrganismFetcher fetcher) {
        this.fetcher = fetcher;
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

    public OrganismEnricherListener getOrganismEnricherListener() {
        return listener;
    }

    @Override
    protected Organism fetchEnrichedVersionFrom(Organism objectToEnrich) throws EnricherException {
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
    protected void enrich(Organism organismToEnrich, Organism organismFetched) throws EnricherException {
        //Special case where taxid is unknown and fetched organism is known
        if(organismToEnrich.getTaxId() == -3 && organismFetched.getTaxId() != -3){
            organismToEnrich.setTaxId(organismFetched.getTaxId());
            if (getOrganismEnricherListener() != null)
                getOrganismEnricherListener().onTaxidUpdate(organismToEnrich, "-3");
        }
        else if (organismToEnrich.getTaxId() != organismFetched.getTaxId()){
            if(getOrganismEnricherListener() != null)
                getOrganismEnricherListener().onEnrichmentComplete(organismToEnrich, EnrichmentStatus.FAILED, "The fetched organism has a taxid " + organismFetched.getTaxId() +" which is different from the original taxid " + organismToEnrich.getTaxId()+". It cannot be enriched.");
        }
        else{
            // Scientific name
            processScientificName(organismToEnrich, organismFetched);

            //Common name
            processCommonName(organismToEnrich, organismFetched);

            //process other info
            processOtherProperties(organismToEnrich, organismFetched);
        }

        if(listener != null)
            listener.onEnrichmentComplete(organismToEnrich, EnrichmentStatus.SUCCESS, "Organism successfully enriched.");
    }

    protected void processOtherProperties(Organism organismToEnrich, Organism organismFetched) {
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