package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * A named experiment is an experiment with a shortname, fullname and aliases
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public interface NamedExperiment extends Experiment {

    /**
     * The short name of the experiment.
     * It can be null or empty.
     * Ex: author-2013-1
     * @return the short name
     */
    public String getShortName();

    /**
     * Sets the short name of an experiment
     * @param name : short name
     */
    public void setShortName(String name);

    /**
     * The full name of the experiment.
     * It can be null
     * Ex: publication title, experiment description
     * @return the full name
     */
    public String getFullName();

    /**
     * Sets the full name of the experiment
     * @param name : full name
     */
    public void setFullName(String name);

    /**
     * Collection of aliases for an experiment.
     * The Collection cannot be null and if the experiment does not have any aliases, the method should return an empty Collection.
     * @return the aliases
     */
    public <A extends Alias> Collection<A> getAliases();
}
