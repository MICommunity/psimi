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

    /**
     * Sets the allosteric feature effector
     * @param modification : the allosteric feature effector
     * @throws IllegalArgumentException if modification is null
     */
    public void setMolecule(ModelledFeature modification);
}
