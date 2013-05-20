package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * This interface is for representing a sequence portion that has been changed (by mutation, a variant, etc.)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/05/13</pre>
 */

public interface ResultingSequence {

    /**
     * The resulting sequence portion.
     * It can be null if we provide a valid Xref to a variant database such as dbSNP.
     * @return The resulting sequence portion as a String
     */
    public String getNewSequence();

    /**
     * The original sequence portion.
     * It can be null if we provide a valid Xref to a variant database such as dbSNP.
     * @return The original sequence portion.
     */
    public String getOriginalSequence();

    /**
     * The collection of external database cross-references for this resulting sequence portion.
     * It cannot be null so if the ResultingSequence does not have any Xrefs, the method should return an empty collection
     * @return The collection of xrefs for this resultingSequence object
     */
    public Collection<Xref> getXrefs();

    /**
     * Sets the new sequence of this object
     * @param sequence
     */
    public void setNewSequence(String sequence);

    /**
     * Sets the original sequence of this object
     * @param sequence
     */
    public void steOriginalSequence(String sequence);
}
