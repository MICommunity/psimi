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
    public DefaultModelledParticipantPool(Interactor interactor) {
        super(interactor);
    }

    public DefaultModelledParticipantPool(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultModelledParticipantPool(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultModelledParticipantPool(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }
}
