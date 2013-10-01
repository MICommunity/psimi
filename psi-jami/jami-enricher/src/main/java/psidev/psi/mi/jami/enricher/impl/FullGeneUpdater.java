package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.model.Gene;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public class FullGeneUpdater extends AbstractInteractorUpdater<Gene> {

    public FullGeneUpdater(GeneFetcher fetcher) {
        super(new FullGeneEnricher(fetcher));
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
