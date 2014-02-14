package psidev.psi.mi.jami.enricher.impl.full;

import psidev.psi.mi.jami.enricher.impl.minimal.MinimalExperimentalEntityPoolEnricher;
import psidev.psi.mi.jami.model.ExperimentalEntityPool;

/**
 * A full enricher for experimental entity pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class FullExperimentalEntityPoolUpdater extends MinimalExperimentalEntityPoolEnricher {

    public FullExperimentalEntityPoolUpdater(){
        super(new FullParticipantEvidenceUpdater<ExperimentalEntityPool>());
    }

    protected boolean removeEntitiesFromPool(){
        return true;
    }
}
