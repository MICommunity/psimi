package psidev.psi.mi.jami.enricher.impl.bioactiveentity;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Provides maximum enrichment of the BioactiveEntity.
 * Will enrich all aspects covered by the minimum enricher.
 * As an enricher, no values from the provided BioactiveEntity to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MaximumBioactiveEntityEnricher
        extends MinimumBioactiveEntityEnricher{

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive entity records.
     */
    public MaximumBioactiveEntityEnricher(BioactiveEntityFetcher fetcher) {
        super(fetcher);
    }

    /**
     * Strategy for the BioactiveEntity enrichment.
     * This method can be overwritten to change how the BioactiveEntity is enriched.
     * @param bioactiveEntityToEnrich   The BioactiveEntity to be enriched.
     */
    @Override
    protected void processBioactiveEntity(BioactiveEntity bioactiveEntityToEnrich) {
        super.processBioactiveEntity(bioactiveEntityToEnrich);
    }
}
