package psidev.psi.mi.jami.model;

import java.util.Collection;

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
    public static String PSI_MI_MI = "MI:0488";
    public static String PSI_MOD = "psi-mod";
    public static String PSI_MOD_MI = "MI:0897";
    public static String PSI_PAR = "psi-par";

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
     * The collection of identifiers which identifies this CvTerm. It is aimed at returning all existing identifiers from one to several external databases and
     * including secondary identifiers or former identifiers.
     * The collection cannot be null so if the term does not exist in any ontologies/databases, the method should return an empty collection.
     * Ex: MI:0982 from the psi-mi ontology
     * @return the ontology identifier
     */
    public Collection<Xref> getIdentifiers();

    /**
     * The unique PSI-MI identifier which identifies the object in the PSI-MI ontology.
     * It is a shortcut for the first psi-mi identifier in the collection of identifiers.
     * It will be null if the collection of identifiers does not contain any PSI-MI Xref objects
     * @return the unique PSI-MI identifier
     */
    public String getMIIdentifier();

    /**
     * The unique PSI-MOD identifier which identifies the object in the PSI-MOD ontology.
     * It is a shortcut for the first psi-mod identifier in the collection of identifiers.
     * It will be null if the collection of identifiers does not contain any PSI-MOD Xref objects
     * @return the unique PSI-MOD identifier
     */
    public String getMODIdentifier();

    /**
     * The unique PSI-PAR identifier which identifies the object in the PSI-PAR ontology.
     * It is a shortcut for the first psi-par identifier in the collection of identifiers.
     * It will be null if the collection of identifiers does not contain any PSI-PAR Xref objects
     * @return the unique PSI-PAR identifier
     */
    public String getPARIdentifier();

    /**
     * Sets the PSI-MI identifier.
     * It will remove the previous PSI-MI identifier from the collection of identifiers, and add the new one in the collection of identifiers
     * with qualifier identity. If mi is null, it will remove all the psi-mi identifiers from the
     * collection of identifiers.
     * @param mi : mi identifier
     */
    public void setMIIdentifier(String mi);

    /**
     * Sets the PSI-MOD identifier.
     * It will remove the previous PSI-MOD identifier from the collection of xrefs, and add the new one in the collection of identifiers
     * with qualifier identity. If mod is null, it will remove all the psi-mod identifiers from the
     * collection of identifiers.
     * @param mod : mod identifier
     */
    public void setMODIdentifier(String mod);

    /**
     * Sets the PSI-PAR identifier.
     * It will remove the previous PSI-PAR identifier from the collection of xrefs, and add the new one in the collection of identifiers
     * with qualifier identity. If par is null, it will remove all the psi-par identifiers from the
     * collection of identifiers.
     * @param par : par identifier
     */
    public void setPARIdentifier(String par);

    /**
     * Collection of cross references describing the CvTerm.
     * This method should never return null. It can return an empty Collection if no xrefs are available for this Cvterm
     * Ex: publication primary references
     * @return the set of Xrefs
     */
    public Collection<Xref> getXrefs();

    /**
     * Collection of annotations describing the CvTerm.
     * This method should never return null. It can return an empty Collection if no annotations are available for this Cvterm.
     * Ex: search url, validation regexp, etc
     * @return the set of annotations
     */
    public Collection<Annotation> getAnnotations();

    /**
     * Collection of synonyms for this CvTerm
     * This method should never return null. It can return an empty Collection if no synonyms are available for this Cvterm
     * Ex: participant detection is a synonym of participant identification method (MI:0002)
     * @return the se of synonyms
     */
    public Collection<Alias> getSynonyms();
}
