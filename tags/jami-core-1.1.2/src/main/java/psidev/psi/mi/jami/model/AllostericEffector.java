package psidev.psi.mi.jami.model;

/**
 * The effector that elicits an allosteric response.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface AllostericEffector {

    /**
     * The type of allosteric effector.
     * This method cannot return null.
     * Example: molecule, feature_modification, other,...
     * @return type of allosteric effector.
     */
    public AllostericEffectorType getEffectorType();
}
