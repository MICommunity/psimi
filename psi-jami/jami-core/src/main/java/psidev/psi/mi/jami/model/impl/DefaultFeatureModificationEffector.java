package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.AllostericEffectorType;
import psidev.psi.mi.jami.model.FeatureModificationEffector;
import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.utils.comparator.cooperativity.UnambiguousExactMoleculeEffectorComparator;
import psidev.psi.mi.jami.utils.comparator.cooperativity.UnambiguousFeatureModificationEffectorComparator;

/**
 * Default implementation for FeatureModificationEffector
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/05/13</pre>
 */

public class DefaultFeatureModificationEffector implements FeatureModificationEffector {

    private ModelledFeature feature;

    public DefaultFeatureModificationEffector(ModelledFeature feature){
        if (feature == null){
            throw new IllegalArgumentException("The feature of a FeatureModificationEffector cannot be null.");
        }
        this.feature = feature;
    }

    public ModelledFeature getFeatureModification() {
        return feature;
    }

    public AllostericEffectorType getEffectorType() {
        return AllostericEffectorType.feature_modification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof FeatureModificationEffector)){
            return false;
        }

        return UnambiguousFeatureModificationEffectorComparator.areEquals(this, (FeatureModificationEffector) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousFeatureModificationEffectorComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return "feature modification effector: " + feature.toString();
    }
}
