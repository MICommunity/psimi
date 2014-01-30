package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.model.Polymer;

/**
 * Full polymer enricher
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullPolymerEnricher<T extends Polymer> extends MinimalPolymerEnricher<T> {

    public FullPolymerEnricher() {
        super();
    }

    public FullPolymerEnricher(InteractorFetcher<T> fetcher) {
        super(fetcher);
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
