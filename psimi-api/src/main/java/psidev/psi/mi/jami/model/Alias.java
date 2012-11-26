package psidev.psi.mi.jami.model;

/**
 * Alias is a synonym. It is composed of a name and a type
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Alias {

    /**
     * The alias type is a controlled vocabulary term.
     * The type can be null.
     * @return the type of the current alias
     */
    public CvTerm getType();

    /**
     * Alias name cannot be null.
     * @return the alias name.
     */
    public String getName();
}
