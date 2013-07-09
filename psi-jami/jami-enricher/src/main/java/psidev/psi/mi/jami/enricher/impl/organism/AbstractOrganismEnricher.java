package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.model.Organism;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/06/13
 */
public abstract class AbstractOrganismEnricher
        implements OrganismEnricher {

    public static final int RETRY_COUNT = 5;

    protected OrganismFetcher fetcher = null;
    protected OrganismEnricherListener listener = null;

    protected Organism organismFetched = null;

    /**
     * Takes an Organism, fetches a version with all details filled in and then enriches fields depending on the implementation.
     * Will report to the OrganismEnricherListener any changes made and the status upon finishing the enrichment.
     * @param organismToEnrich  the Organism to be enriched
     * @throws EnricherException
     */
    public void enrichOrganism(Organism organismToEnrich) throws EnricherException {

        organismFetched = fetchOrganism(organismToEnrich);
        if(organismFetched == null){
            if(listener != null) listener.onOrganismEnriched(organismToEnrich, EnrichmentStatus.FAILED , "No organism could be found.");
            return;
        }

        processOrganism(organismToEnrich);

        if(listener != null) listener.onOrganismEnriched(organismToEnrich, EnrichmentStatus.SUCCESS , "Organism enriched.");
    }

    protected abstract void processOrganism(Organism organismToEnrich);

    /**
     * Tries to find a matching organism.
     *
     * @param organismToEnrich
     * @return
     * @throws EnricherException
     */
    private Organism fetchOrganism(Organism organismToEnrich) throws EnricherException {
        if(organismToEnrich == null) throw new IllegalArgumentException("Can not enrich a null organism.");

        int retryCount = RETRY_COUNT;
        while(true){
            try {
                return fetcher.getOrganismByTaxID(organismToEnrich.getTaxId());
            } catch (BridgeFailedException e) {
                retryCount --;
                if(retryCount >= 0){
                    throw new EnricherException(
                            "Organism fetcher failed on organism with taxID "+organismToEnrich.getTaxId()+". "+
                            "Tried "+RETRY_COUNT+" times.", e);
                }
            }
        }


    }




    public void setFetcher(OrganismFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public OrganismFetcher getFetcher() {
        return fetcher;
    }

    public void setOrganismEnricherListener(OrganismEnricherListener organismEnricherListener) {
        this.listener = organismEnricherListener;
    }

    public OrganismEnricherListener getOrganismEnricherListener() {
        return listener;
    }
}
