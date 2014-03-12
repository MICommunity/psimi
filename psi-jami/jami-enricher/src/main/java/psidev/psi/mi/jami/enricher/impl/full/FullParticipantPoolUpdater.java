package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.minimal.MinimalParticipantPoolUpdater;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.ParticipantPool;

/**
 * A basic minimal updater for Participant pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullParticipantPoolUpdater<P extends ParticipantPool, F extends Feature> extends MinimalParticipantPoolUpdater<P,F> {

    public FullParticipantPoolUpdater(){
        super(new FullParticipantUpdater<P,F>());
    }

    protected boolean removeEntitiesFromPool(){
        return true;
    }

    @Override
    public void processOtherProperties(P poolToEnrich, P fetched) throws EnricherException {
        getMinimalUpdater().processOtherProperties(poolToEnrich, fetched);
        super.processOtherProperties(poolToEnrich, fetched);
    }

    @Override
    public void processOtherProperties(P participantToEnrich) throws EnricherException {
        getMinimalUpdater().processOtherProperties(participantToEnrich);
        super.processOtherProperties(participantToEnrich);
    }
}
