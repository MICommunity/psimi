package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.CollectionComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeCollectionComparator;
import psidev.psi.mi.jami.utils.comparator.range.RangeComparator;
import psidev.psi.mi.jami.utils.comparator.xref.XrefsCollectionComparator;

import java.util.Collection;
import java.util.Comparator;

/**
 * feature comparator.
 * It will look first at the feature shortnames (case insensitive). Then, it will compare the feature types using a UnambiguousCvTermComparator. If the feature types are the same,
 * it will compare interactionEffect and then interactionDependency using UnambiguousCvTermComparator. Then it will compare interpro identifier and if the features do not have an interpro identifier,
 * it will look for at the identifiers in the feature identifiers using UnambiguousIdentifierComparator.
 * Finally, it will look at the ranges using UnambiguousRangeComparator.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class FeatureBaseComparator implements Comparator<Feature> {

    private CollectionComparator<Xref> externalIdentifierCollectionComparator;
    private Comparator<CvTerm> cvTermComparator;
    private Comparator<Xref> identifierComparator;
    private CollectionComparator<Range> rangeCollectionComparator;

    /**
     * Creates a new UnambiguousFeatureBaseComparator. It will use a UnambiguousCvTermComparator to compare feature types and range status,
     * a UnambiguousExternalIdentifierComparator to compare identifiers and a UnambiguousRangeComparator to compare ranges
     */
    public FeatureBaseComparator(Comparator<CvTerm> cvComparator, Comparator<Xref> identifierComparator,
                                 RangeComparator rangeComparator) {
        if (cvComparator == null){
            throw new IllegalArgumentException("Cannot compare feature type without a cv comparator. It cannot be null");
        }
        this.cvTermComparator = cvComparator;
        if (identifierComparator == null){
            throw new IllegalArgumentException("The identifier comparator cannot be null");
        }
        this.identifierComparator = identifierComparator;
        this.externalIdentifierCollectionComparator = new XrefsCollectionComparator(identifierComparator);
        if (rangeComparator == null){
            throw new IllegalArgumentException("The range comparator cannot be null");
        }
        this.rangeCollectionComparator = new RangeCollectionComparator(rangeComparator);
    }

    public FeatureBaseComparator(Comparator<CvTerm> cvComparator, CollectionComparator<Xref> identifierComparator,
                                 CollectionComparator<Range> rangeComparator) {
        if (cvComparator == null){
            throw new IllegalArgumentException("Cannot compare feature type without a cv comparator. It cannot be null");
        }
        this.cvTermComparator = cvComparator;
        if (identifierComparator == null){
            throw new IllegalArgumentException("The identifier comparator cannot be null");
        }
        this.externalIdentifierCollectionComparator = identifierComparator;
        this.identifierComparator = identifierComparator.getObjectComparator();
        if (rangeComparator == null){
            throw new IllegalArgumentException("The range comparator cannot be null");
        }
        this.rangeCollectionComparator = rangeComparator;
    }

    public CollectionComparator<Xref> getExternalIdentifierCollectionComparator() {
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

        if (feature1 == feature2){
            return 0;
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
            CvTerm interactionEffect1 = feature1.getRole();
            CvTerm interactionEffect2 = feature2.getRole();

            comp = cvTermComparator.compare(interactionEffect1, interactionEffect2);
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

    public Comparator<CvTerm> getCvTermComparator() {
        return this.cvTermComparator;
    }

    public Comparator<Xref> getIdentifierComparator() {
        return this.identifierComparator;
    }

    public CollectionComparator<Range> getRangeCollectionComparator() {
        return rangeCollectionComparator;
    }
}
