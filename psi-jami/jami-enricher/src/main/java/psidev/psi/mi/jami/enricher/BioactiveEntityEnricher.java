package psidev.psi.mi.jami.enricher;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.bioactiveentity.BioactiveEntityEnricherListener;
import psidev.psi.mi.jami.model.BioactiveEntity;

import java.util.Collection;

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
     * Enriches a single bioactive entity.
     * @param bioactiveEntityToEnrich   The entity to be enriched.
     * @throws EnricherException        Thrown if problems are encountered in the fetcher
     */
    public void enrichBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich)
            throws EnricherException;

    /**
     * Enriches a collection of bioactive entities.
     * @param bioactiveEntitiesToEnrich       The entities to be enriched
     * @throws EnricherException    Thrown if problems are encountered in the fetcher
     */
     public void enrichBioactiveEntities(Collection<BioactiveEntity> bioactiveEntitiesToEnrich)
            throws EnricherException;

    /**
     * Sets the bioactiveEntity fetcher to be used for enrichment.
     * If the fetcher is null, an illegal state exception will be thrown at the the next enrichment.
     * @param fetcher   The fetcher to be used to gather data for enrichment
     */
    public void setBioactiveEntityFetcher(BioactiveEntityFetcher fetcher);

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
}
