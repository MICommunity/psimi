package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation of ParticipantPool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/07/14</pre>
 */

public class DefaultModelledParticipantPool extends AbstractParticipantPool<ModelledInteraction,ModelledFeature,ModelledParticipantCandidate>
implements ModelledParticipantPool{
    public DefaultModelledParticipantPool(String poolName) {
        super(poolName);
    }

    public DefaultModelledParticipantPool(String poolName, CvTerm bioRole) {
        super(poolName, bioRole);
    }

    public DefaultModelledParticipantPool(String poolName, Stoichiometry stoichiometry) {
        super(poolName, stoichiometry);
    }

    public DefaultModelledParticipantPool(String poolName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(poolName, bioRole, stoichiometry);
    }
}
