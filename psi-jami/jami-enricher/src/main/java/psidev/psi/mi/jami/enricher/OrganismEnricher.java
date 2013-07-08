package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.organism.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.model.Organism;

/**
 * An organism enricher for adding varying levels of information to Organism objects.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   22/05/13
 */
public interface OrganismEnricher{

    /**
     * Enriches the organism
     * @param organismToEnrich  the Organism to be enriched
     * @return                  the final status of the enrichment where true is successful and false failed.
     */
    public void enrichOrganism(Organism organismToEnrich) throws EnricherException;
    /**
     * Sets the fetcher service to retrieve the organism by.
     * @param fetcher   the service to use when fetching an enrichedOrganism
     */
    public void setFetcher(OrganismFetcher fetcher);

    /**
     * The fetcher service to retrieve the organism by.
     * If this has not been set, null will be returned.
     * @return  The service being used when fetching an enrichedOrganism. Null if it has not been set.
     */
    public OrganismFetcher getFetcher();

    /**
     * Sets the listener to use.
     * @param listener
     */
    public void setOrganismEnricherListener(OrganismEnricherListener listener);

    /**
     * The listener which is currently being used.
     *
     * @return  The listener currently in use. Null if it has not been set.
     */
    public OrganismEnricherListener getOrganismEnricherListener();
}
