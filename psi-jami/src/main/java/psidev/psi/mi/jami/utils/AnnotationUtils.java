package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility class for Annotation
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class AnnotationUtils {

    public static boolean doesAnnotationHaveTopic(Annotation annotation, String topicId, String topicName){

        if (annotation == null || (topicName == null && topicId == null)){
            return false;
        }

        CvTerm topic = annotation.getTopic();
        // we can compare identifiers
        if (topicId != null && topic.getMIIdentifier() != null){
            // we have the same topic id
            return topic.getMIIdentifier().equals(topicId);
        }
        // we need to compare topic names
        else if (topicName != null) {
            return topicName.toLowerCase().equals(topic.getShortName().toLowerCase());
        }

        return false;
    }

    /**
     * Remove all annotations having this topic name/database id from the collection of annotations
     * @param annotations : the collection of annotations
     * @param topicId : the topic id to look for
     * @param topicName : the topic name to look for
     */
    public static void removeAllAnnotationsWithTopic(Collection<? extends Annotation> annotations, String topicId, String topicName){

        if (annotations != null){
            Iterator<? extends Annotation> annotIterator = annotations.iterator();

            while (annotIterator.hasNext()){
                if (doesAnnotationHaveTopic(annotIterator.next(), topicId, topicName)){
                    annotIterator.remove();
                }
            }
        }
    }
}
