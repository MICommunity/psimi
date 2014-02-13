package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.impl.AbstractInteractorUpdater;
import psidev.psi.mi.jami.model.Interactor;

/**
 * A basic minimal updater for interactors.
 *
 * See description of minimal enrichment in AbstractInteractorUpdater
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalInteractorBaseUpdater<T extends Interactor> extends AbstractInteractorUpdater<T> {

    public MinimalInteractorBaseUpdater() {
        super(new MinimalInteractorBaseEnricher());
    }

    public MinimalInteractorBaseUpdater(InteractorFetcher<T> fetcher) {
        super(new MinimalInteractorBaseEnricher(fetcher));
    }

    @Override
    protected boolean isFullEnrichment() {
        return false;
    }
}

