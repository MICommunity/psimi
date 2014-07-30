package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * A pool of participants that can be grouped together as a single participant as they are interchangeable
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/14</pre>
 */

public interface ParticipantPool<I extends Interaction, F extends Feature, P extends ParticipantCandidate> extends Participant<I,F>, Collection<P>{

    /**
     * The pool type of this participant pool.
     * It is a controlled vocabulary term and cannot be null.
     * Ex: molecule set, candidate set, defined set, ...
     * @return pool type
     */
    public CvTerm getType();

    /**
     * Sets the pool type for this participant pool
     * If the given type is null, this method automatically sets the interactor type to 'molecule set' (MI:1304)
     * @param type : molecule type
     */
    public void setType(CvTerm type);
}
