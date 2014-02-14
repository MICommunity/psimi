package psidev.psi.mi.jami.enricher.impl.minimal;

import psidev.psi.mi.jami.model.ExperimentalEntityPool;

/**
 * A basic minimal enricher for experimental entity pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalExperimentalEntityPoolUpdater extends MinimalExperimentalEntityPoolEnricher{

    protected MinimalExperimentalEntityPoolUpdater(){
        super(new MinimalParticipantEvidenceUpdater<ExperimentalEntityPool>());
    }

    protected boolean removeEntitiesFromPool(){
        return true;
    }
}
