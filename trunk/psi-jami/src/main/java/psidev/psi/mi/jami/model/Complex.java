package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Set;

/**
 * An interactor composed of interacting molecules that can be copurified.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Complex extends Interactor {

    /**
     * The experiments that have been done to determine the complex.
     * The collection cannot be null. If the complex does not have any experimental evidences, the method should return an empty collection
     * @return the collection of experiments
     */
    public Collection<Experiment> getExperiments();

    /**
     * The components for this complex.
     * The collection cannot be null. If the complex does not have any components, the method should return an empty collection.
     * @return the components of a complex
     */
    public Collection<Component> getComponents();

    /**
     * Numerical parameters for this complex.
     * The set cannot be null. If the complex does not have any parameters, the method should return an empty set.
     * @return the parameters
     */
    public Set<Parameter> getParameters();
}
