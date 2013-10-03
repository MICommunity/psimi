package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;

/**
 * Provides maximum updating of the BioactiveEntity.
 * It extends the functionality of the minimum updater.
 * As an updater, values from the provided BioactiveEntity to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class FullBioactiveEntityUpdater
        extends MinimalBioactiveEntityUpdater {

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive entity records.
     */
    public FullBioactiveEntityUpdater(BioactiveEntityFetcher fetcher) {
        super(new FullBioactiveEntityEnricher(fetcher));
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
