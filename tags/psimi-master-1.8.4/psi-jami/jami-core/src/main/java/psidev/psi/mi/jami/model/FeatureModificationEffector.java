package psidev.psi.mi.jami.model;

/**
 * The modification (feature) that elicits an allosteric response in an allosteric molecule.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface FeatureModificationEffector extends AllostericEffector {

    /**
     * Refers to the modification (feature) that elicits an allosteric response in an allosteric molecule.
     * It cannot be null.
     * @return the feature that elicits the allosteric response.
     */
    public ModelledFeature getFeatureModification();
}
