package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.model.InteractorPool;

/**
 * Full interactor pool updater
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorPoolUpdater extends MinimalInteractorPoolUpdater {

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
