package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * A controlled vocabulary term defined by an ontology.
 * If the term cannot be described in any ontologies, it should at least have a shortName,
 *
 * Ex: controlled vocabulary terms from the MI ontology (http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/12</pre>
 */

public interface CvTerm {

    public static String PSI_MI = "psi-mi";
    public static String PSI_MI_ID = "MI:0488";
    public static String UNSPECIFIED_ROLE = "unspecified role";
    public static String UNSPECIFIED_ROLE_ID = "MI:0499";

    /**
     * Short label of a controlled vocabulary. It cannot be null or empty.
     * Ex: electrophoresis, binding site, protein, ...
     * @return the short label
     */
    public String getShortName();

    /**
     * Set the short label
     * @param name : short name
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setShortName(String name);

    /**
     * Full name of the controlled vocabulary as it appears in the ontology. It can be null.
     * Ex: electrophoretic mobility-based method, binding-associated region, protein
     * @return the full name
     */
    public String getFullName();

    /**
     * Set the full name
     * @param name : full name
     */
    public void setFullName(String name);

    /**
     * The definition of the controlled vocabulary as it appears in the ontology.
     * It can be null
     * Ex: electrophoresis is Any method which relies on the motion of particles relative to a matrix under the influence of an electrical field.
     * @return the definition
     */
    public String getDefinition();

    /**
     * Set the definition
     * @param def : definition of the term
     */
    public void setDefinition(String def);

    /**
     * The ontology ID which identifies this CvTerm. It can be null if the term does not exist in any ontologies.
     * Ex: MI:0982 from the psi-mi ontology
     * @return the ontology identifier
     */
    public ExternalIdentifier getOntologyIdentifier();

    /**
     * Set the ontology ID
     * @param identifier : ontology ID
     */
    public void setOntologyIdentifier(ExternalIdentifier identifier);

    /**
     * Set of cross references describing the CvTerm.
     * This method should never return null. It can return an empty Set if no xrefs are available for this Cvterm
     * Ex: publication primary references
     * @return the set of Xrefs
     */
    public Set<Xref> getXrefs();

    /**
     * Set of annotations describing the CvTerm.
     * This method should never return null. It can return an empty Set if no annotations are available for this Cvterm.
     * Ex: search url, validation regexp, etc
     * @return the set of annotations
     */
    public Set<Annotation> getAnnotations();

    /**
     * Set of synonyms for this CvTerm
     * This method should never return null. It can return an empty Set if no synonyms are available for this Cvterm
     * Ex: participant detection is a synonym of participant identification method (MI:0002)
     * @return the se of synonyms
     */
    public Set<Alias> getSynonyms();

    /**
     * The parent terms of this controlled vocabulary term.
     * The set cannot be null. If the term does not have any parents, the method should return an empty set.
     * @return the parents
     */
    public Set<CvTerm> getParents();

    /**
     * The children terms of this controlled vocabulary term.
     * The set cannot be null. If the term does not have any children, the method should return an empty set.
     * @return the children
     */
    public Set<CvTerm> getChildren();
}
