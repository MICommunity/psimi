package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.EntityPool;
import psidev.psi.mi.jami.model.Feature;

/**
 * A basic minimal updater for entity pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullEntityPoolUpdater<P extends EntityPool, F extends Feature> extends MinimalEntityPoolUpdater<P,F>{

    public FullEntityPoolUpdater(){
        super(new FullParticipantUpdater<P,F>());
    }

    protected boolean removeEntitiesFromPool(){
        return true;
    }

    @Override
    protected void processOtherProperties(P poolToEnrich, P fetched) throws EnricherException {
        getMinimalUpdater().processOtherProperties(poolToEnrich, fetched);
        super.processOtherProperties(poolToEnrich, fetched);
    }

    @Override
    protected void processOtherProperties(P participantToEnrich) throws EnricherException {
        getMinimalUpdater().processOtherProperties(participantToEnrich);
        super.processOtherProperties(participantToEnrich);
    }
}
