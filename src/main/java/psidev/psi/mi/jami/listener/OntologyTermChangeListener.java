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
     * @param o : updated term
     * @param added : added parent
     */
    public void onAddedParent(OntologyTerm o, OntologyTerm added);

    /**
     * Listen to the event where a parent has been removed from the object parents.
     * @param o : updated term
     * @param removed : removed parent
     */
    public void onRemovedParent(OntologyTerm o, OntologyTerm removed);

    /**
     * Listen to the event where a child has been added to the object children.
     * @param o : updated term
     * @param added : added child
     */
    public void onAddedChild(OntologyTerm o, OntologyTerm added);

    /**
     * Listen to the event where a child has been removed from the object children.
     * @param o : updated term
     * @param removed : removed child
     */
    public void onRemovedChild(OntologyTerm o, OntologyTerm removed);

    /**
     * Listen to the event where the definition of a cv term has been changed.
     * If oldDefinition is null, it means that a definition has been added to the cv term.
     * If the definition of the cv term is null, it means that the definition of the cv term has been removed
     * @param cv : updated term
     * @param oldDef : old definition
     */
    public void onDefinitionUpdate(OntologyTerm cv, String oldDef);
}
