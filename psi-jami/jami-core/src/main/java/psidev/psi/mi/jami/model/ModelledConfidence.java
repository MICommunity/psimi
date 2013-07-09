package psidev.psi.mi.jami.model;

import java.util.Collection;

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
     * The publications where this confidence has been reported if relevant.
     * The collection cannot be null. If the modelledConfidence does not have any publications, the method should return an empty collection
     * @return the collection of Publication where this modelledConfidence has been reported
     */
    public <P extends Publication> Collection<P> getPublications();
}
