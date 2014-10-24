package uk.ac.ebi.intact.jami.ontology;

/**
 * Names of the fields in the ontology solr index.
 *
 */
public interface FieldName {

    String ONTOLOGY = "ontology";
    String PARENT_ID = "pid";
    String PARENT_NAME = "pname";
    String PARENT_SYNONYMS = "psynonyms";
    String CHILDREN_ID = "cid";
    String CHILDREN_NAME = "cname"; 
    String CHILDREN_SYNONYMS = "csynonyms";
}
