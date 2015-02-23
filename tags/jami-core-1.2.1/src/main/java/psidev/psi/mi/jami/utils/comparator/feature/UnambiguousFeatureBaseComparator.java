package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousRangeAndResultingSequenceComparator;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousRangeComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous feature comparator.
 * It will look first at the feature shortnames (case insensitive). Then, it will compare the feature types using a UnambiguousCvTermComparator. If the feature types are the same,
 * it will compare interactionEffect and then interactionDependency using UnambiguousCvTermComparator. Then it will compare interpro identifier and if the features do not have an interpro identifier,
 * it will look for at the identifiers in the feature identifiers using UnambiguousIdentifierComparator.
 * Finally, it will look at the ranges using UnambiguousRangeComparator.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class UnambiguousFeatureBaseComparator extends FeatureBaseComparator {

    private static UnambiguousFeatureBaseComparator unambiguousFeatureComparator;

    /**
     * Creates a new UnambiguousFeatureBaseComparator. It will use a UnambiguousCvTermComparator to compare feature types and range status,
     * a UnambiguousExternalIdentifierComparator to compare identifiers and a UnambiguousRangeComparator to compare ranges
     */
    public UnambiguousFeatureBaseComparator() {
        super(new UnambiguousCvTermComparator(), new UnambiguousExternalIdentifierComparator(), new UnambiguousRangeAndResultingSequenceComparator());
    }

    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator)super.getCvTermComparator();
    }

    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator)super.getIdentifierComparator();
    }


    /**
     * Use UnambiguousFeatureBaseComparator to know if two features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two features are equal
     */
    public static boolean areEquals(Feature feature1, Feature feature2){
        if (unambiguousFeatureComparator == null){
            unambiguousFeatureComparator = new UnambiguousFeatureBaseComparator();
        }

        return unambiguousFeatureComparator.compare(feature1, feature2) == 0;
    }

    /**
     *
     * @param feature
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Feature feature){
        if (unambiguousFeatureComparator == null){
            unambiguousFeatureComparator = new UnambiguousFeatureBaseComparator();
        }

        if (feature == null){
            return 0;
        }

        int hashcode = 31;
        hashcode = 31*hashcode + (feature.getShortName() != null ? feature.getShortName().toLowerCase().trim().hashCode() : 0);
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(feature.getType());
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(feature.getRole());
        if (feature.getInterpro() != null){
            hashcode = 31*hashcode + (feature.getInterpro() != null ? feature.getInterpro().hashCode() : 0);
        }
        else {
            List<Xref> identifiers = new ArrayList<Xref>(feature.getIdentifiers());

            Collections.sort(identifiers, unambiguousFeatureComparator.getExternalIdentifierCollectionComparator().getObjectComparator());
            for (Xref ref : identifiers){
                hashcode = 31*hashcode + UnambiguousExternalIdentifierComparator.hashCode(ref);
            }
        }
        List<Range> list1 = new ArrayList<Range>(feature.getRanges());

        Collections.sort(list1, unambiguousFeatureComparator.getRangeCollectionComparator().getObjectComparator());
        for (Range range : list1){
            hashcode = 31*hashcode + UnambiguousRangeComparator.hashCode(range);
        }

        return hashcode;
    }
}
