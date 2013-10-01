package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.model.Gene;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public class MinimalGeneUpdater extends AbstractInteractorUpdater<Gene>{

    public MinimalGeneUpdater(GeneFetcher fetcher) {
        super(new MinimalGeneEnricher(fetcher));
    }
}
