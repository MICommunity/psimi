package psidev.psi.mi.jami.model;

import java.util.Collection;

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
     * The publications where this parameter has been reported if relevant.
     * The collection cannot be null. If the modelledParameter does not have any publications, the method should return an empty collection
     * @return the collection of Publication where this modelledParameter has been reported
     */
    public Collection<Publication> getPublications();
}
