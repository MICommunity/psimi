package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;

/**
 * Created with IntelliJ IDEA.
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
