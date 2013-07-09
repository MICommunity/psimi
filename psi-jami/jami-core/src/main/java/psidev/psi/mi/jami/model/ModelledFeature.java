package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * 	Biological property of a participant that may be involved with or interfere with the binding of a molecule.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface ModelledFeature extends Feature<ModelledParticipant, ModelledFeature>{

    /**
     * The other features that can bind to this feature.
     * The collection cannot be null. If the feature does not bind with any other features, the method should return an empty collection
     * @return the binding features
     */
    public Collection<ModelledFeature> getLinkedFeatures();

}
