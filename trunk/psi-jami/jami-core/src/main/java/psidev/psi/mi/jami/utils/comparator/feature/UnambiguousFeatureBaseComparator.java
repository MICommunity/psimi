package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousRangeComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Unambiguous feature comparator.
 * It will look first at the feature types using a UnambiguousCvTermComparator. If the feature types are the same, it will compare the interpro identifiers.
 * if interpro identifiers are the same, it will look at the ranges using UnambiguousRangeComparator.
 *
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
        super(new UnambiguousCvTermComparator(), new UnambiguousExternalIdentifierComparator());
    }

    @Override
    /**
     * It will look first at the feature types using a UnambiguousCvTermComparator. If the feature types are the same, it will compare the interpro identifiers.
     * if interpro identifiers are the same, it will look at the ranges using UnambiguousRangeComparator.
     */
    public int compare(Feature feature1, Feature feature2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (feature1 == null && feature2 == null){
            return EQUAL;
        }
        else if (feature1 == null){
            return AFTER;
        }
        else if (feature2 == null){
            return BEFORE;
        }
        else {
            // first compares feature types
            CvTerm type1 = feature1.getType();
            CvTerm type2 = feature2.getType();

            int comp = cvTermComparator.compare(type1, type2);
            if (comp != 0){
                return comp;
            }

            // then compares feature effect
            CvTerm interactionEffect1 = feature1.getInteractionEffect();
            CvTerm interactionEffect2 = feature2.getInteractionEffect();

            comp = cvTermComparator.compare(interactionEffect1, interactionEffect2);
            if (comp != 0){
                return comp;
            }

            // then compares feature dependency with interaction
            CvTerm interactionDependency1 = feature1.getInteractionDependency();
            CvTerm interactionDependency2 = feature2.getInteractionDependency();

            comp = cvTermComparator.compare(interactionDependency1, interactionDependency2);
            if (comp != 0){
                return comp;
            }

            String interpro1 = feature1.getInterpro();
            String interpro2 = feature2.getInterpro();

            if (interpro1 != null && interpro2 != null){
                comp = interpro1.compareTo(interpro2);
                if (comp != 0){
                    return comp;
                }
            }
            else if (interpro1 != null){
                return BEFORE;
            }
            else if (interpro2 != null){
                return AFTER;
            }

            // then compares the ranges
            Collection<Range> ranges1 = feature1.getRanges();
            Collection<Range> ranges2 = feature2.getRanges();

            return rangeCollectionComparator.compare(ranges1, ranges2);
        }
    }

    @Override
    public UnambiguousCvTermComparator getCvTermComparator() {
        return (UnambiguousCvTermComparator) this.cvTermComparator;
    }

    @Override
    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return (UnambiguousExternalIdentifierComparator) this.identifierComparator;
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
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(feature.getType());
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(feature.getInteractionEffect());
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(feature.getInteractionDependency());
        hashcode = 31*hashcode + (feature.getInterpro() != null ? feature.getInterpro().hashCode() : 0);
        List<Range> list1 = new ArrayList<Range>(feature.getRanges());

        Collections.sort(list1, unambiguousFeatureComparator.getRangeCollectionComparator().getObjectComparator());
        for (Range range : list1){
            hashcode = 31*hashcode + UnambiguousRangeComparator.hashCode(range);
        }

        return hashcode;
    }
}
