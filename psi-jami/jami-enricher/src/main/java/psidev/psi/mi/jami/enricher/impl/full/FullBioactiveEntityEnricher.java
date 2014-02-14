package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalBioactiveEntityEnricher;

/**
 * A full enricher for bioactive entities.
 *
 * See description of full enrichment in AbstractInteractorEnricher.
 *
 * The bioactive entities fetcher is required for enriching bioactive entities.
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
