package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    /**
     * This method will return the first Annotation having this topicId/topic name
     * It will return null if there are no Annotations with this topic id/name
     * @param annotations : the collection of Annotation
     * @param topicId : the topic id to look for
     * @param topicName : the topic name to look for
     * @return the first annotation having this topic name/id, null if no Annotation with this topic name/id
     */
    public static Annotation collectFirstAnnotationWithTopic(Collection<? extends Annotation> annotations, String topicId, String topicName){

        if (annotations == null || (topicName == null && topicId == null)){
            return null;
        }

        for (Annotation annot : annotations){
            CvTerm method = annot.getTopic();
            // we can compare method ids
            if (topicId != null && method.getMIIdentifier() != null){
                // we have the same method id
                if (method.getMIIdentifier().equals(topicId)){
                    return annot;
                }
            }
            // we need to compare methodName
            else if (topicName != null && topicName.toLowerCase().equals(method.getShortName().toLowerCase())) {
                // we have the same method name
                if (method.getShortName().toLowerCase().trim().equals(topicName)){
                    return annot;
                }
            }
        }

        return null;
    }

    /**
     * Collect all annotations having a specific topic
     * @param annots
     * @param topicId
     * @param topicName
     * @return
     */
    public static Collection<Annotation> collectAllAnnotationsHavingTopic(Collection<? extends Annotation> annots, String topicId, String topicName){

        if (annots == null || annots.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Collection<Annotation> annotations = new ArrayList<Annotation>(annots);

        for (Annotation annot : annotations){
            if (doesAnnotationHaveTopic(annot, topicId, topicName)){
                annotations.add(annot);
            }
        }

        return annotations;
    }
}
