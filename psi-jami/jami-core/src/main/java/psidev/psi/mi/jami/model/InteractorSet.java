package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * InteractorSet represents a collection of potential interactors but we cannot determine which one interacts.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/01/13</pre>
 */

public interface InteractorSet extends Interactor,Set<Molecule>{

}
