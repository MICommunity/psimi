package psidev.psi.mi.jami.model;

/**
 * Identifier from an external database or resource which allows to identify an object without ambiguity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/12</pre>
 */
public interface ExternalIdentifier {

    /**
     * The database is a controlled vocabulary term. It cannot be null.
     * @return the database
     */
    public CvTerm getDatabase();

    /**
     * The identifier in the external database/resource. It cannot be null or empty.
     * @return the database identifier
     */
    public String getId();

    /**
     * The version of the identifier in the database/resource if relevant.
     * It can be null if no versions have been specified
     * @return the version
     */
    public Integer getVersion();
}
