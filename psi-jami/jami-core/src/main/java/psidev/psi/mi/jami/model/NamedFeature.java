package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 *  A named feature is a feature with aliases.
 *
 *  The shortName and fullName of a feature are used for describing the feature.
 *  Aliases will be used to give alternative descriptions or names to the feature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public interface NamedFeature<P extends Entity, F extends Feature> extends Feature<P,F>{

    /**
     * Collection of aliases for a feature.
     * The Collection cannot be null and if the experiment does not have any aliases, the method should return an empty Collection.
     * @return the aliases
     */
    public <A extends Alias> Collection<A> getAliases();
}
