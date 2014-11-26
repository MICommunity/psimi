package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalGeneEnricher;

/**
 * A full enricher for genes.
 *
 * See description of full enrichment in AbstractInteractorEnricher.
 *
 * The GeneFetcher is required for enriching genes.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public class FullGeneEnricher extends MinimalGeneEnricher {

    public FullGeneEnricher(GeneFetcher fetcher) {
        super(fetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
