package psidev.psi.mi.tab.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 12/06/2012
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public interface Annotation extends psidev.psi.mi.jami.model.Annotation,Serializable {

    /**
     * Getter fot property 'topic'.
     *
     * @return  Getter fot property 'topic'.
     */
    String getAnnotationTopic();

    /**
     * Setter for property 'topic'.
     *
     * @param topic Value to set for property 'topic' in the annotation.
     */
    void setTopic(String topic);

    /**
     *  Getter fot property 'text'.
     *
     * @return  Getter fot property 'text' in the annotation.
     */
    String getText();

    /**
     *  Setter for property 'text'.
     *
     * @param text Value to set for property 'text' in the annotation.
     */
    void setText(String text);
}
