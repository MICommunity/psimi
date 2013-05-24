package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.AbstractCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.range.PositionComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeComparator;

import java.util.*;

/**
 * Abstract feature comparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/01/13</pre>
 */

public abstract class AbstractFeatureBaseComparator implements Comparator<Feature> {

    protected AbstractCvTermComparator cvTermComparator;
    protected Comparator<Xref> identifierComparator;
    protected RangeCollectionComparator rangeCollectionComparator;

    /**
     * Creates a new AbstractFeatureBaseComparator.
     * @param cvTermComparator : CvTerm comparator required for comparing feature types and range status positions
     * @param identifierComparator : ExternalIdentifier comparator required for comparing feature identifiers
     */
    public AbstractFeatureBaseComparator(AbstractCvTermComparator cvTermComparator, Comparator<Xref> identifierComparator){

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
     *
     * @param feature1
     * @param feature2
     * @return
     */
    public abstract int compare(Feature feature1, Feature feature2);

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
