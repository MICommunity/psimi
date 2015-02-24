package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.bridges.fetcher.GeneFetcher;
import psidev.psi.mi.jami.enricher.impl.AbstractInteractorEnricher;
import psidev.psi.mi.jami.enricher.impl.AbstractInteractorUpdater;
import psidev.psi.mi.jami.model.Gene;

/**
 * A basic minimal updater for genes.
 *
 * See description of minimal update in AbstractInteractorUpdater.
 *
 * The GeneFetcher is required for enriching genes.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 04/09/13
 */
public class MinimalGeneUpdater extends AbstractInteractorUpdater<Gene> {

    public MinimalGeneUpdater(GeneFetcher fetcher) {
        super(new MinimalGeneEnricher(fetcher));
    }

    protected MinimalGeneUpdater(AbstractInteractorEnricher<Gene> interactorEnricher) {
        super(interactorEnricher);
    }
}
