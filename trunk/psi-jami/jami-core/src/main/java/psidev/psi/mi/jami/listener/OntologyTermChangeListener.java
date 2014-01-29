package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * OntologyTerm change listener
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public interface OntologyTermChangeListener extends CvTermChangeListener {

    /**
     * Listen to the event where a parent has been added to the object parents.
     * @param o
     * @param added
     */
    public void onAddedParent(OntologyTerm o, OntologyTerm added);

    /**
     * Listen to the event where a parent has been removed from the object parents.
     * @param o
     * @param removed
     */
    public void onRemovedParent(OntologyTerm o, OntologyTerm removed);

    /**
     * Listen to the event where a child has been added to the object children.
     * @param o
     * @param added
     */
    public void onAddedChild(OntologyTerm o, OntologyTerm added);

    /**
     * Listen to the event where a child has been removed from the object children.
     * @param o
     * @param removed
     */
    public void onRemovedChild(OntologyTerm o, OntologyTerm removed);
}
