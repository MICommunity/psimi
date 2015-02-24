package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.enricher.listener.OrganismEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;

/**
 * An organism enricher which can enrich either a single organism or a collection.
 * The organismEnricher has CvTermEnricher as sub enrichers subEnrichers.
 *
 * @author  Gabriel Aldam (galdam@ebi.ac.uk)
 * @since   22/05/13
 */
public interface OrganismEnricher extends MIEnricher<Organism>{

    /**
     * The fetcher service to retrieve the organism by.
     * If this has not been set, null will be returned.
     * @return  The service being used when fetching an enrichedOrganism. Null if it has not been set.
     */
    public OrganismFetcher getOrganismFetcher();

    /**
     * The listener which is currently being used.
     * @return  The listener currently in use.
     */
    public OrganismEnricherListener getOrganismEnricherListener();

    public CvTermEnricher<CvTerm> getCvTermEnricher();

    public void setOrganismEnricherListener(OrganismEnricherListener listener);

    public void setCvTermEnricher(CvTermEnricher<CvTerm> enricher);
}
