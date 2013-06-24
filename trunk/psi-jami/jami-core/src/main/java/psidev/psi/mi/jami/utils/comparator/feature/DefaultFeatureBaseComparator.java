package psidev.psi.mi.jami.utils.comparator.feature;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.util.Collection;

/**
 * Default feature comparator.
 * It will look first at the feature shortnames if both shortnames are set (case insensitive). Then, it will compare the feature types using a DefaultCvTermComparator. If the feature types are the same,
 * it will compare interactionEffect and then interactionDependency using DefaultCvTermComparator. Then it will look for at least one same identifier in the
 * feature identifiers using DefaultExternalIdentifierComparator if both feature identifiers are not empty. If at least ont feature identifier is the same, it will look at
 * the ranges using DefaultRangeAndResultingSequenceComparator.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/01/13</pre>
 */

public class DefaultFeatureBaseComparator {

    /**
     * Use DefaultFeatureBaseComparator to know if two features are equals.
     * @param feature1
     * @param feature2
     * @return true if the two features are equal
     */
    public static boolean areEquals(Feature feature1, Feature feature2){
        if (feature1 == null && feature2 == null){
            return true;
        }
        else if (feature1 == null || feature2 == null){
            return false;
        }
        else {
            // first compare shortnames
            String name1 = feature1.getShortName();
            String name2 = feature2.getShortName();
            if (name1 != null && name2 != null){
                if (!name1.toLowerCase().trim().equals(name2.toLowerCase().trim())){
                    return false;
                }
            }

            // then compares feature types
            CvTerm type1 = feature1.getType();
            CvTerm type2 = feature2.getType();

            if (!DefaultCvTermComparator.areEquals(type1, type2)){
                return false;
            }

            // then compares feature effect
            CvTerm interactionEffect1 = feature1.getInteractionEffect();
            CvTerm interactionEffect2 = feature2.getInteractionEffect();

            if (!DefaultCvTermComparator.areEquals(interactionEffect1, interactionEffect2)){
                return false;
            }

            // then compares feature dependency with interaction
            CvTerm interactionDependency1 = feature1.getInteractionDependency();
            CvTerm interactionDependency2 = feature2.getInteractionDependency();

            if (!DefaultCvTermComparator.areEquals(interactionDependency1, interactionDependency2)){
                return false;
            }

            // then compares interpro
            String interpro1 = feature1.getInterpro();
            String interpro2 = feature2.getInterpro();

            if (interpro1 != null && interpro2 != null){
                if (!interpro1.equals(interpro2)){
                    return false;
                }
            }
            // or compare identifiers
            else if (!feature1.getIdentifiers().isEmpty() && !feature2.getIdentifiers().isEmpty()){
                if (!ComparatorUtils.findAtLeastOneMatchingIdentifier(feature1.getIdentifiers(), feature2.getIdentifiers())){
                    return false;
                }
            }

            // then compares the ranges
            Collection<Range> ranges1 = feature1.getRanges();
            Collection<Range> ranges2 = feature2.getRanges();

            return ComparatorUtils.areRangesEqual(ranges1, ranges2);
        }
    }
}
