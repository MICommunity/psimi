package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.listener.BioactiveEntityEnricherListener;

/**
 * An enricher for bioactive entities which can either enrich a single entity or a collection.
 * The enricher must be initiated with a fetcher.
 * Sub enrichers: None.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  07/08/13
 */
public interface BioactiveEntityEnricher{

    /**
     * Returns the current fetcher which is being used to collect information about entities for enrichment.
     * @return  The current fetcher.
     */
    public BioactiveEntityFetcher getBioactiveEntityFetcher();

    /**
     * Sets the listener to use when the bioactiveEntity has been changed
     * @param listener  The new listener. Can be null.
     */
    public void setBioactiveEntityEnricherListener(BioactiveEntityEnricherListener listener);

    /**
     * The current listener of changes to the bioactiveEntities.
     * @return  The current listener. Can be null.
     */
    public BioactiveEntityEnricherListener getBioactiveEntityEnricherListener();

    public void setCvTermEnricher(CvTermEnricher cvTermEnricher);

    public CvTermEnricher getCvTermEnricher();

    public void setOrganismEnricher(OrganismEnricher organismEnricher);

    public OrganismEnricher getOrganismEnricher();
}
