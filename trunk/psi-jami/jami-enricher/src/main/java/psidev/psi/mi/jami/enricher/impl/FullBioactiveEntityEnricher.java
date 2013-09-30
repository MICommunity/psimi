package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;

/**
 * Provides maximum enrichment of the BioactiveEntity.
 * Will enrich all aspects covered by the minimum enricher.
 * As an enricher, no values from the provided BioactiveEntity to enrich will be changed.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class FullBioactiveEntityEnricher extends MinimalBioactiveEntityEnricher {

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive entity records.
     */
    public FullBioactiveEntityEnricher(BioactiveEntityFetcher fetcher) {
        super(fetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
