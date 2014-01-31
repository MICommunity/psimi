package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.bridges.fetcher.InteractorFetcher;
import psidev.psi.mi.jami.model.InteractorPool;

/**
 * A basic minimal updater for interactor pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalInteractorPoolUpdater extends MinimalInteractorPoolEnricher{

    public MinimalInteractorPoolUpdater(){
        super();
    }

    public MinimalInteractorPoolUpdater(InteractorFetcher<InteractorPool> fetcher){
        super(fetcher);
    }

    protected boolean removeInteractorsFromPool(){
        return true;
    }
}
