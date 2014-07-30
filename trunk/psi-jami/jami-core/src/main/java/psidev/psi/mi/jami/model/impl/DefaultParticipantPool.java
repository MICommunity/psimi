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
    public DefaultParticipantPool(Interactor interactor) {
        super(interactor);
    }

    public DefaultParticipantPool(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultParticipantPool(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultParticipantPool(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }
}
