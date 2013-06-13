package psidev.psi.mi.jami.utils.comparator.annotation;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Comparator;

/**
 * Simple comparator for annotations
 * It compares first the topics and then the value (case insensitive)
 * - Two annotations which are null are equals
 * - The annotation which is not null is before null.
 * - use AbstractCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
 * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class AnnotationComparator implements Comparator<Annotation> {

    protected Comparator<CvTerm> topicComparator;

    /**
     * Creates a new AnnotationComparator.
     * @param topicComparator : the CvTerm comparator to compare the topics. It is required
     */
    public AnnotationComparator(Comparator<CvTerm> topicComparator){
        if (topicComparator == null){
           throw new IllegalArgumentException("The CvTerm comparator is required to compare topics. It cannot be null");
        }
        this.topicComparator = topicComparator;
    }

    public Comparator<CvTerm> getTopicComparator() {
        return topicComparator;
    }

    /**
     * It compares first the topics and then the value (case insensitive)
     * - Two annotations which are null are equals
     * - The annotation which is not null is before null.
     * - use AbstractCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
     * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
     * @param annotation1
     * @param annotation2
     * @return
     */
    public int compare(Annotation annotation1, Annotation annotation2) {
        int EQUAL = 0;
        int BEFORE = -1;
        int AFTER = 1;

        if (annotation1 == null && annotation2 == null){
            return EQUAL;
        }
        else if (annotation1 == null){
            return AFTER;
        }
        else if (annotation2 == null){
            return BEFORE;
        }
        else {
            CvTerm topic1 = annotation1.getTopic();
            CvTerm topic2 = annotation2.getTopic();

            int comp = topicComparator.compare(topic1, topic2);
            if (comp != 0){
                return comp;
            }
            // check annotation text
            String text1 = annotation1.getValue();
            String text2 = annotation2.getValue();

            if (text1 == null && text2 == null){
                return EQUAL;
            }
            else if (text1 == null){
                return AFTER;
            }
            else if (text2 == null){
                return BEFORE;
            }
            else {

                return text1.toLowerCase().trim().compareTo(text2.toLowerCase().trim());
            }
        }
    }
}
