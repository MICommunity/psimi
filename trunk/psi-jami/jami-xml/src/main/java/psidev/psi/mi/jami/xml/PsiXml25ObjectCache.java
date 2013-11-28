package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.*;

import java.util.Set;

/**
 * Index that can assign/retrieve an id for a given MI object in a compact XML environment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public interface PsiXml25ObjectCache {

    /**
     * This method will extract the id for this object if it is already registered,
     * assign an id and register the object if not already registered
     * @param av
     * @return the id assigned to this object
     */
    public int extractIdForAvailability(String av);

    /**
     * This method will extract the id for this object if it is already registered,
     * assign an id and register the object if not already registered
     * @param o
     * @return the id assigned to this object
     */
    public int extractIdForExperiment(Experiment o);

    /**
     * This method will extract the id for this object if it is already registered,
     * assign an id and register the object if not already registered
     * @param o
     * @return the id assigned to this object
     */
    public int extractIdForInteractor(Interactor o);

    /**
     * This method will extract the id for this object if it is already registered,
     * assign an id and register the object if not already registered
     * @param o
     * @return the id assigned to this object
     */
    public int extractIdForInteraction(Interaction o);

    /**
     * This method will extract the id for this object if it is already registered,
     * assign an id and register the object if not already registered
     * @param o
     * @return the id assigned to this object
     */
    public int extractIdForParticipant(Entity o);

    /**
     * This method will extract the id for this object if it is already registered,
     * assign an id and register the object if not already registered
     * @param o
     * @return the id assigned to this object
     */
    public int extractIdForFeature(Feature o);

    /**
     * This method will extract the id for this object if it is already registered,
     * assign an id and register the object if not already registered
     * @param o
     * @return the id assigned to this object
     */
    public int extractIdForComplex(Complex o);

    /**
     * Clear registered complexes and object ids
     */
    public void clear();

    /**
     * True if it contains this object
     * @param o
     * @return
     */
    public boolean contains(Object o);

    /**
     * This method will register a complex that is used as an interactor
     * @param c
     */
    public void registerSubComplex(ModelledInteraction c);

    /**
     * This method will return all registered complexes and clear them from the index
     * @return
     */
    public Set<ModelledInteraction> clearRegisteredSubComplexes();

    /**
     *
     * @return true if the index has registered som sub complexes, fasle otherwise
     */
    public boolean hasRegisteredSubComplexes();

    public void removeObject(Object o);
}
