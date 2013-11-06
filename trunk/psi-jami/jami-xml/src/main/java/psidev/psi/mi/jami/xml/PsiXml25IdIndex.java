package psidev.psi.mi.jami.xml;

/**
 * Index that can retrieve an object given its id
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/11/13</pre>
 */

public interface PsiXml25IdIndex {

    /**
     * Retrieves an object given its id
     * @param id
     * @return
     */
    public Object get(int id);

    /**
     * Register an object with an id
     * @param id
     * @param object
     */
    public void put(int id, Object object);

    /**
     * Clear all existing records
     */
    public void clear();

    /**
     * To know if it contains a specific id
     * @param id
     * @return
     */
    public boolean contains(int id);
}
