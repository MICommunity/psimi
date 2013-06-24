package psidev.psi.mi.jami.utils.comparator.annotation;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

/**
 * Default annotation comparator
 * It compares first the topics and then the value (case insensitive)
 * - Two annotations which are null are equals
 * - The annotation which is not null is before null.
 * - use DefaultCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
 * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class DefaultAnnotationComparator {

    /**
     * Use DefaultAnnotationComparator to know if two annotations are equals.
     * @param annotation1
     * @param annotation2
     * @return true if the two annotations are equal
     */
    public static boolean areEquals(Annotation annotation1, Annotation annotation2){
        if (annotation1 == null && annotation2 == null){
            return true;
        }
        else if (annotation1 == null || annotation2 == null){
            return false;
        }
        else {
            CvTerm topic1 = annotation1.getTopic();
            CvTerm topic2 = annotation2.getTopic();

            if (!DefaultCvTermComparator.areEquals(topic1, topic2)){
                return false;
            }
            // check annotation text
            String text1 = annotation1.getValue();
            String text2 = annotation2.getValue();

            if (text1 == null && text2 == null){
                return true;
            }
            else if (text1 == null || text2 == null){
                return false;
            }
            else {

                return text1.toLowerCase().trim().equals(text2.toLowerCase().trim());
            }
        }
    }
}
