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

    public static String COMPLEX_PROPERTIES="complex-properties";
    public static String COMPLEX_PROPERTIES_MI ="MI:0629";
    public static String CAUTION="caution";
    public static String CAUTION_MI ="MI:0618";
    public static String COMMENT="comment";
    public static String COMMENT_MI ="MI:0612";
    public static String COMPLEX_EXPANSION="complex expansion";
    public static String COMPLEX_EXPANSION_MI ="MI:1059";
    public static String SPOKE_EXPANSION="spoke expansion";
    public static String SPOKE_EXPANSION_MI ="MI:1060";
    public static String MATRIX_EXPANSION="matrix expansion";
    public static String MATRIX_EXPANSION_MI ="MI:1061";
    public static String BIPARTITE_EXPANSION="bipartite expansion";
    public static String BIPARTITE_EXPANSION_MI ="MI:1062";
    public static String URL="url";
    public static String URL_MI ="MI:0614";
    public static String PUBLICATION_TITLE="publication title";
    public static String PUBLICATION_TITLE_MI ="MI:1091";
    public static String PUBLICATION_JOURNAL="journal";
    public static String PUBLICATION_JOURNAL_MI ="MI:0885";
    public static String PUBLICATION_YEAR="publication year";
    public static String PUBLICATION_YEAR_MI ="MI:0886";
    public static String AUTHOR="author list";
    public static String AUTHOR_MI ="MI:0636";


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
