package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.model.EntityPool;
import psidev.psi.mi.jami.model.Feature;

/**
 * A basic minimal updater for entity pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalEntityPoolUpdater<P extends EntityPool, F extends Feature> extends MinimalEntityPoolEnricher<P,F>{

    public MinimalEntityPoolUpdater(){
        super();
    }

    protected boolean removeEntitiesFromPool(){
        return true;
    }
}
