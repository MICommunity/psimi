package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.range.DefaultRangeComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

/**
 * Unambiguous feature comparator.
 * It will look first at the feature types using a UnambiguousCvTermComparator. If the feature types are the same, it will look at the
 * feature identifiers using UnambiguousExternalIdentifierComparator. If the feature identifiers are the same, it will look at
 * the ranges using UnambiguousRangeComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class UnambiguousFeatureComparator extends FeatureComparator {

    private static UnambiguousFeatureComparator unambiguousFeatureComparator;

    /**
     * Creates a new UnambiguousFeatureComparator. It will use a UnambiguousCvTermComparator to compare feature types,
     * a UnambiguousExternalIdentifierComparator to compare identifiers and a UnambiguousRangeComparator to compare ranges
     */
    public UnambiguousFeatureComparator() {
        super(new DefaultCvTermComparator(), new DefaultExternalIdentifierComparator(), new DefaultRangeComparator());
    }

    @Override
    /**
     * It will look first at the feature types using a UnambiguousCvTermComparator. If the feature types are the same, it will look at the
     * feature identifiers using UnambiguousExternalIdentifierComparator. If the feature identifiers are the same, it will look at
     * the ranges using UnambiguousRangeComparator.
     */
    public int compare(Feature feature1, Feature feature2) {
        return super.compare(feature1, feature2);
    }

    @Override
    public DefaultCvTermComparator getCvTermComparator() {
        return (DefaultCvTermComparator) this.cvTermComparator;
    }

    @Override
    public DefaultExternalIdentifierComparator getIdentifierComparator() {
        return (DefaultExternalIdentifierComparator) this.identifierComparator;
    }

    /**
     * Use UnambiguousFeatureComparator to know if two features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two features are equal
     */
    public static boolean areEquals(Feature feature1, Feature feature2){
        if (unambiguousFeatureComparator == null){
            unambiguousFeatureComparator = new UnambiguousFeatureComparator();
        }

        return unambiguousFeatureComparator.compare(feature1, feature2) == 0;
    }
}
