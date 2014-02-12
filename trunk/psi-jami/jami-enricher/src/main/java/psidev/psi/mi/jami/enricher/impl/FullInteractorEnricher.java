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

public class FullInteractorEnricher<T extends Interactor> extends MinimalInteractorBaseEnricher<T> {

    public FullInteractorEnricher() {
        super();
    }

    public FullInteractorEnricher(InteractorFetcher<T> fetcher) {
        super(fetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
