package psidev.psi.mi.jami.model;

/**
 * A participant candidate is part of a participant pool and contains the molecule which can interact plus some participant details such as
 * features and causal relationships
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/14</pre>
 */

public interface ParticipantCandidate<P extends ParticipantPool, F extends Feature> extends Entity<F>{

    /**
     * The participant pool parent with which the candidate is involved.
     * It can be null if the participant candidate is not part of any participant pool. It can happen if the participant candidate has been removed from a participant pool and is now invalid.
     * @return the participant pool parent
     */
    public P getParentPool();

    /**
     * Sets the participant pool parent.
     * @param pool : participant pool
     */
    public void setParentPool(P pool);
}
