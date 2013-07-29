package psidev.psi.mi.jami.enricher.impl.organism;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.util.RetryStrategy;
import psidev.psi.mi.jami.model.CvTerm;
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
    protected MockOrganismFetcher mockFetcher = null;
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
            if(listener != null)
                listener.onOrganismEnriched(organismToEnrich, EnrichmentStatus.FAILED , "No organism could be found.");
            return;
        }

        processOrganism(organismToEnrich);

        if(listener != null)
            listener.onOrganismEnriched(organismToEnrich, EnrichmentStatus.SUCCESS , "Organism enriched.");
    }

    protected abstract void processOrganism(Organism organismToEnrich);

    /**
     * Tries to find a matching organism.
     *
     * @param organismToEnrich  The organism to fetch a complete version of
     * @return      A complete form of the organismToEnrich.
     * @throws EnricherException
     */
    private Organism fetchOrganism(Organism organismToEnrich) throws EnricherException {
        if(organismToEnrich == null) throw new IllegalArgumentException("Can not enrich a null organism.");

        Organism fetchedOrganism = null;

        RetryStrategy retryStrategy = new RetryStrategy(RETRY_COUNT ,
                "Organism fetcher failed on organism with taxID "+organismToEnrich.getTaxId()+". " );
        while(retryStrategy.retry()){
            try {
                fetchedOrganism = fetcher.getOrganismByTaxID(organismToEnrich.getTaxId());
                retryStrategy.attemptSucceeded();
            } catch (BridgeFailedException e) {
                retryStrategy.reportException(e);
            }
        }

        return fetchedOrganism;
    }




    public void setFetcher(OrganismFetcher fetcher) {
        this.fetcher = fetcher;
    }

    /**
     *
     * @return  An organism fetcher if one has been set or a mock organism fetcher if it is has not.
     */
    public OrganismFetcher getFetcher() {
        return fetcher;
    }

    /**
     * If the fetcher has been set, the mockOrganismFetcher will remain null.
     * If the fetcher has not been set it will be set to the mock organism fetcher
     * @return  The mockOrganismFetcher if the fetcher was null
     */
    public MockOrganismFetcher getMockFetcher(){
        if( mockFetcher == null) {
            mockFetcher = new MockOrganismFetcher();
        }
        return mockFetcher;
    }
    /*public void useMockFetcher(){
        fetcher = getMockFetcher();
    } */

    public void setOrganismEnricherListener(OrganismEnricherListener organismEnricherListener) {
        this.listener = organismEnricherListener;
    }

    public OrganismEnricherListener getOrganismEnricherListener() {
        return listener;
    }

}
