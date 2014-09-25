package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * A named interaction is an interaction having a fullname and aliases in addition to a shortname.
 * The fullname is the description of the interaction. Aliases would be synonyms of the interaction.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public interface NamedInteraction<T extends Participant> extends Interaction<T>{
    /**
     * The full name of the interaction.
     * It can be null
     * @return the full name
     */
    public String getFullName();

    /**
     * Sets the full name of the interaction
     * @param name : full name
     */
    public void setFullName(String name);

    /**
     * Collection of aliases for an interaction.
     * The Collection cannot be null and if the interaction does not have any aliases, the method should return an empty Collection.
     * @return the aliases
     */
    public <A extends Alias> Collection<A> getAliases();
}
