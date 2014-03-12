package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.model.ParticipantEvidencePool;

/**
 * A basic minimal enricher for experimental Participant pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalParticipantEvidencePoolUpdater extends MinimalParticipantEvidencePoolEnricher {

    protected MinimalParticipantEvidencePoolUpdater(){
        super(new MinimalParticipantEvidenceUpdater<ParticipantEvidencePool>());
    }

    protected boolean removeEntitiesFromPool(){
        return true;
    }
}
