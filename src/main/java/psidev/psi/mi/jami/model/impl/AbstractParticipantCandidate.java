package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

/**
 * Abstract class for participant candidate
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/07/14</pre>
 */

public abstract class AbstractParticipantCandidate<P extends ParticipantPool, F extends Feature> extends AbstractEntity<F> implements ParticipantCandidate<P,F> {

    private P parentPool;

    public AbstractParticipantCandidate(Interactor interactor) {
        super(interactor);
    }

    public AbstractParticipantCandidate(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public P getParentPool() {
        return this.parentPool;
    }

    public void setParentPool(P pool) {
        this.parentPool = pool;
    }

    @Override
    public String toString() {
        return "Participant candidate: "+getInteractor().toString() + (getStoichiometry() != null ? ", stoichiometry: " + getStoichiometry().toString() : "");
    }
}
