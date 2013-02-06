package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;

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
}
