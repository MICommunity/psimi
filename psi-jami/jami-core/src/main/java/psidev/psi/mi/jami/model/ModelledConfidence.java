package psidev.psi.mi.jami.model;

/**
 * A modelled confidence is a confidence for a modelled interaction.
 *
 * It can be computed from different experiments
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/02/13</pre>
 */

public interface ModelledConfidence extends Confidence {

    /**
     * The publication where this confidence has been reported if relevant, null otherwise.
     * @return the Publication where this modelledConfidence has been reported, null if no publications reported this confidence
     */
    public Publication getPublication();

    /**
     * Sets the publication of this confidence
     * @param publication
     */
    public void setPublication(Publication publication);
}
