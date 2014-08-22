package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation of ParticipantPool
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/07/14</pre>
 */

public class DefaultParticipantPool extends AbstractParticipantPool<Interaction,Feature,ParticipantCandidate>{
    public DefaultParticipantPool(String poolName) {
        super(poolName);
    }

    public DefaultParticipantPool(String poolName, CvTerm bioRole) {
        super(poolName, bioRole);
    }

    public DefaultParticipantPool(String poolName, Stoichiometry stoichiometry) {
        super(poolName, stoichiometry);
    }

    public DefaultParticipantPool(String poolName, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(poolName, bioRole, stoichiometry);
    }
}
