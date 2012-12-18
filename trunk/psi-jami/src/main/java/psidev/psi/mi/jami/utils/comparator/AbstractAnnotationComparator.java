package psidev.psi.mi.jami.utils.comparator;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Comparator;

/**
 * Abstract comparator for annotations.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/12/12</pre>
 */

public abstract class AbstractAnnotationComparator<T extends AbstractCvTermComparator> implements Comparator<Annotation> {

    protected T topicComparator;

    public AbstractAnnotationComparator(){
        instantiateAnnotationComparator();
    }

    protected abstract void instantiateAnnotationComparator();

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

                return text1.toLowerCase().compareTo(text2.toLowerCase());
            }
        }
    }
}
