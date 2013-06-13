package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.range.DefaultRangeAndResultingSequenceComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.xref.DefaultExternalIdentifierComparator;

import java.util.*;

/**
 * Default feature comparator.
 * It will look first at the feature shortnames if both shortnames are set (case insensitive). Then, it will compare the feature types using a DefaultCvTermComparator. If the feature types are the same,
 * it will compare interactionEffect and then interactionDependency using DefaultCvTermComparator. Then it will look for at least one same identifier in the
 * feature identifiers using DefaultExternalIdentifierComparator if both feature identifiers are not empty. If at least ont feature identifier is the same, it will look at
 * the ranges using DefaultRangeComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultFeatureBaseComparator implements Comparator<Feature> {

    private static DefaultFeatureBaseComparator defaultFeatureComparator;
    private DefaultCvTermComparator cvTermComparator;
    private DefaultExternalIdentifierComparator identifierComparator;
    private RangeCollectionComparator rangeCollectionComparator;

    /**
     * Creates a new DefaultFeatureBaseComparator. It will use a DefaultCvTermComparator to compare feature types and range status,
     * a DefaultExternalIdentifierComparator to compare identifiers and a DefaultRangeComparator to compare ranges
     */
    public DefaultFeatureBaseComparator() {
        this.cvTermComparator = new DefaultCvTermComparator();
        this.identifierComparator = new DefaultExternalIdentifierComparator();
        this.rangeCollectionComparator = new RangeCollectionComparator(new DefaultRangeAndResultingSequenceComparator());
    }

    /**
     * It will look first at the feature shortnames if both shortnames are set (case insensitive). Then, it will compare the feature types using a DefaultCvTermComparator. If the feature types are the same,
     * it will compare interactionEffect and then interactionDependency using DefaultCvTermComparator. Then it will look for at least one same identifier in the
     * feature identifiers using DefaultExternalIdentifierComparator if both feature identifiers are not empty. If at least ont feature identifier is the same, it will look at
     * the ranges using DefaultRangeComparator.
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

            // then compares interpro
            String interpro1 = feature1.getInterpro();
            String interpro2 = feature2.getInterpro();

            if (interpro1 != null && interpro2 != null){
                comp = interpro1.compareTo(interpro2);
                if (comp != 0){
                    return comp;
                }
            }
            // or compare identifiers
            else if (!feature1.getIdentifiers().isEmpty() && !feature2.getIdentifiers().isEmpty()){
                // then compares the external identifiers. At least one should match
                List<Xref> ids1 = new ArrayList<Xref>(feature1.getIdentifiers());
                List<Xref> ids2 = new ArrayList<Xref>(feature2.getIdentifiers());
                // sort the collections first
                Collections.sort(ids1, identifierComparator);
                Collections.sort(ids2, identifierComparator);
                // get an iterator
                Iterator<Xref> iterator1 = ids1.iterator();
                Iterator<Xref> iterator2 = ids2.iterator();
                Xref altid1 = iterator1.next();
                Xref altid2 = iterator2.next();
                comp = identifierComparator.compare(altid1, altid2);
                while (comp != 0 && altid1 != null && altid2 != null){
                    // altid1 is before altid2
                    if (comp < 0){
                        // we need to get the next element from ids1
                        if (iterator1.hasNext()){
                            altid1 = iterator1.next();
                            comp = identifierComparator.compare(altid1, altid2);
                        }
                        // ids 1 is empty, we can stop here
                        else {
                            altid1 = null;
                        }
                    }
                    // altid2 is before altid1
                    else {
                        // we need to get the next element from ids2
                        if (iterator2.hasNext()){
                            altid2 = iterator2.next();
                            comp = identifierComparator.compare(altid1, altid2);
                        }
                        // ids 2 is empty, we can stop here
                        else {
                            altid2 = null;
                        }
                    }
                }

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

    public DefaultCvTermComparator getCvTermComparator() {
        return this.cvTermComparator;
    }

    public DefaultExternalIdentifierComparator getIdentifierComparator() {
        return this.identifierComparator;
    }

    public RangeCollectionComparator getRangeCollectionComparator() {
        return rangeCollectionComparator;
    }

    /**
     * Use DefaultFeatureBaseComparator to know if two features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two features are equal
     */
    public static boolean areEquals(Feature feature1, Feature feature2){
        if (defaultFeatureComparator == null){
            defaultFeatureComparator = new DefaultFeatureBaseComparator();
        }

        return defaultFeatureComparator.compare(feature1, feature2) == 0;
    }
}
