package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Default implementation of participant candidate
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/07/14</pre>
 */

public class DefaultParticipantCandidate extends AbstractParticipantCandidate<ParticipantPool, Feature>{
    public DefaultParticipantCandidate(Interactor interactor) {
        super(interactor);
    }

    public DefaultParticipantCandidate(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

}
