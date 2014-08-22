package psidev.psi.mi.jami.model;

/**
 * A modelled parameter is a parameter for a modelled interaction.
 *
 * It can be extracted from different experiments.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/02/13</pre>
 */

public interface ModelledParameter extends Parameter {

    /**
     * The publication where this parameter has been reported if relevant, null otherwise.
     * @return the Publication where this modelledParameter has been reported, null if no publications reported this parameter
     */
    public Publication getPublication();

    /**
     * Sets the publication of this parameter
     * @param publication : the publication
     */
    public void setPublication(Publication publication);
}
