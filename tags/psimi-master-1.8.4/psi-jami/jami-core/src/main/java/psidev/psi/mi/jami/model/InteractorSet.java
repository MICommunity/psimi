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

    public static final String MOLECULE_SET="molecule set";
    public static final String MOLECULE_SET_MI="MI:1304";
    public static final String CANDIDATE_SET="candidate set";
    public static final String CANDIDATE_SET_MI="MI:1305";
    public static final String DEFINED_SET="defined set";
    public static final String DEFINED_SET_MI="MI:1307";
    public static final String OPEN_SET="open set";
    public static final String OPEN_SET_MI="MI:1306";
}
