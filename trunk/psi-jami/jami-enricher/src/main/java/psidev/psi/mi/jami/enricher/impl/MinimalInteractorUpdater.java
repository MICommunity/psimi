package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.model.Interactor;

/**
 * A basic minimal updater for interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalInteractorUpdater<T extends Interactor> extends AbstractInteractorUpdater<T>{

    private InteractorEnricherListener listener;

    public MinimalInteractorUpdater() {
        super(new MinimalInteractorEnricher());
    }

    public MinimalInteractorUpdater(InteractorFetcher<T> fetcher) {
        super(new MinimalInteractorEnricher(fetcher));
    }
}

