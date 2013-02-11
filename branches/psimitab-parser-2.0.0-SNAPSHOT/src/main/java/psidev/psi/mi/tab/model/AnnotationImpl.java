package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.impl.DefaultAnnotation;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 12/06/2012
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class AnnotationImpl extends DefaultAnnotation implements Annotation {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = 4162569906234270001L;


    /////////////////////////////////
    // Constructor

    public AnnotationImpl(String topic) {
        super(new DefaultCvTerm(topic));
    }

    public AnnotationImpl(String topic, String text) {
        super(new DefaultCvTerm(topic), text);
    }

    /////////////////////////////////
    // Getters and setters

    /**
     * {@inheritDoc}
     */
    public String getAnnotationTopic() {
        return this.topic.getShortName();
    }

    /**
     * {@inheritDoc}
     */
    public void setAnnotationTopic(String topic) {
        if (topic == null) {
            throw new IllegalArgumentException("Topic name cannot be null.");
        }
        this.topic = new DefaultCvTerm(topic);
    }

    /**
     * {@inheritDoc}
     */
    public String getText() {
        return this.value != null ? this.value : null;
    }

    /**
     * {@inheritDoc}
     */
    public void setText(String text) {
        if (text != null) {
            // ignore empty string
            this.value = text.trim();
            if (text.length() == 0) {
                this.value = null;
            }
        }
        else {
            this.value = null;
        }
    }

    ///////////////////////////////
    // Object's override

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Annotation");
        sb.append("{topic='").append(this.topic != null ? this.topic.getShortName() : "-").append('\'');
        sb.append(", text='").append(this.value != null ? this.value : "-").append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AnnotationImpl that = (AnnotationImpl) o;

        if (!topic.getShortName().equals(that.topic.getShortName())) {
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    /*@Override
    public int hashCode() {
        int result;
        result = (value != null ? value.hashCode() : 0);
        result = 29 * result + topic.hashCode();
        return result;
    }*/

}
