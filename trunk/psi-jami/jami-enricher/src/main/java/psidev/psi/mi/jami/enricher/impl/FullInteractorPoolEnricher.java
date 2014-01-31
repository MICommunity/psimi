package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.model.InteractorPool;

/**
 * Full interactor pool enricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorPoolEnricher extends MinimalInteractorPoolEnricher {

    public FullInteractorPoolEnricher() {
        super();
    }

    public FullInteractorPoolEnricher(InteractorFetcher<InteractorPool> fetcher) {
        super(fetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
