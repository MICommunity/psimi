package psidev.psi.mi.tab.model;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 12/06/2012
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class AnnotationImpl implements Annotation {

    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = 4162569906234270001L;

    /////////////////////////
    // Instance variables

    /**
     * Name of the topic in the PSI-MI CV
     */
    private String topic;

    /**
     * Free text for the annotation.
     */
    private String text;

    /////////////////////////////////
    // Constructor

    public AnnotationImpl(String topic) {
        setTopic(topic);
    }

    public AnnotationImpl(String topic, String text) {
        setTopic(topic);
        setText(text);
    }

    /////////////////////////////////
    // Getters and setters

    /**
     * {@inheritDoc}
     */
    public String getTopic() {
        return topic;
    }

    /**
     * {@inheritDoc}
     */
    public void setTopic(String topic) {
        if (topic == null) {
            throw new IllegalArgumentException("Topic name cannot be null.");
        }
        this.topic = topic;
    }

    /**
     * {@inheritDoc}
     */
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    public void setText(String text) {
        if (text != null) {
            // ignore empty string
            this.text = text.trim();
            if (text.length() == 0) {
                this.text = null;
            }
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
        sb.append("{topic='").append(topic).append('\'');
        sb.append(", text='").append(text).append('\'');
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

        if (!topic.equals(that.topic)) {
            return false;
        }
        if (text != null ? !text.equals(that.text) : that.text != null) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;
        result = (text != null ? text.hashCode() : 0);
        result = 29 * result + topic.hashCode();
        return result;
    }

}
