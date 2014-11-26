package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.impl.AbstractInteractorUpdater;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalInteractorBaseEnricher;
import psidev.psi.mi.jami.model.Interactor;

/**
 * Full updater of interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorBaseUpdater<T extends Interactor> extends AbstractInteractorUpdater<T> {
    public FullInteractorBaseUpdater() {
        super(new MinimalInteractorBaseEnricher<T>());
    }

    public FullInteractorBaseUpdater(InteractorFetcher<T> fetcher) {
        super(new MinimalInteractorBaseEnricher<T>(fetcher));
    }

    protected FullInteractorBaseUpdater(FullInteractorBaseEnricher<T> enricher) {
        super(enricher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
