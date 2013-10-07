package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * A set of entities that do not interact between each other but that are interacting with other molecules/entities and
 * have common properties (open set of molecules, defined set, etc...).
 *
 * An entity set is a participant but is itself composed of several entities.
 *
 * Ex: a collection of potential interactors but we cannot determine which one interacts
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/10/13</pre>
 */

public interface EntitySet<I extends Interaction, F extends Feature, C extends Entity> extends Participant<I,F>, Set<C> {

    public static final String ENTITY_SET="molecule set";
    public static final String ENTITY_SET_MI="MI:1304";

    /**
     * The interactor group type of this participant.
     * It is a controlled vocabulary term and cannot be null.
     * Ex: protein, gene, small molecule, ...
     * @return interactor type
     */
    public CvTerm getType();

    /**
     * Sets the molecule type for this interactor
     * If the given type is null, this method utomatically sets the interactor type to 'unknown participant' (MI:0329)
     * @param type : molecule type
     */
    public void setType(CvTerm type);
}
