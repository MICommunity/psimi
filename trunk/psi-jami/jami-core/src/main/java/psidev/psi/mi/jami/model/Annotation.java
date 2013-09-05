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

    public static final String COMPLEX_PROPERTIES="complex-properties";
    public static final String COMPLEX_PROPERTIES_MI ="MI:0629";
    public static final String CAUTION="caution";
    public static final String CAUTION_MI ="MI:0618";
    public static final String COMMENT="comment";
    public static final String COMMENT_MI ="MI:0612";
    public static final String COMPLEX_EXPANSION="complex expansion";
    public static final String COMPLEX_EXPANSION_MI ="MI:1059";
    public static final String SPOKE_EXPANSION="spoke expansion";
    public static final String SPOKE_EXPANSION_MI ="MI:1060";
    public static final String MATRIX_EXPANSION="matrix expansion";
    public static final String MATRIX_EXPANSION_MI ="MI:1061";
    public static final String BIPARTITE_EXPANSION="bipartite expansion";
    public static final String BIPARTITE_EXPANSION_MI ="MI:1062";
    public static final String URL="url";
    public static final String URL_MI ="MI:0614";
    public static final String PUBLICATION_TITLE="publication title";
    public static final String PUBLICATION_TITLE_MI ="MI:1091";
    public static final String PUBLICATION_JOURNAL="journal";
    public static final String PUBLICATION_JOURNAL_MI ="MI:0885";
    public static final String PUBLICATION_YEAR="publication year";
    public static final String PUBLICATION_YEAR_MI ="MI:0886";
    public static final String AUTHOR="author-list";
    public static final String AUTHOR_MI ="MI:0636";
    public static final String CONTACT_EMAIL = "contact-email";
    public static final String CONTACT_EMAIL_MI = "MI:0634";
    public static final String IMEX_CURATION = "imex curation";
    public static final String IMEX_CURATION_MI = "MI:0959";
    public static final String MIMIX_CURATION = "mimix curation";
    public static final String MIMIX_CURATION_MI = "MI:0960";
    public static final String RAPID_CURATION = "rapid curation";
    public static final String RAPID_CURATION_MI = "MI:0961";
    public static final String FULL_COVERAGE = "full coverage";
    public static final String FULL_COVERAGE_MI = "MI:0957";
    public static final String PARTIAL_COVERAGE = "partial coverage";
    public static final String PARTIAL_COVERAGE_MI = "MI:0958";
    public static final String EXPERIMENTALLY_OBSERVED = "experimentally-observed";
    public static final String EXPERIMENTALLY_OBSERVED_MI = "MI:1054";
    public static final String IMPORTED = "imported";
    public static final String IMPORTED_MI = "MI:1058";
    public static final String INTERNALLY_CURATED = "internally-curated";
    public static final String INTERNALLY_CURATED_MI = "MI:1055";
    public static final String PREDICTED = "predicted";
    public static final String PREDICTED_MI = "MI:1057";
    public static final String TEXT_MINING = "text-mining";
    public static final String TEXT_MINING_MI = "MI:1056";
    public static final String NUCLEIC_ACID_PROTEIN = "nucleicacid-protein";
    public static final String NUCLEIC_ACID_PROTEIN_MI = "MI:1049";
    public static final String PROTEIN_PROTEIN = "protein-protein";
    public static final String PROTEIN_PROTEIN_MI = "MI:1047";
    public static final String SMALL_MOLECULE_PROTEIN = "smallmolecule-protein";
    public static final String SMALL_MOLECULE_PROTEIN_MI = "MI:1048";
    public static final String CLUSTERED = "clustered";
    public static final String CLUSTERED_MI = "MI:1052";
    public static final String EVIDENCE = "evidence";
    public static final String EVIDENCE_MI = "MI:1051";
    public static final String FIGURE_LEGEND = "figure legend";
    public static final String FIGURE_LEGEND_MI = "MI:0599";
    public static final String EXP_MODIFICATION = "experiment modification";
    public static final String EXP_MODIFICATION_MI = "MI:0627";
    public static final String SEARCH_URL = "search-url";
    public static final String SEARCH_URL_MI = "MI:0615";

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
