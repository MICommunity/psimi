package psidev.psi.mi.jami.utils.comparator.annotation;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

/**
 * Strict annotation comparator
 * It compares first the topics and then the value (case insensitive)
 * - Two annotations which are null are equals
 * - The annotation which is not null is before null.
 * - use UnambiguousCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
 * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public class UnambiguousAnnotationComparator extends AnnotationComparator {

    private static UnambiguousAnnotationComparator unambiguousAnnotationComparator;


    /**
     * Creates a new AnnotationComparator with UnambiguousCvTermComparator
     *
     */
    public UnambiguousAnnotationComparator() {
        super(new UnambiguousCvTermComparator());
    }

    @Override
    public UnambiguousCvTermComparator getTopicComparator() {
        return (UnambiguousCvTermComparator) topicComparator;
    }

    /**
     * It compares first the topics and then the value (case insensitive)
     * - Two annotations which are null are equals
     * - The annotation which is not null is before null.
     * - use UnambiguousCvTermComparator to compare the topics. If they are equals, compares the values (case insensitive)
     * - If both annotations have same topic, the one with a null value is always after the one with a non null value.
     * @param annotation1
     * @param annotation2
     * @return
     */
    public int compare(Annotation annotation1, Annotation annotation2) {
        return super.compare(annotation1, annotation2);
    }

    /**
     * Use UnambiguousAnnotationComparator to know if two annotations are equals.
     * @param annotation1
     * @param annotation2
     * @return true if the two annotations are equal
     */
    public static boolean areEquals(Annotation annotation1, Annotation annotation2){
        if (unambiguousAnnotationComparator == null){
            unambiguousAnnotationComparator = new UnambiguousAnnotationComparator();
        }

        return unambiguousAnnotationComparator.compare(annotation1, annotation2) == 0;
    }

    /**
     *
     * @param annot
     * @return the hashcode consistent with the equals method for this comparator
     */
    public static int hashCode(Annotation annot){
        if (unambiguousAnnotationComparator == null){
            unambiguousAnnotationComparator = new UnambiguousAnnotationComparator();
        }

        if (annot == null){
            return 0;
        }

        int hashcode = 31;
        CvTerm topic = annot.getTopic();
        hashcode = 31*hashcode + UnambiguousCvTermComparator.hashCode(topic);

        String text = annot.getValue();
        hashcode = 31*hashcode + (text != null ? text.toLowerCase().trim().hashCode() : 0);

        return hashcode;
    }
}
