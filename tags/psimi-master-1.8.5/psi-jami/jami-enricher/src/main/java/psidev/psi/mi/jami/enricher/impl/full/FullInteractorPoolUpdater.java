package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.AbstractInteractorUpdater;
import psidev.psi.mi.jami.model.InteractorPool;

/**
 * A full updater for interactor pools.
 *
 * See description of full update in AbstractInteractorUpdater.
 * - update all interactors in existing pool using a composite interactor enricher if it is not null.
 * It will add missing interactors in the pool and remove any interactors that are not in the fetched interactor pool
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/01/14</pre>
 */

public class FullInteractorPoolUpdater extends AbstractInteractorUpdater<InteractorPool> {

    public FullInteractorPoolUpdater() {
        super(new FullInteractorPoolEnricher());
    }

    public FullInteractorPoolUpdater(InteractorFetcher<InteractorPool> fetcher) {
        super(new FullInteractorPoolEnricher(fetcher));
    }

    @Override
    protected boolean isFullEnrichment() {
        return true;
    }

    @Override
    protected void processOtherProperties(InteractorPool bioactiveEntityToEnrich, InteractorPool fetched) throws EnricherException {
        ((FullInteractorPoolEnricher)getInteractorEnricher()).processOtherProperties(bioactiveEntityToEnrich, fetched);
    }
}
