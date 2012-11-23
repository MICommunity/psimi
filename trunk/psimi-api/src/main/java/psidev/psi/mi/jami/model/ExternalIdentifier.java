package psidev.psi.mi.jami.model;

/**
 * External identifier. It contains a database CvTerm and an id.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/12</pre>
 */
public interface ExternalIdentifier {

    public CvTerm getDatabase();
    public void setDatabase(CvTerm database);

    public String getId();
    public void setId(String id);
}
