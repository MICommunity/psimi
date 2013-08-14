package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousRangeAndResultingSequenceComparator;
import psidev.psi.mi.jami.utils.comparator.range.UnambiguousRangeComparator;
import psidev.psi.mi.jami.utils.comparator.xref.UnambiguousExternalIdentifierComparator;
import psidev.psi.mi.jami.utils.comparator.xref.XrefsCollectionComparator;

import java.util.*;

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

public class UnambiguousFeatureBaseComparator implements Comparator<Feature> {

    private static UnambiguousFeatureBaseComparator unambiguousFeatureComparator;
    private XrefsCollectionComparator externalIdentifierCollectionComparator;
    private UnambiguousCvTermComparator cvTermComparator;
    private UnambiguousExternalIdentifierComparator identifierComparator;
    private RangeCollectionComparator rangeCollectionComparator;

    /**
     * Creates a new UnambiguousFeatureBaseComparator. It will use a UnambiguousCvTermComparator to compare feature types and range status,
     * a UnambiguousExternalIdentifierComparator to compare identifiers and a UnambiguousRangeComparator to compare ranges
     */
    public UnambiguousFeatureBaseComparator() {
        this.cvTermComparator = new UnambiguousCvTermComparator();
        this.identifierComparator = new UnambiguousExternalIdentifierComparator();
        this.externalIdentifierCollectionComparator = new XrefsCollectionComparator(getIdentifierComparator());
        this.rangeCollectionComparator = new RangeCollectionComparator(new UnambiguousRangeAndResultingSequenceComparator());
    }

    public XrefsCollectionComparator getExternalIdentifierCollectionComparator() {
        return externalIdentifierCollectionComparator;
    }

    /**
     * It will look first at the feature shortnames (case insensitive). Then, it will compare the feature types using a UnambiguousCvTermComparator. If the feature types are the same,
     * it will compare interactionEffect and then interactionDependency using UnambiguousCvTermComparator. Then it will compare interpro identifier and if the features do not have an interpro identifier,
     * it will look for at the identifiers in the feature identifiers using UnambiguousIdentifierComparator.
     * Finally, it will look at the ranges using UnambiguousRangeComparator.
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
            // first compare shortnames
            String name1 = feature1.getShortName();
            String name2 = feature2.getShortName();
            if (name1 != null && name2 != null){
                int comp = name1.toLowerCase().trim().compareTo(name2.toLowerCase().trim());
                if (comp != 0){
                    return comp;
                }
            }
            else if (name1 != null){
                return BEFORE;
            }
            else if (name2 != null){
                return AFTER;
            }

            // then compares feature types
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
            // compare all identifiers
            else {
                Collection<Xref> identifiers1 = feature1.getIdentifiers();
                Collection<Xref> identifiers2 = feature2.getIdentifiers();

                comp = externalIdentifierCollectionComparator.compare(identifiers1, identifiers2);
                if (comp != 0){
                    return comp;
                }
            }

            // then compares the ranges
            Collection<Range> ranges1 = feature1.getRanges();
            Collection<Range> ranges2 = feature2.getRanges();

            return rangeCollectionComparator.compare(ranges1, ranges2);
        }
    }

    public UnambiguousCvTermComparator getCvTermComparator() {
        return this.cvTermComparator;
    }

    public UnambiguousExternalIdentifierComparator getIdentifierComparator() {
        return this.identifierComparator;
    }

    public RangeCollectionComparator getRangeCollectionComparator() {
        return rangeCollectionComparator;
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
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(feature.getInteractionEffect());
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(feature.getInteractionDependency());
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
