package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;

import java.util.Comparator;

/**
 * Comparator for collection of Features
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class FeatureCollectionComparator<T extends Feature> extends CollectionComparator<T> {
    /**
     * Creates a new feature CollectionComparator. It requires a Comparator for the ranges in the Collection
     *
     * @param featureComparator
     */
    public FeatureCollectionComparator(Comparator<T> featureComparator) {
        super(featureComparator);
    }

    @Override
    public Comparator<T> getObjectComparator() {
        return (Comparator<T>) objectComparator;
    }
}
