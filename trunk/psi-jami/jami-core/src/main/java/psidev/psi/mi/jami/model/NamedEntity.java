package psidev.psi.mi.jami.model;

/**
 * A named entity is an entity having a short name and a fullname in addition to aliases.
 * The shortname is used for displaying the entity and the fullname is the description of the entity.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public interface NamedEntity<F extends Feature> extends Entity<F>{
    /**
     * The short name of the entity.
     * It can be null or empty.
     * @return the short name
     */
    public String getShortName();

    /**
     * Sets the short name of an entity
     * @param name : short name
     */
    public void setShortName(String name);

    /**
     * The full name of the entity.
     * It can be null
     * @return the full name
     */
    public String getFullName();

    /**
     * Sets the full name of the entity
     * @param name : full name
     */
    public void setFullName(String name);
}
