package psidev.psi.mi.jami.model;

/**
 * An Annotations gives some information about a specific topic.
 * Ex: topic = 'dataset' and value = 'Alzheimers - Interactions investigated in the context of Alzheimers disease'
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Annotation {

    /**
     * The annotation topic is a controlled vocabulary term and it cannot be null.
     * Ex: dataset, comment, caution, ...
     * @return the annotation topic
     */
    public CvTerm getTopic();

    /**
     * The value of this annotation. Usually free text but can be null if the topic itself is just a tag.
     * Ex: NPM1 negatively regulates the MDM2-TP53 interaction.
     * @return the description of an annotation
     */
    public String getValue();

    /**
     * Set the value of this annotation.
     * @param value
     */
    public void setValue(String value);
}
