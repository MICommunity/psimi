package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.model.Polymer;

/**
 * Full updater of polymer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullPolymerUpdater<T extends Polymer> extends AbstractInteractorUpdater<T>{
    public FullPolymerUpdater() {
        super(new FullPolymerEnricher<T>());
    }

    public FullPolymerUpdater(InteractorFetcher<T> fetcher) {
        super(new FullPolymerEnricher<T>(fetcher));
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }
}
