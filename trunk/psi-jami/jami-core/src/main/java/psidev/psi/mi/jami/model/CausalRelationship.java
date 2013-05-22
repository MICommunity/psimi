package psidev.psi.mi.jami.model;

/**
 * These relationship types denote a causal relationship, or the absence of a causal relationship between a subject and an object term
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface CausalRelationship {

    /**
     * The relation type with the participant target.
     * It is a controlled vocabulary term and cannot be null
     * @return the relationType
     */
    public CvTerm getRelationType();

    /**
     * The participant target of this causalRelationchip.
     * It cannot be null.
     * @return the participant target
     */
    public Participant getTarget();
}
