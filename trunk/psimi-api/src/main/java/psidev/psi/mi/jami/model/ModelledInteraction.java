package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * An interaction that is not directly supported by experimental evidence but is based on homology statements, modelling, etc...
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/12/12</pre>
 */

public interface ModelledInteraction extends Interaction<Component>{

    /**
     * Experiments that have been used to predict this modelled interaction.
     * The collection cannot be null. If the modelled interaction does not have experimental interactions attached to it, the method should return an empty set
     * @return the collection of experimental evidences
     */
    public Collection<Experiment> getExperiments();
}
