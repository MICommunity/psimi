package psidev.psi.mi.jami.utils.factory;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.impl.DefaultAnnotation;

/**
 * Factory for annotations
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/02/13</pre>
 */

public class AnnotationFactory {

    public Annotation createAnnotation(String topicName, String topicMi, String name){
        return new DefaultAnnotation(CvTermFactory.createMICvTerm(topicName, topicMi), name);
    }

    public Annotation createAnnotation(String topicName, String name){
        return new DefaultAnnotation(CvTermFactory.createMICvTerm(topicName, null), name);
    }

    public Annotation createComment(String comment){
        return new DefaultAnnotation(CvTermFactory.createMICvTerm(Annotation.COMMENT, Annotation.COMMENT_MI), comment);
    }

    public Annotation createCaution(String caution){
        return new DefaultAnnotation(CvTermFactory.createMICvTerm(Annotation.CAUTION, Annotation.CAUTION_MI), caution);
    }
}
