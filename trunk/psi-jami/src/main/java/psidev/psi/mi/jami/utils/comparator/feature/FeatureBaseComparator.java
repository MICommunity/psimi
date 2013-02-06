package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.range.PositionComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeComparator;

import java.util.*;

/**
 * Basic feature comparator.
 * It will look first at the feature types using an AbstractCvTermComparator. If the feature types are the same, it will look at the
 * feature identifiers using Comparator<ExternalIdentifier>. If at least one of the feature identifiers are the same, it will look at
 * the ranges using RangeComparator.
 *
 * This comparator will ignore all the other properties of a feature.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class FeatureBaseComparator implements Comparator<Feature> {

    protected AbstractCvTermComparator cvTermComparator;
    protected Comparator<Xref> identifierComparator;
    protected RangeCollectionComparator rangeCollectionComparator;

    /**
     * Creates a new FeatureBaseComparator.
     * @param cvTermComparator : CvTerm comparator required for comparing feature types and range status positions
     * @param identifierComparator : ExternalIdentifier comparator required for comparing feature identifiers
     */
    public FeatureBaseComparator(AbstractCvTermComparator cvTermComparator, Comparator<Xref> identifierComparator){

        if (cvTermComparator == null){
            throw new IllegalArgumentException("The CvTerm comparator is required to compare feature types and range status positions. It cannot be null");
        }
        this.cvTermComparator = cvTermComparator;

        if (identifierComparator == null){
            throw new IllegalArgumentException("The ExternalIdentifier comparator is required to compare feature external identifiers. It cannot be null");
        }
        this.identifierComparator = identifierComparator;

        this.rangeCollectionComparator = new RangeCollectionComparator(new RangeComparator(new PositionComparator(this.cvTermComparator)));
    }

    /**
     * It will look first at the feature types using an AbstractCvTermComparator. If the feature types are the same, it will look at the
     * feature identifiers using Comparator<ExternalIdentifier>. If at least one of the feature identifiers are the same, it will look at
     * the ranges using RangeComparator.
     *
     * This comparator will ignore all the other properties of a feature.
     * @param feature1
     * @param feature2
     * @return
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

            // then compares the external identifiers. At least one should match
            List<ExternalIdentifier> ids1 = new ArrayList<ExternalIdentifier>(feature1.getIdentifiers());
            List<ExternalIdentifier> ids2 = new ArrayList<ExternalIdentifier>(feature2.getIdentifiers());
            // sort the collections first
            Collections.sort(ids1, identifierComparator);
            Collections.sort(ids2, identifierComparator);
            // get an iterator
            Iterator<ExternalIdentifier> iterator1 = ids1.iterator();
            Iterator<ExternalIdentifier> iterator2 = ids2.iterator();
            ExternalIdentifier altid1 = iterator1.next();
            ExternalIdentifier altid2 = iterator2.next();
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

            // then compares the ranges
            Collection<Range> ranges1 = feature1.getRanges();
            Collection<Range> ranges2 = feature2.getRanges();

            return rangeCollectionComparator.compare(ranges1, ranges2);
        }
    }

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public Comparator<Xref> getIdentifierComparator() {
        return identifierComparator;
    }

    public RangeCollectionComparator getRangeCollectionComparator() {
        return rangeCollectionComparator;
    }
}
