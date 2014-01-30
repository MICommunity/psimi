package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.model.Interactor;

/**
 * Full updater of interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorUpdater<T extends Interactor> extends AbstractInteractorUpdater<T>{
    public FullInteractorUpdater() {
        super(new FullInteractorEnricher<T>());
    }

    public FullInteractorUpdater(InteractorFetcher<T> fetcher) {
        super(new FullInteractorEnricher<T>(fetcher));
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
