package psidev.psi.mi.jami.model;

/**
 * Alias is a synonym. It is composed of a name and a type
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Alias {

    public CvTerm getType();
    public void setType(CvTerm type);

    public String getName();
    public void setName(String name);
}
