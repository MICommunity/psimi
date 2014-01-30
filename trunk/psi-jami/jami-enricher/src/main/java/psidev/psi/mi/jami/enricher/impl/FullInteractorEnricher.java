package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.model.Interactor;

/**
 * Full interactor enricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorEnricher extends MinimalInteractorEnricher {

    public FullInteractorEnricher() {
        super();
    }

    public FullInteractorEnricher(InteractorFetcher<Interactor> fetcher) {
        super(fetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
