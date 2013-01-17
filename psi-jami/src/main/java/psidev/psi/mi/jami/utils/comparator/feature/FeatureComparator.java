package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.range.PositionComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * Basic feature comparator.
 * It will look first at the feature types using an AbstractCvTermComparator. If the feature types are the same, it will look at the
 * feature identifiers using Comparator<ExternalIdentifier>. If the feature identifiers are the same, it will look at
 * the ranges using RangeComparator.
 *
 * This comparator will ignore all the other properties of a feature.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public class FeatureComparator implements Comparator<Feature> {

    protected AbstractCvTermComparator cvTermComparator;
    protected Comparator<ExternalIdentifier> identifierComparator;
    protected RangeCollectionComparator rangeCollectionComparator;

    /**
     * Creates a new FeatureComparator.
     * @param cvTermComparator : CvTerm comparator required for comparing feature types and range status positions
     * @param identifierComparator : ExternalIdentifier comparator required for comparing feature identifiers
     */
    public FeatureComparator(AbstractCvTermComparator cvTermComparator, Comparator<ExternalIdentifier> identifierComparator){

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
     * feature identifiers using Comparator<ExternalIdentifier>. If the feature identifiers are the same, it will look at
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

            // then compares the external identifiers
            ExternalIdentifier id1 = feature1.getIdentifier();
            ExternalIdentifier id2 = feature2.getIdentifier();

            if (id1 != null && id2 != null){
                comp = identifierComparator.compare(id1, id2);
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

    public AbstractCvTermComparator getCvTermComparator() {
        return cvTermComparator;
    }

    public Comparator<ExternalIdentifier> getIdentifierComparator() {
        return identifierComparator;
    }

    public RangeCollectionComparator getRangeCollectionComparator() {
        return rangeCollectionComparator;
    }
}
