package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * Ontology term with definition,  parents and children
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/02/13</pre>
 */

public interface OntologyTerm extends CvTerm {

    /**
     * The definition of the controlled vocabulary as it appears in the ontology.
     * It can be null
     * Ex: electrophoresis is Any method which relies on the motion of particles relative to a matrix under the influence of an electrical field.
     * @return the definition
     */
    public String getDefinition();

    /**
     * Set the definition
     * @param def : definition of the term
     */
    public void setDefinition(String def);

    /**
     * The parent terms of this controlled vocabulary term.
     * The Collection cannot be null. If the term does not have any parents, the method should return an empty Collection.
     * @return the parents
     */
    public Collection<OntologyTerm> getParents();

    /**
     * The children terms of this controlled vocabulary term.
     * The Collection cannot be null. If the term does not have any children, the method should return an empty Collection.
     * @return the children
     */
    public Collection<OntologyTerm> getChildren();
}
