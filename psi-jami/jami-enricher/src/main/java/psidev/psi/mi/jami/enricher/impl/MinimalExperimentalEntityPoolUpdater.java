package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.model.ExperimentalEntityPool;
import psidev.psi.mi.jami.model.FeatureEvidence;

/**
 * A basic minimal enricher for experimental entity pools
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public class MinimalExperimentalEntityPoolUpdater extends MinimalExperimentalEntityPoolEnricher{

    protected MinimalExperimentalEntityPoolUpdater(){
        super(new MinimalParticipantEvidenceUpdater<ExperimentalEntityPool, FeatureEvidence>());
    }

    protected boolean removeEntitiesFromPool(){
        return true;
    }
}
