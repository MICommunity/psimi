package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation of experimental participant candidate
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/07/14</pre>
 */

public class DefaultExperimentalParticipantCandidate extends
        AbstractParticipantCandidate<ExperimentalParticipantPool, FeatureEvidence> implements ExperimentalParticipantCandidate{
    public DefaultExperimentalParticipantCandidate(Interactor interactor) {
        super(interactor);
    }

    public DefaultExperimentalParticipantCandidate(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultExperimentalParticipantCandidate(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultExperimentalParticipantCandidate(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }
}
