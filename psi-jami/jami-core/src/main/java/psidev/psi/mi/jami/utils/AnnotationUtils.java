package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;

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

    /**
     * To know if the annotation does have a specific topic
     * @param annotation
     * @param topicId
     * @param topicName
     * @return true if the annotation has the topic with given name/identifier
     */
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
        Collection<Annotation> annotations = new ArrayList<Annotation>(annots.size());

        for (Annotation annot : annots){
            if (doesAnnotationHaveTopic(annot, topicId, topicName)){
                annotations.add(annot);
            }
        }

        return annotations;
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

        if (annotations == null || annotations.isEmpty()){
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

    public static Annotation createAnnotation(String topicName, String topicMi, String name){
        return new DefaultAnnotation(CvTermUtils.createMICvTerm(topicName, topicMi), name);
    }

    public static Annotation createAnnotation(String topicName, String name){
        return new DefaultAnnotation(CvTermUtils.createMICvTerm(topicName, null), name);
    }

    public static Annotation createComment(String comment){
        return new DefaultAnnotation(CvTermUtils.createMICvTerm(Annotation.COMMENT, Annotation.COMMENT_MI), comment);
    }

    public static Annotation createCaution(String caution){
        return new DefaultAnnotation(CvTermUtils.createMICvTerm(Annotation.CAUTION, Annotation.CAUTION_MI), caution);
    }
}
