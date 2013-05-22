package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * The experimental method and corresponding publication from which a cooperative effect has been inferred.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface CooperativityEvidence {

    /**
     * The publication where the cooperativityEvidence has been described.
     * It cannot be null.
     * @return the publication
     */
    public Publication getPublication();

    /**
     * Set the publication where the cooperativityEvidence has been described.
     * @param publication : the publication
     * @throws IllegalArgumentException when publication is null
     */
    public void setPublication(Publication publication);

    /**
     * The collection of methods used to infer the cooperative effect.
     * This collection cannot be null. If the CooperativityEvidence does not have any methods, the method should return an empty collection
     * @return The collection of methods
     */
    public Collection<CvTerm> getEvidenceMethods();
}
