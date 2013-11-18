package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.ModelledInteraction;

import java.util.Set;

/**
 * Index that can assign/retrieve an id for a given MI object.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public interface PsiXml25ObjectIndex {

    public int extractIdFor(Object o);

    /**
     * Clear registered complexes and object ids
     */
    public void clear();

    /**
     * True if it contains this object
     * @param o
     * @return
     */
    public boolean contain(Object o);

    /**
     * This method will register a complex that is used as an interactor
     * @param c
     */
    public void registerSubComplex(ModelledInteraction c);

    /**
     * This method will return all registered complexes and clear them from the index
     * @return
     */
    public Set<ModelledInteraction> clearRegisteredComplexes();

    /**
     *
     * @return true if the index has registered som sub complexes, fasle otherwise
     */
    public boolean hasRegisteredSubComplexes();
}
