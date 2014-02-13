package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.model.InteractorPool;

/**
 * Full interactor pool updater
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorPoolUpdater extends FullInteractorBaseUpdater<InteractorPool> {

    public FullInteractorPoolUpdater() {
        super();
    }

    public FullInteractorPoolUpdater(InteractorFetcher<InteractorPool> fetcher) {
        super(fetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
