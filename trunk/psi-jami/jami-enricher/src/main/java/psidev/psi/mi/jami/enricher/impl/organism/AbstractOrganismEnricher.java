package psidev.psi.mi.jami.enricher.impl.organism;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.bridges.fetcher.mockfetcher.organism.MockOrganismFetcher;
import psidev.psi.mi.jami.enricher.OrganismEnricher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.util.RetryStrategy;
import psidev.psi.mi.jami.model.Organism;

/**
 * The general architecture for a organism enricher with methods to fetch an organism and coordinate the enriching.
 * Has an abstract method 'processOrganism' which can be overridden to determine which parts should be enriched and how.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  14/06/13
 */
public abstract class AbstractOrganismEnricher
        implements OrganismEnricher {


    protected static final Logger log = LoggerFactory.getLogger(AbstractOrganismEnricher.class.getName());


    public static final int RETRY_COUNT = 5;

    private OrganismFetcher fetcher = null;
    private MockOrganismFetcher mockFetcher = null;
    private OrganismEnricherListener listener = null;

    protected Organism organismFetched = null;

    public AbstractOrganismEnricher(){}

    public AbstractOrganismEnricher(OrganismFetcher organismFetcher){
        setOrganismFetcher(organismFetcher);
    }

    /**
     * Takes an Organism, fetches a version with all details filled in and then enriches fields depending on the implementation.
     * Will report to the OrganismEnricherListener any changes made and the status upon finishing the enrichment.
     * @param organismToEnrich  the Organism to be enriched
     * @throws EnricherException
     */
    public void enrichOrganism(Organism organismToEnrich) throws EnricherException {
        if( organismToEnrich == null )
            throw new IllegalArgumentException("Null organism can not be enriched.");

        organismFetched = fetchOrganism(organismToEnrich);

        if(organismFetched == null){
            if(getOrganismEnricherListener() != null)
                getOrganismEnricherListener().onOrganismEnriched(organismToEnrich, EnrichmentStatus.FAILED , "No organism could be found.");
            return;
        }

        processOrganism(organismToEnrich);

        if(listener != null)
            listener.onOrganismEnriched(organismToEnrich, EnrichmentStatus.SUCCESS , "Organism enriched.");
    }

    /**
     * Strategy for the organism enrichment.
     * This method can be overwritten to change how the organism is enriched.
     * @param organismToEnrich   The protein to be enriched.
     */
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
        if(getOrganismFetcher() == null) throw new IllegalStateException("Can not enrich with a null fetcher.");

        Organism organismFetched = null;


        try {
            organismFetched = getOrganismFetcher().getOrganismByTaxID(organismToEnrich.getTaxId());
            if(organismFetched != null) return organismFetched;
        } catch (BridgeFailedException e) {
            int index = 0;
            while(index < RETRY_COUNT){
                try {
                    organismFetched = getOrganismFetcher().getOrganismByTaxID(organismToEnrich.getTaxId());
                    if(organismFetched != null) return organismFetched;
                } catch (BridgeFailedException ee) {
                    ee.printStackTrace();
                }
                index++;
            }
            throw new EnricherException("Retried "+RETRY_COUNT+" times", e);
        }

        return organismFetched;
    }




    public void setOrganismFetcher(OrganismFetcher fetcher) {
        this.fetcher = fetcher;
    }

    /**
     *
     * @return  An organism fetcher if one has been set or a mock organism fetcher if it is has not.
     */
    public OrganismFetcher getOrganismFetcher() {
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
