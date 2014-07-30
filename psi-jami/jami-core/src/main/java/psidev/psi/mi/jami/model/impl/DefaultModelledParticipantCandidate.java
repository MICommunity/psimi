package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation of modelled participant candidate
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/07/14</pre>
 */

public class DefaultModelledParticipantCandidate extends AbstractParticipantCandidate<ModelledParticipantPool, ModelledFeature>
        implements ModelledParticipantCandidate{
    public DefaultModelledParticipantCandidate(Interactor interactor) {
        super(interactor);
    }

    public DefaultModelledParticipantCandidate(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultModelledParticipantCandidate(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultModelledParticipantCandidate(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }
}
