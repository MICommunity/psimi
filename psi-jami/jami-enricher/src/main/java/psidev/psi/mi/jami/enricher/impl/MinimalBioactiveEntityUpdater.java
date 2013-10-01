package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

/**
 * Provides minimum updating of the bioactiveEntity.
 * As an updater, values from the provided bioactiveEntity to enrich may be overwritten.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 07/08/13
 */
public class MinimalBioactiveEntityUpdater extends AbstractInteractorUpdater<BioactiveEntity>{

    /**
     * A constructor which initiates with a fetcher.
     * @param fetcher   The fetcher to use to gather bioactive entity records.
     */
    public MinimalBioactiveEntityUpdater(BioactiveEntityFetcher fetcher) {
        super(new MinimalBioactiveEntityEnricher(fetcher));
    }
}
