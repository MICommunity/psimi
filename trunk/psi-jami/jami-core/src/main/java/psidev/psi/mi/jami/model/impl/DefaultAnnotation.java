package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.annotation.UnambiguousAnnotationComparator;

import java.io.Serializable;

/**
 * Default implementation for Annotation.
 *
 * Notes: The equals and hashcode methods have been overridden to be consistent with UnambiguousAnnotationComparator

 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultAnnotation implements Annotation, Serializable {

    private CvTerm topic;
    private String value;

    public DefaultAnnotation(CvTerm topic){
        if (topic == null){
            throw new IllegalArgumentException("The annotation topic is required and cannot be null");
        }
        this.topic = topic;
    }

    public DefaultAnnotation(CvTerm topic, String value){
        this(topic);
        this.value = value;
    }

    public CvTerm getTopic() {
        return this.topic;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return UnambiguousAnnotationComparator.hashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Annotation)){
            return false;
        }

        return UnambiguousAnnotationComparator.areEquals(this, (Annotation) o);
    }

    @Override
    public String toString() {
        return topic.toString()+(value != null ? ": " + value : "");
    }
}
